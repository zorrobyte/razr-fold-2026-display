package androidx.compose.material3;

import androidx.compose.ui.graphics.Color;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SliderColors {
    private final long activeTickColor;
    private final long activeTrackColor;
    private final long disabledActiveTickColor;
    private final long disabledActiveTrackColor;
    private final long disabledInactiveTickColor;
    private final long disabledInactiveTrackColor;
    private final long disabledThumbColor;
    private final long inactiveTickColor;
    private final long inactiveTrackColor;
    private final long thumbColor;

    private SliderColors(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
        this.thumbColor = j;
        this.activeTrackColor = j2;
        this.activeTickColor = j3;
        this.inactiveTrackColor = j4;
        this.inactiveTickColor = j5;
        this.disabledThumbColor = j6;
        this.disabledActiveTrackColor = j7;
        this.disabledActiveTickColor = j8;
        this.disabledInactiveTrackColor = j9;
        this.disabledInactiveTickColor = j10;
    }

    public /* synthetic */ SliderColors(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8, j9, j10);
    }

    /* JADX INFO: renamed from: copy--K518z4$default, reason: not valid java name */
    public static /* synthetic */ SliderColors m311copyK518z4$default(SliderColors sliderColors, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, int i, Object obj) {
        long j11;
        long j12;
        long j13 = (i & 1) != 0 ? sliderColors.thumbColor : j;
        long j14 = (i & 2) != 0 ? sliderColors.activeTrackColor : j2;
        long j15 = (i & 4) != 0 ? sliderColors.activeTickColor : j3;
        long j16 = (i & 8) != 0 ? sliderColors.inactiveTrackColor : j4;
        long j17 = (i & 16) != 0 ? sliderColors.inactiveTickColor : j5;
        long j18 = (i & 32) != 0 ? sliderColors.disabledThumbColor : j6;
        long j19 = (i & 64) != 0 ? sliderColors.disabledActiveTrackColor : j7;
        long j20 = j13;
        long j21 = (i & 128) != 0 ? sliderColors.disabledActiveTickColor : j8;
        long j22 = (i & 256) != 0 ? sliderColors.disabledInactiveTrackColor : j9;
        if ((i & 512) != 0) {
            j12 = j22;
            j11 = sliderColors.disabledInactiveTickColor;
        } else {
            j11 = j10;
            j12 = j22;
        }
        return sliderColors.m312copyK518z4(j20, j14, j15, j16, j17, j18, j19, j21, j12, j11);
    }

    /* JADX INFO: renamed from: copy--K518z4, reason: not valid java name */
    public final SliderColors m312copyK518z4(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
        return new SliderColors(j != 16 ? j : this.thumbColor, j2 != 16 ? j2 : this.activeTrackColor, j3 != 16 ? j3 : this.activeTickColor, j4 != 16 ? j4 : this.inactiveTrackColor, j5 != 16 ? j5 : this.inactiveTickColor, j6 != 16 ? j6 : this.disabledThumbColor, j7 != 16 ? j7 : this.disabledActiveTrackColor, j8 != 16 ? j8 : this.disabledActiveTickColor, j9 != 16 ? j9 : this.disabledInactiveTrackColor, j10 != 16 ? j10 : this.disabledInactiveTickColor, null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SliderColors)) {
            return false;
        }
        SliderColors sliderColors = (SliderColors) obj;
        return Color.m882equalsimpl0(this.thumbColor, sliderColors.thumbColor) && Color.m882equalsimpl0(this.activeTrackColor, sliderColors.activeTrackColor) && Color.m882equalsimpl0(this.activeTickColor, sliderColors.activeTickColor) && Color.m882equalsimpl0(this.inactiveTrackColor, sliderColors.inactiveTrackColor) && Color.m882equalsimpl0(this.inactiveTickColor, sliderColors.inactiveTickColor) && Color.m882equalsimpl0(this.disabledThumbColor, sliderColors.disabledThumbColor) && Color.m882equalsimpl0(this.disabledActiveTrackColor, sliderColors.disabledActiveTrackColor) && Color.m882equalsimpl0(this.disabledActiveTickColor, sliderColors.disabledActiveTickColor) && Color.m882equalsimpl0(this.disabledInactiveTrackColor, sliderColors.disabledInactiveTrackColor) && Color.m882equalsimpl0(this.disabledInactiveTickColor, sliderColors.disabledInactiveTickColor);
    }

    /* JADX INFO: renamed from: getActiveTickColor-0d7_KjU, reason: not valid java name */
    public final long m313getActiveTickColor0d7_KjU() {
        return this.activeTickColor;
    }

    /* JADX INFO: renamed from: getActiveTrackColor-0d7_KjU, reason: not valid java name */
    public final long m314getActiveTrackColor0d7_KjU() {
        return this.activeTrackColor;
    }

    /* JADX INFO: renamed from: getInactiveTickColor-0d7_KjU, reason: not valid java name */
    public final long m315getInactiveTickColor0d7_KjU() {
        return this.inactiveTickColor;
    }

    /* JADX INFO: renamed from: getInactiveTrackColor-0d7_KjU, reason: not valid java name */
    public final long m316getInactiveTrackColor0d7_KjU() {
        return this.inactiveTrackColor;
    }

    public int hashCode() {
        return (((((((((((((((((Color.m888hashCodeimpl(this.thumbColor) * 31) + Color.m888hashCodeimpl(this.activeTrackColor)) * 31) + Color.m888hashCodeimpl(this.activeTickColor)) * 31) + Color.m888hashCodeimpl(this.inactiveTrackColor)) * 31) + Color.m888hashCodeimpl(this.inactiveTickColor)) * 31) + Color.m888hashCodeimpl(this.disabledThumbColor)) * 31) + Color.m888hashCodeimpl(this.disabledActiveTrackColor)) * 31) + Color.m888hashCodeimpl(this.disabledActiveTickColor)) * 31) + Color.m888hashCodeimpl(this.disabledInactiveTrackColor)) * 31) + Color.m888hashCodeimpl(this.disabledInactiveTickColor);
    }

    /* JADX INFO: renamed from: thumbColor-vNxB06k$material3_release, reason: not valid java name */
    public final long m317thumbColorvNxB06k$material3_release(boolean z) {
        return z ? this.thumbColor : this.disabledThumbColor;
    }

    /* JADX INFO: renamed from: tickColor-WaAFU9c$material3_release, reason: not valid java name */
    public final long m318tickColorWaAFU9c$material3_release(boolean z, boolean z2) {
        return z ? z2 ? this.activeTickColor : this.inactiveTickColor : z2 ? this.disabledActiveTickColor : this.disabledInactiveTickColor;
    }

    /* JADX INFO: renamed from: trackColor-WaAFU9c$material3_release, reason: not valid java name */
    public final long m319trackColorWaAFU9c$material3_release(boolean z, boolean z2) {
        return z ? z2 ? this.activeTrackColor : this.inactiveTrackColor : z2 ? this.disabledActiveTrackColor : this.disabledInactiveTrackColor;
    }
}
