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
    }

    /* JADX INFO: compiled from: LineBreak.android.kt */
    public abstract class Strategy {
        public static final Companion Companion = new Companion(null);
        private static final int Simple = m902constructorimpl(1);
        private static final int HighQuality = m902constructorimpl(2);
        private static final int Balanced = m902constructorimpl(3);
        private static final int Unspecified = m902constructorimpl(0);

        /* JADX INFO: compiled from: LineBreak.android.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getBalanced-fcGXIks, reason: not valid java name */
            public final int m905getBalancedfcGXIks() {
                return Strategy.Balanced;
            }

            /* JADX INFO: renamed from: getHighQuality-fcGXIks, reason: not valid java name */
            public final int m906getHighQualityfcGXIks() {
                return Strategy.HighQuality;
            }

            /* JADX INFO: renamed from: getSimple-fcGXIks, reason: not valid java name */
            public final int m907getSimplefcGXIks() {
                return Strategy.Simple;
            }
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        public static int m902constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m903equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m904toStringimpl(int i) {
            return m903equalsimpl0(i, Simple) ? "Strategy.Simple" : m903equalsimpl0(i, HighQuality) ? "Strategy.HighQuality" : m903equalsimpl0(i, Balanced) ? "Strategy.Balanced" : m903equalsimpl0(i, Unspecified) ? "Strategy.Unspecified" : "Invalid";
        }
    }

    /* JADX INFO: compiled from: LineBreak.android.kt */
    public abstract class Strictness {
        public static final Companion Companion = new Companion(null);
        private static final int Default = m908constructorimpl(1);
        private static final int Loose = m908constructorimpl(2);
        private static final int Normal = m908constructorimpl(3);
        private static final int Strict = m908constructorimpl(4);
        private static final int Unspecified = m908constructorimpl(0);

        /* JADX INFO: compiled from: LineBreak.android.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getLoose-usljTpc, reason: not valid java name */
            public final int m911getLooseusljTpc() {
                return Strictness.Loose;
            }

            /* JADX INFO: renamed from: getNormal-usljTpc, reason: not valid java name */
            public final int m912getNormalusljTpc() {
                return Strictness.Normal;
            }

            /* JADX INFO: renamed from: getStrict-usljTpc, reason: not valid java name */
            public final int m913getStrictusljTpc() {
                return Strictness.Strict;
            }
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        public static int m908constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m909equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m910toStringimpl(int i) {
            return m909equalsimpl0(i, Default) ? "Strictness.None" : m909equalsimpl0(i, Loose) ? "Strictness.Loose" : m909equalsimpl0(i, Normal) ? "Strictness.Normal" : m909equalsimpl0(i, Strict) ? "Strictness.Strict" : m909equalsimpl0(i, Unspecified) ? "Strictness.Unspecified" : "Invalid";
        }
    }

    /* JADX INFO: compiled from: LineBreak.android.kt */
    public abstract class WordBreak {
        public static final Companion Companion = new Companion(null);
        private static final int Default = m914constructorimpl(1);
        private static final int Phrase = m914constructorimpl(2);
        private static final int Unspecified = m914constructorimpl(0);

        /* JADX INFO: compiled from: LineBreak.android.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getDefault-jp8hJ3c, reason: not valid java name */
            public final int m917getDefaultjp8hJ3c() {
                return WordBreak.Default;
            }

            /* JADX INFO: renamed from: getPhrase-jp8hJ3c, reason: not valid java name */
            public final int m918getPhrasejp8hJ3c() {
                return WordBreak.Phrase;
            }
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        public static int m914constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m915equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m916toStringimpl(int i) {
            return m915equalsimpl0(i, Default) ? "WordBreak.None" : m915equalsimpl0(i, Phrase) ? "WordBreak.Phrase" : m915equalsimpl0(i, Unspecified) ? "WordBreak.Unspecified" : "Invalid";
        }
    }

    static {
        Strategy.Companion companion = Strategy.Companion;
        int iM907getSimplefcGXIks = companion.m907getSimplefcGXIks();
        Strictness.Companion companion2 = Strictness.Companion;
        int iM912getNormalusljTpc = companion2.m912getNormalusljTpc();
        WordBreak.Companion companion3 = WordBreak.Companion;
        Simple = m893constructorimpl(LineBreak_androidKt.packBytes(iM907getSimplefcGXIks, iM912getNormalusljTpc, companion3.m917getDefaultjp8hJ3c()));
        Heading = m893constructorimpl(LineBreak_androidKt.packBytes(companion.m905getBalancedfcGXIks(), companion2.m911getLooseusljTpc(), companion3.m918getPhrasejp8hJ3c()));
        Paragraph = m893constructorimpl(LineBreak_androidKt.packBytes(companion.m906getHighQualityfcGXIks(), companion2.m913getStrictusljTpc(), companion3.m917getDefaultjp8hJ3c()));
        Unspecified = m893constructorimpl(0);
    }

    private /* synthetic */ LineBreak(int i) {
        this.mask = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ LineBreak m892boximpl(int i) {
        return new LineBreak(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m893constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m894equalsimpl(int i, Object obj) {
        return (obj instanceof LineBreak) && i == ((LineBreak) obj).m901unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m895equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: getStrategy-fcGXIks, reason: not valid java name */
    public static final int m896getStrategyfcGXIks(int i) {
        return Strategy.m902constructorimpl(LineBreak_androidKt.unpackByte1(i));
    }

    /* JADX INFO: renamed from: getStrictness-usljTpc, reason: not valid java name */
    public static final int m897getStrictnessusljTpc(int i) {
        return Strictness.m908constructorimpl(LineBreak_androidKt.unpackByte2(i));
    }

    /* JADX INFO: renamed from: getWordBreak-jp8hJ3c, reason: not valid java name */
    public static final int m898getWordBreakjp8hJ3c(int i) {
        return WordBreak.m914constructorimpl(LineBreak_androidKt.unpackByte3(i));
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m899hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m900toStringimpl(int i) {
        return "LineBreak(strategy=" + ((Object) Strategy.m904toStringimpl(m896getStrategyfcGXIks(i))) + ", strictness=" + ((Object) Strictness.m910toStringimpl(m897getStrictnessusljTpc(i))) + ", wordBreak=" + ((Object) WordBreak.m916toStringimpl(m898getWordBreakjp8hJ3c(i))) + ')';
    }

    public boolean equals(Object obj) {
        return m894equalsimpl(this.mask, obj);
    }

    public int hashCode() {
        return m899hashCodeimpl(this.mask);
    }

    public String toString() {
        return m900toStringimpl(this.mask);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m901unboximpl() {
        return this.mask;
    }
}
