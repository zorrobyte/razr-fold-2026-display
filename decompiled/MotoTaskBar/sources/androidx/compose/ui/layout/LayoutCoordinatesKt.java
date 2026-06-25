package androidx.compose.ui.layout;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.node.NodeCoordinator;

/* JADX INFO: compiled from: LayoutCoordinates.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LayoutCoordinatesKt {
    public static final Rect boundsInParent(LayoutCoordinates layoutCoordinates) {
        Rect rectLocalBoundingBoxOf$default;
        LayoutCoordinates parentLayoutCoordinates = layoutCoordinates.getParentLayoutCoordinates();
        return (parentLayoutCoordinates == null || (rectLocalBoundingBoxOf$default = LayoutCoordinates.localBoundingBoxOf$default(parentLayoutCoordinates, layoutCoordinates, false, 2, null)) == null) ? new Rect(0.0f, 0.0f, (int) (layoutCoordinates.mo531getSizeYbymL2g() >> 32), (int) (layoutCoordinates.mo531getSizeYbymL2g() & 4294967295L)) : rectLocalBoundingBoxOf$default;
    }

    public static final Rect boundsInRoot(LayoutCoordinates layoutCoordinates) {
        return LayoutCoordinates.localBoundingBoxOf$default(findRootCoordinates(layoutCoordinates), layoutCoordinates, false, 2, null);
    }

    public static final Rect boundsInWindow(LayoutCoordinates layoutCoordinates) {
        LayoutCoordinates layoutCoordinatesFindRootCoordinates = findRootCoordinates(layoutCoordinates);
        float fMo531getSizeYbymL2g = (int) (layoutCoordinatesFindRootCoordinates.mo531getSizeYbymL2g() >> 32);
        float fMo531getSizeYbymL2g2 = (int) (layoutCoordinatesFindRootCoordinates.mo531getSizeYbymL2g() & 4294967295L);
        Rect rectLocalBoundingBoxOf$default = LayoutCoordinates.localBoundingBoxOf$default(layoutCoordinatesFindRootCoordinates, layoutCoordinates, false, 2, null);
        float left = rectLocalBoundingBoxOf$default.getLeft();
        if (left < 0.0f) {
            left = 0.0f;
        }
        if (left > fMo531getSizeYbymL2g) {
            left = fMo531getSizeYbymL2g;
        }
        float top = rectLocalBoundingBoxOf$default.getTop();
        if (top < 0.0f) {
            top = 0.0f;
        }
        if (top > fMo531getSizeYbymL2g2) {
            top = fMo531getSizeYbymL2g2;
        }
        float right = rectLocalBoundingBoxOf$default.getRight();
        if (right < 0.0f) {
            right = 0.0f;
        }
        if (right <= fMo531getSizeYbymL2g) {
            fMo531getSizeYbymL2g = right;
        }
        float bottom = rectLocalBoundingBoxOf$default.getBottom();
        float f = bottom >= 0.0f ? bottom : 0.0f;
        if (f <= fMo531getSizeYbymL2g2) {
            fMo531getSizeYbymL2g2 = f;
        }
        if (left == fMo531getSizeYbymL2g || top == fMo531getSizeYbymL2g2) {
            return Rect.Companion.getZero();
        }
        long jMo535localToWindowMKHz9U = layoutCoordinatesFindRootCoordinates.mo535localToWindowMKHz9U(Offset.m182constructorimpl((((long) Float.floatToRawIntBits(left)) << 32) | (((long) Float.floatToRawIntBits(top)) & 4294967295L)));
        long jMo535localToWindowMKHz9U2 = layoutCoordinatesFindRootCoordinates.mo535localToWindowMKHz9U(Offset.m182constructorimpl((((long) Float.floatToRawIntBits(top)) & 4294967295L) | (((long) Float.floatToRawIntBits(fMo531getSizeYbymL2g)) << 32)));
        long jMo535localToWindowMKHz9U3 = layoutCoordinatesFindRootCoordinates.mo535localToWindowMKHz9U(Offset.m182constructorimpl((((long) Float.floatToRawIntBits(fMo531getSizeYbymL2g)) << 32) | (((long) Float.floatToRawIntBits(fMo531getSizeYbymL2g2)) & 4294967295L)));
        long jMo535localToWindowMKHz9U4 = layoutCoordinatesFindRootCoordinates.mo535localToWindowMKHz9U(Offset.m182constructorimpl((((long) Float.floatToRawIntBits(fMo531getSizeYbymL2g2)) & 4294967295L) | (((long) Float.floatToRawIntBits(left)) << 32)));
        float fIntBitsToFloat = Float.intBitsToFloat((int) (jMo535localToWindowMKHz9U >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jMo535localToWindowMKHz9U2 >> 32));
        float fIntBitsToFloat3 = Float.intBitsToFloat((int) (jMo535localToWindowMKHz9U4 >> 32));
        float fIntBitsToFloat4 = Float.intBitsToFloat((int) (jMo535localToWindowMKHz9U3 >> 32));
        float fMin = Math.min(fIntBitsToFloat, Math.min(fIntBitsToFloat2, Math.min(fIntBitsToFloat3, fIntBitsToFloat4)));
        float fMax = Math.max(fIntBitsToFloat, Math.max(fIntBitsToFloat2, Math.max(fIntBitsToFloat3, fIntBitsToFloat4)));
        float fIntBitsToFloat5 = Float.intBitsToFloat((int) (jMo535localToWindowMKHz9U & 4294967295L));
        float fIntBitsToFloat6 = Float.intBitsToFloat((int) (jMo535localToWindowMKHz9U2 & 4294967295L));
        float fIntBitsToFloat7 = Float.intBitsToFloat((int) (jMo535localToWindowMKHz9U4 & 4294967295L));
        float fIntBitsToFloat8 = Float.intBitsToFloat((int) (jMo535localToWindowMKHz9U3 & 4294967295L));
        return new Rect(fMin, Math.min(fIntBitsToFloat5, Math.min(fIntBitsToFloat6, Math.min(fIntBitsToFloat7, fIntBitsToFloat8))), fMax, Math.max(fIntBitsToFloat5, Math.max(fIntBitsToFloat6, Math.max(fIntBitsToFloat7, fIntBitsToFloat8))));
    }

    public static final LayoutCoordinates findRootCoordinates(LayoutCoordinates layoutCoordinates) {
        LayoutCoordinates layoutCoordinates2;
        LayoutCoordinates parentLayoutCoordinates = layoutCoordinates.getParentLayoutCoordinates();
        while (true) {
            LayoutCoordinates layoutCoordinates3 = parentLayoutCoordinates;
            layoutCoordinates2 = layoutCoordinates;
            layoutCoordinates = layoutCoordinates3;
            if (layoutCoordinates == null) {
                break;
            }
            parentLayoutCoordinates = layoutCoordinates.getParentLayoutCoordinates();
        }
        NodeCoordinator nodeCoordinator = layoutCoordinates2 instanceof NodeCoordinator ? (NodeCoordinator) layoutCoordinates2 : null;
        if (nodeCoordinator == null) {
            return layoutCoordinates2;
        }
        NodeCoordinator wrappedBy$ui_release = nodeCoordinator.getWrappedBy$ui_release();
        while (true) {
            NodeCoordinator nodeCoordinator2 = wrappedBy$ui_release;
            NodeCoordinator nodeCoordinator3 = nodeCoordinator;
            nodeCoordinator = nodeCoordinator2;
            if (nodeCoordinator == null) {
                return nodeCoordinator3;
            }
            wrappedBy$ui_release = nodeCoordinator.getWrappedBy$ui_release();
        }
    }

    public static final long positionInRoot(LayoutCoordinates layoutCoordinates) {
        return layoutCoordinates.mo534localToRootMKHz9U(Offset.Companion.m195getZeroF1C5BW0());
    }
}
