package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import java.util.List;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LookaheadPassDelegate.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LookaheadPassDelegate extends Placeable implements Measurable, AlignmentLinesOwner, MotionReferencePlacementDelegate {
    private boolean duringAlignmentLinesQuery;
    private boolean isPlacedUnderMotionFrameOfReference;
    private GraphicsLayer lastExplicitLayer;
    private Function1 lastLayerBlock;
    private float lastZIndex;
    private boolean layingOutChildren;
    private final LayoutNodeLayoutDelegate layoutNodeLayoutDelegate;
    private Constraints lookaheadConstraints;
    private boolean measuredOnce;
    private boolean onNodePlacedCalled;
    private boolean placedOnce;
    private boolean relayoutWithoutParentInProgress;
    private int previousPlaceOrder = Integer.MAX_VALUE;
    private int placeOrder = Integer.MAX_VALUE;
    private LayoutNode.UsageByParent measuredByParent = LayoutNode.UsageByParent.NotUsed;
    private long lastPosition = IntOffset.Companion.m1002getZeronOccac();
    private PlacedState _placedState = PlacedState.IsNotPlaced;
    private final AlignmentLines alignmentLines = new LookaheadAlignmentLines(this);
    private final MutableVector _childDelegates = new MutableVector(new LookaheadPassDelegate[16], 0);
    private boolean childDelegatesDirty = true;
    private boolean parentDataDirty = true;
    private Object parentData = getMeasurePassDelegate$ui_release().getParentData();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: LookaheadPassDelegate.kt */
    final class PlacedState {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ PlacedState[] $VALUES;
        public static final PlacedState IsPlacedInLookahead = new PlacedState("IsPlacedInLookahead", 0);
        public static final PlacedState IsPlacedInApproach = new PlacedState("IsPlacedInApproach", 1);
        public static final PlacedState IsNotPlaced = new PlacedState("IsNotPlaced", 2);

        private static final /* synthetic */ PlacedState[] $values() {
            return new PlacedState[]{IsPlacedInLookahead, IsPlacedInApproach, IsNotPlaced};
        }

        static {
            PlacedState[] placedStateArr$values = $values();
            $VALUES = placedStateArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(placedStateArr$values);
        }

        private PlacedState(String str, int i) {
        }

        public static PlacedState valueOf(String str) {
            return (PlacedState) Enum.valueOf(PlacedState.class, str);
        }

        public static PlacedState[] values() {
            return (PlacedState[]) $VALUES.clone();
        }
    }

    /* JADX INFO: compiled from: LookaheadPassDelegate.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[LayoutNode.LayoutState.values().length];
            try {
                iArr[LayoutNode.LayoutState.LookaheadMeasuring.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutNode.LayoutState.Measuring.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[LayoutNode.LayoutState.LayingOut.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[LayoutNode.LayoutState.LookaheadLayingOut.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[LayoutNode.UsageByParent.values().length];
            try {
                iArr2[LayoutNode.UsageByParent.InMeasureBlock.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[LayoutNode.UsageByParent.InLayoutBlock.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public LookaheadPassDelegate(LayoutNodeLayoutDelegate layoutNodeLayoutDelegate) {
        this.layoutNodeLayoutDelegate = layoutNodeLayoutDelegate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkChildrenPlaceOrderForUpdates() {
        MutableVector mutableVector = getLayoutNode().get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            LookaheadPassDelegate lookaheadPassDelegate$ui_release = ((LayoutNode) objArr[i]).getLayoutDelegate$ui_release().getLookaheadPassDelegate$ui_release();
            lookaheadPassDelegate$ui_release.getClass();
            int i2 = lookaheadPassDelegate$ui_release.previousPlaceOrder;
            int i3 = lookaheadPassDelegate$ui_release.placeOrder;
            if (i2 != i3 && i3 == Integer.MAX_VALUE) {
                lookaheadPassDelegate$ui_release.markNodeAndSubtreeAsNotPlaced$ui_release(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clearPlaceOrder() {
        this.layoutNodeLayoutDelegate.setNextChildLookaheadPlaceOrder$ui_release(0);
        MutableVector mutableVector = getLayoutNode().get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            LookaheadPassDelegate lookaheadPassDelegate$ui_release = ((LayoutNode) objArr[i]).getLayoutDelegate$ui_release().getLookaheadPassDelegate$ui_release();
            lookaheadPassDelegate$ui_release.getClass();
            lookaheadPassDelegate$ui_release.previousPlaceOrder = lookaheadPassDelegate$ui_release.placeOrder;
            lookaheadPassDelegate$ui_release.placeOrder = Integer.MAX_VALUE;
            if (lookaheadPassDelegate$ui_release.measuredByParent == LayoutNode.UsageByParent.InLayoutBlock) {
                lookaheadPassDelegate$ui_release.measuredByParent = LayoutNode.UsageByParent.NotUsed;
            }
        }
    }

    private final boolean getDetachedFromParentLookaheadPlacement() {
        return this.layoutNodeLayoutDelegate.getDetachedFromParentLookaheadPlacement$ui_release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LayoutNode getLayoutNode() {
        return this.layoutNodeLayoutDelegate.getLayoutNode$ui_release();
    }

    private final boolean getLayoutPending() {
        return this.layoutNodeLayoutDelegate.getLookaheadLayoutPending$ui_release();
    }

    private final boolean getLayoutPendingForAlignment() {
        return this.layoutNodeLayoutDelegate.getLookaheadLayoutPendingForAlignment$ui_release();
    }

    private final LayoutNode.LayoutState getLayoutState() {
        return this.layoutNodeLayoutDelegate.getLayoutState$ui_release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NodeCoordinator getOuterCoordinator() {
        return this.layoutNodeLayoutDelegate.getOuterCoordinator();
    }

    private final void markNodeAndSubtreeAsPlaced() {
        PlacedState placedState = this._placedState;
        if (getDetachedFromParentLookaheadPlacement()) {
            this._placedState = PlacedState.IsPlacedInApproach;
        } else {
            this._placedState = PlacedState.IsPlacedInLookahead;
        }
        if (placedState != PlacedState.IsPlacedInLookahead && this.layoutNodeLayoutDelegate.getLookaheadMeasurePending$ui_release()) {
            LayoutNode.requestLookaheadRemeasure$ui_release$default(getLayoutNode(), true, false, false, 6, null);
        }
        MutableVector mutableVector = getLayoutNode().get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            LayoutNode layoutNode = (LayoutNode) objArr[i];
            LookaheadPassDelegate lookaheadPassDelegate$ui_release = layoutNode.getLookaheadPassDelegate$ui_release();
            if (lookaheadPassDelegate$ui_release == null) {
                throw new IllegalArgumentException("Error: Child node's lookahead pass delegate cannot be null when in a lookahead scope.");
            }
            if (lookaheadPassDelegate$ui_release.placeOrder != Integer.MAX_VALUE) {
                lookaheadPassDelegate$ui_release.markNodeAndSubtreeAsPlaced();
                layoutNode.rescheduleRemeasureOrRelayout$ui_release(layoutNode);
            }
        }
    }

    private final void onBeforeLayoutChildren() {
        MutableVector mutableVector = getLayoutNode().get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            LayoutNode layoutNode = (LayoutNode) objArr[i];
            if (layoutNode.getLookaheadMeasurePending$ui_release() && layoutNode.getMeasuredByParentInLookahead$ui_release() == LayoutNode.UsageByParent.InMeasureBlock) {
                LookaheadPassDelegate lookaheadPassDelegate$ui_release = layoutNode.getLayoutDelegate$ui_release().getLookaheadPassDelegate$ui_release();
                lookaheadPassDelegate$ui_release.getClass();
                Constraints constraintsM592getLastLookaheadConstraintsDWUhwKw = layoutNode.getLayoutDelegate$ui_release().m592getLastLookaheadConstraintsDWUhwKw();
                constraintsM592getLastLookaheadConstraintsDWUhwKw.getClass();
                if (lookaheadPassDelegate$ui_release.m605remeasureBRTryo0(constraintsM592getLastLookaheadConstraintsDWUhwKw.m985unboximpl())) {
                    LayoutNode.requestLookaheadRemeasure$ui_release$default(getLayoutNode(), false, false, false, 7, null);
                }
            }
        }
    }

    /* JADX INFO: renamed from: placeSelf-MLgxB_4, reason: not valid java name */
    private final void m602placeSelfMLgxB_4(final long j, float f, Function1 function1, GraphicsLayer graphicsLayer) throws Throwable {
        LayoutNode layoutNode = getLayoutNode();
        try {
            LayoutNode parent$ui_release = getLayoutNode().getParent$ui_release();
            LayoutNode.LayoutState layoutState$ui_release = parent$ui_release != null ? parent$ui_release.getLayoutState$ui_release() : null;
            LayoutNode.LayoutState layoutState = LayoutNode.LayoutState.LookaheadLayingOut;
            if (layoutState$ui_release == layoutState) {
                this.layoutNodeLayoutDelegate.setDetachedFromParentLookaheadPlacement$ui_release(false);
            }
            if (getLayoutNode().isDeactivated()) {
                InlineClassHelperKt.throwIllegalArgumentException("place is called on a deactivated node");
            }
            setLayoutState(layoutState);
            this.placedOnce = true;
            this.onNodePlacedCalled = false;
            if (!IntOffset.m996equalsimpl0(j, this.lastPosition)) {
                if (this.layoutNodeLayoutDelegate.getLookaheadCoordinatesAccessedDuringModifierPlacement() || this.layoutNodeLayoutDelegate.getLookaheadCoordinatesAccessedDuringPlacement()) {
                    setLayoutPending(true);
                }
                notifyChildrenUsingLookaheadCoordinatesWhilePlacing();
            }
            final Owner ownerRequireOwner = LayoutNodeKt.requireOwner(getLayoutNode());
            if (getLayoutPending() || !isPlaced()) {
                this.layoutNodeLayoutDelegate.setLookaheadCoordinatesAccessedDuringModifierPlacement(false);
                getAlignmentLines().setUsedByModifierLayout$ui_release(false);
                OwnerSnapshotObserver.observeLayoutModifierSnapshotReads$ui_release$default(ownerRequireOwner.getSnapshotObserver(), getLayoutNode(), false, new Function0() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$placeSelf$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public /* bridge */ /* synthetic */ Object mo2224invoke() {
                        m608invoke();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                    public final void m608invoke() {
                        LookaheadDelegate lookaheadDelegate;
                        Placeable.PlacementScope placementScope = null;
                        if (LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(this.this$0.getLayoutNode()) || this.this$0.layoutNodeLayoutDelegate.getDetachedFromParentLookaheadPlacement$ui_release()) {
                            NodeCoordinator wrappedBy$ui_release = this.this$0.getOuterCoordinator().getWrappedBy$ui_release();
                            if (wrappedBy$ui_release != null) {
                                placementScope = wrappedBy$ui_release.getPlacementScope();
                            }
                        } else {
                            NodeCoordinator wrappedBy$ui_release2 = this.this$0.getOuterCoordinator().getWrappedBy$ui_release();
                            if (wrappedBy$ui_release2 != null && (lookaheadDelegate = wrappedBy$ui_release2.getLookaheadDelegate()) != null) {
                                placementScope = lookaheadDelegate.getPlacementScope();
                            }
                        }
                        if (placementScope == null) {
                            placementScope = ownerRequireOwner.getPlacementScope();
                        }
                        LookaheadPassDelegate lookaheadPassDelegate = this.this$0;
                        long j2 = j;
                        LookaheadDelegate lookaheadDelegate2 = lookaheadPassDelegate.getOuterCoordinator().getLookaheadDelegate();
                        lookaheadDelegate2.getClass();
                        Placeable.PlacementScope.m549place70tqf50$default(placementScope, lookaheadDelegate2, j2, 0.0f, 2, null);
                    }
                }, 2, null);
            } else {
                LookaheadDelegate lookaheadDelegate = getOuterCoordinator().getLookaheadDelegate();
                lookaheadDelegate.getClass();
                lookaheadDelegate.m599placeSelfApparentToRealOffsetgyyYBs$ui_release(j);
                onNodePlaced$ui_release();
            }
            this.lastPosition = j;
            this.lastZIndex = f;
            this.lastLayerBlock = function1;
            this.lastExplicitLayer = graphicsLayer;
            setLayoutState(LayoutNode.LayoutState.Idle);
            Unit unit = Unit.INSTANCE;
        } catch (Throwable th) {
            layoutNode.rethrowWithComposeStackTrace(th);
            throw new KotlinNothingValueException();
        }
    }

    private final void setLayoutPending(boolean z) {
        this.layoutNodeLayoutDelegate.setLookaheadLayoutPending$ui_release(z);
    }

    private final void setLayoutPendingForAlignment(boolean z) {
        this.layoutNodeLayoutDelegate.setLookaheadLayoutPendingForAlignment$ui_release(z);
    }

    private final void setLayoutState(LayoutNode.LayoutState layoutState) {
        this.layoutNodeLayoutDelegate.setLayoutState$ui_release(layoutState);
    }

    private final void setMeasurePending(boolean z) {
        this.layoutNodeLayoutDelegate.setLookaheadMeasurePending$ui_release(z);
    }

    private final void trackLookaheadMeasurementByParent(LayoutNode layoutNode) {
        LayoutNode.UsageByParent usageByParent;
        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
        if (parent$ui_release == null) {
            this.measuredByParent = LayoutNode.UsageByParent.NotUsed;
            return;
        }
        if (!(this.measuredByParent == LayoutNode.UsageByParent.NotUsed || layoutNode.getCanMultiMeasure$ui_release())) {
            InlineClassHelperKt.throwIllegalStateException("measure() may not be called multiple times on the same Measurable. If you want to get the content size of the Measurable before calculating the final constraints, please use methods like minIntrinsicWidth()/maxIntrinsicWidth() and minIntrinsicHeight()/maxIntrinsicHeight()");
        }
        int i = WhenMappings.$EnumSwitchMapping$0[parent$ui_release.getLayoutState$ui_release().ordinal()];
        if (i == 1 || i == 2) {
            usageByParent = LayoutNode.UsageByParent.InMeasureBlock;
        } else {
            if (i != 3 && i != 4) {
                throw new IllegalStateException("Measurable could be only measured from the parent's measure or layout block. Parents state is " + parent$ui_release.getLayoutState$ui_release());
            }
            usageByParent = LayoutNode.UsageByParent.InLayoutBlock;
        }
        this.measuredByParent = usageByParent;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public Map calculateAlignmentLines() {
        if (!this.duringAlignmentLinesQuery) {
            if (getLayoutState() == LayoutNode.LayoutState.LookaheadMeasuring) {
                getAlignmentLines().setUsedByModifierMeasurement$ui_release(true);
                if (getAlignmentLines().getDirty$ui_release()) {
                    this.layoutNodeLayoutDelegate.markLookaheadLayoutPending$ui_release();
                }
            } else {
                getAlignmentLines().setUsedByModifierLayout$ui_release(true);
            }
        }
        LookaheadDelegate lookaheadDelegate = getInnerCoordinator().getLookaheadDelegate();
        if (lookaheadDelegate != null) {
            lookaheadDelegate.setPlacingForAlignment$ui_release(true);
        }
        layoutChildren();
        LookaheadDelegate lookaheadDelegate2 = getInnerCoordinator().getLookaheadDelegate();
        if (lookaheadDelegate2 != null) {
            lookaheadDelegate2.setPlacingForAlignment$ui_release(false);
        }
        return getAlignmentLines().getLastCalculation();
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public void forEachChildAlignmentLinesOwner(Function1 function1) {
        MutableVector mutableVector = getLayoutNode().get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            AlignmentLinesOwner lookaheadAlignmentLinesOwner$ui_release = ((LayoutNode) objArr[i]).getLayoutDelegate$ui_release().getLookaheadAlignmentLinesOwner$ui_release();
            lookaheadAlignmentLinesOwner$ui_release.getClass();
            function1.invoke(lookaheadAlignmentLinesOwner$ui_release);
        }
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public AlignmentLines getAlignmentLines() {
        return this.alignmentLines;
    }

    public final List getChildDelegates$ui_release() {
        getLayoutNode().getChildren$ui_release();
        if (!this.childDelegatesDirty) {
            return this._childDelegates.asMutableList();
        }
        LayoutNode layoutNode = getLayoutNode();
        MutableVector mutableVector = this._childDelegates;
        MutableVector mutableVector2 = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector2.content;
        int size = mutableVector2.getSize();
        for (int i = 0; i < size; i++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i];
            if (mutableVector.getSize() <= i) {
                LookaheadPassDelegate lookaheadPassDelegate$ui_release = layoutNode2.getLayoutDelegate$ui_release().getLookaheadPassDelegate$ui_release();
                lookaheadPassDelegate$ui_release.getClass();
                mutableVector.add(lookaheadPassDelegate$ui_release);
            } else {
                LookaheadPassDelegate lookaheadPassDelegate$ui_release2 = layoutNode2.getLayoutDelegate$ui_release().getLookaheadPassDelegate$ui_release();
                lookaheadPassDelegate$ui_release2.getClass();
                mutableVector.set(i, lookaheadPassDelegate$ui_release2);
            }
        }
        mutableVector.removeRange(layoutNode.getChildren$ui_release().size(), mutableVector.getSize());
        this.childDelegatesDirty = false;
        return this._childDelegates.asMutableList();
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public NodeCoordinator getInnerCoordinator() {
        return getLayoutNode().getInnerCoordinator$ui_release();
    }

    /* JADX INFO: renamed from: getLastConstraints-DWUhwKw, reason: not valid java name */
    public final Constraints m603getLastConstraintsDWUhwKw() {
        return this.lookaheadConstraints;
    }

    public final boolean getLayingOutChildren() {
        return this.layingOutChildren;
    }

    public final MeasurePassDelegate getMeasurePassDelegate$ui_release() {
        return this.layoutNodeLayoutDelegate.getMeasurePassDelegate$ui_release();
    }

    public final LayoutNode.UsageByParent getMeasuredByParent$ui_release() {
        return this.measuredByParent;
    }

    public final boolean getNeedsToBePlacedInApproach() {
        if (LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(getLayoutNode())) {
            return true;
        }
        if (this._placedState == PlacedState.IsNotPlaced && !this.layoutNodeLayoutDelegate.getDetachedFromParentLookaheadPass$ui_release()) {
            this.layoutNodeLayoutDelegate.setDetachedFromParentLookaheadPlacement$ui_release(true);
        }
        return getDetachedFromParentLookaheadPlacement();
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public AlignmentLinesOwner getParentAlignmentLinesOwner() {
        LayoutNodeLayoutDelegate layoutDelegate$ui_release;
        LayoutNode parent$ui_release = getLayoutNode().getParent$ui_release();
        if (parent$ui_release == null || (layoutDelegate$ui_release = parent$ui_release.getLayoutDelegate$ui_release()) == null) {
            return null;
        }
        return layoutDelegate$ui_release.getLookaheadAlignmentLinesOwner$ui_release();
    }

    public Object getParentData() {
        return this.parentData;
    }

    public final boolean getPlacedOnce$ui_release() {
        return this.placedOnce;
    }

    public final void invalidateIntrinsicsParent(boolean z) {
        LayoutNode layoutNode;
        LayoutNode parent$ui_release = getLayoutNode().getParent$ui_release();
        LayoutNode.UsageByParent intrinsicsUsageByParent$ui_release = getLayoutNode().getIntrinsicsUsageByParent$ui_release();
        if (parent$ui_release == null || intrinsicsUsageByParent$ui_release == LayoutNode.UsageByParent.NotUsed) {
            return;
        }
        do {
            layoutNode = parent$ui_release;
            if (layoutNode.getIntrinsicsUsageByParent$ui_release() != intrinsicsUsageByParent$ui_release) {
                break;
            } else {
                parent$ui_release = layoutNode.getParent$ui_release();
            }
        } while (parent$ui_release != null);
        int i = WhenMappings.$EnumSwitchMapping$1[intrinsicsUsageByParent$ui_release.ordinal()];
        if (i == 1) {
            if (layoutNode.getLookaheadRoot$ui_release() != null) {
                LayoutNode.requestLookaheadRemeasure$ui_release$default(layoutNode, z, false, false, 6, null);
                return;
            } else {
                LayoutNode.requestRemeasure$ui_release$default(layoutNode, z, false, false, 6, null);
                return;
            }
        }
        if (i != 2) {
            throw new IllegalStateException("Intrinsics isn't used by the parent");
        }
        if (layoutNode.getLookaheadRoot$ui_release() != null) {
            layoutNode.requestLookaheadRelayout$ui_release(z);
        } else {
            layoutNode.requestRelayout$ui_release(z);
        }
    }

    public final void invalidateParentData() {
        this.parentDataDirty = true;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public boolean isPlaced() {
        return this._placedState != PlacedState.IsNotPlaced;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public void layoutChildren() {
        this.layingOutChildren = true;
        getAlignmentLines().recalculateQueryOwner();
        if (getLayoutPending()) {
            onBeforeLayoutChildren();
        }
        final LookaheadDelegate lookaheadDelegate = getInnerCoordinator().getLookaheadDelegate();
        lookaheadDelegate.getClass();
        if (getLayoutPendingForAlignment() || (!this.duringAlignmentLinesQuery && !lookaheadDelegate.isPlacingForAlignment$ui_release() && getLayoutPending())) {
            setLayoutPending(false);
            LayoutNode.LayoutState layoutState = getLayoutState();
            setLayoutState(LayoutNode.LayoutState.LookaheadLayingOut);
            Owner ownerRequireOwner = LayoutNodeKt.requireOwner(getLayoutNode());
            this.layoutNodeLayoutDelegate.setLookaheadCoordinatesAccessedDuringPlacement(false);
            OwnerSnapshotObserver.observeLayoutSnapshotReads$ui_release$default(ownerRequireOwner.getSnapshotObserver(), getLayoutNode(), false, new Function0() { // from class: androidx.compose.ui.node.LookaheadPassDelegate.layoutChildren.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public /* bridge */ /* synthetic */ Object mo2224invoke() {
                    m606invoke();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m606invoke() {
                    LookaheadPassDelegate.this.clearPlaceOrder();
                    LookaheadPassDelegate.this.forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.LookaheadPassDelegate.layoutChildren.1.1
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                            invoke((AlignmentLinesOwner) obj);
                            return Unit.INSTANCE;
                        }

                        public final void invoke(AlignmentLinesOwner alignmentLinesOwner) {
                            alignmentLinesOwner.getAlignmentLines().setUsedDuringParentLayout$ui_release(false);
                        }
                    });
                    LookaheadDelegate lookaheadDelegate2 = LookaheadPassDelegate.this.getInnerCoordinator().getLookaheadDelegate();
                    if (lookaheadDelegate2 != null) {
                        boolean zIsPlacingForAlignment$ui_release = lookaheadDelegate2.isPlacingForAlignment$ui_release();
                        List children$ui_release = LookaheadPassDelegate.this.getLayoutNode().getChildren$ui_release();
                        int size = children$ui_release.size();
                        for (int i = 0; i < size; i++) {
                            LookaheadDelegate lookaheadDelegate3 = ((LayoutNode) children$ui_release.get(i)).getOuterCoordinator$ui_release().getLookaheadDelegate();
                            if (lookaheadDelegate3 != null) {
                                lookaheadDelegate3.setPlacingForAlignment$ui_release(zIsPlacingForAlignment$ui_release);
                            }
                        }
                    }
                    lookaheadDelegate.getMeasureResult$ui_release().placeChildren();
                    LookaheadDelegate lookaheadDelegate4 = LookaheadPassDelegate.this.getInnerCoordinator().getLookaheadDelegate();
                    if (lookaheadDelegate4 != null) {
                        lookaheadDelegate4.isPlacingForAlignment$ui_release();
                        List children$ui_release2 = LookaheadPassDelegate.this.getLayoutNode().getChildren$ui_release();
                        int size2 = children$ui_release2.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            LookaheadDelegate lookaheadDelegate5 = ((LayoutNode) children$ui_release2.get(i2)).getOuterCoordinator$ui_release().getLookaheadDelegate();
                            if (lookaheadDelegate5 != null) {
                                lookaheadDelegate5.setPlacingForAlignment$ui_release(false);
                            }
                        }
                    }
                    LookaheadPassDelegate.this.checkChildrenPlaceOrderForUpdates();
                    LookaheadPassDelegate.this.forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.LookaheadPassDelegate.layoutChildren.1.4
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                            invoke((AlignmentLinesOwner) obj);
                            return Unit.INSTANCE;
                        }

                        public final void invoke(AlignmentLinesOwner alignmentLinesOwner) {
                            alignmentLinesOwner.getAlignmentLines().setPreviousUsedDuringParentLayout$ui_release(alignmentLinesOwner.getAlignmentLines().getUsedDuringParentLayout$ui_release());
                        }
                    });
                }
            }, 2, null);
            setLayoutState(layoutState);
            if (this.layoutNodeLayoutDelegate.getLookaheadCoordinatesAccessedDuringPlacement() && lookaheadDelegate.isPlacingForAlignment$ui_release()) {
                requestLayout();
            }
            setLayoutPendingForAlignment(false);
        }
        if (getAlignmentLines().getUsedDuringParentLayout$ui_release()) {
            getAlignmentLines().setPreviousUsedDuringParentLayout$ui_release(true);
        }
        if (getAlignmentLines().getDirty$ui_release() && getAlignmentLines().getRequired$ui_release()) {
            getAlignmentLines().recalculate();
        }
        this.layingOutChildren = false;
    }

    public final void markLayoutPending$ui_release() {
        setLayoutPending(true);
        setLayoutPendingForAlignment(true);
    }

    public final void markNodeAndSubtreeAsNotPlaced$ui_release(boolean z) {
        if (z && getDetachedFromParentLookaheadPlacement()) {
            return;
        }
        if (z || getDetachedFromParentLookaheadPlacement()) {
            this._placedState = PlacedState.IsNotPlaced;
            MutableVector mutableVector = getLayoutNode().get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int size = mutableVector.getSize();
            for (int i = 0; i < size; i++) {
                LookaheadPassDelegate lookaheadPassDelegate$ui_release = ((LayoutNode) objArr[i]).getLayoutDelegate$ui_release().getLookaheadPassDelegate$ui_release();
                lookaheadPassDelegate$ui_release.getClass();
                lookaheadPassDelegate$ui_release.markNodeAndSubtreeAsNotPlaced$ui_release(true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0027  */
    @Override // androidx.compose.ui.layout.Measurable
    /* JADX INFO: renamed from: measure-BRTryo0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public androidx.compose.ui.layout.Placeable mo537measureBRTryo0(long r4) throws java.lang.Throwable {
        /*
            r3 = this;
            androidx.compose.ui.node.LayoutNode r0 = r3.getLayoutNode()
            androidx.compose.ui.node.LayoutNode r0 = r0.getParent$ui_release()
            r1 = 0
            if (r0 == 0) goto L10
            androidx.compose.ui.node.LayoutNode$LayoutState r0 = r0.getLayoutState$ui_release()
            goto L11
        L10:
            r0 = r1
        L11:
            androidx.compose.ui.node.LayoutNode$LayoutState r2 = androidx.compose.ui.node.LayoutNode.LayoutState.LookaheadMeasuring
            if (r0 == r2) goto L27
            androidx.compose.ui.node.LayoutNode r0 = r3.getLayoutNode()
            androidx.compose.ui.node.LayoutNode r0 = r0.getParent$ui_release()
            if (r0 == 0) goto L23
            androidx.compose.ui.node.LayoutNode$LayoutState r1 = r0.getLayoutState$ui_release()
        L23:
            androidx.compose.ui.node.LayoutNode$LayoutState r0 = androidx.compose.ui.node.LayoutNode.LayoutState.LookaheadLayingOut
            if (r1 != r0) goto L2d
        L27:
            androidx.compose.ui.node.LayoutNodeLayoutDelegate r0 = r3.layoutNodeLayoutDelegate
            r1 = 0
            r0.setDetachedFromParentLookaheadPass$ui_release(r1)
        L2d:
            androidx.compose.ui.node.LayoutNode r0 = r3.getLayoutNode()
            r3.trackLookaheadMeasurementByParent(r0)
            androidx.compose.ui.node.LayoutNode r0 = r3.getLayoutNode()
            androidx.compose.ui.node.LayoutNode$UsageByParent r0 = r0.getIntrinsicsUsageByParent$ui_release()
            androidx.compose.ui.node.LayoutNode$UsageByParent r1 = androidx.compose.ui.node.LayoutNode.UsageByParent.NotUsed
            if (r0 != r1) goto L47
            androidx.compose.ui.node.LayoutNode r0 = r3.getLayoutNode()
            r0.clearSubtreeIntrinsicsUsage$ui_release()
        L47:
            r3.m605remeasureBRTryo0(r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.LookaheadPassDelegate.mo537measureBRTryo0(long):androidx.compose.ui.layout.Placeable");
    }

    public final void notifyChildrenUsingLookaheadCoordinatesWhilePlacing() {
        if (this.layoutNodeLayoutDelegate.getChildrenAccessingLookaheadCoordinatesDuringPlacement() > 0) {
            MutableVector mutableVector = getLayoutNode().get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int size = mutableVector.getSize();
            for (int i = 0; i < size; i++) {
                LayoutNode layoutNode = (LayoutNode) objArr[i];
                LayoutNodeLayoutDelegate layoutDelegate$ui_release = layoutNode.getLayoutDelegate$ui_release();
                if ((layoutDelegate$ui_release.getLookaheadCoordinatesAccessedDuringPlacement() || layoutDelegate$ui_release.getLookaheadCoordinatesAccessedDuringModifierPlacement()) && !layoutDelegate$ui_release.getLookaheadLayoutPending$ui_release()) {
                    LayoutNode.requestLookaheadRelayout$ui_release$default(layoutNode, false, 1, null);
                }
                LookaheadPassDelegate lookaheadPassDelegate$ui_release = layoutDelegate$ui_release.getLookaheadPassDelegate$ui_release();
                if (lookaheadPassDelegate$ui_release != null) {
                    lookaheadPassDelegate$ui_release.notifyChildrenUsingLookaheadCoordinatesWhilePlacing();
                }
            }
        }
    }

    public final void onAttachedToNullParent() {
        this._placedState = PlacedState.IsPlacedInLookahead;
    }

    public final void onNodeDetached() {
        this.placeOrder = Integer.MAX_VALUE;
        this.previousPlaceOrder = Integer.MAX_VALUE;
        this._placedState = PlacedState.IsNotPlaced;
    }

    public final void onNodePlaced$ui_release() {
        this.onNodePlacedCalled = true;
        LayoutNode parent$ui_release = getLayoutNode().getParent$ui_release();
        if ((this._placedState != PlacedState.IsPlacedInLookahead && !getDetachedFromParentLookaheadPlacement()) || (this._placedState != PlacedState.IsPlacedInApproach && getDetachedFromParentLookaheadPlacement())) {
            markNodeAndSubtreeAsPlaced();
            if (this.relayoutWithoutParentInProgress && parent$ui_release != null) {
                LayoutNode.requestLookaheadRelayout$ui_release$default(parent$ui_release, false, 1, null);
            }
        }
        if (parent$ui_release == null) {
            this.placeOrder = 0;
        } else if (!this.relayoutWithoutParentInProgress && (parent$ui_release.getLayoutState$ui_release() == LayoutNode.LayoutState.LayingOut || parent$ui_release.getLayoutState$ui_release() == LayoutNode.LayoutState.LookaheadLayingOut)) {
            if (!(this.placeOrder == Integer.MAX_VALUE)) {
                InlineClassHelperKt.throwIllegalStateException("Place was called on a node which was placed already");
            }
            this.placeOrder = parent$ui_release.getLayoutDelegate$ui_release().getNextChildLookaheadPlaceOrder$ui_release();
            LayoutNodeLayoutDelegate layoutDelegate$ui_release = parent$ui_release.getLayoutDelegate$ui_release();
            layoutDelegate$ui_release.setNextChildLookaheadPlaceOrder$ui_release(layoutDelegate$ui_release.getNextChildLookaheadPlaceOrder$ui_release() + 1);
        }
        layoutChildren();
    }

    /* JADX INFO: renamed from: performMeasure-BRTryo0$ui_release, reason: not valid java name */
    public final void m604performMeasureBRTryo0$ui_release(final long j) {
        setLayoutState(LayoutNode.LayoutState.LookaheadMeasuring);
        setMeasurePending(false);
        OwnerSnapshotObserver.observeMeasureSnapshotReads$ui_release$default(LayoutNodeKt.requireOwner(getLayoutNode()).getSnapshotObserver(), getLayoutNode(), false, new Function0() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$performMeasure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public /* bridge */ /* synthetic */ Object mo2224invoke() {
                m607invoke();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
            public final void m607invoke() {
                LookaheadDelegate lookaheadDelegate = this.this$0.getOuterCoordinator().getLookaheadDelegate();
                lookaheadDelegate.getClass();
                lookaheadDelegate.mo537measureBRTryo0(j);
            }
        }, 2, null);
        markLayoutPending$ui_release();
        if (LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(getLayoutNode())) {
            getMeasurePassDelegate$ui_release().markLayoutPending();
        } else {
            getMeasurePassDelegate$ui_release().markMeasurePending$ui_release();
        }
        setLayoutState(LayoutNode.LayoutState.Idle);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.compose.ui.layout.Placeable
    /* JADX INFO: renamed from: placeAt-f8xVGno */
    public void mo545placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) throws Throwable {
        m602placeSelfMLgxB_4(j, f, null, graphicsLayer);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.compose.ui.layout.Placeable
    /* JADX INFO: renamed from: placeAt-f8xVGno */
    public void mo546placeAtf8xVGno(long j, float f, Function1 function1) throws Throwable {
        m602placeSelfMLgxB_4(j, f, function1, null);
    }

    /* JADX INFO: renamed from: remeasure-BRTryo0, reason: not valid java name */
    public final boolean m605remeasureBRTryo0(long j) throws Throwable {
        long jM1008constructorimpl;
        LayoutNode layoutNode = getLayoutNode();
        try {
            if (getLayoutNode().isDeactivated()) {
                InlineClassHelperKt.throwIllegalArgumentException("measure is called on a deactivated node");
            }
            LayoutNode parent$ui_release = getLayoutNode().getParent$ui_release();
            getLayoutNode().setCanMultiMeasure$ui_release(getLayoutNode().getCanMultiMeasure$ui_release() || (parent$ui_release != null && parent$ui_release.getCanMultiMeasure$ui_release()));
            if (!getLayoutNode().getLookaheadMeasurePending$ui_release()) {
                Constraints constraints = this.lookaheadConstraints;
                if (constraints == null ? false : Constraints.m976equalsimpl0(constraints.m985unboximpl(), j)) {
                    Owner owner$ui_release = getLayoutNode().getOwner$ui_release();
                    if (owner$ui_release != null) {
                        owner$ui_release.forceMeasureTheSubtree(getLayoutNode(), true);
                    }
                    getLayoutNode().resetSubtreeIntrinsicsUsage$ui_release();
                    return false;
                }
            }
            this.lookaheadConstraints = Constraints.m973boximpl(j);
            m548setMeasurementConstraintsBRTryo0(j);
            getAlignmentLines().setUsedByModifierMeasurement$ui_release(false);
            forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    invoke((AlignmentLinesOwner) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(AlignmentLinesOwner alignmentLinesOwner) {
                    alignmentLinesOwner.getAlignmentLines().setUsedDuringParentMeasurement$ui_release(false);
                }
            });
            if (this.measuredOnce) {
                jM1008constructorimpl = m543getMeasuredSizeYbymL2g();
            } else {
                long j2 = Integer.MIN_VALUE;
                jM1008constructorimpl = IntSize.m1008constructorimpl((j2 & 4294967295L) | (j2 << 32));
            }
            this.measuredOnce = true;
            LookaheadDelegate lookaheadDelegate = getOuterCoordinator().getLookaheadDelegate();
            if (!(lookaheadDelegate != null)) {
                InlineClassHelperKt.throwIllegalStateException("Lookahead result from lookaheadRemeasure cannot be null");
            }
            this.layoutNodeLayoutDelegate.m593performLookaheadMeasureBRTryo0$ui_release(j);
            m547setMeasuredSizeozmzZPI(IntSize.m1008constructorimpl((((long) lookaheadDelegate.getHeight()) & 4294967295L) | (((long) lookaheadDelegate.getWidth()) << 32)));
            return (((int) (jM1008constructorimpl >> 32)) == lookaheadDelegate.getWidth() && ((int) (jM1008constructorimpl & 4294967295L)) == lookaheadDelegate.getHeight()) ? false : true;
        } catch (Throwable th) {
            layoutNode.rethrowWithComposeStackTrace(th);
            throw new KotlinNothingValueException();
        }
    }

    public final void replace() {
        LookaheadPassDelegate lookaheadPassDelegate;
        LayoutNode parent$ui_release;
        try {
            this.relayoutWithoutParentInProgress = true;
            if (!this.placedOnce) {
                InlineClassHelperKt.throwIllegalStateException("replace() called on item that was not placed");
            }
            this.onNodePlacedCalled = false;
            boolean zIsPlaced = isPlaced();
            lookaheadPassDelegate = this;
            try {
                lookaheadPassDelegate.m602placeSelfMLgxB_4(this.lastPosition, 0.0f, this.lastLayerBlock, this.lastExplicitLayer);
                if (zIsPlaced && !lookaheadPassDelegate.onNodePlacedCalled && (parent$ui_release = lookaheadPassDelegate.getLayoutNode().getParent$ui_release()) != null) {
                    LayoutNode.requestLookaheadRelayout$ui_release$default(parent$ui_release, false, 1, null);
                }
                lookaheadPassDelegate.relayoutWithoutParentInProgress = false;
            } catch (Throwable th) {
                th = th;
                lookaheadPassDelegate.relayoutWithoutParentInProgress = false;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            lookaheadPassDelegate = this;
        }
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public void requestLayout() {
        LayoutNode.requestLookaheadRelayout$ui_release$default(getLayoutNode(), false, 1, null);
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public void requestMeasure() {
        LayoutNode.requestLookaheadRemeasure$ui_release$default(getLayoutNode(), false, false, false, 7, null);
    }

    public final void setChildDelegatesDirty$ui_release(boolean z) {
        this.childDelegatesDirty = z;
    }

    public final void setMeasuredByParent$ui_release(LayoutNode.UsageByParent usageByParent) {
        this.measuredByParent = usageByParent;
    }

    public final void setPlaceOrder$ui_release(int i) {
        this.placeOrder = i;
    }

    public void setPlacedUnderMotionFrameOfReference(boolean z) {
        this.isPlacedUnderMotionFrameOfReference = z;
    }

    public final boolean updateParentData() {
        if (getParentData() == null) {
            LookaheadDelegate lookaheadDelegate = getOuterCoordinator().getLookaheadDelegate();
            lookaheadDelegate.getClass();
            if (lookaheadDelegate.getParentData() == null) {
                return false;
            }
        }
        if (!this.parentDataDirty) {
            return false;
        }
        this.parentDataDirty = false;
        LookaheadDelegate lookaheadDelegate2 = getOuterCoordinator().getLookaheadDelegate();
        lookaheadDelegate2.getClass();
        this.parentData = lookaheadDelegate2.getParentData();
        return true;
    }

    @Override // androidx.compose.ui.node.MotionReferencePlacementDelegate
    public void updatePlacedUnderMotionFrameOfReference(boolean z) {
        LookaheadDelegate lookaheadDelegate;
        LookaheadDelegate lookaheadDelegate2 = getOuterCoordinator().getLookaheadDelegate();
        if (!Intrinsics.areEqual(Boolean.valueOf(z), lookaheadDelegate2 != null ? Boolean.valueOf(lookaheadDelegate2.isPlacedUnderMotionFrameOfReference()) : null) && (lookaheadDelegate = getOuterCoordinator().getLookaheadDelegate()) != null) {
            lookaheadDelegate.setPlacedUnderMotionFrameOfReference(z);
        }
        setPlacedUnderMotionFrameOfReference(z);
    }
}
