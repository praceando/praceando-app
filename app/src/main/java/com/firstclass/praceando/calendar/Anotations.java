package com.firstclass.praceando.calendar;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.Globals;
import com.firstclass.praceando.R;
import com.firstclass.praceando.firebase.database.Database;
import com.firstclass.praceando.firebase.database.callback.AvatarCallback;

public class Anotations extends AppCompatActivity {
    private EditText annotations;
    private ImageView saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anotations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Globals globals = (Globals) getApplication();
        Database database = new Database();

        annotations = findViewById(R.id.annotations);
        saveBtn = findViewById(R.id.saveBtn);

        database.buscarAnotacao(globals.getId(), new AvatarCallback() {
            @Override
            public void onAvatarRetrieved(String anotacao) {
                annotations.setText(anotacao);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        saveBtn.setOnClickListener(v -> {
            database.alterarAnotacao(globals.getId(), annotations.getText().toString());
            finish();
        });

    }
}