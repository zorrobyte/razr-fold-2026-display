package androidx.window.core;

import android.util.Log;

/* JADX INFO: compiled from: SpecificationComputer.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AndroidLogger implements Logger {
    public static final AndroidLogger INSTANCE = new AndroidLogger();

    private AndroidLogger() {
    }

    @Override // androidx.window.core.Logger
    public void debug(String str, String str2) {
        str.getClass();
        str2.getClass();
        Log.d(str, str2);
    }
}
