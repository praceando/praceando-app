package com.firstclass.praceando.login;

import static android.view.View.inflate;

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
        findViewById(R.id.startBtn).setOnClickListener(v -> navigateToHome());

        tagsFlexbox = findViewById(R.id.tagsFlexblox);

        addTagsInList();
        setupTags();
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
        tagList.add(new Tag(1L, "Artesanato"));
        tagList.add(new Tag(2L, "Pintura"));
        tagList.add(new Tag(3L, "Escultura"));
        tagList.add(new Tag(4L, "Fotografia"));
        tagList.add(new Tag(5L, "Música ao vivo"));
        tagList.add(new Tag(6L, "Teatro"));
        tagList.add(new Tag(7L, "Dança"));
        tagList.add(new Tag(8L, "Cinema"));
        tagList.add(new Tag(9L, "Literatura"));
        tagList.add(new Tag(10L, "Feira de livros"));
        tagList.add(new Tag(11L, "Festival de comida"));
        tagList.add(new Tag(12L, "Exposição de arte"));
        tagList.add(new Tag(13L, "Show de comédia"));
        tagList.add(new Tag(14L, "Stand-up"));
        tagList.add(new Tag(15L, "Workshop"));
        tagList.add(new Tag(16L, "Seminário"));
        tagList.add(new Tag(17L, "Palestra"));
        tagList.add(new Tag(18L, "Conferência"));
        tagList.add(new Tag(19L, "Encontro de negócios"));
        tagList.add(new Tag(20L, "Networking"));
        tagList.add(new Tag(21L, "Hackathon"));
        tagList.add(new Tag(22L, "Corrida de rua"));
        tagList.add(new Tag(23L, "Maratona"));
        tagList.add(new Tag(24L, "Triatlo"));
        tagList.add(new Tag(25L, "Caminhada ecológica"));
        tagList.add(new Tag(26L, "Acampamento"));
        tagList.add(new Tag(27L, "Festival de música"));
        tagList.add(new Tag(28L, "Competição de dança"));
        tagList.add(new Tag(29L, "Feira de ciências"));
        tagList.add(new Tag(30L, "Festa junina"));
        tagList.add(new Tag(31L, "Carnaval"));
        tagList.add(new Tag(32L, "Festa de ano novo"));
        tagList.add(new Tag(33L, "Festa temática"));
        tagList.add(new Tag(34L, "Festa de casamento"));
        tagList.add(new Tag(35L, "Evento corporativo"));
        tagList.add(new Tag(36L, "Inauguração"));
        tagList.add(new Tag(37L, "Feira de negócios"));
        tagList.add(new Tag(38L, "Evento de caridade"));
        tagList.add(new Tag(39L, "Bazar beneficente"));
        tagList.add(new Tag(40L, "Encontro de carros antigos"));
        tagList.add(new Tag(41L, "Festa infantil"));
        tagList.add(new Tag(42L, "Festival cultural"));
        tagList.add(new Tag(43L, "Encontro de colecionadores"));
        tagList.add(new Tag(44L, "Lançamento de livro"));
        tagList.add(new Tag(45L, "Desfile de moda"));
        tagList.add(new Tag(46L, "Mostra de cinema"));
        tagList.add(new Tag(47L, "Sarau"));
        tagList.add(new Tag(48L, "Feira de artesanato"));
        tagList.add(new Tag(49L, "Evento esportivo"));
        tagList.add(new Tag(50L, "Competição de jogos eletrônicos"));
        tagList.add(new Tag(51L, "Encontro de motociclistas"));
        tagList.add(new Tag(52L, "Feira de tecnologia"));
        tagList.add(new Tag(53L, "Encontro de startups"));
        tagList.add(new Tag(54L, "Festival de cerveja artesanal"));
        tagList.add(new Tag(55L, "Festa de aniversário"));
        tagList.add(new Tag(56L, "Festival gastronômico"));
        tagList.add(new Tag(57L, "Piquenique"));
        tagList.add(new Tag(58L, "Evento de ioga"));
        tagList.add(new Tag(59L, "Corrida de aventura"));
        tagList.add(new Tag(60L, "Feira de adoção de animais"));
        tagList.add(new Tag(61L, "Competição de natação"));
        tagList.add(new Tag(62L, "Encontro de fotografia"));
        tagList.add(new Tag(63L, "Festival de inverno"));
        tagList.add(new Tag(64L, "Feira de automóveis"));
        tagList.add(new Tag(65L, "Torneio de futebol"));
        tagList.add(new Tag(66L, "Feira de quadrinhos"));
        tagList.add(new Tag(67L, "Competição de cosplay"));
        tagList.add(new Tag(68L, "Exposição de flores"));
        tagList.add(new Tag(69L, "Torneio de xadrez"));
        tagList.add(new Tag(70L, "Festival de jazz"));
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}