package androidx.compose.ui.text.android;

import android.text.StaticLayout;

/* JADX INFO: compiled from: StaticLayoutFactory.android.kt */
/* JADX INFO: loaded from: classes.dex */
interface StaticLayoutFactoryImpl {
    StaticLayout create(StaticLayoutParams staticLayoutParams);

    boolean isFallbackLineSpacingEnabled(StaticLayout staticLayout, boolean z);
}
