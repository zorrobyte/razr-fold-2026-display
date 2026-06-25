package androidx.compose.ui.platform;

import androidx.compose.runtime.collection.MutableVector;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: WeakCache.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class WeakCache {
    private final MutableVector values = new MutableVector(new Reference[16], 0);
    private final ReferenceQueue referenceQueue = new ReferenceQueue();

    private final void clearWeakReferences() {
        Reference referencePoll;
        do {
            referencePoll = this.referenceQueue.poll();
            if (referencePoll != null) {
                this.values.remove(referencePoll);
            }
        } while (referencePoll != null);
    }

    public final Object pop() {
        clearWeakReferences();
        while (this.values.getSize() != 0) {
            Object obj = ((Reference) this.values.removeAt(r0.getSize() - 1)).get();
            if (obj != null) {
                return obj;
            }
        }
        return null;
    }

    public final void push(Object obj) {
        clearWeakReferences();
        this.values.add(new WeakReference(obj, this.referenceQueue));
    }
}
