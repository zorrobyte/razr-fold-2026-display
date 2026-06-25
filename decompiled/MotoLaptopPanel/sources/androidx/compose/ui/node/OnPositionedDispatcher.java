package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import java.util.Comparator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OnPositionedDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
public final class OnPositionedDispatcher {
    private LayoutNode[] cachedNodes;
    private final MutableVector layoutNodes = new MutableVector(new LayoutNode[16], 0);
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: OnPositionedDispatcher.kt */
    public final class Companion {

        /* JADX INFO: compiled from: OnPositionedDispatcher.kt */
        final class DepthComparator implements Comparator {
            public static final DepthComparator INSTANCE = new DepthComparator();

            private DepthComparator() {
            }

            @Override // java.util.Comparator
            public int compare(LayoutNode layoutNode, LayoutNode layoutNode2) {
                int iCompare = Intrinsics.compare(layoutNode2.getDepth$ui_release(), layoutNode.getDepth$ui_release());
                return iCompare != 0 ? iCompare : Intrinsics.compare(layoutNode.hashCode(), layoutNode2.hashCode());
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final void dispatchHierarchy(LayoutNode layoutNode) {
        layoutNode.dispatchOnPositionedCallbacks$ui_release();
        layoutNode.setNeedsOnPositionedDispatch$ui_release(false);
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            dispatchHierarchy((LayoutNode) objArr[i]);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void dispatch() {
        /*
            r4 = this;
            androidx.compose.runtime.collection.MutableVector r0 = r4.layoutNodes
            androidx.compose.ui.node.OnPositionedDispatcher$Companion$DepthComparator r1 = androidx.compose.ui.node.OnPositionedDispatcher.Companion.DepthComparator.INSTANCE
            r0.sortWith(r1)
            androidx.compose.runtime.collection.MutableVector r0 = r4.layoutNodes
            int r0 = r0.getSize()
            androidx.compose.ui.node.LayoutNode[] r1 = r4.cachedNodes
            if (r1 == 0) goto L14
            int r2 = r1.length
            if (r2 >= r0) goto L22
        L14:
            androidx.compose.runtime.collection.MutableVector r1 = r4.layoutNodes
            int r1 = r1.getSize()
            r2 = 16
            int r1 = java.lang.Math.max(r2, r1)
            androidx.compose.ui.node.LayoutNode[] r1 = new androidx.compose.ui.node.LayoutNode[r1]
        L22:
            r2 = 0
            r4.cachedNodes = r2
            r2 = 0
        L26:
            if (r2 >= r0) goto L33
            androidx.compose.runtime.collection.MutableVector r3 = r4.layoutNodes
            java.lang.Object[] r3 = r3.content
            r3 = r3[r2]
            r1[r2] = r3
            int r2 = r2 + 1
            goto L26
        L33:
            androidx.compose.runtime.collection.MutableVector r2 = r4.layoutNodes
            r2.clear()
            int r0 = r0 + (-1)
        L3a:
            r2 = -1
            if (r2 >= r0) goto L4e
            r2 = r1[r0]
            r2.getClass()
            boolean r3 = r2.getNeedsOnPositionedDispatch$ui_release()
            if (r3 == 0) goto L4b
            r4.dispatchHierarchy(r2)
        L4b:
            int r0 = r0 + (-1)
            goto L3a
        L4e:
            r4.cachedNodes = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.OnPositionedDispatcher.dispatch():void");
    }

    public final boolean isNotEmpty() {
        return this.layoutNodes.getSize() != 0;
    }

    public final void onNodePositioned(LayoutNode layoutNode) {
        this.layoutNodes.add(layoutNode);
        layoutNode.setNeedsOnPositionedDispatch$ui_release(true);
    }

    public final void onRootNodePositioned(LayoutNode layoutNode) {
        this.layoutNodes.clear();
        this.layoutNodes.add(layoutNode);
        layoutNode.setNeedsOnPositionedDispatch$ui_release(true);
    }

    public final void remove(LayoutNode layoutNode) {
        this.layoutNodes.remove(layoutNode);
    }
}
