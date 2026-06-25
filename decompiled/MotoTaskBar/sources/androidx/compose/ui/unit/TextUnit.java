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
        public final long m1027getUnspecifiedXSAIIZE() {
            return TextUnit.Unspecified;
        }
    }

    static {
        TextUnitType.Companion companion = TextUnitType.Companion;
        TextUnitTypes = new TextUnitType[]{TextUnitType.m1029boximpl(companion.m1038getUnspecifiedUIouoOA()), TextUnitType.m1029boximpl(companion.m1037getSpUIouoOA()), TextUnitType.m1029boximpl(companion.m1036getEmUIouoOA())};
        Unspecified = TextUnitKt.pack(0L, Float.NaN);
    }

    private /* synthetic */ TextUnit(long j) {
        this.packedValue = j;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextUnit m1017boximpl(long j) {
        return new TextUnit(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1018constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1019equalsimpl(long j, Object obj) {
        return (obj instanceof TextUnit) && j == ((TextUnit) obj).m1026unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1020equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: getRawType-impl, reason: not valid java name */
    public static final long m1021getRawTypeimpl(long j) {
        return j & 1095216660480L;
    }

    /* JADX INFO: renamed from: getType-UIouoOA, reason: not valid java name */
    public static final long m1022getTypeUIouoOA(long j) {
        return TextUnitTypes[(int) (m1021getRawTypeimpl(j) >>> 32)].m1035unboximpl();
    }

    /* JADX INFO: renamed from: getValue-impl, reason: not valid java name */
    public static final float m1023getValueimpl(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1024hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1025toStringimpl(long j) {
        long jM1022getTypeUIouoOA = m1022getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        if (TextUnitType.m1032equalsimpl0(jM1022getTypeUIouoOA, companion.m1038getUnspecifiedUIouoOA())) {
            return "Unspecified";
        }
        if (TextUnitType.m1032equalsimpl0(jM1022getTypeUIouoOA, companion.m1037getSpUIouoOA())) {
            return m1023getValueimpl(j) + ".sp";
        }
        if (!TextUnitType.m1032equalsimpl0(jM1022getTypeUIouoOA, companion.m1036getEmUIouoOA())) {
            return "Invalid";
        }
        return m1023getValueimpl(j) + ".em";
    }

    public boolean equals(Object obj) {
        return m1019equalsimpl(this.packedValue, obj);
    }

    public int hashCode() {
        return m1024hashCodeimpl(this.packedValue);
    }

    public String toString() {
        return m1025toStringimpl(this.packedValue);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m1026unboximpl() {
        return this.packedValue;
    }
}
