package androidx.compose.foundation.gestures;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputChange;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: DragGestureDetector.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TouchSlopDetector {
    private final Orientation orientation;
    private long totalPositionChange;

    private TouchSlopDetector(Orientation orientation, long j) {
        this.orientation = orientation;
        this.totalPositionChange = j;
    }

    public /* synthetic */ TouchSlopDetector(Orientation orientation, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(orientation, j);
    }

    /* JADX INFO: renamed from: calculatePostSlopOffset-tuRUvjQ, reason: not valid java name */
    private final long m151calculatePostSlopOffsettuRUvjQ(float f) {
        if (this.orientation == null) {
            long j = this.totalPositionChange;
            return Offset.m763minusMKHz9U(this.totalPositionChange, Offset.m765timestuRUvjQ(Offset.m755divtuRUvjQ(j, Offset.m758getDistanceimpl(j)), f));
        }
        float fM154mainAxisk4lQ0M = m154mainAxisk4lQ0M(this.totalPositionChange) - (Math.signum(m154mainAxisk4lQ0M(this.totalPositionChange)) * f);
        float fM153crossAxisk4lQ0M = m153crossAxisk4lQ0M(this.totalPositionChange);
        if (this.orientation != Orientation.Horizontal) {
            return Offset.m752constructorimpl((((long) Float.floatToRawIntBits(fM153crossAxisk4lQ0M)) << 32) | (((long) Float.floatToRawIntBits(fM154mainAxisk4lQ0M)) & 4294967295L));
        }
        return Offset.m752constructorimpl((((long) Float.floatToRawIntBits(fM153crossAxisk4lQ0M)) & 4294967295L) | (Float.floatToRawIntBits(fM154mainAxisk4lQ0M) << 32));
    }

    /* JADX INFO: renamed from: addPointerInputChange-dBAh8RU, reason: not valid java name */
    public final long m152addPointerInputChangedBAh8RU(PointerInputChange pointerInputChange, float f) {
        long jM764plusMKHz9U = Offset.m764plusMKHz9U(this.totalPositionChange, Offset.m763minusMKHz9U(pointerInputChange.m1233getPositionF1C5BW0(), pointerInputChange.m1234getPreviousPositionF1C5BW0()));
        this.totalPositionChange = jM764plusMKHz9U;
        return (this.orientation == null ? Offset.m758getDistanceimpl(jM764plusMKHz9U) : Math.abs(m154mainAxisk4lQ0M(jM764plusMKHz9U))) >= f ? m151calculatePostSlopOffsettuRUvjQ(f) : Offset.Companion.m769getUnspecifiedF1C5BW0();
    }

    /* JADX INFO: renamed from: crossAxis-k-4lQ0M, reason: not valid java name */
    public final float m153crossAxisk4lQ0M(long j) {
        return Float.intBitsToFloat((int) (this.orientation == Orientation.Horizontal ? j & 4294967295L : j >> 32));
    }

    /* JADX INFO: renamed from: mainAxis-k-4lQ0M, reason: not valid java name */
    public final float m154mainAxisk4lQ0M(long j) {
        return Float.intBitsToFloat((int) (this.orientation == Orientation.Horizontal ? j >> 32 : j & 4294967295L));
    }

    public final void reset() {
        this.totalPositionChange = Offset.Companion.m770getZeroF1C5BW0();
    }
}
