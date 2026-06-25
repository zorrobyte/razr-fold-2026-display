package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: BufferIterator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BufferIterator extends AbstractListIterator {
    private final Object[] buffer;

    public BufferIterator(Object[] objArr, int i, int i2) {
        super(i, i2);
        this.buffer = objArr;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object[] objArr = this.buffer;
        int index = getIndex();
        setIndex(index + 1);
        return objArr[index];
    }

    @Override // java.util.ListIterator
    public Object previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        Object[] objArr = this.buffer;
        setIndex(getIndex() - 1);
        return objArr[getIndex()];
    }
}
