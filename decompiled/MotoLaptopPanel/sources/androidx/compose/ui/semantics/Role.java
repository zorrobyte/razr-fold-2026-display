package androidx.compose.ui.semantics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SemanticsProperties.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Role {
    private final int value;
    public static final Companion Companion = new Companion(null);
    private static final int Button = m1483constructorimpl(0);
    private static final int Checkbox = m1483constructorimpl(1);
    private static final int Switch = m1483constructorimpl(2);
    private static final int RadioButton = m1483constructorimpl(3);
    private static final int Tab = m1483constructorimpl(4);
    private static final int Image = m1483constructorimpl(5);
    private static final int DropdownList = m1483constructorimpl(6);
    private static final int ValuePicker = m1483constructorimpl(7);
    private static final int Carousel = m1483constructorimpl(8);

    /* JADX INFO: compiled from: SemanticsProperties.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getButton-o7Vup1c, reason: not valid java name */
        public final int m1489getButtono7Vup1c() {
            return Role.Button;
        }

        /* JADX INFO: renamed from: getCarousel-o7Vup1c, reason: not valid java name */
        public final int m1490getCarouselo7Vup1c() {
            return Role.Carousel;
        }

        /* JADX INFO: renamed from: getCheckbox-o7Vup1c, reason: not valid java name */
        public final int m1491getCheckboxo7Vup1c() {
            return Role.Checkbox;
        }

        /* JADX INFO: renamed from: getDropdownList-o7Vup1c, reason: not valid java name */
        public final int m1492getDropdownListo7Vup1c() {
            return Role.DropdownList;
        }

        /* JADX INFO: renamed from: getImage-o7Vup1c, reason: not valid java name */
        public final int m1493getImageo7Vup1c() {
            return Role.Image;
        }

        /* JADX INFO: renamed from: getRadioButton-o7Vup1c, reason: not valid java name */
        public final int m1494getRadioButtono7Vup1c() {
            return Role.RadioButton;
        }

        /* JADX INFO: renamed from: getSwitch-o7Vup1c, reason: not valid java name */
        public final int m1495getSwitcho7Vup1c() {
            return Role.Switch;
        }

        /* JADX INFO: renamed from: getTab-o7Vup1c, reason: not valid java name */
        public final int m1496getTabo7Vup1c() {
            return Role.Tab;
        }

        /* JADX INFO: renamed from: getValuePicker-o7Vup1c, reason: not valid java name */
        public final int m1497getValuePickero7Vup1c() {
            return Role.ValuePicker;
        }
    }

    private /* synthetic */ Role(int i) {
        this.value = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Role m1482boximpl(int i) {
        return new Role(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m1483constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1484equalsimpl(int i, Object obj) {
        return (obj instanceof Role) && i == ((Role) obj).m1488unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1485equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1486hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1487toStringimpl(int i) {
        return m1485equalsimpl0(i, Button) ? "Button" : m1485equalsimpl0(i, Checkbox) ? "Checkbox" : m1485equalsimpl0(i, Switch) ? "Switch" : m1485equalsimpl0(i, RadioButton) ? "RadioButton" : m1485equalsimpl0(i, Tab) ? "Tab" : m1485equalsimpl0(i, Image) ? "Image" : m1485equalsimpl0(i, DropdownList) ? "DropdownList" : m1485equalsimpl0(i, ValuePicker) ? "Picker" : m1485equalsimpl0(i, Carousel) ? "Carousel" : "Unknown";
    }

    public boolean equals(Object obj) {
        return m1484equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m1486hashCodeimpl(this.value);
    }

    public String toString() {
        return m1487toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m1488unboximpl() {
        return this.value;
    }
}
