package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: PersistentVectorIterator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentVectorIterator extends AbstractListIterator {
    private final Object[] tail;
    private final TrieIterator trieIterator;

    public PersistentVectorIterator(Object[] objArr, Object[] objArr2, int i, int i2, int i3) {
        super(i, i2);
        this.tail = objArr2;
        int iRootSize = UtilsKt.rootSize(i2);
        this.trieIterator = new TrieIterator(objArr, RangesKt.coerceAtMost(i, iRootSize), iRootSize, i3);
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public Object next() {
        checkHasNext$runtime_release();
        if (this.trieIterator.hasNext()) {
            setIndex(getIndex() + 1);
            return this.trieIterator.next();
        }
        Object[] objArr = this.tail;
        int index = getIndex();
        setIndex(index + 1);
        return objArr[index - this.trieIterator.getSize()];
    }

    @Override // java.util.ListIterator
    public Object previous() {
        checkHasPrevious$runtime_release();
        if (getIndex() <= this.trieIterator.getSize()) {
            setIndex(getIndex() - 1);
            return this.trieIterator.previous();
        }
        Object[] objArr = this.tail;
        setIndex(getIndex() - 1);
        return objArr[getIndex() - this.trieIterator.getSize()];
    }
}
