package com.firstclass.praceando.EventDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firstclass.praceando.entities.Locale;

import java.util.List;

public class LocaleItemAdapter extends ArrayAdapter<Locale> {

    public LocaleItemAdapter(Context context, List<Locale> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        Locale item = getItem(position);
        if (item != null) {
            textView.setText(item.getName());
        }

        return convertView;
    }
}

