package androidx.compose.ui.graphics.layer;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.ui.graphics.InlineClassHelperKt;

/* JADX INFO: compiled from: ChildLayerDependenciesTracker.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ChildLayerDependenciesTracker {
    private MutableScatterSet dependenciesSet;
    private GraphicsLayer dependency;
    private MutableScatterSet oldDependenciesSet;
    private GraphicsLayer oldDependency;
    private boolean trackingInProgress;

    public final boolean onDependencyAdded(GraphicsLayer graphicsLayer) {
        if (!this.trackingInProgress) {
            InlineClassHelperKt.throwIllegalArgumentException("Only add dependencies during a tracking");
        }
        MutableScatterSet mutableScatterSet = this.dependenciesSet;
        if (mutableScatterSet != null) {
            mutableScatterSet.getClass();
            mutableScatterSet.add(graphicsLayer);
        } else if (this.dependency != null) {
            MutableScatterSet mutableScatterSetMutableScatterSetOf = ScatterSetKt.mutableScatterSetOf();
            GraphicsLayer graphicsLayer2 = this.dependency;
            graphicsLayer2.getClass();
            mutableScatterSetMutableScatterSetOf.add(graphicsLayer2);
            mutableScatterSetMutableScatterSetOf.add(graphicsLayer);
            this.dependenciesSet = mutableScatterSetMutableScatterSetOf;
            this.dependency = null;
        } else {
            this.dependency = graphicsLayer;
        }
        MutableScatterSet mutableScatterSet2 = this.oldDependenciesSet;
        if (mutableScatterSet2 != null) {
            mutableScatterSet2.getClass();
            return !mutableScatterSet2.remove(graphicsLayer);
        }
        if (this.oldDependency != graphicsLayer) {
            return true;
        }
        this.oldDependency = null;
        return false;
    }
}
