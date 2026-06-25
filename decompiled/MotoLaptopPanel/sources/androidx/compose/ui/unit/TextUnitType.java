package androidx.compose.ui.unit;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TextUnit.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TextUnitType {
    private final long type;
    public static final Companion Companion = new Companion(null);
    private static final long Unspecified = m1945constructorimpl(0);
    private static final long Sp = m1945constructorimpl(4294967296L);
    private static final long Em = m1945constructorimpl(8589934592L);

    /* JADX INFO: compiled from: TextUnit.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getEm-UIouoOA, reason: not valid java name */
        public final long m1951getEmUIouoOA() {
            return TextUnitType.Em;
        }

        /* JADX INFO: renamed from: getSp-UIouoOA, reason: not valid java name */
        public final long m1952getSpUIouoOA() {
            return TextUnitType.Sp;
        }

        /* JADX INFO: renamed from: getUnspecified-UIouoOA, reason: not valid java name */
        public final long m1953getUnspecifiedUIouoOA() {
            return TextUnitType.Unspecified;
        }
    }

    private /* synthetic */ TextUnitType(long j) {
        this.type = j;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextUnitType m1944boximpl(long j) {
        return new TextUnitType(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1945constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1946equalsimpl(long j, Object obj) {
        return (obj instanceof TextUnitType) && j == ((TextUnitType) obj).m1950unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1947equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1948hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1949toStringimpl(long j) {
        return m1947equalsimpl0(j, Unspecified) ? "Unspecified" : m1947equalsimpl0(j, Sp) ? "Sp" : m1947equalsimpl0(j, Em) ? "Em" : "Invalid";
    }

    public boolean equals(Object obj) {
        return m1946equalsimpl(this.type, obj);
    }

    public int hashCode() {
        return m1948hashCodeimpl(this.type);
    }

    public String toString() {
        return m1949toStringimpl(this.type);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m1950unboximpl() {
        return this.type;
    }
}
