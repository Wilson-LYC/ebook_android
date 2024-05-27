package com.ebook.app.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsUtil {

    private static final String PREFS_NAME = "app_preferences";
    private SharedPreferences preferences;

    private SharedPrefsUtil(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefsUtil with(Context context) {
        return new SharedPrefsUtil(context);
    }

    // 保存字符串
    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // 获取字符串
    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    // 保存整型
    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    // 获取整型
    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    // 保存布尔值
    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // 获取布尔值
    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    // 移除某个键值对
    public void remove(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    // 清空所有数据
    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}