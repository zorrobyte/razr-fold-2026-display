package androidx.compose.ui.spatial;

import android.os.Trace;
import androidx.collection.IntObjectMap;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Actual_androidKt;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.MatrixKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.MeasurePassDelegate;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.OwnedLayer;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: RectManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RectManager {
    private Object dispatchToken;
    private boolean isDirty;
    private boolean isFragmented;
    private boolean isScreenOrWindowDirty;
    private final IntObjectMap layoutNodes;
    private final RectList rects = new RectList();
    private final ThrottledCallbacks throttledCallbacks = new ThrottledCallbacks();
    private final MutableObjectList callbacks = new MutableObjectList(0, 1, null);
    private long scheduledDispatchDeadline = -1;
    private final Function0 dispatchLambda = new Function0() { // from class: androidx.compose.ui.spatial.RectManager$dispatchLambda$1
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Object invoke() {
            m1508invoke();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m1508invoke() {
            this.this$0.dispatchToken = null;
            RectManager rectManager = this.this$0;
            Trace.beginSection("OnPositionedDispatch");
            try {
                rectManager.dispatchCallbacks();
                Unit unit = Unit.INSTANCE;
            } finally {
                Trace.endSection();
            }
        }
    };
    private final MutableRect cachedRect = new MutableRect(0.0f, 0.0f, 0.0f, 0.0f);

    public RectManager(IntObjectMap intObjectMap) {
        this.layoutNodes = intObjectMap;
    }

    private final void boundingRectInRoot(NodeCoordinator nodeCoordinator, MutableRect mutableRect) {
        while (nodeCoordinator != null) {
            OwnedLayer layer = nodeCoordinator.getLayer();
            long jMo1340getPositionnOccac = nodeCoordinator.mo1340getPositionnOccac();
            mutableRect.m750translatek4lQ0M(Offset.m752constructorimpl((((long) Float.floatToRawIntBits(IntOffset.m1905getXimpl(jMo1340getPositionnOccac))) << 32) | (((long) Float.floatToRawIntBits(IntOffset.m1906getYimpl(jMo1340getPositionnOccac))) & 4294967295L)));
            nodeCoordinator = nodeCoordinator.getWrappedBy$ui_release();
            if (layer != null) {
                float[] fArrMo1406getUnderlyingMatrixsQKQjiQ = layer.mo1406getUnderlyingMatrixsQKQjiQ();
                if (!MatrixKt.m955isIdentity58bKbWc(fArrMo1406getUnderlyingMatrixsQKQjiQ)) {
                    Matrix.m945mapimpl(fArrMo1406getUnderlyingMatrixsQKQjiQ, mutableRect);
                }
            }
        }
    }

    private final void insertOrUpdate(LayoutNode layoutNode, boolean z, int i, int i2, int i3, int i4) {
        int semanticsId = layoutNode.getSemanticsId();
        if (z || !this.rects.move(semanticsId, i, i2, i3, i4)) {
            LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
            RectList.insert$default(this.rects, semanticsId, i, i2, i3, i4, parent$ui_release != null ? parent$ui_release.getSemanticsId() : -1, false, false, 192, null);
        }
        invalidate();
    }

    /* JADX INFO: renamed from: insertOrUpdateTransformedNode-70tqf50, reason: not valid java name */
    private final void m1503insertOrUpdateTransformedNode70tqf50(LayoutNode layoutNode, long j, boolean z) {
        NodeCoordinator outerCoordinator$ui_release = layoutNode.getOuterCoordinator$ui_release();
        MeasurePassDelegate measurePassDelegate$ui_release = layoutNode.getMeasurePassDelegate$ui_release();
        int measuredWidth = measurePassDelegate$ui_release.getMeasuredWidth();
        int measuredHeight = measurePassDelegate$ui_release.getMeasuredHeight();
        MutableRect mutableRect = this.cachedRect;
        mutableRect.set(IntOffset.m1905getXimpl(j), IntOffset.m1906getYimpl(j), IntOffset.m1905getXimpl(j) + measuredWidth, IntOffset.m1906getYimpl(j) + measuredHeight);
        boundingRectInRoot(outerCoordinator$ui_release, mutableRect);
        int left = (int) mutableRect.getLeft();
        int top = (int) mutableRect.getTop();
        int right = (int) mutableRect.getRight();
        int bottom = (int) mutableRect.getBottom();
        int semanticsId = layoutNode.getSemanticsId();
        if (z || !this.rects.update(semanticsId, left, top, right, bottom)) {
            LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
            RectList.insert$default(this.rects, semanticsId, left, top, right, bottom, parent$ui_release != null ? parent$ui_release.getSemanticsId() : -1, false, false, 192, null);
        }
        invalidate();
    }

    private final void insertOrUpdateTransformedNodeSubhierarchy(LayoutNode layoutNode) {
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i];
            m1503insertOrUpdateTransformedNode70tqf50(layoutNode2, layoutNode2.getOuterCoordinator$ui_release().mo1340getPositionnOccac(), false);
            insertOrUpdateTransformedNodeSubhierarchy(layoutNode2);
        }
    }

    /* JADX INFO: renamed from: outerToInnerOffset-Bjo55l4, reason: not valid java name */
    private final long m1504outerToInnerOffsetBjo55l4(LayoutNode layoutNode) {
        NodeCoordinator outerCoordinator$ui_release = layoutNode.getOuterCoordinator$ui_release();
        long jM770getZeroF1C5BW0 = Offset.Companion.m770getZeroF1C5BW0();
        NodeCoordinator innerCoordinator$ui_release = layoutNode.getInnerCoordinator$ui_release();
        while (innerCoordinator$ui_release != null && innerCoordinator$ui_release != outerCoordinator$ui_release) {
            OwnedLayer layer = innerCoordinator$ui_release.getLayer();
            jM770getZeroF1C5BW0 = IntOffsetKt.m1915plusNvtHpc(jM770getZeroF1C5BW0, innerCoordinator$ui_release.mo1340getPositionnOccac());
            innerCoordinator$ui_release = innerCoordinator$ui_release.getWrappedBy$ui_release();
            if (layer != null) {
                float[] fArrMo1406getUnderlyingMatrixsQKQjiQ = layer.mo1406getUnderlyingMatrixsQKQjiQ();
                int iM1511analyzeComponents58bKbWc = RectManagerKt.m1511analyzeComponents58bKbWc(fArrMo1406getUnderlyingMatrixsQKQjiQ);
                if (iM1511analyzeComponents58bKbWc == 3) {
                    continue;
                } else {
                    if ((iM1511analyzeComponents58bKbWc & 2) == 0) {
                        return IntOffset.Companion.m1912getMaxnOccac();
                    }
                    jM770getZeroF1C5BW0 = Matrix.m944mapMKHz9U(fArrMo1406getUnderlyingMatrixsQKQjiQ, jM770getZeroF1C5BW0);
                }
            }
        }
        return IntOffsetKt.m1916roundk4lQ0M(jM770getZeroF1C5BW0);
    }

    /* JADX INFO: renamed from: positionInRoot-Bjo55l4, reason: not valid java name */
    private final long m1505positionInRootBjo55l4(NodeCoordinator nodeCoordinator) {
        long jM770getZeroF1C5BW0 = Offset.Companion.m770getZeroF1C5BW0();
        while (nodeCoordinator != null) {
            OwnedLayer layer = nodeCoordinator.getLayer();
            jM770getZeroF1C5BW0 = IntOffsetKt.m1915plusNvtHpc(jM770getZeroF1C5BW0, nodeCoordinator.mo1340getPositionnOccac());
            nodeCoordinator = nodeCoordinator.getWrappedBy$ui_release();
            if (layer != null) {
                float[] fArrMo1406getUnderlyingMatrixsQKQjiQ = layer.mo1406getUnderlyingMatrixsQKQjiQ();
                int iM1511analyzeComponents58bKbWc = RectManagerKt.m1511analyzeComponents58bKbWc(fArrMo1406getUnderlyingMatrixsQKQjiQ);
                if (iM1511analyzeComponents58bKbWc == 3) {
                    continue;
                } else {
                    if ((iM1511analyzeComponents58bKbWc & 2) == 0) {
                        return IntOffset.Companion.m1912getMaxnOccac();
                    }
                    jM770getZeroF1C5BW0 = Matrix.m944mapMKHz9U(fArrMo1406getUnderlyingMatrixsQKQjiQ, jM770getZeroF1C5BW0);
                }
            }
        }
        return IntOffsetKt.m1916roundk4lQ0M(jM770getZeroF1C5BW0);
    }

    public final void dispatchCallbacks() {
        long jCurrentTimeMillis = Actual_androidKt.currentTimeMillis();
        boolean z = this.isDirty;
        boolean z2 = z || this.isScreenOrWindowDirty;
        if (z) {
            this.isDirty = false;
            MutableObjectList mutableObjectList = this.callbacks;
            Object[] objArr = mutableObjectList.content;
            int i = mutableObjectList._size;
            for (int i2 = 0; i2 < i; i2++) {
                ((Function0) objArr[i2]).invoke();
            }
            RectList rectList = this.rects;
            long[] jArr = rectList.items;
            int i3 = rectList.itemsSize;
            for (int i4 = 0; i4 < jArr.length - 2 && i4 < i3; i4 += 3) {
                long j = jArr[i4 + 2];
                if ((((int) (j >> 61)) & 1) != 0) {
                    this.throttledCallbacks.fireOnUpdatedRect(67108863 & ((int) j), jArr[i4], jArr[i4 + 1], jCurrentTimeMillis);
                }
            }
            this.rects.clearUpdated();
        }
        if (this.isScreenOrWindowDirty) {
            this.isScreenOrWindowDirty = false;
            this.throttledCallbacks.fireOnRectChangedEntries(jCurrentTimeMillis);
        }
        if (z2) {
            this.throttledCallbacks.fireGlobalChangeEntries(jCurrentTimeMillis);
        }
        if (this.isFragmented) {
            this.isFragmented = false;
            this.rects.defragment();
        }
        this.throttledCallbacks.triggerDebounced(jCurrentTimeMillis);
    }

    public final RectList getRects() {
        return this.rects;
    }

    public final void invalidate() {
        this.isDirty = true;
    }

    public final void invalidateCallbacksFor(LayoutNode layoutNode) {
        this.isDirty = true;
        this.rects.markUpdated(layoutNode.getSemanticsId());
        scheduleDebounceCallback(true);
    }

    public final void onLayoutLayerPositionalPropertiesChanged(LayoutNode layoutNode) {
        if (ComposeUiFlags.isRectTrackingEnabled) {
            long jM1504outerToInnerOffsetBjo55l4 = m1504outerToInnerOffsetBjo55l4(layoutNode);
            if (!RectManagerKt.m1512isSetgyyYBs(jM1504outerToInnerOffsetBjo55l4)) {
                insertOrUpdateTransformedNodeSubhierarchy(layoutNode);
                return;
            }
            layoutNode.m1330setOuterToInnerOffsetgyyYBs$ui_release(jM1504outerToInnerOffsetBjo55l4);
            layoutNode.setOuterToInnerOffsetDirty$ui_release(false);
            MutableVector mutableVector = layoutNode.get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int size = mutableVector.getSize();
            for (int i = 0; i < size; i++) {
                LayoutNode layoutNode2 = (LayoutNode) objArr[i];
                m1506onLayoutPositionChanged70tqf50(layoutNode2, layoutNode2.getOuterCoordinator$ui_release().mo1340getPositionnOccac(), false);
            }
            invalidateCallbacksFor(layoutNode);
        }
    }

    /* JADX INFO: renamed from: onLayoutPositionChanged-70tqf50, reason: not valid java name */
    public final void m1506onLayoutPositionChanged70tqf50(LayoutNode layoutNode, long j, boolean z) {
        long j2;
        long jM1505positionInRootBjo55l4;
        long jM1504outerToInnerOffsetBjo55l4;
        if (ComposeUiFlags.isRectTrackingEnabled) {
            MeasurePassDelegate measurePassDelegate$ui_release = layoutNode.getMeasurePassDelegate$ui_release();
            int measuredWidth = measurePassDelegate$ui_release.getMeasuredWidth();
            int measuredHeight = measurePassDelegate$ui_release.getMeasuredHeight();
            LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
            long jM1322getOffsetFromRootnOccac$ui_release = layoutNode.m1322getOffsetFromRootnOccac$ui_release();
            long jM1321getLastSizeYbymL2g$ui_release = layoutNode.m1321getLastSizeYbymL2g$ui_release();
            int i = (int) (jM1321getLastSizeYbymL2g$ui_release >> 32);
            int i2 = (int) (jM1321getLastSizeYbymL2g$ui_release & 4294967295L);
            boolean z2 = false;
            if (parent$ui_release != null) {
                boolean outerToInnerOffsetDirty$ui_release = parent$ui_release.getOuterToInnerOffsetDirty$ui_release();
                long jM1322getOffsetFromRootnOccac$ui_release2 = parent$ui_release.m1322getOffsetFromRootnOccac$ui_release();
                long jM1323getOuterToInnerOffsetnOccac$ui_release = parent$ui_release.m1323getOuterToInnerOffsetnOccac$ui_release();
                if (RectManagerKt.m1512isSetgyyYBs(jM1322getOffsetFromRootnOccac$ui_release2)) {
                    if (outerToInnerOffsetDirty$ui_release) {
                        j2 = 4294967295L;
                        jM1504outerToInnerOffsetBjo55l4 = m1504outerToInnerOffsetBjo55l4(parent$ui_release);
                        parent$ui_release.m1330setOuterToInnerOffsetgyyYBs$ui_release(jM1504outerToInnerOffsetBjo55l4);
                        parent$ui_release.setOuterToInnerOffsetDirty$ui_release(false);
                    } else {
                        j2 = 4294967295L;
                        jM1504outerToInnerOffsetBjo55l4 = jM1323getOuterToInnerOffsetnOccac$ui_release;
                    }
                    z2 = !RectManagerKt.m1512isSetgyyYBs(jM1504outerToInnerOffsetBjo55l4);
                    jM1505positionInRootBjo55l4 = IntOffset.m1909plusqkQi6aY(IntOffset.m1909plusqkQi6aY(jM1322getOffsetFromRootnOccac$ui_release2, jM1504outerToInnerOffsetBjo55l4), j);
                } else {
                    j2 = 4294967295L;
                    jM1505positionInRootBjo55l4 = m1505positionInRootBjo55l4(layoutNode.getOuterCoordinator$ui_release());
                }
            } else {
                j2 = 4294967295L;
                jM1505positionInRootBjo55l4 = j;
            }
            if (z2 || !RectManagerKt.m1512isSetgyyYBs(jM1505positionInRootBjo55l4)) {
                m1503insertOrUpdateTransformedNode70tqf50(layoutNode, j, z);
                return;
            }
            layoutNode.m1329setOffsetFromRootgyyYBs$ui_release(jM1505positionInRootBjo55l4);
            layoutNode.m1328setLastSizeozmzZPI$ui_release(IntSize.m1919constructorimpl((((long) measuredWidth) << 32) | (((long) measuredHeight) & j2)));
            int iM1905getXimpl = IntOffset.m1905getXimpl(jM1505positionInRootBjo55l4);
            int iM1906getYimpl = IntOffset.m1906getYimpl(jM1505positionInRootBjo55l4);
            int i3 = iM1905getXimpl + measuredWidth;
            int i4 = iM1906getYimpl + measuredHeight;
            if (!z && IntOffset.m1904equalsimpl0(jM1505positionInRootBjo55l4, jM1322getOffsetFromRootnOccac$ui_release) && i == measuredWidth && i2 == measuredHeight) {
                return;
            }
            insertOrUpdate(layoutNode, z, iM1905getXimpl, iM1906getYimpl, i3, i4);
        }
    }

    public final void remove(LayoutNode layoutNode) {
        this.rects.remove(layoutNode.getSemanticsId());
        invalidate();
        this.isFragmented = true;
    }

    public final void scheduleDebounceCallback(boolean z) {
        boolean z2 = (z && this.dispatchToken == null) ? false : true;
        long minDebounceDeadline = this.throttledCallbacks.getMinDebounceDeadline();
        if (minDebounceDeadline >= 0 || !z2) {
            if (this.scheduledDispatchDeadline == minDebounceDeadline && z2) {
                return;
            }
            Object obj = this.dispatchToken;
            if (obj != null) {
                Actual_androidKt.removePost(obj);
            }
            long jCurrentTimeMillis = Actual_androidKt.currentTimeMillis();
            long jMax = Math.max(minDebounceDeadline, ((long) 16) + jCurrentTimeMillis);
            this.scheduledDispatchDeadline = jMax;
            this.dispatchToken = Actual_androidKt.postDelayed(jMax - jCurrentTimeMillis, this.dispatchLambda);
        }
    }

    /* JADX INFO: renamed from: updateOffsets-ucfNpQE, reason: not valid java name */
    public final void m1507updateOffsetsucfNpQE(long j, long j2, float[] fArr) {
        int iM1511analyzeComponents58bKbWc = RectManagerKt.m1511analyzeComponents58bKbWc(fArr);
        ThrottledCallbacks throttledCallbacks = this.throttledCallbacks;
        if ((iM1511analyzeComponents58bKbWc & 2) != 0) {
            fArr = null;
        }
        this.isScreenOrWindowDirty = throttledCallbacks.m1513updateOffsetsbT0EZQs(j, j2, fArr) || this.isScreenOrWindowDirty;
    }
}
