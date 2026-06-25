package androidx.compose.ui.node;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.unit.IntOffset;
import java.util.Map;

/* JADX INFO: compiled from: LayoutNodeAlignmentLines.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LookaheadAlignmentLines extends AlignmentLines {
    public LookaheadAlignmentLines(AlignmentLinesOwner alignmentLinesOwner) {
        super(alignmentLinesOwner, null);
    }

    @Override // androidx.compose.ui.node.AlignmentLines
    /* JADX INFO: renamed from: calculatePositionInParent-R5De75A */
    protected long mo1301calculatePositionInParentR5De75A(NodeCoordinator nodeCoordinator, long j) {
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        long jMo1340getPositionnOccac = lookaheadDelegate.mo1340getPositionnOccac();
        float fM1905getXimpl = IntOffset.m1905getXimpl(jMo1340getPositionnOccac);
        return Offset.m764plusMKHz9U(Offset.m752constructorimpl((((long) Float.floatToRawIntBits(IntOffset.m1906getYimpl(jMo1340getPositionnOccac))) & 4294967295L) | (Float.floatToRawIntBits(fM1905getXimpl) << 32)), j);
    }

    @Override // androidx.compose.ui.node.AlignmentLines
    protected Map getAlignmentLinesMap(NodeCoordinator nodeCoordinator) {
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.getMeasureResult$ui_release().getAlignmentLines();
    }

    @Override // androidx.compose.ui.node.AlignmentLines
    protected int getPositionFor(NodeCoordinator nodeCoordinator, AlignmentLine alignmentLine) {
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.get(alignmentLine);
    }
}
