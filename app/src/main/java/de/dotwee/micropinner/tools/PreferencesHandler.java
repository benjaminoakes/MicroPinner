package de.dotwee.micropinner.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lukas on 18.08.2015 - 16:11
 * for project MicroPinner.
 */
public class PreferencesHandler {
    private static final String PREF_FIRST_USE = "pref_firstuse";
    private static final String PREF_ADVANCED_USE = "pref_advanceduse";
    private static final String PREF_LIGHT_THEME = "pref_lighttheme";

    private final static String LOG_TAG = "PreferencesHandler";
    private static PreferencesHandler instance;
    private final SharedPreferences preferences;

    private PreferencesHandler(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized PreferencesHandler getInstance(Context context) {
        if (instance == null)
            instance = new PreferencesHandler(context);

        return instance;
    }

    public boolean isFirstUse() {
        boolean ret = false;

        if (!preferences.contains(PREF_FIRST_USE)) {
            preferences.edit().putBoolean(PREF_FIRST_USE, true).apply();
            ret = true;
        }

        return ret;
    }

    public boolean isAdvancedUsed() {
        return preferences.getBoolean(PREF_ADVANCED_USE, false);
    }

    public void setAdvancedUse(Boolean b) {
        applyPreference(PREF_ADVANCED_USE, b);
    }

    public boolean isLightThemeEnabled() {
        return preferences.getBoolean(PREF_LIGHT_THEME, true);
    }

    public void setLightThemeEnabled(Boolean b) {
        applyPreference(PREF_LIGHT_THEME, b);
    }

    private void applyPreference(String key, boolean state) {
        preferences.edit().putBoolean(key, state).apply();
    }
}
