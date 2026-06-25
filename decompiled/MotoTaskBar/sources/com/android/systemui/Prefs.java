package com.android.systemui;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public abstract class Prefs {
    public static SharedPreferences get(Context context) {
        return context.getSharedPreferences(context.getPackageName(), 0);
    }

    public static boolean getBoolean(Context context, String str, boolean z) {
        return get(context).getBoolean(str, z);
    }

    public static String getString(Context context, String str, String str2) {
        return get(context).getString(str, str2);
    }

    public static void putBoolean(Context context, String str, boolean z) {
        get(context).edit().putBoolean(str, z).apply();
    }

    public static void putString(Context context, String str, String str2) {
        get(context).edit().putString(str, str2).apply();
    }
}
