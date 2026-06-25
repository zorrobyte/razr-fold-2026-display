package androidx.core.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: ViewGroup.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TreeIterator implements Iterator, KMappedMarker {
    private final Function1 getChildIterator;
    private Iterator iterator;
    private final List stack = new ArrayList();

    public TreeIterator(Iterator it, Function1 function1) {
        this.getChildIterator = function1;
        this.iterator = it;
    }

    private final void prepareNextIterator(Object obj) {
        Iterator it = (Iterator) this.getChildIterator.invoke(obj);
        if (it != null && it.hasNext()) {
            this.stack.add(this.iterator);
            this.iterator = it;
        } else {
            while (!this.iterator.hasNext() && !this.stack.isEmpty()) {
                this.iterator = (Iterator) CollectionsKt.last(this.stack);
                CollectionsKt.removeLast(this.stack);
            }
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public Object next() {
        Object next = this.iterator.next();
        prepareNextIterator(next);
        return next;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
