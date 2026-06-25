package androidx.compose.ui.text.font;

import android.content.Context;

/* JADX INFO: compiled from: AndroidFontResolveInterceptor.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FontWeightAdjustmentHelper {
    public static final FontWeightAdjustmentHelper INSTANCE = new FontWeightAdjustmentHelper();

    private FontWeightAdjustmentHelper() {
    }

    public final int getFontWeightAdjustment(Context context) {
        return FontWeightAdjustmentHelperApi31.INSTANCE.fontWeightAdjustment(context);
    }
}
