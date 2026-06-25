package androidx.compose.ui.node;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.AndroidPaint_androidKt;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Paint;
import androidx.compose.ui.graphics.PaintingStyle;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.ApproachMeasureScopeImpl;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LayoutModifierNodeCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LayoutModifierNodeCoordinator extends NodeCoordinator {
    public static final Companion Companion = new Companion(null);
    private static final Paint modifierBoundsPaint;
    private ApproachMeasureScopeImpl approachMeasureScope;
    private LayoutModifierNode layoutModifierNode;
    private Constraints lookaheadConstraints;
    private LookaheadDelegate lookaheadDelegate;

    /* JADX INFO: compiled from: LayoutModifierNodeCoordinator.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: LayoutModifierNodeCoordinator.kt */
    final class LookaheadDelegateForLayoutModifierNode extends LookaheadDelegate {
        public LookaheadDelegateForLayoutModifierNode() {
            super(LayoutModifierNodeCoordinator.this);
        }

        @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
        public int calculateAlignmentLine(AlignmentLine alignmentLine) {
            int iCalculateAlignmentAndPlaceChildAsNeeded = LayoutModifierNodeCoordinatorKt.calculateAlignmentAndPlaceChildAsNeeded(this, alignmentLine);
            getCachedAlignmentLinesMap().set(alignmentLine, iCalculateAlignmentAndPlaceChildAsNeeded);
            return iCalculateAlignmentAndPlaceChildAsNeeded;
        }

        @Override // androidx.compose.ui.layout.Measurable
        /* JADX INFO: renamed from: measure-BRTryo0 */
        public Placeable mo537measureBRTryo0(long j) {
            LayoutModifierNodeCoordinator layoutModifierNodeCoordinator = LayoutModifierNodeCoordinator.this;
            m548setMeasurementConstraintsBRTryo0(j);
            layoutModifierNodeCoordinator.m570setLookaheadConstraints_Sx5XlM$ui_release(Constraints.m973boximpl(j));
            LayoutModifierNode layoutModifierNode = layoutModifierNodeCoordinator.getLayoutModifierNode();
            LookaheadDelegate lookaheadDelegate = layoutModifierNodeCoordinator.getWrappedNonNull().getLookaheadDelegate();
            lookaheadDelegate.getClass();
            set_measureResult(layoutModifierNode.mo554measure3p2s80s(this, lookaheadDelegate, j));
            return this;
        }
    }

    static {
        Paint Paint = AndroidPaint_androidKt.Paint();
        Paint.mo225setColor8_81llA(Color.Companion.m288getBlue0d7_KjU());
        Paint.setStrokeWidth(1.0f);
        Paint.mo227setStylek9PVt8s(PaintingStyle.Companion.m321getStrokeTiuSbCo());
        modifierBoundsPaint = Paint;
    }

    public LayoutModifierNodeCoordinator(LayoutNode layoutNode, LayoutModifierNode layoutModifierNode) {
        super(layoutNode);
        this.layoutModifierNode = layoutModifierNode;
        ApproachMeasureScopeImpl approachMeasureScopeImpl = null;
        this.lookaheadDelegate = layoutNode.getLookaheadRoot$ui_release() != null ? new LookaheadDelegateForLayoutModifierNode() : null;
        if ((layoutModifierNode.getNode().getKindSet$ui_release() & NodeKind.m658constructorimpl(512)) != 0) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(layoutModifierNode);
            approachMeasureScopeImpl = new ApproachMeasureScopeImpl(this, null);
        }
        this.approachMeasureScope = approachMeasureScopeImpl;
    }

    private final void onAfterPlaceAt() {
        if (isShallowPlacing$ui_release()) {
            return;
        }
        onPlaced();
        ApproachMeasureScopeImpl approachMeasureScopeImpl = this.approachMeasureScope;
        if (approachMeasureScopeImpl == null) {
            getMeasureResult$ui_release().placeChildren();
            getWrappedNonNull().setForcePlaceWithLookaheadOffset$ui_release(false);
            return;
        }
        approachMeasureScopeImpl.getApproachNode();
        getPlacementScope();
        LookaheadDelegate lookaheadDelegate = getLookaheadDelegate();
        lookaheadDelegate.getClass();
        lookaheadDelegate.getLookaheadLayoutCoordinates();
        throw null;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public int calculateAlignmentLine(AlignmentLine alignmentLine) {
        LookaheadDelegate lookaheadDelegate = getLookaheadDelegate();
        return lookaheadDelegate != null ? lookaheadDelegate.getCachedAlignmentLine$ui_release(alignmentLine) : LayoutModifierNodeCoordinatorKt.calculateAlignmentAndPlaceChildAsNeeded(this, alignmentLine);
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public void ensureLookaheadDelegateCreated() {
        if (getLookaheadDelegate() == null) {
            setLookaheadDelegate(new LookaheadDelegateForLayoutModifierNode());
        }
    }

    public final LayoutModifierNode getLayoutModifierNode() {
        return this.layoutModifierNode;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public LookaheadDelegate getLookaheadDelegate() {
        return this.lookaheadDelegate;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public Modifier.Node getTail() {
        return this.layoutModifierNode.getNode();
    }

    public final NodeCoordinator getWrappedNonNull() {
        NodeCoordinator wrapped$ui_release = getWrapped$ui_release();
        wrapped$ui_release.getClass();
        return wrapped$ui_release;
    }

    @Override // androidx.compose.ui.layout.Measurable
    /* JADX INFO: renamed from: measure-BRTryo0 */
    public Placeable mo537measureBRTryo0(long j) {
        if (getForceMeasureWithLookaheadConstraints$ui_release()) {
            Constraints constraints = this.lookaheadConstraints;
            if (constraints == null) {
                throw new IllegalArgumentException("Lookahead constraints cannot be null in approach pass.");
            }
            j = constraints.m985unboximpl();
        }
        m548setMeasurementConstraintsBRTryo0(j);
        ApproachMeasureScopeImpl approachMeasureScopeImpl = this.approachMeasureScope;
        if (approachMeasureScopeImpl == null) {
            setMeasureResult$ui_release(getLayoutModifierNode().mo554measure3p2s80s(this, getWrappedNonNull(), j));
            onMeasured();
            return this;
        }
        approachMeasureScopeImpl.getApproachNode();
        approachMeasureScopeImpl.m526getLookaheadSizeYbymL2g();
        throw null;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public void performDraw(Canvas canvas, GraphicsLayer graphicsLayer) {
        getWrappedNonNull().draw(canvas, graphicsLayer);
        if (LayoutNodeKt.requireOwner(getLayoutNode()).getShowLayoutBounds()) {
            drawBorder(canvas, modifierBoundsPaint);
        }
    }

    @Override // androidx.compose.ui.node.NodeCoordinator, androidx.compose.ui.layout.Placeable
    /* JADX INFO: renamed from: placeAt-f8xVGno */
    protected void mo545placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) {
        super.mo545placeAtf8xVGno(j, f, graphicsLayer);
        onAfterPlaceAt();
    }

    @Override // androidx.compose.ui.node.NodeCoordinator, androidx.compose.ui.layout.Placeable
    /* JADX INFO: renamed from: placeAt-f8xVGno */
    protected void mo546placeAtf8xVGno(long j, float f, Function1 function1) {
        super.mo546placeAtf8xVGno(j, f, function1);
        onAfterPlaceAt();
    }

    public final void setLayoutModifierNode$ui_release(LayoutModifierNode layoutModifierNode) {
        if (!Intrinsics.areEqual(layoutModifierNode, this.layoutModifierNode)) {
            Modifier.Node node = layoutModifierNode.getNode();
            if ((node.getKindSet$ui_release() & NodeKind.m658constructorimpl(512)) != 0) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(layoutModifierNode);
                ApproachMeasureScopeImpl approachMeasureScopeImpl = this.approachMeasureScope;
                if (approachMeasureScopeImpl != null) {
                    ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(layoutModifierNode);
                    approachMeasureScopeImpl.setApproachNode(null);
                } else {
                    ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(layoutModifierNode);
                    approachMeasureScopeImpl = new ApproachMeasureScopeImpl(this, null);
                }
                this.approachMeasureScope = approachMeasureScopeImpl;
            } else {
                this.approachMeasureScope = null;
            }
        }
        this.layoutModifierNode = layoutModifierNode;
    }

    /* JADX INFO: renamed from: setLookaheadConstraints-_Sx5XlM$ui_release, reason: not valid java name */
    public final void m570setLookaheadConstraints_Sx5XlM$ui_release(Constraints constraints) {
        this.lookaheadConstraints = constraints;
    }

    protected void setLookaheadDelegate(LookaheadDelegate lookaheadDelegate) {
        this.lookaheadDelegate = lookaheadDelegate;
    }
}
