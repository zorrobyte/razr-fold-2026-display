package androidx.compose.ui.graphics.layer;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.drawscope.DrawScope;

/* JADX INFO: compiled from: GraphicsLayer.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class GraphicsLayerKt {
    public static final void drawLayer(DrawScope drawScope, GraphicsLayer graphicsLayer) {
        graphicsLayer.draw$ui_graphics_release(drawScope.getDrawContext().getCanvas(), drawScope.getDrawContext().getGraphicsLayer());
    }

    public static final void setOutline(GraphicsLayer graphicsLayer, Outline outline) {
        if (outline instanceof Outline.Rectangle) {
            Outline.Rectangle rectangle = (Outline.Rectangle) outline;
            long jM182constructorimpl = Offset.m182constructorimpl((((long) Float.floatToRawIntBits(rectangle.getRect().getLeft())) << 32) | (((long) Float.floatToRawIntBits(rectangle.getRect().getTop())) & 4294967295L));
            Rect rect = rectangle.getRect();
            float right = rect.getRight() - rect.getLeft();
            Rect rect2 = rectangle.getRect();
            graphicsLayer.m408setRectOutlinetz77jQw(jM182constructorimpl, Size.m206constructorimpl((((long) Float.floatToRawIntBits(rect2.getBottom() - rect2.getTop())) & 4294967295L) | (Float.floatToRawIntBits(right) << 32)));
            return;
        }
        if (outline instanceof Outline.Generic) {
            graphicsLayer.setPathOutline(((Outline.Generic) outline).getPath());
            return;
        }
        if (outline instanceof Outline.Rounded) {
            Outline.Rounded rounded = (Outline.Rounded) outline;
            if (rounded.getRoundRectPath$ui_graphics_release() != null) {
                graphicsLayer.setPathOutline(rounded.getRoundRectPath$ui_graphics_release());
                return;
            }
            RoundRect roundRect = rounded.getRoundRect();
            long jM182constructorimpl2 = Offset.m182constructorimpl((((long) Float.floatToRawIntBits(roundRect.getLeft())) << 32) | (((long) Float.floatToRawIntBits(roundRect.getTop())) & 4294967295L));
            float width = roundRect.getWidth();
            graphicsLayer.m409setRoundRectOutlineTNW_H78(jM182constructorimpl2, Size.m206constructorimpl((((long) Float.floatToRawIntBits(roundRect.getHeight())) & 4294967295L) | (Float.floatToRawIntBits(width) << 32)), Float.intBitsToFloat((int) (roundRect.m201getBottomLeftCornerRadiuskKHJgLs() >> 32)));
        }
    }
}
