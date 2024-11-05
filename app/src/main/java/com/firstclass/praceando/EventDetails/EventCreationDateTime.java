package com.firstclass.praceando.EventDetails;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.LocalesCallback;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Locale;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EventCreationDateTime extends AppCompatActivity {

    private List<Locale> localeList = new ArrayList<>();
    private Button nextBtn;
    private TextInputEditText startDate, endDate, startTime, endTime;
    private TextInputLayout startDateInputLayout, endDateInputLayout, startTimeInputLayout, endTimeInputLayout, dropdownLayout;
    private AutoCompleteTextView autoCompleteTextView;
    private Locale selectedLocale;
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    private String title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_creation_date_time);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 60, systemBars.right, systemBars.bottom);
            return insets;
        });

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        ArrayList<Uri> imagesUri = getIntent().getParcelableArrayListExtra("imagesUri");


        findViewById(R.id.returnArrow).setOnClickListener(v -> finish());

        endTimeInputLayout = findViewById(R.id.endTimeInputLayout);
        startTimeInputLayout = findViewById(R.id.startTimeInputLayout);
        endDateInputLayout = findViewById(R.id.endDateInputLayout);
        startDateInputLayout = findViewById(R.id.startDateInputLayout);
        dropdownLayout = findViewById(R.id.dropdownLayout);

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setEnabled(false);
        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, EventCreationTagsSelection.class);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            intent.putParcelableArrayListExtra("imagesUri", imagesUri);
            intent.putExtra("localeId", String.valueOf(selectedLocale.getId()));
            intent.putExtra("startDate", Objects.requireNonNull(startDate.getText()).toString());
            intent.putExtra("endDate", Objects.requireNonNull(endDate.getText()).toString());
            intent.putExtra("startTime", Objects.requireNonNull(startTime.getText()).toString());
            intent.putExtra("endTime", Objects.requireNonNull(endTime.getText()).toString());
            startActivity(intent);
        });

        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        addLocalesInTheList();

        setupDateAndTimePickers();

        autoCompleteTextView.setOnItemClickListener((parent, v, position, id) -> {
            selectedLocale = localeList.get(position);
            validateFields();
        });

        startDate.addTextChangedListener(new SimpleTextWatcher(this::validateFields));
        endDate.addTextChangedListener(new SimpleTextWatcher(this::validateFields));
        startTime.addTextChangedListener(new SimpleTextWatcher(this::validateFields));
        endTime.addTextChangedListener(new SimpleTextWatcher(this::validateFields));
    }

    private void setupDateAndTimePickers() {
        Calendar calendar = Calendar.getInstance();

        startDateInputLayout.setEndIconOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this, R.style.CustomDatePicker, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    startDate.setText(day +"/"+ (month + 1) +"/"+year);
                    validateFields();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
            // Acessar os botões "OK" e "Cancelar" e aplicar o estilo
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        });

        endDateInputLayout.setEndIconOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this, R.style.CustomDatePicker, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    endDate.setText(day +"/"+ (month + 1) +"/"+year);
                    validateFields();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
            // Acessar os botões "OK" e "Cancelar" e aplicar o estilo
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        });

        startTimeInputLayout.setEndIconOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(this,R.style.CustomTimePicker, new TimePickerDialog.OnTimeSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                    String formattedTime = String.format("%02d:%02d", hours, minutes);
                    startTime.setText(formattedTime);
                    validateFields();
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), 0, true);

            dialog.show();

            // Acessar os botões "OK" e "Cancelar" e aplicar o estilo
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        });


        // Configura TimePicker para o horário de fim
        endTimeInputLayout.setEndIconOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(this,R.style.CustomTimePicker, new TimePickerDialog.OnTimeSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                    String formattedTime = String.format("%02d:%02d", hours, minutes);
                    endTime.setText(formattedTime);
                    validateFields();
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), 0, true);

            dialog.show();
            // Acessar os botões "OK" e "Cancelar" e aplicar o estilo
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        });
    }

    private void validateFields() {
        boolean isValid = true;  // Começa assumindo que todos os campos são válidos

        // Validação do dropdown (Local selecionado)
        if (selectedLocale == null) {
            dropdownLayout.setError("Selecione um local");
            isValid = false;  // Se inválido, isValid é false
        } else {
            dropdownLayout.setError("");
        }

        // Validação das datas
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

        // Validação dos horários
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            if (Objects.requireNonNull(startTime.getText()).toString().length() > 1 && selectedLocale != null) {
                Date openingTime = timeFormat.parse(selectedLocale.getOpeningTime());
                Date closingTime = timeFormat.parse(selectedLocale.getClosingTime());

                Date startTimeValue = timeFormat.parse(Objects.requireNonNull(startTime.getText()).toString().replace("h", ""));
                assert startTimeValue != null;

                if (startTimeValue.before(openingTime) || startTimeValue.after(closingTime)) {
                    startTimeInputLayout.setError("Horário fora de funcionamento do local");
                    isValid = false;
                } else {
                    startTimeInputLayout.setError("");
                }

                if (Objects.requireNonNull(endTime.getText()).toString().length() > 1) {
                    Date endTimeValue = timeFormat.parse(Objects.requireNonNull(endTime.getText()).toString().replace("h", ""));

                    if (endTimeValue.after(closingTime)) {
                        endTimeInputLayout.setError("Horário fora do funcionamento do local");
                        isValid = false;
                    } else if (!endTimeValue.after(startTimeValue)) {
                        endTimeInputLayout.setError("Horário de encerramento deve ser após o de início");
                        isValid = false;
                    } else {
                        endTimeInputLayout.setError("");
                    }
                }
            }

        } catch (ParseException e) {
            Log.e("ERRO", Objects.requireNonNull(e.getMessage()));
            isValid = false;
        }

        boolean allFieldsFilled = endTime.getText().toString().length() > 1 &&
                startTime.getText().toString().length() > 1 &&
                selectedLocale != null &&
                startDate.getText().toString().length() > 1 &&
                endDate.getText().toString().length() > 1;

        if (isValid && allFieldsFilled) {
            nextBtn.setEnabled(true);
            nextBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.rosaEscuraoClaro));
        } else {
            nextBtn.setEnabled(false);
            nextBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.rosaEscuraoDesativado));
        }

    }


    private void setupAutoCompleteTextView() {
        LocaleItemAdapter adapter = new LocaleItemAdapter(this, localeList);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void addLocalesInTheList() {
        postgresqlAPI.getLocales(new LocalesCallback() {
            @Override
            public void onSuccess(List<Locale> locales) {
                Log.e("LOCAL", ""+locales);
                localeList.addAll(locales);
                setupAutoCompleteTextView();

            }

            @Override
            public void onError(String errorMessage) {
                Log.e("LOCAL", errorMessage);
            }
        });

    }
}



class SimpleTextWatcher implements TextWatcher {

    private Runnable callback;

    public SimpleTextWatcher(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        callback.run();
    }
}