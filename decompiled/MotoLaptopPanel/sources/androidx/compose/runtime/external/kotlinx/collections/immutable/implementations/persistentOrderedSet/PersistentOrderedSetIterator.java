package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: PersistentOrderedSetIterator.kt */
/* JADX INFO: loaded from: classes.dex */
public class PersistentOrderedSetIterator implements Iterator, KMappedMarker {
    private int index;
    private final Map map;
    private Object nextElement;

    public PersistentOrderedSetIterator(Object obj, Map map) {
        this.nextElement = obj;
        this.map = map;
    }

    private final void checkHasNext() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.index < this.map.size();
    }

    @Override // java.util.Iterator
    public Object next() {
        checkHasNext();
        Object obj = this.nextElement;
        this.index++;
        Object obj2 = this.map.get(obj);
        if (obj2 != null) {
            this.nextElement = ((Links) obj2).getNext();
            return obj;
        }
        throw new ConcurrentModificationException("Hash code of an element (" + obj + ") has changed after it was added to the persistent set.");
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
