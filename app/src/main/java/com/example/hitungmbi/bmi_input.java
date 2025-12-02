package com.example.hitungmbi;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
    private Button buttonHitung;

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

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        buttonHitung   = findViewById(R.id.buttonHitung);

        buttonHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimDataKeResult();
            }
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

            Intent intent = new Intent(bmi_input.this, bmi_result.class);
            intent.putExtra("EXTRA_HEIGHT", height);
            intent.putExtra("EXTRA_WEIGHT", weight);
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