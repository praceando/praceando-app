package com.firstclass.praceando.EventDetails;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Tag;

import java.util.List;

public class TagsItemAdapter extends RecyclerView.Adapter<TagsItemAdapter.TagsItemViewHolder> {
    private List<Tag> tagsList;
    public TagsItemAdapter(List<Tag> tagsList) {
        this.tagsList = tagsList;
    }
    @NonNull
    @Override
    public TagsItemAdapter.TagsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new TagsItemViewHolder(viewItem);
    }
    @Override
    public void onBindViewHolder(@NonNull TagsItemAdapter.TagsItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(tagsList.get(position).getName());
    }
    @Override
    public int getItemCount() {
        return tagsList.size();
    }
    public class TagsItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public TagsItemViewHolder(@NonNull View viewItem) {
            super(viewItem);
            name = viewItem.findViewById(R.id.tagName);
        }
    }
}