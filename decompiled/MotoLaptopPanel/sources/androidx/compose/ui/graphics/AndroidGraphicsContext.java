package androidx.compose.ui.graphics;

import android.content.ComponentCallbacks2;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerV29;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: AndroidGraphicsContext.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class AndroidGraphicsContext implements GraphicsContext {
    public static final Companion Companion = new Companion(null);
    private static boolean isRenderNodeCompatible = true;
    private final ViewGroup ownerView;
    private final Object lock = new Object();
    private final ComponentCallbacks2 componentCallback = null;

    /* JADX INFO: compiled from: AndroidGraphicsContext.android.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: AndroidGraphicsContext.android.kt */
    final class UniqueDrawingIdApi29 {
        public static final UniqueDrawingIdApi29 INSTANCE = new UniqueDrawingIdApi29();

        private UniqueDrawingIdApi29() {
        }

        public static final long getUniqueDrawingId(View view) {
            return view.getUniqueDrawingId();
        }
    }

    public AndroidGraphicsContext(ViewGroup viewGroup) {
        this.ownerView = viewGroup;
    }

    private final long getUniqueDrawingId(View view) {
        return UniqueDrawingIdApi29.getUniqueDrawingId(view);
    }

    @Override // androidx.compose.ui.graphics.GraphicsContext
    public GraphicsLayer createGraphicsLayer() {
        GraphicsLayer graphicsLayer;
        synchronized (this.lock) {
            graphicsLayer = new GraphicsLayer(new GraphicsLayerV29(getUniqueDrawingId(this.ownerView), null, null, 6, null), null);
        }
        return graphicsLayer;
    }

    @Override // androidx.compose.ui.graphics.GraphicsContext
    public void releaseGraphicsLayer(GraphicsLayer graphicsLayer) {
        synchronized (this.lock) {
            graphicsLayer.release$ui_graphics_release();
            Unit unit = Unit.INSTANCE;
        }
    }
}
