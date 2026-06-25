package h;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: h.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0136c implements Collection, Set {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final int[] f2571e = new int[0];

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final Object[] f2572f = new Object[0];

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static Object[] f2573g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public static int f2574h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public static Object[] f2575i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public static int f2576j;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int[] f2577a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object[] f2578b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2579c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public C0134a f2580d;

    public static void b(int[] iArr, Object[] objArr, int i2) {
        if (iArr.length == 8) {
            synchronized (C0136c.class) {
                try {
                    if (f2576j < 10) {
                        objArr[0] = f2575i;
                        objArr[1] = iArr;
                        for (int i3 = i2 - 1; i3 >= 2; i3--) {
                            objArr[i3] = null;
                        }
                        f2575i = objArr;
                        f2576j++;
                    }
                } finally {
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (C0136c.class) {
                try {
                    if (f2574h < 10) {
                        objArr[0] = f2573g;
                        objArr[1] = iArr;
                        for (int i4 = i2 - 1; i4 >= 2; i4--) {
                            objArr[i4] = null;
                        }
                        f2573g = objArr;
                        f2574h++;
                    }
                } finally {
                }
            }
        }
    }

    public final void a(int i2) {
        if (i2 == 8) {
            synchronized (C0136c.class) {
                try {
                    Object[] objArr = f2575i;
                    if (objArr != null) {
                        this.f2578b = objArr;
                        f2575i = (Object[]) objArr[0];
                        this.f2577a = (int[]) objArr[1];
                        objArr[1] = null;
                        objArr[0] = null;
                        f2576j--;
                        return;
                    }
                } finally {
                }
            }
        } else if (i2 == 4) {
            synchronized (C0136c.class) {
                try {
                    Object[] objArr2 = f2573g;
                    if (objArr2 != null) {
                        this.f2578b = objArr2;
                        f2573g = (Object[]) objArr2[0];
                        this.f2577a = (int[]) objArr2[1];
                        objArr2[1] = null;
                        objArr2[0] = null;
                        f2574h--;
                        return;
                    }
                } finally {
                }
            }
        }
        this.f2577a = new int[i2];
        this.f2578b = new Object[i2];
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        int i2;
        int iC;
        if (obj == null) {
            iC = d();
            i2 = 0;
        } else {
            int iHashCode = obj.hashCode();
            i2 = iHashCode;
            iC = c(iHashCode, obj);
        }
        if (iC >= 0) {
            return false;
        }
        int i3 = ~iC;
        int i4 = this.f2579c;
        int[] iArr = this.f2577a;
        if (i4 >= iArr.length) {
            int i5 = 8;
            if (i4 >= 8) {
                i5 = (i4 >> 1) + i4;
            } else if (i4 < 4) {
                i5 = 4;
            }
            Object[] objArr = this.f2578b;
            a(i5);
            int[] iArr2 = this.f2577a;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr, 0, this.f2578b, 0, objArr.length);
            }
            b(iArr, objArr, this.f2579c);
        }
        int i6 = this.f2579c;
        if (i3 < i6) {
            int[] iArr3 = this.f2577a;
            int i7 = i3 + 1;
            System.arraycopy(iArr3, i3, iArr3, i7, i6 - i3);
            Object[] objArr2 = this.f2578b;
            System.arraycopy(objArr2, i3, objArr2, i7, this.f2579c - i3);
        }
        this.f2577a[i3] = i2;
        this.f2578b[i3] = obj;
        this.f2579c++;
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean addAll(Collection collection) {
        int size = collection.size() + this.f2579c;
        int[] iArr = this.f2577a;
        boolean zAdd = false;
        if (iArr.length < size) {
            Object[] objArr = this.f2578b;
            a(size);
            int i2 = this.f2579c;
            if (i2 > 0) {
                System.arraycopy(iArr, 0, this.f2577a, 0, i2);
                System.arraycopy(objArr, 0, this.f2578b, 0, this.f2579c);
            }
            b(iArr, objArr, this.f2579c);
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            zAdd |= add(it.next());
        }
        return zAdd;
    }

    public final int c(int i2, Object obj) {
        int i3 = this.f2579c;
        if (i3 == 0) {
            return -1;
        }
        int iA = AbstractC0137d.a(this.f2577a, i3, i2);
        if (iA < 0 || obj.equals(this.f2578b[iA])) {
            return iA;
        }
        int i4 = iA + 1;
        while (i4 < i3 && this.f2577a[i4] == i2) {
            if (obj.equals(this.f2578b[i4])) {
                return i4;
            }
            i4++;
        }
        for (int i5 = iA - 1; i5 >= 0 && this.f2577a[i5] == i2; i5--) {
            if (obj.equals(this.f2578b[i5])) {
                return i5;
            }
        }
        return ~i4;
    }

    @Override // java.util.Collection, java.util.Set
    public final void clear() {
        int i2 = this.f2579c;
        if (i2 != 0) {
            b(this.f2577a, this.f2578b, i2);
            this.f2577a = f2571e;
            this.f2578b = f2572f;
            this.f2579c = 0;
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public final int d() {
        int i2 = this.f2579c;
        if (i2 == 0) {
            return -1;
        }
        int iA = AbstractC0137d.a(this.f2577a, i2, 0);
        if (iA < 0 || this.f2578b[iA] == null) {
            return iA;
        }
        int i3 = iA + 1;
        while (i3 < i2 && this.f2577a[i3] == 0) {
            if (this.f2578b[i3] == null) {
                return i3;
            }
            i3++;
        }
        for (int i4 = iA - 1; i4 >= 0 && this.f2577a[i4] == 0; i4--) {
            if (this.f2578b[i4] == null) {
                return i4;
            }
        }
        return ~i3;
    }

    public final void e(int i2) {
        Object[] objArr = this.f2578b;
        Object obj = objArr[i2];
        int i3 = this.f2579c;
        if (i3 <= 1) {
            b(this.f2577a, objArr, i3);
            this.f2577a = f2571e;
            this.f2578b = f2572f;
            this.f2579c = 0;
            return;
        }
        int[] iArr = this.f2577a;
        if (iArr.length <= 8 || i3 >= iArr.length / 3) {
            int i4 = i3 - 1;
            this.f2579c = i4;
            if (i2 < i4) {
                int i5 = i2 + 1;
                System.arraycopy(iArr, i5, iArr, i2, i4 - i2);
                Object[] objArr2 = this.f2578b;
                System.arraycopy(objArr2, i5, objArr2, i2, this.f2579c - i2);
            }
            this.f2578b[this.f2579c] = null;
            return;
        }
        a(i3 > 8 ? i3 + (i3 >> 1) : 8);
        this.f2579c--;
        if (i2 > 0) {
            System.arraycopy(iArr, 0, this.f2577a, 0, i2);
            System.arraycopy(objArr, 0, this.f2578b, 0, i2);
        }
        int i6 = this.f2579c;
        if (i2 < i6) {
            int i7 = i2 + 1;
            System.arraycopy(iArr, i7, this.f2577a, i2, i6 - i2);
            System.arraycopy(objArr, i7, this.f2578b, i2, this.f2579c - i2);
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            if (this.f2579c != set.size()) {
                return false;
            }
            for (int i2 = 0; i2 < this.f2579c; i2++) {
                try {
                    if (!set.contains(this.f2578b[i2])) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        int[] iArr = this.f2577a;
        int i2 = this.f2579c;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 += iArr[i4];
        }
        return i3;
    }

    public final int indexOf(Object obj) {
        return obj == null ? d() : c(obj.hashCode(), obj);
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.f2579c <= 0;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        if (this.f2580d == null) {
            this.f2580d = new C0134a(1, this);
        }
        C0134a c0134a = this.f2580d;
        if (c0134a.f2566b == null) {
            c0134a.f2566b = new h(c0134a, 1);
        }
        return c0134a.f2566b.iterator();
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf < 0) {
            return false;
        }
        e(iIndexOf);
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean removeAll(Collection collection) {
        Iterator it = collection.iterator();
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean retainAll(Collection collection) {
        boolean z2 = false;
        for (int i2 = this.f2579c - 1; i2 >= 0; i2--) {
            if (!collection.contains(this.f2578b[i2])) {
                e(i2);
                z2 = true;
            }
        }
        return z2;
    }

    @Override // java.util.Collection, java.util.Set
    public final int size() {
        return this.f2579c;
    }

    @Override // java.util.Collection, java.util.Set
    public final Object[] toArray() {
        int i2 = this.f2579c;
        Object[] objArr = new Object[i2];
        System.arraycopy(this.f2578b, 0, objArr, 0, i2);
        return objArr;
    }

    @Override // java.util.Collection, java.util.Set
    public final Object[] toArray(Object[] objArr) {
        if (objArr.length < this.f2579c) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), this.f2579c);
        }
        System.arraycopy(this.f2578b, 0, objArr, 0, this.f2579c);
        int length = objArr.length;
        int i2 = this.f2579c;
        if (length > i2) {
            objArr[i2] = null;
        }
        return objArr;
    }

    public final String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f2579c * 14);
        sb.append('{');
        for (int i2 = 0; i2 < this.f2579c; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            Object obj = this.f2578b[i2];
            if (obj != this) {
                sb.append(obj);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
