package com.firstclass.praceando.marketplace;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.CreateCompraCallback;
import com.firstclass.praceando.API.postgresql.entities.CreateCompra;
import com.firstclass.praceando.API.postgresql.entities.CreateCompraResponse;
import com.firstclass.praceando.Globals;
import com.firstclass.praceando.R;
import com.firstclass.praceando.firebase.database.Database;
import com.squareup.picasso.Picasso;

public class Payment extends AppCompatActivity {

    private TextView title, description, price;
    private ImageView image;
    private Button finishBtn;
    private String nmCategoria, imageUrl, titleText;
    private long productId;
    private double priceValue;
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    private Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, 0);
            return insets;
        });

        globals = (Globals) getApplication();

        title = findViewById(R.id.productTitle);
        description = findViewById(R.id.productDescription);
        price = findViewById(R.id.productPrice);
        image = findViewById(R.id.productImage);
        finishBtn = findViewById(R.id.finishBtn);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            titleText = extras.getString("title");
            String descriptionText = extras.getString("description");
            priceValue = extras.getDouble("price");
            imageUrl = extras.getString("image");
            nmCategoria = extras.getString("categoria");
            productId = extras.getLong("id");
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

            CreateCompra compra = new CreateCompra(globals.getId(), priceValue, productId);
            Log.e("COMPRA", compra+"");
            postgresqlAPI.createCompra(compra, new CreateCompraCallback() {
                @Override
                public void onSuccess(CreateCompraResponse response) {
                    postgresqlAPI.pagamento(response.getIdCompra());
                    Log.e("API", "Compra realizada: "+response);
                    if (titleText.contains("Premium")) globals.setPremium(true);
                    Toast.makeText(Payment.this, "Compra realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("API", "Erro ao realizar a compra: "+errorMessage);
                    Toast.makeText(Payment.this, "Erro ao realizar a compra, por favor, tente novamente!", Toast.LENGTH_SHORT).show();
                }
            });

        });

    }
}
