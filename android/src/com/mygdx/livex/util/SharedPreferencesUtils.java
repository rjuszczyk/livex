package com.mygdx.livex.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by Radek on 2016-02-27.
 */
public class SharedPreferencesUtils {
    public static final String APP_SHARED_PREF_KEY = "SilesiaApp";

    public static final String KEY_STORE_INDEX_INSERTED = "store_index_inserterd";



    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SHARED_PREF_KEY, Context.MODE_PRIVATE);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void setStoreIndexInserted(Context context, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_STORE_INDEX_INSERTED, value);
        editor.apply();
    }

    public static boolean isStoreIndexInserted(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_STORE_INDEX_INSERTED, false);
    }
}
