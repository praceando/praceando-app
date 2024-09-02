package com.firstclass.praceando.EventDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firstclass.praceando.R;

public class ActivityReviews extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        recyclerView = findViewById(R.id.commentsRecyclerView);


    }
}