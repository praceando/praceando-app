package com.firstclass.praceando.perfil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Goal;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Goal> goalList;

    public GoalAdapter(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goal, parent, false);
        return new GoalItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GoalItemViewHolder goalHolder = (GoalItemViewHolder) holder;
        Goal goal = goalList.get(position);

        goalHolder.description.setText(goal.getDescription());
        goalHolder.score.setText(goal.getScore());
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    public static class GoalItemViewHolder extends RecyclerView.ViewHolder {
        TextView description, score;

        public GoalItemViewHolder(@NonNull View view) {
            super(view);
            description = view.findViewById(R.id.description);
            score = view.findViewById(R.id.score);
        }
    }
}

