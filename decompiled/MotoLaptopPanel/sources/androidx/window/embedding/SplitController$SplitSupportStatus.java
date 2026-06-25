package androidx.window.embedding;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SplitController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SplitController$SplitSupportStatus {
    private final int rawValue;
    public static final Companion Companion = new Companion(null);
    public static final SplitController$SplitSupportStatus SPLIT_AVAILABLE = new SplitController$SplitSupportStatus(0);
    public static final SplitController$SplitSupportStatus SPLIT_UNAVAILABLE = new SplitController$SplitSupportStatus(1);
    public static final SplitController$SplitSupportStatus SPLIT_ERROR_PROPERTY_NOT_DECLARED = new SplitController$SplitSupportStatus(2);

    /* JADX INFO: compiled from: SplitController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private SplitController$SplitSupportStatus(int i) {
        this.rawValue = i;
    }

    public String toString() {
        int i = this.rawValue;
        return i != 0 ? i != 1 ? i != 2 ? "UNKNOWN" : "SplitSupportStatus: ERROR_SPLIT_PROPERTY_NOT_DECLARED" : "SplitSupportStatus: UNAVAILABLE" : "SplitSupportStatus: AVAILABLE";
    }
}
