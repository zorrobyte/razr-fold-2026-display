package androidx.compose.ui;

import android.os.Handler;
import android.os.Looper;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: Actual.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Actual_androidKt {
    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static final long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static final Object postDelayed(long j, final Function0 function0) {
        Runnable runnable = new Runnable() { // from class: androidx.compose.ui.Actual_androidKt$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                function0.invoke();
            }
        };
        handler.postDelayed(runnable, j);
        return runnable;
    }

    public static final void removePost(Object obj) {
        if ((obj instanceof Runnable ? (Runnable) obj : null) == null) {
            return;
        }
        handler.removeCallbacks((Runnable) obj);
    }
}
