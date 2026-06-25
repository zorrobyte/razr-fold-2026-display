package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.drawscope.Fill;
import kotlin.NoWhenBranchMatchedException;

/* JADX INFO: compiled from: Outline.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class OutlineKt {
    /* JADX INFO: renamed from: drawOutline-hn5TExg, reason: not valid java name */
    public static final void m956drawOutlinehn5TExg(DrawScope drawScope, Outline outline, Brush brush, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        if (outline instanceof Outline.Rectangle) {
            Rect rect = ((Outline.Rectangle) outline).getRect();
            drawScope.mo1058drawRectAsUm42w(brush, topLeft(rect), size(rect), f, drawStyle, colorFilter, i);
            return;
        }
        if (!(outline instanceof Outline.Rounded)) {
            if (!(outline instanceof Outline.Generic)) {
                throw new NoWhenBranchMatchedException();
            }
            drawScope.mo1056drawPathGBMwjPU(((Outline.Generic) outline).getPath(), brush, f, drawStyle, colorFilter, i);
            return;
        }
        Outline.Rounded rounded = (Outline.Rounded) outline;
        Path roundRectPath$ui_graphics_release = rounded.getRoundRectPath$ui_graphics_release();
        if (roundRectPath$ui_graphics_release != null) {
            drawScope.mo1056drawPathGBMwjPU(roundRectPath$ui_graphics_release, brush, f, drawStyle, colorFilter, i);
            return;
        }
        RoundRect roundRect = rounded.getRoundRect();
        float fIntBitsToFloat = Float.intBitsToFloat((int) (roundRect.m776getBottomLeftCornerRadiuskKHJgLs() >> 32));
        drawScope.mo1060drawRoundRectZuiqVtQ(brush, topLeft(roundRect), size(roundRect), CornerRadius.m745constructorimpl((((long) Float.floatToRawIntBits(fIntBitsToFloat)) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32)), f, drawStyle, colorFilter, i);
    }

    /* JADX INFO: renamed from: drawOutline-hn5TExg$default, reason: not valid java name */
    public static /* synthetic */ void m957drawOutlinehn5TExg$default(DrawScope drawScope, Outline outline, Brush brush, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2, Object obj) {
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
            i = DrawScope.Companion.m1084getDefaultBlendMode0nO6VwU();
        }
        m956drawOutlinehn5TExg(drawScope, outline, brush, f2, drawStyle2, colorFilter2, i);
    }

    /* JADX INFO: renamed from: drawOutline-wDX37Ww, reason: not valid java name */
    public static final void m958drawOutlinewDX37Ww(DrawScope drawScope, Outline outline, long j, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        if (outline instanceof Outline.Rectangle) {
            Rect rect = ((Outline.Rectangle) outline).getRect();
            drawScope.mo1059drawRectnJ9OG0(j, topLeft(rect), size(rect), f, drawStyle, colorFilter, i);
            return;
        }
        if (!(outline instanceof Outline.Rounded)) {
            if (!(outline instanceof Outline.Generic)) {
                throw new NoWhenBranchMatchedException();
            }
            drawScope.mo1057drawPathLG529CI(((Outline.Generic) outline).getPath(), j, f, drawStyle, colorFilter, i);
            return;
        }
        Outline.Rounded rounded = (Outline.Rounded) outline;
        Path roundRectPath$ui_graphics_release = rounded.getRoundRectPath$ui_graphics_release();
        if (roundRectPath$ui_graphics_release != null) {
            drawScope.mo1057drawPathLG529CI(roundRectPath$ui_graphics_release, j, f, drawStyle, colorFilter, i);
            return;
        }
        RoundRect roundRect = rounded.getRoundRect();
        float fIntBitsToFloat = Float.intBitsToFloat((int) (roundRect.m776getBottomLeftCornerRadiuskKHJgLs() >> 32));
        drawScope.mo1061drawRoundRectuAw5IA(j, topLeft(roundRect), size(roundRect), CornerRadius.m745constructorimpl((((long) Float.floatToRawIntBits(fIntBitsToFloat)) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32)), drawStyle, f, colorFilter, i);
    }

    /* JADX INFO: renamed from: drawOutline-wDX37Ww$default, reason: not valid java name */
    public static /* synthetic */ void m959drawOutlinewDX37Ww$default(DrawScope drawScope, Outline outline, long j, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2, Object obj) {
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
        m958drawOutlinewDX37Ww(drawScope, outline, j, f2, drawStyle2, colorFilter, (i2 & 32) != 0 ? DrawScope.Companion.m1084getDefaultBlendMode0nO6VwU() : i);
    }

    private static final long size(Rect rect) {
        float right = rect.getRight() - rect.getLeft();
        return Size.m783constructorimpl((((long) Float.floatToRawIntBits(rect.getBottom() - rect.getTop())) & 4294967295L) | (Float.floatToRawIntBits(right) << 32));
    }

    private static final long size(RoundRect roundRect) {
        float width = roundRect.getWidth();
        float height = roundRect.getHeight();
        return Size.m783constructorimpl((((long) Float.floatToRawIntBits(width)) << 32) | (((long) Float.floatToRawIntBits(height)) & 4294967295L));
    }

    private static final long topLeft(Rect rect) {
        float left = rect.getLeft();
        float top = rect.getTop();
        return Offset.m752constructorimpl((((long) Float.floatToRawIntBits(left)) << 32) | (((long) Float.floatToRawIntBits(top)) & 4294967295L));
    }

    private static final long topLeft(RoundRect roundRect) {
        float left = roundRect.getLeft();
        float top = roundRect.getTop();
        return Offset.m752constructorimpl((((long) Float.floatToRawIntBits(left)) << 32) | (((long) Float.floatToRawIntBits(top)) & 4294967295L));
    }
}
