package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LineBreak.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LineBreak {
    public static final Companion Companion = new Companion(null);
    private static final int Heading;
    private static final int Paragraph;
    private static final int Simple;
    private static final int Unspecified;
    private final int mask;

    /* JADX INFO: compiled from: LineBreak.android.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getSimple-rAG3T2k, reason: not valid java name */
        public final int m1746getSimplerAG3T2k() {
            return LineBreak.Simple;
        }

        /* JADX INFO: renamed from: getUnspecified-rAG3T2k, reason: not valid java name */
        public final int m1747getUnspecifiedrAG3T2k() {
            return LineBreak.Unspecified;
        }
    }

    /* JADX INFO: compiled from: LineBreak.android.kt */
    public abstract class Strategy {
        public static final Companion Companion = new Companion(null);
        private static final int Simple = m1748constructorimpl(1);
        private static final int HighQuality = m1748constructorimpl(2);
        private static final int Balanced = m1748constructorimpl(3);
        private static final int Unspecified = m1748constructorimpl(0);

        /* JADX INFO: compiled from: LineBreak.android.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getBalanced-fcGXIks, reason: not valid java name */
            public final int m1751getBalancedfcGXIks() {
                return Strategy.Balanced;
            }

            /* JADX INFO: renamed from: getHighQuality-fcGXIks, reason: not valid java name */
            public final int m1752getHighQualityfcGXIks() {
                return Strategy.HighQuality;
            }

            /* JADX INFO: renamed from: getSimple-fcGXIks, reason: not valid java name */
            public final int m1753getSimplefcGXIks() {
                return Strategy.Simple;
            }
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        public static int m1748constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m1749equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m1750toStringimpl(int i) {
            return m1749equalsimpl0(i, Simple) ? "Strategy.Simple" : m1749equalsimpl0(i, HighQuality) ? "Strategy.HighQuality" : m1749equalsimpl0(i, Balanced) ? "Strategy.Balanced" : m1749equalsimpl0(i, Unspecified) ? "Strategy.Unspecified" : "Invalid";
        }
    }

    /* JADX INFO: compiled from: LineBreak.android.kt */
    public abstract class Strictness {
        public static final Companion Companion = new Companion(null);
        private static final int Default = m1754constructorimpl(1);
        private static final int Loose = m1754constructorimpl(2);
        private static final int Normal = m1754constructorimpl(3);
        private static final int Strict = m1754constructorimpl(4);
        private static final int Unspecified = m1754constructorimpl(0);

        /* JADX INFO: compiled from: LineBreak.android.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getDefault-usljTpc, reason: not valid java name */
            public final int m1757getDefaultusljTpc() {
                return Strictness.Default;
            }

            /* JADX INFO: renamed from: getLoose-usljTpc, reason: not valid java name */
            public final int m1758getLooseusljTpc() {
                return Strictness.Loose;
            }

            /* JADX INFO: renamed from: getNormal-usljTpc, reason: not valid java name */
            public final int m1759getNormalusljTpc() {
                return Strictness.Normal;
            }

            /* JADX INFO: renamed from: getStrict-usljTpc, reason: not valid java name */
            public final int m1760getStrictusljTpc() {
                return Strictness.Strict;
            }
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        public static int m1754constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m1755equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m1756toStringimpl(int i) {
            return m1755equalsimpl0(i, Default) ? "Strictness.None" : m1755equalsimpl0(i, Loose) ? "Strictness.Loose" : m1755equalsimpl0(i, Normal) ? "Strictness.Normal" : m1755equalsimpl0(i, Strict) ? "Strictness.Strict" : m1755equalsimpl0(i, Unspecified) ? "Strictness.Unspecified" : "Invalid";
        }
    }

    /* JADX INFO: compiled from: LineBreak.android.kt */
    public abstract class WordBreak {
        public static final Companion Companion = new Companion(null);
        private static final int Default = m1761constructorimpl(1);
        private static final int Phrase = m1761constructorimpl(2);
        private static final int Unspecified = m1761constructorimpl(0);

        /* JADX INFO: compiled from: LineBreak.android.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getDefault-jp8hJ3c, reason: not valid java name */
            public final int m1764getDefaultjp8hJ3c() {
                return WordBreak.Default;
            }

            /* JADX INFO: renamed from: getPhrase-jp8hJ3c, reason: not valid java name */
            public final int m1765getPhrasejp8hJ3c() {
                return WordBreak.Phrase;
            }
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        public static int m1761constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m1762equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m1763toStringimpl(int i) {
            return m1762equalsimpl0(i, Default) ? "WordBreak.None" : m1762equalsimpl0(i, Phrase) ? "WordBreak.Phrase" : m1762equalsimpl0(i, Unspecified) ? "WordBreak.Unspecified" : "Invalid";
        }
    }

    static {
        Strategy.Companion companion = Strategy.Companion;
        int iM1753getSimplefcGXIks = companion.m1753getSimplefcGXIks();
        Strictness.Companion companion2 = Strictness.Companion;
        int iM1759getNormalusljTpc = companion2.m1759getNormalusljTpc();
        WordBreak.Companion companion3 = WordBreak.Companion;
        Simple = m1737constructorimpl(LineBreak_androidKt.packBytes(iM1753getSimplefcGXIks, iM1759getNormalusljTpc, companion3.m1764getDefaultjp8hJ3c()));
        Heading = m1737constructorimpl(LineBreak_androidKt.packBytes(companion.m1751getBalancedfcGXIks(), companion2.m1758getLooseusljTpc(), companion3.m1765getPhrasejp8hJ3c()));
        Paragraph = m1737constructorimpl(LineBreak_androidKt.packBytes(companion.m1752getHighQualityfcGXIks(), companion2.m1760getStrictusljTpc(), companion3.m1764getDefaultjp8hJ3c()));
        Unspecified = m1737constructorimpl(0);
    }

    private /* synthetic */ LineBreak(int i) {
        this.mask = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ LineBreak m1736boximpl(int i) {
        return new LineBreak(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m1737constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1738equalsimpl(int i, Object obj) {
        return (obj instanceof LineBreak) && i == ((LineBreak) obj).m1745unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1739equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: getStrategy-fcGXIks, reason: not valid java name */
    public static final int m1740getStrategyfcGXIks(int i) {
        return Strategy.m1748constructorimpl(LineBreak_androidKt.unpackByte1(i));
    }

    /* JADX INFO: renamed from: getStrictness-usljTpc, reason: not valid java name */
    public static final int m1741getStrictnessusljTpc(int i) {
        return Strictness.m1754constructorimpl(LineBreak_androidKt.unpackByte2(i));
    }

    /* JADX INFO: renamed from: getWordBreak-jp8hJ3c, reason: not valid java name */
    public static final int m1742getWordBreakjp8hJ3c(int i) {
        return WordBreak.m1761constructorimpl(LineBreak_androidKt.unpackByte3(i));
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1743hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1744toStringimpl(int i) {
        return "LineBreak(strategy=" + ((Object) Strategy.m1750toStringimpl(m1740getStrategyfcGXIks(i))) + ", strictness=" + ((Object) Strictness.m1756toStringimpl(m1741getStrictnessusljTpc(i))) + ", wordBreak=" + ((Object) WordBreak.m1763toStringimpl(m1742getWordBreakjp8hJ3c(i))) + ')';
    }

    public boolean equals(Object obj) {
        return m1738equalsimpl(this.mask, obj);
    }

    public int hashCode() {
        return m1743hashCodeimpl(this.mask);
    }

    public String toString() {
        return m1744toStringimpl(this.mask);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m1745unboximpl() {
        return this.mask;
    }
}
