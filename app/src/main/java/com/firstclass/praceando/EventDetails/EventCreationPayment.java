package com.firstclass.praceando.EventDetails;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.API.mongo.MongoAPI;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.RecorrenciaCallback;
import com.firstclass.praceando.API.mongo.entities.Recorrencia;
import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.CreateCompraCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.CreateEventoCallback;
import com.firstclass.praceando.API.postgresql.entities.CreateCompra;
import com.firstclass.praceando.API.postgresql.entities.CreateCompraResponse;
import com.firstclass.praceando.API.postgresql.entities.CreateEventoResponse;
import com.firstclass.praceando.API.postgresql.entities.Evento;
import com.firstclass.praceando.API.postgresql.entities.Evento2;
import com.firstclass.praceando.Globals;
import com.firstclass.praceando.MainActivity;
import com.firstclass.praceando.R;
import com.firstclass.praceando.firebase.database.Database;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

public class EventCreationPayment extends AppCompatActivity {
    private String startDateString, endDateString, title, description, localeId, startTime, endTime;
    private String[] tags;
    private Globals globals;
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    private MongoAPI mongoAPI = new MongoAPI();
    private double price;
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

        globals = (Globals) getApplication();

        startDateString = Objects.requireNonNull(getIntent().getStringExtra("startDate"));
        endDateString = getIntent().getStringExtra("endDate");

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        ArrayList<Uri> imagesUri = getIntent().getParcelableArrayListExtra("imagesUri");
        localeId = getIntent().getStringExtra("localeId");
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        tags = getIntent().getStringArrayListExtra("tags").toArray(new String[0]);


        Evento createEvento = new Evento(Long.parseLong(localeId), globals.getId(), title, description, startDateString, startTime, endDateString, endTime);
        Evento2 evento = new Evento2(createEvento, tags);

        Log.e("EVENTOO", evento+"");
        postgresqlAPI.createEvento(evento, new CreateEventoCallback() {

            @Override
            public void onResponse(CreateEventoResponse response) {
                Toast.makeText(EventCreationPayment.this, "Evento criado!", Toast.LENGTH_SHORT).show();
                postgresqlAPI.createCompra(new CreateCompra(globals.getId(), response.getIdEvento(), price), new CreateCompraCallback() {
                    @Override
                    public void onSuccess(CreateCompraResponse response) {
                        postgresqlAPI.pagamento(response.getIdCompra());

                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("API", "erro ao criar compra     "+errorMessage);
                    }
                });

                mongoAPI.createRecorrencia(new Recorrencia("Diaria", response.getIdEvento()), new RecorrenciaCallback() {
                    @Override
                    public void onSuccess(Recorrencia recorrencia) {
                        Log.e("API", "recorrencia criada");
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("API", "erro ao criar recorrencia     "+errorMessage);
                    }
                });

                Handler handler = new Handler(Looper.getMainLooper());

                for (int i = 0; i < imagesUri.size(); i++) {
                    int finalI = i;
                    handler.postDelayed(() -> {
                        ImageView imageView = new ImageView(EventCreationPayment.this);
                        Picasso.get().load(imagesUri.get(finalI)).into(imageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Database database = new Database();
                                // A imagem foi carregada com sucesso
                                database.uploadFoto(String.valueOf(response.getIdEvento()), imageView);
                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }, finalI * 700);
                }

            }

            @Override
            public void onError(String errorMessage) {
                Log.e("EVENTO", errorMessage);
            }
        });


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = LocalDate.parse(endDateString, formatter);

        durationDescription.setText("Período do anúncio:\n\n" + startDateString + " - " + endDateString);

        long dias = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        price = 3.50 * dias;

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