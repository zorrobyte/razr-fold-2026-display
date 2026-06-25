package androidx.compose.ui.text.android;

import android.text.Spanned;

/* JADX INFO: compiled from: SpannedExtensions.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SpannedExtensions_androidKt {
    public static final boolean hasSpan(Spanned spanned, Class cls) {
        return spanned.nextSpanTransition(-1, spanned.length(), cls) != spanned.length();
    }

    public static final boolean hasSpan(Spanned spanned, Class cls, int i, int i2) {
        return spanned.nextSpanTransition(i - 1, i2, cls) != i2;
    }
}
