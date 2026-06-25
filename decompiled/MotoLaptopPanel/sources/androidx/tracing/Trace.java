package androidx.tracing;

/* JADX INFO: compiled from: Trace.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Trace {
    public static final Trace INSTANCE = new Trace();

    private Trace() {
    }

    public static final void beginSection(String str) {
        str.getClass();
        android.os.Trace.beginSection(INSTANCE.truncatedTraceSectionLabel(str));
    }

    public static final void endSection() {
        android.os.Trace.endSection();
    }

    public static final boolean isEnabled() {
        return TraceApi29Impl.INSTANCE.isEnabled();
    }

    private final String truncatedTraceSectionLabel(String str) {
        String str2 = str.length() <= 127 ? str : null;
        if (str2 != null) {
            return str2;
        }
        String strSubstring = str.substring(0, 127);
        strSubstring.getClass();
        return strSubstring;
    }
}
