package com.firstclass.praceando.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.EventsCallback;
import com.firstclass.praceando.API.postgresql.entities.EventoFeed;
import com.firstclass.praceando.R;
import com.firstclass.praceando.fragments.HeaderFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CalendarFragment extends Fragment {

    private TextView dateTxt;
    private RecyclerView recyclerView;
    private List<EventoFeed> eventList = new ArrayList<>();
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout first
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Initialize and commit child fragments
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.calendarFragmentLayout, new CalendarViewFragment());
        fragmentTransaction.add(R.id.headerFragmentLayout, new HeaderFragment());
        fragmentTransaction.commit();

        dateTxt = view.findViewById(R.id.dateText);



        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d, MMMM", new Locale("pt", "BR"));
        String formattedDate = dateFormat.format(currentDate.getTime());

        dateTxt.setText(formattedDate);

        SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd", new Locale("pt", "BR"));
        String formattedDateShort = dateFormatShort.format(currentDate.getTime());

        loadEvents(formattedDateShort);

        recyclerView = view.findViewById(R.id.recyclerViewCalendarEvent);
        CalendarEventItemAdapter calendarEventItemAdaptereventAdapter = new CalendarEventItemAdapter(eventList);
        recyclerView.setAdapter(calendarEventItemAdaptereventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    // Method to update the date TextView from another fragment
    public void updateDateText(String date) {
        if (dateTxt != null) {
            dateTxt.setText(date);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void loadEvents(String date) {

        eventList.clear();
        postgresqlAPI.getEventsByDate(date, new EventsCallback() {
            @Override
            public void onSuccess(List<EventoFeed> events) {
                eventList.addAll(events);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
