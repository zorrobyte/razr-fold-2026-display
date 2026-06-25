package androidx.compose.material3;

import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: ContentColor.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ContentColorKt {
    private static final ProvidableCompositionLocal LocalContentColor = CompositionLocalKt.compositionLocalOf$default(null, new Function0() { // from class: androidx.compose.material3.ContentColorKt$LocalContentColor$1
        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Object invoke() {
            return Color.m876boximpl(m288invoke0d7_KjU());
        }

        /* JADX INFO: renamed from: invoke-0d7_KjU, reason: not valid java name */
        public final long m288invoke0d7_KjU() {
            return Color.Companion.m891getBlack0d7_KjU();
        }
    }, 1, null);

    public static final ProvidableCompositionLocal getLocalContentColor() {
        return LocalContentColor;
    }
}
