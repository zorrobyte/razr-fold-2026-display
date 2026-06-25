package androidx.compose.material3;

import androidx.compose.material3.tokens.ColorDarkTokens;
import androidx.compose.material3.tokens.ColorLightTokens;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: ColorScheme.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ColorSchemeKt {
    private static final ProvidableCompositionLocal LocalColorScheme = CompositionLocalKt.staticCompositionLocalOf(new Function0() { // from class: androidx.compose.material3.ColorSchemeKt$LocalColorScheme$1
        @Override // kotlin.jvm.functions.Function0
        public final ColorScheme invoke() {
            return ColorSchemeKt.m286lightColorSchemeCXl9yA$default(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, -1, 15, null);
        }
    });
    private static final ProvidableCompositionLocal LocalTonalElevationEnabled = CompositionLocalKt.staticCompositionLocalOf(new Function0() { // from class: androidx.compose.material3.ColorSchemeKt$LocalTonalElevationEnabled$1
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return Boolean.TRUE;
        }
    });

    /* JADX INFO: compiled from: ColorScheme.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ColorSchemeKeyTokens.values().length];
            try {
                iArr[ColorSchemeKeyTokens.Background.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Error.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ColorSchemeKeyTokens.ErrorContainer.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ColorSchemeKeyTokens.InverseOnSurface.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ColorSchemeKeyTokens.InversePrimary.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ColorSchemeKeyTokens.InverseSurface.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnBackground.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnError.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnErrorContainer.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnPrimary.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnPrimaryContainer.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnSecondary.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnSecondaryContainer.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnSurface.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnSurfaceVariant.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceTint.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnTertiary.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnTertiaryContainer.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Outline.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OutlineVariant.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Primary.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[ColorSchemeKeyTokens.PrimaryContainer.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Scrim.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Secondary.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SecondaryContainer.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Surface.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceVariant.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceBright.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceContainer.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceContainerHigh.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceContainerHighest.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceContainerLow.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceContainerLowest.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceDim.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Tertiary.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr[ColorSchemeKeyTokens.TertiaryContainer.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: applyTonalElevation-RFCenO8, reason: not valid java name */
    public static final long m280applyTonalElevationRFCenO8(ColorScheme colorScheme, long j, float f, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-1610977682, i, -1, "androidx.compose.material3.applyTonalElevation (ColorScheme.kt:907)");
        }
        boolean zBooleanValue = ((Boolean) composer.consume(LocalTonalElevationEnabled)).booleanValue();
        if (Color.m882equalsimpl0(j, colorScheme.m268getSurface0d7_KjU()) && zBooleanValue) {
            j = m287surfaceColorAtElevation3ABfNKs(colorScheme, f);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return j;
    }

    /* JADX INFO: renamed from: contentColorFor-4WTKRHQ, reason: not valid java name */
    public static final long m281contentColorFor4WTKRHQ(ColorScheme colorScheme, long j) {
        if (Color.m882equalsimpl0(j, colorScheme.m263getPrimary0d7_KjU())) {
            return colorScheme.m253getOnPrimary0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m266getSecondary0d7_KjU())) {
            return colorScheme.m255getOnSecondary0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m278getTertiary0d7_KjU())) {
            return colorScheme.m259getOnTertiary0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m244getBackground0d7_KjU())) {
            return colorScheme.m250getOnBackground0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m245getError0d7_KjU())) {
            return colorScheme.m251getOnError0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m264getPrimaryContainer0d7_KjU())) {
            return colorScheme.m254getOnPrimaryContainer0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m267getSecondaryContainer0d7_KjU())) {
            return colorScheme.m256getOnSecondaryContainer0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m279getTertiaryContainer0d7_KjU())) {
            return colorScheme.m260getOnTertiaryContainer0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m246getErrorContainer0d7_KjU())) {
            return colorScheme.m252getOnErrorContainer0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m249getInverseSurface0d7_KjU())) {
            return colorScheme.m247getInverseOnSurface0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m268getSurface0d7_KjU())) {
            return colorScheme.m257getOnSurface0d7_KjU();
        }
        if (Color.m882equalsimpl0(j, colorScheme.m277getSurfaceVariant0d7_KjU())) {
            return colorScheme.m258getOnSurfaceVariant0d7_KjU();
        }
        if (!Color.m882equalsimpl0(j, colorScheme.m269getSurfaceBright0d7_KjU()) && !Color.m882equalsimpl0(j, colorScheme.m270getSurfaceContainer0d7_KjU()) && !Color.m882equalsimpl0(j, colorScheme.m271getSurfaceContainerHigh0d7_KjU()) && !Color.m882equalsimpl0(j, colorScheme.m272getSurfaceContainerHighest0d7_KjU()) && !Color.m882equalsimpl0(j, colorScheme.m273getSurfaceContainerLow0d7_KjU()) && !Color.m882equalsimpl0(j, colorScheme.m274getSurfaceContainerLowest0d7_KjU())) {
            return Color.Companion.m895getUnspecified0d7_KjU();
        }
        return colorScheme.m257getOnSurface0d7_KjU();
    }

    /* JADX INFO: renamed from: contentColorFor-ek8zF_U, reason: not valid java name */
    public static final long m282contentColorForek8zF_U(long j, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(509589638, i, -1, "androidx.compose.material3.contentColorFor (ColorScheme.kt:890)");
        }
        composer.startReplaceGroup(-1680907984);
        long jM281contentColorFor4WTKRHQ = m281contentColorFor4WTKRHQ(MaterialTheme.INSTANCE.getColorScheme(composer, 6), j);
        if (jM281contentColorFor4WTKRHQ == 16) {
            jM281contentColorFor4WTKRHQ = ((Color) composer.consume(ContentColorKt.getLocalContentColor())).m890unboximpl();
        }
        composer.endReplaceGroup();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return jM281contentColorFor4WTKRHQ;
    }

    /* JADX INFO: renamed from: darkColorScheme-C-Xl9yA, reason: not valid java name */
    public static final ColorScheme m283darkColorSchemeCXl9yA(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36) {
        return new ColorScheme(j, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12, j13, j14, j15, j16, j17, j18, j19, j20, j21, j22, j23, j24, j25, j26, j27, j28, j29, j30, j36, j31, j32, j33, j34, j35, null);
    }

    /* JADX INFO: renamed from: darkColorScheme-C-Xl9yA$default, reason: not valid java name */
    public static /* synthetic */ ColorScheme m284darkColorSchemeCXl9yA$default(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36, int i, int i2, Object obj) {
        long jM369getPrimary0d7_KjU = (i & 1) != 0 ? ColorDarkTokens.INSTANCE.m369getPrimary0d7_KjU() : j;
        long jM359getOnPrimary0d7_KjU = (i & 2) != 0 ? ColorDarkTokens.INSTANCE.m359getOnPrimary0d7_KjU() : j2;
        long jM370getPrimaryContainer0d7_KjU = (i & 4) != 0 ? ColorDarkTokens.INSTANCE.m370getPrimaryContainer0d7_KjU() : j3;
        long jM360getOnPrimaryContainer0d7_KjU = (i & 8) != 0 ? ColorDarkTokens.INSTANCE.m360getOnPrimaryContainer0d7_KjU() : j4;
        long jM354getInversePrimary0d7_KjU = (i & 16) != 0 ? ColorDarkTokens.INSTANCE.m354getInversePrimary0d7_KjU() : j5;
        long jM372getSecondary0d7_KjU = (i & 32) != 0 ? ColorDarkTokens.INSTANCE.m372getSecondary0d7_KjU() : j6;
        long jM361getOnSecondary0d7_KjU = (i & 64) != 0 ? ColorDarkTokens.INSTANCE.m361getOnSecondary0d7_KjU() : j7;
        long jM373getSecondaryContainer0d7_KjU = (i & 128) != 0 ? ColorDarkTokens.INSTANCE.m373getSecondaryContainer0d7_KjU() : j8;
        long j37 = jM369getPrimary0d7_KjU;
        long jM362getOnSecondaryContainer0d7_KjU = (i & 256) != 0 ? ColorDarkTokens.INSTANCE.m362getOnSecondaryContainer0d7_KjU() : j9;
        long jM383getTertiary0d7_KjU = (i & 512) != 0 ? ColorDarkTokens.INSTANCE.m383getTertiary0d7_KjU() : j10;
        long jM365getOnTertiary0d7_KjU = (i & 1024) != 0 ? ColorDarkTokens.INSTANCE.m365getOnTertiary0d7_KjU() : j11;
        long jM384getTertiaryContainer0d7_KjU = (i & 2048) != 0 ? ColorDarkTokens.INSTANCE.m384getTertiaryContainer0d7_KjU() : j12;
        long jM366getOnTertiaryContainer0d7_KjU = (i & 4096) != 0 ? ColorDarkTokens.INSTANCE.m366getOnTertiaryContainer0d7_KjU() : j13;
        long jM350getBackground0d7_KjU = (i & 8192) != 0 ? ColorDarkTokens.INSTANCE.m350getBackground0d7_KjU() : j14;
        long jM356getOnBackground0d7_KjU = (i & 16384) != 0 ? ColorDarkTokens.INSTANCE.m356getOnBackground0d7_KjU() : j15;
        long jM374getSurface0d7_KjU = (i & 32768) != 0 ? ColorDarkTokens.INSTANCE.m374getSurface0d7_KjU() : j16;
        long jM363getOnSurface0d7_KjU = (i & 65536) != 0 ? ColorDarkTokens.INSTANCE.m363getOnSurface0d7_KjU() : j17;
        long jM382getSurfaceVariant0d7_KjU = (i & 131072) != 0 ? ColorDarkTokens.INSTANCE.m382getSurfaceVariant0d7_KjU() : j18;
        long jM364getOnSurfaceVariant0d7_KjU = (i & 262144) != 0 ? ColorDarkTokens.INSTANCE.m364getOnSurfaceVariant0d7_KjU() : j19;
        long j38 = (i & 524288) != 0 ? j37 : j20;
        long jM355getInverseSurface0d7_KjU = (i & 1048576) != 0 ? ColorDarkTokens.INSTANCE.m355getInverseSurface0d7_KjU() : j21;
        long jM353getInverseOnSurface0d7_KjU = (i & 2097152) != 0 ? ColorDarkTokens.INSTANCE.m353getInverseOnSurface0d7_KjU() : j22;
        long jM351getError0d7_KjU = (i & 4194304) != 0 ? ColorDarkTokens.INSTANCE.m351getError0d7_KjU() : j23;
        long jM357getOnError0d7_KjU = (i & 8388608) != 0 ? ColorDarkTokens.INSTANCE.m357getOnError0d7_KjU() : j24;
        long jM352getErrorContainer0d7_KjU = (i & 16777216) != 0 ? ColorDarkTokens.INSTANCE.m352getErrorContainer0d7_KjU() : j25;
        long jM358getOnErrorContainer0d7_KjU = (i & 33554432) != 0 ? ColorDarkTokens.INSTANCE.m358getOnErrorContainer0d7_KjU() : j26;
        long jM367getOutline0d7_KjU = (i & 67108864) != 0 ? ColorDarkTokens.INSTANCE.m367getOutline0d7_KjU() : j27;
        long jM368getOutlineVariant0d7_KjU = (i & 134217728) != 0 ? ColorDarkTokens.INSTANCE.m368getOutlineVariant0d7_KjU() : j28;
        long jM371getScrim0d7_KjU = (i & 268435456) != 0 ? ColorDarkTokens.INSTANCE.m371getScrim0d7_KjU() : j29;
        long jM375getSurfaceBright0d7_KjU = (i & 536870912) != 0 ? ColorDarkTokens.INSTANCE.m375getSurfaceBright0d7_KjU() : j30;
        long jM376getSurfaceContainer0d7_KjU = (i & 1073741824) != 0 ? ColorDarkTokens.INSTANCE.m376getSurfaceContainer0d7_KjU() : j31;
        long jM377getSurfaceContainerHigh0d7_KjU = (i & Integer.MIN_VALUE) != 0 ? ColorDarkTokens.INSTANCE.m377getSurfaceContainerHigh0d7_KjU() : j32;
        long jM378getSurfaceContainerHighest0d7_KjU = (i2 & 1) != 0 ? ColorDarkTokens.INSTANCE.m378getSurfaceContainerHighest0d7_KjU() : j33;
        long jM379getSurfaceContainerLow0d7_KjU = (i2 & 2) != 0 ? ColorDarkTokens.INSTANCE.m379getSurfaceContainerLow0d7_KjU() : j34;
        long jM380getSurfaceContainerLowest0d7_KjU = (i2 & 4) != 0 ? ColorDarkTokens.INSTANCE.m380getSurfaceContainerLowest0d7_KjU() : j35;
        if ((i2 & 8) != 0) {
            j36 = ColorDarkTokens.INSTANCE.m381getSurfaceDim0d7_KjU();
        }
        return m283darkColorSchemeCXl9yA(j37, jM359getOnPrimary0d7_KjU, jM370getPrimaryContainer0d7_KjU, jM360getOnPrimaryContainer0d7_KjU, jM354getInversePrimary0d7_KjU, jM372getSecondary0d7_KjU, jM361getOnSecondary0d7_KjU, jM373getSecondaryContainer0d7_KjU, jM362getOnSecondaryContainer0d7_KjU, jM383getTertiary0d7_KjU, jM365getOnTertiary0d7_KjU, jM384getTertiaryContainer0d7_KjU, jM366getOnTertiaryContainer0d7_KjU, jM350getBackground0d7_KjU, jM356getOnBackground0d7_KjU, jM374getSurface0d7_KjU, jM363getOnSurface0d7_KjU, jM382getSurfaceVariant0d7_KjU, jM364getOnSurfaceVariant0d7_KjU, j38, jM355getInverseSurface0d7_KjU, jM353getInverseOnSurface0d7_KjU, jM351getError0d7_KjU, jM357getOnError0d7_KjU, jM352getErrorContainer0d7_KjU, jM358getOnErrorContainer0d7_KjU, jM367getOutline0d7_KjU, jM368getOutlineVariant0d7_KjU, jM371getScrim0d7_KjU, jM375getSurfaceBright0d7_KjU, jM376getSurfaceContainer0d7_KjU, jM377getSurfaceContainerHigh0d7_KjU, jM378getSurfaceContainerHighest0d7_KjU, jM379getSurfaceContainerLow0d7_KjU, jM380getSurfaceContainerLowest0d7_KjU, j36);
    }

    public static final long fromToken(ColorScheme colorScheme, ColorSchemeKeyTokens colorSchemeKeyTokens) {
        switch (WhenMappings.$EnumSwitchMapping$0[colorSchemeKeyTokens.ordinal()]) {
            case 1:
                return colorScheme.m244getBackground0d7_KjU();
            case 2:
                return colorScheme.m245getError0d7_KjU();
            case 3:
                return colorScheme.m246getErrorContainer0d7_KjU();
            case 4:
                return colorScheme.m247getInverseOnSurface0d7_KjU();
            case 5:
                return colorScheme.m248getInversePrimary0d7_KjU();
            case 6:
                return colorScheme.m249getInverseSurface0d7_KjU();
            case 7:
                return colorScheme.m250getOnBackground0d7_KjU();
            case 8:
                return colorScheme.m251getOnError0d7_KjU();
            case 9:
                return colorScheme.m252getOnErrorContainer0d7_KjU();
            case 10:
                return colorScheme.m253getOnPrimary0d7_KjU();
            case 11:
                return colorScheme.m254getOnPrimaryContainer0d7_KjU();
            case 12:
                return colorScheme.m255getOnSecondary0d7_KjU();
            case 13:
                return colorScheme.m256getOnSecondaryContainer0d7_KjU();
            case 14:
                return colorScheme.m257getOnSurface0d7_KjU();
            case 15:
                return colorScheme.m258getOnSurfaceVariant0d7_KjU();
            case 16:
                return colorScheme.m276getSurfaceTint0d7_KjU();
            case 17:
                return colorScheme.m259getOnTertiary0d7_KjU();
            case 18:
                return colorScheme.m260getOnTertiaryContainer0d7_KjU();
            case 19:
                return colorScheme.m261getOutline0d7_KjU();
            case 20:
                return colorScheme.m262getOutlineVariant0d7_KjU();
            case 21:
                return colorScheme.m263getPrimary0d7_KjU();
            case 22:
                return colorScheme.m264getPrimaryContainer0d7_KjU();
            case 23:
                return colorScheme.m265getScrim0d7_KjU();
            case 24:
                return colorScheme.m266getSecondary0d7_KjU();
            case 25:
                return colorScheme.m267getSecondaryContainer0d7_KjU();
            case 26:
                return colorScheme.m268getSurface0d7_KjU();
            case 27:
                return colorScheme.m277getSurfaceVariant0d7_KjU();
            case 28:
                return colorScheme.m269getSurfaceBright0d7_KjU();
            case 29:
                return colorScheme.m270getSurfaceContainer0d7_KjU();
            case 30:
                return colorScheme.m271getSurfaceContainerHigh0d7_KjU();
            case 31:
                return colorScheme.m272getSurfaceContainerHighest0d7_KjU();
            case 32:
                return colorScheme.m273getSurfaceContainerLow0d7_KjU();
            case 33:
                return colorScheme.m274getSurfaceContainerLowest0d7_KjU();
            case 34:
                return colorScheme.m275getSurfaceDim0d7_KjU();
            case 35:
                return colorScheme.m278getTertiary0d7_KjU();
            case 36:
                return colorScheme.m279getTertiaryContainer0d7_KjU();
            default:
                return Color.Companion.m895getUnspecified0d7_KjU();
        }
    }

    public static final ProvidableCompositionLocal getLocalColorScheme() {
        return LocalColorScheme;
    }

    /* JADX INFO: renamed from: lightColorScheme-C-Xl9yA, reason: not valid java name */
    public static final ColorScheme m285lightColorSchemeCXl9yA(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36) {
        return new ColorScheme(j, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12, j13, j14, j15, j16, j17, j18, j19, j20, j21, j22, j23, j24, j25, j26, j27, j28, j29, j30, j36, j31, j32, j33, j34, j35, null);
    }

    /* JADX INFO: renamed from: lightColorScheme-C-Xl9yA$default, reason: not valid java name */
    public static /* synthetic */ ColorScheme m286lightColorSchemeCXl9yA$default(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36, int i, int i2, Object obj) {
        long jM404getPrimary0d7_KjU = (i & 1) != 0 ? ColorLightTokens.INSTANCE.m404getPrimary0d7_KjU() : j;
        long jM394getOnPrimary0d7_KjU = (i & 2) != 0 ? ColorLightTokens.INSTANCE.m394getOnPrimary0d7_KjU() : j2;
        long jM405getPrimaryContainer0d7_KjU = (i & 4) != 0 ? ColorLightTokens.INSTANCE.m405getPrimaryContainer0d7_KjU() : j3;
        long jM395getOnPrimaryContainer0d7_KjU = (i & 8) != 0 ? ColorLightTokens.INSTANCE.m395getOnPrimaryContainer0d7_KjU() : j4;
        long jM389getInversePrimary0d7_KjU = (i & 16) != 0 ? ColorLightTokens.INSTANCE.m389getInversePrimary0d7_KjU() : j5;
        long jM407getSecondary0d7_KjU = (i & 32) != 0 ? ColorLightTokens.INSTANCE.m407getSecondary0d7_KjU() : j6;
        long jM396getOnSecondary0d7_KjU = (i & 64) != 0 ? ColorLightTokens.INSTANCE.m396getOnSecondary0d7_KjU() : j7;
        long jM408getSecondaryContainer0d7_KjU = (i & 128) != 0 ? ColorLightTokens.INSTANCE.m408getSecondaryContainer0d7_KjU() : j8;
        long j37 = jM404getPrimary0d7_KjU;
        long jM397getOnSecondaryContainer0d7_KjU = (i & 256) != 0 ? ColorLightTokens.INSTANCE.m397getOnSecondaryContainer0d7_KjU() : j9;
        long jM418getTertiary0d7_KjU = (i & 512) != 0 ? ColorLightTokens.INSTANCE.m418getTertiary0d7_KjU() : j10;
        long jM400getOnTertiary0d7_KjU = (i & 1024) != 0 ? ColorLightTokens.INSTANCE.m400getOnTertiary0d7_KjU() : j11;
        long jM419getTertiaryContainer0d7_KjU = (i & 2048) != 0 ? ColorLightTokens.INSTANCE.m419getTertiaryContainer0d7_KjU() : j12;
        long jM401getOnTertiaryContainer0d7_KjU = (i & 4096) != 0 ? ColorLightTokens.INSTANCE.m401getOnTertiaryContainer0d7_KjU() : j13;
        long jM385getBackground0d7_KjU = (i & 8192) != 0 ? ColorLightTokens.INSTANCE.m385getBackground0d7_KjU() : j14;
        long jM391getOnBackground0d7_KjU = (i & 16384) != 0 ? ColorLightTokens.INSTANCE.m391getOnBackground0d7_KjU() : j15;
        long jM409getSurface0d7_KjU = (i & 32768) != 0 ? ColorLightTokens.INSTANCE.m409getSurface0d7_KjU() : j16;
        long jM398getOnSurface0d7_KjU = (i & 65536) != 0 ? ColorLightTokens.INSTANCE.m398getOnSurface0d7_KjU() : j17;
        long jM417getSurfaceVariant0d7_KjU = (i & 131072) != 0 ? ColorLightTokens.INSTANCE.m417getSurfaceVariant0d7_KjU() : j18;
        long jM399getOnSurfaceVariant0d7_KjU = (i & 262144) != 0 ? ColorLightTokens.INSTANCE.m399getOnSurfaceVariant0d7_KjU() : j19;
        long j38 = (i & 524288) != 0 ? j37 : j20;
        long jM390getInverseSurface0d7_KjU = (i & 1048576) != 0 ? ColorLightTokens.INSTANCE.m390getInverseSurface0d7_KjU() : j21;
        long jM388getInverseOnSurface0d7_KjU = (i & 2097152) != 0 ? ColorLightTokens.INSTANCE.m388getInverseOnSurface0d7_KjU() : j22;
        long jM386getError0d7_KjU = (i & 4194304) != 0 ? ColorLightTokens.INSTANCE.m386getError0d7_KjU() : j23;
        long jM392getOnError0d7_KjU = (i & 8388608) != 0 ? ColorLightTokens.INSTANCE.m392getOnError0d7_KjU() : j24;
        long jM387getErrorContainer0d7_KjU = (i & 16777216) != 0 ? ColorLightTokens.INSTANCE.m387getErrorContainer0d7_KjU() : j25;
        long jM393getOnErrorContainer0d7_KjU = (i & 33554432) != 0 ? ColorLightTokens.INSTANCE.m393getOnErrorContainer0d7_KjU() : j26;
        long jM402getOutline0d7_KjU = (i & 67108864) != 0 ? ColorLightTokens.INSTANCE.m402getOutline0d7_KjU() : j27;
        long jM403getOutlineVariant0d7_KjU = (i & 134217728) != 0 ? ColorLightTokens.INSTANCE.m403getOutlineVariant0d7_KjU() : j28;
        long jM406getScrim0d7_KjU = (i & 268435456) != 0 ? ColorLightTokens.INSTANCE.m406getScrim0d7_KjU() : j29;
        long jM410getSurfaceBright0d7_KjU = (i & 536870912) != 0 ? ColorLightTokens.INSTANCE.m410getSurfaceBright0d7_KjU() : j30;
        long jM411getSurfaceContainer0d7_KjU = (i & 1073741824) != 0 ? ColorLightTokens.INSTANCE.m411getSurfaceContainer0d7_KjU() : j31;
        long jM412getSurfaceContainerHigh0d7_KjU = (i & Integer.MIN_VALUE) != 0 ? ColorLightTokens.INSTANCE.m412getSurfaceContainerHigh0d7_KjU() : j32;
        long jM413getSurfaceContainerHighest0d7_KjU = (i2 & 1) != 0 ? ColorLightTokens.INSTANCE.m413getSurfaceContainerHighest0d7_KjU() : j33;
        long jM414getSurfaceContainerLow0d7_KjU = (i2 & 2) != 0 ? ColorLightTokens.INSTANCE.m414getSurfaceContainerLow0d7_KjU() : j34;
        long jM415getSurfaceContainerLowest0d7_KjU = (i2 & 4) != 0 ? ColorLightTokens.INSTANCE.m415getSurfaceContainerLowest0d7_KjU() : j35;
        if ((i2 & 8) != 0) {
            j36 = ColorLightTokens.INSTANCE.m416getSurfaceDim0d7_KjU();
        }
        return m285lightColorSchemeCXl9yA(j37, jM394getOnPrimary0d7_KjU, jM405getPrimaryContainer0d7_KjU, jM395getOnPrimaryContainer0d7_KjU, jM389getInversePrimary0d7_KjU, jM407getSecondary0d7_KjU, jM396getOnSecondary0d7_KjU, jM408getSecondaryContainer0d7_KjU, jM397getOnSecondaryContainer0d7_KjU, jM418getTertiary0d7_KjU, jM400getOnTertiary0d7_KjU, jM419getTertiaryContainer0d7_KjU, jM401getOnTertiaryContainer0d7_KjU, jM385getBackground0d7_KjU, jM391getOnBackground0d7_KjU, jM409getSurface0d7_KjU, jM398getOnSurface0d7_KjU, jM417getSurfaceVariant0d7_KjU, jM399getOnSurfaceVariant0d7_KjU, j38, jM390getInverseSurface0d7_KjU, jM388getInverseOnSurface0d7_KjU, jM386getError0d7_KjU, jM392getOnError0d7_KjU, jM387getErrorContainer0d7_KjU, jM393getOnErrorContainer0d7_KjU, jM402getOutline0d7_KjU, jM403getOutlineVariant0d7_KjU, jM406getScrim0d7_KjU, jM410getSurfaceBright0d7_KjU, jM411getSurfaceContainer0d7_KjU, jM412getSurfaceContainerHigh0d7_KjU, jM413getSurfaceContainerHighest0d7_KjU, jM414getSurfaceContainerLow0d7_KjU, jM415getSurfaceContainerLowest0d7_KjU, j36);
    }

    /* JADX INFO: renamed from: surfaceColorAtElevation-3ABfNKs, reason: not valid java name */
    public static final long m287surfaceColorAtElevation3ABfNKs(ColorScheme colorScheme, float f) {
        if (Dp.m1879equalsimpl0(f, Dp.m1877constructorimpl(0))) {
            return colorScheme.m268getSurface0d7_KjU();
        }
        return ColorKt.m899compositeOverOWjLjI(Color.m880copywmQWz5c$default(colorScheme.m276getSurfaceTint0d7_KjU(), ((((float) Math.log(f + 1)) * 4.5f) + 2.0f) / 100.0f, 0.0f, 0.0f, 0.0f, 14, null), colorScheme.m268getSurface0d7_KjU());
    }
}
