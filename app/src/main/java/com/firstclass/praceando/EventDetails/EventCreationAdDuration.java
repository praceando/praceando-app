package com.firstclass.praceando.EventDetails;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class EventCreationAdDuration extends AppCompatActivity {
    private TextInputEditText startDate, endDate;
    private TextInputLayout startDateInputLayout, endDateInputLayout;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_creation_ad_duration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.returnArrow).setOnClickListener(v -> finish());
        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, EventCreationPayment.class);
            intent.putExtra("startDate", startDate.getText().toString());
            intent.putExtra("endDate", endDate.getText().toString());
            startActivity(intent);
        });

        endDateInputLayout = findViewById(R.id.endDateInputLayout);
        startDateInputLayout = findViewById(R.id.startDateInputLayout);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);

        Calendar calendar = Calendar.getInstance();

        startDateInputLayout.setEndIconOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this, R.style.CustomDatePicker, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    String formattedDate = String.format("%02d/%02d/%d", day, month + 1, year);
                    startDate.setText(formattedDate);
                    validateFields();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        });

        endDateInputLayout.setEndIconOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this, R.style.CustomDatePicker, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    String formattedDate = String.format("%02d/%02d/%d", day, month + 1, year);
                    endDate.setText(formattedDate);
                    validateFields();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();

            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        });

    }

    private void validateFields() {
        boolean isValid = true;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();

            if (Objects.requireNonNull(startDate.getText()).toString().length() > 1) {
                Date startDateValue = dateFormat.parse(startDate.getText().toString());
                assert startDateValue != null;

                if (startDateValue.before(currentDate)) {
                    startDateInputLayout.setError("Data de início não pode ser no passado");
                    isValid = false;
                } else {
                    startDateInputLayout.setError("");
                }

                if (Objects.requireNonNull(endDate.getText()).toString().length() > 1) {
                    Date endDateValue = dateFormat.parse(endDate.getText().toString());
                    assert endDateValue != null;

                    if (endDateValue.before(startDateValue)) {
                        endDateInputLayout.setError("Data de fim não pode ser antes da data de início");
                        isValid = false;
                    } else {
                        endDateInputLayout.setError("");
                    }
                }
            }

        } catch (ParseException e) {
            isValid = false;
            Log.e("ERRO", Objects.requireNonNull(e.getMessage()));
        }

        boolean allFieldsFilled = startDate.getText().toString().length() > 1 && endDate.getText().toString().length() > 1;

        if (isValid && allFieldsFilled) {
            nextBtn.setEnabled(true);
            nextBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.rosaEscuraoClaro));
        } else {
            nextBtn.setEnabled(false);
            nextBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.rosaEscuraoDesativado));
        }

    }
}