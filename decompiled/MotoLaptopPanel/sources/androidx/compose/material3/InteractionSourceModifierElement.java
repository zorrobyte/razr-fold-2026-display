package androidx.compose.material3;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InteractionSourceModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
final class InteractionSourceModifierElement extends ModifierNodeElement {
    private final MutableInteractionSource interactionSource;

    public InteractionSourceModifierElement(MutableInteractionSource mutableInteractionSource) {
        this.interactionSource = mutableInteractionSource;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public InteractionSourceModifierNode create() {
        return new InteractionSourceModifierNode(this.interactionSource);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof InteractionSourceModifierElement) && Intrinsics.areEqual(this.interactionSource, ((InteractionSourceModifierElement) obj).interactionSource);
    }

    public int hashCode() {
        return this.interactionSource.hashCode();
    }

    public String toString() {
        return "InteractionSourceModifierElement(interactionSource=" + this.interactionSource + ')';
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(InteractionSourceModifierNode interactionSourceModifierNode) {
        interactionSourceModifierNode.setInteractionSource(this.interactionSource);
    }
}
