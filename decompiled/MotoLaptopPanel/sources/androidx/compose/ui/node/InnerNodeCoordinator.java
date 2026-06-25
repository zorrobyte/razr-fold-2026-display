package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.graphics.AndroidPaint_androidKt;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Paint;
import androidx.compose.ui.graphics.PaintingStyle;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeCoordinator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: InnerNodeCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class InnerNodeCoordinator extends NodeCoordinator {
    public static final Companion Companion = new Companion(null);
    private static final Paint innerBoundsPaint;
    private LookaheadDelegate lookaheadDelegate;
    private final TailModifierNode tail;

    /* JADX INFO: compiled from: InnerNodeCoordinator.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: InnerNodeCoordinator.kt */
    final class LookaheadDelegateImpl extends LookaheadDelegate {
        public LookaheadDelegateImpl() {
            super(InnerNodeCoordinator.this);
        }

        @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
        public int calculateAlignmentLine(AlignmentLine alignmentLine) {
            Integer num = (Integer) getAlignmentLinesOwner().calculateAlignmentLines().get(alignmentLine);
            int iIntValue = num != null ? num.intValue() : Integer.MIN_VALUE;
            getCachedAlignmentLinesMap().set(alignmentLine, iIntValue);
            return iIntValue;
        }

        @Override // androidx.compose.ui.layout.Measurable
        /* JADX INFO: renamed from: measure-BRTryo0 */
        public Placeable mo1284measureBRTryo0(long j) {
            m1294setMeasurementConstraintsBRTryo0(j);
            MutableVector mutableVector = getLayoutNode().get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int size = mutableVector.getSize();
            for (int i = 0; i < size; i++) {
                LookaheadPassDelegate lookaheadPassDelegate$ui_release = ((LayoutNode) objArr[i]).getLookaheadPassDelegate$ui_release();
                lookaheadPassDelegate$ui_release.getClass();
                lookaheadPassDelegate$ui_release.setMeasuredByParent$ui_release(LayoutNode.UsageByParent.NotUsed);
            }
            set_measureResult(getLayoutNode().getMeasurePolicy().mo19measure3p2s80s(this, getLayoutNode().getChildLookaheadMeasurables$ui_release(), j));
            return this;
        }

        @Override // androidx.compose.ui.node.LookaheadDelegate
        protected void placeChildren() {
            LookaheadPassDelegate lookaheadPassDelegate$ui_release = getLayoutNode().getLookaheadPassDelegate$ui_release();
            lookaheadPassDelegate$ui_release.getClass();
            lookaheadPassDelegate$ui_release.onNodePlaced$ui_release();
        }
    }

    static {
        Paint Paint = AndroidPaint_androidKt.Paint();
        Paint.mo816setColor8_81llA(Color.Companion.m893getRed0d7_KjU());
        Paint.setStrokeWidth(1.0f);
        Paint.mo820setStylek9PVt8s(PaintingStyle.Companion.m963getStrokeTiuSbCo());
        innerBoundsPaint = Paint;
    }

    public InnerNodeCoordinator(LayoutNode layoutNode) {
        super(layoutNode);
        this.tail = new TailModifierNode();
        getTail().updateCoordinator$ui_release(this);
        this.lookaheadDelegate = layoutNode.getLookaheadRoot$ui_release() != null ? new LookaheadDelegateImpl() : null;
    }

    private final void onAfterPlaceAt() {
        if (isShallowPlacing$ui_release()) {
            return;
        }
        getLayoutNode().getMeasurePassDelegate$ui_release().onNodePlaced$ui_release();
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public int calculateAlignmentLine(AlignmentLine alignmentLine) {
        LookaheadDelegate lookaheadDelegate = getLookaheadDelegate();
        if (lookaheadDelegate != null) {
            return lookaheadDelegate.calculateAlignmentLine(alignmentLine);
        }
        Integer num = (Integer) getAlignmentLinesOwner().calculateAlignmentLines().get(alignmentLine);
        if (num != null) {
            return num.intValue();
        }
        return Integer.MIN_VALUE;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public void ensureLookaheadDelegateCreated() {
        if (getLookaheadDelegate() == null) {
            setLookaheadDelegate(new LookaheadDelegateImpl());
        }
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public LookaheadDelegate getLookaheadDelegate() {
        return this.lookaheadDelegate;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public TailModifierNode getTail() {
        return this.tail;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    /* JADX INFO: renamed from: hitTestChild-qzLsGqo, reason: not valid java name */
    public void mo1315hitTestChildqzLsGqo(NodeCoordinator.HitTestSource hitTestSource, long j, HitTestResult hitTestResult, int i, boolean z) {
        NodeCoordinator.HitTestSource hitTestSource2;
        boolean z2 = false;
        if (hitTestSource.shouldHitTestChildren(getLayoutNode())) {
            if (m1394withinLayerBoundsk4lQ0M(j)) {
                z2 = true;
            } else if (PointerType.m1255equalsimpl0(i, PointerType.Companion.m1261getTouchT8wyACA()) && (Float.floatToRawIntBits(m1384distanceInMinimumTouchTargettz77jQw(j, m1387getMinimumTouchTargetSizeNHjbRc())) & Integer.MAX_VALUE) < 2139095040) {
                z = false;
                z2 = true;
            }
        }
        if (z2) {
            int i2 = hitTestResult.hitDepth;
            MutableVector zSortedChildren = getLayoutNode().getZSortedChildren();
            Object[] objArr = zSortedChildren.content;
            int size = zSortedChildren.getSize() - 1;
            while (size >= 0) {
                LayoutNode layoutNode = (LayoutNode) objArr[size];
                if (layoutNode.isPlaced()) {
                    hitTestSource2 = hitTestSource;
                    hitTestSource2.mo1395childHitTestqzLsGqo(layoutNode, j, hitTestResult, i, z);
                    if (!hitTestResult.hasHit()) {
                        continue;
                    } else if (!layoutNode.getOuterCoordinator$ui_release().shouldSharePointerInputWithSiblings()) {
                        break;
                    } else {
                        hitTestResult.acceptHits();
                    }
                } else {
                    hitTestSource2 = hitTestSource;
                }
                size--;
                hitTestSource = hitTestSource2;
            }
            hitTestResult.hitDepth = i2;
        }
    }

    @Override // androidx.compose.ui.layout.Measurable
    /* JADX INFO: renamed from: measure-BRTryo0 */
    public Placeable mo1284measureBRTryo0(long j) {
        if (getForceMeasureWithLookaheadConstraints$ui_release()) {
            LookaheadDelegate lookaheadDelegate = getLookaheadDelegate();
            lookaheadDelegate.getClass();
            j = lookaheadDelegate.m1344getConstraintsmsEJaDk$ui_release();
        }
        m1294setMeasurementConstraintsBRTryo0(j);
        MutableVector mutableVector = getLayoutNode().get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            ((LayoutNode) objArr[i]).getMeasurePassDelegate$ui_release().setMeasuredByParent$ui_release(LayoutNode.UsageByParent.NotUsed);
        }
        setMeasureResult$ui_release(getLayoutNode().getMeasurePolicy().mo19measure3p2s80s(this, getLayoutNode().getChildMeasurables$ui_release(), j));
        onMeasured();
        return this;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public void performDraw(Canvas canvas, GraphicsLayer graphicsLayer) throws Throwable {
        Owner ownerRequireOwner = LayoutNodeKt.requireOwner(getLayoutNode());
        MutableVector zSortedChildren = getLayoutNode().getZSortedChildren();
        Object[] objArr = zSortedChildren.content;
        int size = zSortedChildren.getSize();
        for (int i = 0; i < size; i++) {
            LayoutNode layoutNode = (LayoutNode) objArr[i];
            if (layoutNode.isPlaced()) {
                layoutNode.draw$ui_release(canvas, graphicsLayer);
            }
        }
        if (ownerRequireOwner.getShowLayoutBounds()) {
            drawBorder(canvas, innerBoundsPaint);
        }
    }

    @Override // androidx.compose.ui.node.NodeCoordinator, androidx.compose.ui.layout.Placeable
    /* JADX INFO: renamed from: placeAt-f8xVGno */
    protected void mo1291placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) {
        super.mo1291placeAtf8xVGno(j, f, graphicsLayer);
        onAfterPlaceAt();
    }

    @Override // androidx.compose.ui.node.NodeCoordinator, androidx.compose.ui.layout.Placeable
    /* JADX INFO: renamed from: placeAt-f8xVGno */
    protected void mo1292placeAtf8xVGno(long j, float f, Function1 function1) {
        super.mo1292placeAtf8xVGno(j, f, function1);
        onAfterPlaceAt();
    }

    protected void setLookaheadDelegate(LookaheadDelegate lookaheadDelegate) {
        this.lookaheadDelegate = lookaheadDelegate;
    }
}
