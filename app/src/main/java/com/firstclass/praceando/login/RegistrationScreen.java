package com.firstclass.praceando.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.EmailExistsCallback;
import com.firstclass.praceando.API.postgresql.entities.EmailIsInUse;
import com.firstclass.praceando.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class RegistrationScreen extends AppCompatActivity {
    private TextInputEditText cnpjEditText;
    private TextInputLayout cnpjInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout passwordRepeatInputLayout;
    private TextInputEditText passwordEditText;
    private TextInputEditText passwordRepeatEditText;
    private TextInputEditText emailEditText;
    private TextInputLayout emailInputLayout;
    private CheckBox termsCheckbox;
    private Button nextBtn;
    private boolean isAdvertiser;
    private LinearLayout cnpjLayout;
    private boolean enable;
    private String gender;
    private String birthDate;
    private String name;
    private String userRole;
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();

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

        gender = getIntent().getStringExtra("gender");
        birthDate = getIntent().getStringExtra("birthDate");
        name = getIntent().getStringExtra("name");
        userRole = getIntent().getStringExtra("type");

        isAdvertiser = Objects.equals(getIntent().getStringExtra("type"), "advertiser");
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
                    validateFields();
                } else {
                    emailInputLayout.setError(null);
                    emailEditText.setTextColor(getResources().getColor(R.color.black));
                    isEmailAlreadyInUse();
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

            Intent intent = new Intent(RegistrationScreen.this, InfosPerfil.class);
            intent.putExtra("gender",gender);
            intent.putExtra("birthDate", birthDate);
            intent.putExtra("name", name);
            intent.putExtra("type", userRole);
            intent.putExtra("email", Objects.requireNonNull(emailEditText.getText()).toString());
            intent.putExtra("password", Objects.requireNonNull(passwordEditText.getText()).toString());
            if (isAdvertiser) {
                intent.putExtra("cnpj", Objects.requireNonNull(cnpjEditText.getText()).toString());
            }
            startActivity(intent);
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

        boolean isEmailAvailable = emailInputLayout.getError() == null;

        if(isAdvertiser){
            enable = isEmailValid && isEmailAvailable && isPasswordValid && doPasswordsMatch && isCNPJValid && isTermsChecked;
        } else {
            enable = isEmailValid && isEmailAvailable && isPasswordValid && doPasswordsMatch && isTermsChecked;
        }

        nextBtn.setEnabled(enable);
        int color = enable ? getResources().getColor(R.color.rosaEscuraoClaro, getTheme()) : getResources().getColor(R.color.rosaEscuraoDesativado, getTheme());
        nextBtn.setBackgroundColor(color);
    }

    private void isEmailAlreadyInUse() {
        postgresqlAPI.emailExists(emailEditText.getText().toString(), new EmailExistsCallback() {
            @Override
            public void onSuccess(EmailIsInUse message) {
                boolean emailEmUso = message.getEmailEmUso();
                if (emailEmUso) {
                    emailInputLayout.setError("E-mail já em uso");
                    emailEditText.setTextColor(getResources().getColor(R.color.red));
                } else {
                    emailInputLayout.setError(null);
                    emailEditText.setTextColor(getResources().getColor(R.color.black));
                }
                validateFields();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API", errorMessage);
            }
        });
    }
}