package com.jal.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by apollo on 2017/6/28.
 */

public class SPUtil {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void initSPUtil(Context context, String name) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 将Sp存入指定key对应的数据
     *
     * @param key
     * @param value 可以是String、boolean、int、float、long
     */
    public static void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 清除sp所有数据
     */
    public static void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 移除指定key对应的数据
     *
     * @param key
     */
    public static void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 获取Sp数据里指定的key对应的值
     *
     * @param key
     * @param defValue 不存在时返回
     * @return
     */
    public static String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    /**
     * 判断SP是否含有key的数据
     * @param key
     * @return
     */
    public static boolean contains(String key){
        return preferences.contains(key);
    }
}
