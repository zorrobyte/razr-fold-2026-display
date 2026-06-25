package androidx.compose.ui.node;

/* JADX INFO: compiled from: ParentDataModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ParentDataModifierNodeKt {
    public static final void invalidateParentData(ParentDataModifierNode parentDataModifierNode) {
        DelegatableNodeKt.requireLayoutNode(parentDataModifierNode).invalidateParentData$ui_release();
    }
}
