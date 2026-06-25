package androidx.compose.ui.input.pointer;

import android.util.SparseBooleanArray;
import android.util.SparseLongArray;
import android.view.MotionEvent;
import androidx.compose.ui.geometry.Offset;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: MotionEventAdapter.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MotionEventAdapter {
    private long nextId;
    private final SparseLongArray motionEventToComposePointerIdMap = new SparseLongArray();
    private final SparseBooleanArray activeHoverIds = new SparseBooleanArray();
    private final List pointers = new ArrayList();
    private int previousToolType = -1;
    private int previousSource = -1;

    private final void addFreshIds(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0 && actionMasked != 5) {
            if (actionMasked != 9) {
                return;
            }
            int pointerId = motionEvent.getPointerId(0);
            if (this.motionEventToComposePointerIdMap.indexOfKey(pointerId) < 0) {
                SparseLongArray sparseLongArray = this.motionEventToComposePointerIdMap;
                long j = this.nextId;
                this.nextId = 1 + j;
                sparseLongArray.put(pointerId, j);
                return;
            }
            return;
        }
        int actionIndex = motionEvent.getActionIndex();
        int pointerId2 = motionEvent.getPointerId(actionIndex);
        if (this.motionEventToComposePointerIdMap.indexOfKey(pointerId2) < 0) {
            SparseLongArray sparseLongArray2 = this.motionEventToComposePointerIdMap;
            long j2 = this.nextId;
            this.nextId = 1 + j2;
            sparseLongArray2.put(pointerId2, j2);
            if (motionEvent.getToolType(actionIndex) == 3) {
                this.activeHoverIds.put(pointerId2, true);
            }
        }
    }

    private final void clearOnDeviceChange(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 1) {
            return;
        }
        int toolType = motionEvent.getToolType(0);
        int source = motionEvent.getSource();
        if (toolType == this.previousToolType && source == this.previousSource) {
            return;
        }
        this.previousToolType = toolType;
        this.previousSource = source;
        this.activeHoverIds.clear();
        this.motionEventToComposePointerIdMap.clear();
    }

    private final PointerInputEventData createPointerInputEventData(PositionCalculator positionCalculator, MotionEvent motionEvent, int i, boolean z) {
        long jM1206toRawOffsetdBAh8RU;
        long jMo1263screenToLocalMKHz9U;
        long j;
        int i2 = i;
        long jM1205getComposePointerId_I2yYro = m1205getComposePointerId_I2yYro(motionEvent.getPointerId(i));
        float pressure = motionEvent.getPressure(i);
        long j2 = 4294967295L;
        long jM754copydBAh8RU$default = Offset.m754copydBAh8RU$default(Offset.m752constructorimpl((((long) Float.floatToRawIntBits(motionEvent.getY(i))) & 4294967295L) | (((long) Float.floatToRawIntBits(motionEvent.getX(i))) << 32)), 0.0f, 0.0f, 3, null);
        if (i2 == 0) {
            jM1206toRawOffsetdBAh8RU = Offset.m752constructorimpl((((long) Float.floatToRawIntBits(motionEvent.getRawY())) & 4294967295L) | (((long) Float.floatToRawIntBits(motionEvent.getRawX())) << 32));
            jMo1263screenToLocalMKHz9U = positionCalculator.mo1263screenToLocalMKHz9U(jM1206toRawOffsetdBAh8RU);
        } else {
            jM1206toRawOffsetdBAh8RU = MotionEventHelper.INSTANCE.m1206toRawOffsetdBAh8RU(motionEvent, i2);
            jMo1263screenToLocalMKHz9U = positionCalculator.mo1263screenToLocalMKHz9U(jM1206toRawOffsetdBAh8RU);
        }
        int toolType = motionEvent.getToolType(i);
        int iM1262getUnknownT8wyACA = toolType != 0 ? toolType != 1 ? toolType != 2 ? toolType != 3 ? toolType != 4 ? PointerType.Companion.m1262getUnknownT8wyACA() : PointerType.Companion.m1258getEraserT8wyACA() : PointerType.Companion.m1259getMouseT8wyACA() : PointerType.Companion.m1260getStylusT8wyACA() : PointerType.Companion.m1261getTouchT8wyACA() : PointerType.Companion.m1262getUnknownT8wyACA();
        ArrayList arrayList = new ArrayList(motionEvent.getHistorySize());
        int historySize = motionEvent.getHistorySize();
        int i3 = 0;
        while (i3 < historySize) {
            float historicalX = motionEvent.getHistoricalX(i2, i3);
            float historicalY = motionEvent.getHistoricalY(i2, i3);
            long j3 = j2;
            if ((Float.floatToRawIntBits(historicalX) & Integer.MAX_VALUE) >= 2139095040 || (Float.floatToRawIntBits(historicalY) & Integer.MAX_VALUE) >= 2139095040) {
                j = jM1206toRawOffsetdBAh8RU;
            } else {
                long jFloatToRawIntBits = Float.floatToRawIntBits(historicalX);
                j = jM1206toRawOffsetdBAh8RU;
                long jM752constructorimpl = Offset.m752constructorimpl((((long) Float.floatToRawIntBits(historicalY)) & j3) | (jFloatToRawIntBits << 32));
                arrayList.add(new HistoricalChange(motionEvent.getHistoricalEventTime(i3), jM752constructorimpl, jM752constructorimpl, null));
            }
            i3++;
            i2 = i;
            jM1206toRawOffsetdBAh8RU = j;
            j2 = j3;
        }
        return new PointerInputEventData(jM1205getComposePointerId_I2yYro, motionEvent.getEventTime(), jM1206toRawOffsetdBAh8RU, jMo1263screenToLocalMKHz9U, z, pressure, iM1262getUnknownT8wyACA, this.activeHoverIds.get(motionEvent.getPointerId(i), false), arrayList, motionEvent.getActionMasked() == 8 ? Offset.m752constructorimpl((((long) Float.floatToRawIntBits((-motionEvent.getAxisValue(9)) + 0.0f)) & j2) | (((long) Float.floatToRawIntBits(motionEvent.getAxisValue(10))) << 32)) : Offset.Companion.m770getZeroF1C5BW0(), jM754copydBAh8RU$default, null);
    }

    /* JADX INFO: renamed from: getComposePointerId-_I2yYro, reason: not valid java name */
    private final long m1205getComposePointerId_I2yYro(int i) {
        long jValueAt;
        int iIndexOfKey = this.motionEventToComposePointerIdMap.indexOfKey(i);
        if (iIndexOfKey >= 0) {
            jValueAt = this.motionEventToComposePointerIdMap.valueAt(iIndexOfKey);
        } else {
            long j = this.nextId;
            this.nextId = 1 + j;
            this.motionEventToComposePointerIdMap.put(i, j);
            jValueAt = j;
        }
        return PointerId.m1224constructorimpl(jValueAt);
    }

    private final boolean hasPointerId(MotionEvent motionEvent, int i) {
        int pointerCount = motionEvent.getPointerCount();
        for (int i2 = 0; i2 < pointerCount; i2++) {
            if (motionEvent.getPointerId(i2) == i) {
                return true;
            }
        }
        return false;
    }

    private final void removeStaleIds(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 6) {
            int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
            if (!this.activeHoverIds.get(pointerId, false)) {
                this.motionEventToComposePointerIdMap.delete(pointerId);
                this.activeHoverIds.delete(pointerId);
            }
        }
        if (this.motionEventToComposePointerIdMap.size() > motionEvent.getPointerCount()) {
            for (int size = this.motionEventToComposePointerIdMap.size() - 1; -1 < size; size--) {
                int iKeyAt = this.motionEventToComposePointerIdMap.keyAt(size);
                if (!hasPointerId(motionEvent, iKeyAt)) {
                    this.motionEventToComposePointerIdMap.removeAt(size);
                    this.activeHoverIds.delete(iKeyAt);
                }
            }
        }
    }

    public final PointerInputEvent convertToPointerInputEvent$ui_release(MotionEvent motionEvent, PositionCalculator positionCalculator) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 3 || actionMasked == 4) {
            this.motionEventToComposePointerIdMap.clear();
            this.activeHoverIds.clear();
            return null;
        }
        clearOnDeviceChange(motionEvent);
        addFreshIds(motionEvent);
        boolean z = actionMasked == 9 || actionMasked == 7 || actionMasked == 10;
        boolean z2 = actionMasked == 8;
        if (z) {
            this.activeHoverIds.put(motionEvent.getPointerId(motionEvent.getActionIndex()), true);
        }
        int actionIndex = actionMasked != 1 ? actionMasked != 6 ? -1 : motionEvent.getActionIndex() : 0;
        this.pointers.clear();
        int pointerCount = motionEvent.getPointerCount();
        int i = 0;
        while (i < pointerCount) {
            this.pointers.add(createPointerInputEventData(positionCalculator, motionEvent, i, (z || i == actionIndex || (z2 && motionEvent.getButtonState() == 0)) ? false : true));
            i++;
        }
        removeStaleIds(motionEvent);
        return new PointerInputEvent(motionEvent.getEventTime(), this.pointers, motionEvent);
    }

    public final void endStream(int i) {
        this.activeHoverIds.delete(i);
        this.motionEventToComposePointerIdMap.delete(i);
    }
}
