package com.example.hitungmbi.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// KETERANGAN : Stroring Data with Room-SQLite Prime
@Dao
public interface BmiRecordDao {
    @Insert
    long insert(BmiRecord record);

    @Query("SELECT * FROM bmi_records ORDER BY createdAtMillis DESC")
    LiveData<List<BmiRecord>> observeAll();

    @Query("SELECT * FROM bmi_records WHERE synced = 0 ORDER BY createdAtMillis ASC LIMIT :limit")
    List<BmiRecord> getUnsynced(int limit);

    @Update
    int update(List<BmiRecord> records);

    @Query("DELETE FROM bmi_records")
    void deleteAll();
}
