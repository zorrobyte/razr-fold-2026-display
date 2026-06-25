package androidx.compose.ui.scrollcapture;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.platform.SemanticsUtils_androidKt;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntRectKt;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: ScrollCapture.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ScrollCapture_androidKt {
    private static final boolean getCanScrollVertically(SemanticsNode semanticsNode) {
        getScrollCaptureScrollByAction(semanticsNode);
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(SemanticsConfigurationKt.getOrNull(semanticsNode.getUnmergedConfig$ui_release(), SemanticsProperties.INSTANCE.getVerticalScrollAxisRange()));
        return false;
    }

    private static final List getChildrenForSearch(SemanticsNode semanticsNode) {
        return semanticsNode.getChildren$ui_release(false, false, false);
    }

    public static final Function2 getScrollCaptureScrollByAction(SemanticsNode semanticsNode) {
        return (Function2) SemanticsConfigurationKt.getOrNull(semanticsNode.getUnmergedConfig$ui_release(), SemanticsActions.INSTANCE.getScrollByOffset());
    }

    private static final void visitScrollCaptureCandidates(SemanticsNode semanticsNode, int i, Function1 function1) {
        SemanticsNode semanticsNode2;
        MutableVector mutableVector = new MutableVector(new SemanticsNode[16], 0);
        List childrenForSearch = getChildrenForSearch(semanticsNode);
        while (true) {
            mutableVector.addAll(mutableVector.getSize(), childrenForSearch);
            while (mutableVector.getSize() != 0) {
                semanticsNode2 = (SemanticsNode) mutableVector.removeAt(mutableVector.getSize() - 1);
                if (!SemanticsUtils_androidKt.isHidden(semanticsNode2) && !semanticsNode2.getUnmergedConfig$ui_release().contains(SemanticsProperties.INSTANCE.getDisabled())) {
                    NodeCoordinator nodeCoordinatorFindCoordinatorToGetBounds$ui_release = semanticsNode2.findCoordinatorToGetBounds$ui_release();
                    if (nodeCoordinatorFindCoordinatorToGetBounds$ui_release == null) {
                        InlineClassHelperKt.throwIllegalStateExceptionForNullCheck("Expected semantics node to have a coordinator.");
                        throw new KotlinNothingValueException();
                    }
                    LayoutCoordinates coordinates = nodeCoordinatorFindCoordinatorToGetBounds$ui_release.getCoordinates();
                    IntRect intRectRoundToIntRect = IntRectKt.roundToIntRect(LayoutCoordinatesKt.boundsInWindow(coordinates));
                    if (intRectRoundToIntRect.isEmpty()) {
                        continue;
                    } else {
                        if (!getCanScrollVertically(semanticsNode2)) {
                            break;
                        }
                        int i2 = i + 1;
                        function1.invoke(new ScrollCaptureCandidate(semanticsNode2, i2, intRectRoundToIntRect, coordinates));
                        visitScrollCaptureCandidates(semanticsNode2, i2, function1);
                    }
                }
            }
            return;
            childrenForSearch = getChildrenForSearch(semanticsNode2);
        }
    }

    static /* synthetic */ void visitScrollCaptureCandidates$default(SemanticsNode semanticsNode, int i, Function1 function1, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        visitScrollCaptureCandidates(semanticsNode, i, function1);
    }
}
