package androidx.tracing;

/* JADX INFO: compiled from: TraceApi29Impl.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TraceApi29Impl {
    public static final TraceApi29Impl INSTANCE = new TraceApi29Impl();

    private TraceApi29Impl() {
    }

    public final boolean isEnabled() {
        return android.os.Trace.isEnabled();
    }
}
