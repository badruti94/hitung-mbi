package com.example.hitungmbi;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class bmi_input extends AppCompatActivity {

    private TextInputEditText editTextHeight;
    private TextInputEditText editTextWeight;
    private AutoCompleteTextView editGender;
    private Button buttonHitung;

    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editGender =  findViewById(R.id.editGender);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        buttonHitung   = findViewById(R.id.buttonHitung);
        backBtn = findViewById(R.id.backBtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Male", "Female"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editGender.setAdapter(adapter);

        buttonHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimDataKeResult();
            }
        });


        backBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void kirimDataKeResult() {
        String heightStr = editTextHeight.getText() != null ? editTextHeight.getText().toString().trim() : "";
        String weightStr = editTextWeight.getText() != null ? editTextWeight.getText().toString().trim() : "";

        if (TextUtils.isEmpty(heightStr) || TextUtils.isEmpty(weightStr)) {
            Toast.makeText(this, "Tinggi dan berat badan harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double height = Double.parseDouble(heightStr); // diasumsikan cm
            double weight = Double.parseDouble(weightStr); // kg
            String selectedGender = editGender.getText().toString();

            Intent intent = new Intent(bmi_input.this, bmi_result.class);
            intent.putExtra("EXTRA_HEIGHT", height);
            intent.putExtra("EXTRA_WEIGHT", weight);
            intent.putExtra("EXTRA_GENDER", selectedGender);
            startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Format input tidak valid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(bmi_input.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}