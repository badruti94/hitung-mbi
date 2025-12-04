package com.example.hitungmbi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View layoutBmi   = findViewById(R.id.layoutBmi);
        View layoutTips  = findViewById(R.id.layoutTips);
        View layoutAbout = findViewById(R.id.layoutAbout);
        View layoutKalori = findViewById(R.id.layoutKalori);
        TextView textGreeting = findViewById(R.id.textView4);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;
        if (hour >= 4 && hour < 10) {
            greeting = "Selamat Pagi";
        } else if (hour >= 10 && hour < 15) {
            greeting = "Selamat Siang";
        } else if (hour >= 15 && hour < 18) {
            greeting = "Selamat Sore";
        } else {
            greeting = "Selamat Malam";
        }

        textGreeting.setText(greeting);


        layoutBmi.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, bmi_input.class);
            startActivity(intent);
        });

        layoutTips.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, tips.class);
            startActivity(intent);
        });

        layoutAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, about.class);
            startActivity(intent);
        });

        layoutKalori.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, kalori_input.class);
            startActivity(intent);
        });
    }
}