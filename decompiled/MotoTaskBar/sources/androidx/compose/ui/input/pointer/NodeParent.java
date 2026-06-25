package androidx.compose.ui.input.pointer;

import androidx.collection.LongSparseArray;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutCoordinates;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HitPathTracker.kt */
/* JADX INFO: loaded from: classes.dex */
public class NodeParent {
    private final MutableVector children = new MutableVector(new Node[16], 0);
    private final MutableObjectList removeMatchingPointerInputModifierNodeList = new MutableObjectList(10);

    public boolean buildCache(LongSparseArray longSparseArray, LayoutCoordinates layoutCoordinates, InternalPointerEvent internalPointerEvent, boolean z) {
        MutableVector mutableVector = this.children;
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            z2 = ((Node) objArr[i]).buildCache(longSparseArray, layoutCoordinates, internalPointerEvent, z) || z2;
        }
        return z2;
    }

    public void cleanUpHits(InternalPointerEvent internalPointerEvent) {
        int size = this.children.getSize();
        while (true) {
            size--;
            if (-1 >= size) {
                return;
            }
            if (((Node) this.children.content[size]).getPointerIds().isEmpty()) {
                this.children.removeAt(size);
            }
        }
    }

    public final void clear() {
        this.children.clear();
    }

    public void dispatchCancel() {
        MutableVector mutableVector = this.children;
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            ((Node) objArr[i]).dispatchCancel();
        }
    }

    public boolean dispatchFinalEventPass(InternalPointerEvent internalPointerEvent) {
        MutableVector mutableVector = this.children;
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            z = ((Node) objArr[i]).dispatchFinalEventPass(internalPointerEvent) || z;
        }
        cleanUpHits(internalPointerEvent);
        return z;
    }

    public boolean dispatchMainEventPass(LongSparseArray longSparseArray, LayoutCoordinates layoutCoordinates, InternalPointerEvent internalPointerEvent, boolean z) {
        MutableVector mutableVector = this.children;
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            z2 = ((Node) objArr[i]).dispatchMainEventPass(longSparseArray, layoutCoordinates, internalPointerEvent, z) || z2;
        }
        return z2;
    }

    public final MutableVector getChildren() {
        return this.children;
    }

    public void removeInvalidPointerIdsAndChanges(long j, MutableObjectList mutableObjectList) {
        MutableVector mutableVector = this.children;
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            ((Node) objArr[i]).removeInvalidPointerIdsAndChanges(j, mutableObjectList);
        }
    }

    public void removePointerInputModifierNode(Modifier.Node node) {
        this.removeMatchingPointerInputModifierNodeList.clear();
        this.removeMatchingPointerInputModifierNodeList.add(this);
        while (this.removeMatchingPointerInputModifierNodeList.isNotEmpty()) {
            NodeParent nodeParent = (NodeParent) this.removeMatchingPointerInputModifierNodeList.removeAt(r0.getSize() - 1);
            int i = 0;
            while (i < nodeParent.children.getSize()) {
                Node node2 = (Node) nodeParent.children.content[i];
                if (Intrinsics.areEqual(node2.getModifierNode(), node)) {
                    nodeParent.children.remove(node2);
                    node2.dispatchCancel();
                } else {
                    this.removeMatchingPointerInputModifierNodeList.add(node2);
                    i++;
                }
            }
        }
    }
}
