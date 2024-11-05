package com.firstclass.praceando.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.R;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.consumerBtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, GenderAndBirth.class);
            intent.putExtra("type", "consumer");
            startActivity(intent);
        });

        findViewById(R.id.advertiserBtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, GenderAndBirth.class);
            intent.putExtra("type", "advertiser");
            startActivity(intent);
        });

        findViewById(R.id.enterTxt).setOnClickListener(v -> startActivity(new Intent(this, LandingScreen.class)));
    }
}