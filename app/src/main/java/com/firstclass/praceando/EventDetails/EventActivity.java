package com.firstclass.praceando.EventDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.PostgresqlAPIInterface;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.EventByIdCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.TagsCallback;
import com.firstclass.praceando.API.postgresql.entities.Evento;
import com.firstclass.praceando.API.postgresql.entities.EventoFeed;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Event;
import com.firstclass.praceando.entities.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class EventActivity  extends AppCompatActivity {

    private ImageView returnArrow;
    private TextView title, locale, time, date, averageRate, description;
    private LinearLayout goToReviews;
    private RatingBar ratingBar;
    private RecyclerView recyclerView;
    private List<Tag> tagList = new ArrayList<>();
    private final PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    private EventoFeed event;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 60, systemBars.right, systemBars.bottom);
            return insets;
        });

        averageRate = findViewById(R.id.averageRate);
        locale = findViewById(R.id.locale);
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);
        recyclerView = findViewById(R.id.tagsRecyclerView);
        goToReviews = findViewById(R.id.goToReviews);
        description = findViewById(R.id.description);

        returnArrow = findViewById(R.id.returnArrow);
        returnArrow.setOnClickListener(v -> finish());

        title = findViewById(R.id.eventTitle);
        event = (EventoFeed) getIntent().getParcelableExtra("event");

        ratingBar = findViewById(R.id.ratingBar);


        ArrayList<SlideModel> imageList = new ArrayList<>();

//        imageList.add(new SlideModel(event.getImageUrl(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://lumiere-a.akamaihd.net/v1/images/07ff8e314e2798d32bfc8c39f82a9601677de34c.jpeg?region=0,0,600,600", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://i.pinimg.com/236x/d5/47/e0/d547e0429887780910f5647135c25845.jpg", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://i.scdn.co/image/ab67616d00001e02d2446570a048376ec2e720f4", ScaleTypes.CENTER_CROP));


        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        imageSlider.setImageList(imageList);

        ratingBar.setRating(3.5F);
        averageRate.setText(String.valueOf(3.5));

        goToReviews.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("event", event);

            Intent intent = new Intent(this, ReviewsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        getEventById();

    }

    private void getEventById() {
        postgresqlAPI.getEventById(event.getId(), new EventByIdCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(Evento evento) {
                Log.e("EVENTO",""+evento);

                title.setText(evento.getNmEvento());
                locale.setText(evento.getLocale().getName());
                time.setText(evento.getFormattedHrInicio() + " - " + evento.getFormattedHrFim());
                date.setText(evento.getFormattedDtInicio() + " - " + evento.getFormattedDtFim());
                description.setText(evento.getDsEvento());

//                tagList = Arrays.asList(evento.getTags());
                tagList.add(new Tag(1L, "gastronomia"));
                tagList.add(new Tag(1L, "arte"));
                tagList.add(new Tag(1L, "cultura"));

                TagsItemAdapter tagsItemAdapter = new TagsItemAdapter(tagList);
                recyclerView.setAdapter(tagsItemAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(EventActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}