package com.example.hitungmbi.jobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.preference.PreferenceManager;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean enabled = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("pref_reminder_enabled", false);
        if (!enabled) return;

        String time = PreferenceManager.getDefaultSharedPreferences(context)
                .getString("pref_reminder_time", "08:00");
        int h = 8, m = 0;
        try {
            String[] p = time.split(":");
            h = Integer.parseInt(p[0]);
            m = Integer.parseInt(p[1]);
        } catch (Exception ignored) {}

        AlarmSchedulerUtil.scheduleDaily(context, h, m);
    }
}
