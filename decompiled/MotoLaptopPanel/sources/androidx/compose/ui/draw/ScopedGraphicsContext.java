package androidx.compose.ui.draw;

import androidx.collection.MutableObjectList;
import androidx.collection.ObjectListKt;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.internal.InlineClassHelperKt;

/* JADX INFO: compiled from: DrawModifier.kt */
/* JADX INFO: loaded from: classes.dex */
final class ScopedGraphicsContext implements GraphicsContext {
    private MutableObjectList allocatedGraphicsLayers;
    private GraphicsContext graphicsContext;

    @Override // androidx.compose.ui.graphics.GraphicsContext
    public GraphicsLayer createGraphicsLayer() {
        GraphicsContext graphicsContext = this.graphicsContext;
        if (!(graphicsContext != null)) {
            InlineClassHelperKt.throwIllegalStateException("GraphicsContext not provided");
        }
        GraphicsLayer graphicsLayerCreateGraphicsLayer = graphicsContext.createGraphicsLayer();
        MutableObjectList mutableObjectList = this.allocatedGraphicsLayers;
        if (mutableObjectList == null) {
            this.allocatedGraphicsLayers = ObjectListKt.mutableObjectListOf(graphicsLayerCreateGraphicsLayer);
            return graphicsLayerCreateGraphicsLayer;
        }
        mutableObjectList.add(graphicsLayerCreateGraphicsLayer);
        return graphicsLayerCreateGraphicsLayer;
    }

    public final GraphicsContext getGraphicsContext() {
        return this.graphicsContext;
    }

    @Override // androidx.compose.ui.graphics.GraphicsContext
    public void releaseGraphicsLayer(GraphicsLayer graphicsLayer) {
        GraphicsContext graphicsContext = this.graphicsContext;
        if (graphicsContext != null) {
            graphicsContext.releaseGraphicsLayer(graphicsLayer);
        }
    }

    public final void releaseGraphicsLayers() {
        MutableObjectList mutableObjectList = this.allocatedGraphicsLayers;
        if (mutableObjectList != null) {
            Object[] objArr = mutableObjectList.content;
            int i = mutableObjectList._size;
            for (int i2 = 0; i2 < i; i2++) {
                releaseGraphicsLayer((GraphicsLayer) objArr[i2]);
            }
            mutableObjectList.clear();
        }
    }

    public final void setGraphicsContext(GraphicsContext graphicsContext) {
        releaseGraphicsLayers();
        this.graphicsContext = graphicsContext;
    }
}
