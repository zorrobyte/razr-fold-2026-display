package com.android.settingslib;

import android.R;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.IconFactory;
import com.motorola.taskbar.R$drawable;

/* JADX INFO: loaded from: classes.dex */
public abstract class Utils {
    static final int[] WIFI_PIE = {R$drawable.ic_wifi_signal_0, R$drawable.ic_wifi_signal_1, R$drawable.ic_wifi_signal_2, R$drawable.ic_wifi_signal_3, R$drawable.ic_wifi_signal_4};

    public static int applyAlpha(float f, int i) {
        return Color.argb((int) (f * Color.alpha(i)), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static Drawable getBadgedIcon(Context context, ApplicationInfo applicationInfo) {
        return getBadgedIcon(context, applicationInfo.loadUnbadgedIcon(context.getPackageManager()), UserHandle.getUserHandleForUid(applicationInfo.uid));
    }

    public static Drawable getBadgedIcon(Context context, Drawable drawable, UserHandle userHandle) {
        IconFactory iconFactoryObtain = IconFactory.obtain(context);
        try {
            FastBitmapDrawable fastBitmapDrawableNewIcon = iconFactoryObtain.createBadgedIconBitmap(drawable, new BaseIconFactory.IconOptions().setUser(userHandle)).newIcon(context, 1);
            iconFactoryObtain.close();
            return fastBitmapDrawableNewIcon;
        } catch (Throwable th) {
            if (iconFactoryObtain != null) {
                try {
                    iconFactoryObtain.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static ColorStateList getColorAccent(Context context) {
        return getColorAttr(context, R.attr.colorAccent);
    }

    public static ColorStateList getColorAttr(Context context, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        try {
            return typedArrayObtainStyledAttributes.getColorStateList(0);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static int getColorAttrDefaultColor(Context context, int i) {
        return getColorAttrDefaultColor(context, i, 0);
    }

    public static int getColorAttrDefaultColor(Context context, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int color = typedArrayObtainStyledAttributes.getColor(0, i2);
        typedArrayObtainStyledAttributes.recycle();
        return color;
    }

    public static int getThemeAttr(Context context, int i) {
        return getThemeAttr(context, i, 0);
    }

    public static int getThemeAttr(Context context, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, i2);
        typedArrayObtainStyledAttributes.recycle();
        return resourceId;
    }
}
