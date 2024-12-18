package com.firstclass.praceando.EventDetails;

import static android.view.View.inflate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.TagsCallback;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Tag;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventCreationTagsSelection extends AppCompatActivity {
    private List<Tag> tagList = new ArrayList<>();
    private List<Tag> tagsSelected = new ArrayList<>();
    private FlexboxLayout tagsFlexbox;
    private Button nextBtn;
    private PostgresqlAPI postgresqlAPI;
    private String title, description, localeId, startDate, endDate, startTime, endTime;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_creation_tags_selection);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        ArrayList<Uri> imagesUri = getIntent().getParcelableArrayListExtra("imagesUri");
        localeId = getIntent().getStringExtra("localeId");
        startDate = getIntent().getStringExtra("startDate");
        startTime = getIntent().getStringExtra("startTime");
        endDate = getIntent().getStringExtra("endDate");
        endTime = getIntent().getStringExtra("endTime");

        postgresqlAPI = new PostgresqlAPI();
        tagsFlexbox = findViewById(R.id.tagsFlexblox);
        nextBtn = findViewById(R.id.nextBtn);
        findViewById(R.id.returnArrow).setOnClickListener(v -> finish());

        addTagsInTheList();

        nextBtn.setOnClickListener(v -> {
            ArrayList<String> tags = new ArrayList<>();
            for (Tag tag : tagsSelected) {
                tags.add(tag.getName());
            }

            Intent intent = new Intent(this, EventCreationPayment.class);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            intent.putParcelableArrayListExtra("imagesUri", imagesUri);
            intent.putExtra("localeId", String.valueOf(localeId));
            intent.putExtra("startDate", Objects.requireNonNull(startDate));
            intent.putExtra("endDate", Objects.requireNonNull(endDate));
            intent.putExtra("startTime", Objects.requireNonNull(startTime));
            intent.putExtra("endTime", Objects.requireNonNull(endTime));
            intent.putStringArrayListExtra("tags", tags);
            startActivity(intent);
        });

        updateButtonState();
    }

    private void setupTags() {
        float density = getResources().getDisplayMetrics().density;
        int marginInPixels = (int) (5 * density);

        for (Tag tag : tagList) {
            View tagItem = inflate(this, R.layout.item_tag, null);

            TextView tagName = tagItem.findViewById(R.id.tagName);
            tagName.setText(tag.getName());
            tagName.setTextColor(getColor(R.color.rosaEscurao));
            tagName.setTextSize(13);


            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
            tagItem.setLayoutParams(layoutParams);

            tagItem.setOnClickListener(v -> {
                if (tagsSelected.contains(tag)) {
                    tagName.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_tag));
                    tagsSelected.remove(tag);
                } else if (tagsSelected.size() < 5) {
                    tagName.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_tag_selected));
                    tagsSelected.add(tag);
                }
                updateButtonState();
            });

            tagsFlexbox.addView(tagItem);
        }
    }

    private void updateButtonState() {
        if (tagsSelected.size() == 0) {
            nextBtn.setEnabled(false);
            nextBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.rosaEscuraoDesativado));
        } else {
            nextBtn.setEnabled(true);
            nextBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.rosaEscuraoClaro));
        }
    }

    private void addTagsInTheList() {

        postgresqlAPI.getTags(new TagsCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Tag> tags) {
                tagList.addAll(tags);
                setupTags();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

}