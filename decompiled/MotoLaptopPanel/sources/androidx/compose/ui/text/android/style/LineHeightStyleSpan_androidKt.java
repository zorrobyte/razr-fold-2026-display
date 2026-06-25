package androidx.compose.ui.text.android.style;

import android.graphics.Paint;

/* JADX INFO: compiled from: LineHeightStyleSpan.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LineHeightStyleSpan_androidKt {
    public static final int lineHeight(Paint.FontMetricsInt fontMetricsInt) {
        return fontMetricsInt.descent - fontMetricsInt.ascent;
    }
}
