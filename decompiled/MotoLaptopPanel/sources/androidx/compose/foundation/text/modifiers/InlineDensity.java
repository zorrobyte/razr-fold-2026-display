package androidx.compose.foundation.text.modifiers;

import androidx.compose.ui.unit.Density;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: InlineDensity.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class InlineDensity {
    public static final Companion Companion = new Companion(null);
    private static final long Unspecified = m197constructorimpl(Float.NaN, Float.NaN);

    /* JADX INFO: compiled from: InlineDensity.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getUnspecified-L26CHvs, reason: not valid java name */
        public final long m204getUnspecifiedL26CHvs() {
            return InlineDensity.Unspecified;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m197constructorimpl(float f, float f2) {
        return m198constructorimpl((((long) Float.floatToRawIntBits(f2)) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static long m198constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m199constructorimpl(Density density) {
        return m197constructorimpl(density.getDensity(), density.getFontScale());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m200equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: getDensity-impl, reason: not valid java name */
    public static final float m201getDensityimpl(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    /* JADX INFO: renamed from: getFontScale-impl, reason: not valid java name */
    public static final float m202getFontScaleimpl(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m203toStringimpl(long j) {
        return "InlineDensity(density=" + m201getDensityimpl(j) + ", fontScale=" + m202getFontScaleimpl(j) + ')';
    }
}
