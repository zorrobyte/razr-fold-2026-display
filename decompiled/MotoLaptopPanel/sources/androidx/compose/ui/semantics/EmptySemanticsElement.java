package androidx.compose.ui.semantics;

import androidx.compose.ui.node.ModifierNodeElement;

/* JADX INFO: compiled from: SemanticsModifier.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public final class EmptySemanticsElement extends ModifierNodeElement {
    private final EmptySemanticsModifier node;

    public EmptySemanticsElement(EmptySemanticsModifier emptySemanticsModifier) {
        this.node = emptySemanticsModifier;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public EmptySemanticsModifier create() {
        return this.node;
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(EmptySemanticsModifier emptySemanticsModifier) {
    }
}
