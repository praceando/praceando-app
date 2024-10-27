package com.firstclass.praceando.EventDetails;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.R;
import com.firstclass.praceando.firebase.database.Database;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventCreationBasicDatas extends AppCompatActivity {
    ImageView img1, img2, img3, selectedImageView;
    ImageView x1, x2, x3;
    private EditText titleEditText, descriptionEditText;
    private TextView titleErrorMessage, descriptionErrorMessage;
    private Button nextButton;
    List<Uri> imagesUri = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_creation_basic_datas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Database database = new Database();

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        x1 = findViewById(R.id.x1);
        x2 = findViewById(R.id.x2);
        x3 = findViewById(R.id.x3);

        findViewById(R.id.nextBtn).setOnClickListener(v -> {
            startActivity(new Intent(this, EventCreationDateTime.class));
        });

        findViewById(R.id.returnArrow).setOnClickListener(v -> finish());

        img1.setOnClickListener(v -> {
            if(x1.getVisibility() == View.VISIBLE){
                return;
            }
            openGalleryForImage(img1);
        });
        img2.setOnClickListener(v -> {
            if(x2.getVisibility() == View.VISIBLE){
                return;
            }
            openGalleryForImage(img2);
        });
        img3.setOnClickListener(v -> {
            if(x3.getVisibility() == View.VISIBLE){
                return;
            }
            openGalleryForImage(img3);
        });

        x1.setOnClickListener(v -> clearImage(img1, x1));
        x2.setOnClickListener(v -> clearImage(img2, x2));
        x3.setOnClickListener(v -> clearImage(img3, x3));

        titleEditText = findViewById(R.id.title);
        descriptionEditText = findViewById(R.id.description);
        titleErrorMessage = findViewById(R.id.titleErrorMessage);
        descriptionErrorMessage = findViewById(R.id.descriptionErrorMessage);
        nextButton = findViewById(R.id.nextBtn);

        titleEditText.addTextChangedListener(textWatcher);
        descriptionEditText.addTextChangedListener(textWatcher);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            validateFields();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    // Validação dos campos
    private void validateFields() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        boolean isTitleValid = title.length() >= 3;
        boolean isDescriptionValid = description.length() >= 3;

        if (!isTitleValid) {
            titleErrorMessage.setText("O título deve ter no mínimo 3 caracteres.");
        } else {
            titleErrorMessage.setText("");
        }

        if (!isDescriptionValid) {
            descriptionErrorMessage.setText("A descrição deve ter no mínimo 3 caracteres.");
        } else {
            descriptionErrorMessage.setText("");
        }

        if (isTitleValid && isDescriptionValid && !title.isEmpty() && !description.isEmpty()) {
            nextButton.setEnabled(true);
            nextButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.rosaEscuraoClaro));
        } else {
            nextButton.setEnabled(false);
            nextButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.rosaEscuraoDesativado));
        }
    }

    private void openGalleryForImage(ImageView imageView) {
        selectedImageView = imageView;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null && result.getData().getData() != null) {
                    Uri imageUri = result.getData().getData();
                    imagesUri.add(imageUri);

                    updateImageView(selectedImageView, imageUri);
                }
            }
    );

    private void updateImageView(ImageView imageView, Uri imageUri) {
        imageView.setPadding(0, 0, 0, 0);

        imageView.setImageURI(imageUri);
        Database database = new Database();
        database.teste(imageView);
//        Picasso.get().load(imageUri).into(imageView, new com.squareup.picasso.Callback() {
//            @Override
//            public void onSuccess() {
//                // A imagem foi carregada com sucesso, agora podemos acessar o drawable
//                Toast.makeText(EventCreationBasicDatas.this, "" + imageView.getDrawable(), Toast.LENGTH_SHORT).show();
//
//                // Chame a função do banco de dados aqui
//
//            }
//
//            @Override
//            public void onError(Exception e) {
//                // Trate o erro de carregamento aqui
//                Toast.makeText(EventCreationBasicDatas.this, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
//            }
//        });


        if (imageView == img1) {
            x1.setVisibility(View.VISIBLE);
        } else if (imageView == img2) {
            x2.setVisibility(View.VISIBLE);
        } else if (imageView == img3) {
            x3.setVisibility(View.VISIBLE);
        }


    }

    private void clearImage(ImageView imageView, ImageView closeButton) {
        imageView.setImageResource(R.drawable.ic_plus);
        imageView.setPadding(143, 143, 143, 143);
        closeButton.setVisibility(View.GONE);
    }
}
