package com.firstclass.praceando.EventDetails;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firstclass.praceando.API.mongo.MongoAPI;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.EventReviewsInterface;
import com.firstclass.praceando.API.mongo.entities.Avaliacao;
import com.firstclass.praceando.API.mongo.entities.AvaliacoesUsuarios;
import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.UserByIdCallback;
import com.firstclass.praceando.API.postgresql.entities.EventoFeed;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Review;
import com.firstclass.praceando.entities.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Review> reviewList = new ArrayList<>();
    private ImageView returnArrow;
    private TextView title;
    private FloatingActionButton addReviewBtn;
    private EventoFeed event;
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        EdgeToEdge.enable(this);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 60, systemBars.right, systemBars.bottom);
            return insets;
        });

        returnArrow = findViewById(R.id.returnArrow);
        returnArrow.setOnClickListener(v -> finish());
        title = findViewById(R.id.title);
        addReviewBtn = findViewById(R.id.addReviewBtn);

        event = getIntent().getParcelableExtra("event");

        recyclerView = findViewById(R.id.commentsRecyclerView);

       addReviewsInTheList();

        ReviewItemAdapter reviewItemAdapter = new ReviewItemAdapter(reviewList);
        recyclerView.setAdapter(reviewItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addReviewBtn.setOnClickListener(v -> {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ReviewsActivity.this);
            View view = LayoutInflater.from(ReviewsActivity.this).inflate(R.layout.botton_sheet_review, null);
            bottomSheetDialog.setContentView(view);
            bottomSheetDialog.show();

            TextView title = view.findViewById(R.id.title);
            RatingBar ratingBar = view.findViewById(R.id.ratingBar);
            EditText comment = view.findViewById(R.id.comment);
            Button reviewBtn = view.findViewById(R.id.reviewBtn);

            title.setText("Como você avaliaria "+event.getNomeEvento()+"?");
            reviewBtn.setOnClickListener(vv -> {
                //enviar para a api e tals
                bottomSheetDialog.dismiss();
            });

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    Toast.makeText(ReviewsActivity.this, ratingBar.getRating() + "", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void addReviewsInTheList() {

        MongoAPI mongoAPI = new MongoAPI();

        mongoAPI.getEventReviews(event.getId(), 1, new EventReviewsInterface() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(AvaliacoesUsuarios avaliacoesUsuarios) {

                for (Avaliacao avaliacao : avaliacoesUsuarios.getAvaliacoes()) {

                    postgresqlAPI.getUserById(avaliacao.getCdUsuario(), new UserByIdCallback() {
                        @Override
                        public void onSuccess(User user) {
                            Log.e("USER", user+"");
                            reviewList.add(
                                    new Review(
                                            user.getNome(),
                                            "https://img.freepik.com/psd-gratuitas/ilustracao-3d-de-avatar-ou-perfil-humano_23-2150671142.jpg?size=338&ext=jpg&ga=GA1.1.1413502914.1725148800&semt=ais_hybrid",
                                            avaliacao.getNrNota(),
                                            avaliacao.getDsComentario()
                                    )
                            );
                            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Log.e("API", errorMessage);
                        }
                    });
                }
                title.setText(avaliacoesUsuarios.getAvaliacoes().size()+" Avaliações de "+event.getNomeEvento());
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("ERRO", errorMessage);
            }
        });

    }
}