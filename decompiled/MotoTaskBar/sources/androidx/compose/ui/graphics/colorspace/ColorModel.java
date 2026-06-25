package androidx.compose.ui.graphics.colorspace;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ColorModel.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ColorModel {
    private static final long Cmyk;
    public static final Companion Companion = new Companion(null);
    private static final long Lab;
    private static final long Rgb;
    private static final long Xyz;

    /* JADX INFO: compiled from: ColorModel.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getLab-xdoWZVw, reason: not valid java name */
        public final long m353getLabxdoWZVw() {
            return ColorModel.Lab;
        }

        /* JADX INFO: renamed from: getRgb-xdoWZVw, reason: not valid java name */
        public final long m354getRgbxdoWZVw() {
            return ColorModel.Rgb;
        }

        /* JADX INFO: renamed from: getXyz-xdoWZVw, reason: not valid java name */
        public final long m355getXyzxdoWZVw() {
            return ColorModel.Xyz;
        }
    }

    static {
        long j = 3;
        long j2 = j << 32;
        Rgb = m348constructorimpl((((long) 0) & 4294967295L) | j2);
        Xyz = m348constructorimpl((((long) 1) & 4294967295L) | j2);
        Lab = m348constructorimpl(j2 | (((long) 2) & 4294967295L));
        Cmyk = m348constructorimpl((j & 4294967295L) | (((long) 4) << 32));
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m348constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m349equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: getComponentCount-impl, reason: not valid java name */
    public static final int m350getComponentCountimpl(long j) {
        return (int) (j >> 32);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m351hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m352toStringimpl(long j) {
        return m349equalsimpl0(j, Rgb) ? "Rgb" : m349equalsimpl0(j, Xyz) ? "Xyz" : m349equalsimpl0(j, Lab) ? "Lab" : m349equalsimpl0(j, Cmyk) ? "Cmyk" : "Unknown";
    }
}
