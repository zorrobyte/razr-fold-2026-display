package androidx.compose.ui.input.pointer;

import androidx.collection.MutableLongObjectMap;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutCoordinates;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HitPathTracker.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HitPathTracker {
    private final LayoutCoordinates rootCoordinates;
    private final NodeParent root = new NodeParent();
    private final MutableLongObjectMap hitPointerIdsAndNodes = new MutableLongObjectMap(10);

    public HitPathTracker(LayoutCoordinates layoutCoordinates) {
        this.rootCoordinates = layoutCoordinates;
    }

    private final void removeInvalidPointerIdsAndChanges(long j, MutableObjectList mutableObjectList) {
        this.root.removeInvalidPointerIdsAndChanges(j, mutableObjectList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removePointerInputModifierNode(Modifier.Node node) {
        this.root.removePointerInputModifierNode(node);
    }

    /* JADX INFO: renamed from: addHitPath-QJqDSyo, reason: not valid java name */
    public final void m469addHitPathQJqDSyo(long j, List list, boolean z) {
        Object obj;
        NodeParent nodeParent = this.root;
        this.hitPointerIdsAndNodes.clear();
        int size = list.size();
        boolean z2 = true;
        for (int i = 0; i < size; i++) {
            final Modifier.Node node = (Modifier.Node) list.get(i);
            if (node.isAttached()) {
                node.setDetachedListener$ui_release(new Function0() { // from class: androidx.compose.ui.input.pointer.HitPathTracker$addHitPath$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public /* bridge */ /* synthetic */ Object mo2224invoke() {
                        m470invoke();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                    public final void m470invoke() {
                        this.this$0.removePointerInputModifierNode(node);
                    }
                });
                if (z2) {
                    MutableVector children = nodeParent.getChildren();
                    Object[] objArr = children.content;
                    int size2 = children.getSize();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size2) {
                            obj = null;
                            break;
                        }
                        obj = objArr[i2];
                        if (Intrinsics.areEqual(((Node) obj).getModifierNode(), node)) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    Node node2 = (Node) obj;
                    if (node2 != null) {
                        node2.markIsIn();
                        node2.getPointerIds().add(j);
                        MutableLongObjectMap mutableLongObjectMap = this.hitPointerIdsAndNodes;
                        Object mutableObjectList = mutableLongObjectMap.get(j);
                        if (mutableObjectList == null) {
                            mutableObjectList = new MutableObjectList(0, 1, null);
                            mutableLongObjectMap.set(j, mutableObjectList);
                        }
                        ((MutableObjectList) mutableObjectList).add(node2);
                        nodeParent = node2;
                    } else {
                        z2 = false;
                    }
                }
                Node node3 = new Node(node);
                node3.getPointerIds().add(j);
                MutableLongObjectMap mutableLongObjectMap2 = this.hitPointerIdsAndNodes;
                Object mutableObjectList2 = mutableLongObjectMap2.get(j);
                if (mutableObjectList2 == null) {
                    mutableObjectList2 = new MutableObjectList(0, 1, null);
                    mutableLongObjectMap2.set(j, mutableObjectList2);
                }
                ((MutableObjectList) mutableObjectList2).add(node3);
                nodeParent.getChildren().add(node3);
                nodeParent = node3;
            }
        }
        if (!z) {
            return;
        }
        MutableLongObjectMap mutableLongObjectMap3 = this.hitPointerIdsAndNodes;
        long[] jArr = mutableLongObjectMap3.keys;
        Object[] objArr2 = mutableLongObjectMap3.values;
        long[] jArr2 = mutableLongObjectMap3.metadata;
        int length = jArr2.length - 2;
        if (length < 0) {
            return;
        }
        int i3 = 0;
        while (true) {
            long j2 = jArr2[i3];
            if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                int i4 = 8 - ((~(i3 - length)) >>> 31);
                for (int i5 = 0; i5 < i4; i5++) {
                    if ((255 & j2) < 128) {
                        int i6 = (i3 << 3) + i5;
                        removeInvalidPointerIdsAndChanges(jArr[i6], (MutableObjectList) objArr2[i6]);
                    }
                    j2 >>= 8;
                }
                if (i4 != 8) {
                    return;
                }
            }
            if (i3 == length) {
                return;
            } else {
                i3++;
            }
        }
    }

    public final void clearPreviouslyHitModifierNodeCache() {
        this.root.clear();
    }

    public final boolean dispatchChanges(InternalPointerEvent internalPointerEvent, boolean z) {
        if (this.root.buildCache(internalPointerEvent.getChanges(), this.rootCoordinates, internalPointerEvent, z)) {
            return this.root.dispatchFinalEventPass(internalPointerEvent) || this.root.dispatchMainEventPass(internalPointerEvent.getChanges(), this.rootCoordinates, internalPointerEvent, z);
        }
        return false;
    }

    public final void processCancel() {
        this.root.dispatchCancel();
        clearPreviouslyHitModifierNodeCache();
    }
}
