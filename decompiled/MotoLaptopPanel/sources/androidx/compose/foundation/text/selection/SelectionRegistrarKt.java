package androidx.compose.foundation.text.selection;

import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidableCompositionLocal;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: SelectionRegistrar.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SelectionRegistrarKt {
    private static final ProvidableCompositionLocal LocalSelectionRegistrar = CompositionLocalKt.compositionLocalOf$default(null, new Function0() { // from class: androidx.compose.foundation.text.selection.SelectionRegistrarKt$LocalSelectionRegistrar$1
        @Override // kotlin.jvm.functions.Function0
        public final SelectionRegistrar invoke() {
            return null;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Object invoke() {
            invoke();
            return null;
        }
    }, 1, null);

    public static final ProvidableCompositionLocal getLocalSelectionRegistrar() {
        return LocalSelectionRegistrar;
    }
}
