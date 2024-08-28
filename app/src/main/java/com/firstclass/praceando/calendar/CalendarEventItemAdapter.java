package com.firstclass.praceando.calendar;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        // Carrega os dados do objeto no viewItem
        holder.title.setText(eventlist.get(position).getTitle());
        holder.locale.setText(eventlist.get(position).getLocale());
        holder.time.setText(eventlist.get(position).getTime());
        holder.date.setText(eventlist.get(position).getDate());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Position clicked: " + position, Toast.LENGTH_SHORT).show();
//            }
//        });
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