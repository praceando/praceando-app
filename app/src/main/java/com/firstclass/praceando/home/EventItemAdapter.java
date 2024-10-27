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

import com.firstclass.praceando.API.postgresql.entities.Evento;
import com.firstclass.praceando.API.postgresql.entities.EventoFeed;
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
            EventoFeed event = (EventoFeed) itemList.get(position);

            eventHolder.title.setText(event.getNomeEvento());
            eventHolder.locale.setText(event.getNomeLocal());
            eventHolder.time.setText(event.getFormattedHrInicio() + " - " + event.getFormattedHrFim());
            eventHolder.date.setText(event.getFormattedDtInicio() + " - " + event.getFormattedDtFim());

            Picasso.get()
                    .load("https://joycone.com/wp-content/uploads/2019/07/joycone_milkshakes3.jpeg")
                    .into(eventHolder.image);

            eventHolder.tagsFlexbox.removeAllViews();
            List<String> tags = event.getTags();
            LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());

            float density = holder.itemView.getContext().getResources().getDisplayMetrics().density;
            int marginInPixels = (int) (5 * density);

            for (String tag : tags) {
                View tagItem = inflater.inflate(R.layout.item_tag, eventHolder.tagsFlexbox, false);

                TextView tagName = tagItem.findViewById(R.id.tagName);
                tagName.setText(tag);

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
