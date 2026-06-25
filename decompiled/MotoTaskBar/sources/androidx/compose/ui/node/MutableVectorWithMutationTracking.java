package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: MutableVectorWithMutationTracking.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MutableVectorWithMutationTracking {
    public static final int $stable = MutableVector.$stable;
    private final Function0 onVectorMutated;
    private final MutableVector vector;

    public MutableVectorWithMutationTracking(MutableVector mutableVector, Function0 function0) {
        this.vector = mutableVector;
        this.onVectorMutated = function0;
    }

    public final void add(int i, Object obj) {
        this.vector.add(i, obj);
        this.onVectorMutated.mo2224invoke();
    }

    public final void clear() {
        this.vector.clear();
        this.onVectorMutated.mo2224invoke();
    }

    public final MutableVector getVector() {
        return this.vector;
    }

    public final Object removeAt(int i) {
        Object objRemoveAt = this.vector.removeAt(i);
        this.onVectorMutated.mo2224invoke();
        return objRemoveAt;
    }
}
