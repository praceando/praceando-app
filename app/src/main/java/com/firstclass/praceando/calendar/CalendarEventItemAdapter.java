package com.firstclass.praceando.calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstclass.praceando.EventDetails.EventActivity;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Event;

import java.util.List;

public class CalendarEventItemAdapter extends RecyclerView.Adapter<CalendarEventItemAdapter.CalendarEventItemViewHolder> {
    private List<Event> eventlist;
    public CalendarEventItemAdapter(List<Event> eventList) {
        this.eventlist = eventList;
    }
    @NonNull
    @Override
    public CalendarEventItemAdapter.CalendarEventItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_calendar, parent, false);
        return new CalendarEventItemViewHolder(viewItem);
    }
    @Override
    public void onBindViewHolder(@NonNull CalendarEventItemAdapter.CalendarEventItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Event event = eventlist.get(position);

        holder.title.setText(event.getTitle());
        holder.locale.setText(event.getLocale());
        holder.time.setText(event.getTime());
        holder.date.setText(event.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("event", event);

                Intent intent = new Intent(holder.itemView.getContext(), EventActivity.class);
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return eventlist.size();
    }
    public class CalendarEventItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, time, locale;
        public CalendarEventItemViewHolder(@NonNull View viewItem) {
            super(viewItem);
            title = viewItem.findViewById(R.id.title);
            date = viewItem.findViewById(R.id.date);
            time = viewItem.findViewById(R.id.time);
            locale = viewItem.findViewById(R.id.locale);
        }
    }
}