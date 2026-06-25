package com.motorola.taskbar.settings;

import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public abstract class WindowManagerUtil {
    private static boolean sIsInitialized = false;
    private static Method sSetFontScaleMethod;
    static final IWindowManager wm;

    static {
        IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
        wm = windowManagerService;
        try {
            sSetFontScaleMethod = windowManagerService.getClass().getDeclaredMethod("setFontScale", Integer.TYPE, Float.TYPE);
            sIsInitialized = true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void setFontScale(int i, float f) {
        if (sIsInitialized) {
            try {
                sSetFontScaleMethod.invoke(wm, Integer.valueOf(i), Float.valueOf(f));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
