package androidx.compose.ui.unit;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Dp.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Dp implements Comparable {
    public static final Companion Companion = new Companion(null);
    private static final float Hairline = m1877constructorimpl(0.0f);
    private static final float Infinity = m1877constructorimpl(Float.POSITIVE_INFINITY);
    private static final float Unspecified = m1877constructorimpl(Float.NaN);
    private final float value;

    /* JADX INFO: compiled from: Dp.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getHairline-D9Ej5fM, reason: not valid java name */
        public final float m1884getHairlineD9Ej5fM() {
            return Dp.Hairline;
        }

        /* JADX INFO: renamed from: getUnspecified-D9Ej5fM, reason: not valid java name */
        public final float m1885getUnspecifiedD9Ej5fM() {
            return Dp.Unspecified;
        }
    }

    private /* synthetic */ Dp(float f) {
        this.value = f;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Dp m1875boximpl(float f) {
        return new Dp(f);
    }

    /* JADX INFO: renamed from: compareTo-0680j_4, reason: not valid java name */
    public static int m1876compareTo0680j_4(float f, float f2) {
        return Float.compare(f, f2);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static float m1877constructorimpl(float f) {
        return f;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1878equalsimpl(float f, Object obj) {
        return (obj instanceof Dp) && Float.compare(f, ((Dp) obj).m1883unboximpl()) == 0;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1879equalsimpl0(float f, float f2) {
        return Float.compare(f, f2) == 0;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1880hashCodeimpl(float f) {
        return Float.hashCode(f);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1881toStringimpl(float f) {
        if (Float.isNaN(f)) {
            return "Dp.Unspecified";
        }
        return f + ".dp";
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return m1882compareTo0680j_4(((Dp) obj).m1883unboximpl());
    }

    /* JADX INFO: renamed from: compareTo-0680j_4, reason: not valid java name */
    public int m1882compareTo0680j_4(float f) {
        return m1876compareTo0680j_4(this.value, f);
    }

    public boolean equals(Object obj) {
        return m1878equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m1880hashCodeimpl(this.value);
    }

    public String toString() {
        return m1881toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ float m1883unboximpl() {
        return this.value;
    }
}
