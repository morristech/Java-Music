package com.nihk.github.musictheoryromannumeralanalysisquiz;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MyPreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}