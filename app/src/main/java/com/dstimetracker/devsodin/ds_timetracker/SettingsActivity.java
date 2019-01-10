package com.dstimetracker.devsodin.ds_timetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity that holds the Settings fragment.
 * It also contains the keys used on preferences.
 */
public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_PREFERENCE_REFRESH_RATE = "pref_refresh_rate";
    public static final String KEY_PREFERENCE_LANGUAGE = "pref_languages";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }


}
