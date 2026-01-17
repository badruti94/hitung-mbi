package com.example.hitungmbi.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OnlineTipsLoader extends AsyncTaskLoader<List<String>> {

    public OnlineTipsLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<String> loadInBackground() {
        if (!NetworkUtil.isOnline(getContext())) return null;
        try {
            URL url = new URL("https://hitungmbi-be.vercel.app/api/v1/tips");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setConnectTimeout(10_000);
            c.setReadTimeout(15_000);
            c.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);

                JSONArray arr = new JSONArray(sb.toString());
                List<String> tips = new ArrayList<>();
                for (int i = 0; i < Math.min(10, arr.length()); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    tips.add(o.getString("title"));
                }
                return tips;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
