package com.example.hitungmbi.jobs;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.preference.PreferenceManager;

/**
 * Helper untuk menjadwalkan sinkronisasi data BMI menggunakan JobScheduler.
 */
public final class JobSchedulerUtil {
    private static final int JOB_ID_SYNC = 1001;

    private JobSchedulerUtil() {}

    public static void scheduleOneOffSync(Context context) {
        ComponentName service = new ComponentName(context, BmiSyncJobService.class);
        JobInfo.Builder b = new JobInfo.Builder(JOB_ID_SYNC, service);

        boolean wifiOnly = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("pref_sync_wifi_only", false);

        if (wifiOnly) {
            b.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // Wi-Fi
        } else {
            b.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY); // data/Wi-Fi
        }

        b.setBackoffCriteria(30_000L, JobInfo.BACKOFF_POLICY_LINEAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            b.setMinimumLatency(1_000L);
        }

        b.setOverrideDeadline(5_000L);

        JobScheduler js = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = js.schedule(b.build());
        Log.d("JOB", "JobScheduler schedule result=" + result);
    }
}
