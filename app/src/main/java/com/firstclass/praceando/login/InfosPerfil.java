package com.firstclass.praceando.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.Globals;
import com.firstclass.praceando.MainActivity;
import com.firstclass.praceando.R;

import java.util.Objects;

public class InfosPerfil extends AppCompatActivity {

    EditText nickname;
    EditText bio;
    Button enterBtn;
    ImageView userImage;
    Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_infos_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.returnArrow).setOnClickListener(v -> finish());

        globals = (Globals) getApplication();

        userImage = findViewById(R.id.userImage);
        userImage = findViewById(R.id.userImage);
        nickname = findViewById(R.id.nickname);
        bio = findViewById(R.id.bio);
        enterBtn = findViewById(R.id.enterBtn);

        nickname.addTextChangedListener(watchFields);
        bio.addTextChangedListener(watchFields);

        findViewById(R.id.enterBtn).setOnClickListener(v -> {
            startActivity(new Intent(this, UserInterest.class));
        });

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

        enterBtn.setEnabled(enable);


        if(enable) {
            globals.setNickname(nickname.getText().toString());
            globals.setBio(bio.getText().toString());
        }

        int color = enable ? R.color.rosaEscurao : R.color.rosaEscuraoDesativado;
        enterBtn.setBackgroundTintList(getResources().getColorStateList(color, getTheme()));
    }

}