package androidx.compose.ui.input.pointer;

import androidx.collection.LongSparseArray;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.util.PointerIdArray;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
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
            if (!Offset.m757equalsimpl0(((PointerInputChange) pointerEvent.getChanges().get(i)).m1233getPositionF1C5BW0(), ((PointerInputChange) pointerEvent2.getChanges().get(i)).m1233getPositionF1C5BW0())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0259  */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r5v2, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v26 */
    /* JADX WARN: Type inference failed for: r5v27, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v28, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v29 */
    /* JADX WARN: Type inference failed for: r5v3, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v30 */
    /* JADX WARN: Type inference failed for: r5v31 */
    /* JADX WARN: Type inference failed for: r5v32 */
    /* JADX WARN: Type inference failed for: r5v33 */
    /* JADX WARN: Type inference failed for: r5v34 */
    @Override // androidx.compose.ui.input.pointer.NodeParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean buildCache(androidx.collection.LongSparseArray r46, androidx.compose.ui.layout.LayoutCoordinates r47, androidx.compose.ui.input.pointer.InternalPointerEvent r48, boolean r49) {
        /*
            Method dump skipped, instruction units count: 700
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
            boolean zM1203activeHoverEvent0FcD4WY = internalPointerEvent.m1203activeHoverEvent0FcD4WY(pointerInputChange.m1231getIdJ3iCeTQ());
            boolean z = this.isIn;
            if ((!pressed && !zM1203activeHoverEvent0FcD4WY) || (!pressed && !z)) {
                this.pointerIds.remove(pointerInputChange.m1231getIdJ3iCeTQ());
            }
        }
        this.isIn = false;
        this.hasExited = PointerEventType.m1215equalsimpl0(pointerEvent.m1210getType7fucELk(), PointerEventType.Companion.m1217getExit7fucELk());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r9v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v2, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r9v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    @Override // androidx.compose.ui.input.pointer.NodeParent
    public void dispatchCancel() {
        MutableVector children = getChildren();
        Object[] objArr = children.content;
        int size = children.getSize();
        for (int i = 0; i < size; i++) {
            ((Node) objArr[i]).dispatchCancel();
        }
        ?? Pop = this.modifierNode;
        int iM1404constructorimpl = NodeKind.m1404constructorimpl(16);
        ?? mutableVector = 0;
        while (Pop != 0) {
            if (Pop instanceof PointerInputModifierNode) {
                ((PointerInputModifierNode) Pop).onCancelPointerInput();
            } else if ((Pop.getKindSet$ui_release() & iM1404constructorimpl) != 0 && (Pop instanceof DelegatingNode)) {
                Modifier.Node delegate$ui_release = ((DelegatingNode) Pop).getDelegate$ui_release();
                int i2 = 0;
                mutableVector = mutableVector;
                Pop = Pop;
                while (delegate$ui_release != null) {
                    if ((delegate$ui_release.getKindSet$ui_release() & iM1404constructorimpl) != 0) {
                        i2++;
                        mutableVector = mutableVector;
                        if (i2 == 1) {
                            Pop = delegate$ui_release;
                        } else {
                            if (mutableVector == 0) {
                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                            }
                            if (Pop != 0) {
                                mutableVector.add(Pop);
                                Pop = 0;
                            }
                            mutableVector.add(delegate$ui_release);
                        }
                    }
                    delegate$ui_release = delegate$ui_release.getChild$ui_release();
                    mutableVector = mutableVector;
                    Pop = Pop;
                }
                if (i2 == 1) {
                }
            }
            Pop = DelegatableNodeKt.pop(mutableVector);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r4v0, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    @Override // androidx.compose.ui.input.pointer.NodeParent
    public boolean dispatchFinalEventPass(InternalPointerEvent internalPointerEvent) {
        boolean z = false;
        z = false;
        if (!this.relevantChanges.isEmpty() && this.modifierNode.isAttached()) {
            PointerEvent pointerEvent = this.pointerEvent;
            pointerEvent.getClass();
            LayoutCoordinates layoutCoordinates = this.coordinates;
            layoutCoordinates.getClass();
            long jMo1278getSizeYbymL2g = layoutCoordinates.mo1278getSizeYbymL2g();
            ?? Pop = this.modifierNode;
            int iM1404constructorimpl = NodeKind.m1404constructorimpl(16);
            ?? mutableVector = 0;
            while (Pop != 0) {
                if (Pop instanceof PointerInputModifierNode) {
                    ((PointerInputModifierNode) Pop).mo74onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Final, jMo1278getSizeYbymL2g);
                } else if ((Pop.getKindSet$ui_release() & iM1404constructorimpl) != 0 && (Pop instanceof DelegatingNode)) {
                    Modifier.Node delegate$ui_release = ((DelegatingNode) Pop).getDelegate$ui_release();
                    int i = 0;
                    Pop = Pop;
                    mutableVector = mutableVector;
                    while (delegate$ui_release != null) {
                        if ((delegate$ui_release.getKindSet$ui_release() & iM1404constructorimpl) != 0) {
                            i++;
                            mutableVector = mutableVector;
                            if (i == 1) {
                                Pop = delegate$ui_release;
                            } else {
                                if (mutableVector == 0) {
                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                }
                                if (Pop != 0) {
                                    mutableVector.add(Pop);
                                    Pop = 0;
                                }
                                mutableVector.add(delegate$ui_release);
                            }
                        }
                        delegate$ui_release = delegate$ui_release.getChild$ui_release();
                        Pop = Pop;
                        mutableVector = mutableVector;
                    }
                    if (i == 1) {
                    }
                }
                Pop = DelegatableNodeKt.pop(mutableVector);
            }
            if (this.modifierNode.isAttached()) {
                MutableVector children = getChildren();
                Object[] objArr = children.content;
                int size = children.getSize();
                for (int i2 = 0; i2 < size; i2++) {
                    ((Node) objArr[i2]).dispatchFinalEventPass(internalPointerEvent);
                }
            }
            z = true;
        }
        cleanUpHits(internalPointerEvent);
        clearCache();
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v2, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r11v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r11v9 */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r15v4, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r15v9 */
    /* JADX WARN: Type inference failed for: r2v0, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v19, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v6 */
    @Override // androidx.compose.ui.input.pointer.NodeParent
    public boolean dispatchMainEventPass(LongSparseArray longSparseArray, LayoutCoordinates layoutCoordinates, InternalPointerEvent internalPointerEvent, boolean z) {
        if (this.relevantChanges.isEmpty() || !this.modifierNode.isAttached()) {
            return false;
        }
        PointerEvent pointerEvent = this.pointerEvent;
        pointerEvent.getClass();
        LayoutCoordinates layoutCoordinates2 = this.coordinates;
        layoutCoordinates2.getClass();
        long jMo1278getSizeYbymL2g = layoutCoordinates2.mo1278getSizeYbymL2g();
        ?? Pop = this.modifierNode;
        int iM1404constructorimpl = NodeKind.m1404constructorimpl(16);
        ?? mutableVector = 0;
        while (Pop != 0) {
            if (Pop instanceof PointerInputModifierNode) {
                ((PointerInputModifierNode) Pop).mo74onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Initial, jMo1278getSizeYbymL2g);
            } else if ((Pop.getKindSet$ui_release() & iM1404constructorimpl) != 0 && (Pop instanceof DelegatingNode)) {
                Modifier.Node delegate$ui_release = ((DelegatingNode) Pop).getDelegate$ui_release();
                int i = 0;
                Pop = Pop;
                mutableVector = mutableVector;
                while (delegate$ui_release != null) {
                    if ((delegate$ui_release.getKindSet$ui_release() & iM1404constructorimpl) != 0) {
                        i++;
                        mutableVector = mutableVector;
                        if (i == 1) {
                            Pop = delegate$ui_release;
                        } else {
                            if (mutableVector == 0) {
                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                            }
                            if (Pop != 0) {
                                mutableVector.add(Pop);
                                Pop = 0;
                            }
                            mutableVector.add(delegate$ui_release);
                        }
                    }
                    delegate$ui_release = delegate$ui_release.getChild$ui_release();
                    Pop = Pop;
                    mutableVector = mutableVector;
                }
                if (i == 1) {
                }
            }
            Pop = DelegatableNodeKt.pop(mutableVector);
        }
        if (this.modifierNode.isAttached()) {
            MutableVector children = getChildren();
            Object[] objArr = children.content;
            int size = children.getSize();
            for (int i2 = 0; i2 < size; i2++) {
                Node node = (Node) objArr[i2];
                LongSparseArray longSparseArray2 = this.relevantChanges;
                LayoutCoordinates layoutCoordinates3 = this.coordinates;
                layoutCoordinates3.getClass();
                node.dispatchMainEventPass(longSparseArray2, layoutCoordinates3, internalPointerEvent, z);
            }
        }
        if (this.modifierNode.isAttached()) {
            ?? Pop2 = this.modifierNode;
            int iM1404constructorimpl2 = NodeKind.m1404constructorimpl(16);
            ?? mutableVector2 = 0;
            while (Pop2 != 0) {
                if (Pop2 instanceof PointerInputModifierNode) {
                    ((PointerInputModifierNode) Pop2).mo74onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Main, jMo1278getSizeYbymL2g);
                } else if ((Pop2.getKindSet$ui_release() & iM1404constructorimpl2) != 0 && (Pop2 instanceof DelegatingNode)) {
                    Modifier.Node delegate$ui_release2 = ((DelegatingNode) Pop2).getDelegate$ui_release();
                    int i3 = 0;
                    Pop2 = Pop2;
                    mutableVector2 = mutableVector2;
                    while (delegate$ui_release2 != null) {
                        if ((delegate$ui_release2.getKindSet$ui_release() & iM1404constructorimpl2) != 0) {
                            i3++;
                            mutableVector2 = mutableVector2;
                            if (i3 == 1) {
                                Pop2 = delegate$ui_release2;
                            } else {
                                if (mutableVector2 == 0) {
                                    mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                }
                                if (Pop2 != 0) {
                                    mutableVector2.add(Pop2);
                                    Pop2 = 0;
                                }
                                mutableVector2.add(delegate$ui_release2);
                            }
                        }
                        delegate$ui_release2 = delegate$ui_release2.getChild$ui_release();
                        Pop2 = Pop2;
                        mutableVector2 = mutableVector2;
                    }
                    if (i3 == 1) {
                    }
                }
                Pop2 = DelegatableNodeKt.pop(mutableVector2);
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
