package androidx.compose.ui.focus;

import androidx.compose.ui.node.DelegatableNode;

/* JADX INFO: compiled from: FocusTargetModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public interface FocusTargetModifierNode extends DelegatableNode {
    /* JADX INFO: renamed from: requestFocus-3ESFkO8$default, reason: not valid java name */
    static /* synthetic */ boolean m714requestFocus3ESFkO8$default(FocusTargetModifierNode focusTargetModifierNode, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: requestFocus-3ESFkO8");
        }
        if ((i2 & 1) != 0) {
            i = FocusDirection.Companion.m692getEnterdhqQ8s();
        }
        return focusTargetModifierNode.mo715requestFocus3ESFkO8(i);
    }

    FocusState getFocusState();

    /* JADX INFO: renamed from: requestFocus-3ESFkO8, reason: not valid java name */
    boolean mo715requestFocus3ESFkO8(int i);
}
