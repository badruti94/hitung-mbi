package com.example.hitungmbi.data;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BmiRepository {
    private final BmiRecordDao dao;
    private final Executor io = Executors.newSingleThreadExecutor();

    public BmiRepository(Context context) {
        dao = AppDatabase.getInstance(context).bmiRecordDao();
    }

    public LiveData<List<BmiRecord>> observeAll() {
        return dao.observeAll();
    }

    public void insertAsync(BmiRecord record, Runnable onDone) {
        io.execute(() -> {
            dao.insert(record);
            if (onDone != null) onDone.run();
        });
    }

    public List<BmiRecord> getUnsyncedBlocking(int limit) {
        return dao.getUnsynced(limit);
    }

    public void markSyncedBlocking(List<BmiRecord> records) {
        for (BmiRecord r : records) r.synced = true;
        dao.update(records);
    }
}
