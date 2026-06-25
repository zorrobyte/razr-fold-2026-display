package androidx.compose.ui.text.android;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;

/* JADX INFO: compiled from: StaticLayoutFactory.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class StaticLayoutFactory {
    public static final StaticLayoutFactory INSTANCE = new StaticLayoutFactory();
    private static final StaticLayoutFactoryImpl delegate = new StaticLayoutFactory23();
    public static final int $stable = 8;

    private StaticLayoutFactory() {
    }

    public static /* synthetic */ StaticLayout create$default(StaticLayoutFactory staticLayoutFactory, CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3, TextDirectionHeuristic textDirectionHeuristic, Layout.Alignment alignment, int i4, TextUtils.TruncateAt truncateAt, int i5, float f, float f2, int i6, boolean z, boolean z2, int i7, int i8, int i9, int i10, int[] iArr, int[] iArr2, int i11, Object obj) {
        int[] iArr3;
        StaticLayoutFactory staticLayoutFactory2;
        CharSequence charSequence2;
        TextPaint textPaint2;
        int i12;
        int i13 = (i11 & 8) != 0 ? 0 : i2;
        int length = (i11 & 16) != 0 ? charSequence.length() : i3;
        TextDirectionHeuristic dEFAULT_TEXT_DIRECTION_HEURISTIC$ui_text_release = (i11 & 32) != 0 ? LayoutCompat.INSTANCE.getDEFAULT_TEXT_DIRECTION_HEURISTIC$ui_text_release() : textDirectionHeuristic;
        Layout.Alignment dEFAULT_LAYOUT_ALIGNMENT$ui_text_release = (i11 & 64) != 0 ? LayoutCompat.INSTANCE.getDEFAULT_LAYOUT_ALIGNMENT$ui_text_release() : alignment;
        int i14 = (i11 & 128) != 0 ? Integer.MAX_VALUE : i4;
        TextUtils.TruncateAt truncateAt2 = (i11 & 256) != 0 ? null : truncateAt;
        int i15 = (i11 & 512) != 0 ? i : i5;
        float f3 = (i11 & 1024) != 0 ? 1.0f : f;
        float f4 = (i11 & 2048) != 0 ? 0.0f : f2;
        int i16 = (i11 & 4096) != 0 ? 0 : i6;
        boolean z3 = (i11 & 8192) != 0 ? false : z;
        boolean z4 = (i11 & 16384) != 0 ? true : z2;
        int i17 = (32768 & i11) != 0 ? 0 : i7;
        int i18 = (65536 & i11) != 0 ? 0 : i8;
        int i19 = (131072 & i11) != 0 ? 0 : i9;
        int i20 = (262144 & i11) != 0 ? 0 : i10;
        int[] iArr4 = (524288 & i11) != 0 ? null : iArr;
        if ((i11 & 1048576) != 0) {
            iArr3 = null;
            charSequence2 = charSequence;
            textPaint2 = textPaint;
            i12 = i;
            staticLayoutFactory2 = staticLayoutFactory;
        } else {
            iArr3 = iArr2;
            staticLayoutFactory2 = staticLayoutFactory;
            charSequence2 = charSequence;
            textPaint2 = textPaint;
            i12 = i;
        }
        return staticLayoutFactory2.create(charSequence2, textPaint2, i12, i13, length, dEFAULT_TEXT_DIRECTION_HEURISTIC$ui_text_release, dEFAULT_LAYOUT_ALIGNMENT$ui_text_release, i14, truncateAt2, i15, f3, f4, i16, z3, z4, i17, i18, i19, i20, iArr4, iArr3);
    }

    public final StaticLayout create(CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3, TextDirectionHeuristic textDirectionHeuristic, Layout.Alignment alignment, int i4, TextUtils.TruncateAt truncateAt, int i5, float f, float f2, int i6, boolean z, boolean z2, int i7, int i8, int i9, int i10, int[] iArr, int[] iArr2) {
        return delegate.create(new StaticLayoutParams(charSequence, i2, i3, textPaint, i, textDirectionHeuristic, alignment, i4, truncateAt, i5, f, f2, i6, z, z2, i7, i8, i9, i10, iArr, iArr2));
    }

    public final boolean isFallbackLineSpacingEnabled(StaticLayout staticLayout, boolean z) {
        return delegate.isFallbackLineSpacingEnabled(staticLayout, z);
    }
}
