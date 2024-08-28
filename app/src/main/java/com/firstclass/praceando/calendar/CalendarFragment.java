package com.firstclass.praceando.calendar;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Event;
import com.firstclass.praceando.fragments.HeaderFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    TextView dateTxt;
    RecyclerView recyclerView;
    List<Event> eventList = new ArrayList<>();
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

        eventList.add(new Event("9:30h - 17h", "Praça do Samba", "14/07 - 14/07", "Hot Dog Sr. Antonio"));
        eventList.add(new Event("9:30h - 17h", "Praça do Samba", "14/07 - 14/07", "Hot Dog Sr. Antonio"));
        eventList.add(new Event("9:30h - 17h", "Praça do Samba", "14/07 - 14/07", "Hot Dog Sr. Antonio"));
        eventList.add(new Event("9:30h - 17h", "Praça do Samba", "14/07 - 14/07", "Hot Dog Sr. Antonio"));
        eventList.add(new Event("9:30h - 17h", "Praça do Samba", "14/07 - 14/07", "Hot Dog Sr. Antonio"));
        eventList.add(new Event("9:30h - 17h", "Praça do Samba", "14/07 - 14/07", "Hot Dog Sr. Antonio"));
        eventList.add(new Event("9:30h - 17h", "Praça do Samba", "14/07 - 14/07", "Hot Dog Sr. Antonio"));

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
}
