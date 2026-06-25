package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PathOperation.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PathOperation {
    public static final Companion Companion = new Companion(null);
    private static final int Difference = m969constructorimpl(0);
    private static final int Intersect = m969constructorimpl(1);
    private static final int Union = m969constructorimpl(2);
    private static final int Xor = m969constructorimpl(3);
    private static final int ReverseDifference = m969constructorimpl(4);

    /* JADX INFO: compiled from: PathOperation.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getDifference-b3I0S0c, reason: not valid java name */
        public final int m971getDifferenceb3I0S0c() {
            return PathOperation.Difference;
        }

        /* JADX INFO: renamed from: getIntersect-b3I0S0c, reason: not valid java name */
        public final int m972getIntersectb3I0S0c() {
            return PathOperation.Intersect;
        }

        /* JADX INFO: renamed from: getReverseDifference-b3I0S0c, reason: not valid java name */
        public final int m973getReverseDifferenceb3I0S0c() {
            return PathOperation.ReverseDifference;
        }

        /* JADX INFO: renamed from: getUnion-b3I0S0c, reason: not valid java name */
        public final int m974getUnionb3I0S0c() {
            return PathOperation.Union;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m969constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m970equalsimpl0(int i, int i2) {
        return i == i2;
    }
}
