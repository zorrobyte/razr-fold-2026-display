package androidx.core.os;

import android.os.Trace;

/* JADX INFO: loaded from: classes.dex */
public abstract class TraceCompat {

    abstract class Api29Impl {
        static boolean isEnabled() {
            return Trace.isEnabled();
        }
    }

    public static void beginSection(String str) {
        Trace.beginSection(str);
    }

    public static void endSection() {
        Trace.endSection();
    }

    public static boolean isEnabled() {
        return Api29Impl.isEnabled();
    }
}
