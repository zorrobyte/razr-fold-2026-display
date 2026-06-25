package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BlendMode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class BlendMode {
    public static final Companion Companion = new Companion(null);
    private static final int Clear = m831constructorimpl(0);
    private static final int Src = m831constructorimpl(1);
    private static final int Dst = m831constructorimpl(2);
    private static final int SrcOver = m831constructorimpl(3);
    private static final int DstOver = m831constructorimpl(4);
    private static final int SrcIn = m831constructorimpl(5);
    private static final int DstIn = m831constructorimpl(6);
    private static final int SrcOut = m831constructorimpl(7);
    private static final int DstOut = m831constructorimpl(8);
    private static final int SrcAtop = m831constructorimpl(9);
    private static final int DstAtop = m831constructorimpl(10);
    private static final int Xor = m831constructorimpl(11);
    private static final int Plus = m831constructorimpl(12);
    private static final int Modulate = m831constructorimpl(13);
    private static final int Screen = m831constructorimpl(14);
    private static final int Overlay = m831constructorimpl(15);
    private static final int Darken = m831constructorimpl(16);
    private static final int Lighten = m831constructorimpl(17);
    private static final int ColorDodge = m831constructorimpl(18);
    private static final int ColorBurn = m831constructorimpl(19);
    private static final int Hardlight = m831constructorimpl(20);
    private static final int Softlight = m831constructorimpl(21);
    private static final int Difference = m831constructorimpl(22);
    private static final int Exclusion = m831constructorimpl(23);
    private static final int Multiply = m831constructorimpl(24);
    private static final int Hue = m831constructorimpl(25);
    private static final int Saturation = m831constructorimpl(26);
    private static final int Color = m831constructorimpl(27);
    private static final int Luminosity = m831constructorimpl(28);

    /* JADX INFO: compiled from: BlendMode.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getClear-0nO6VwU, reason: not valid java name */
        public final int m835getClear0nO6VwU() {
            return BlendMode.Clear;
        }

        /* JADX INFO: renamed from: getColor-0nO6VwU, reason: not valid java name */
        public final int m836getColor0nO6VwU() {
            return BlendMode.Color;
        }

        /* JADX INFO: renamed from: getColorBurn-0nO6VwU, reason: not valid java name */
        public final int m837getColorBurn0nO6VwU() {
            return BlendMode.ColorBurn;
        }

        /* JADX INFO: renamed from: getColorDodge-0nO6VwU, reason: not valid java name */
        public final int m838getColorDodge0nO6VwU() {
            return BlendMode.ColorDodge;
        }

        /* JADX INFO: renamed from: getDarken-0nO6VwU, reason: not valid java name */
        public final int m839getDarken0nO6VwU() {
            return BlendMode.Darken;
        }

        /* JADX INFO: renamed from: getDifference-0nO6VwU, reason: not valid java name */
        public final int m840getDifference0nO6VwU() {
            return BlendMode.Difference;
        }

        /* JADX INFO: renamed from: getDst-0nO6VwU, reason: not valid java name */
        public final int m841getDst0nO6VwU() {
            return BlendMode.Dst;
        }

        /* JADX INFO: renamed from: getDstAtop-0nO6VwU, reason: not valid java name */
        public final int m842getDstAtop0nO6VwU() {
            return BlendMode.DstAtop;
        }

        /* JADX INFO: renamed from: getDstIn-0nO6VwU, reason: not valid java name */
        public final int m843getDstIn0nO6VwU() {
            return BlendMode.DstIn;
        }

        /* JADX INFO: renamed from: getDstOut-0nO6VwU, reason: not valid java name */
        public final int m844getDstOut0nO6VwU() {
            return BlendMode.DstOut;
        }

        /* JADX INFO: renamed from: getDstOver-0nO6VwU, reason: not valid java name */
        public final int m845getDstOver0nO6VwU() {
            return BlendMode.DstOver;
        }

        /* JADX INFO: renamed from: getExclusion-0nO6VwU, reason: not valid java name */
        public final int m846getExclusion0nO6VwU() {
            return BlendMode.Exclusion;
        }

        /* JADX INFO: renamed from: getHardlight-0nO6VwU, reason: not valid java name */
        public final int m847getHardlight0nO6VwU() {
            return BlendMode.Hardlight;
        }

        /* JADX INFO: renamed from: getHue-0nO6VwU, reason: not valid java name */
        public final int m848getHue0nO6VwU() {
            return BlendMode.Hue;
        }

        /* JADX INFO: renamed from: getLighten-0nO6VwU, reason: not valid java name */
        public final int m849getLighten0nO6VwU() {
            return BlendMode.Lighten;
        }

        /* JADX INFO: renamed from: getLuminosity-0nO6VwU, reason: not valid java name */
        public final int m850getLuminosity0nO6VwU() {
            return BlendMode.Luminosity;
        }

        /* JADX INFO: renamed from: getModulate-0nO6VwU, reason: not valid java name */
        public final int m851getModulate0nO6VwU() {
            return BlendMode.Modulate;
        }

        /* JADX INFO: renamed from: getMultiply-0nO6VwU, reason: not valid java name */
        public final int m852getMultiply0nO6VwU() {
            return BlendMode.Multiply;
        }

        /* JADX INFO: renamed from: getOverlay-0nO6VwU, reason: not valid java name */
        public final int m853getOverlay0nO6VwU() {
            return BlendMode.Overlay;
        }

        /* JADX INFO: renamed from: getPlus-0nO6VwU, reason: not valid java name */
        public final int m854getPlus0nO6VwU() {
            return BlendMode.Plus;
        }

        /* JADX INFO: renamed from: getSaturation-0nO6VwU, reason: not valid java name */
        public final int m855getSaturation0nO6VwU() {
            return BlendMode.Saturation;
        }

        /* JADX INFO: renamed from: getScreen-0nO6VwU, reason: not valid java name */
        public final int m856getScreen0nO6VwU() {
            return BlendMode.Screen;
        }

        /* JADX INFO: renamed from: getSoftlight-0nO6VwU, reason: not valid java name */
        public final int m857getSoftlight0nO6VwU() {
            return BlendMode.Softlight;
        }

        /* JADX INFO: renamed from: getSrc-0nO6VwU, reason: not valid java name */
        public final int m858getSrc0nO6VwU() {
            return BlendMode.Src;
        }

        /* JADX INFO: renamed from: getSrcAtop-0nO6VwU, reason: not valid java name */
        public final int m859getSrcAtop0nO6VwU() {
            return BlendMode.SrcAtop;
        }

        /* JADX INFO: renamed from: getSrcIn-0nO6VwU, reason: not valid java name */
        public final int m860getSrcIn0nO6VwU() {
            return BlendMode.SrcIn;
        }

        /* JADX INFO: renamed from: getSrcOut-0nO6VwU, reason: not valid java name */
        public final int m861getSrcOut0nO6VwU() {
            return BlendMode.SrcOut;
        }

        /* JADX INFO: renamed from: getSrcOver-0nO6VwU, reason: not valid java name */
        public final int m862getSrcOver0nO6VwU() {
            return BlendMode.SrcOver;
        }

        /* JADX INFO: renamed from: getXor-0nO6VwU, reason: not valid java name */
        public final int m863getXor0nO6VwU() {
            return BlendMode.Xor;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m831constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m832equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m833hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m834toStringimpl(int i) {
        return m832equalsimpl0(i, Clear) ? "Clear" : m832equalsimpl0(i, Src) ? "Src" : m832equalsimpl0(i, Dst) ? "Dst" : m832equalsimpl0(i, SrcOver) ? "SrcOver" : m832equalsimpl0(i, DstOver) ? "DstOver" : m832equalsimpl0(i, SrcIn) ? "SrcIn" : m832equalsimpl0(i, DstIn) ? "DstIn" : m832equalsimpl0(i, SrcOut) ? "SrcOut" : m832equalsimpl0(i, DstOut) ? "DstOut" : m832equalsimpl0(i, SrcAtop) ? "SrcAtop" : m832equalsimpl0(i, DstAtop) ? "DstAtop" : m832equalsimpl0(i, Xor) ? "Xor" : m832equalsimpl0(i, Plus) ? "Plus" : m832equalsimpl0(i, Modulate) ? "Modulate" : m832equalsimpl0(i, Screen) ? "Screen" : m832equalsimpl0(i, Overlay) ? "Overlay" : m832equalsimpl0(i, Darken) ? "Darken" : m832equalsimpl0(i, Lighten) ? "Lighten" : m832equalsimpl0(i, ColorDodge) ? "ColorDodge" : m832equalsimpl0(i, ColorBurn) ? "ColorBurn" : m832equalsimpl0(i, Hardlight) ? "HardLight" : m832equalsimpl0(i, Softlight) ? "Softlight" : m832equalsimpl0(i, Difference) ? "Difference" : m832equalsimpl0(i, Exclusion) ? "Exclusion" : m832equalsimpl0(i, Multiply) ? "Multiply" : m832equalsimpl0(i, Hue) ? "Hue" : m832equalsimpl0(i, Saturation) ? "Saturation" : m832equalsimpl0(i, Color) ? "Color" : m832equalsimpl0(i, Luminosity) ? "Luminosity" : "Unknown";
    }
}
