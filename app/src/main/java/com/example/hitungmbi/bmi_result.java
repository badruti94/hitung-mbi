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
            double heightMeter = height / 100.0;
            double bmi = weight / (heightMeter * heightMeter);

            // format 2 angka di belakang koma
            DecimalFormat df = new DecimalFormat("#.##");
            String bmiStr = df.format(bmi);

            BmiInfo info = getBmiInfo(bmi);

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


    // Class kecil untuk menampung informasi BMI
    private static class BmiInfo {
        String category;
        String title;
        String description;
        String detail;

        BmiInfo(String category, String title, String description, String detail) {
            this.category = category;
            this.title = title;
            this.description = description;
            this.detail = detail;
        }
    }

    private BmiInfo getBmiInfo(double bmi) {
        if (bmi < 18.5) {
            return new BmiInfo(
                    "Kurus",
                    "Berat Badan Kurang",
                    "Berat badanmu berada di bawah rentang normal.",
                    "Cobalah untuk meningkatkan asupan kalori dengan makanan bergizi seimbang dan konsultasikan ke tenaga kesehatan bila perlu."
            );
        } else if (bmi < 25) {
            return new BmiInfo(
                    "Normal",
                    "Berat Badan Normal",
                    "Berat badanmu berada pada rentang sehat.",
                    "Pertahankan pola makan seimbang, rutin berolahraga, cukup tidur, dan kelola stres agar kesehatan tetap terjaga."
            );
        } else if (bmi < 30) {
            return new BmiInfo(
                    "Berlebih",
                    "Berat Badan Berlebih",
                    "Berat badanmu sedikit di atas rentang normal.",
                    "Mulailah mengatur pola makan, kurangi makanan tinggi gula dan lemak jenuh, serta tambahkan aktivitas fisik ringan-sedang."
            );
        } else {
            return new BmiInfo(
                    "Obesitas",
                    "Obesitas",
                    "Berat badanmu sudah berada pada kategori obesitas.",
                    "Disarankan berkonsultasi dengan dokter atau ahli gizi untuk rencana penurunan berat badan yang aman dan bertahap."
            );
        }
    }
}