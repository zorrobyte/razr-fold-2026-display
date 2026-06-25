package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: GraphicsLayerModifier.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CompositingStrategy {
    public static final Companion Companion = new Companion(null);
    private static final int Auto = m293constructorimpl(0);
    private static final int Offscreen = m293constructorimpl(1);
    private static final int ModulateAlpha = m293constructorimpl(2);

    /* JADX INFO: compiled from: GraphicsLayerModifier.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getAuto--NrFUSI, reason: not valid java name */
        public final int m295getAutoNrFUSI() {
            return CompositingStrategy.Auto;
        }

        /* JADX INFO: renamed from: getModulateAlpha--NrFUSI, reason: not valid java name */
        public final int m296getModulateAlphaNrFUSI() {
            return CompositingStrategy.ModulateAlpha;
        }

        /* JADX INFO: renamed from: getOffscreen--NrFUSI, reason: not valid java name */
        public final int m297getOffscreenNrFUSI() {
            return CompositingStrategy.Offscreen;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m293constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m294equalsimpl0(int i, int i2) {
        return i == i2;
    }
}
