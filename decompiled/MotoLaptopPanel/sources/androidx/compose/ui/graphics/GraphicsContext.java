package androidx.compose.ui.graphics;

import androidx.compose.ui.graphics.layer.GraphicsLayer;

/* JADX INFO: compiled from: GraphicsContext.kt */
/* JADX INFO: loaded from: classes.dex */
public interface GraphicsContext {
    GraphicsLayer createGraphicsLayer();

    void releaseGraphicsLayer(GraphicsLayer graphicsLayer);
}
