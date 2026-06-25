package androidx.compose.ui.semantics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SemanticsProperties.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Role {
    private final int value;
    public static final Companion Companion = new Companion(null);
    private static final int Button = m734constructorimpl(0);
    private static final int Checkbox = m734constructorimpl(1);
    private static final int Switch = m734constructorimpl(2);
    private static final int RadioButton = m734constructorimpl(3);
    private static final int Tab = m734constructorimpl(4);
    private static final int Image = m734constructorimpl(5);
    private static final int DropdownList = m734constructorimpl(6);
    private static final int ValuePicker = m734constructorimpl(7);
    private static final int Carousel = m734constructorimpl(8);

    /* JADX INFO: compiled from: SemanticsProperties.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getButton-o7Vup1c, reason: not valid java name */
        public final int m740getButtono7Vup1c() {
            return Role.Button;
        }

        /* JADX INFO: renamed from: getCarousel-o7Vup1c, reason: not valid java name */
        public final int m741getCarouselo7Vup1c() {
            return Role.Carousel;
        }

        /* JADX INFO: renamed from: getCheckbox-o7Vup1c, reason: not valid java name */
        public final int m742getCheckboxo7Vup1c() {
            return Role.Checkbox;
        }

        /* JADX INFO: renamed from: getDropdownList-o7Vup1c, reason: not valid java name */
        public final int m743getDropdownListo7Vup1c() {
            return Role.DropdownList;
        }

        /* JADX INFO: renamed from: getImage-o7Vup1c, reason: not valid java name */
        public final int m744getImageo7Vup1c() {
            return Role.Image;
        }

        /* JADX INFO: renamed from: getRadioButton-o7Vup1c, reason: not valid java name */
        public final int m745getRadioButtono7Vup1c() {
            return Role.RadioButton;
        }

        /* JADX INFO: renamed from: getSwitch-o7Vup1c, reason: not valid java name */
        public final int m746getSwitcho7Vup1c() {
            return Role.Switch;
        }

        /* JADX INFO: renamed from: getTab-o7Vup1c, reason: not valid java name */
        public final int m747getTabo7Vup1c() {
            return Role.Tab;
        }

        /* JADX INFO: renamed from: getValuePicker-o7Vup1c, reason: not valid java name */
        public final int m748getValuePickero7Vup1c() {
            return Role.ValuePicker;
        }
    }

    private /* synthetic */ Role(int i) {
        this.value = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Role m733boximpl(int i) {
        return new Role(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m734constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m735equalsimpl(int i, Object obj) {
        return (obj instanceof Role) && i == ((Role) obj).m739unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m736equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m737hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m738toStringimpl(int i) {
        return m736equalsimpl0(i, Button) ? "Button" : m736equalsimpl0(i, Checkbox) ? "Checkbox" : m736equalsimpl0(i, Switch) ? "Switch" : m736equalsimpl0(i, RadioButton) ? "RadioButton" : m736equalsimpl0(i, Tab) ? "Tab" : m736equalsimpl0(i, Image) ? "Image" : m736equalsimpl0(i, DropdownList) ? "DropdownList" : m736equalsimpl0(i, ValuePicker) ? "Picker" : m736equalsimpl0(i, Carousel) ? "Carousel" : "Unknown";
    }

    public boolean equals(Object obj) {
        return m735equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m737hashCodeimpl(this.value);
    }

    public String toString() {
        return m738toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m739unboximpl() {
        return this.value;
    }
}
