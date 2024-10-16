package com.firstclass.praceando.calendar;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.firstclass.praceando.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarViewFragment extends Fragment {

    CalendarView calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_view, container, false);

        calendar = view.findViewById(R.id.calendarView);
        calendar.setDateTextAppearance(R.style.CalenderViewDateCustomText);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Create a Calendar instance and set the selected date
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                // Define the desired date format
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d, MMMM", new Locale("pt", "BR"));
                String formattedDate = dateFormat.format(selectedDate.getTime());

                // Find the parent fragment (CalendarFragment) and update the date
                CalendarFragment parentFragment = (CalendarFragment) getParentFragment();
                if (parentFragment != null) {
                    parentFragment.updateDateText(formattedDate);
                }
            }
        });

        return view;
    }
}
