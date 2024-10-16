package com.firstclass.praceando.EventDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Event;
import com.firstclass.praceando.entities.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EventActivity  extends AppCompatActivity {

    private ImageView returnArrow;
    private TextView title, locale, time, date;
    private LinearLayout goToReviews;
    private RatingBar ratingBar;
    private RecyclerView recyclerView;
    private List<Tag> tagList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        locale = findViewById(R.id.locale);
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);
        recyclerView = findViewById(R.id.tagsRecyclerView);
        goToReviews = findViewById(R.id.goToReviews);

        returnArrow = findViewById(R.id.returnArrow);
        returnArrow.setOnClickListener(v -> finish());

        title = findViewById(R.id.eventTitle);
        Event event = (Event) getIntent().getParcelableExtra("event");

        ratingBar = findViewById(R.id.ratingBar);

        title.setText(event.getTitle());
        locale.setText(event.getLocale());
        time.setText(event.getTime());
        date.setText(event.getDate());

        ArrayList<SlideModel> imageList = new ArrayList<>();

        imageList.add(new SlideModel(event.getImageUrl(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://lumiere-a.akamaihd.net/v1/images/07ff8e314e2798d32bfc8c39f82a9601677de34c.jpeg?region=0,0,600,600", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://i.pinimg.com/236x/d5/47/e0/d547e0429887780910f5647135c25845.jpg", ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        imageSlider.setImageList(imageList);

        ratingBar.setRating(3.5F);

        tagList = Arrays.asList(event.getTags());

        TagsItemAdapter tagsItemAdapter = new TagsItemAdapter(tagList);
        recyclerView.setAdapter(tagsItemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        goToReviews.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("event", event);

            Intent intent = new Intent(this, ReviewsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

    }
}