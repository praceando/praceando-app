package com.firstclass.praceando.login;

import static android.view.View.inflate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.TagsCallback;
import com.firstclass.praceando.API.postgresql.entities.Interesse;
import com.firstclass.praceando.Globals;
import com.firstclass.praceando.MainActivity;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Tag;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class UserInterest extends AppCompatActivity {
    private List<Tag> tagList = new ArrayList<>();
    private List<Tag> tagsSelected = new ArrayList<>();
    private FlexboxLayout tagsFlexbox;
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_interest);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.returnArrow).setOnClickListener(v -> finish());
        findViewById(R.id.startBtn).setOnClickListener(v -> {
            Globals globals = (Globals) getApplication();
            PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
            List<String> tags = new ArrayList<>();

            for (Tag tag: tagsSelected) {
                tags.add(tag.getName());
            }

            postgresqlAPI.addInteresse(new Interesse(globals.getId(), null, tags));

            navigateToHome();
        });

        tagsFlexbox = findViewById(R.id.tagsFlexblox);

        addTagsInList();
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
            });

            tagsFlexbox.addView(tagItem);
        }
    }

    public void addTagsInList() {

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

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}