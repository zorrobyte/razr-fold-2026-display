package androidx.compose.foundation;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Focusable.kt */
/* JADX INFO: loaded from: classes.dex */
final class FocusableElement extends ModifierNodeElement {
    private final MutableInteractionSource interactionSource;

    public FocusableElement(MutableInteractionSource mutableInteractionSource) {
        this.interactionSource = mutableInteractionSource;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public FocusableNode create() {
        return new FocusableNode(this.interactionSource, 0, null, 6, null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FocusableElement) && Intrinsics.areEqual(this.interactionSource, ((FocusableElement) obj).interactionSource);
    }

    public int hashCode() {
        MutableInteractionSource mutableInteractionSource = this.interactionSource;
        if (mutableInteractionSource != null) {
            return mutableInteractionSource.hashCode();
        }
        return 0;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(FocusableNode focusableNode) {
        focusableNode.update(this.interactionSource);
    }
}
