package com.firstclass.praceando.EventDetails;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Event;
import com.firstclass.praceando.entities.Review;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Review> reviewList = new ArrayList<>();
    private ImageView returnArrow;
    private TextView title;
    private FloatingActionButton addReviewBtn;
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

        Event event = (Event) getIntent().getParcelableExtra("event");

        recyclerView = findViewById(R.id.commentsRecyclerView);

        reviewList.add(new Review(
                "MysticShadow_StarGazer_NightWhisper",
                "https://img.freepik.com/psd-gratuitas/ilustracao-3d-de-avatar-ou-perfil-humano_23-2150671142.jpg?size=338&ext=jpg&ga=GA1.1.1413502914.1725148800&semt=ais_hybrid",
                4.5f,
                "Excelente evento! Tudo foi bem organizado e divertido."
        ));

        reviewList.add(new Review(
                "NightRider_HiddenEcho",
                "https://img.freepik.com/psd-gratis/ilustracion-3d-avatar-o-perfil-humano_23-2150671116.jpg?size=338&ext=jpg&ga=GA1.1.2008272138.1724976000&semt=ais_hybrid",
                3.0f,
                "Foi um bom evento, mas algumas atividades poderiam ter sido melhores."
        ));

        reviewList.add(new Review(
                "EchoKnight_DuskBringer",
                "https://img.freepik.com/psd-gratuitas/ilustracao-3d-de-avatar-ou-perfil-humano_23-2150671130.jpg?size=338&ext=jpg&ga=GA1.1.2008272138.1725062400&semt=ais_hybrid",
                2.5f
        ));

        reviewList.add(new Review(
                "LunarWolf_SilentHunter",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfMkSd3q4P5iEp8Afhty7gZwuyAHBQCNXbYagz8D_oEkcu-7yyggxiCXZUjnFTwT_OpDY&usqp=CAU",
                5.0f,
                "Incrível! Superou todas as minhas expectativas!"
        ));

        reviewList.add(new Review(
                "SolarFlare_DreamChaser",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmzwO4A38YY78S55EjUOwdqXNUiKqm6tVnacvqUj0brZ8kOyUseOe2RBfUzotpsICntho&usqp=CAU",
                4.0f,
                "Ótimo evento, mas o local poderia ser mais acessível."
        ));

        reviewList.add(new Review(
                "EchoKnight_DuskBringer",
                "https://img.freepik.com/psd-gratuitas/ilustracao-3d-de-avatar-ou-perfil-humano_23-2150671120.jpg?size=338&ext=jpg&ga=GA1.1.2008272138.1724889600&semt=ais_hybrid",
                2.5f,
                "Não gostei muito do evento, a organização deixou a desejar."
        ));

        ReviewItemAdapter reviewItemAdapter = new ReviewItemAdapter(reviewList);
        recyclerView.setAdapter(reviewItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        title.setText(reviewList.size()+" Avaliações de "+event.getTitle());


        addReviewBtn.setOnClickListener(v -> {
//            Dialog reviewDialog = new Dialog(ReviewsActivity.this);
//            reviewDialog.setContentView(R.layout.dialog_review);
//            reviewDialog.getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
////                reviewDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.));
//
//            //nao permitir clique fora da caixa
//            reviewDialog.setCancelable(false);
//
//            //inicializar os componentes da caixa
//            TextView title = reviewDialog.findViewById(R.id.title);
//            EditText comment = reviewDialog.findViewById(R.id.comment);
//            RatingBar ratingBar = reviewDialog.findViewById(R.id.ratingBar);
//            Button reviewBtn = reviewDialog.findViewById(R.id.reviewBtn);
//            ImageView closeBtn = reviewDialog.findViewById(R.id.closeBtn);
//
//            title.setText("Como você avaliaria "+event.getTitle()+"?");
//
//            closeBtn.setOnClickListener(view -> {reviewDialog.dismiss();});
//            reviewDialog.show();

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ReviewsActivity.this);
            View view = LayoutInflater.from(ReviewsActivity.this).inflate(R.layout.botton_sheet_review, null);
            bottomSheetDialog.setContentView(view);
            bottomSheetDialog.show();

            TextView title = view.findViewById(R.id.title);
            RatingBar ratingBar = view.findViewById(R.id.ratingBar);
            EditText comment = view.findViewById(R.id.comment);
            Button reviewBtn = view.findViewById(R.id.reviewBtn);

            title.setText("Como você avaliaria "+event.getTitle()+"?");
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
}