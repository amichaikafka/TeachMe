package com.example.myapplication2.view;
import android.os.Bundle;
import android.preference.PreferenceFragment;


import androidx.annotation.Nullable;

import com.example.myapplication2.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.root_preferences);
    }

}