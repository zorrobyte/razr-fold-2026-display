package androidx.compose.ui.layout;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.LookaheadDelegate;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntSize;

/* JADX INFO: compiled from: LookaheadLayoutCoordinates.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LookaheadLayoutCoordinates implements LayoutCoordinates {
    private final LookaheadDelegate lookaheadDelegate;

    public LookaheadLayoutCoordinates(LookaheadDelegate lookaheadDelegate) {
        this.lookaheadDelegate = lookaheadDelegate;
    }

    /* JADX INFO: renamed from: getLookaheadOffset-F1C5BW0, reason: not valid java name */
    private final long m1283getLookaheadOffsetF1C5BW0() {
        LookaheadDelegate rootLookaheadDelegate = LookaheadLayoutCoordinatesKt.getRootLookaheadDelegate(this.lookaheadDelegate);
        LayoutCoordinates coordinates = rootLookaheadDelegate.getCoordinates();
        Offset.Companion companion = Offset.Companion;
        return Offset.m763minusMKHz9U(mo1279localPositionOfR5De75A(coordinates, companion.m770getZeroF1C5BW0()), getCoordinator().mo1279localPositionOfR5De75A(rootLookaheadDelegate.getCoordinator(), companion.m770getZeroF1C5BW0()));
    }

    public final NodeCoordinator getCoordinator() {
        return this.lookaheadDelegate.getCoordinator();
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    public LayoutCoordinates getParentLayoutCoordinates() {
        LookaheadDelegate lookaheadDelegate;
        if (!isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("LayoutCoordinate operations are only valid when isAttached is true");
        }
        NodeCoordinator wrappedBy$ui_release = getCoordinator().getLayoutNode().getOuterCoordinator$ui_release().getWrappedBy$ui_release();
        if (wrappedBy$ui_release == null || (lookaheadDelegate = wrappedBy$ui_release.getLookaheadDelegate()) == null) {
            return null;
        }
        return lookaheadDelegate.getCoordinates();
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* JADX INFO: renamed from: getSize-YbymL2g */
    public long mo1278getSizeYbymL2g() {
        LookaheadDelegate lookaheadDelegate = this.lookaheadDelegate;
        return IntSize.m1919constructorimpl((((long) lookaheadDelegate.getWidth()) << 32) | (((long) lookaheadDelegate.getHeight()) & 4294967295L));
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    public boolean isAttached() {
        return getCoordinator().isAttached();
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    public Rect localBoundingBoxOf(LayoutCoordinates layoutCoordinates, boolean z) {
        return getCoordinator().localBoundingBoxOf(layoutCoordinates, z);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* JADX INFO: renamed from: localPositionOf-R5De75A */
    public long mo1279localPositionOfR5De75A(LayoutCoordinates layoutCoordinates, long j) {
        return mo1280localPositionOfS_NoaFU(layoutCoordinates, j, true);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* JADX INFO: renamed from: localPositionOf-S_NoaFU */
    public long mo1280localPositionOfS_NoaFU(LayoutCoordinates layoutCoordinates, long j, boolean z) {
        if (!(layoutCoordinates instanceof LookaheadLayoutCoordinates)) {
            LookaheadDelegate rootLookaheadDelegate = LookaheadLayoutCoordinatesKt.getRootLookaheadDelegate(this.lookaheadDelegate);
            long jMo1280localPositionOfS_NoaFU = mo1280localPositionOfS_NoaFU(rootLookaheadDelegate.getLookaheadLayoutCoordinates(), j, z);
            long jMo1340getPositionnOccac = rootLookaheadDelegate.mo1340getPositionnOccac();
            float fM1905getXimpl = IntOffset.m1905getXimpl(jMo1340getPositionnOccac);
            long jM763minusMKHz9U = Offset.m763minusMKHz9U(jMo1280localPositionOfS_NoaFU, Offset.m752constructorimpl((4294967295L & ((long) Float.floatToRawIntBits(IntOffset.m1906getYimpl(jMo1340getPositionnOccac)))) | (Float.floatToRawIntBits(fM1905getXimpl) << 32)));
            LayoutCoordinates parentCoordinates = rootLookaheadDelegate.getCoordinator().getParentCoordinates();
            if (parentCoordinates == null) {
                parentCoordinates = rootLookaheadDelegate.getCoordinator().getCoordinates();
            }
            return Offset.m764plusMKHz9U(jM763minusMKHz9U, parentCoordinates.mo1280localPositionOfS_NoaFU(layoutCoordinates, Offset.Companion.m770getZeroF1C5BW0(), z));
        }
        LookaheadDelegate lookaheadDelegate = ((LookaheadLayoutCoordinates) layoutCoordinates).lookaheadDelegate;
        lookaheadDelegate.getCoordinator().onCoordinatesUsed$ui_release();
        LookaheadDelegate lookaheadDelegate2 = getCoordinator().findCommonAncestor$ui_release(lookaheadDelegate.getCoordinator()).getLookaheadDelegate();
        if (lookaheadDelegate2 != null) {
            long jM1908minusqkQi6aY = IntOffset.m1908minusqkQi6aY(IntOffset.m1909plusqkQi6aY(lookaheadDelegate.m1346positionIniSbpLlY$ui_release(lookaheadDelegate2, !z), IntOffsetKt.m1916roundk4lQ0M(j)), this.lookaheadDelegate.m1346positionIniSbpLlY$ui_release(lookaheadDelegate2, !z));
            return Offset.m752constructorimpl((((long) Float.floatToRawIntBits(IntOffset.m1905getXimpl(jM1908minusqkQi6aY))) << 32) | (((long) Float.floatToRawIntBits(IntOffset.m1906getYimpl(jM1908minusqkQi6aY))) & 4294967295L));
        }
        LookaheadDelegate rootLookaheadDelegate2 = LookaheadLayoutCoordinatesKt.getRootLookaheadDelegate(lookaheadDelegate);
        long jM1909plusqkQi6aY = IntOffset.m1909plusqkQi6aY(IntOffset.m1909plusqkQi6aY(lookaheadDelegate.m1346positionIniSbpLlY$ui_release(rootLookaheadDelegate2, !z), rootLookaheadDelegate2.mo1340getPositionnOccac()), IntOffsetKt.m1916roundk4lQ0M(j));
        LookaheadDelegate rootLookaheadDelegate3 = LookaheadLayoutCoordinatesKt.getRootLookaheadDelegate(this.lookaheadDelegate);
        long jM1908minusqkQi6aY2 = IntOffset.m1908minusqkQi6aY(jM1909plusqkQi6aY, IntOffset.m1909plusqkQi6aY(this.lookaheadDelegate.m1346positionIniSbpLlY$ui_release(rootLookaheadDelegate3, !z), rootLookaheadDelegate3.mo1340getPositionnOccac()));
        long jM752constructorimpl = Offset.m752constructorimpl((((long) Float.floatToRawIntBits(IntOffset.m1905getXimpl(jM1908minusqkQi6aY2))) << 32) | (4294967295L & ((long) Float.floatToRawIntBits(IntOffset.m1906getYimpl(jM1908minusqkQi6aY2)))));
        NodeCoordinator wrappedBy$ui_release = rootLookaheadDelegate3.getCoordinator().getWrappedBy$ui_release();
        wrappedBy$ui_release.getClass();
        NodeCoordinator wrappedBy$ui_release2 = rootLookaheadDelegate2.getCoordinator().getWrappedBy$ui_release();
        wrappedBy$ui_release2.getClass();
        return wrappedBy$ui_release.mo1280localPositionOfS_NoaFU(wrappedBy$ui_release2, jM752constructorimpl, z);
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* JADX INFO: renamed from: localToRoot-MK-Hz9U */
    public long mo1281localToRootMKHz9U(long j) {
        return getCoordinator().mo1281localToRootMKHz9U(Offset.m764plusMKHz9U(j, m1283getLookaheadOffsetF1C5BW0()));
    }

    @Override // androidx.compose.ui.layout.LayoutCoordinates
    /* JADX INFO: renamed from: localToWindow-MK-Hz9U */
    public long mo1282localToWindowMKHz9U(long j) {
        return getCoordinator().mo1282localToWindowMKHz9U(Offset.m764plusMKHz9U(j, m1283getLookaheadOffsetF1C5BW0()));
    }
}
