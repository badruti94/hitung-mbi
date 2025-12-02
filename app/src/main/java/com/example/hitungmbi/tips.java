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
        TipsGenerator.setRandomTip(textViewTips);

        // kalau layout di-klik, ganti tips baru
        layoutTips.setOnClickListener(v -> TipsGenerator.setRandomTip(textViewTips));
    }


}