package com.dstimetracker.devsodin.ds_timetracker;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.dstimetracker.devsodin.core.Clock;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String key) {
        addPreferencesFromResource(R.xml.app_preferences);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                switch (key) {
                    case SettingsActivity.KEY_PREFERENCE_LANGUAGE:
                        break;
                    case SettingsActivity.KEY_PREFERENCE_REFRESH_RATE:
                        int ticks = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREFERENCE_REFRESH_RATE, "1"));
                        Clock.getInstance().setRefreshTicks(ticks);
                        break;
                    default:
                        break;
                }
            }
        });
    }



}
