package androidx.compose.runtime.snapshots;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import kotlin.KotlinNothingValueException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: SnapshotStateList.kt */
/* JADX INFO: loaded from: classes.dex */
final class StateListIterator implements ListIterator, KMappedMarker {
    private int index;
    private int lastRequested = -1;
    private final SnapshotStateList list;
    private int structure;

    public StateListIterator(SnapshotStateList snapshotStateList, int i) {
        this.list = snapshotStateList;
        this.index = i - 1;
        this.structure = snapshotStateList.getStructure$runtime_release();
    }

    private final void validateModification() {
        if (this.list.getStructure$runtime_release() != this.structure) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.ListIterator
    public void add(Object obj) {
        validateModification();
        this.list.add(this.index + 1, obj);
        this.lastRequested = -1;
        this.index++;
        this.structure = this.list.getStructure$runtime_release();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public boolean hasNext() {
        return this.index < this.list.size() - 1;
    }

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        return this.index >= 0;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public Object next() {
        validateModification();
        int i = this.index + 1;
        this.lastRequested = i;
        SnapshotStateListKt.validateRange(i, this.list.size());
        Object obj = this.list.get(i);
        this.index = i;
        return obj;
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return this.index + 1;
    }

    @Override // java.util.ListIterator
    public Object previous() {
        validateModification();
        SnapshotStateListKt.validateRange(this.index, this.list.size());
        int i = this.index;
        this.lastRequested = i;
        this.index--;
        return this.list.get(i);
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return this.index;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public void remove() {
        validateModification();
        this.list.remove(this.index);
        this.index--;
        this.lastRequested = -1;
        this.structure = this.list.getStructure$runtime_release();
    }

    @Override // java.util.ListIterator
    public void set(Object obj) {
        validateModification();
        int i = this.lastRequested;
        if (i < 0) {
            SnapshotStateListKt.invalidIteratorSet();
            throw new KotlinNothingValueException();
        }
        this.list.set(i, obj);
        this.structure = this.list.getStructure$runtime_release();
    }
}
