package com.firstclass.praceando.EventDetails;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.MainActivity;
import com.firstclass.praceando.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class EventCreationPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_creation_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView adPriceTxt = findViewById(R.id.adPrice);
        findViewById(R.id.returnArrow).setOnClickListener(v -> finish());
        findViewById(R.id.finishBtn).setOnClickListener(v -> navigateToHome());

        TextView durationDescription = findViewById(R.id.durationDescription);
        TextView adDescription = findViewById(R.id.adDescription);

        String startDateString = Objects.requireNonNull(getIntent().getExtras()).getString("startDate");
        String endDateString = getIntent().getExtras().getString("endDate");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = LocalDate.parse(endDateString, formatter);

        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String formattedStartDate = startDate.format(displayFormatter);
        String formattedEndDate = endDate.format(displayFormatter);

        durationDescription.setText("Período do anúncio:\n" + formattedStartDate + " - " + formattedEndDate);

        long dias = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double price = 3.50 * dias;

        String priceStr = String.format("R$ %.2f", price);
        adPriceTxt.setText(priceStr);


        String fullText = "seu anuncio ficara exposto durante " + dias + " dias, totalizando R$" + priceStr;

        SpannableString spannableString = new SpannableString(fullText);

        int diasStartIndex = fullText.indexOf(dias + " dias");
        int diasEndIndex = diasStartIndex + (dias + " dias").length();

        int precoStartIndex = fullText.indexOf(priceStr);
        int precoEndIndex = fullText.length();

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), diasStartIndex, diasEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), precoStartIndex, precoEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        adDescription.setText(spannableString);
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}