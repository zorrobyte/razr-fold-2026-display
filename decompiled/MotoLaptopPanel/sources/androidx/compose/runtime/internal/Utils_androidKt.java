package androidx.compose.runtime.internal;

import android.util.Log;

/* JADX INFO: compiled from: Utils.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Utils_androidKt {
    public static final void logError(String str, Throwable th) {
        Log.e("ComposeInternal", str, th);
    }
}
