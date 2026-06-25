package androidx.compose.ui.text.android;

/* JADX INFO: compiled from: TextLayout.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class VerticalPaddings {
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1619constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: getBottomPadding-impl, reason: not valid java name */
    public static final int m1620getBottomPaddingimpl(long j) {
        return (int) (j & 4294967295L);
    }

    /* JADX INFO: renamed from: getTopPadding-impl, reason: not valid java name */
    public static final int m1621getTopPaddingimpl(long j) {
        return (int) (j >> 32);
    }
}
