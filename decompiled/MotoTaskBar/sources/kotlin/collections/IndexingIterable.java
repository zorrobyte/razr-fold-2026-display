package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: Iterables.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class IndexingIterable implements Iterable, KMappedMarker {
    private final Function0 iteratorFactory;

    public IndexingIterable(Function0 function0) {
        function0.getClass();
        this.iteratorFactory = function0;
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        return new IndexingIterator((Iterator) this.iteratorFactory.mo2224invoke());
    }
}
