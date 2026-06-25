package androidx.compose.ui.draganddrop;

import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNode$Companion$TraverseDescendantsAction;
import androidx.compose.ui.node.TraversableNodeKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: DragAndDropNode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DragAndDropNodeKt {
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: contains-Uv8p0NA, reason: not valid java name */
    public static final boolean m115containsUv8p0NA(DragAndDropNode dragAndDropNode, long j) {
        if (!dragAndDropNode.getNode().isAttached()) {
            return false;
        }
        LayoutCoordinates coordinates = DelegatableNodeKt.requireLayoutNode(dragAndDropNode).getCoordinates();
        if (!coordinates.isAttached()) {
            return false;
        }
        long jPositionInRoot = LayoutCoordinatesKt.positionInRoot(coordinates);
        float fIntBitsToFloat = Float.intBitsToFloat((int) (jPositionInRoot >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jPositionInRoot & 4294967295L));
        float fM112getSizeYbymL2g$ui_release = ((int) (dragAndDropNode.m112getSizeYbymL2g$ui_release() >> 32)) + fIntBitsToFloat;
        float fM112getSizeYbymL2g$ui_release2 = ((int) (dragAndDropNode.m112getSizeYbymL2g$ui_release() & 4294967295L)) + fIntBitsToFloat2;
        float fIntBitsToFloat3 = Float.intBitsToFloat((int) (j >> 32));
        if (fIntBitsToFloat <= fIntBitsToFloat3 && fIntBitsToFloat3 <= fM112getSizeYbymL2g$ui_release) {
            float fIntBitsToFloat4 = Float.intBitsToFloat((int) (j & 4294967295L));
            if (fIntBitsToFloat2 <= fIntBitsToFloat4 && fIntBitsToFloat4 <= fM112getSizeYbymL2g$ui_release2) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dispatchEntered(DragAndDropTarget dragAndDropTarget, DragAndDropEvent dragAndDropEvent) {
        dragAndDropTarget.onEntered(dragAndDropEvent);
        dragAndDropTarget.onMoved(dragAndDropEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void traverseSelfAndDescendants(TraversableNode traversableNode, Function1 function1) {
        if (function1.invoke(traversableNode) != TraversableNode$Companion$TraverseDescendantsAction.ContinueTraversal) {
            return;
        }
        TraversableNodeKt.traverseDescendants(traversableNode, function1);
    }
}
