package com.example.hitungmbi.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bmi_records")
public class BmiRecord {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public double heightCm;
    public double weightKg;
    public double bmi;
    public String gender;
    public long createdAtMillis;
    public boolean synced;

    public BmiRecord() {}

    public BmiRecord(double heightCm, double weightKg, double bmi, String gender, long createdAtMillis) {
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.bmi = bmi;
        this.gender = gender;
        this.createdAtMillis = createdAtMillis;
        this.synced = false;
    }
}
