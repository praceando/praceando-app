package com.firstclass.praceando.perfil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstclass.praceando.API.mongo.MongoAPI;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.UserGoalsCallback;
import com.firstclass.praceando.API.mongo.entities.ConquistaUser;
import com.firstclass.praceando.Globals;
import com.firstclass.praceando.R;
import com.firstclass.praceando.firebase.authentication.Authentication;
import com.firstclass.praceando.entities.Goal;
import com.firstclass.praceando.login.LandingScreen;
import com.firstclass.praceando.notification.Notify;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PerfilFragment extends Fragment {
    private List<Goal> goalList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Globals globals;
    List<ConquistaUser> conquistaUserList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewGoal);
        view.findViewById(R.id.editBtn).setOnClickListener(v -> startActivity(new Intent(getContext(), PerfilEdit.class)));
        view.findViewById(R.id.singOut).setOnClickListener(v -> {
            new Authentication().signOut();
            startActivity(new Intent(getContext(), LandingScreen.class));
            requireActivity().finish();
        });

        addGoalInTheList();

        GoalAdapter goalAdapter = new GoalAdapter(goalList);
        recyclerView.setAdapter(goalAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ImageView userImage = view.findViewById(R.id.userImage);
        TextView nickname = view.findViewById(R.id.nickname);
        TextView bio = view.findViewById(R.id.bio);

        globals = (Globals) requireActivity().getApplication();

        nickname.setText(globals.getNickname());
        bio.setText(globals.getBio());

        Picasso.get()
                .load(globals.getUserProfileImage())
                .into(userImage);

        if (!globals.isAlreadyNotified()) scheduleNotificationAfterDelay(5000);

        return view;
    }

    public void addGoalInTheList() {

        MongoAPI mongoAPI = new MongoAPI();

        mongoAPI.getUserGoals(1, new UserGoalsCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<ConquistaUser> conquistaUser) {

                for (ConquistaUser conquista : conquistaUser) {
                    goalList.add(new Goal(conquista.getDsConquista(), "1/3", conquista.getNmConquista()));
                }

                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("ERRO", errorMessage);
            }
        });

    }

    private void scheduleNotificationAfterDelay(long delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Create and execute the notification
            Notify notify = new Notify();
            notify.execute(requireContext());
        }, delayMillis);

        globals.setAlreadyNotified(true);
    }
}