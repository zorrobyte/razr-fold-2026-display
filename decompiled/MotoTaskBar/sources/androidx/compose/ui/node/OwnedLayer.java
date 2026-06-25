package androidx.compose.ui.node;

import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: OwnedLayer.kt */
/* JADX INFO: loaded from: classes.dex */
public interface OwnedLayer {
    void destroy();

    void drawLayer(Canvas canvas, GraphicsLayer graphicsLayer);

    /* JADX INFO: renamed from: getUnderlyingMatrix-sQKQjiQ, reason: not valid java name */
    float[] mo660getUnderlyingMatrixsQKQjiQ();

    void invalidate();

    /* JADX INFO: renamed from: isInLayer-k-4lQ0M, reason: not valid java name */
    boolean mo661isInLayerk4lQ0M(long j);

    void mapBounds(MutableRect mutableRect, boolean z);

    /* JADX INFO: renamed from: mapOffset-8S9VItk, reason: not valid java name */
    long mo662mapOffset8S9VItk(long j, boolean z);

    /* JADX INFO: renamed from: move--gyyYBs, reason: not valid java name */
    void mo663movegyyYBs(long j);

    /* JADX INFO: renamed from: resize-ozmzZPI, reason: not valid java name */
    void mo664resizeozmzZPI(long j);

    void reuseLayer(Function2 function2, Function0 function0);

    void updateDisplayList();

    void updateLayerProperties(ReusableGraphicsLayerScope reusableGraphicsLayerScope);
}
