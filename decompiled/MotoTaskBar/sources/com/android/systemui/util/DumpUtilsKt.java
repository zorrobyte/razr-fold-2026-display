package com.android.systemui.util;

/* JADX INFO: compiled from: DumpUtils.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DumpUtilsKt {
    public static final String visibilityString(int i) {
        if (i == 0) {
            return "visible";
        }
        if (i == 4) {
            return "invisible";
        }
        if (i == 8) {
            return "gone";
        }
        return "unknown:" + i;
    }
}
