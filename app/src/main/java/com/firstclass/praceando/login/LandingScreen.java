package com.firstclass.praceando.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.MainActivity;
import com.firstclass.praceando.R;
import com.firstclass.praceando.firebase.authentication.Authentication;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LandingScreen extends AppCompatActivity {
    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;
    Button enterBtn;
    boolean enable;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        enterBtn = findViewById(R.id.enterBtn);
        errorMessage = findViewById(R.id.errorMessage);
        emailEditText.addTextChangedListener(watchFields);
        passwordEditText.addTextChangedListener(watchFields);

        findViewById(R.id.singInBtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, WelcomeScreen.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.enterBtn).setOnClickListener(v -> {
            Authentication authManager = new Authentication();
            authManager.signIn(Objects.requireNonNull(emailEditText.getText()).toString(), Objects.requireNonNull(passwordEditText.getText()).toString(), new Authentication.AuthCallback() {
                @Override
                public void onSuccess() {
                    Intent intent = new Intent(LandingScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Exception exception) {
                    // O login falhou
                    errorMessage.setText("Usuário inválido!");
                }
            });

        });

        validateFields();
    }

    // TextWatcher para verificar mudanças nos campos de email e senha
    private final TextWatcher watchFields = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            validateFields(); // Validar os campos sempre que houver mudanças
        }
    };

    // Função para validar os campos e ajustar a cor e habilitação do botão
    @SuppressLint("ResourceType")
    private void validateFields() {
        boolean isEmailFilled = !Objects.requireNonNull(emailEditText.getText()).toString().isEmpty();
        boolean isPasswordFilled = !Objects.requireNonNull(passwordEditText.getText()).toString().isEmpty();

        enable = isEmailFilled && isPasswordFilled;

        enterBtn.setEnabled(enable);

        if (!enable) errorMessage.setText("");

        int color = enable ? R.color.rosaEscurao : R.color.rosaEscuraoDesativado;
        enterBtn.setBackgroundTintList(getResources().getColorStateList(color, getTheme()));

    }
}
