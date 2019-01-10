package com.dstimetracker.devsodin.ds_timetracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.dstimetracker.devsodin.core.Clock;

import java.util.Locale;


/**
 * Fragment that adds the app preferneces and register a listener (to change the settings on runtime)
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
                            updateLanguage(getContext(),sharedPreferences);
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

    /**
     * Method that changes the app language on runtime
     *
     * @param context actual context
     * @param prefs   sharedprefereces (always the same file but must be a parameter because is static method)
     */
    public static void updateLanguage(Context context, SharedPreferences prefs){
        Locale locale = new Locale(prefs.getString(SettingsActivity.KEY_PREFERENCE_LANGUAGE, "en"));
        Configuration config = new Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config,null);
    }



}
