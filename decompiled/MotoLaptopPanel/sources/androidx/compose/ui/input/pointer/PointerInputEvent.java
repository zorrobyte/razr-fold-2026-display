package androidx.compose.ui.input.pointer;

import android.view.MotionEvent;
import java.util.List;

/* JADX INFO: compiled from: PointerInputEvent.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PointerInputEvent {
    private final MotionEvent motionEvent;
    private final List pointers;
    private final long uptime;

    public PointerInputEvent(long j, List list, MotionEvent motionEvent) {
        this.uptime = j;
        this.pointers = list;
        this.motionEvent = motionEvent;
    }

    public final MotionEvent getMotionEvent() {
        return this.motionEvent;
    }

    public final List getPointers() {
        return this.pointers;
    }
}
