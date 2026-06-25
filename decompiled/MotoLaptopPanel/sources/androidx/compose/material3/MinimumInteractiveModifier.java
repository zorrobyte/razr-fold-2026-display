package androidx.compose.material3;

import androidx.compose.material3.internal.System_jvmKt;
import androidx.compose.ui.node.ModifierNodeElement;

/* JADX INFO: compiled from: InteractiveComponentSize.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MinimumInteractiveModifier extends ModifierNodeElement {
    public static final MinimumInteractiveModifier INSTANCE = new MinimumInteractiveModifier();

    private MinimumInteractiveModifier() {
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public MinimumInteractiveModifierNode create() {
        return new MinimumInteractiveModifierNode();
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public int hashCode() {
        return System_jvmKt.identityHashCode(this);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(MinimumInteractiveModifierNode minimumInteractiveModifierNode) {
    }
}
