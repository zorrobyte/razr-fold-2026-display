package androidx.compose.ui.input.pointer;

import android.view.MotionEvent;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: PointerInteropUtils.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PointerInteropUtils_androidKt {
    public static final void emptyCancelMotionEventScope(long j, Function1 function1) {
        MotionEvent motionEventObtain = MotionEvent.obtain(j, j, 3, 0.0f, 0.0f, 0);
        motionEventObtain.setSource(0);
        function1.invoke(motionEventObtain);
        motionEventObtain.recycle();
    }

    /* JADX INFO: renamed from: toCancelMotionEventScope-d-4ec7I, reason: not valid java name */
    public static final void m1245toCancelMotionEventScoped4ec7I(PointerEvent pointerEvent, long j, Function1 function1) {
        m1247toMotionEventScopeubNVwUQ(pointerEvent, j, function1, true);
    }

    /* JADX INFO: renamed from: toMotionEventScope-d-4ec7I, reason: not valid java name */
    public static final void m1246toMotionEventScoped4ec7I(PointerEvent pointerEvent, long j, Function1 function1) {
        m1247toMotionEventScopeubNVwUQ(pointerEvent, j, function1, false);
    }

    /* JADX INFO: renamed from: toMotionEventScope-ubNVwUQ, reason: not valid java name */
    private static final void m1247toMotionEventScopeubNVwUQ(PointerEvent pointerEvent, long j, Function1 function1, boolean z) {
        MotionEvent motionEvent$ui_release = pointerEvent.getMotionEvent$ui_release();
        if (motionEvent$ui_release == null) {
            throw new IllegalArgumentException("The PointerEvent receiver cannot have a null MotionEvent.");
        }
        int action = motionEvent$ui_release.getAction();
        if (z) {
            motionEvent$ui_release.setAction(3);
        }
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        motionEvent$ui_release.offsetLocation(-Float.intBitsToFloat(i), -Float.intBitsToFloat(i2));
        function1.invoke(motionEvent$ui_release);
        motionEvent$ui_release.offsetLocation(Float.intBitsToFloat(i), Float.intBitsToFloat(i2));
        motionEvent$ui_release.setAction(action);
    }
}
