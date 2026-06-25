package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ClipOp.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ClipOp {
    public static final Companion Companion = new Companion(null);
    private static final int Difference = m268constructorimpl(0);
    private static final int Intersect = m268constructorimpl(1);

    /* JADX INFO: compiled from: ClipOp.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getDifference-rtfAjoo, reason: not valid java name */
        public final int m270getDifferencertfAjoo() {
            return ClipOp.Difference;
        }

        /* JADX INFO: renamed from: getIntersect-rtfAjoo, reason: not valid java name */
        public final int m271getIntersectrtfAjoo() {
            return ClipOp.Intersect;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m268constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m269equalsimpl0(int i, int i2) {
        return i == i2;
    }
}
