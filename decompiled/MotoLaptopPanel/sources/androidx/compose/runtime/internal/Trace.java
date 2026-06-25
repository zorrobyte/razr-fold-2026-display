package androidx.compose.runtime.internal;

/* JADX INFO: compiled from: Trace.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Trace {
    public static final Trace INSTANCE = new Trace();

    private Trace() {
    }

    public final Object beginSection(String str) {
        android.os.Trace.beginSection(str);
        return null;
    }

    public final void endSection(Object obj) {
        android.os.Trace.endSection();
    }
}
