package androidx.compose.ui.focus;

import androidx.compose.ui.input.InputMode;
import androidx.compose.ui.input.InputModeManager;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Focusability.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Focusability {
    public static final Companion Companion = new Companion(null);
    private static final int Always = m159constructorimpl(1);
    private static final int SystemDefined = m159constructorimpl(0);
    private static final int Never = m159constructorimpl(2);

    /* JADX INFO: compiled from: Focusability.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getAlways-LCbbffg, reason: not valid java name */
        public final int m161getAlwaysLCbbffg() {
            return Focusability.Always;
        }

        /* JADX INFO: renamed from: getNever-LCbbffg, reason: not valid java name */
        public final int m162getNeverLCbbffg() {
            return Focusability.Never;
        }
    }

    /* JADX INFO: renamed from: canFocus-impl$ui_release, reason: not valid java name */
    public static final boolean m158canFocusimpl$ui_release(int i, CompositionLocalConsumerModifierNode compositionLocalConsumerModifierNode) {
        if (m160equalsimpl0(i, Always)) {
            return true;
        }
        if (m160equalsimpl0(i, SystemDefined)) {
            return !InputMode.m427equalsimpl0(((InputModeManager) CompositionLocalConsumerModifierNodeKt.currentValueOf(compositionLocalConsumerModifierNode, CompositionLocalsKt.getLocalInputModeManager())).mo433getInputModeaOaMEAU(), InputMode.Companion.m432getTouchaOaMEAU());
        }
        if (m160equalsimpl0(i, Never)) {
            return false;
        }
        throw new IllegalStateException("Unknown Focusability");
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m159constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m160equalsimpl0(int i, int i2) {
        return i == i2;
    }
}
