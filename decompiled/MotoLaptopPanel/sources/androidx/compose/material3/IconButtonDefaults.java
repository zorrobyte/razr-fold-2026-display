package androidx.compose.material3;

import androidx.compose.material3.tokens.LargeIconButtonTokens;
import androidx.compose.material3.tokens.MediumIconButtonTokens;
import androidx.compose.material3.tokens.SmallIconButtonTokens;
import androidx.compose.material3.tokens.StandardIconButtonTokens;
import androidx.compose.material3.tokens.XLargeIconButtonTokens;
import androidx.compose.material3.tokens.XSmallIconButtonTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: IconButtonDefaults.kt */
/* JADX INFO: loaded from: classes.dex */
public final class IconButtonDefaults {
    public static final int $stable = 0;
    public static final IconButtonDefaults INSTANCE = new IconButtonDefaults();
    private static final float xSmallIconSize = XSmallIconButtonTokens.INSTANCE.m575getIconSizeD9Ej5fM();
    private static final float smallIconSize = SmallIconButtonTokens.INSTANCE.m479getIconSizeD9Ej5fM();
    private static final float mediumIconSize = MediumIconButtonTokens.INSTANCE.m421getIconSizeD9Ej5fM();
    private static final float largeIconSize = LargeIconButtonTokens.INSTANCE.m420getIconSizeD9Ej5fM();
    private static final float xLargeIconSize = XLargeIconButtonTokens.INSTANCE.m574getIconSizeD9Ej5fM();

    /* JADX INFO: compiled from: IconButtonDefaults.kt */
    public abstract class IconButtonWidthOption {
        public static final Companion Companion = new Companion(null);
        private static final int Narrow = m300constructorimpl(0);
        private static final int Uniform = m300constructorimpl(1);
        private static final int Wide = m300constructorimpl(2);

        /* JADX INFO: compiled from: IconButtonDefaults.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX INFO: renamed from: getNarrow-rc6NtMs, reason: not valid java name */
            public final int m302getNarrowrc6NtMs() {
                return IconButtonWidthOption.Narrow;
            }

            /* JADX INFO: renamed from: getUniform-rc6NtMs, reason: not valid java name */
            public final int m303getUniformrc6NtMs() {
                return IconButtonWidthOption.Uniform;
            }

            /* JADX INFO: renamed from: getWide-rc6NtMs, reason: not valid java name */
            public final int m304getWiderc6NtMs() {
                return IconButtonWidthOption.Wide;
            }
        }

        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        private static int m300constructorimpl(int i) {
            return i;
        }

        /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
        public static final boolean m301equalsimpl0(int i, int i2) {
            return i == i2;
        }
    }

    private IconButtonDefaults() {
    }

    /* JADX INFO: renamed from: smallContainerSize-N-wlBFI$default, reason: not valid java name */
    public static /* synthetic */ long m296smallContainerSizeNwlBFI$default(IconButtonDefaults iconButtonDefaults, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = IconButtonWidthOption.Companion.m303getUniformrc6NtMs();
        }
        return iconButtonDefaults.m299smallContainerSizeNwlBFI(i);
    }

    /* JADX INFO: renamed from: defaultIconButtonColors-4WTKRHQ$material3_release, reason: not valid java name */
    public final IconButtonColors m297defaultIconButtonColors4WTKRHQ$material3_release(ColorScheme colorScheme, long j) {
        IconButtonColors defaultIconButtonColorsCached$material3_release = colorScheme.getDefaultIconButtonColorsCached$material3_release();
        if (defaultIconButtonColorsCached$material3_release != null) {
            return defaultIconButtonColorsCached$material3_release;
        }
        Color.Companion companion = Color.Companion;
        IconButtonColors iconButtonColors = new IconButtonColors(companion.m894getTransparent0d7_KjU(), j, companion.m894getTransparent0d7_KjU(), Color.m880copywmQWz5c$default(j, StandardIconButtonTokens.INSTANCE.getDisabledOpacity(), 0.0f, 0.0f, 0.0f, 14, null), null);
        colorScheme.setDefaultIconButtonColorsCached$material3_release(iconButtonColors);
        return iconButtonColors;
    }

    public final Shape getStandardShape(Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-377108005, i, -1, "androidx.compose.material3.IconButtonDefaults.<get-standardShape> (IconButtonDefaults.kt:855)");
        }
        Shape value = ShapesKt.getValue(SmallIconButtonTokens.INSTANCE.getContainerShapeRound(), composer, 6);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    public final IconButtonColors iconButtonColors(Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-1037266503, i, -1, "androidx.compose.material3.IconButtonDefaults.iconButtonColors (IconButtonDefaults.kt:48)");
        }
        long jM890unboximpl = ((Color) composer.consume(ContentColorKt.getLocalContentColor())).m890unboximpl();
        IconButtonColors iconButtonColorsM297defaultIconButtonColors4WTKRHQ$material3_release = m297defaultIconButtonColors4WTKRHQ$material3_release(MaterialTheme.INSTANCE.getColorScheme(composer, 6), jM890unboximpl);
        if (!Color.m882equalsimpl0(iconButtonColorsM297defaultIconButtonColors4WTKRHQ$material3_release.m295getContentColor0d7_KjU(), jM890unboximpl)) {
            iconButtonColorsM297defaultIconButtonColors4WTKRHQ$material3_release = IconButtonColors.m291copyjRlVdoo$default(iconButtonColorsM297defaultIconButtonColors4WTKRHQ$material3_release, 0L, jM890unboximpl, 0L, Color.m880copywmQWz5c$default(jM890unboximpl, StandardIconButtonTokens.INSTANCE.getDisabledOpacity(), 0.0f, 0.0f, 0.0f, 14, null), 5, null);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return iconButtonColorsM297defaultIconButtonColors4WTKRHQ$material3_release;
    }

    /* JADX INFO: renamed from: iconButtonColors-ro_MJ88, reason: not valid java name */
    public final IconButtonColors m298iconButtonColorsro_MJ88(long j, long j2, long j3, long j4, Composer composer, int i, int i2) {
        if ((i2 & 1) != 0) {
            j = Color.Companion.m895getUnspecified0d7_KjU();
        }
        long jM890unboximpl = (i2 & 2) != 0 ? ((Color) composer.consume(ContentColorKt.getLocalContentColor())).m890unboximpl() : j2;
        long jM895getUnspecified0d7_KjU = (i2 & 4) != 0 ? Color.Companion.m895getUnspecified0d7_KjU() : j3;
        long jM880copywmQWz5c$default = (i2 & 8) != 0 ? Color.m880copywmQWz5c$default(jM890unboximpl, StandardIconButtonTokens.INSTANCE.getDisabledOpacity(), 0.0f, 0.0f, 0.0f, 14, null) : j4;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-1639168605, i, -1, "androidx.compose.material3.IconButtonDefaults.iconButtonColors (IconButtonDefaults.kt:84)");
        }
        IconButtonColors iconButtonColorsM294copyjRlVdoo = m297defaultIconButtonColors4WTKRHQ$material3_release(MaterialTheme.INSTANCE.getColorScheme(composer, 6), ((Color) composer.consume(ContentColorKt.getLocalContentColor())).m890unboximpl()).m294copyjRlVdoo(j, jM890unboximpl, jM895getUnspecified0d7_KjU, jM880copywmQWz5c$default);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return iconButtonColorsM294copyjRlVdoo;
    }

    /* JADX INFO: renamed from: smallContainerSize-N-wlBFI, reason: not valid java name */
    public final long m299smallContainerSizeNwlBFI(int i) {
        float fM1877constructorimpl;
        IconButtonWidthOption.Companion companion = IconButtonWidthOption.Companion;
        if (IconButtonWidthOption.m301equalsimpl0(i, companion.m302getNarrowrc6NtMs())) {
            SmallIconButtonTokens smallIconButtonTokens = SmallIconButtonTokens.INSTANCE;
            fM1877constructorimpl = Dp.m1877constructorimpl(smallIconButtonTokens.m480getNarrowLeadingSpaceD9Ej5fM() + smallIconButtonTokens.m481getNarrowTrailingSpaceD9Ej5fM());
        } else if (IconButtonWidthOption.m301equalsimpl0(i, companion.m303getUniformrc6NtMs())) {
            SmallIconButtonTokens smallIconButtonTokens2 = SmallIconButtonTokens.INSTANCE;
            fM1877constructorimpl = Dp.m1877constructorimpl(smallIconButtonTokens2.m478getDefaultLeadingSpaceD9Ej5fM() + smallIconButtonTokens2.m478getDefaultLeadingSpaceD9Ej5fM());
        } else if (IconButtonWidthOption.m301equalsimpl0(i, companion.m304getWiderc6NtMs())) {
            SmallIconButtonTokens smallIconButtonTokens3 = SmallIconButtonTokens.INSTANCE;
            fM1877constructorimpl = Dp.m1877constructorimpl(smallIconButtonTokens3.m482getWideLeadingSpaceD9Ej5fM() + smallIconButtonTokens3.m483getWideTrailingSpaceD9Ej5fM());
        } else {
            fM1877constructorimpl = Dp.m1877constructorimpl(0);
        }
        SmallIconButtonTokens smallIconButtonTokens4 = SmallIconButtonTokens.INSTANCE;
        return DpKt.m1886DpSizeYgX7TsA(Dp.m1877constructorimpl(smallIconButtonTokens4.m479getIconSizeD9Ej5fM() + fM1877constructorimpl), smallIconButtonTokens4.m477getContainerHeightD9Ej5fM());
    }
}
