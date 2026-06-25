package androidx.compose.ui.semantics;

import androidx.collection.IntObjectMap;
import androidx.collection.MutableObjectList;
import androidx.compose.ui.node.LayoutNode;

/* JADX INFO: compiled from: SemanticsOwner.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SemanticsOwner {
    private final MutableObjectList listeners = new MutableObjectList(2);
    private final IntObjectMap nodes;
    private final EmptySemanticsModifier outerSemanticsNode;
    private final LayoutNode rootNode;

    public SemanticsOwner(LayoutNode layoutNode, EmptySemanticsModifier emptySemanticsModifier, IntObjectMap intObjectMap) {
        this.rootNode = layoutNode;
        this.outerSemanticsNode = emptySemanticsModifier;
        this.nodes = intObjectMap;
    }

    public final SemanticsInfo get$ui_release(int i) {
        return (SemanticsInfo) this.nodes.get(i);
    }

    public final MutableObjectList getListeners$ui_release() {
        return this.listeners;
    }

    public final SemanticsInfo getRootInfo$ui_release() {
        return this.rootNode;
    }

    public final SemanticsNode getUnmergedRootSemanticsNode() {
        return new SemanticsNode(this.outerSemanticsNode, false, this.rootNode, new SemanticsConfiguration());
    }

    public final void notifySemanticsChange$ui_release(SemanticsInfo semanticsInfo, SemanticsConfiguration semanticsConfiguration) {
        MutableObjectList mutableObjectList = this.listeners;
        Object[] objArr = mutableObjectList.content;
        int i = mutableObjectList._size;
        for (int i2 = 0; i2 < i; i2++) {
            ((SemanticsListener) objArr[i2]).onSemanticsChanged(semanticsInfo, semanticsConfiguration);
        }
    }
}
