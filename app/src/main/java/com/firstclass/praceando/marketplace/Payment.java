package com.firstclass.praceando.marketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstclass.praceando.Globals;
import com.firstclass.praceando.R;
import com.firstclass.praceando.firebase.database.Database;
import com.squareup.picasso.Picasso;

public class Payment extends AppCompatActivity {

    private TextView title, description, price;
    private ImageView image;
    private Button finishBtn;
    private String nmCategoria, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0);
            return insets;
        });

        title = findViewById(R.id.productTitle);
        description = findViewById(R.id.productDescription);
        price = findViewById(R.id.productPrice);
        image = findViewById(R.id.productImage);
        finishBtn = findViewById(R.id.finishBtn);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titleText = extras.getString("title");
            String descriptionText = extras.getString("description");
            double priceValue = extras.getDouble("price");
            imageUrl = extras.getString("image");
            nmCategoria = extras.getString("categoria");
            title.setText(titleText);
            description.setText(descriptionText);
            price.setText("R$:" + priceValue);

            Picasso.get()
                    .load(imageUrl)
                    .into(image);
        }

        finishBtn.setOnClickListener(v -> {
            if (nmCategoria.equals("Avatar")) {
                Database database = new Database();
                Globals globals = (Globals) getApplication();
                database.adicionarAvatar(globals.getId(), imageUrl);
            }
            finish();
        });

    }
}
