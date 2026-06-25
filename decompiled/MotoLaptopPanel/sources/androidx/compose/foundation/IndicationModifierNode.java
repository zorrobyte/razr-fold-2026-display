package androidx.compose.foundation;

import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatingNode;

/* JADX INFO: compiled from: Indication.kt */
/* JADX INFO: loaded from: classes.dex */
final class IndicationModifierNode extends DelegatingNode {
    private DelegatableNode indicationNode;

    public IndicationModifierNode(DelegatableNode delegatableNode) {
        this.indicationNode = delegatableNode;
        delegate(delegatableNode);
    }

    public final void update(DelegatableNode delegatableNode) {
        undelegate(this.indicationNode);
        this.indicationNode = delegatableNode;
        delegate(delegatableNode);
    }
}
