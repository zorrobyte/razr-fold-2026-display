package com.android.systemui.shared.recents.utilities;

/* JADX INFO: loaded from: classes.dex */
public abstract class Utilities {
    public static float clamp(float f, float f2, float f3) {
        return Math.max(f2, Math.min(f3, f));
    }
}
