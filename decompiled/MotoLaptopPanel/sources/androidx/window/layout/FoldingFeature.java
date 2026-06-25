package androidx.window.layout;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FoldingFeature.kt */
/* JADX INFO: loaded from: classes.dex */
public interface FoldingFeature extends DisplayFeature {

    /* JADX INFO: compiled from: FoldingFeature.kt */
    public final class Orientation {
        private final String description;
        public static final Companion Companion = new Companion(null);
        public static final Orientation VERTICAL = new Orientation("VERTICAL");
        public static final Orientation HORIZONTAL = new Orientation("HORIZONTAL");

        /* JADX INFO: compiled from: FoldingFeature.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        private Orientation(String str) {
            this.description = str;
        }

        public String toString() {
            return this.description;
        }
    }

    /* JADX INFO: compiled from: FoldingFeature.kt */
    public final class State {
        public static final Companion Companion = new Companion(null);
        public static final State FLAT = new State("FLAT");
        public static final State HALF_OPENED = new State("HALF_OPENED");
        private final String description;

        /* JADX INFO: compiled from: FoldingFeature.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        private State(String str) {
            this.description = str;
        }

        public String toString() {
            return this.description;
        }
    }

    Orientation getOrientation();
}
