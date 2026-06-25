package androidx.compose.ui.unit;

import androidx.compose.ui.unit.TextUnitType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TextUnit.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TextUnit {
    public static final Companion Companion = new Companion(null);
    private static final TextUnitType[] TextUnitTypes;
    private static final long Unspecified;
    private final long packedValue;

    /* JADX INFO: compiled from: TextUnit.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getUnspecified-XSAIIZE, reason: not valid java name */
        public final long m1941getUnspecifiedXSAIIZE() {
            return TextUnit.Unspecified;
        }
    }

    static {
        TextUnitType.Companion companion = TextUnitType.Companion;
        TextUnitTypes = new TextUnitType[]{TextUnitType.m1944boximpl(companion.m1953getUnspecifiedUIouoOA()), TextUnitType.m1944boximpl(companion.m1952getSpUIouoOA()), TextUnitType.m1944boximpl(companion.m1951getEmUIouoOA())};
        Unspecified = TextUnitKt.pack(0L, Float.NaN);
    }

    private /* synthetic */ TextUnit(long j) {
        this.packedValue = j;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextUnit m1931boximpl(long j) {
        return new TextUnit(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1932constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1933equalsimpl(long j, Object obj) {
        return (obj instanceof TextUnit) && j == ((TextUnit) obj).m1940unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1934equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: getRawType-impl, reason: not valid java name */
    public static final long m1935getRawTypeimpl(long j) {
        return j & 1095216660480L;
    }

    /* JADX INFO: renamed from: getType-UIouoOA, reason: not valid java name */
    public static final long m1936getTypeUIouoOA(long j) {
        return TextUnitTypes[(int) (m1935getRawTypeimpl(j) >>> 32)].m1950unboximpl();
    }

    /* JADX INFO: renamed from: getValue-impl, reason: not valid java name */
    public static final float m1937getValueimpl(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1938hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1939toStringimpl(long j) {
        long jM1936getTypeUIouoOA = m1936getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1953getUnspecifiedUIouoOA())) {
            return "Unspecified";
        }
        if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1952getSpUIouoOA())) {
            return m1937getValueimpl(j) + ".sp";
        }
        if (!TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1951getEmUIouoOA())) {
            return "Invalid";
        }
        return m1937getValueimpl(j) + ".em";
    }

    public boolean equals(Object obj) {
        return m1933equalsimpl(this.packedValue, obj);
    }

    public int hashCode() {
        return m1938hashCodeimpl(this.packedValue);
    }

    public String toString() {
        return m1939toStringimpl(this.packedValue);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m1940unboximpl() {
        return this.packedValue;
    }
}
