package com.android.launcher3.icons;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public abstract class IconPackExt {
    public static final Pattern PATTERN_UNDERSCORE = Pattern.compile("(?<!_)_(?!_)");
    public static final Pattern PATTERN_UNDERSCORE2 = Pattern.compile("__");

    public static boolean load(IconPack iconPack, Context context, Bundle bundle) {
        Context contextCreatePackageContext;
        if (!iconPack.isDefault()) {
            return false;
        }
        String systemIconPack = IconPackManager.getSystemIconPack(context);
        if (!TextUtils.isEmpty(systemIconPack) && IconPack.isAppEnabled(context.getPackageManager(), systemIconPack)) {
            iconPack.setTargetPackage(systemIconPack);
            return false;
        }
        iconPack.setTargetPackage("com.motorola.launcherconfig");
        try {
            contextCreatePackageContext = context.createPackageContext("com.motorola.launcherconfig", 0);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("IconPackExt", "load - Cannot find pkg: com.motorola.launcherconfig");
            contextCreatePackageContext = null;
        }
        if (contextCreatePackageContext == null) {
            return true;
        }
        loadCustomAppIcons(contextCreatePackageContext, iconPack.getPackageDrawables());
        return true;
    }

    private static void loadCustomAppIcons(Context context, Map map) {
        try {
            if (Logger.DEBUG) {
                Log.d("IconPackExt", "Start cache drawables resources: " + context);
            }
            Resources resources = context.getResources();
            int identifier = resources.getIdentifier("app_icons", "array", "com.motorola.launcherconfig");
            if (identifier == 0) {
                return;
            }
            for (String str : resources.getStringArray(identifier)) {
                if (str.startsWith("appicon_")) {
                    map.put(PATTERN_UNDERSCORE2.matcher(PATTERN_UNDERSCORE.matcher(str.substring(8)).replaceAll(".")).replaceAll("_"), str);
                }
            }
        } catch (Exception e) {
            Log.e("IconPackExt", "Exception on getting app icons map", e);
        }
    }
}
