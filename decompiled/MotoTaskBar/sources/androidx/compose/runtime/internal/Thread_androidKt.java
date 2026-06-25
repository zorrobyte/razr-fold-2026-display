package androidx.compose.runtime.internal;

import android.os.Looper;

/* JADX INFO: compiled from: Thread.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Thread_androidKt {
    private static final long MainThreadId;

    static {
        long id;
        try {
            id = Looper.getMainLooper().getThread().getId();
        } catch (Exception unused) {
            id = -1;
        }
        MainThreadId = id;
    }

    public static final long getMainThreadId() {
        return MainThreadId;
    }
}
