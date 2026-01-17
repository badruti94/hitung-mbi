package com.example.hitungmbi.jobs;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.hitungmbi.data.BmiRecord;
import com.example.hitungmbi.data.BmiRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

// KETERANGAN : Service
public class BmiUploadService extends IntentService {

    public static final String ACTION_UPLOAD = "com.example.hitungmbi.action.UPLOAD";

    public BmiUploadService() {
        super("BmiUploadService");
    }

    public static void startUpload(Context context) {
        Intent i = new Intent(context, BmiUploadService.class);
        i.setAction(ACTION_UPLOAD);
        context.startService(i);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) return;
        if (!ACTION_UPLOAD.equals(intent.getAction())) return;

        try {
            BmiRepository repo = new BmiRepository(getApplicationContext());
            List<BmiRecord> batch = repo.getUnsyncedBlocking(25);
            if (batch.isEmpty()) {
                Log.d("JOB", "Service sync: tidak ada data pending");
                return;
            }

            JSONArray arr = new JSONArray();
            for (BmiRecord r : batch) {
                JSONObject o = new JSONObject();
                o.put("id", r.id);
                o.put("heightCm", r.heightCm);
                o.put("weightKg", r.weightKg);
                o.put("bmi", r.bmi);
                o.put("gender", r.gender);
                o.put("createdAtMillis", r.createdAtMillis);
                arr.put(o);
            }
            JSONObject payload = new JSONObject();
            payload.put("records", arr);

            URL url = new URL("https://hitungmbi-be.vercel.app/api/v1/bmi/sync");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setConnectTimeout(10_000);
            c.setReadTimeout(15_000);
            c.setRequestMethod("POST");
            c.setRequestProperty("Content-Type", "application/json");
            c.setDoOutput(true);

            try (OutputStream os = new BufferedOutputStream(c.getOutputStream())) {
                os.write(payload.toString().getBytes());
                os.flush();
            }

            int code = c.getResponseCode();
            Log.d("JOB", "Service sync httpCode=" + code);
            if (code >= 200 && code < 300) {
                repo.markSyncedBlocking(batch);
                Log.d("JOB", "Service sync sukses -> markSynced");
            }
        } catch (Exception e) {
            Log.e("JOB", "Service sync gagal", e);
        }
    }
}
