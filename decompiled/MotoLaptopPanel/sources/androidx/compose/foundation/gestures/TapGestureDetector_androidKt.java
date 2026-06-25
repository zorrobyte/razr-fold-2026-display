package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.pointer.PointerEvent;

/* JADX INFO: compiled from: TapGestureDetector.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TapGestureDetector_androidKt {
    public static final boolean firstDownRefersToPrimaryMouseButtonOnly() {
        return false;
    }

    public static final boolean isDeepPress(PointerEvent pointerEvent) {
        return pointerEvent.getClassification() == 2;
    }
}
