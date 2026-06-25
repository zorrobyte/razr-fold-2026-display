package h;

import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class j implements Collection {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0134a f2604a;

    public j(C0134a c0134a) {
        this.f2604a = c0134a;
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final void clear() {
        this.f2604a.a();
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        return this.f2604a.f(obj) >= 0;
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.f2604a.d() == 0;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new g(this.f2604a, 1);
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        C0134a c0134a = this.f2604a;
        int iF = c0134a.f(obj);
        if (iF < 0) {
            return false;
        }
        c0134a.h(iF);
        return true;
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        C0134a c0134a = this.f2604a;
        int iD = c0134a.d();
        int i2 = 0;
        boolean z2 = false;
        while (i2 < iD) {
            if (collection.contains(c0134a.b(i2, 1))) {
                c0134a.h(i2);
                i2--;
                iD--;
                z2 = true;
            }
            i2++;
        }
        return z2;
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        C0134a c0134a = this.f2604a;
        int iD = c0134a.d();
        int i2 = 0;
        boolean z2 = false;
        while (i2 < iD) {
            if (!collection.contains(c0134a.b(i2, 1))) {
                c0134a.h(i2);
                i2--;
                iD--;
                z2 = true;
            }
            i2++;
        }
        return z2;
    }

    @Override // java.util.Collection
    public final int size() {
        return this.f2604a.d();
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        C0134a c0134a = this.f2604a;
        int iD = c0134a.d();
        Object[] objArr = new Object[iD];
        for (int i2 = 0; i2 < iD; i2++) {
            objArr[i2] = c0134a.b(i2, 1);
        }
        return objArr;
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return this.f2604a.l(1, objArr);
    }
}
