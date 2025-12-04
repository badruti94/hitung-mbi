package com.example.hitungmbi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class kalori_input extends AppCompatActivity {

    private TextInputEditText editTextAge;
    private TextInputEditText editTextHeightKalori;
    private TextInputEditText editTextWeightKalori;
    private RadioGroup radioGroupGender;
    private RadioButton radioMale, radioFemale;
    private Spinner spinnerActivityLevel;
    private Button buttonHitungKalori;

    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kalori_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextAge           = findViewById(R.id.editTextAge);
        editTextHeightKalori  = findViewById(R.id.editTextHeightKalori);
        editTextWeightKalori  = findViewById(R.id.editTextWeightKalori);
        radioGroupGender      = findViewById(R.id.radioGroupGender);
        radioMale             = findViewById(R.id.radioMale);
        radioFemale           = findViewById(R.id.radioFemale);
        spinnerActivityLevel  = findViewById(R.id.spinnerActivityLevel);
        buttonHitungKalori    = findViewById(R.id.buttonHitungKalori);
        backBtn = findViewById(R.id.backBtn);

        setupSpinnerActivity();

        buttonHitungKalori.setOnClickListener(v -> kirimDataKeResult());
        backBtn.setOnClickListener(v -> {
            finish();
        });

    }

    private void setupSpinnerActivity() {
        String[] levels = new String[]{
                "Sangat ringan (jarang olahraga)",
                "Ringan (1-3x/minggu)",
                "Sedang (3-5x/minggu)",
                "Berat (6-7x/minggu)",
                "Sangat berat (olahraga + kerja fisik)"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                levels
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivityLevel.setAdapter(adapter);
    }

    private void kirimDataKeResult() {
        String ageStr    = editTextAge.getText() != null ? editTextAge.getText().toString().trim() : "";
        String heightStr = editTextHeightKalori.getText() != null ? editTextHeightKalori.getText().toString().trim() : "";
        String weightStr = editTextWeightKalori.getText() != null ? editTextWeightKalori.getText().toString().trim() : "";

        if (TextUtils.isEmpty(ageStr) || TextUtils.isEmpty(heightStr) || TextUtils.isEmpty(weightStr)) {
            Toast.makeText(this, "Umur, tinggi, dan berat harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isMale = (selectedGenderId == R.id.radioMale);
        int activityIndex = spinnerActivityLevel.getSelectedItemPosition();

        try {
            int age = Integer.parseInt(ageStr);
            double height = Double.parseDouble(heightStr); // cm
            double weight = Double.parseDouble(weightStr); // kg

            Intent intent = new Intent(kalori_input.this, kalori_result.class);
            intent.putExtra("EXTRA_AGE", age);
            intent.putExtra("EXTRA_HEIGHT", height);
            intent.putExtra("EXTRA_WEIGHT", weight);
            intent.putExtra("EXTRA_IS_MALE", isMale);
            intent.putExtra("EXTRA_ACTIVITY_INDEX", activityIndex);
            startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Format input tidak valid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        // Back ke MainActivity seperti BMI input
        Intent intent = new Intent(kalori_input.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}