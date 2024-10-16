package com.firstclass.praceando.perfil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstclass.praceando.Globals;
import com.firstclass.praceando.R;
import com.firstclass.praceando.authentication.Authentication;
import com.firstclass.praceando.entities.Goal;
import com.firstclass.praceando.fragments.HeaderFragment;
import com.firstclass.praceando.login.LandingScreen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PerfilFragment extends Fragment {
    private List<Goal> goalList = new ArrayList<>();
    private RecyclerView recyclerView;

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

        Globals globals = (Globals) requireActivity().getApplication();

        nickname.setText(globals.getNickname());
        bio.setText(globals.getBio());

        Picasso.get()
                .load(globals.getUserProfileImage())
                .into(userImage);

        return view;
    }

    public void addGoalInTheList() {
        goalList.add(new Goal("Faça 3 avaliações", "1/3"));
        goalList.add(new Goal("Se interesse por 5 eventos", "2/5"));
        goalList.add(new Goal("Vá até 2 parques e/ou praças", "0/2"));
    }
}