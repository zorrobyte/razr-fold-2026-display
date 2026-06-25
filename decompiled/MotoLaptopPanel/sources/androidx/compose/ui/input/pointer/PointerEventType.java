package androidx.compose.ui.input.pointer;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PointerEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PointerEventType {
    public static final Companion Companion = new Companion(null);
    private static final int Unknown = m1214constructorimpl(0);
    private static final int Press = m1214constructorimpl(1);
    private static final int Release = m1214constructorimpl(2);
    private static final int Move = m1214constructorimpl(3);
    private static final int Enter = m1214constructorimpl(4);
    private static final int Exit = m1214constructorimpl(5);
    private static final int Scroll = m1214constructorimpl(6);

    /* JADX INFO: compiled from: PointerEvent.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getEnter-7fucELk, reason: not valid java name */
        public final int m1216getEnter7fucELk() {
            return PointerEventType.Enter;
        }

        /* JADX INFO: renamed from: getExit-7fucELk, reason: not valid java name */
        public final int m1217getExit7fucELk() {
            return PointerEventType.Exit;
        }

        /* JADX INFO: renamed from: getMove-7fucELk, reason: not valid java name */
        public final int m1218getMove7fucELk() {
            return PointerEventType.Move;
        }

        /* JADX INFO: renamed from: getPress-7fucELk, reason: not valid java name */
        public final int m1219getPress7fucELk() {
            return PointerEventType.Press;
        }

        /* JADX INFO: renamed from: getRelease-7fucELk, reason: not valid java name */
        public final int m1220getRelease7fucELk() {
            return PointerEventType.Release;
        }

        /* JADX INFO: renamed from: getScroll-7fucELk, reason: not valid java name */
        public final int m1221getScroll7fucELk() {
            return PointerEventType.Scroll;
        }

        /* JADX INFO: renamed from: getUnknown-7fucELk, reason: not valid java name */
        public final int m1222getUnknown7fucELk() {
            return PointerEventType.Unknown;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m1214constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1215equalsimpl0(int i, int i2) {
        return i == i2;
    }
}
