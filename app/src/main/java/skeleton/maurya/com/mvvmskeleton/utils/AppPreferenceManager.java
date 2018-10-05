package skeleton.maurya.com.mvvmskeleton.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * AppPreferenceManager is class to save all types of data like string, boolean, int etc
 * {@link Application}
 *
 */
public class AppPreferenceManager {

    private final Application application;
    private static final String SHARED_PREFERENCE = "rogers-shared-pref";

    @Inject
    public AppPreferenceManager(Application application) {
        this.application = application;
    }

    /**
     * save string value in shared preference
     *
     * @param key
     * @param value
     */
    public void save(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * save boolean value in shared preference
     *
     * @param key
     * @param value
     */
    public void save(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void save(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * save long value in shared preference
     *
     * @param key
     * @param value
     */
    public void save(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * get string value from shared preference
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return getPreferences().getString(key, "");
    }

    /**
     * get integer value from shared preference
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        return getPreferences().getInt(key, 0);
    }

    /**
     * get boolean value from shared preference
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        return getPreferences().getBoolean(key, false);
    }

    /**
     * get long value from shared preference
     *
     * @param key
     * @return
     */
    public long getLong(String key) {
        return getPreferences().getLong(key, 0);
    }

    /**
     * remove particular key from shared preference
     *
     * @param key
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * clear shared preference from complete app
     */
    public void clear() {
        getPreferences().edit().clear().apply();
    }


    /**
     * return shared preference
     *
     * @return
     */
    private SharedPreferences getPreferences() {
        return application.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

}
