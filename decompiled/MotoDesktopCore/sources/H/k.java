package h;

import java.util.ConcurrentModificationException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class k {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static Object[] f2605d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static int f2606e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static Object[] f2607f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static int f2608g;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int[] f2609a = AbstractC0137d.f2581a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object[] f2610b = AbstractC0137d.f2582b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2611c = 0;

    public static void c(int[] iArr, Object[] objArr, int i2) {
        if (iArr.length == 8) {
            synchronized (C0135b.class) {
                try {
                    if (f2608g < 10) {
                        objArr[0] = f2607f;
                        objArr[1] = iArr;
                        for (int i3 = (i2 << 1) - 1; i3 >= 2; i3--) {
                            objArr[i3] = null;
                        }
                        f2607f = objArr;
                        f2608g++;
                    }
                } finally {
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (C0135b.class) {
                try {
                    if (f2606e < 10) {
                        objArr[0] = f2605d;
                        objArr[1] = iArr;
                        for (int i4 = (i2 << 1) - 1; i4 >= 2; i4--) {
                            objArr[i4] = null;
                        }
                        f2605d = objArr;
                        f2606e++;
                    }
                } finally {
                }
            }
        }
    }

    public final void a(int i2) {
        if (i2 == 8) {
            synchronized (C0135b.class) {
                try {
                    Object[] objArr = f2607f;
                    if (objArr != null) {
                        this.f2610b = objArr;
                        f2607f = (Object[]) objArr[0];
                        this.f2609a = (int[]) objArr[1];
                        objArr[1] = null;
                        objArr[0] = null;
                        f2608g--;
                        return;
                    }
                } finally {
                }
            }
        } else if (i2 == 4) {
            synchronized (C0135b.class) {
                try {
                    Object[] objArr2 = f2605d;
                    if (objArr2 != null) {
                        this.f2610b = objArr2;
                        f2605d = (Object[]) objArr2[0];
                        this.f2609a = (int[]) objArr2[1];
                        objArr2[1] = null;
                        objArr2[0] = null;
                        f2606e--;
                        return;
                    }
                } finally {
                }
            }
        }
        this.f2609a = new int[i2];
        this.f2610b = new Object[i2 << 1];
    }

    public final void b(int i2) {
        int i3 = this.f2611c;
        int[] iArr = this.f2609a;
        if (iArr.length < i2) {
            Object[] objArr = this.f2610b;
            a(i2);
            if (this.f2611c > 0) {
                System.arraycopy(iArr, 0, this.f2609a, 0, i3);
                System.arraycopy(objArr, 0, this.f2610b, 0, i3 << 1);
            }
            c(iArr, objArr, i3);
        }
        if (this.f2611c != i3) {
            throw new ConcurrentModificationException();
        }
    }

    public final void clear() {
        int i2 = this.f2611c;
        if (i2 > 0) {
            int[] iArr = this.f2609a;
            Object[] objArr = this.f2610b;
            this.f2609a = AbstractC0137d.f2581a;
            this.f2610b = AbstractC0137d.f2582b;
            this.f2611c = 0;
            c(iArr, objArr, i2);
        }
        if (this.f2611c > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public final boolean containsKey(Object obj) {
        return e(obj) >= 0;
    }

    public final boolean containsValue(Object obj) {
        return g(obj) >= 0;
    }

    public final int d(int i2, Object obj) {
        int i3 = this.f2611c;
        if (i3 == 0) {
            return -1;
        }
        try {
            int iA = AbstractC0137d.a(this.f2609a, i3, i2);
            if (iA < 0 || obj.equals(this.f2610b[iA << 1])) {
                return iA;
            }
            int i4 = iA + 1;
            while (i4 < i3 && this.f2609a[i4] == i2) {
                if (obj.equals(this.f2610b[i4 << 1])) {
                    return i4;
                }
                i4++;
            }
            for (int i5 = iA - 1; i5 >= 0 && this.f2609a[i5] == i2; i5--) {
                if (obj.equals(this.f2610b[i5 << 1])) {
                    return i5;
                }
            }
            return ~i4;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    public final int e(Object obj) {
        return obj == null ? f() : d(obj.hashCode(), obj);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof k) {
            k kVar = (k) obj;
            if (this.f2611c != kVar.f2611c) {
                return false;
            }
            for (int i2 = 0; i2 < this.f2611c; i2++) {
                try {
                    Object objH = h(i2);
                    Object objJ = j(i2);
                    Object obj2 = kVar.get(objH);
                    if (objJ == null) {
                        if (obj2 != null || !kVar.containsKey(objH)) {
                            return false;
                        }
                    } else if (!objJ.equals(obj2)) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                    return false;
                }
            }
            return true;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (this.f2611c != map.size()) {
                return false;
            }
            for (int i3 = 0; i3 < this.f2611c; i3++) {
                try {
                    Object objH2 = h(i3);
                    Object objJ2 = j(i3);
                    Object obj3 = map.get(objH2);
                    if (objJ2 == null) {
                        if (obj3 != null || !map.containsKey(objH2)) {
                            return false;
                        }
                    } else if (!objJ2.equals(obj3)) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused2) {
                }
            }
            return true;
        }
        return false;
    }

    public final int f() {
        int i2 = this.f2611c;
        if (i2 == 0) {
            return -1;
        }
        try {
            int iA = AbstractC0137d.a(this.f2609a, i2, 0);
            if (iA < 0 || this.f2610b[iA << 1] == null) {
                return iA;
            }
            int i3 = iA + 1;
            while (i3 < i2 && this.f2609a[i3] == 0) {
                if (this.f2610b[i3 << 1] == null) {
                    return i3;
                }
                i3++;
            }
            for (int i4 = iA - 1; i4 >= 0 && this.f2609a[i4] == 0; i4--) {
                if (this.f2610b[i4 << 1] == null) {
                    return i4;
                }
            }
            return ~i3;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    public final int g(Object obj) {
        int i2 = this.f2611c * 2;
        Object[] objArr = this.f2610b;
        if (obj == null) {
            for (int i3 = 1; i3 < i2; i3 += 2) {
                if (objArr[i3] == null) {
                    return i3 >> 1;
                }
            }
            return -1;
        }
        for (int i4 = 1; i4 < i2; i4 += 2) {
            if (obj.equals(objArr[i4])) {
                return i4 >> 1;
            }
        }
        return -1;
    }

    public final Object get(Object obj) {
        int iE = e(obj);
        if (iE >= 0) {
            return this.f2610b[(iE << 1) + 1];
        }
        return null;
    }

    public final Object h(int i2) {
        return this.f2610b[i2 << 1];
    }

    public final int hashCode() {
        int[] iArr = this.f2609a;
        Object[] objArr = this.f2610b;
        int i2 = this.f2611c;
        int i3 = 1;
        int i4 = 0;
        int iHashCode = 0;
        while (i4 < i2) {
            Object obj = objArr[i3];
            iHashCode += (obj == null ? 0 : obj.hashCode()) ^ iArr[i4];
            i4++;
            i3 += 2;
        }
        return iHashCode;
    }

    public final Object i(int i2) {
        Object[] objArr = this.f2610b;
        int i3 = i2 << 1;
        Object obj = objArr[i3 + 1];
        int i4 = this.f2611c;
        int i5 = 0;
        if (i4 <= 1) {
            c(this.f2609a, objArr, i4);
            this.f2609a = AbstractC0137d.f2581a;
            this.f2610b = AbstractC0137d.f2582b;
        } else {
            int i6 = i4 - 1;
            int[] iArr = this.f2609a;
            if (iArr.length <= 8 || i4 >= iArr.length / 3) {
                if (i2 < i6) {
                    int i7 = i2 + 1;
                    int i8 = i6 - i2;
                    System.arraycopy(iArr, i7, iArr, i2, i8);
                    Object[] objArr2 = this.f2610b;
                    System.arraycopy(objArr2, i7 << 1, objArr2, i3, i8 << 1);
                }
                Object[] objArr3 = this.f2610b;
                int i9 = i6 << 1;
                objArr3[i9] = null;
                objArr3[i9 + 1] = null;
            } else {
                a(i4 > 8 ? i4 + (i4 >> 1) : 8);
                if (i4 != this.f2611c) {
                    throw new ConcurrentModificationException();
                }
                if (i2 > 0) {
                    System.arraycopy(iArr, 0, this.f2609a, 0, i2);
                    System.arraycopy(objArr, 0, this.f2610b, 0, i3);
                }
                if (i2 < i6) {
                    int i10 = i2 + 1;
                    int i11 = i6 - i2;
                    System.arraycopy(iArr, i10, this.f2609a, i2, i11);
                    System.arraycopy(objArr, i10 << 1, this.f2610b, i3, i11 << 1);
                }
            }
            i5 = i6;
        }
        if (i4 != this.f2611c) {
            throw new ConcurrentModificationException();
        }
        this.f2611c = i5;
        return obj;
    }

    public final boolean isEmpty() {
        return this.f2611c <= 0;
    }

    public final Object j(int i2) {
        return this.f2610b[(i2 << 1) + 1];
    }

    public final Object put(Object obj, Object obj2) {
        int i2;
        int iD;
        int i3 = this.f2611c;
        if (obj == null) {
            iD = f();
            i2 = 0;
        } else {
            int iHashCode = obj.hashCode();
            i2 = iHashCode;
            iD = d(iHashCode, obj);
        }
        if (iD >= 0) {
            int i4 = (iD << 1) + 1;
            Object[] objArr = this.f2610b;
            Object obj3 = objArr[i4];
            objArr[i4] = obj2;
            return obj3;
        }
        int i5 = ~iD;
        int[] iArr = this.f2609a;
        if (i3 >= iArr.length) {
            int i6 = 8;
            if (i3 >= 8) {
                i6 = (i3 >> 1) + i3;
            } else if (i3 < 4) {
                i6 = 4;
            }
            Object[] objArr2 = this.f2610b;
            a(i6);
            if (i3 != this.f2611c) {
                throw new ConcurrentModificationException();
            }
            int[] iArr2 = this.f2609a;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr2, 0, this.f2610b, 0, objArr2.length);
            }
            c(iArr, objArr2, i3);
        }
        if (i5 < i3) {
            int[] iArr3 = this.f2609a;
            int i7 = i5 + 1;
            System.arraycopy(iArr3, i5, iArr3, i7, i3 - i5);
            Object[] objArr3 = this.f2610b;
            System.arraycopy(objArr3, i5 << 1, objArr3, i7 << 1, (this.f2611c - i5) << 1);
        }
        int i8 = this.f2611c;
        if (i3 == i8) {
            int[] iArr4 = this.f2609a;
            if (i5 < iArr4.length) {
                iArr4[i5] = i2;
                Object[] objArr4 = this.f2610b;
                int i9 = i5 << 1;
                objArr4[i9] = obj;
                objArr4[i9 + 1] = obj2;
                this.f2611c = i8 + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    public final Object remove(Object obj) {
        int iE = e(obj);
        if (iE >= 0) {
            return i(iE);
        }
        return null;
    }

    public final int size() {
        return this.f2611c;
    }

    public final String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f2611c * 28);
        sb.append('{');
        for (int i2 = 0; i2 < this.f2611c; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            Object objH = h(i2);
            if (objH != this) {
                sb.append(objH);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            Object objJ = j(i2);
            if (objJ != this) {
                sb.append(objJ);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
