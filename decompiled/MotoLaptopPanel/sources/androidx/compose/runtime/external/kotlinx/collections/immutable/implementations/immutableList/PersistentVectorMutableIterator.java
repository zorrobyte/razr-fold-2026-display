package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: PersistentVectorMutableIterator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentVectorMutableIterator extends AbstractListIterator implements ListIterator, KMappedMarker {
    private final PersistentVectorBuilder builder;
    private int expectedModCount;
    private int lastIteratedIndex;
    private TrieIterator trieIterator;

    public PersistentVectorMutableIterator(PersistentVectorBuilder persistentVectorBuilder, int i) {
        super(i, persistentVectorBuilder.size());
        this.builder = persistentVectorBuilder;
        this.expectedModCount = persistentVectorBuilder.getModCount$runtime_release();
        this.lastIteratedIndex = -1;
        setupTrieIterator();
    }

    private final void checkForComodification() {
        if (this.expectedModCount != this.builder.getModCount$runtime_release()) {
            throw new ConcurrentModificationException();
        }
    }

    private final void checkHasIterated() {
        if (this.lastIteratedIndex == -1) {
            throw new IllegalStateException();
        }
    }

    private final void reset() {
        setSize(this.builder.size());
        this.expectedModCount = this.builder.getModCount$runtime_release();
        this.lastIteratedIndex = -1;
        setupTrieIterator();
    }

    private final void setupTrieIterator() {
        Object[] root$runtime_release = this.builder.getRoot$runtime_release();
        if (root$runtime_release == null) {
            this.trieIterator = null;
            return;
        }
        int iRootSize = UtilsKt.rootSize(this.builder.size());
        int iCoerceAtMost = RangesKt.coerceAtMost(getIndex(), iRootSize);
        int rootShift$runtime_release = (this.builder.getRootShift$runtime_release() / 5) + 1;
        TrieIterator trieIterator = this.trieIterator;
        if (trieIterator == null) {
            this.trieIterator = new TrieIterator(root$runtime_release, iCoerceAtMost, iRootSize, rootShift$runtime_release);
        } else {
            trieIterator.getClass();
            trieIterator.reset$runtime_release(root$runtime_release, iCoerceAtMost, iRootSize, rootShift$runtime_release);
        }
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractListIterator, java.util.ListIterator
    public void add(Object obj) {
        checkForComodification();
        this.builder.add(getIndex(), obj);
        setIndex(getIndex() + 1);
        reset();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public Object next() {
        checkForComodification();
        checkHasNext$runtime_release();
        this.lastIteratedIndex = getIndex();
        TrieIterator trieIterator = this.trieIterator;
        if (trieIterator == null) {
            Object[] tail$runtime_release = this.builder.getTail$runtime_release();
            int index = getIndex();
            setIndex(index + 1);
            return tail$runtime_release[index];
        }
        if (trieIterator.hasNext()) {
            setIndex(getIndex() + 1);
            return trieIterator.next();
        }
        Object[] tail$runtime_release2 = this.builder.getTail$runtime_release();
        int index2 = getIndex();
        setIndex(index2 + 1);
        return tail$runtime_release2[index2 - trieIterator.getSize()];
    }

    @Override // java.util.ListIterator
    public Object previous() {
        checkForComodification();
        checkHasPrevious$runtime_release();
        this.lastIteratedIndex = getIndex() - 1;
        TrieIterator trieIterator = this.trieIterator;
        if (trieIterator == null) {
            Object[] tail$runtime_release = this.builder.getTail$runtime_release();
            setIndex(getIndex() - 1);
            return tail$runtime_release[getIndex()];
        }
        if (getIndex() <= trieIterator.getSize()) {
            setIndex(getIndex() - 1);
            return trieIterator.previous();
        }
        Object[] tail$runtime_release2 = this.builder.getTail$runtime_release();
        setIndex(getIndex() - 1);
        return tail$runtime_release2[getIndex() - trieIterator.getSize()];
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractListIterator, java.util.ListIterator, java.util.Iterator
    public void remove() {
        checkForComodification();
        checkHasIterated();
        this.builder.remove(this.lastIteratedIndex);
        if (this.lastIteratedIndex < getIndex()) {
            setIndex(this.lastIteratedIndex);
        }
        reset();
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractListIterator, java.util.ListIterator
    public void set(Object obj) {
        checkForComodification();
        checkHasIterated();
        this.builder.set(this.lastIteratedIndex, obj);
        this.expectedModCount = this.builder.getModCount$runtime_release();
        setupTrieIterator();
    }
}
