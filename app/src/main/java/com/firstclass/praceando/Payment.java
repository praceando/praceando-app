package com.firstclass.praceando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Payment extends AppCompatActivity {

    TextView title, description, price;
    ImageView image;
    Button finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

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
            String imageUrl = extras.getString("image");

            title.setText(titleText);
            description.setText(descriptionText);
            price.setText("R$:" + priceValue);

            Picasso.get()
                    .load(imageUrl)
                    .into(image);
        }

        finishBtn.setOnClickListener(v -> finish());

    }
}
