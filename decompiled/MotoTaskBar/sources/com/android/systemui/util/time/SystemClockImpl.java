package com.android.systemui.util.time;

/* JADX INFO: loaded from: classes.dex */
public class SystemClockImpl implements SystemClock {
    @Override // com.android.systemui.util.time.SystemClock
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override // com.android.systemui.util.time.SystemClock
    public long elapsedRealtime() {
        return android.os.SystemClock.elapsedRealtime();
    }

    @Override // com.android.systemui.util.time.SystemClock
    public long uptimeMillis() {
        return android.os.SystemClock.uptimeMillis();
    }
}
