package androidx.compose.material3.internal;

import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.SemanticsModifierNodeKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ChildParentSemantics.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ChildSemanticsNodeElement extends ModifierNodeElement {
    private final Function1 properties;

    public ChildSemanticsNodeElement(Function1 function1) {
        this.properties = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public ChildSemanticsNode create() {
        return new ChildSemanticsNode(this.properties);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChildSemanticsNodeElement) && this.properties == ((ChildSemanticsNodeElement) obj).properties;
    }

    public int hashCode() {
        return this.properties.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(ChildSemanticsNode childSemanticsNode) {
        childSemanticsNode.setProperties(this.properties);
        SemanticsModifierNodeKt.invalidateSemantics(childSemanticsNode);
    }
}
