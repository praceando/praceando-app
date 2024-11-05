package com.firstclass.praceando.login;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.GendersCallback;
import com.firstclass.praceando.EventDetails.GenderItemAdapter;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Gender;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import android.text.TextUtils;
import android.widget.TextView;

public class GenderAndBirth extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteTextView;
    private Gender selectedGender;
    private List<Gender> genderList = new ArrayList<>();
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    private TextInputLayout birthDateInputLayout;
    private TextInputEditText birthDate;
    private Button nextBtn;
    private String userRole;
    private TextView dropdownError;
    private TextInputLayout nameInputLayout;
    private TextInputEditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gender_and_birth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.returnArrow).setOnClickListener(v -> finish());
        userRole = getIntent().getStringExtra("type");
        birthDate = findViewById(R.id.birthDate);
        birthDateInputLayout = findViewById(R.id.birthDateInputLayout);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        nextBtn = findViewById(R.id.nextBtn);
        dropdownError = findViewById(R.id.dropdownError);
        nameInputLayout = findViewById(R.id.nameInputLayout);
        name = findViewById(R.id.name);

        autoCompleteTextView.setOnItemClickListener((parent, v, position, id) -> {
            selectedGender = genderList.get(position);
            validateFields();
        });

        addGenderInTheList();

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateFields();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        Calendar calendar = Calendar.getInstance();
        birthDateInputLayout.setEndIconOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this, R.style.CustomDatePicker, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    birthDate.setText(day + "/" + (month + 1) + "/" + year);
                    validateFields();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        });

        nextBtn.setOnClickListener(v -> {
            if (validateFields()) {
                Intent intent = new Intent(this, RegistrationScreen.class);
                intent.putExtra("gender", String.valueOf(selectedGender.getId()));
                intent.putExtra("birthDate", Objects.requireNonNull(birthDate.getText()).toString());
                intent.putExtra("name", Objects.requireNonNull(name.getText()).toString());
                intent.putExtra("type", userRole);

                startActivity(intent);
            }
        });
    }

    // Validação dos campos de gênero e data de nascimento
    private boolean validateFields() {
        boolean isValid = true;

        if (selectedGender == null) {
            dropdownError.setText("Selecione um gênero");
            isValid = false;
        } else {
            dropdownError.setText(null);
        }

        String birthDateString = birthDate.getText().toString();
        if (TextUtils.isEmpty(birthDateString)) {
            birthDateInputLayout.setError("Data de nascimento é obrigatória");
            isValid = false;
        } else {
            if (!isValidBirthDate(birthDateString)) {
                birthDateInputLayout.setError("Idade não permitida.");
                isValid = false;
            } else {
                birthDateInputLayout.setError(null);
            }
        }

        String nameString = Objects.requireNonNull(name.getText()).toString().trim();
        if (TextUtils.isEmpty(nameString)) {
            nameInputLayout.setError("Nome é obrigatório");
            isValid = false;
        } else {
            if (nameString.length() < 3) {
                nameInputLayout.setError("Nome deve ter pelo menos 3 caracteres");
                isValid = false;
            } else {
                nameInputLayout.setError(null);
            }
        }

        updateNextButtonState(isValid);
        return isValid;
    }

    private boolean isValidBirthDate(String birthDateString) {
        try {
            String[] dateParts = birthDateString.split("/");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1;
            int year = Integer.parseInt(dateParts[2]);

            Calendar birthDateCalendar = Calendar.getInstance();
            birthDateCalendar.set(year, month, day);

            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < birthDateCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--; // Ajusta se ainda não passou o aniversário este ano
            }

            if (userRole.equals("advertiser") && age < 18) {
                return false;
            } else if (age < 12) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void updateNextButtonState(boolean isValid) {
        if (isValid) {
            nextBtn.setEnabled(true);
            nextBtn.setBackgroundTintList(getResources().getColorStateList(R.color.rosaEscurao));
        } else {
            nextBtn.setEnabled(false);
            nextBtn.setBackgroundTintList(getResources().getColorStateList(R.color.rosaEscuraoDesativado));
        }
    }

    private void addGenderInTheList() {
        postgresqlAPI.getGenders(new GendersCallback() {
            @Override
            public void onSuccess(List<Gender> genders) {
                genderList.addAll(genders);
                setupAutoCompleteTextView();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("GENDER", errorMessage);
            }
        });
    }

    private void setupAutoCompleteTextView() {
        GenderItemAdapter adapter = new GenderItemAdapter(this, genderList);
        autoCompleteTextView.setAdapter(adapter);
    }
}
