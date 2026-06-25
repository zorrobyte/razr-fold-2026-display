package androidx.compose.foundation.layout;

import androidx.compose.ui.node.ModifierNodeElement;

/* JADX INFO: compiled from: RowColumnImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LayoutWeightElement extends ModifierNodeElement {
    private final boolean fill;
    private final float weight;

    public LayoutWeightElement(float f, boolean z) {
        this.weight = f;
        this.fill = z;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public LayoutWeightNode create() {
        return new LayoutWeightNode(this.weight, this.fill);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        LayoutWeightElement layoutWeightElement = obj instanceof LayoutWeightElement ? (LayoutWeightElement) obj : null;
        return layoutWeightElement != null && this.weight == layoutWeightElement.weight && this.fill == layoutWeightElement.fill;
    }

    public int hashCode() {
        return (Float.hashCode(this.weight) * 31) + Boolean.hashCode(this.fill);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(LayoutWeightNode layoutWeightNode) {
        layoutWeightNode.setWeight(this.weight);
        layoutWeightNode.setFill(this.fill);
    }
}
