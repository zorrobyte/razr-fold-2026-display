package androidx.transition;

import android.graphics.Canvas;

/* JADX INFO: loaded from: classes.dex */
abstract class CanvasUtils {

    abstract class Api29Impl {
        static void disableZ(Canvas canvas) {
            canvas.disableZ();
        }

        static void enableZ(Canvas canvas) {
            canvas.enableZ();
        }
    }

    static void enableZ(Canvas canvas, boolean z) {
        if (z) {
            Api29Impl.enableZ(canvas);
        } else {
            Api29Impl.disableZ(canvas);
        }
    }
}
