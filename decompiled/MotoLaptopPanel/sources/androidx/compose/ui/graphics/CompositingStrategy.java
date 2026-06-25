package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: GraphicsLayerModifier.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CompositingStrategy {
    public static final Companion Companion = new Companion(null);
    private static final int Auto = m902constructorimpl(0);
    private static final int Offscreen = m902constructorimpl(1);
    private static final int ModulateAlpha = m902constructorimpl(2);

    /* JADX INFO: compiled from: GraphicsLayerModifier.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getAuto--NrFUSI, reason: not valid java name */
        public final int m906getAutoNrFUSI() {
            return CompositingStrategy.Auto;
        }

        /* JADX INFO: renamed from: getModulateAlpha--NrFUSI, reason: not valid java name */
        public final int m907getModulateAlphaNrFUSI() {
            return CompositingStrategy.ModulateAlpha;
        }

        /* JADX INFO: renamed from: getOffscreen--NrFUSI, reason: not valid java name */
        public final int m908getOffscreenNrFUSI() {
            return CompositingStrategy.Offscreen;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m902constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m903equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m904hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m905toStringimpl(int i) {
        return "CompositingStrategy(value=" + i + ')';
    }
}
