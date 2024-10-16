package com.firstclass.praceando.EventDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.ReviewItemViewHolder> {
    private List<Review> reviewList;

    public ReviewItemAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewItemViewHolder holder, int position) {
        Review review = reviewList.get(position);

        holder.nickname.setText(review.getNickname());
        holder.ratingBar.setRating(review.getRating());

        if(review.getComment() == null) {
            holder.comment.setVisibility(View.GONE);
        }else {
            holder.comment.setText(review.getComment());

        }

        Picasso.get()
                .load(review.getUserImage())
                .into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewItemViewHolder extends RecyclerView.ViewHolder {
        TextView nickname, comment;
        ImageView userImage;
        RatingBar ratingBar;

        public ReviewItemViewHolder(@NonNull View viewItem) {
            super(viewItem);
            nickname = viewItem.findViewById(R.id.nickname);
            comment = viewItem.findViewById(R.id.comment);
            userImage = viewItem.findViewById(R.id.userImage);
            ratingBar = viewItem.findViewById(R.id.ratingBar);
        }
    }
}
