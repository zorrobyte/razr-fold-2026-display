package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.compose.runtime.external.kotlinx.collections.immutable.ImmutableSet;
import java.util.Iterator;
import java.util.Map;
import kotlin.collections.AbstractSet;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PersistentHashMapContentViews.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentHashMapEntries extends AbstractSet implements ImmutableSet {
    private final PersistentHashMap map;

    public PersistentHashMapEntries(PersistentHashMap persistentHashMap) {
        this.map = persistentHashMap;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.Set
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            return contains((Map.Entry) obj);
        }
        return false;
    }

    public boolean contains(Map.Entry entry) {
        if (entry == null) {
            return false;
        }
        Object obj = this.map.get(entry.getKey());
        return obj != null ? Intrinsics.areEqual(obj, entry.getValue()) : entry.getValue() == null && this.map.containsKey(entry.getKey());
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.map.size();
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator iterator() {
        return new PersistentHashMapEntriesIterator(this.map.getNode$runtime_release());
    }
}
