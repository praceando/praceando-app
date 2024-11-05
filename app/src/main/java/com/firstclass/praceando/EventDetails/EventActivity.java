package com.firstclass.praceando.EventDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.firstclass.praceando.API.mongo.MongoAPI;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.EventMeanInterface;
import com.firstclass.praceando.API.mongo.entities.Mean;
import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.PostgresqlAPIInterface;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.EventByIdCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.TagsCallback;
import com.firstclass.praceando.API.postgresql.entities.Evento;
import com.firstclass.praceando.API.postgresql.entities.EventoCompleto;
import com.firstclass.praceando.API.postgresql.entities.EventoFeed;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Event;
import com.firstclass.praceando.entities.Tag;
import com.firstclass.praceando.firebase.database.Database;
import com.firstclass.praceando.firebase.database.FotosCallback;
import com.squareup.picasso.Picasso;

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
    private ProgressBar progressBar;
    private MongoAPI mongoAPI = new MongoAPI();

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

        progressBar = findViewById(R.id.progressBar);
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

        Database database = new Database();

        database.listar(event.getId(), new FotosCallback() {
            @Override
            public void onFotosReceived(List<String> fotos) {
                if (fotos != null && !fotos.isEmpty()) {
                    for (String url: fotos) {
                        imageList.add(new SlideModel(url, ScaleTypes.CENTER_CROP));
                    }
                    ImageSlider imageSlider = findViewById(R.id.imageSlider);
                    imageSlider.setImageList(imageList);
                }
            }
        });

        goToReviews.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("event", event);

            Intent intent = new Intent(this, ReviewsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        getMeanById();
        getEventById();

    }

    private void getMeanById() {
            mongoAPI.getEventMean(event.getId(), new EventMeanInterface() {
                @Override
                public void onSuccess(Mean mean) {
                    ratingBar.setRating(mean.getMedia());
                    averageRate.setText(String.valueOf(mean.getMedia()));
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("API", errorMessage);
                }
            });
    }

    private void getEventById() {
        progressBar.setVisibility(View.VISIBLE);
        postgresqlAPI.getEventById(event.getId(), new EventByIdCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(EventoCompleto evento) {

                title.setText(evento.getNmEvento());
                locale.setText(evento.getLocale().getName());
                time.setText(evento.getFormattedHrInicio() + " - " + evento.getFormattedHrFim());
                date.setText(evento.getFormattedDtInicio() + " - " + evento.getFormattedDtFim());
                description.setText(evento.getDsEvento());

                for (String tagName: event.getTags()) {
                    tagList.add(new Tag(0L, tagName));
                }

                TagsItemAdapter tagsItemAdapter = new TagsItemAdapter(tagList);
                recyclerView.setAdapter(tagsItemAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(EventActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API", errorMessage);
            }
        });
    }
}