package androidx.compose.ui.text.style;

import androidx.compose.ui.text.internal.InlineClassHelperKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LineHeightStyle.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LineHeightStyle {
    public static final Companion Companion;
    private static final LineHeightStyle Default;
    private final float alignment;
    private final int mode;
    private final int trim;

    /* JADX INFO: compiled from: LineHeightStyle.kt */
    public final class Alignment {
        private final float topRatio;
        public static final Companion Companion = new Companion(null);
        private static final float Top = m1770constructorimpl(0.0f);
        private static final float Center = m1770constructorimpl(0.5f);
        private static final float Proportional = m1770constructorimpl(-1.0f);
        private static final float Bottom = m1770constructorimpl(1.0f);

        /* JADX INFO: compiled from: LineHeightStyle.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getCenter-PIaL0Z0, reason: not valid java name */
            public final float m1776getCenterPIaL0Z0() {
                return Alignment.Center;
            }

            /* JADX INFO: renamed from: getProportional-PIaL0Z0, reason: not valid java name */
            public final float m1777getProportionalPIaL0Z0() {
                return Alignment.Proportional;
            }
        }

        private /* synthetic */ Alignment(float f) {
            this.topRatio = f;
        }

        /* JADX INFO: renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Alignment m1769boximpl(float f) {
            return new Alignment(f);
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        public static float m1770constructorimpl(float f) {
            if (!((0.0f <= f && f <= 1.0f) || f == -1.0f)) {
                InlineClassHelperKt.throwIllegalStateException("topRatio should be in [0..1] range or -1");
            }
            return f;
        }

        /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
        public static boolean m1771equalsimpl(float f, Object obj) {
            return (obj instanceof Alignment) && Float.compare(f, ((Alignment) obj).m1775unboximpl()) == 0;
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m1772equalsimpl0(float f, float f2) {
            return Float.compare(f, f2) == 0;
        }

        /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
        public static int m1773hashCodeimpl(float f) {
            return Float.hashCode(f);
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m1774toStringimpl(float f) {
            if (f == Top) {
                return "LineHeightStyle.Alignment.Top";
            }
            if (f == Center) {
                return "LineHeightStyle.Alignment.Center";
            }
            if (f == Proportional) {
                return "LineHeightStyle.Alignment.Proportional";
            }
            if (f == Bottom) {
                return "LineHeightStyle.Alignment.Bottom";
            }
            return "LineHeightStyle.Alignment(topPercentage = " + f + ')';
        }

        public boolean equals(Object obj) {
            return m1771equalsimpl(this.topRatio, obj);
        }

        public int hashCode() {
            return m1773hashCodeimpl(this.topRatio);
        }

        public String toString() {
            return m1774toStringimpl(this.topRatio);
        }

        /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
        public final /* synthetic */ float m1775unboximpl() {
            return this.topRatio;
        }
    }

    /* JADX INFO: compiled from: LineHeightStyle.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final LineHeightStyle getDefault() {
            return LineHeightStyle.Default;
        }
    }

    /* JADX INFO: compiled from: LineHeightStyle.kt */
    public final class Mode {
        public static final Companion Companion = new Companion(null);
        private static final int Fixed = m1779constructorimpl(0);
        private static final int Minimum = m1779constructorimpl(1);
        private final int value;

        /* JADX INFO: compiled from: LineHeightStyle.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getFixed-lzQqcRY, reason: not valid java name */
            public final int m1785getFixedlzQqcRY() {
                return Mode.Fixed;
            }

            /* JADX INFO: renamed from: getMinimum-lzQqcRY, reason: not valid java name */
            public final int m1786getMinimumlzQqcRY() {
                return Mode.Minimum;
            }
        }

        private /* synthetic */ Mode(int i) {
            this.value = i;
        }

        /* JADX INFO: renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Mode m1778boximpl(int i) {
            return new Mode(i);
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        private static int m1779constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
        public static boolean m1780equalsimpl(int i, Object obj) {
            return (obj instanceof Mode) && i == ((Mode) obj).m1784unboximpl();
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m1781equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
        public static int m1782hashCodeimpl(int i) {
            return Integer.hashCode(i);
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m1783toStringimpl(int i) {
            return "Mode(value=" + i + ')';
        }

        public boolean equals(Object obj) {
            return m1780equalsimpl(this.value, obj);
        }

        public int hashCode() {
            return m1782hashCodeimpl(this.value);
        }

        public String toString() {
            return m1783toStringimpl(this.value);
        }

        /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
        public final /* synthetic */ int m1784unboximpl() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: LineHeightStyle.kt */
    public final class Trim {
        private final int value;
        public static final Companion Companion = new Companion(null);
        private static final int FirstLineTop = m1788constructorimpl(1);
        private static final int LastLineBottom = m1788constructorimpl(16);
        private static final int Both = m1788constructorimpl(17);
        private static final int None = m1788constructorimpl(0);

        /* JADX INFO: compiled from: LineHeightStyle.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getBoth-EVpEnUU, reason: not valid java name */
            public final int m1796getBothEVpEnUU() {
                return Trim.Both;
            }

            /* JADX INFO: renamed from: getNone-EVpEnUU, reason: not valid java name */
            public final int m1797getNoneEVpEnUU() {
                return Trim.None;
            }
        }

        private /* synthetic */ Trim(int i) {
            this.value = i;
        }

        /* JADX INFO: renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Trim m1787boximpl(int i) {
            return new Trim(i);
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        private static int m1788constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
        public static boolean m1789equalsimpl(int i, Object obj) {
            return (obj instanceof Trim) && i == ((Trim) obj).m1795unboximpl();
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m1790equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
        public static int m1791hashCodeimpl(int i) {
            return Integer.hashCode(i);
        }

        /* JADX INFO: renamed from: isTrimFirstLineTop-impl$ui_text_release, reason: not valid java name */
        public static final boolean m1792isTrimFirstLineTopimpl$ui_text_release(int i) {
            return (i & 1) > 0;
        }

        /* JADX INFO: renamed from: isTrimLastLineBottom-impl$ui_text_release, reason: not valid java name */
        public static final boolean m1793isTrimLastLineBottomimpl$ui_text_release(int i) {
            return (i & 16) > 0;
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m1794toStringimpl(int i) {
            return i == FirstLineTop ? "LineHeightStyle.Trim.FirstLineTop" : i == LastLineBottom ? "LineHeightStyle.Trim.LastLineBottom" : i == Both ? "LineHeightStyle.Trim.Both" : i == None ? "LineHeightStyle.Trim.None" : "Invalid";
        }

        public boolean equals(Object obj) {
            return m1789equalsimpl(this.value, obj);
        }

        public int hashCode() {
            return m1791hashCodeimpl(this.value);
        }

        public String toString() {
            return m1794toStringimpl(this.value);
        }

        /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
        public final /* synthetic */ int m1795unboximpl() {
            return this.value;
        }
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        Companion = new Companion(defaultConstructorMarker);
        Default = new LineHeightStyle(Alignment.Companion.m1777getProportionalPIaL0Z0(), Trim.Companion.m1796getBothEVpEnUU(), Mode.Companion.m1785getFixedlzQqcRY(), defaultConstructorMarker);
    }

    private LineHeightStyle(float f, int i) {
        this(f, i, Mode.Companion.m1785getFixedlzQqcRY(), null);
    }

    private LineHeightStyle(float f, int i, int i2) {
        this.alignment = f;
        this.trim = i;
        this.mode = i2;
    }

    public /* synthetic */ LineHeightStyle(float f, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, i, i2);
    }

    public /* synthetic */ LineHeightStyle(float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LineHeightStyle)) {
            return false;
        }
        LineHeightStyle lineHeightStyle = (LineHeightStyle) obj;
        return Alignment.m1772equalsimpl0(this.alignment, lineHeightStyle.alignment) && Trim.m1790equalsimpl0(this.trim, lineHeightStyle.trim) && Mode.m1781equalsimpl0(this.mode, lineHeightStyle.mode);
    }

    /* JADX INFO: renamed from: getAlignment-PIaL0Z0, reason: not valid java name */
    public final float m1766getAlignmentPIaL0Z0() {
        return this.alignment;
    }

    /* JADX INFO: renamed from: getMode-lzQqcRY, reason: not valid java name */
    public final int m1767getModelzQqcRY() {
        return this.mode;
    }

    /* JADX INFO: renamed from: getTrim-EVpEnUU, reason: not valid java name */
    public final int m1768getTrimEVpEnUU() {
        return this.trim;
    }

    public int hashCode() {
        return (((Alignment.m1773hashCodeimpl(this.alignment) * 31) + Trim.m1791hashCodeimpl(this.trim)) * 31) + Mode.m1782hashCodeimpl(this.mode);
    }

    public String toString() {
        return "LineHeightStyle(alignment=" + ((Object) Alignment.m1774toStringimpl(this.alignment)) + ", trim=" + ((Object) Trim.m1794toStringimpl(this.trim)) + ",mode=" + ((Object) Mode.m1783toStringimpl(this.mode)) + ')';
    }
}
