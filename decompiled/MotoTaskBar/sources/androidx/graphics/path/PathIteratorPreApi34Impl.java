package androidx.graphics.path;

import android.graphics.Path;
import dalvik.annotation.optimization.FastNative;

/* JADX INFO: compiled from: PathIteratorImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PathIteratorPreApi34Impl extends PathIteratorImpl {
    private final long internalPathIterator;

    private final native long createInternalPathIterator(Path path, int i, float f);

    private final native void destroyInternalPathIterator(long j);

    @FastNative
    private final native boolean internalPathIteratorHasNext(long j);

    @FastNative
    private final native int internalPathIteratorNext(long j, float[] fArr, int i);

    @FastNative
    private final native int internalPathIteratorPeek(long j);

    @FastNative
    private final native int internalPathIteratorRawSize(long j);

    @FastNative
    private final native int internalPathIteratorSize(long j);

    protected final void finalize() {
        destroyInternalPathIterator(this.internalPathIterator);
    }
}
