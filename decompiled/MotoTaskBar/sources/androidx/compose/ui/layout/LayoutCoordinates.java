package androidx.compose.ui.layout;

import androidx.compose.ui.geometry.Rect;

/* JADX INFO: compiled from: LayoutCoordinates.kt */
/* JADX INFO: loaded from: classes.dex */
public interface LayoutCoordinates {
    static /* synthetic */ Rect localBoundingBoxOf$default(LayoutCoordinates layoutCoordinates, LayoutCoordinates layoutCoordinates2, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: localBoundingBoxOf");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return layoutCoordinates.localBoundingBoxOf(layoutCoordinates2, z);
    }

    LayoutCoordinates getParentLayoutCoordinates();

    /* JADX INFO: renamed from: getSize-YbymL2g, reason: not valid java name */
    long mo531getSizeYbymL2g();

    boolean isAttached();

    Rect localBoundingBoxOf(LayoutCoordinates layoutCoordinates, boolean z);

    /* JADX INFO: renamed from: localPositionOf-R5De75A, reason: not valid java name */
    long mo532localPositionOfR5De75A(LayoutCoordinates layoutCoordinates, long j);

    /* JADX INFO: renamed from: localPositionOf-S_NoaFU, reason: not valid java name */
    long mo533localPositionOfS_NoaFU(LayoutCoordinates layoutCoordinates, long j, boolean z);

    /* JADX INFO: renamed from: localToRoot-MK-Hz9U, reason: not valid java name */
    long mo534localToRootMKHz9U(long j);

    /* JADX INFO: renamed from: localToWindow-MK-Hz9U, reason: not valid java name */
    long mo535localToWindowMKHz9U(long j);
}
