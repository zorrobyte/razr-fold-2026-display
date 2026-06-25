package androidx.compose.runtime.tooling;

import androidx.compose.runtime.CompositionLocal;
import androidx.compose.runtime.CompositionLocalKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: CompositionErrorContext.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CompositionErrorContextKt {
    private static final CompositionLocal LocalCompositionErrorContext = CompositionLocalKt.staticCompositionLocalOf(new Function0() { // from class: androidx.compose.runtime.tooling.CompositionErrorContextKt$LocalCompositionErrorContext$1
        @Override // kotlin.jvm.functions.Function0
        public final CompositionErrorContext invoke() {
            return null;
        }
    });

    public static final CompositionLocal getLocalCompositionErrorContext() {
        return LocalCompositionErrorContext;
    }
}
