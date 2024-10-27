package com.firstclass.praceando.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.NicknameExistsCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.UsuarioAnuncianteCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.UsuarioConsumidorCallback;
import com.firstclass.praceando.API.postgresql.entities.NicknameIsInUse;
import com.firstclass.praceando.API.postgresql.entities.UsuarioAnunciante;
import com.firstclass.praceando.API.postgresql.entities.UsuarioConsumidor;
import com.firstclass.praceando.Globals;
import com.firstclass.praceando.MainActivity;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Gender;
import com.firstclass.praceando.firebase.authentication.Authentication;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class InfosPerfil extends AppCompatActivity {

    private EditText nickname;
    private EditText bio;
    private Button enterBtn;
    private ImageView userImage;
    private Globals globals;
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    private TextInputLayout nicknameInputLayout, bioInputLayout;
    private boolean enable;
    private String gender;
    private String birthDate;
    private String name;
    private String userRole;
    private boolean isAdvertiser;
    private String email;
    private String password;
    private String cnpj;

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

        gender = getIntent().getStringExtra("gender");
        birthDate = getIntent().getStringExtra("birthDate");
        name = getIntent().getStringExtra("name");
        userRole = getIntent().getStringExtra("type");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        birthDate = getIntent().getStringExtra("birthDate");

        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            birthDate = targetFormat.format(originalFormat.parse(birthDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        isAdvertiser = Objects.equals(String.valueOf(userRole), "advertiser");
        if (isAdvertiser) {
            cnpj = getIntent().getStringExtra("cnpj");
            assert cnpj != null;
            cnpj = cnpj.replaceAll("[^\\d]", ""); // mantém apenas números

        }

        nicknameInputLayout = findViewById(R.id.nicknameInputLayout);
        bioInputLayout = findViewById(R.id.bioInputLayout);
        nickname = findViewById(R.id.nickname);
        bio = findViewById(R.id.bio);
        enterBtn = findViewById(R.id.enterBtn);

        nickname.addTextChangedListener(watchFields);
        bio.addTextChangedListener(watchFields);

        findViewById(R.id.enterBtn).setOnClickListener(v -> {

            if (isAdvertiser) {
                UsuarioAnunciante usuarioAnunciante = new UsuarioAnunciante(1, new Gender(Integer.parseInt(gender)), name, email, password, birthDate, nickname.getText().toString(), bio.getText().toString(), cnpj);
                Log.e("USUARIO FINAL", usuarioAnunciante+"");
                postgresqlAPI.createUsuarioAnunciante(usuarioAnunciante, new UsuarioAnuncianteCallback() {
                    @Override
                    public void onSuccess(UsuarioAnunciante usuarioAnunciante1) {
                        Log.e("API", usuarioAnunciante1+"");
                        Toast.makeText(InfosPerfil.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();

                        Authentication authManager = new Authentication();
                        authManager.signUp(email, password, new Authentication.AuthCallback() {
                            @Override
                            public void onSuccess() {
                                Log.d("AUTHENTICATION", "usuario registrado");
                                globals.setNickname(usuarioAnunciante1.getNmNickname());
                                globals.setBio(usuarioAnunciante1.getDsUsuario());
                                globals.setId(usuarioAnunciante1.getId());
                                globals.setUserRole(1);
                                globals.setId(usuarioAnunciante1.getId());
                                startActivity(new Intent(InfosPerfil.this, UserInterest.class));
                            }

                            @Override
                            public void onFailure(Exception exception) {
                                Log.e("AUTHENTICATION", exception.getMessage());
                            }

                        });
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("API", errorMessage);
                        Toast.makeText(InfosPerfil.this, "Erro ao criar usuário", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                UsuarioConsumidor usuarioConsumidor = new UsuarioConsumidor(1, new Gender(Integer.parseInt(gender)), name, email, password, birthDate, nickname.getText().toString(), bio.getText().toString());
                Log.e("USUARIO FINAL", usuarioConsumidor+"\ngender: "+gender);
                postgresqlAPI.createUsuarioConsumidor(usuarioConsumidor, new UsuarioConsumidorCallback() {
                    @Override
                    public void onSuccess(UsuarioConsumidor usuarioConsumidor) {
                        Log.e("API", usuarioConsumidor+"");
                        Toast.makeText(InfosPerfil.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();

                        Authentication authManager = new Authentication();
                        authManager.signUp(email, password, new Authentication.AuthCallback() {
                            @Override
                            public void onSuccess() {
                                Log.d("AUTHENTICATION", "usuario registrado");
                                globals.setNickname(usuarioConsumidor.getNmNickname());
                                globals.setBio(usuarioConsumidor.getDsUsuario());
                                globals.setId(usuarioConsumidor.getId());
                                globals.setUserRole(1);
                                globals.setId(usuarioConsumidor.getId());
                                startActivity(new Intent(InfosPerfil.this, UserInterest.class));
                            }

                            @Override
                            public void onFailure(Exception exception) {
                                Log.e("AUTHENTICATION", exception.getMessage());
                            }

                        });
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("API", errorMessage);
                        Toast.makeText(InfosPerfil.this, "Erro ao criar usuário", Toast.LENGTH_SHORT).show();
                    }
                });


            }
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
        String nicknameText = Objects.requireNonNull(nickname.getText()).toString().trim();
        String bioText = Objects.requireNonNull(bio.getText()).toString().trim();

        boolean isNicknameValid = validateNickname(nicknameText);
        boolean isBioValid = validateBio(bioText);

        enable = isNicknameValid && isBioValid;


        if (isNicknameValid) {
            postgresqlAPI.nicknameExists(nicknameText, new NicknameExistsCallback() {
                @Override
                public void onSuccess(NicknameIsInUse nicknameIsInUse) {
                    if (nicknameIsInUse.getNicknameEmUso()) {
                        nicknameInputLayout.setError("Esse nickname já está em uso.");
                        enable = false;
                    } else {
                        nicknameInputLayout.setError(null);
                    }

                    enterBtn.setEnabled(enable);
                    int color = enable ? R.color.rosaEscurao : R.color.rosaEscuraoDesativado;
                    enterBtn.setBackgroundTintList(getResources().getColorStateList(color, getTheme()));
                }

                @Override
                public void onError(String errorMessage) {
                    nicknameInputLayout.setError(errorMessage);
                }
            });
        }
    }

    // Validação do Nickname
    private boolean validateNickname(String nicknameText) {
        if (nicknameText.length() < 3 || nicknameText.length() > 20) {
            nicknameInputLayout.setError("O nickname deve ter entre 3 e 20 caracteres.");
            return false;
        }
        nicknameInputLayout.setError(null);
        return true;
    }

    // Validação da Bio
    private boolean validateBio(String bioText) {
        if (bioText.length() > 230) {
            bioInputLayout.setError("A bio deve ter no máximo 230 caracteres.");
            return false;
        }
        if (bioText.contains("\n\n")) {
            bioInputLayout.setError("Não pode haver mais de uma quebra de linha seguida.");
            return false;
        }
        bioInputLayout.setError(null);
        return true;
    }
}