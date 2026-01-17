package com.example.hitungmbi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import com.example.hitungmbi.jobs.AlarmSchedulerUtil;
import com.example.hitungmbi.jobs.BmiUploadService;
import com.example.hitungmbi.settings.SettingsActivity;

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

        View layoutBmi = findViewById(R.id.layoutBmi);
        View layoutTips = findViewById(R.id.layoutTips);
        View layoutAbout = findViewById(R.id.layoutAbout);
        View layoutKalori = findViewById(R.id.layoutKalori);
        View layoutHistory = findViewById(R.id.layoutHistory);
        View layoutSettings = findViewById(R.id.layoultSettings);
        View layoutSync = findViewById(R.id.layoultSync);
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

        layoutBmi.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, bmi_input.class)));
        layoutTips.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, tips.class)));
        layoutAbout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, about.class)));
        layoutKalori.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, kalori_input.class)));
        layoutHistory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HistoryActivity.class)));
        layoutSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
        layoutSync.setOnClickListener(v -> {
            BmiUploadService.startUpload(this);
            Toast.makeText(this, "Sync dijalankan di background", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Implementasi Alarms and Schedulers
        boolean enabled = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("pref_reminder_enabled", false);
        String time = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("pref_reminder_time", "08:00");

        if (enabled) {
            int h = 8, m = 0;
            try {
                String[] p = time.split(":");
                h = Integer.parseInt(p[0]);
                m = Integer.parseInt(p[1]);
            } catch (Exception ignored) {}
            AlarmSchedulerUtil.scheduleDaily(this, h, m);
        } else {
            AlarmSchedulerUtil.cancelDaily(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_history) {
            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        } else if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_sync) {
            // Service demo: manual sync via JobIntentService
            BmiUploadService.startUpload(this);
            Toast.makeText(this, "Sync dijalankan di background", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
