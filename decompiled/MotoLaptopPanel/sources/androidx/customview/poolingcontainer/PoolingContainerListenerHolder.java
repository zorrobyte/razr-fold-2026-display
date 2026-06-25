package androidx.customview.poolingcontainer;

import java.util.ArrayList;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: PoolingContainer.kt */
/* JADX INFO: loaded from: classes.dex */
final class PoolingContainerListenerHolder {
    private final ArrayList listeners = new ArrayList();

    public final void addListener(PoolingContainerListener poolingContainerListener) {
        poolingContainerListener.getClass();
        this.listeners.add(poolingContainerListener);
    }

    public final void onRelease() {
        for (int lastIndex = CollectionsKt.getLastIndex(this.listeners); -1 < lastIndex; lastIndex--) {
            ((PoolingContainerListener) this.listeners.get(lastIndex)).onRelease();
        }
    }

    public final void removeListener(PoolingContainerListener poolingContainerListener) {
        poolingContainerListener.getClass();
        this.listeners.remove(poolingContainerListener);
    }
}
