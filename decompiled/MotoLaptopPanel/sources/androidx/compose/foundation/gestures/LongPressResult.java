package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.pointer.PointerInputChange;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TapGestureDetector.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LongPressResult {

    /* JADX INFO: compiled from: TapGestureDetector.kt */
    public final class Canceled extends LongPressResult {
        public static final Canceled INSTANCE = new Canceled();

        private Canceled() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: TapGestureDetector.kt */
    public final class Released extends LongPressResult {
        private final PointerInputChange finalUpChange;

        public Released(PointerInputChange pointerInputChange) {
            super(null);
            this.finalUpChange = pointerInputChange;
        }

        public final PointerInputChange getFinalUpChange() {
            return this.finalUpChange;
        }
    }

    /* JADX INFO: compiled from: TapGestureDetector.kt */
    public final class Success extends LongPressResult {
        public static final Success INSTANCE = new Success();

        private Success() {
            super(null);
        }
    }

    private LongPressResult() {
    }

    public /* synthetic */ LongPressResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
