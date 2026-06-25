package com.motorola.taskbar.utils;

import android.app.StatusBarManager;
import android.view.Display;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ExtendedFunction {
    public static Display.Mode[] getDesktopSupportedModes(Display display) {
        return display.getDesktopSupportedModes();
    }

    public static void refreshDesktopIcons(StatusBarManager statusBarManager, int i) {
        statusBarManager.refreshDesktopIcons(i);
    }
}
