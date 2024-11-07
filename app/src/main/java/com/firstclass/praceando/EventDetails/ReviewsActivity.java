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
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firstclass.praceando.API.mongo.MongoAPI;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.AvaliacaoCallback;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.EventReviewsInterface;
import com.firstclass.praceando.API.mongo.entities.Avaliacao;
import com.firstclass.praceando.API.mongo.entities.AvaliacoesUsuarios;
import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.UserByIdCallback;
import com.firstclass.praceando.API.postgresql.entities.EventoFeed;
import com.firstclass.praceando.Globals;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Review;
import com.firstclass.praceando.entities.User;
import com.firstclass.praceando.firebase.database.Database;
import com.firstclass.praceando.firebase.database.callback.AvatarCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Review> reviewList = new ArrayList<>();
    private ImageView returnArrow;
    private TextView title, nothingFoundText;
    private FloatingActionButton addReviewBtn;
    private EventoFeed event;
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    private MongoAPI mongoAPI = new MongoAPI();
    private Globals globals;
    private ProgressBar progressBar;
    private Database database = new Database();

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

        nothingFoundText = findViewById(R.id.nothingFoundText);
        progressBar = findViewById(R.id.progressBar);
        globals = (Globals) getApplication();
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

            // Adiciona um listener para o RatingBar
            ratingBar.setOnRatingBarChangeListener((ratingBar1,  rating, fromUser) -> {
                // Ativa o botão se o rating for maior que 0
                if (rating > 0) {
                    reviewBtn.setEnabled(true);
                    reviewBtn.setBackgroundColor(getResources().getColor(R.color.rosaEscuro));
                } else {
                    reviewBtn.setEnabled(false);
                    reviewBtn.setBackgroundColor(getResources().getColor(R.color.rosaEscuraoDesativado));
                }
            });

            reviewBtn.setOnClickListener(vv -> {

                progressBar.setVisibility(View.VISIBLE);
                Avaliacao avaliacao = new Avaliacao(event.getId(), globals.getId(), ratingBar.getRating(), comment.getText().toString());

                mongoAPI.createAvaliacao(avaliacao, new AvaliacaoCallback() {
                    @Override
                    public void onSuccess(Avaliacao avaliacao) {
                        Log.e("API", avaliacao+"");
                        Toast.makeText(ReviewsActivity.this, "Obrigada por avaliar!", Toast.LENGTH_SHORT).show();
                        reviewList.clear();
                        addReviewBtn.setVisibility(View.INVISIBLE);
                        addReviewsInTheList();
                        bottomSheetDialog.dismiss();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("API", errorMessage);
                    }
                });

            });

        });

    }

    private void addReviewsInTheList() {
        progressBar.setVisibility(View.VISIBLE);
        mongoAPI.getEventReviews(event.getId(), globals.getId(), new EventReviewsInterface() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(AvaliacoesUsuarios avaliacoesUsuarios) {

                if (!avaliacoesUsuarios.isUsuarioJaAvaliou()) {
                    addReviewBtn.setVisibility(View.VISIBLE);
                }

                if (avaliacoesUsuarios.getAvaliacoes().isEmpty()) {
                    nothingFoundText.setVisibility(View.VISIBLE);
                    return;
                }

                for (Avaliacao avaliacao : avaliacoesUsuarios.getAvaliacoes()) {

                    postgresqlAPI.getUserById(avaliacao.getCdUsuario(), new UserByIdCallback() {
                        @Override
                        public void onSuccess(User user) {
                            progressBar.setVisibility(View.GONE);

                            database.buscarAvatarAtual(user.getId(), new AvatarCallback() {
                                @Override
                                public void onAvatarRetrieved(String avatarAtual) {
                                    reviewList.add(
                                            new Review(
                                                    user.getNome(),
                                                    avatarAtual,
                                                    avaliacao.getNrNota(),
                                                    avaliacao.getDsComentario()
                                            )
                                    );
                                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onError(String errorMessage) {
                                    Log.e("FIREBASE", errorMessage);
                                }
                            });
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Log.e("API", errorMessage);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                title.setText(avaliacoesUsuarios.getAvaliacoes().size()+" Avaliações de "+event.getNomeEvento());
            }

            @Override
            public void onError(String errorMessage) {
                progressBar.setVisibility(View.GONE);
                title.setText(0+" Avaliações de "+event.getNomeEvento());
                nothingFoundText.setVisibility(View.VISIBLE);
                addReviewBtn.setVisibility(View.VISIBLE);
                Log.e("API", errorMessage);
            }
        });

    }
}