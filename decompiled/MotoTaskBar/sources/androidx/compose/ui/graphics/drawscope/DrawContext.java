package androidx.compose.ui.graphics.drawscope;

import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* JADX INFO: compiled from: DrawContext.kt */
/* JADX INFO: loaded from: classes.dex */
public interface DrawContext {
    Canvas getCanvas();

    Density getDensity();

    GraphicsLayer getGraphicsLayer();

    LayoutDirection getLayoutDirection();

    /* JADX INFO: renamed from: getSize-NH-jbRc */
    long mo384getSizeNHjbRc();

    DrawTransform getTransform();

    void setCanvas(Canvas canvas);

    void setDensity(Density density);

    void setGraphicsLayer(GraphicsLayer graphicsLayer);

    void setLayoutDirection(LayoutDirection layoutDirection);

    /* JADX INFO: renamed from: setSize-uvyYCjk */
    void mo385setSizeuvyYCjk(long j);
}
