package androidx.compose.ui.input.pointer;

import androidx.compose.ui.geometry.Offset;

/* JADX INFO: compiled from: PointerEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PointerEventKt {
    public static final boolean changedToDownIgnoreConsumed(PointerInputChange pointerInputChange) {
        return !pointerInputChange.getPreviousPressed() && pointerInputChange.getPressed();
    }

    public static final boolean changedToUpIgnoreConsumed(PointerInputChange pointerInputChange) {
        return pointerInputChange.getPreviousPressed() && !pointerInputChange.getPressed();
    }

    /* JADX INFO: renamed from: isOutOfBounds-O0kMr_c, reason: not valid java name */
    public static final boolean m479isOutOfBoundsO0kMr_c(PointerInputChange pointerInputChange, long j) {
        long jM497getPositionF1C5BW0 = pointerInputChange.m497getPositionF1C5BW0();
        float fIntBitsToFloat = Float.intBitsToFloat((int) (jM497getPositionF1C5BW0 >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jM497getPositionF1C5BW0 & 4294967295L));
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        return (fIntBitsToFloat > ((float) i)) | (fIntBitsToFloat < 0.0f) | (fIntBitsToFloat2 < 0.0f) | (fIntBitsToFloat2 > ((float) i2));
    }

    private static final long positionChangeInternal(PointerInputChange pointerInputChange, boolean z) {
        return (z || !pointerInputChange.isConsumed()) ? Offset.m189minusMKHz9U(pointerInputChange.m497getPositionF1C5BW0(), pointerInputChange.m498getPreviousPositionF1C5BW0()) : Offset.Companion.m195getZeroF1C5BW0();
    }

    public static final boolean positionChangedIgnoreConsumed(PointerInputChange pointerInputChange) {
        return !Offset.m186equalsimpl0(positionChangeInternal(pointerInputChange, true), Offset.Companion.m195getZeroF1C5BW0());
    }
}
