package com.example.hitungmbi;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class kalori_result extends AppCompatActivity {

    public static final String EXTRA_AGE = "EXTRA_AGE";
    public static final String EXTRA_HEIGHT = "EXTRA_HEIGHT";
    public static final String EXTRA_WEIGHT = "EXTRA_WEIGHT";
    public static final String EXTRA_IS_MALE = "EXTRA_IS_MALE";
    public static final String EXTRA_ACTIVITY_INDEX = "EXTRA_ACTIVITY_INDEX";

    private TextView tvCalorieValue, tvResultActivityLevel;
    private TextView tvCalorieDesc1, tvCalorieDesc2, tvCalorieDesc3;
    private Button btnHitungLagi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalori_result);

        tvCalorieValue = findViewById(R.id.tvCalorieValue);
        tvResultActivityLevel = findViewById(R.id.tvResultActivityLevel);
        tvCalorieDesc1 = findViewById(R.id.tvCalorieDesc1);
        tvCalorieDesc2 = findViewById(R.id.tvCalorieDesc2); // turun BB
        tvCalorieDesc3 = findViewById(R.id.tvCalorieDesc3); // naik BB
        btnHitungLagi = findViewById(R.id.btnHitungKaloriLagi);

        // Ambil data dari Intent
        int age = getIntent().getIntExtra(EXTRA_AGE, 0);
        double height = getIntent().getDoubleExtra(EXTRA_HEIGHT, 0);
        double weight = getIntent().getDoubleExtra(EXTRA_WEIGHT, 0);
        boolean isMale = getIntent().getBooleanExtra(EXTRA_IS_MALE, true);
        int activityIndex = getIntent().getIntExtra(EXTRA_ACTIVITY_INDEX, 1);

        // Hitung kalori
        double bmr = hitungBMR(weight, height, age, isMale);
        double tdee = hitungTDEE(bmr, activityIndex);

        // Bulatkan
        int tdeeInt = (int) Math.round(tdee);
        tvCalorieValue.setText(String.valueOf(tdeeInt));

        // Tampilkan level aktivitas
        tvResultActivityLevel.setText("Level aktivitas: " + getActivityLabel(activityIndex));

        // Keterangan mempertahankan berat
        tvCalorieDesc1.setText(
                "Kalori ini adalah estimasi untuk mempertahankan berat badan Anda saat ini."
        );

        // Hitung rentang kalori untuk turun & naik berat
        int turunMin = (int) Math.round(tdee - 500);  // lebih besar defisit
        int turunMax = (int) Math.round(tdee - 300);  // defisit lebih kecil
        int naikMin  = (int) Math.round(tdee + 300);
        int naikMax  = (int) Math.round(tdee + 500);

        // Pastikan tidak ada nilai negatif
        if (turunMin < 0) turunMin = 0;
        if (turunMax < 0) turunMax = 0;

        // Set teks dinamis (turun BB)
        String textTurun = String.format(
                Locale.getDefault(),
                "Untuk menurunkan berat badan, Anda dapat mengonsumsi sekitar %d–%d kcal per hari secara bertahap.",
                turunMin, turunMax
        );
        tvCalorieDesc2.setText(textTurun);

        // Set teks dinamis (naik BB)
        String textNaik = String.format(
                Locale.getDefault(),
                "Untuk menaikkan berat badan, Anda dapat mengonsumsi sekitar %d–%d kcal per hari dengan makanan bergizi.",
                naikMin, naikMax
        );
        tvCalorieDesc3.setText(textNaik);

        // Tombol kembali ke form
        btnHitungLagi.setOnClickListener(v -> finish());
    }

    // Rumus Mifflin-St Jeor
    private double hitungBMR(double weight, double height, int age, boolean isMale) {
        if (isMale) {
            return (10 * weight) + (6.25 * height) - (5 * age) + 5;
        } else {
            return (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }
    }

    // Faktor Aktivitas:
    // 0 = Sangat ringan
    // 1 = Ringan
    // 2 = Sedang
    // 3 = Berat
    // 4 = Sangat Berat
    private double hitungTDEE(double bmr, int index) {
        double factor;
        switch (index) {
            case 0:
                factor = 1.2;   // sangat ringan
                break;
            case 1:
                factor = 1.375; // ringan
                break;
            case 2:
                factor = 1.55;  // sedang
                break;
            case 3:
                factor = 1.725; // berat
                break;
            case 4:
                factor = 1.9;   // sangat berat
                break;
            default:
                factor = 1.55;
        }
        return bmr * factor;
    }

    private String getActivityLabel(int index) {
        switch (index) {
            case 0:
                return "Sangat Ringan (jarang olahraga)";
            case 1:
                return "Ringan (1–2x seminggu)";
            case 2:
                return "Sedang (3–5x seminggu)";
            case 3:
                return "Berat (6–7x seminggu)";
            case 4:
                return "Sangat Berat (atlet)";
            default:
                return "Tidak diketahui";
        }
    }
}
