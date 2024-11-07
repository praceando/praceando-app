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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstclass.praceando.API.mongo.MongoAPI;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.UserGoalsCallback;
import com.firstclass.praceando.API.mongo.entities.ConquistaUser;
import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.ProductByIdCallback;
import com.firstclass.praceando.Globals;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Product;
import com.firstclass.praceando.firebase.authentication.Authentication;
import com.firstclass.praceando.entities.Goal;
import com.firstclass.praceando.login.LandingScreen;
import com.firstclass.praceando.marketplace.Payment;
import com.firstclass.praceando.notification.Notify;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PerfilFragment extends Fragment {
    private List<Goal> goalList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Globals globals;
    private Button premiumBtn;
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();


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

        premiumBtn = view.findViewById(R.id.premiumBtn);
        GoalAdapter goalAdapter = new GoalAdapter(goalList);
        recyclerView.setAdapter(goalAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ImageView userImage = view.findViewById(R.id.userImage);
        TextView nickname = view.findViewById(R.id.nickname);
        TextView bio = view.findViewById(R.id.bio);

        globals = (Globals) requireActivity().getApplication();

        if (globals.getUserRole() == 1 ) premiumBtn.setText("Estatísticas gerais");

        if (globals.getUserRole() == 2 && globals.getPremium()) premiumBtn.setText("Minhas estatísticas");

        nickname.setText(globals.getNickname());
        bio.setText(globals.getBio());

        Picasso.get()
                .load(globals.getUserProfileImage())
                .into(userImage);

        if (!globals.isAlreadyNotified()) scheduleNotificationAfterDelay(5000);

        premiumBtn.setOnClickListener(v -> {
            Intent intent;
            if (globals.getUserRole() == 2 && globals.getPremium()) {
                intent = new Intent(requireActivity(), AreaRestrita.class);
                startActivity(intent);
            }
            else if (globals.getUserRole() == 2) {
                intent = new Intent(requireActivity(), Payment.class);
                postgresqlAPI.getProductById(4, new ProductByIdCallback() {
                    @Override
                    public void onSuccess(Product product) {
                        intent.putExtra("title", product.getTitle());
                        intent.putExtra("price", product.getPrice());
                        intent.putExtra("image", product.getImageUrl());
                        intent.putExtra("description", product.getDescription());
                        intent.putExtra("id", product.getId());
                        intent.putExtra("categoria", product.getNmCategoria());

                        startActivity(intent);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("API", errorMessage);
                    }
                });
            } else {
                intent = new Intent(requireActivity(), AreaRestrita.class);
                startActivity(intent);
            }

        });

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
            if (isAdded()) {
                Notify notify = new Notify();
                notify.execute(requireContext());
                globals.setAlreadyNotified(true);
            }
        }, delayMillis);
    }

}