package com.example.islamicapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Map.Entry;

import java.util.Map;

public class PreferencesHandler {
    private static final String SHARED_PREFERENCES_KEY = "com.webandcrafts.itspremium";
    static PreferencesHandler mThis;
    private Context mContext;

    private PreferencesHandler(Context c) {
        this.mContext = c.getApplicationContext();
    }

    public static PreferencesHandler getInstance(Context c) {
        if (mThis == null) {
            mThis = new PreferencesHandler(c);
        }
        return mThis;
    }

    private SharedPreferences getAppSharedPreferencesObject() {
        return this.mContext.getSharedPreferences(SHARED_PREFERENCES_KEY, 0);
    }

    public Object getValue(String key) {
        if (key == null) {
            return null;
        }
        for (Map.Entry<String, ?> entry : getAppSharedPreferencesObject().getAll().entrySet()) {
            if (((String) entry.getKey()).equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean saveSharedPreferences(String key, boolean object) {
        if (key == null) {
            return false;
        }
        SharedPreferences.Editor sharedPreferencesEditor = getAppSharedPreferencesObject().edit();
        sharedPreferencesEditor.putBoolean(key, object);
        return sharedPreferencesEditor.commit();
    }

    public boolean saveSharedPreferences(String key, float object) {
        if (key == null) {
            return false;
        }
        Editor sharedPreferencesEditor = getAppSharedPreferencesObject().edit();
        sharedPreferencesEditor.putFloat(key, object);
        return sharedPreferencesEditor.commit();
    }

    public boolean saveSharedPreferences(String key, int object) {
        if (key == null) {
            return false;
        }
        Editor sharedPreferencesEditor = getAppSharedPreferencesObject().edit();
        sharedPreferencesEditor.putInt(key, object);
        return sharedPreferencesEditor.commit();
    }

    public boolean saveSharedPreferences(String key, long object) {
        if (key == null) {
            return false;
        }
        SharedPreferences.Editor sharedPreferencesEditor = getAppSharedPreferencesObject().edit();
        sharedPreferencesEditor.putLong(key, object);
        return sharedPreferencesEditor.commit();
    }

    public boolean saveSharedPreferences(String key, String object) {
        if (key == null || object == null) {
            return false;
        }
        Editor sharedPreferencesEditor = getAppSharedPreferencesObject().edit();
        sharedPreferencesEditor.putString(key, object);
        return sharedPreferencesEditor.commit();
    }

    public void setNavigation(String from) {
        saveSharedPreferences("from", from);
    }

    public String getNavigation() {
        Object prefObj = getValue("from");
        if (prefObj != null) {
            return (String) prefObj;
        }
        return (String) prefObj;
    }
}
