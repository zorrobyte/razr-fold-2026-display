package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OneDimensionalFocusSearch.kt */
/* JADX INFO: loaded from: classes.dex */
final class FocusableChildrenComparator implements Comparator {
    public static final FocusableChildrenComparator INSTANCE = new FocusableChildrenComparator();

    private FocusableChildrenComparator() {
    }

    private final MutableVector pathFromRoot(LayoutNode layoutNode) {
        MutableVector mutableVector = new MutableVector(new LayoutNode[16], 0);
        while (layoutNode != null) {
            mutableVector.add(0, layoutNode);
            layoutNode = layoutNode.getParent$ui_release();
        }
        return mutableVector;
    }

    @Override // java.util.Comparator
    public int compare(FocusTargetNode focusTargetNode, FocusTargetNode focusTargetNode2) {
        int i = 0;
        if (!FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode) || !FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode2)) {
            if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode)) {
                return -1;
            }
            return FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode2) ? 1 : 0;
        }
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
        LayoutNode layoutNodeRequireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(focusTargetNode2);
        if (Intrinsics.areEqual(layoutNodeRequireLayoutNode, layoutNodeRequireLayoutNode2)) {
            return 0;
        }
        MutableVector mutableVectorPathFromRoot = pathFromRoot(layoutNodeRequireLayoutNode);
        MutableVector mutableVectorPathFromRoot2 = pathFromRoot(layoutNodeRequireLayoutNode2);
        int iMin = Math.min(mutableVectorPathFromRoot.getSize() - 1, mutableVectorPathFromRoot2.getSize() - 1);
        if (iMin >= 0) {
            while (Intrinsics.areEqual(mutableVectorPathFromRoot.content[i], mutableVectorPathFromRoot2.content[i])) {
                if (i != iMin) {
                    i++;
                }
            }
            return Intrinsics.compare(((LayoutNode) mutableVectorPathFromRoot.content[i]).getPlaceOrder$ui_release(), ((LayoutNode) mutableVectorPathFromRoot2.content[i]).getPlaceOrder$ui_release());
        }
        throw new IllegalStateException("Could not find a common ancestor between the two FocusModifiers.");
    }
}
