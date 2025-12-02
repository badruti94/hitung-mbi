package com.example.hitungmbi;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class tips extends AppCompatActivity {
    private TextView textViewTips;
    private View layoutTips;

    private final String[] tipsList = new String[]{
            "Minum air putih minimal 8 gelas sehari.",
            "Usahakan tidur 7–9 jam setiap malam.",
            "Kurangi minuman manis dan perbanyak makan buah.",
            "Lakukan olahraga ringan 20–30 menit setiap hari.",
            "Jangan skip sarapan, pilih menu yang tinggi protein.",
            "Batasi makanan cepat saji dan gorengan.",
            "Luangkan waktu untuk stretching setelah duduk lama.",
            "Kelola stres dengan meditasi atau hobi yang kamu suka.",
            "Hindari merokok dan minuman beralkohol.",
            "Periksa kesehatan secara rutin bila diperlukan."
    };

    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tips);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewTips = findViewById(R.id.textView7);
        layoutTips   = findViewById(R.id.layoutTips);

        // pertama kali Activity dibuka
        setRandomTip();

        // kalau layout di-klik, ganti tips baru
        layoutTips.setOnClickListener(v -> setRandomTip());
    }

    private void setRandomTip() {
        int index = random.nextInt(tipsList.length);
        textViewTips.setText(tipsList[index]);
    }
}