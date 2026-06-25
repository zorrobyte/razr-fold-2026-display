package androidx.compose.ui.node;

import androidx.compose.ui.layout.Measurable;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: LayoutNodeLayoutDelegate.kt */
/* JADX INFO: loaded from: classes.dex */
public interface AlignmentLinesOwner extends Measurable {
    Map calculateAlignmentLines();

    void forEachChildAlignmentLinesOwner(Function1 function1);

    AlignmentLines getAlignmentLines();

    NodeCoordinator getInnerCoordinator();

    AlignmentLinesOwner getParentAlignmentLinesOwner();

    boolean isPlaced();

    void layoutChildren();

    void requestLayout();

    void requestMeasure();
}
