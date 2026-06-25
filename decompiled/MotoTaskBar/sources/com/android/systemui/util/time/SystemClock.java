package com.android.systemui.util.time;

/* JADX INFO: loaded from: classes.dex */
public interface SystemClock {
    long currentTimeMillis();

    long elapsedRealtime();

    long uptimeMillis();
}
