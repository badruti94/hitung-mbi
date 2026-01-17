package com.example.hitungmbi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hitungmbi.data.BmiRecord;
import com.example.hitungmbi.data.BmiViewModel;
import com.example.hitungmbi.jobs.BmiUploadService;
import com.example.hitungmbi.util.AppActions;
import com.example.hitungmbi.util.NetworkUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class bmi_result extends AppCompatActivity {

    private TextView textViewBmiValue;
    private TextView textViewBmiCategory;
    private TextView textViewKategoriTitle;
    private TextView textViewDescription;
    private TextView textViewDescriptionDetail;
    private TextView textViewAdviceOnline;
    private Button buttonHitungKembali;
    private Button buttonFetchAdvice;

    private AsyncTask<Void, Void, String> adviceTask;

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
        textViewAdviceOnline      = findViewById(R.id.textViewAdviceOnline);
        buttonHitungKembali       = findViewById(R.id.buttonHitungKembali);
        buttonFetchAdvice         = findViewById(R.id.buttonFetchAdvice);

        Intent intent = getIntent();
        double height = intent.getDoubleExtra("EXTRA_HEIGHT", 0.0); // cm
        double weight = intent.getDoubleExtra("EXTRA_WEIGHT", 0.0); // kg
        String gender = getIntent().getStringExtra("EXTRA_GENDER");

        if (height > 0 && weight > 0) {
            double bmi = BmiCalculator.calculateBmi(weight, height);

            DecimalFormat df = new DecimalFormat("#.##");
            String bmiStr = df.format(bmi);

            BmiInfo info = BmiCalculator.getBmiInfo(bmi, gender);

            textViewBmiValue.setText(bmiStr);
            textViewBmiCategory.setText(info.category);
            textViewKategoriTitle.setText(info.title);
            textViewDescription.setText(info.description);
            textViewDescriptionDetail.setText(info.detail);

            // --- Room + LiveData + ViewModel ---
            BmiViewModel vm = new ViewModelProvider(this).get(BmiViewModel.class);
            BmiRecord record = new BmiRecord(height, weight, bmi, gender, System.currentTimeMillis());
            vm.insert(record, () -> {
//                sendBroadcast(new Intent(AppActions.ACTION_BMI_SAVED));
                // KETERANGAN : Broadcast
                Intent i = new Intent(this, com.example.hitungmbi.jobs.BmiSavedReceiver.class);
                i.setAction(AppActions.ACTION_BMI_SAVED); // optional, biar log action tetap ada
                sendBroadcast(i);

            });

        } else {
            textViewBmiValue.setText("-");
            textViewBmiCategory.setText("Data tidak valid");
            textViewKategoriTitle.setText("");
            textViewDescription.setText("");
            textViewDescriptionDetail.setText("");
        }

        // --- Background Task (AsyncTask) + Internet Connection ---
        buttonFetchAdvice.setOnClickListener(v -> {
            if (!NetworkUtil.isOnline(this)) {
                Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                return;
            }
            if (adviceTask != null) adviceTask.cancel(true);

            textViewAdviceOnline.setText("Memuat saran online...");
            // KETERANGAN : Background TaskAsyncTask, Background Task-Internet Connection
            adviceTask = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        URL url = new URL("https://hitungmbi-be.vercel.app/api/v1/tips/random");
                        HttpURLConnection c = (HttpURLConnection) url.openConnection();
                        c.setConnectTimeout(10_000);
                        c.setReadTimeout(15_000);
                        c.setRequestMethod("GET");

                        try (BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) sb.append(line);
                            JSONObject o = new JSONObject(sb.toString());
                            return o.optString("title", "");
                        }
                    } catch (Exception e) {
                        Log.e("NET", "Fetch advice failed", e);
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    if (result == null || result.trim().isEmpty()) {
                        textViewAdviceOnline.setText("Gagal memuat saran online");
                    } else {
                        textViewAdviceOnline.setText("Saran online: " + result);
                    }
                }
            }.execute();
        });

        buttonHitungKembali.setOnClickListener(v -> {
            Intent backIntent = new Intent(bmi_result.this, bmi_input.class);
            startActivity(backIntent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adviceTask != null) adviceTask.cancel(true);
    }
}
