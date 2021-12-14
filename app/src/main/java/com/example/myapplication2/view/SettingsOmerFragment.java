package com.example.myapplication2.view;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.myapplication2.R;

public class SettingsOmerFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}