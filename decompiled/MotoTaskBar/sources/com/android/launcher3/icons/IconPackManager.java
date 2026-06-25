package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public abstract class IconPackManager {
    private static String sDefaultAppliedIconPack;
    private static String sSystemIconPack;

    public static String getAppliedIconPack(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "pref_icon_pack");
        if (TextUtils.isEmpty(string)) {
            string = getDefaultAppliedIconPack(context);
        }
        return (TextUtils.isEmpty(string) || TextUtils.isEmpty(string.trim())) ? "system" : string;
    }

    public static String getDefaultAppliedIconPack(Context context) {
        Resources resources;
        int identifier;
        String str = sDefaultAppliedIconPack;
        if (str != null) {
            return str;
        }
        try {
            resources = context.createPackageContext("com.motorola.launcherconfig", 0).getResources();
        } catch (Exception e) {
            Log.w("IconPackManager", "loadIconPackConfigPackage error", e);
            resources = null;
        }
        if (resources != null && (identifier = resources.getIdentifier("default_icon_pack", "string", "com.motorola.launcherconfig")) != 0) {
            sDefaultAppliedIconPack = resources.getString(identifier);
        }
        if (sDefaultAppliedIconPack == null) {
            sDefaultAppliedIconPack = "system";
        }
        Log.d("IconPackManager", "getDefaultAppliedIconPack: " + sDefaultAppliedIconPack);
        return sDefaultAppliedIconPack;
    }

    public static String getSystemIconPack(Context context) {
        Resources resources;
        int identifier;
        String str = sSystemIconPack;
        if (str != null) {
            return str;
        }
        try {
            resources = context.createPackageContext("com.motorola.launcherconfig", 0).getResources();
        } catch (Exception e) {
            Log.w("IconPackManager", "loadIconPackConfigPackage error", e);
            resources = null;
        }
        if (resources != null && (identifier = resources.getIdentifier("system_icon_pack", "string", "com.motorola.launcherconfig")) != 0) {
            sSystemIconPack = resources.getString(identifier);
        }
        if (sSystemIconPack == null) {
            sSystemIconPack = "";
        }
        Log.d("IconPackManager", "getSystemIconPack: " + sSystemIconPack);
        return sSystemIconPack;
    }

    public static IconPack newIconPack(Context context, String str) {
        return newIconPack(context, str, null);
    }

    public static IconPack newIconPack(Context context, String str, String str2) {
        if (str == null || "system".equals(str) || getSystemIconPack(context).equals(str)) {
            IconPack iconPack = IconPack.DEFAULT_ICON_PACK;
            iconPack.load(context);
            return iconPack;
        }
        if (!IconPack.isAppEnabled(context.getPackageManager(), str)) {
            String defaultAppliedIconPack = getDefaultAppliedIconPack(context);
            if (!str.equals(defaultAppliedIconPack)) {
                return newIconPack(context, defaultAppliedIconPack);
            }
            IconPack iconPack2 = IconPack.DEFAULT_ICON_PACK;
            iconPack2.load(context);
            return iconPack2;
        }
        try {
            IconPack iconPack3 = new IconPack(context, str, str2);
            iconPack3.load(context);
            return iconPack3;
        } catch (Exception unused) {
            Log.w("IconPackManager", "Cannot new Icon pack: " + str);
            return IconPack.DEFAULT_ICON_PACK;
        }
    }
}
