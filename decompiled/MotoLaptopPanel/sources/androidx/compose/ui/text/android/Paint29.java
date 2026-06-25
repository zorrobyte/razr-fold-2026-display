package androidx.compose.ui.text.android;

import android.graphics.Paint;
import android.graphics.Rect;

/* JADX INFO: compiled from: PaintExtensions.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class Paint29 {
    public static final Paint29 INSTANCE = new Paint29();

    private Paint29() {
    }

    public static final void getTextBounds(Paint paint, CharSequence charSequence, int i, int i2, Rect rect) {
        paint.getTextBounds(charSequence, i, i2, rect);
    }
}
