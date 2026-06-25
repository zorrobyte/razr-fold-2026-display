package com.android.launcher3.util;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public abstract class Preconditions {
    public static final boolean IS_DEBUG_DEVICE;

    /* JADX WARN: Removed duplicated region for block: B:12:0x0039  */
    static {
        /*
            java.lang.String r0 = android.os.Build.TYPE
            java.util.Locale r1 = java.util.Locale.ROOT
            java.lang.String r2 = r0.toLowerCase(r1)
            java.lang.String r3 = "debug"
            boolean r2 = r2.contains(r3)
            if (r2 != 0) goto L39
            java.lang.String r0 = r0.toLowerCase(r1)
            java.lang.String r2 = "eng"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L39
            java.lang.String r0 = android.os.Build.TAGS
            java.lang.String r2 = r0.toLowerCase(r1)
            java.lang.String r3 = "intcfg"
            boolean r2 = r2.contains(r3)
            if (r2 != 0) goto L39
            java.lang.String r0 = r0.toLowerCase(r1)
            java.lang.String r1 = "bldccfg"
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L37
            goto L39
        L37:
            r0 = 0
            goto L3a
        L39:
            r0 = 1
        L3a:
            com.android.launcher3.util.Preconditions.IS_DEBUG_DEVICE = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.launcher3.util.Preconditions.<clinit>():void");
    }

    public static void assertUIThread() {
        if (IS_DEBUG_DEVICE && !isSameLooper(Looper.getMainLooper())) {
            throw new IllegalStateException();
        }
    }

    private static boolean isSameLooper(Looper looper) {
        return Looper.myLooper() == looper;
    }
}
