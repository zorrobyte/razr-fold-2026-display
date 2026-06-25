package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ParentDataModifierNode;
import androidx.compose.ui.unit.Density;

/* JADX INFO: compiled from: Box.kt */
/* JADX INFO: loaded from: classes.dex */
final class BoxChildDataNode extends Modifier.Node implements ParentDataModifierNode {
    private Alignment alignment;
    private boolean matchParentSize;

    public BoxChildDataNode(Alignment alignment, boolean z) {
        this.alignment = alignment;
        this.matchParentSize = z;
    }

    public final Alignment getAlignment() {
        return this.alignment;
    }

    public final boolean getMatchParentSize() {
        return this.matchParentSize;
    }

    @Override // androidx.compose.ui.node.ParentDataModifierNode
    public BoxChildDataNode modifyParentData(Density density, Object obj) {
        return this;
    }

    public final void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public final void setMatchParentSize(boolean z) {
        this.matchParentSize = z;
    }
}
