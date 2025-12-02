package com.example.hitungmbi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class bmi_result extends AppCompatActivity {

    private TextView textViewBmiValue;
    private TextView textViewBmiCategory;
    private TextView textViewKategoriTitle;
    private TextView textViewDescription;
    private TextView textViewDescriptionDetail;
    private Button buttonHitungKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        textViewBmiValue          = findViewById(R.id.textViewBmiValue);
        textViewBmiCategory       = findViewById(R.id.textViewBmiCategory);
        textViewKategoriTitle     = findViewById(R.id.textViewKategoriTitle);
        textViewDescription       = findViewById(R.id.textViewDescription);
        textViewDescriptionDetail = findViewById(R.id.textViewDescriptionDetail);
        buttonHitungKembali       = findViewById(R.id.buttonHitungKembali);

        // Ambil data dari Intent
        Intent intent = getIntent();
        double height = intent.getDoubleExtra("EXTRA_HEIGHT", 0.0); // cm
        double weight = intent.getDoubleExtra("EXTRA_WEIGHT", 0.0); // kg

        if (height > 0 && weight > 0) {
            double bmi = BmiCalculator.calculateBmi(weight, height);

            // format 2 angka di belakang koma
            DecimalFormat df = new DecimalFormat("#.##");
            String bmiStr = df.format(bmi);

            BmiInfo info = BmiCalculator.getBmiInfo(bmi);

            textViewBmiValue.setText(bmiStr);
            textViewBmiCategory.setText(info.category);
            textViewKategoriTitle.setText(info.title);
            textViewDescription.setText(info.description);
            textViewDescriptionDetail.setText(info.detail);
        } else {
            textViewBmiValue.setText("-");
            textViewBmiCategory.setText("Data tidak valid");
            textViewKategoriTitle.setText("");
            textViewDescription.setText("");
            textViewDescriptionDetail.setText("");
        }

        buttonHitungKembali.setOnClickListener(v -> {
            Intent backIntent = new Intent(bmi_result.this, bmi_input.class);
            startActivity(backIntent);
            finish();
        });
    }

}