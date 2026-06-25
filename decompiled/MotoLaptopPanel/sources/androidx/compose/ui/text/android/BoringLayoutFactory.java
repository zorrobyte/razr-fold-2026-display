package androidx.compose.ui.text.android;

import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.compose.ui.text.internal.InlineClassHelperKt;

/* JADX INFO: compiled from: BoringLayoutFactory.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BoringLayoutFactory {
    public static final BoringLayoutFactory INSTANCE = new BoringLayoutFactory();

    private BoringLayoutFactory() {
    }

    public final BoringLayout create(CharSequence charSequence, TextPaint textPaint, int i, BoringLayout.Metrics metrics, Layout.Alignment alignment, boolean z, boolean z2, TextUtils.TruncateAt truncateAt, int i2) {
        if (!(i >= 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("negative width");
        }
        if (!(i2 >= 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("negative ellipsized width");
        }
        return BoringLayoutFactory33.create(charSequence, textPaint, i, alignment, 1.0f, 0.0f, metrics, z, z2, truncateAt, i2);
    }

    public final boolean isFallbackLineSpacingEnabled(BoringLayout boringLayout) {
        return BoringLayoutFactory33.isFallbackLineSpacingEnabled(boringLayout);
    }

    public final BoringLayout.Metrics measure(CharSequence charSequence, TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic) {
        return BoringLayoutFactory33.isBoring(charSequence, textPaint, textDirectionHeuristic);
    }
}
