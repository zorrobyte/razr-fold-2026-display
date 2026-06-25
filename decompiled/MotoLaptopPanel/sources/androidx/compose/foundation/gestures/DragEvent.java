package androidx.compose.foundation.gestures;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Draggable.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DragEvent {

    /* JADX INFO: compiled from: Draggable.kt */
    public final class DragCancelled extends DragEvent {
        public static final DragCancelled INSTANCE = new DragCancelled();

        private DragCancelled() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: Draggable.kt */
    public final class DragDelta extends DragEvent {
        private final long delta;

        private DragDelta(long j) {
            super(null);
            this.delta = j;
        }

        public /* synthetic */ DragDelta(long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(j);
        }

        /* JADX INFO: renamed from: getDelta-F1C5BW0, reason: not valid java name */
        public final long m119getDeltaF1C5BW0() {
            return this.delta;
        }
    }

    /* JADX INFO: compiled from: Draggable.kt */
    public final class DragStarted extends DragEvent {
        private final long startPoint;

        private DragStarted(long j) {
            super(null);
            this.startPoint = j;
        }

        public /* synthetic */ DragStarted(long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(j);
        }

        /* JADX INFO: renamed from: getStartPoint-F1C5BW0, reason: not valid java name */
        public final long m120getStartPointF1C5BW0() {
            return this.startPoint;
        }
    }

    /* JADX INFO: compiled from: Draggable.kt */
    public final class DragStopped extends DragEvent {
        private final long velocity;

        private DragStopped(long j) {
            super(null);
            this.velocity = j;
        }

        public /* synthetic */ DragStopped(long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(j);
        }

        /* JADX INFO: renamed from: getVelocity-9UxMQ8M, reason: not valid java name */
        public final long m121getVelocity9UxMQ8M() {
            return this.velocity;
        }
    }

    private DragEvent() {
    }

    public /* synthetic */ DragEvent(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
