package h;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class h implements Set {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2598a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0134a f2599b;

    public /* synthetic */ h(C0134a c0134a, int i2) {
        this.f2598a = i2;
        this.f2599b = c0134a;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean add(Object obj) {
        switch (this.f2598a) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean addAll(Collection collection) {
        switch (this.f2598a) {
            case 0:
                C0134a c0134a = this.f2599b;
                int iD = c0134a.d();
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    c0134a.g(entry.getKey(), entry.getValue());
                }
                return iD != c0134a.d();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final void clear() {
        switch (this.f2598a) {
            case 0:
                this.f2599b.a();
                break;
            default:
                this.f2599b.a();
                break;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
        switch (this.f2598a) {
            case 0:
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                C0134a c0134a = this.f2599b;
                int iE = c0134a.e(key);
                if (iE < 0) {
                    return false;
                }
                Object objB = c0134a.b(iE, 1);
                Object value = entry.getValue();
                return objB == value || (objB != null && objB.equals(value));
            default:
                return this.f2599b.e(obj) >= 0;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean containsAll(Collection collection) {
        switch (this.f2598a) {
            case 0:
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    if (!contains(it.next())) {
                        break;
                    }
                }
                break;
            default:
                Map mapC = this.f2599b.c();
                Iterator it2 = collection.iterator();
                while (it2.hasNext()) {
                    if (!mapC.containsKey(it2.next())) {
                        break;
                    }
                }
                break;
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean equals(Object obj) {
        switch (this.f2598a) {
        }
        return C0134a.j(this, obj);
    }

    @Override // java.util.Set, java.util.Collection
    public final int hashCode() {
        switch (this.f2598a) {
            case 0:
                C0134a c0134a = this.f2599b;
                int iHashCode = 0;
                for (int iD = c0134a.d() - 1; iD >= 0; iD--) {
                    Object objB = c0134a.b(iD, 0);
                    Object objB2 = c0134a.b(iD, 1);
                    iHashCode += (objB == null ? 0 : objB.hashCode()) ^ (objB2 == null ? 0 : objB2.hashCode());
                }
                return iHashCode;
            default:
                C0134a c0134a2 = this.f2599b;
                int iHashCode2 = 0;
                for (int iD2 = c0134a2.d() - 1; iD2 >= 0; iD2--) {
                    Object objB3 = c0134a2.b(iD2, 0);
                    iHashCode2 += objB3 == null ? 0 : objB3.hashCode();
                }
                return iHashCode2;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        switch (this.f2598a) {
            case 0:
                if (this.f2599b.d() == 0) {
                }
                break;
            default:
                if (this.f2599b.d() == 0) {
                }
                break;
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        switch (this.f2598a) {
            case 0:
                return new i(this.f2599b);
            default:
                return new g(this.f2599b, 0);
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        switch (this.f2598a) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                C0134a c0134a = this.f2599b;
                int iE = c0134a.e(obj);
                if (iE < 0) {
                    return false;
                }
                c0134a.h(iE);
                return true;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean removeAll(Collection collection) {
        switch (this.f2598a) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                Map mapC = this.f2599b.c();
                int size = mapC.size();
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    mapC.remove(it.next());
                }
                return size != mapC.size();
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean retainAll(Collection collection) {
        switch (this.f2598a) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                return C0134a.k(this.f2599b.c(), collection);
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final int size() {
        switch (this.f2598a) {
        }
        return this.f2599b.d();
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray() {
        switch (this.f2598a) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                C0134a c0134a = this.f2599b;
                int iD = c0134a.d();
                Object[] objArr = new Object[iD];
                for (int i2 = 0; i2 < iD; i2++) {
                    objArr[i2] = c0134a.b(i2, 0);
                }
                return objArr;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        switch (this.f2598a) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                return this.f2599b.l(0, objArr);
        }
    }
}
