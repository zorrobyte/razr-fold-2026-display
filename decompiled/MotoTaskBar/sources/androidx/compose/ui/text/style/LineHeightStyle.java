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
        private static final float Top = m923constructorimpl(0.0f);
        private static final float Center = m923constructorimpl(0.5f);
        private static final float Proportional = m923constructorimpl(-1.0f);
        private static final float Bottom = m923constructorimpl(1.0f);

        /* JADX INFO: compiled from: LineHeightStyle.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getProportional-PIaL0Z0, reason: not valid java name */
            public final float m929getProportionalPIaL0Z0() {
                return Alignment.Proportional;
            }
        }

        private /* synthetic */ Alignment(float f) {
            this.topRatio = f;
        }

        /* JADX INFO: renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Alignment m922boximpl(float f) {
            return new Alignment(f);
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        public static float m923constructorimpl(float f) {
            if (!((0.0f <= f && f <= 1.0f) || f == -1.0f)) {
                InlineClassHelperKt.throwIllegalStateException("topRatio should be in [0..1] range or -1");
            }
            return f;
        }

        /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
        public static boolean m924equalsimpl(float f, Object obj) {
            return (obj instanceof Alignment) && Float.compare(f, ((Alignment) obj).m928unboximpl()) == 0;
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m925equalsimpl0(float f, float f2) {
            return Float.compare(f, f2) == 0;
        }

        /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
        public static int m926hashCodeimpl(float f) {
            return Float.hashCode(f);
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m927toStringimpl(float f) {
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
            return m924equalsimpl(this.topRatio, obj);
        }

        public int hashCode() {
            return m926hashCodeimpl(this.topRatio);
        }

        public String toString() {
            return m927toStringimpl(this.topRatio);
        }

        /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
        public final /* synthetic */ float m928unboximpl() {
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
    }

    /* JADX INFO: compiled from: LineHeightStyle.kt */
    public final class Mode {
        public static final Companion Companion = new Companion(null);
        private static final int Fixed = m931constructorimpl(0);
        private static final int Minimum = m931constructorimpl(1);
        private final int value;

        /* JADX INFO: compiled from: LineHeightStyle.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getFixed-lzQqcRY, reason: not valid java name */
            public final int m937getFixedlzQqcRY() {
                return Mode.Fixed;
            }
        }

        private /* synthetic */ Mode(int i) {
            this.value = i;
        }

        /* JADX INFO: renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Mode m930boximpl(int i) {
            return new Mode(i);
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        private static int m931constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
        public static boolean m932equalsimpl(int i, Object obj) {
            return (obj instanceof Mode) && i == ((Mode) obj).m936unboximpl();
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m933equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
        public static int m934hashCodeimpl(int i) {
            return Integer.hashCode(i);
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m935toStringimpl(int i) {
            return "Mode(value=" + i + ')';
        }

        public boolean equals(Object obj) {
            return m932equalsimpl(this.value, obj);
        }

        public int hashCode() {
            return m934hashCodeimpl(this.value);
        }

        public String toString() {
            return m935toStringimpl(this.value);
        }

        /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
        public final /* synthetic */ int m936unboximpl() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: LineHeightStyle.kt */
    public final class Trim {
        private final int value;
        public static final Companion Companion = new Companion(null);
        private static final int FirstLineTop = m939constructorimpl(1);
        private static final int LastLineBottom = m939constructorimpl(16);
        private static final int Both = m939constructorimpl(17);
        private static final int None = m939constructorimpl(0);

        /* JADX INFO: compiled from: LineHeightStyle.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getBoth-EVpEnUU, reason: not valid java name */
            public final int m945getBothEVpEnUU() {
                return Trim.Both;
            }
        }

        private /* synthetic */ Trim(int i) {
            this.value = i;
        }

        /* JADX INFO: renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Trim m938boximpl(int i) {
            return new Trim(i);
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        private static int m939constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
        public static boolean m940equalsimpl(int i, Object obj) {
            return (obj instanceof Trim) && i == ((Trim) obj).m944unboximpl();
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m941equalsimpl0(int i, int i2) {
            return i == i2;
        }

        /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
        public static int m942hashCodeimpl(int i) {
            return Integer.hashCode(i);
        }

        /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
        public static String m943toStringimpl(int i) {
            return i == FirstLineTop ? "LineHeightStyle.Trim.FirstLineTop" : i == LastLineBottom ? "LineHeightStyle.Trim.LastLineBottom" : i == Both ? "LineHeightStyle.Trim.Both" : i == None ? "LineHeightStyle.Trim.None" : "Invalid";
        }

        public boolean equals(Object obj) {
            return m940equalsimpl(this.value, obj);
        }

        public int hashCode() {
            return m942hashCodeimpl(this.value);
        }

        public String toString() {
            return m943toStringimpl(this.value);
        }

        /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
        public final /* synthetic */ int m944unboximpl() {
            return this.value;
        }
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        Companion = new Companion(defaultConstructorMarker);
        Default = new LineHeightStyle(Alignment.Companion.m929getProportionalPIaL0Z0(), Trim.Companion.m945getBothEVpEnUU(), Mode.Companion.m937getFixedlzQqcRY(), defaultConstructorMarker);
    }

    private LineHeightStyle(float f, int i, int i2) {
        this.alignment = f;
        this.trim = i;
        this.mode = i2;
    }

    public /* synthetic */ LineHeightStyle(float f, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LineHeightStyle)) {
            return false;
        }
        LineHeightStyle lineHeightStyle = (LineHeightStyle) obj;
        return Alignment.m925equalsimpl0(this.alignment, lineHeightStyle.alignment) && Trim.m941equalsimpl0(this.trim, lineHeightStyle.trim) && Mode.m933equalsimpl0(this.mode, lineHeightStyle.mode);
    }

    /* JADX INFO: renamed from: getAlignment-PIaL0Z0, reason: not valid java name */
    public final float m919getAlignmentPIaL0Z0() {
        return this.alignment;
    }

    /* JADX INFO: renamed from: getMode-lzQqcRY, reason: not valid java name */
    public final int m920getModelzQqcRY() {
        return this.mode;
    }

    /* JADX INFO: renamed from: getTrim-EVpEnUU, reason: not valid java name */
    public final int m921getTrimEVpEnUU() {
        return this.trim;
    }

    public int hashCode() {
        return (((Alignment.m926hashCodeimpl(this.alignment) * 31) + Trim.m942hashCodeimpl(this.trim)) * 31) + Mode.m934hashCodeimpl(this.mode);
    }

    public String toString() {
        return "LineHeightStyle(alignment=" + ((Object) Alignment.m927toStringimpl(this.alignment)) + ", trim=" + ((Object) Trim.m943toStringimpl(this.trim)) + ",mode=" + ((Object) Mode.m935toStringimpl(this.mode)) + ')';
    }
}
