package com.android.launcher3.icons;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.android.launcher3.util.ReflectUtils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class CustomAppIcons {
    private static CustomAppIcons sInstance;
    private IconPack mIconPack;
    private String mSystemState;
    private final Map mIconFactoryMap = new HashMap();
    private IconPack mDefaultIconPack = IconPack.DEFAULT_ICON_PACK;

    private CustomAppIcons() {
    }

    private Drawable getCustomAppIcon(Context context, String str, String str2) {
        Drawable appIcon;
        resetConfigIfNeed(context);
        String lowerCase = str == null ? "" : str.toLowerCase();
        String lowerCase2 = str2 != null ? str2.toLowerCase() : "";
        String appliedIconPack = IconPackManager.getAppliedIconPack(context);
        IconPack iconPack = this.mIconPack;
        if (iconPack == null || !appliedIconPack.equals(iconPack.getPackageName())) {
            this.mIconPack = IconPackManager.newIconPack(context, appliedIconPack);
        }
        if (this.mIconPack.isDefault() || (appIcon = this.mIconPack.getAppIcon(context, lowerCase, lowerCase2)) == null) {
            appIcon = this.mDefaultIconPack.getAppIcon(context, lowerCase, lowerCase2);
        }
        if (Logger.DEBUG) {
            Log.d("CustomAppIcons", "Icon from CustomAppIcons: " + lowerCase + " | " + lowerCase2 + " | " + appIcon);
        }
        return appIcon;
    }

    public static CustomAppIcons getInstance() {
        if (sInstance == null) {
            synchronized (CustomAppIcons.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new CustomAppIcons();
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    private void resetConfigIfNeed(Context context) {
        String systemIconState = IconProvider.getSystemIconState(context);
        if (systemIconState.equals(this.mSystemState)) {
            return;
        }
        this.mSystemState = systemIconState;
        resetConfig();
    }

    public Drawable loadIcon(Context context, ComponentName componentName) {
        try {
            return getCustomAppIcon(context, componentName.getPackageName(), componentName.getClassName());
        } catch (Exception unused) {
            Log.e("CustomAppIcons", "Get error on getting custom icon drawable for " + componentName);
            return null;
        }
    }

    public Drawable loadIcon(Context context, Drawable drawable, boolean z) {
        try {
            if (IconProvider.verifyMonoIcon(z, drawable) || (drawable instanceof BitmapDrawable)) {
                return drawable;
            }
            Drawable drawable2 = (Drawable) ReflectUtils.getFieldValue(drawable, "originDrawable");
            if (IconProvider.verifyMonoIcon(z, drawable2)) {
                return drawable2;
            }
            ComponentName componentName = (ComponentName) ReflectUtils.getFieldValue(drawable, "component");
            if (componentName != null) {
                Drawable drawableLoadIcon = loadIcon(context, componentName);
                if (drawableLoadIcon != null) {
                    return drawableLoadIcon;
                }
            }
            if (drawable2 != null) {
                return drawable2;
            }
        } catch (Exception e) {
            Log.e("CustomAppIcons", "Exception on loading icon from drawable: " + drawable, e);
        }
        return drawable;
    }

    public void resetConfig() {
        this.mDefaultIconPack.resetConfig();
        IconPack iconPack = this.mIconPack;
        if (iconPack != null) {
            iconPack.resetConfig();
        }
    }
}
