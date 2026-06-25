package androidx.compose.ui.input.pointer;

import androidx.collection.LongSparseArray;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PointerInputEventProcessor.kt */
/* JADX INFO: loaded from: classes.dex */
final class PointerInputChangeEventProducer {
    private final LongSparseArray previousPointerInputData = new LongSparseArray(0, 1, null);

    /* JADX INFO: compiled from: PointerInputEventProcessor.kt */
    final class PointerInputData {
        private final boolean down;
        private final long positionOnScreen;
        private final long uptime;

        private PointerInputData(long j, long j2, boolean z) {
            this.uptime = j;
            this.positionOnScreen = j2;
            this.down = z;
        }

        public /* synthetic */ PointerInputData(long j, long j2, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, j2, z);
        }

        public final boolean getDown() {
            return this.down;
        }

        /* JADX INFO: renamed from: getPositionOnScreen-F1C5BW0, reason: not valid java name */
        public final long m500getPositionOnScreenF1C5BW0() {
            return this.positionOnScreen;
        }

        public final long getUptime() {
            return this.uptime;
        }
    }

    public final void clear() {
        this.previousPointerInputData.clear();
    }

    public final InternalPointerEvent produce(PointerInputEvent pointerInputEvent, PositionCalculator positionCalculator) {
        long uptime;
        boolean down;
        long jMo523screenToLocalMKHz9U;
        LongSparseArray longSparseArray = new LongSparseArray(pointerInputEvent.getPointers().size());
        List pointers = pointerInputEvent.getPointers();
        int size = pointers.size();
        for (int i = 0; i < size; i++) {
            PointerInputEventData pointerInputEventData = (PointerInputEventData) pointers.get(i);
            PointerInputData pointerInputData = (PointerInputData) this.previousPointerInputData.get(pointerInputEventData.m501getIdJ3iCeTQ());
            if (pointerInputData == null) {
                down = false;
                uptime = pointerInputEventData.getUptime();
                jMo523screenToLocalMKHz9U = pointerInputEventData.m503getPositionF1C5BW0();
            } else {
                uptime = pointerInputData.getUptime();
                down = pointerInputData.getDown();
                jMo523screenToLocalMKHz9U = positionCalculator.mo523screenToLocalMKHz9U(pointerInputData.m500getPositionOnScreenF1C5BW0());
            }
            longSparseArray.put(pointerInputEventData.m501getIdJ3iCeTQ(), new PointerInputChange(pointerInputEventData.m501getIdJ3iCeTQ(), pointerInputEventData.getUptime(), pointerInputEventData.m503getPositionF1C5BW0(), pointerInputEventData.getDown(), pointerInputEventData.getPressure(), uptime, jMo523screenToLocalMKHz9U, down, false, pointerInputEventData.m506getTypeT8wyACA(), pointerInputEventData.getHistorical(), pointerInputEventData.m505getScrollDeltaF1C5BW0(), pointerInputEventData.m502getOriginalEventPositionF1C5BW0(), null));
            if (pointerInputEventData.getDown()) {
                this.previousPointerInputData.put(pointerInputEventData.m501getIdJ3iCeTQ(), new PointerInputData(pointerInputEventData.getUptime(), pointerInputEventData.m504getPositionOnScreenF1C5BW0(), pointerInputEventData.getDown(), null));
            } else {
                this.previousPointerInputData.remove(pointerInputEventData.m501getIdJ3iCeTQ());
            }
        }
        return new InternalPointerEvent(longSparseArray, pointerInputEvent);
    }
}
