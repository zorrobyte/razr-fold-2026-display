package t;

import android.text.PrecomputedText;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;
import java.util.Objects;

/* JADX INFO: renamed from: t.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0159a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final TextPaint f2819a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final TextDirectionHeuristic f2820b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f2821c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final int f2822d;

    public C0159a(PrecomputedText.Params params) {
        this.f2819a = params.getTextPaint();
        this.f2820b = params.getTextDirection();
        this.f2821c = params.getBreakStrategy();
        this.f2822d = params.getHyphenationFrequency();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C0159a)) {
            return false;
        }
        C0159a c0159a = (C0159a) obj;
        if (this.f2821c == c0159a.f2821c && this.f2822d == c0159a.f2822d) {
            TextPaint textPaint = this.f2819a;
            float textSize = textPaint.getTextSize();
            TextPaint textPaint2 = c0159a.f2819a;
            return textSize == textPaint2.getTextSize() && textPaint.getTextScaleX() == textPaint2.getTextScaleX() && textPaint.getTextSkewX() == textPaint2.getTextSkewX() && textPaint.getLetterSpacing() == textPaint2.getLetterSpacing() && TextUtils.equals(textPaint.getFontFeatureSettings(), textPaint2.getFontFeatureSettings()) && textPaint.getFlags() == textPaint2.getFlags() && textPaint.getTextLocales().equals(textPaint2.getTextLocales()) && (textPaint.getTypeface() != null ? textPaint.getTypeface().equals(textPaint2.getTypeface()) : textPaint2.getTypeface() == null) && this.f2820b == c0159a.f2820b;
        }
        return false;
    }

    public final int hashCode() {
        TextPaint textPaint = this.f2819a;
        return Objects.hash(Float.valueOf(textPaint.getTextSize()), Float.valueOf(textPaint.getTextScaleX()), Float.valueOf(textPaint.getTextSkewX()), Float.valueOf(textPaint.getLetterSpacing()), Integer.valueOf(textPaint.getFlags()), textPaint.getTextLocales(), textPaint.getTypeface(), Boolean.valueOf(textPaint.isElegantTextHeight()), this.f2820b, Integer.valueOf(this.f2821c), Integer.valueOf(this.f2822d));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        StringBuilder sb2 = new StringBuilder("textSize=");
        TextPaint textPaint = this.f2819a;
        sb2.append(textPaint.getTextSize());
        sb.append(sb2.toString());
        sb.append(", textScaleX=" + textPaint.getTextScaleX());
        sb.append(", textSkewX=" + textPaint.getTextSkewX());
        sb.append(", letterSpacing=" + textPaint.getLetterSpacing());
        sb.append(", elegantTextHeight=" + textPaint.isElegantTextHeight());
        sb.append(", textLocale=" + textPaint.getTextLocales());
        sb.append(", typeface=" + textPaint.getTypeface());
        sb.append(", variationSettings=" + textPaint.getFontVariationSettings());
        sb.append(", textDir=" + this.f2820b);
        sb.append(", breakStrategy=" + this.f2821c);
        sb.append(", hyphenationFrequency=" + this.f2822d);
        sb.append("}");
        return sb.toString();
    }
}
