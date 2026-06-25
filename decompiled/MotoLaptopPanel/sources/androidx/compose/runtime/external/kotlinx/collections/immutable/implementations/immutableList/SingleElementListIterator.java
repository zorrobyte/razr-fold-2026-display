package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

/* JADX INFO: compiled from: AbstractListIterator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SingleElementListIterator extends AbstractListIterator {
    private final Object element;

    public SingleElementListIterator(Object obj, int i) {
        super(i, 1);
        this.element = obj;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public Object next() {
        checkHasNext$runtime_release();
        setIndex(getIndex() + 1);
        return this.element;
    }

    @Override // java.util.ListIterator
    public Object previous() {
        checkHasPrevious$runtime_release();
        setIndex(getIndex() - 1);
        return this.element;
    }
}
