package com.example.hitungmbi.jobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.hitungmbi.util.NotificationHelper;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showSimpleNotification(context, "HitungBMI", "Saatnya cek BMI hari ini");
    }
}
