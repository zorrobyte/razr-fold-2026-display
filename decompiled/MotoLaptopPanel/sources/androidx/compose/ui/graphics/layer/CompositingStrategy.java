package androidx.compose.ui.graphics.layer;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: CompositingStrategy.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CompositingStrategy {
    public static final Companion Companion = new Companion(null);
    private static final int Auto = m1089constructorimpl(0);
    private static final int Offscreen = m1089constructorimpl(1);
    private static final int ModulateAlpha = m1089constructorimpl(2);

    /* JADX INFO: compiled from: CompositingStrategy.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getAuto-ke2Ky5w, reason: not valid java name */
        public final int m1091getAutoke2Ky5w() {
            return CompositingStrategy.Auto;
        }

        /* JADX INFO: renamed from: getModulateAlpha-ke2Ky5w, reason: not valid java name */
        public final int m1092getModulateAlphake2Ky5w() {
            return CompositingStrategy.ModulateAlpha;
        }

        /* JADX INFO: renamed from: getOffscreen-ke2Ky5w, reason: not valid java name */
        public final int m1093getOffscreenke2Ky5w() {
            return CompositingStrategy.Offscreen;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m1089constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1090equalsimpl0(int i, int i2) {
        return i == i2;
    }
}
