package androidx.compose.ui.text.android.style;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/* JADX INFO: compiled from: SkewXSpan.android.kt */
/* JADX INFO: loaded from: classes.dex */
public class SkewXSpan extends MetricAffectingSpan {
    private final float skewX;

    public SkewXSpan(float f) {
        this.skewX = f;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setTextSkewX(this.skewX + textPaint.getTextSkewX());
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(TextPaint textPaint) {
        textPaint.setTextSkewX(this.skewX + textPaint.getTextSkewX());
    }
}
