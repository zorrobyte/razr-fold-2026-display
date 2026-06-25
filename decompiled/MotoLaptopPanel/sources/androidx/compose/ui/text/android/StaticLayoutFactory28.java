package androidx.compose.ui.text.android;

import android.text.StaticLayout;

/* JADX INFO: compiled from: StaticLayoutFactory.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class StaticLayoutFactory28 {
    public static final StaticLayoutFactory28 INSTANCE = new StaticLayoutFactory28();

    private StaticLayoutFactory28() {
    }

    public static final void setUseLineSpacingFromFallbacks(StaticLayout.Builder builder, boolean z) {
        builder.setUseLineSpacingFromFallbacks(z);
    }
}
