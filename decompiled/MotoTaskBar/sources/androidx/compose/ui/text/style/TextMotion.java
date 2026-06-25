package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TextMotion.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TextMotion {
    private static final TextMotion Animated;
    public static final Companion Companion;
    private static final TextMotion Static;
    private final int linearity;
    private final boolean subpixelTextPositioning;

    /* JADX INFO: compiled from: TextMotion.android.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: TextMotion.android.kt */
    public final class Linearity {
        private final int value;
        public static final Companion Companion = new Companion(null);
        private static final int Linear = m965constructorimpl(1);
        private static final int FontHinting = m965constructorimpl(2);
        private static final int None = m965constructorimpl(3);

        /* JADX INFO: compiled from: TextMotion.android.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getFontHinting-4e0Vf04, reason: not valid java name */
            public final int m971getFontHinting4e0Vf04() {
                return Linearity.FontHinting;
            }

            /* JADX INFO: renamed from: getLinear-4e0Vf04, reason: not valid java name */
            public final int m972getLinear4e0Vf04() {
                return Linearity.Linear;
            }
        }

        private /* synthetic */ Linearity(int i) {
            this.value = i;
        }

        /* JADX INFO: renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Linearity m964boximpl(int i) {
            return new Linearity(i);
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        private static int m965constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
        public static boolean m966equalsimpl(int i, Object obj) {
            return (obj instanceof Linearity) && i == ((Linearity) obj).m970unboximpl();
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m967equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
        public static int m968hashCodeimpl(int i) {
            return Integer.hashCode(i);
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m969toStringimpl(int i) {
            return m967equalsimpl0(i, Linear) ? "Linearity.Linear" : m967equalsimpl0(i, FontHinting) ? "Linearity.FontHinting" : m967equalsimpl0(i, None) ? "Linearity.None" : "Invalid";
        }

        public boolean equals(Object obj) {
            return m966equalsimpl(this.value, obj);
        }

        public int hashCode() {
            return m968hashCodeimpl(this.value);
        }

        public String toString() {
            return m969toStringimpl(this.value);
        }

        /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
        public final /* synthetic */ int m970unboximpl() {
            return this.value;
        }
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        Companion = new Companion(defaultConstructorMarker);
        Linearity.Companion companion = Linearity.Companion;
        Static = new TextMotion(companion.m971getFontHinting4e0Vf04(), false, defaultConstructorMarker);
        Animated = new TextMotion(companion.m972getLinear4e0Vf04(), true, defaultConstructorMarker);
    }

    private TextMotion(int i, boolean z) {
        this.linearity = i;
        this.subpixelTextPositioning = z;
    }

    public /* synthetic */ TextMotion(int i, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextMotion)) {
            return false;
        }
        TextMotion textMotion = (TextMotion) obj;
        return Linearity.m967equalsimpl0(this.linearity, textMotion.linearity) && this.subpixelTextPositioning == textMotion.subpixelTextPositioning;
    }

    /* JADX INFO: renamed from: getLinearity-4e0Vf04$ui_text_release, reason: not valid java name */
    public final int m963getLinearity4e0Vf04$ui_text_release() {
        return this.linearity;
    }

    public final boolean getSubpixelTextPositioning$ui_text_release() {
        return this.subpixelTextPositioning;
    }

    public int hashCode() {
        return (Linearity.m968hashCodeimpl(this.linearity) * 31) + Boolean.hashCode(this.subpixelTextPositioning);
    }

    public String toString() {
        return Intrinsics.areEqual(this, Static) ? "TextMotion.Static" : Intrinsics.areEqual(this, Animated) ? "TextMotion.Animated" : "Invalid";
    }
}
