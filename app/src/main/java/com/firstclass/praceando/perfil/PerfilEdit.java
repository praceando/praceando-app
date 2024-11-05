package com.firstclass.praceando.perfil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.Globals;
import com.firstclass.praceando.MainActivity;
import com.firstclass.praceando.R;
import com.firstclass.praceando.firebase.database.Database;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class PerfilEdit extends AppCompatActivity {
    private FlexboxLayout avatarsFlexbox;
    private ImageView userImage;
    private Globals globals;
    private String avatarSelecionado;
    private TextInputLayout nicknameInputLayout, bioInputLayout;
    private Database database = new Database();
    private TextView bio, nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        globals = (Globals) getApplication();

        findViewById(R.id.returnArrow).setOnClickListener(v -> finish());

        avatarsFlexbox = findViewById(R.id.avatarFlexblox);
        userImage = findViewById(R.id.userImage);
        bio = findViewById(R.id.bio);
        nickname = findViewById(R.id.nickname);
        nicknameInputLayout = findViewById(R.id.nicknameInputLayout);
        bioInputLayout = findViewById(R.id.bioInputLayout);

        database.buscarEAdicionarAvatares(globals.getId(), avatares -> {
            float density = getResources().getDisplayMetrics().density;
            int marginInPixels = (int) (10 * density);
            int sizeInDp = (int) (80 * density);

            for (String avatarUrl : avatares) {
                ImageView avatarImageView = new ImageView(this);
                FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(sizeInDp, sizeInDp);
                layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
                avatarImageView.setLayoutParams(layoutParams);

                Picasso.get()
                        .load(avatarUrl)
                        .fit()
                        .into(avatarImageView);

                avatarImageView.setOnClickListener(v -> {
                    avatarSelecionado = avatarUrl;
                    Picasso.get().load(avatarUrl).fit().into(userImage);
                });

                avatarsFlexbox.addView(avatarImageView);
            }
        });

        nickname.setText(globals.getNickname());
        bio.setText(globals.getBio());

        Picasso.get()
                .load(globals.getUserProfileImage())
                .fit()
                .into(userImage);

        nickname.addTextChangedListener(watchFields);
        bio.addTextChangedListener(watchFields);

        validateFields();

        findViewById(R.id.saveBtn).setOnClickListener(v -> {
            Intent intent = new Intent(PerfilEdit.this, MainActivity.class);
            intent.putExtra("openFragment", "perfil");
            startActivity(intent);
            database.alterarAvatarAtual(globals.getId(), avatarSelecionado);
            globals.setUserProfileImage(avatarSelecionado);
            finish();
        });
    }

    private final TextWatcher watchFields = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            validateFields();
        }
    };

    // Função para validar os campos e ajustar a cor e habilitação do botão
    @SuppressLint("ResourceType")
    private void validateFields() {
        boolean isNicknameFilled = !Objects.requireNonNull(nickname.getText()).toString().isEmpty();
        boolean isBioFilled = !Objects.requireNonNull(bio.getText()).toString().isEmpty();

        boolean isBioLengthValid = bio.getText().length() <= 230;

        boolean enable = isNicknameFilled && isBioFilled && isBioLengthValid;

//        enterBtn.setEnabled(enable);

        globals.setNickname(nickname.getText().toString());
        globals.setBio(bio.getText().toString());

        int color = enable ? R.color.rosaEscurao : R.color.rosaEscuraoDesativado;
//        enterBtn.setBackgroundTintList(getResources().getColorStateList(color, getTheme()));
    }
}