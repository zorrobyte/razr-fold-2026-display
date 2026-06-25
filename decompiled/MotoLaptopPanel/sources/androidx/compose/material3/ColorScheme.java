package androidx.compose.material3;

import androidx.compose.ui.graphics.Color;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ColorScheme.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ColorScheme {
    private final long background;
    private IconButtonColors defaultIconButtonColorsCached;
    private SliderColors defaultSliderColorsCached;
    private final long error;
    private final long errorContainer;
    private final long inverseOnSurface;
    private final long inversePrimary;
    private final long inverseSurface;
    private final long onBackground;
    private final long onError;
    private final long onErrorContainer;
    private final long onPrimary;
    private final long onPrimaryContainer;
    private final long onSecondary;
    private final long onSecondaryContainer;
    private final long onSurface;
    private final long onSurfaceVariant;
    private final long onTertiary;
    private final long onTertiaryContainer;
    private final long outline;
    private final long outlineVariant;
    private final long primary;
    private final long primaryContainer;
    private final long scrim;
    private final long secondary;
    private final long secondaryContainer;
    private final long surface;
    private final long surfaceBright;
    private final long surfaceContainer;
    private final long surfaceContainerHigh;
    private final long surfaceContainerHighest;
    private final long surfaceContainerLow;
    private final long surfaceContainerLowest;
    private final long surfaceDim;
    private final long surfaceTint;
    private final long surfaceVariant;
    private final long tertiary;
    private final long tertiaryContainer;

    private ColorScheme(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36) {
        this.primary = j;
        this.onPrimary = j2;
        this.primaryContainer = j3;
        this.onPrimaryContainer = j4;
        this.inversePrimary = j5;
        this.secondary = j6;
        this.onSecondary = j7;
        this.secondaryContainer = j8;
        this.onSecondaryContainer = j9;
        this.tertiary = j10;
        this.onTertiary = j11;
        this.tertiaryContainer = j12;
        this.onTertiaryContainer = j13;
        this.background = j14;
        this.onBackground = j15;
        this.surface = j16;
        this.onSurface = j17;
        this.surfaceVariant = j18;
        this.onSurfaceVariant = j19;
        this.surfaceTint = j20;
        this.inverseSurface = j21;
        this.inverseOnSurface = j22;
        this.error = j23;
        this.onError = j24;
        this.errorContainer = j25;
        this.onErrorContainer = j26;
        this.outline = j27;
        this.outlineVariant = j28;
        this.scrim = j29;
        this.surfaceBright = j30;
        this.surfaceDim = j31;
        this.surfaceContainer = j32;
        this.surfaceContainerHigh = j33;
        this.surfaceContainerHighest = j34;
        this.surfaceContainerLow = j35;
        this.surfaceContainerLowest = j36;
    }

    public /* synthetic */ ColorScheme(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12, j13, j14, j15, j16, j17, j18, j19, j20, j21, j22, j23, j24, j25, j26, j27, j28, j29, j30, j31, j32, j33, j34, j35, j36);
    }

    /* JADX INFO: renamed from: copy-C-Xl9yA$default, reason: not valid java name */
    public static /* synthetic */ ColorScheme m242copyCXl9yA$default(ColorScheme colorScheme, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36, int i, int i2, Object obj) {
        long j37;
        long j38;
        long j39;
        long j40;
        long j41;
        long j42;
        long j43;
        long j44;
        long j45;
        long j46;
        long j47;
        long j48;
        long j49;
        long j50;
        long j51;
        long j52;
        long j53;
        long j54;
        long j55;
        long j56;
        long j57;
        long j58;
        long j59;
        long j60;
        long j61;
        long j62;
        long j63;
        long j64;
        long j65;
        long j66;
        ColorScheme colorScheme2;
        long j67;
        long j68;
        long j69;
        long j70;
        long j71;
        long j72 = (i & 1) != 0 ? colorScheme.primary : j;
        long j73 = (i & 2) != 0 ? colorScheme.onPrimary : j2;
        long j74 = (i & 4) != 0 ? colorScheme.primaryContainer : j3;
        long j75 = (i & 8) != 0 ? colorScheme.onPrimaryContainer : j4;
        long j76 = (i & 16) != 0 ? colorScheme.inversePrimary : j5;
        long j77 = (i & 32) != 0 ? colorScheme.secondary : j6;
        long j78 = (i & 64) != 0 ? colorScheme.onSecondary : j7;
        long j79 = j72;
        long j80 = (i & 128) != 0 ? colorScheme.secondaryContainer : j8;
        long j81 = (i & 256) != 0 ? colorScheme.onSecondaryContainer : j9;
        long j82 = (i & 512) != 0 ? colorScheme.tertiary : j10;
        long j83 = (i & 1024) != 0 ? colorScheme.onTertiary : j11;
        long j84 = (i & 2048) != 0 ? colorScheme.tertiaryContainer : j12;
        long j85 = (i & 4096) != 0 ? colorScheme.onTertiaryContainer : j13;
        long j86 = (i & 8192) != 0 ? colorScheme.background : j14;
        long j87 = (i & 16384) != 0 ? colorScheme.onBackground : j15;
        long j88 = (i & 32768) != 0 ? colorScheme.surface : j16;
        long j89 = (i & 65536) != 0 ? colorScheme.onSurface : j17;
        long j90 = (i & 131072) != 0 ? colorScheme.surfaceVariant : j18;
        long j91 = (i & 262144) != 0 ? colorScheme.onSurfaceVariant : j19;
        long j92 = (i & 524288) != 0 ? colorScheme.surfaceTint : j20;
        long j93 = (i & 1048576) != 0 ? colorScheme.inverseSurface : j21;
        long j94 = (i & 2097152) != 0 ? colorScheme.inverseOnSurface : j22;
        long j95 = (i & 4194304) != 0 ? colorScheme.error : j23;
        long j96 = (i & 8388608) != 0 ? colorScheme.onError : j24;
        long j97 = (i & 16777216) != 0 ? colorScheme.errorContainer : j25;
        long j98 = (i & 33554432) != 0 ? colorScheme.onErrorContainer : j26;
        long j99 = (i & 67108864) != 0 ? colorScheme.outline : j27;
        long j100 = (i & 134217728) != 0 ? colorScheme.outlineVariant : j28;
        long j101 = (i & 268435456) != 0 ? colorScheme.scrim : j29;
        long j102 = (i & 536870912) != 0 ? colorScheme.surfaceBright : j30;
        long j103 = (i & 1073741824) != 0 ? colorScheme.surfaceDim : j31;
        long j104 = (i & Integer.MIN_VALUE) != 0 ? colorScheme.surfaceContainer : j32;
        long j105 = (i2 & 1) != 0 ? colorScheme.surfaceContainerHigh : j33;
        long j106 = (i2 & 2) != 0 ? colorScheme.surfaceContainerHighest : j34;
        long j107 = (i2 & 4) != 0 ? colorScheme.surfaceContainerLow : j35;
        if ((i2 & 8) != 0) {
            j38 = j107;
            j37 = colorScheme.surfaceContainerLowest;
            j40 = j101;
            j41 = j102;
            j42 = j103;
            j43 = j104;
            j44 = j105;
            j45 = j106;
            j47 = j94;
            j48 = j95;
            j49 = j96;
            j50 = j97;
            j51 = j98;
            j52 = j99;
            j39 = j100;
            j54 = j87;
            j55 = j88;
            j56 = j89;
            j57 = j90;
            j58 = j91;
            j59 = j92;
            j46 = j93;
            j61 = j80;
            j62 = j81;
            j63 = j82;
            j64 = j83;
            j65 = j84;
            j66 = j85;
            j53 = j86;
            colorScheme2 = colorScheme;
            j67 = j73;
            j68 = j74;
            j69 = j75;
            j70 = j76;
            j71 = j77;
            j60 = j78;
        } else {
            j37 = j36;
            j38 = j107;
            j39 = j100;
            j40 = j101;
            j41 = j102;
            j42 = j103;
            j43 = j104;
            j44 = j105;
            j45 = j106;
            j46 = j93;
            j47 = j94;
            j48 = j95;
            j49 = j96;
            j50 = j97;
            j51 = j98;
            j52 = j99;
            j53 = j86;
            j54 = j87;
            j55 = j88;
            j56 = j89;
            j57 = j90;
            j58 = j91;
            j59 = j92;
            j60 = j78;
            j61 = j80;
            j62 = j81;
            j63 = j82;
            j64 = j83;
            j65 = j84;
            j66 = j85;
            colorScheme2 = colorScheme;
            j67 = j73;
            j68 = j74;
            j69 = j75;
            j70 = j76;
            j71 = j77;
        }
        return colorScheme2.m243copyCXl9yA(j79, j67, j68, j69, j70, j71, j60, j61, j62, j63, j64, j65, j66, j53, j54, j55, j56, j57, j58, j59, j46, j47, j48, j49, j50, j51, j52, j39, j40, j41, j42, j43, j44, j45, j38, j37);
    }

    /* JADX INFO: renamed from: copy-C-Xl9yA, reason: not valid java name */
    public final ColorScheme m243copyCXl9yA(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36) {
        return new ColorScheme(j, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12, j13, j14, j15, j16, j17, j18, j19, j20, j21, j22, j23, j24, j25, j26, j27, j28, j29, j30, j31, j32, j33, j34, j35, j36, null);
    }

    /* JADX INFO: renamed from: getBackground-0d7_KjU, reason: not valid java name */
    public final long m244getBackground0d7_KjU() {
        return this.background;
    }

    public final IconButtonColors getDefaultIconButtonColorsCached$material3_release() {
        return this.defaultIconButtonColorsCached;
    }

    public final SliderColors getDefaultSliderColorsCached$material3_release() {
        return this.defaultSliderColorsCached;
    }

    /* JADX INFO: renamed from: getError-0d7_KjU, reason: not valid java name */
    public final long m245getError0d7_KjU() {
        return this.error;
    }

    /* JADX INFO: renamed from: getErrorContainer-0d7_KjU, reason: not valid java name */
    public final long m246getErrorContainer0d7_KjU() {
        return this.errorContainer;
    }

    /* JADX INFO: renamed from: getInverseOnSurface-0d7_KjU, reason: not valid java name */
    public final long m247getInverseOnSurface0d7_KjU() {
        return this.inverseOnSurface;
    }

    /* JADX INFO: renamed from: getInversePrimary-0d7_KjU, reason: not valid java name */
    public final long m248getInversePrimary0d7_KjU() {
        return this.inversePrimary;
    }

    /* JADX INFO: renamed from: getInverseSurface-0d7_KjU, reason: not valid java name */
    public final long m249getInverseSurface0d7_KjU() {
        return this.inverseSurface;
    }

    /* JADX INFO: renamed from: getOnBackground-0d7_KjU, reason: not valid java name */
    public final long m250getOnBackground0d7_KjU() {
        return this.onBackground;
    }

    /* JADX INFO: renamed from: getOnError-0d7_KjU, reason: not valid java name */
    public final long m251getOnError0d7_KjU() {
        return this.onError;
    }

    /* JADX INFO: renamed from: getOnErrorContainer-0d7_KjU, reason: not valid java name */
    public final long m252getOnErrorContainer0d7_KjU() {
        return this.onErrorContainer;
    }

    /* JADX INFO: renamed from: getOnPrimary-0d7_KjU, reason: not valid java name */
    public final long m253getOnPrimary0d7_KjU() {
        return this.onPrimary;
    }

    /* JADX INFO: renamed from: getOnPrimaryContainer-0d7_KjU, reason: not valid java name */
    public final long m254getOnPrimaryContainer0d7_KjU() {
        return this.onPrimaryContainer;
    }

    /* JADX INFO: renamed from: getOnSecondary-0d7_KjU, reason: not valid java name */
    public final long m255getOnSecondary0d7_KjU() {
        return this.onSecondary;
    }

    /* JADX INFO: renamed from: getOnSecondaryContainer-0d7_KjU, reason: not valid java name */
    public final long m256getOnSecondaryContainer0d7_KjU() {
        return this.onSecondaryContainer;
    }

    /* JADX INFO: renamed from: getOnSurface-0d7_KjU, reason: not valid java name */
    public final long m257getOnSurface0d7_KjU() {
        return this.onSurface;
    }

    /* JADX INFO: renamed from: getOnSurfaceVariant-0d7_KjU, reason: not valid java name */
    public final long m258getOnSurfaceVariant0d7_KjU() {
        return this.onSurfaceVariant;
    }

    /* JADX INFO: renamed from: getOnTertiary-0d7_KjU, reason: not valid java name */
    public final long m259getOnTertiary0d7_KjU() {
        return this.onTertiary;
    }

    /* JADX INFO: renamed from: getOnTertiaryContainer-0d7_KjU, reason: not valid java name */
    public final long m260getOnTertiaryContainer0d7_KjU() {
        return this.onTertiaryContainer;
    }

    /* JADX INFO: renamed from: getOutline-0d7_KjU, reason: not valid java name */
    public final long m261getOutline0d7_KjU() {
        return this.outline;
    }

    /* JADX INFO: renamed from: getOutlineVariant-0d7_KjU, reason: not valid java name */
    public final long m262getOutlineVariant0d7_KjU() {
        return this.outlineVariant;
    }

    /* JADX INFO: renamed from: getPrimary-0d7_KjU, reason: not valid java name */
    public final long m263getPrimary0d7_KjU() {
        return this.primary;
    }

    /* JADX INFO: renamed from: getPrimaryContainer-0d7_KjU, reason: not valid java name */
    public final long m264getPrimaryContainer0d7_KjU() {
        return this.primaryContainer;
    }

    /* JADX INFO: renamed from: getScrim-0d7_KjU, reason: not valid java name */
    public final long m265getScrim0d7_KjU() {
        return this.scrim;
    }

    /* JADX INFO: renamed from: getSecondary-0d7_KjU, reason: not valid java name */
    public final long m266getSecondary0d7_KjU() {
        return this.secondary;
    }

    /* JADX INFO: renamed from: getSecondaryContainer-0d7_KjU, reason: not valid java name */
    public final long m267getSecondaryContainer0d7_KjU() {
        return this.secondaryContainer;
    }

    /* JADX INFO: renamed from: getSurface-0d7_KjU, reason: not valid java name */
    public final long m268getSurface0d7_KjU() {
        return this.surface;
    }

    /* JADX INFO: renamed from: getSurfaceBright-0d7_KjU, reason: not valid java name */
    public final long m269getSurfaceBright0d7_KjU() {
        return this.surfaceBright;
    }

    /* JADX INFO: renamed from: getSurfaceContainer-0d7_KjU, reason: not valid java name */
    public final long m270getSurfaceContainer0d7_KjU() {
        return this.surfaceContainer;
    }

    /* JADX INFO: renamed from: getSurfaceContainerHigh-0d7_KjU, reason: not valid java name */
    public final long m271getSurfaceContainerHigh0d7_KjU() {
        return this.surfaceContainerHigh;
    }

    /* JADX INFO: renamed from: getSurfaceContainerHighest-0d7_KjU, reason: not valid java name */
    public final long m272getSurfaceContainerHighest0d7_KjU() {
        return this.surfaceContainerHighest;
    }

    /* JADX INFO: renamed from: getSurfaceContainerLow-0d7_KjU, reason: not valid java name */
    public final long m273getSurfaceContainerLow0d7_KjU() {
        return this.surfaceContainerLow;
    }

    /* JADX INFO: renamed from: getSurfaceContainerLowest-0d7_KjU, reason: not valid java name */
    public final long m274getSurfaceContainerLowest0d7_KjU() {
        return this.surfaceContainerLowest;
    }

    /* JADX INFO: renamed from: getSurfaceDim-0d7_KjU, reason: not valid java name */
    public final long m275getSurfaceDim0d7_KjU() {
        return this.surfaceDim;
    }

    /* JADX INFO: renamed from: getSurfaceTint-0d7_KjU, reason: not valid java name */
    public final long m276getSurfaceTint0d7_KjU() {
        return this.surfaceTint;
    }

    /* JADX INFO: renamed from: getSurfaceVariant-0d7_KjU, reason: not valid java name */
    public final long m277getSurfaceVariant0d7_KjU() {
        return this.surfaceVariant;
    }

    /* JADX INFO: renamed from: getTertiary-0d7_KjU, reason: not valid java name */
    public final long m278getTertiary0d7_KjU() {
        return this.tertiary;
    }

    /* JADX INFO: renamed from: getTertiaryContainer-0d7_KjU, reason: not valid java name */
    public final long m279getTertiaryContainer0d7_KjU() {
        return this.tertiaryContainer;
    }

    public final void setDefaultIconButtonColorsCached$material3_release(IconButtonColors iconButtonColors) {
        this.defaultIconButtonColorsCached = iconButtonColors;
    }

    public final void setDefaultSliderColorsCached$material3_release(SliderColors sliderColors) {
        this.defaultSliderColorsCached = sliderColors;
    }

    public String toString() {
        return "ColorScheme(primary=" + ((Object) Color.m889toStringimpl(this.primary)) + "onPrimary=" + ((Object) Color.m889toStringimpl(this.onPrimary)) + "primaryContainer=" + ((Object) Color.m889toStringimpl(this.primaryContainer)) + "onPrimaryContainer=" + ((Object) Color.m889toStringimpl(this.onPrimaryContainer)) + "inversePrimary=" + ((Object) Color.m889toStringimpl(this.inversePrimary)) + "secondary=" + ((Object) Color.m889toStringimpl(this.secondary)) + "onSecondary=" + ((Object) Color.m889toStringimpl(this.onSecondary)) + "secondaryContainer=" + ((Object) Color.m889toStringimpl(this.secondaryContainer)) + "onSecondaryContainer=" + ((Object) Color.m889toStringimpl(this.onSecondaryContainer)) + "tertiary=" + ((Object) Color.m889toStringimpl(this.tertiary)) + "onTertiary=" + ((Object) Color.m889toStringimpl(this.onTertiary)) + "tertiaryContainer=" + ((Object) Color.m889toStringimpl(this.tertiaryContainer)) + "onTertiaryContainer=" + ((Object) Color.m889toStringimpl(this.onTertiaryContainer)) + "background=" + ((Object) Color.m889toStringimpl(this.background)) + "onBackground=" + ((Object) Color.m889toStringimpl(this.onBackground)) + "surface=" + ((Object) Color.m889toStringimpl(this.surface)) + "onSurface=" + ((Object) Color.m889toStringimpl(this.onSurface)) + "surfaceVariant=" + ((Object) Color.m889toStringimpl(this.surfaceVariant)) + "onSurfaceVariant=" + ((Object) Color.m889toStringimpl(this.onSurfaceVariant)) + "surfaceTint=" + ((Object) Color.m889toStringimpl(this.surfaceTint)) + "inverseSurface=" + ((Object) Color.m889toStringimpl(this.inverseSurface)) + "inverseOnSurface=" + ((Object) Color.m889toStringimpl(this.inverseOnSurface)) + "error=" + ((Object) Color.m889toStringimpl(this.error)) + "onError=" + ((Object) Color.m889toStringimpl(this.onError)) + "errorContainer=" + ((Object) Color.m889toStringimpl(this.errorContainer)) + "onErrorContainer=" + ((Object) Color.m889toStringimpl(this.onErrorContainer)) + "outline=" + ((Object) Color.m889toStringimpl(this.outline)) + "outlineVariant=" + ((Object) Color.m889toStringimpl(this.outlineVariant)) + "scrim=" + ((Object) Color.m889toStringimpl(this.scrim)) + "surfaceBright=" + ((Object) Color.m889toStringimpl(this.surfaceBright)) + "surfaceDim=" + ((Object) Color.m889toStringimpl(this.surfaceDim)) + "surfaceContainer=" + ((Object) Color.m889toStringimpl(this.surfaceContainer)) + "surfaceContainerHigh=" + ((Object) Color.m889toStringimpl(this.surfaceContainerHigh)) + "surfaceContainerHighest=" + ((Object) Color.m889toStringimpl(this.surfaceContainerHighest)) + "surfaceContainerLow=" + ((Object) Color.m889toStringimpl(this.surfaceContainerLow)) + "surfaceContainerLowest=" + ((Object) Color.m889toStringimpl(this.surfaceContainerLowest)) + ')';
    }
}
