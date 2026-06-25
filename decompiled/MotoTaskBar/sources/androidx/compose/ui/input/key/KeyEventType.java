package androidx.compose.ui.input.key;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: KeyEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class KeyEventType {
    public static final Companion Companion = new Companion(null);
    private static final int Unknown = m457constructorimpl(0);
    private static final int KeyUp = m457constructorimpl(1);
    private static final int KeyDown = m457constructorimpl(2);

    /* JADX INFO: compiled from: KeyEvent.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getKeyDown-CS__XNY, reason: not valid java name */
        public final int m459getKeyDownCS__XNY() {
            return KeyEventType.KeyDown;
        }

        /* JADX INFO: renamed from: getKeyUp-CS__XNY, reason: not valid java name */
        public final int m460getKeyUpCS__XNY() {
            return KeyEventType.KeyUp;
        }

        /* JADX INFO: renamed from: getUnknown-CS__XNY, reason: not valid java name */
        public final int m461getUnknownCS__XNY() {
            return KeyEventType.Unknown;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m457constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m458equalsimpl0(int i, int i2) {
        return i == i2;
    }
}
