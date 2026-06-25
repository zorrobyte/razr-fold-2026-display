package com.motorola.taskbar.qs;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.Display;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.util.DebugConfig;

/* JADX INFO: loaded from: classes2.dex */
public abstract class PanelSizes {
    private static final boolean DEBUG = DebugConfig.DEBUG_QS_PANEL;
    private static SparseIntArray sTaskbarHeights = new SparseIntArray();

    public static int[] getMirrorPhoneSize(Context context) {
        return getMirrorPhoneSizeByExtraDisplayPrecent(context);
    }

    private static int[] getMirrorPhoneSizeByExtraDisplayPrecent(Context context) {
        int i;
        int i2;
        int dimensionPixelSize;
        int i3;
        int i4;
        int i5;
        int dimensionPixelSize2;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        Display display = context.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        boolean z = displayMetrics.widthPixels > displayMetrics.heightPixels;
        Display display2 = displayManager.getDisplay(0);
        DisplayMetrics displayMetrics2 = new DisplayMetrics();
        display2.getRealMetrics(displayMetrics2);
        int i6 = displayMetrics2.widthPixels;
        int i7 = displayMetrics2.heightPixels;
        boolean z2 = i6 > i7;
        if (z && z2) {
            i4 = (int) (displayMetrics.widthPixels * 0.5714286f);
            i5 = (i7 * i4) / i6;
            dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R$dimen.mirror_phone_panel_title_bar_height);
        } else {
            if (!z2 || z) {
                if (z2 || !z) {
                    i = (int) (displayMetrics.heightPixels * 0.5f);
                    i2 = (i6 * i) / i7;
                    dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.mirror_phone_panel_title_bar_height);
                } else {
                    i = (int) (displayMetrics.heightPixels * 0.71428573f);
                    i2 = (i6 * i) / i7;
                    dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.mirror_phone_panel_title_bar_height);
                }
                i3 = i + dimensionPixelSize;
                i4 = i2;
                return new int[]{i4, i3};
            }
            i4 = (int) (displayMetrics.widthPixels * 0.71428573f);
            i5 = (i7 * i4) / i6;
            dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R$dimen.mirror_phone_panel_title_bar_height);
        }
        i3 = i5 + dimensionPixelSize2;
        return new int[]{i4, i3};
    }

    public static int getTaskbarCacheHeight(int i) {
        return sTaskbarHeights.get(i);
    }

    public static int getTaskbarHeight(int i, Context context) {
        int i2 = sTaskbarHeights.get(i);
        if (i2 > 0) {
            return i2;
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.task_bar_height);
        setTaskbarHeight(i, dimensionPixelSize);
        return dimensionPixelSize;
    }

    public static void setTaskbarHeight(int i, int i2) {
        if (i2 <= 0) {
            return;
        }
        sTaskbarHeights.put(i, i2);
    }
}
