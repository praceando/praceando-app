package com.firstclass.praceando.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firstclass.praceando.EventDetails.EventActivity;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Event;
import com.firstclass.praceando.entities.Tag;
import com.firstclass.praceando.marketplace.Payment;
import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class EventItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_EVENT = 1;

    private List<Object> itemList;

    public EventItemAdapter(List itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_EVENT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View viewHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phrase, parent, false);
            return new HeaderViewHolder(viewHeader);
        } else {
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
            return new EventItemViewHolder(viewItem);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.phrase.setText((String) itemList.get(0));

        } else {
            EventItemViewHolder eventHolder = (EventItemViewHolder) holder;
            Event event = (Event) itemList.get(position);

            eventHolder.title.setText(event.getTitle());
            eventHolder.locale.setText(event.getLocale());
            eventHolder.time.setText(event.getTime());
            eventHolder.date.setText(event.getDate());

            Picasso.get()
                    .load(event.getImageUrl())
                    .into(eventHolder.image);

            eventHolder.tagsFlexbox.removeAllViews();
            Tag[] tags = event.getTags();
            LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());

            float density = holder.itemView.getContext().getResources().getDisplayMetrics().density;
            int marginInPixels = (int) (5 * density);

            for (Tag tag : tags) {
                View tagItem = inflater.inflate(R.layout.item_tag, eventHolder.tagsFlexbox, false);

                TextView tagName = tagItem.findViewById(R.id.tagName);
                tagName.setText(tag.getName());

                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tagItem.getLayoutParams();
                layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
                tagItem.setLayoutParams(layoutParams);

                eventHolder.tagsFlexbox.addView(tagItem);
            }

            eventHolder.itemView.setOnClickListener(v -> {
                if (getItemViewType(position) == TYPE_HEADER) {
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putParcelable("event", event);

                Intent intent = new Intent(holder.itemView.getContext(), EventActivity.class);
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView phrase;
        public HeaderViewHolder(@NonNull View view) {
            super(view);
            phrase = view.findViewById(R.id.fraseAmbiental);
        }
    }

    public static class EventItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, time, locale;
        FlexboxLayout tagsFlexbox;
        ImageView image;

        public EventItemViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            time = view.findViewById(R.id.time);
            locale = view.findViewById(R.id.locale);
            image = view.findViewById(R.id.eventImage);
            tagsFlexbox = view.findViewById(R.id.tagsFlexblox);
        }
    }
}
