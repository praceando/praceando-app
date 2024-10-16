package com.firstclass.praceando.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.Globals;
import com.firstclass.praceando.MainActivity;
import com.firstclass.praceando.R;
import com.firstclass.praceando.authentication.Authentication;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class RegistrationScreen extends AppCompatActivity {
    TextInputEditText cnpjEditText;
    TextInputLayout cnpjInputLayout;
    TextInputLayout passwordInputLayout;
    TextInputLayout passwordRepeatInputLayout;
    TextInputEditText passwordEditText;
    TextInputEditText passwordRepeatEditText;
    TextInputEditText emailEditText;
    TextInputLayout emailInputLayout;
    CheckBox termsCheckbox;
    Button nextBtn;
    boolean isAdvertiser;
    LinearLayout cnpjLayout;
    Globals globals;
    boolean enable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        isAdvertiser = getIntent().getStringExtra("type").equals("advertiser");
        globals = (Globals) getApplication();
        cnpjLayout = findViewById(R.id.cnpjBox);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        cnpjEditText = findViewById(R.id.cnpj);
        cnpjInputLayout = findViewById(R.id.cnpjInputLayout);
        nextBtn = findViewById(R.id.nextBtn);
        passwordEditText = findViewById(R.id.password);
        passwordRepeatEditText = findViewById(R.id.passwordRepeat);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        passwordRepeatInputLayout = findViewById(R.id.passwordRepeatInputLayout);
        emailEditText = findViewById(R.id.email);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        findViewById(R.id.returnArrow).setOnClickListener(v -> {
            finish();
        });

        cnpjEditText.addTextChangedListener(watchFields);
        emailEditText.addTextChangedListener(watchFields);
        passwordEditText.addTextChangedListener(watchFields);
        passwordRepeatEditText.addTextChangedListener(watchFields);
        termsCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> validateFields());

        if(isAdvertiser){
            cnpjEditText.addTextChangedListener(new TextWatcher() {
                private boolean isUpdating = false;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (isUpdating) {
                        isUpdating = false;
                        return;
                    }

                    String str = unmask(s.toString());
                    StringBuilder masked = new StringBuilder();

                    int i = 0;
                    String mask = "##.###.###/####-##";
                    for (char m : mask.toCharArray()) {
                        if (m != '#' && str.length() > i) {
                            masked.append(m);
                            continue;
                        }
                        try {
                            masked.append(str.charAt(i));
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }

                    isUpdating = true;
                    cnpjEditText.setText(masked.toString());
                    cnpjEditText.setSelection(masked.length());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Validação do CNPJ
                    if (!isCNPJValid(s.toString())) {
                        cnpjInputLayout.setError("CNPJ inválido");
                        cnpjEditText.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        cnpjInputLayout.setError(null);
                        cnpjEditText.setTextColor(getResources().getColor(R.color.black));
                    }
                }

                private String unmask(String s) {
                    return s.replaceAll("\\D", "");
                }

            });
        } else {
            cnpjLayout.setVisibility(View.GONE);
        }

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Validação do Email
                if (!isValidEmail(s.toString())) {
                    emailInputLayout.setError("E-mail inválido");
                    emailEditText.setTextColor(getResources().getColor(R.color.red));
                } else {
                    emailInputLayout.setError(null);
                    emailEditText.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        TextWatcher passwordTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validatePassword();
                validatePasswordsMatch();
            }
        };

        passwordEditText.addTextChangedListener(passwordTextWatcher);
        passwordRepeatEditText.addTextChangedListener(passwordTextWatcher);

        validateFields();

        nextBtn.setOnClickListener(v -> {

            Authentication authManager = new Authentication();
            authManager.signUp(Objects.requireNonNull(emailEditText.getText()).toString(), Objects.requireNonNull(passwordEditText.getText()).toString(), new Authentication.AuthCallback() {
                @Override
                public void onSuccess() {
                    Intent intent = new Intent(RegistrationScreen.this, InfosPerfil.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Exception exception) {
                    Toast.makeText(globals, ""+exception, Toast.LENGTH_SHORT).show();
//                    errorMessage.setText("Usuário inválido!");
                }
            });

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

    private void validatePassword() {
        String password = Objects.requireNonNull(passwordEditText.getText()).toString();

        if (!isValidPassword(password)) {
            passwordInputLayout.setError("A senha deve ter pelo menos 8 caracteres, incluindo números e um caractere especial.");
            passwordEditText.setTextColor(getResources().getColor(R.color.red));
        } else {
            passwordInputLayout.setError(null);
            passwordEditText.setTextColor(getResources().getColor(R.color.black));
        }
    }

    private void validatePasswordsMatch() {
        String password = Objects.requireNonNull(passwordEditText.getText()).toString();
        String repeatPassword = Objects.requireNonNull(passwordRepeatEditText.getText()).toString();

        if (!password.equals(repeatPassword)) {
            passwordRepeatInputLayout.setError("As senhas não coincidem.");
            passwordRepeatEditText.setTextColor(getResources().getColor(R.color.red));
        } else {
            passwordRepeatInputLayout.setError(null);
            passwordRepeatEditText.setTextColor(getResources().getColor(R.color.black));
        }
    }

    private boolean isValidPassword(String password) {
        // mínimo 8 caracteres, um número e um caractere especial
        String passwordPattern = "^(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).{8,}$";
        return password.matches(passwordPattern);
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isCNPJValid(String cnpj) {
        return cnpj.length() == 18;
    }

    @SuppressLint("ResourceAsColor")
    private void validateFields() {
        boolean isEmailValid = isValidEmail(Objects.requireNonNull(emailEditText.getText()).toString());
        boolean isPasswordValid = isValidPassword(Objects.requireNonNull(passwordEditText.getText()).toString());
        boolean doPasswordsMatch = passwordEditText.getText().toString().equals(Objects.requireNonNull(passwordRepeatEditText.getText()).toString());
        boolean isCNPJValid = isCNPJValid(Objects.requireNonNull(cnpjEditText.getText()).toString());
        boolean isTermsChecked = termsCheckbox.isChecked();

        if(isAdvertiser){
            enable = isEmailValid && isPasswordValid && doPasswordsMatch && isCNPJValid && isTermsChecked;
        } else {
            enable = isEmailValid && isPasswordValid && doPasswordsMatch && isTermsChecked;
        }

        globals.setUserRole(isAdvertiser ? 0 : 1);

        nextBtn.setEnabled(enable);
        int color = enable ? getResources().getColor(R.color.rosaEscuraoClaro, getTheme()) : getResources().getColor(R.color.rosaEscuraoDesativado, getTheme());
        nextBtn.setBackgroundColor(color);
    }
}