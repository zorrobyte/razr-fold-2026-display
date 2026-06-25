package androidx.compose.ui.input.pointer;

import androidx.collection.LongSparseArray;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.util.PointerIdArray;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.NodeKind;
import androidx.compose.ui.node.PointerInputModifierNode;
import java.util.List;

/* JADX INFO: compiled from: HitPathTracker.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Node extends NodeParent {
    private LayoutCoordinates coordinates;
    private final Modifier.Node modifierNode;
    private PointerEvent pointerEvent;
    private boolean wasIn;
    private final PointerIdArray pointerIds = new PointerIdArray();
    private final LongSparseArray relevantChanges = new LongSparseArray(2);
    private boolean isIn = true;
    private boolean hasExited = true;

    public Node(Modifier.Node node) {
        this.modifierNode = node;
    }

    private final void clearCache() {
        this.relevantChanges.clear();
        this.coordinates = null;
    }

    private final boolean hasPositionChanged(PointerEvent pointerEvent, PointerEvent pointerEvent2) {
        if (pointerEvent == null || pointerEvent.getChanges().size() != pointerEvent2.getChanges().size()) {
            return true;
        }
        int size = pointerEvent2.getChanges().size();
        for (int i = 0; i < size; i++) {
            if (!Offset.m186equalsimpl0(((PointerInputChange) pointerEvent.getChanges().get(i)).m497getPositionF1C5BW0(), ((PointerInputChange) pointerEvent2.getChanges().get(i)).m497getPositionF1C5BW0())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0217  */
    @Override // androidx.compose.ui.input.pointer.NodeParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean buildCache(androidx.collection.LongSparseArray r44, androidx.compose.ui.layout.LayoutCoordinates r45, androidx.compose.ui.input.pointer.InternalPointerEvent r46, boolean r47) {
        /*
            Method dump skipped, instruction units count: 634
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.Node.buildCache(androidx.collection.LongSparseArray, androidx.compose.ui.layout.LayoutCoordinates, androidx.compose.ui.input.pointer.InternalPointerEvent, boolean):boolean");
    }

    @Override // androidx.compose.ui.input.pointer.NodeParent
    public void cleanUpHits(InternalPointerEvent internalPointerEvent) {
        super.cleanUpHits(internalPointerEvent);
        PointerEvent pointerEvent = this.pointerEvent;
        if (pointerEvent == null) {
            return;
        }
        this.wasIn = this.isIn;
        List changes = pointerEvent.getChanges();
        int size = changes.size();
        for (int i = 0; i < size; i++) {
            PointerInputChange pointerInputChange = (PointerInputChange) changes.get(i);
            boolean pressed = pointerInputChange.getPressed();
            boolean zM471activeHoverEvent0FcD4WY = internalPointerEvent.m471activeHoverEvent0FcD4WY(pointerInputChange.m496getIdJ3iCeTQ());
            boolean z = this.isIn;
            if ((!pressed && !zM471activeHoverEvent0FcD4WY) || (!pressed && !z)) {
                this.pointerIds.remove(pointerInputChange.m496getIdJ3iCeTQ());
            }
        }
        this.isIn = false;
        this.hasExited = PointerEventType.m481equalsimpl0(pointerEvent.m477getType7fucELk(), PointerEventType.Companion.m483getExit7fucELk());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.compose.ui.input.pointer.NodeParent
    public void dispatchCancel() {
        MutableVector children = getChildren();
        Object[] objArr = children.content;
        int size = children.getSize();
        for (int i = 0; i < size; i++) {
            ((Node) objArr[i]).dispatchCancel();
        }
        Modifier.Node node = this.modifierNode;
        NodeKind.m658constructorimpl(16);
        for (Modifier.Node nodePop = node; nodePop != 0; nodePop = DelegatableNodeKt.pop(null)) {
            if (nodePop instanceof PointerInputModifierNode) {
                ((PointerInputModifierNode) nodePop).onCancelPointerInput();
            } else {
                nodePop.getKindSet$ui_release();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.compose.ui.input.pointer.NodeParent
    public boolean dispatchFinalEventPass(InternalPointerEvent internalPointerEvent) {
        boolean z = false;
        z = false;
        if (!this.relevantChanges.isEmpty() && this.modifierNode.isAttached()) {
            PointerEvent pointerEvent = this.pointerEvent;
            pointerEvent.getClass();
            LayoutCoordinates layoutCoordinates = this.coordinates;
            layoutCoordinates.getClass();
            long jMo531getSizeYbymL2g = layoutCoordinates.mo531getSizeYbymL2g();
            Modifier.Node node = this.modifierNode;
            NodeKind.m658constructorimpl(16);
            for (Modifier.Node nodePop = node; nodePop != 0; nodePop = DelegatableNodeKt.pop(null)) {
                if (nodePop instanceof PointerInputModifierNode) {
                    ((PointerInputModifierNode) nodePop).mo555onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Final, jMo531getSizeYbymL2g);
                } else {
                    nodePop.getKindSet$ui_release();
                }
            }
            if (this.modifierNode.isAttached()) {
                MutableVector children = getChildren();
                Object[] objArr = children.content;
                int size = children.getSize();
                for (int i = 0; i < size; i++) {
                    ((Node) objArr[i]).dispatchFinalEventPass(internalPointerEvent);
                }
            }
            z = true;
        }
        cleanUpHits(internalPointerEvent);
        clearCache();
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r9v3, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8 */
    @Override // androidx.compose.ui.input.pointer.NodeParent
    public boolean dispatchMainEventPass(LongSparseArray longSparseArray, LayoutCoordinates layoutCoordinates, InternalPointerEvent internalPointerEvent, boolean z) {
        if (this.relevantChanges.isEmpty() || !this.modifierNode.isAttached()) {
            return false;
        }
        PointerEvent pointerEvent = this.pointerEvent;
        pointerEvent.getClass();
        LayoutCoordinates layoutCoordinates2 = this.coordinates;
        layoutCoordinates2.getClass();
        long jMo531getSizeYbymL2g = layoutCoordinates2.mo531getSizeYbymL2g();
        Modifier.Node node = this.modifierNode;
        NodeKind.m658constructorimpl(16);
        for (?? Pop = node; Pop != 0; Pop = DelegatableNodeKt.pop(null)) {
            if (Pop instanceof PointerInputModifierNode) {
                ((PointerInputModifierNode) Pop).mo555onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Initial, jMo531getSizeYbymL2g);
            } else {
                Pop.getKindSet$ui_release();
            }
        }
        if (this.modifierNode.isAttached()) {
            MutableVector children = getChildren();
            Object[] objArr = children.content;
            int size = children.getSize();
            for (int i = 0; i < size; i++) {
                Node node2 = (Node) objArr[i];
                LongSparseArray longSparseArray2 = this.relevantChanges;
                LayoutCoordinates layoutCoordinates3 = this.coordinates;
                layoutCoordinates3.getClass();
                node2.dispatchMainEventPass(longSparseArray2, layoutCoordinates3, internalPointerEvent, z);
            }
        }
        if (!this.modifierNode.isAttached()) {
            return true;
        }
        Modifier.Node node3 = this.modifierNode;
        NodeKind.m658constructorimpl(16);
        for (?? Pop2 = node3; Pop2 != 0; Pop2 = DelegatableNodeKt.pop(null)) {
            if (Pop2 instanceof PointerInputModifierNode) {
                ((PointerInputModifierNode) Pop2).mo555onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Main, jMo531getSizeYbymL2g);
            } else {
                Pop2.getKindSet$ui_release();
            }
        }
        return true;
    }

    public final Modifier.Node getModifierNode() {
        return this.modifierNode;
    }

    public final PointerIdArray getPointerIds() {
        return this.pointerIds;
    }

    public final void markIsIn() {
        this.isIn = true;
    }

    @Override // androidx.compose.ui.input.pointer.NodeParent
    public void removeInvalidPointerIdsAndChanges(long j, MutableObjectList mutableObjectList) {
        if (this.pointerIds.contains(j) && !mutableObjectList.contains(this)) {
            this.pointerIds.remove(j);
            this.relevantChanges.remove(j);
        }
        MutableVector children = getChildren();
        Object[] objArr = children.content;
        int size = children.getSize();
        for (int i = 0; i < size; i++) {
            ((Node) objArr[i]).removeInvalidPointerIdsAndChanges(j, mutableObjectList);
        }
    }

    public String toString() {
        return "Node(modifierNode=" + this.modifierNode + ", children=" + getChildren() + ", pointerIds=" + this.pointerIds + ')';
    }
}
