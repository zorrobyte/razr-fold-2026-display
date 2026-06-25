package androidx.compose.ui.focus;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FocusDirection.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FocusDirection {
    private final int value;
    public static final Companion Companion = new Companion(null);
    private static final int Next = m118constructorimpl(1);
    private static final int Previous = m118constructorimpl(2);
    private static final int Left = m118constructorimpl(3);
    private static final int Right = m118constructorimpl(4);
    private static final int Up = m118constructorimpl(5);
    private static final int Down = m118constructorimpl(6);
    private static final int Enter = m118constructorimpl(7);
    private static final int Exit = m118constructorimpl(8);

    /* JADX INFO: compiled from: FocusDirection.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getDown-dhqQ-8s, reason: not valid java name */
        public final int m124getDowndhqQ8s() {
            return FocusDirection.Down;
        }

        /* JADX INFO: renamed from: getEnter-dhqQ-8s, reason: not valid java name */
        public final int m125getEnterdhqQ8s() {
            return FocusDirection.Enter;
        }

        /* JADX INFO: renamed from: getExit-dhqQ-8s, reason: not valid java name */
        public final int m126getExitdhqQ8s() {
            return FocusDirection.Exit;
        }

        /* JADX INFO: renamed from: getLeft-dhqQ-8s, reason: not valid java name */
        public final int m127getLeftdhqQ8s() {
            return FocusDirection.Left;
        }

        /* JADX INFO: renamed from: getNext-dhqQ-8s, reason: not valid java name */
        public final int m128getNextdhqQ8s() {
            return FocusDirection.Next;
        }

        /* JADX INFO: renamed from: getPrevious-dhqQ-8s, reason: not valid java name */
        public final int m129getPreviousdhqQ8s() {
            return FocusDirection.Previous;
        }

        /* JADX INFO: renamed from: getRight-dhqQ-8s, reason: not valid java name */
        public final int m130getRightdhqQ8s() {
            return FocusDirection.Right;
        }

        /* JADX INFO: renamed from: getUp-dhqQ-8s, reason: not valid java name */
        public final int m131getUpdhqQ8s() {
            return FocusDirection.Up;
        }
    }

    private /* synthetic */ FocusDirection(int i) {
        this.value = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ FocusDirection m117boximpl(int i) {
        return new FocusDirection(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m118constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m119equalsimpl(int i, Object obj) {
        return (obj instanceof FocusDirection) && i == ((FocusDirection) obj).m123unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m120equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m121hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m122toStringimpl(int i) {
        return m120equalsimpl0(i, Next) ? "Next" : m120equalsimpl0(i, Previous) ? "Previous" : m120equalsimpl0(i, Left) ? "Left" : m120equalsimpl0(i, Right) ? "Right" : m120equalsimpl0(i, Up) ? "Up" : m120equalsimpl0(i, Down) ? "Down" : m120equalsimpl0(i, Enter) ? "Enter" : m120equalsimpl0(i, Exit) ? "Exit" : "Invalid FocusDirection";
    }

    public boolean equals(Object obj) {
        return m119equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m121hashCodeimpl(this.value);
    }

    public String toString() {
        return m122toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m123unboximpl() {
        return this.value;
    }
}
