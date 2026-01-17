package com.example.hitungmbi.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// KETERANGAN : Room Live Data, and View Model
public class BmiViewModel extends AndroidViewModel {
    private final BmiRepository repo;
    private final LiveData<List<BmiRecord>> all;

    public BmiViewModel(@NonNull Application application) {
        super(application);
        repo = new BmiRepository(application);
        all = repo.observeAll();
    }

    public LiveData<List<BmiRecord>> observeAll() {
        return all;
    }

    public void insert(BmiRecord record, Runnable onDone) {
        repo.insertAsync(record, onDone);
    }
}
