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
import com.firstclass.praceando.entities.Avatar;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PerfilEdit extends AppCompatActivity {
    private List<Avatar> avatarList = new ArrayList<>();
    private FlexboxLayout avatarsFlexbox;
    private ImageView userImage;
    private Globals globals;
    private TextInputLayout nicknameInputLayout, bioInputLayout;
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

        findViewById(R.id.returnArrow).setOnClickListener(v -> finish());

        avatarsFlexbox = findViewById(R.id.avatarFlexblox);
        userImage = findViewById(R.id.userImage);
        bio = findViewById(R.id.bio);
        nickname = findViewById(R.id.nickname);
        nicknameInputLayout = findViewById(R.id.nicknameInputLayout);
        bioInputLayout = findViewById(R.id.bioInputLayout);

        findViewById(R.id.saveBtn).setOnClickListener(v -> {
            Intent intent = new Intent(PerfilEdit.this, MainActivity.class);
            intent.putExtra("openFragment", "perfil");
            startActivity(intent);
            finish();
        });


        globals = (Globals) getApplication();

        nickname.setText(globals.getNickname());
        bio.setText(globals.getBio());

        Picasso.get()
                .load(globals.getUserProfileImage())
                .into(userImage);

        addAvatarsInTheList();
        setupAvatars();

    }

    private void addAvatarsInTheList() {
        avatarList.add(new Avatar(1L, "https://cdn-icons-png.freepik.com/512/13748/13748379.png"));
        avatarList.add(new Avatar(2L, "https://cdn-icons-png.freepik.com/512/13748/13748436.png"));
        avatarList.add(new Avatar(3L, "https://cdn-icons-png.freepik.com/512/13748/13748555.png"));
        avatarList.add(new Avatar(4L, "https://cdn-icons-png.freepik.com/256/13748/13748527.png"));
        avatarList.add(new Avatar(5L, "https://cdn-icons-png.freepik.com/512/13748/13748670.png"));
        avatarList.add(new Avatar(6L, "https://cdn-icons-png.freepik.com/512/13748/13748614.png"));
        avatarList.add(new Avatar(7L, "https://cdn-icons-png.freepik.com/512/13748/13748570.png"));
        avatarList.add(new Avatar(8L, "https://cdn-icons-png.freepik.com/512/13748/13748687.png"));
        avatarList.add(new Avatar(9L, "https://cdn-icons-png.freepik.com/512/13748/13748379.png"));
        avatarList.add(new Avatar(10L, "https://cdn-icons-png.freepik.com/512/13748/13748436.png"));
        avatarList.add(new Avatar(11L, "https://cdn-icons-png.freepik.com/512/13748/13748555.png"));
        avatarList.add(new Avatar(12L, "https://cdn-icons-png.freepik.com/256/13748/13748527.png"));
        avatarList.add(new Avatar(13L, "https://cdn-icons-png.freepik.com/512/13748/13748670.png"));
        avatarList.add(new Avatar(14L, "https://cdn-icons-png.freepik.com/512/13748/13748614.png"));
        avatarList.add(new Avatar(15L, "https://cdn-icons-png.freepik.com/512/13748/13748570.png"));
        avatarList.add(new Avatar(16L, "https://cdn-icons-png.freepik.com/512/13748/13748687.png"));
    }

    private void setupAvatars() {
        float density = getResources().getDisplayMetrics().density;
        int marginInPixels = (int) (10 * density); // Margem de 5dp em pixels

        for (Avatar avatar : avatarList) {
            // Cria um novo ImageView para cada avatar
            ImageView avatarImageView = new ImageView(this);

            int sizeInDp = (int) (80 * density); // 80dp
            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    sizeInDp,
                    sizeInDp
            );
            layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
            avatarImageView.setLayoutParams(layoutParams);

            // Usa Picasso para carregar a imagem pela URL
            Picasso.get()
                    .load(avatar.getUrl())
                    //.placeholder(R.drawable.shape_user_image_background)
                    //.error(R.drawable.error_avatar) // Imagem de erro se a URL falhar
                    .resize(sizeInDp, sizeInDp) // Redimensiona a imagem para o tamanho do ImageView
                    .centerCrop()
                    .into(avatarImageView);

            avatarImageView.setOnClickListener(v -> {

                globals.setUserProfileImage(avatar.getUrl());

                Picasso.get()
                        .load(avatar.getUrl())
                        //.placeholder(R.drawable.shape_user_image_background)
                        .into(userImage);
            });

            // Adiciona o ImageView ao FlexboxLayout
            avatarsFlexbox.addView(avatarImageView);
        }

        nickname.addTextChangedListener(watchFields);
        bio.addTextChangedListener(watchFields);

        validateFields();
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