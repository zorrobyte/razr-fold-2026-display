package com.android.systemui.flags;

import android.os.Build;
import android.util.Log;

/* JADX INFO: compiled from: RefactorFlagUtils.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RefactorFlagUtils {
    public static final RefactorFlagUtils INSTANCE = new RefactorFlagUtils();

    private RefactorFlagUtils() {
    }

    public final void assertOnEngBuild(String str) {
        str.getClass();
        if (Log.isLoggable("RefactorFlagAssert", 7)) {
            Log.wtf("RefactorFlagAssert", str, Build.isDebuggable() ? new IllegalStateException(str) : null);
        } else if (Log.isLoggable("RefactorFlag", 5)) {
            Log.w("RefactorFlag", str);
        }
    }
}
