package com.example.hitungmbi.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
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

public class BmiSyncJobService extends JobService {

    private AsyncTask<Void, Void, Boolean> task;

    // KETERANGAN :  JobScheduler
    @Override
    public boolean onStartJob(JobParameters params) {
        task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    BmiRepository repo = new BmiRepository(getApplicationContext());
                    List<BmiRecord> batch = repo.getUnsyncedBlocking(25);
                    if (batch.isEmpty()) return true;

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

                    // KETERANGAN : Efficient Data Transfer
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
                    if (code >= 200 && code < 300) {
                        repo.markSyncedBlocking(batch);
                        return true;
                    }
                    return false;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean ok) {
                jobFinished(params, !ok);
            }
        };
        task.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (task != null) task.cancel(true);
        return true;
    }
}
