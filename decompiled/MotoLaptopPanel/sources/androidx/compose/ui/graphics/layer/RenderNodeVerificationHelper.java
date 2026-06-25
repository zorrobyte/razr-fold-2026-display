package androidx.compose.ui.graphics.layer;

import android.graphics.RenderNode;
import androidx.compose.ui.graphics.RenderEffect;

/* JADX INFO: compiled from: GraphicsLayerV29.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RenderNodeVerificationHelper {
    public static final RenderNodeVerificationHelper INSTANCE = new RenderNodeVerificationHelper();

    private RenderNodeVerificationHelper() {
    }

    public final void setRenderEffect(RenderNode renderNode, RenderEffect renderEffect) {
        renderNode.setRenderEffect(null);
    }
}
