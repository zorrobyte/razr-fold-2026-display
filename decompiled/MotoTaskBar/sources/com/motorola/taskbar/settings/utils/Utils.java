package com.motorola.taskbar.settings.utils;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.display.DisplayManager;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.View;
import android.view.Window;
import com.motorola.internal.app.MotoDesktopManager;
import com.motorola.taskbar.settings.MotorolaSettingsSystem;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Utils {
    public static final String TAG = "Utils";

    public static int getColorAccent(Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.colorAccent});
        int color = typedArrayObtainStyledAttributes.getColor(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return color;
    }

    public static MotorolaSettingsSystem.ConnectType getConnectType(Context context, int i) {
        Display display = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(i);
        if (display != null && i != 0) {
            return display.getType() == 2 ? MotorolaSettingsSystem.ConnectType.DP : display.getType() == 3 ? MotorolaSettingsSystem.ConnectType.MIRROR : MotorolaSettingsSystem.ConnectType.RDP;
        }
        return MotorolaSettingsSystem.ConnectType.NONE;
    }

    public static boolean isDisplayVirtual(Context context) {
        Display display = context.getDisplay();
        return display != null && display.getType() == 5;
    }

    public static boolean isHDMI(Context context, int i) {
        Display display = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(i);
        if (display == null) {
            return false;
        }
        return (display.getType() == 2 || display.getType() == 3) && i != 0;
    }

    public static boolean isMobileUIAndPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == 1 && MotoDesktopManager.isMobileUiMode(context.getDisplay());
    }

    public static boolean isOwnerUser() {
        Process.myUserHandle();
        return UserHandle.myUserId() == 0;
    }

    public static boolean isTablet(Context context) {
        DisplayInfo displayInfo = new DisplayInfo();
        if (context.getDisplay() == null) {
            return false;
        }
        context.getDisplay().getDisplayInfo(displayInfo);
        return displayInfo.name.equals("R4Tablet");
    }

    public static void rejectShowingDecorCaptionView(Window window) {
        try {
            View decorView = window.getDecorView();
            Field declaredField = decorView.getClass().getDeclaredField("mShowingDecorCaptionViewForFullscreen");
            declaredField.setAccessible(true);
            declaredField.set(decorView, Boolean.TRUE);
        } catch (Exception e) {
            Log.e(TAG, "rejectShowingDecorCaptionView: ", e);
        }
    }
}
