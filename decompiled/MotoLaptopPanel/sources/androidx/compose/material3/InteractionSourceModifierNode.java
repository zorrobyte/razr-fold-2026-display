package androidx.compose.material3;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.TraversableNode;

/* JADX INFO: compiled from: InteractionSourceModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
final class InteractionSourceModifierNode extends Modifier.Node implements TraversableNode {
    private MutableInteractionSource interactionSource;
    private final Object traverseKey = InteractionSourceModifierNodeTraverseKey.INSTANCE;

    public InteractionSourceModifierNode(MutableInteractionSource mutableInteractionSource) {
        this.interactionSource = mutableInteractionSource;
    }

    @Override // androidx.compose.ui.node.TraversableNode
    public Object getTraverseKey() {
        return this.traverseKey;
    }

    public final void setInteractionSource(MutableInteractionSource mutableInteractionSource) {
        this.interactionSource = mutableInteractionSource;
    }
}
