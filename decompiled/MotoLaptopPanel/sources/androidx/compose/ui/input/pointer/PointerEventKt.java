package androidx.compose.ui.input.pointer;

import androidx.compose.ui.geometry.Offset;

/* JADX INFO: compiled from: PointerEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PointerEventKt {
    public static final boolean changedToDown(PointerInputChange pointerInputChange) {
        return (pointerInputChange.isConsumed() || pointerInputChange.getPreviousPressed() || !pointerInputChange.getPressed()) ? false : true;
    }

    public static final boolean changedToDownIgnoreConsumed(PointerInputChange pointerInputChange) {
        return !pointerInputChange.getPreviousPressed() && pointerInputChange.getPressed();
    }

    public static final boolean changedToUp(PointerInputChange pointerInputChange) {
        return (pointerInputChange.isConsumed() || !pointerInputChange.getPreviousPressed() || pointerInputChange.getPressed()) ? false : true;
    }

    public static final boolean changedToUpIgnoreConsumed(PointerInputChange pointerInputChange) {
        return pointerInputChange.getPreviousPressed() && !pointerInputChange.getPressed();
    }

    /* JADX INFO: renamed from: isOutOfBounds-O0kMr_c, reason: not valid java name */
    public static final boolean m1212isOutOfBoundsO0kMr_c(PointerInputChange pointerInputChange, long j) {
        long jM1233getPositionF1C5BW0 = pointerInputChange.m1233getPositionF1C5BW0();
        float fIntBitsToFloat = Float.intBitsToFloat((int) (jM1233getPositionF1C5BW0 >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jM1233getPositionF1C5BW0 & 4294967295L));
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        return (fIntBitsToFloat > ((float) i)) | (fIntBitsToFloat < 0.0f) | (fIntBitsToFloat2 < 0.0f) | (fIntBitsToFloat2 > ((float) i2));
    }

    /* JADX INFO: renamed from: isOutOfBounds-jwHxaWs, reason: not valid java name */
    public static final boolean m1213isOutOfBoundsjwHxaWs(PointerInputChange pointerInputChange, long j, long j2) {
        boolean zM1255equalsimpl0 = PointerType.m1255equalsimpl0(pointerInputChange.m1235getTypeT8wyACA(), PointerType.Companion.m1261getTouchT8wyACA());
        long jM1233getPositionF1C5BW0 = pointerInputChange.m1233getPositionF1C5BW0();
        float fIntBitsToFloat = Float.intBitsToFloat((int) (jM1233getPositionF1C5BW0 >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jM1233getPositionF1C5BW0 & 4294967295L));
        float fIntBitsToFloat3 = Float.intBitsToFloat((int) (j2 >> 32));
        float f = zM1255equalsimpl0 ? 1.0f : 0.0f;
        float f2 = fIntBitsToFloat3 * f;
        float f3 = ((int) (j >> 32)) + f2;
        float fIntBitsToFloat4 = Float.intBitsToFloat((int) (j2 & 4294967295L)) * f;
        return (fIntBitsToFloat > f3) | (fIntBitsToFloat < (-f2)) | (fIntBitsToFloat2 < (-fIntBitsToFloat4)) | (fIntBitsToFloat2 > ((int) (j & 4294967295L)) + fIntBitsToFloat4);
    }

    public static final long positionChange(PointerInputChange pointerInputChange) {
        return positionChangeInternal(pointerInputChange, false);
    }

    public static final long positionChangeIgnoreConsumed(PointerInputChange pointerInputChange) {
        return positionChangeInternal(pointerInputChange, true);
    }

    private static final long positionChangeInternal(PointerInputChange pointerInputChange, boolean z) {
        return (z || !pointerInputChange.isConsumed()) ? Offset.m763minusMKHz9U(pointerInputChange.m1233getPositionF1C5BW0(), pointerInputChange.m1234getPreviousPositionF1C5BW0()) : Offset.Companion.m770getZeroF1C5BW0();
    }

    public static final boolean positionChangedIgnoreConsumed(PointerInputChange pointerInputChange) {
        return !Offset.m757equalsimpl0(positionChangeInternal(pointerInputChange, true), Offset.Companion.m770getZeroF1C5BW0());
    }
}
