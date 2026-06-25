package androidx.compose.ui.graphics.drawscope;

import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.FilterQuality;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: DrawScope.kt */
/* JADX INFO: loaded from: classes.dex */
public interface DrawScope extends Density {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: DrawScope.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final int DefaultBlendMode = BlendMode.Companion.m862getSrcOver0nO6VwU();
        private static final int DefaultFilterQuality = FilterQuality.Companion.m913getLowfv9h1I();

        private Companion() {
        }

        /* JADX INFO: renamed from: getDefaultBlendMode-0nO6VwU, reason: not valid java name */
        public final int m1084getDefaultBlendMode0nO6VwU() {
            return DefaultBlendMode;
        }

        /* JADX INFO: renamed from: getDefaultFilterQuality-f-v9h1I, reason: not valid java name */
        public final int m1085getDefaultFilterQualityfv9h1I() {
            return DefaultFilterQuality;
        }
    }

    /* JADX INFO: renamed from: drawCircle-VaOC9Bg$default, reason: not valid java name */
    static /* synthetic */ void m1072drawCircleVaOC9Bg$default(DrawScope drawScope, long j, float f, long j2, float f2, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawCircle-VaOC9Bg");
        }
        if ((i2 & 2) != 0) {
            f = Size.m787getMinDimensionimpl(drawScope.mo1082getSizeNHjbRc()) / 2.0f;
        }
        drawScope.mo1054drawCircleVaOC9Bg(j, f, (i2 & 4) != 0 ? drawScope.mo1081getCenterF1C5BW0() : j2, (i2 & 8) != 0 ? 1.0f : f2, (i2 & 16) != 0 ? Fill.INSTANCE : drawStyle, (i2 & 32) != 0 ? null : colorFilter, (i2 & 64) != 0 ? Companion.m1084getDefaultBlendMode0nO6VwU() : i);
    }

    /* JADX INFO: renamed from: drawImage-AZ2fEMs$default, reason: not valid java name */
    static /* synthetic */ void m1073drawImageAZ2fEMs$default(DrawScope drawScope, ImageBitmap imageBitmap, long j, long j2, long j3, long j4, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawImage-AZ2fEMs");
        }
        long jM1913getZeronOccac = (i3 & 2) != 0 ? IntOffset.Companion.m1913getZeronOccac() : j;
        long jM1919constructorimpl = (i3 & 4) != 0 ? IntSize.m1919constructorimpl((((long) imageBitmap.getHeight()) & 4294967295L) | (((long) imageBitmap.getWidth()) << 32)) : j2;
        drawScope.mo1055drawImageAZ2fEMs(imageBitmap, jM1913getZeronOccac, jM1919constructorimpl, (i3 & 8) != 0 ? IntOffset.Companion.m1913getZeronOccac() : j3, (i3 & 16) != 0 ? jM1919constructorimpl : j4, (i3 & 32) != 0 ? 1.0f : f, (i3 & 64) != 0 ? Fill.INSTANCE : drawStyle, (i3 & 128) != 0 ? null : colorFilter, (i3 & 256) != 0 ? Companion.m1084getDefaultBlendMode0nO6VwU() : i, (i3 & 512) != 0 ? Companion.m1085getDefaultFilterQualityfv9h1I() : i2);
    }

    /* JADX INFO: renamed from: drawPath-GBMwjPU$default, reason: not valid java name */
    static /* synthetic */ void m1074drawPathGBMwjPU$default(DrawScope drawScope, Path path, Brush brush, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawPath-GBMwjPU");
        }
        if ((i2 & 4) != 0) {
            f = 1.0f;
        }
        float f2 = f;
        if ((i2 & 8) != 0) {
            drawStyle = Fill.INSTANCE;
        }
        DrawStyle drawStyle2 = drawStyle;
        if ((i2 & 16) != 0) {
            colorFilter = null;
        }
        ColorFilter colorFilter2 = colorFilter;
        if ((i2 & 32) != 0) {
            i = Companion.m1084getDefaultBlendMode0nO6VwU();
        }
        drawScope.mo1056drawPathGBMwjPU(path, brush, f2, drawStyle2, colorFilter2, i);
    }

    /* JADX INFO: renamed from: drawPath-LG529CI$default, reason: not valid java name */
    static /* synthetic */ void m1075drawPathLG529CI$default(DrawScope drawScope, Path path, long j, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawPath-LG529CI");
        }
        if ((i2 & 4) != 0) {
            f = 1.0f;
        }
        float f2 = f;
        if ((i2 & 8) != 0) {
            drawStyle = Fill.INSTANCE;
        }
        DrawStyle drawStyle2 = drawStyle;
        if ((i2 & 16) != 0) {
            colorFilter = null;
        }
        drawScope.mo1057drawPathLG529CI(path, j, f2, drawStyle2, colorFilter, (i2 & 32) != 0 ? Companion.m1084getDefaultBlendMode0nO6VwU() : i);
    }

    /* JADX INFO: renamed from: drawRect-AsUm42w$default, reason: not valid java name */
    static /* synthetic */ void m1076drawRectAsUm42w$default(DrawScope drawScope, Brush brush, long j, long j2, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawRect-AsUm42w");
        }
        long jM770getZeroF1C5BW0 = (i2 & 2) != 0 ? Offset.Companion.m770getZeroF1C5BW0() : j;
        drawScope.mo1058drawRectAsUm42w(brush, jM770getZeroF1C5BW0, (i2 & 4) != 0 ? drawScope.m1080offsetSizePENXr5M(drawScope.mo1082getSizeNHjbRc(), jM770getZeroF1C5BW0) : j2, (i2 & 8) != 0 ? 1.0f : f, (i2 & 16) != 0 ? Fill.INSTANCE : drawStyle, (i2 & 32) != 0 ? null : colorFilter, (i2 & 64) != 0 ? Companion.m1084getDefaultBlendMode0nO6VwU() : i);
    }

    /* JADX INFO: renamed from: drawRect-n-J9OG0$default, reason: not valid java name */
    static /* synthetic */ void m1077drawRectnJ9OG0$default(DrawScope drawScope, long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawRect-n-J9OG0");
        }
        long jM770getZeroF1C5BW0 = (i2 & 2) != 0 ? Offset.Companion.m770getZeroF1C5BW0() : j2;
        drawScope.mo1059drawRectnJ9OG0(j, jM770getZeroF1C5BW0, (i2 & 4) != 0 ? drawScope.m1080offsetSizePENXr5M(drawScope.mo1082getSizeNHjbRc(), jM770getZeroF1C5BW0) : j3, (i2 & 8) != 0 ? 1.0f : f, (i2 & 16) != 0 ? Fill.INSTANCE : drawStyle, (i2 & 32) != 0 ? null : colorFilter, (i2 & 64) != 0 ? Companion.m1084getDefaultBlendMode0nO6VwU() : i);
    }

    /* JADX INFO: renamed from: drawRoundRect-ZuiqVtQ$default, reason: not valid java name */
    static /* synthetic */ void m1078drawRoundRectZuiqVtQ$default(DrawScope drawScope, Brush brush, long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawRoundRect-ZuiqVtQ");
        }
        long jM770getZeroF1C5BW0 = (i2 & 2) != 0 ? Offset.Companion.m770getZeroF1C5BW0() : j;
        drawScope.mo1060drawRoundRectZuiqVtQ(brush, jM770getZeroF1C5BW0, (i2 & 4) != 0 ? drawScope.m1080offsetSizePENXr5M(drawScope.mo1082getSizeNHjbRc(), jM770getZeroF1C5BW0) : j2, (i2 & 8) != 0 ? CornerRadius.Companion.m749getZerokKHJgLs() : j3, (i2 & 16) != 0 ? 1.0f : f, (i2 & 32) != 0 ? Fill.INSTANCE : drawStyle, (i2 & 64) != 0 ? null : colorFilter, (i2 & 128) != 0 ? Companion.m1084getDefaultBlendMode0nO6VwU() : i);
    }

    /* JADX INFO: renamed from: drawRoundRect-u-Aw5IA$default, reason: not valid java name */
    static /* synthetic */ void m1079drawRoundRectuAw5IA$default(DrawScope drawScope, long j, long j2, long j3, long j4, DrawStyle drawStyle, float f, ColorFilter colorFilter, int i, int i2, Object obj) {
        DrawScope drawScope2;
        long jM1080offsetSizePENXr5M;
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawRoundRect-u-Aw5IA");
        }
        long jM770getZeroF1C5BW0 = (i2 & 2) != 0 ? Offset.Companion.m770getZeroF1C5BW0() : j2;
        if ((i2 & 4) != 0) {
            drawScope2 = drawScope;
            jM1080offsetSizePENXr5M = drawScope2.m1080offsetSizePENXr5M(drawScope.mo1082getSizeNHjbRc(), jM770getZeroF1C5BW0);
        } else {
            drawScope2 = drawScope;
            jM1080offsetSizePENXr5M = j3;
        }
        drawScope2.mo1061drawRoundRectuAw5IA(j, jM770getZeroF1C5BW0, jM1080offsetSizePENXr5M, (i2 & 8) != 0 ? CornerRadius.Companion.m749getZerokKHJgLs() : j4, (i2 & 16) != 0 ? Fill.INSTANCE : drawStyle, (i2 & 32) != 0 ? 1.0f : f, (i2 & 64) != 0 ? null : colorFilter, (i2 & 128) != 0 ? Companion.m1084getDefaultBlendMode0nO6VwU() : i);
    }

    /* JADX INFO: renamed from: offsetSize-PENXr5M, reason: not valid java name */
    private default long m1080offsetSizePENXr5M(long j, long j2) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) - Float.intBitsToFloat((int) (j2 >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) - Float.intBitsToFloat((int) (j2 & 4294967295L));
        return Size.m783constructorimpl((((long) Float.floatToRawIntBits(fIntBitsToFloat)) << 32) | (((long) Float.floatToRawIntBits(fIntBitsToFloat2)) & 4294967295L));
    }

    /* JADX INFO: renamed from: drawCircle-VaOC9Bg */
    void mo1054drawCircleVaOC9Bg(long j, float f, long j2, float f2, DrawStyle drawStyle, ColorFilter colorFilter, int i);

    /* JADX INFO: renamed from: drawImage-AZ2fEMs */
    void mo1055drawImageAZ2fEMs(ImageBitmap imageBitmap, long j, long j2, long j3, long j4, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2);

    /* JADX INFO: renamed from: drawPath-GBMwjPU */
    void mo1056drawPathGBMwjPU(Path path, Brush brush, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i);

    /* JADX INFO: renamed from: drawPath-LG529CI */
    void mo1057drawPathLG529CI(Path path, long j, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i);

    /* JADX INFO: renamed from: drawRect-AsUm42w */
    void mo1058drawRectAsUm42w(Brush brush, long j, long j2, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i);

    /* JADX INFO: renamed from: drawRect-n-J9OG0 */
    void mo1059drawRectnJ9OG0(long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i);

    /* JADX INFO: renamed from: drawRoundRect-ZuiqVtQ */
    void mo1060drawRoundRectZuiqVtQ(Brush brush, long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i);

    /* JADX INFO: renamed from: drawRoundRect-u-Aw5IA */
    void mo1061drawRoundRectuAw5IA(long j, long j2, long j3, long j4, DrawStyle drawStyle, float f, ColorFilter colorFilter, int i);

    /* JADX INFO: renamed from: getCenter-F1C5BW0, reason: not valid java name */
    default long mo1081getCenterF1C5BW0() {
        return SizeKt.m795getCenteruvyYCjk(getDrawContext().mo1065getSizeNHjbRc());
    }

    DrawContext getDrawContext();

    LayoutDirection getLayoutDirection();

    /* JADX INFO: renamed from: getSize-NH-jbRc, reason: not valid java name */
    default long mo1082getSizeNHjbRc() {
        return getDrawContext().mo1065getSizeNHjbRc();
    }

    /* JADX INFO: renamed from: record-JVtK1S4, reason: not valid java name */
    void mo1083recordJVtK1S4(GraphicsLayer graphicsLayer, long j, Function1 function1);
}
