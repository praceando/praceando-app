package com.firstclass.praceando;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.UserByIdCallback;
import com.firstclass.praceando.entities.User;
import com.firstclass.praceando.login.LandingScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserAuthentication();
            }
        }, 2000);
    }

    private void checkUserAuthentication() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {

            Globals globals = (Globals) getApplication();
            PostgresqlAPI postgresqlAPI = new PostgresqlAPI();

            postgresqlAPI.getUserByEmail(currentUser.getEmail(), new UserByIdCallback() {
                @Override
                public void onSuccess(User user) {
                    Log.e("API", user+"");
                    globals.setBio(user.getBio());
                    globals.setUserRole(user.getTipoUsuario());
                    globals.setId(user.getId());
                    globals.setNickname(user.getNome());
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("API", errorMessage);
                }
            });

            // Usuário está autenticado, redirecionar para MainActivity
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Usuário não está autenticado, redirecionar para LandingScreenActivity
            Intent intent = new Intent(SplashScreenActivity.this, LandingScreen.class);
            startActivity(intent);
            finish();
        }
    }

}
