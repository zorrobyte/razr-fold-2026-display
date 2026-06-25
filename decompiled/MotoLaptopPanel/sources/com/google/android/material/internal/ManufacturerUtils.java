package com.google.android.material.internal;

import android.os.Build;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public abstract class ManufacturerUtils {
    private static String getManufacturer() {
        String str = Build.MANUFACTURER;
        return str != null ? str.toLowerCase(Locale.ENGLISH) : "";
    }

    public static boolean isMeizuDevice() {
        return getManufacturer().equals("meizu");
    }
}
