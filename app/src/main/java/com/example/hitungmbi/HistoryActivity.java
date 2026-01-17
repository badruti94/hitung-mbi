package com.example.hitungmbi;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hitungmbi.data.BmiViewModel;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        TextView empty = findViewById(R.id.textEmpty);

        RecyclerView rv = findViewById(R.id.recyclerHistory);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        HistoryAdapter adapter = new HistoryAdapter();
        rv.setAdapter(adapter);

        BmiViewModel vm = new ViewModelProvider(this).get(BmiViewModel.class);
        vm.observeAll().observe(this, records -> {
            adapter.submit(records);
            boolean isEmpty = (records == null || records.isEmpty());
            empty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        });
    }
}
