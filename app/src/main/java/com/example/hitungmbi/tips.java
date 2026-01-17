package com.example.hitungmbi;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.hitungmbi.util.NetworkUtil;
import com.example.hitungmbi.util.OnlineTipsLoader;

import java.util.ArrayList;
import java.util.List;

public class tips extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>> {

    private static final int LOADER_ID_TIPS = 5001;

    private TextView textViewTips;
    private View layoutTips;

    private final List<String> onlineTips = new ArrayList<>();
    private int onlineIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tips);

        textViewTips = findViewById(R.id.textView7);
        layoutTips = findViewById(R.id.layoutTips);

        TipsGenerator.setRandomTip(textViewTips);

        // AsyncTaskLoader
        if (NetworkUtil.isOnline(this)) {
            LoaderManager.getInstance(this).initLoader(LOADER_ID_TIPS, null, this);
        } else {
            Toast.makeText(this, "Offline: tips online tidak dimuat", Toast.LENGTH_SHORT).show();
        }

        layoutTips.setOnClickListener(v -> {
            if (!onlineTips.isEmpty()) {
                onlineIndex = (onlineIndex + 1) % onlineTips.size();
                textViewTips.setText(onlineTips.get(onlineIndex));
            } else {
                TipsGenerator.setRandomTip(textViewTips);
            }
        });
    }

    // KETERANGAN : AsyncTask Loade
    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        return new OnlineTipsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        if (data == null || data.isEmpty()) return;
        onlineTips.clear();
        onlineTips.addAll(data);
        onlineIndex = 0;
        textViewTips.setText(onlineTips.get(onlineIndex));
        Toast.makeText(this, "Tips online berhasil dimuat", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {
        onlineTips.clear();
    }
}
