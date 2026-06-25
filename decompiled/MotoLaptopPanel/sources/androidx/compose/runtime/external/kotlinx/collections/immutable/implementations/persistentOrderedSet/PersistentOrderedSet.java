package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet;

import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentSet;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.EndOfChain;
import java.util.Iterator;
import kotlin.collections.AbstractSet;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PersistentOrderedSet.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentOrderedSet extends AbstractSet implements PersistentSet {
    private static final PersistentOrderedSet EMPTY;
    private final Object firstElement;
    private final PersistentHashMap hashMap;
    private final Object lastElement;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: PersistentOrderedSet.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PersistentSet emptyOf$runtime_release() {
            return PersistentOrderedSet.EMPTY;
        }
    }

    static {
        EndOfChain endOfChain = EndOfChain.INSTANCE;
        EMPTY = new PersistentOrderedSet(endOfChain, endOfChain, PersistentHashMap.Companion.emptyOf$runtime_release());
    }

    public PersistentOrderedSet(Object obj, Object obj2, PersistentHashMap persistentHashMap) {
        this.firstElement = obj;
        this.lastElement = obj2;
        this.hashMap = persistentHashMap;
    }

    @Override // java.util.Collection, java.util.Set, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentSet
    public PersistentSet add(Object obj) {
        if (this.hashMap.containsKey(obj)) {
            return this;
        }
        if (isEmpty()) {
            return new PersistentOrderedSet(obj, obj, this.hashMap.put(obj, (Object) new Links()));
        }
        Object obj2 = this.lastElement;
        Object obj3 = this.hashMap.get(obj2);
        obj3.getClass();
        return new PersistentOrderedSet(this.firstElement, obj, this.hashMap.put(obj2, (Object) ((Links) obj3).withNext(obj)).put(obj, (Object) new Links(obj2)));
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        return this.hashMap.containsKey(obj);
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.hashMap.size();
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator iterator() {
        return new PersistentOrderedSetIterator(this.firstElement, this.hashMap);
    }

    @Override // java.util.Collection, java.util.Set, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentSet
    public PersistentSet remove(Object obj) {
        Links links = (Links) this.hashMap.get(obj);
        if (links == null) {
            return this;
        }
        PersistentHashMap persistentHashMapRemove = this.hashMap.remove(obj);
        if (links.getHasPrevious()) {
            Object obj2 = persistentHashMapRemove.get(links.getPrevious());
            obj2.getClass();
            persistentHashMapRemove = persistentHashMapRemove.put(links.getPrevious(), (Object) ((Links) obj2).withNext(links.getNext()));
        }
        if (links.getHasNext()) {
            Object obj3 = persistentHashMapRemove.get(links.getNext());
            obj3.getClass();
            persistentHashMapRemove = persistentHashMapRemove.put(links.getNext(), (Object) ((Links) obj3).withPrevious(links.getPrevious()));
        }
        return new PersistentOrderedSet(!links.getHasPrevious() ? links.getNext() : this.firstElement, !links.getHasNext() ? links.getPrevious() : this.lastElement, persistentHashMapRemove);
    }
}
