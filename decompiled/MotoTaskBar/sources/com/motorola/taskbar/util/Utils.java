package com.motorola.taskbar.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Utils {
    public static Context createDisplayContext(Context context, int i) {
        Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(i);
        return display == null ? context : context.createDisplayContext(display);
    }

    public static int getColorAttrDefaultColor(Context context, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int color = typedArrayObtainStyledAttributes.getColor(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return color;
    }

    public static int getColorStateListDefaultColor(Context context, int i) {
        return context.getResources().getColorStateList(i, context.getTheme()).getDefaultColor();
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

    public static void rejectHideTaskbar(Window window) {
        try {
            window.getDecorView().setSystemUiVisibility((window.getDecorView().getSystemUiVisibility() & (-4099)) | 64);
        } catch (Exception e) {
            Log.e("DecorView", "rejectHideTaskbar: ", e);
        }
    }

    public static void rejectShowingDecorCaptionView(Window window) {
        try {
            View decorView = window.getDecorView();
            Field declaredField = decorView.getClass().getDeclaredField("mShowingDecorCaptionViewForFullscreen");
            declaredField.setAccessible(true);
            declaredField.set(decorView, Boolean.TRUE);
        } catch (Exception e) {
            Log.e("DecorView", "rejectShowingDecorCaptionView: ", e);
        }
    }
}
