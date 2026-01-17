package com.example.hitungmbi.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.hitungmbi.R;

// KETERANGAN : Shared Preferences and Settings
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
