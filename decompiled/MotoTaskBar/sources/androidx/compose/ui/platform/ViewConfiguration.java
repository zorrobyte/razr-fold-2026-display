package androidx.compose.ui.platform;

import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;

/* JADX INFO: compiled from: ViewConfiguration.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ViewConfiguration {
    /* JADX INFO: renamed from: getMinimumTouchTargetSize-MYxV2XQ */
    default long mo585getMinimumTouchTargetSizeMYxV2XQ() {
        float f = 48;
        return DpKt.m990DpSizeYgX7TsA(Dp.m989constructorimpl(f), Dp.m989constructorimpl(f));
    }
}
