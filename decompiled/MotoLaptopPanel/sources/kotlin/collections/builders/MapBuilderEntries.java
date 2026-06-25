package kotlin.collections.builders;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: MapBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MapBuilderEntries extends AbstractMapBuilderEntrySet {
    private final MapBuilder backing;

    public MapBuilderEntries(MapBuilder mapBuilder) {
        mapBuilder.getClass();
        this.backing = mapBuilder;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(Map.Entry entry) {
        entry.getClass();
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean addAll(Collection collection) {
        collection.getClass();
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.backing.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean containsAll(Collection collection) {
        collection.getClass();
        return this.backing.containsAllEntries$kotlin_stdlib(collection);
    }

    @Override // kotlin.collections.builders.AbstractMapBuilderEntrySet
    public boolean containsEntry(Map.Entry entry) {
        entry.getClass();
        return this.backing.containsEntry$kotlin_stdlib(entry);
    }

    @Override // kotlin.collections.AbstractMutableSet
    public int getSize() {
        return this.backing.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.backing.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator iterator() {
        return this.backing.entriesIterator$kotlin_stdlib();
    }

    @Override // kotlin.collections.builders.AbstractMapBuilderEntrySet
    public boolean remove(Map.Entry entry) {
        entry.getClass();
        return this.backing.removeEntry$kotlin_stdlib(entry);
    }

    @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean removeAll(Collection collection) {
        collection.getClass();
        this.backing.checkIsMutable$kotlin_stdlib();
        return super.removeAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean retainAll(Collection collection) {
        collection.getClass();
        this.backing.checkIsMutable$kotlin_stdlib();
        return super.retainAll(collection);
    }
}
