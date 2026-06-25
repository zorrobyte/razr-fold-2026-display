package androidx.compose.ui.layout;

import androidx.compose.ui.modifier.ModifierLocalKt;
import androidx.compose.ui.modifier.ProvidableModifierLocal;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: BeyondBoundsLayout.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class BeyondBoundsLayoutKt {
    private static final ProvidableModifierLocal ModifierLocalBeyondBoundsLayout = ModifierLocalKt.modifierLocalOf(new Function0() { // from class: androidx.compose.ui.layout.BeyondBoundsLayoutKt$ModifierLocalBeyondBoundsLayout$1
        @Override // kotlin.jvm.functions.Function0
        public final BeyondBoundsLayout invoke() {
            return null;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Object invoke() {
            invoke();
            return null;
        }
    });

    public static final ProvidableModifierLocal getModifierLocalBeyondBoundsLayout() {
        return ModifierLocalBeyondBoundsLayout;
    }
}
