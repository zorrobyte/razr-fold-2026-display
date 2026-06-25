package androidx.compose.ui.layout;

import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: LayoutModifier.kt */
/* JADX INFO: loaded from: classes.dex */
final class LayoutElement extends ModifierNodeElement {
    private final Function3 measure;

    public LayoutElement(Function3 function3) {
        this.measure = function3;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public LayoutModifierImpl create() {
        return new LayoutModifierImpl(this.measure);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LayoutElement) && this.measure == ((LayoutElement) obj).measure;
    }

    public int hashCode() {
        return this.measure.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(LayoutModifierImpl layoutModifierImpl) {
        layoutModifierImpl.setMeasureBlock(this.measure);
    }
}
