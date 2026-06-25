package androidx.compose.material3;

import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidableCompositionLocal;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: MotionScheme.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MotionSchemeKt {
    private static final ProvidableCompositionLocal LocalMotionScheme = CompositionLocalKt.staticCompositionLocalOf(new Function0() { // from class: androidx.compose.material3.MotionSchemeKt$LocalMotionScheme$1
        @Override // kotlin.jvm.functions.Function0
        public final MotionScheme invoke() {
            return MotionScheme.Companion.standard();
        }
    });

    public static final ProvidableCompositionLocal getLocalMotionScheme() {
        return LocalMotionScheme;
    }
}
