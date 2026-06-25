package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: StrokeCap.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class StrokeCap {
    public static final Companion Companion = new Companion(null);
    private static final int Butt = m994constructorimpl(0);
    private static final int Round = m994constructorimpl(1);
    private static final int Square = m994constructorimpl(2);

    /* JADX INFO: compiled from: StrokeCap.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getButt-KaPHkGw, reason: not valid java name */
        public final int m998getButtKaPHkGw() {
            return StrokeCap.Butt;
        }

        /* JADX INFO: renamed from: getRound-KaPHkGw, reason: not valid java name */
        public final int m999getRoundKaPHkGw() {
            return StrokeCap.Round;
        }

        /* JADX INFO: renamed from: getSquare-KaPHkGw, reason: not valid java name */
        public final int m1000getSquareKaPHkGw() {
            return StrokeCap.Square;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m994constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m995equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m996hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m997toStringimpl(int i) {
        return m995equalsimpl0(i, Butt) ? "Butt" : m995equalsimpl0(i, Round) ? "Round" : m995equalsimpl0(i, Square) ? "Square" : "Unknown";
    }
}
