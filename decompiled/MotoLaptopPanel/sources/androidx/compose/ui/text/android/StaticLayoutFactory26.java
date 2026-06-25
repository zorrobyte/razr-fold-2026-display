package androidx.compose.ui.text.android;

import android.text.StaticLayout;

/* JADX INFO: compiled from: StaticLayoutFactory.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class StaticLayoutFactory26 {
    public static final StaticLayoutFactory26 INSTANCE = new StaticLayoutFactory26();

    private StaticLayoutFactory26() {
    }

    public static final void setJustificationMode(StaticLayout.Builder builder, int i) {
        builder.setJustificationMode(i);
    }
}
