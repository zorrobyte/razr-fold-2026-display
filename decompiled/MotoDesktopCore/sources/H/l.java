package h;

/* JADX INFO: loaded from: classes.dex */
public final class l implements Cloneable {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final Object f2612e = new Object();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f2613a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int[] f2614b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object[] f2615c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f2616d;

    public l() {
        this(10);
    }

    public l(int i2) {
        this.f2613a = false;
        if (i2 == 0) {
            this.f2614b = AbstractC0137d.f2581a;
            this.f2615c = AbstractC0137d.f2582b;
        } else {
            int i3 = i2 * 4;
            int i4 = 4;
            while (true) {
                if (i4 >= 32) {
                    break;
                }
                int i5 = (1 << i4) - 12;
                if (i3 <= i5) {
                    i3 = i5;
                    break;
                }
                i4++;
            }
            int i6 = i3 / 4;
            this.f2614b = new int[i6];
            this.f2615c = new Object[i6];
        }
        this.f2616d = 0;
    }

    public final void a(int i2, Object obj) {
        int i3 = this.f2616d;
        if (i3 != 0 && i2 <= this.f2614b[i3 - 1]) {
            e(i2, obj);
            return;
        }
        if (this.f2613a && i3 >= this.f2614b.length) {
            c();
        }
        int i4 = this.f2616d;
        if (i4 >= this.f2614b.length) {
            int i5 = (i4 + 1) * 4;
            int i6 = 4;
            while (true) {
                if (i6 >= 32) {
                    break;
                }
                int i7 = (1 << i6) - 12;
                if (i5 <= i7) {
                    i5 = i7;
                    break;
                }
                i6++;
            }
            int i8 = i5 / 4;
            int[] iArr = new int[i8];
            Object[] objArr = new Object[i8];
            int[] iArr2 = this.f2614b;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr2 = this.f2615c;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.f2614b = iArr;
            this.f2615c = objArr;
        }
        this.f2614b[i4] = i2;
        this.f2615c[i4] = obj;
        this.f2616d = i4 + 1;
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final l clone() {
        try {
            l lVar = (l) super.clone();
            lVar.f2614b = (int[]) this.f2614b.clone();
            lVar.f2615c = (Object[]) this.f2615c.clone();
            return lVar;
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    public final void c() {
        int i2 = this.f2616d;
        int[] iArr = this.f2614b;
        Object[] objArr = this.f2615c;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            Object obj = objArr[i4];
            if (obj != f2612e) {
                if (i4 != i3) {
                    iArr[i3] = iArr[i4];
                    objArr[i3] = obj;
                    objArr[i4] = null;
                }
                i3++;
            }
        }
        this.f2613a = false;
        this.f2616d = i3;
    }

    public final Object d(int i2, Integer num) {
        Object obj;
        int iA = AbstractC0137d.a(this.f2614b, this.f2616d, i2);
        return (iA < 0 || (obj = this.f2615c[iA]) == f2612e) ? num : obj;
    }

    public final void e(int i2, Object obj) {
        int iA = AbstractC0137d.a(this.f2614b, this.f2616d, i2);
        if (iA >= 0) {
            this.f2615c[iA] = obj;
            return;
        }
        int i3 = ~iA;
        int i4 = this.f2616d;
        if (i3 < i4) {
            Object[] objArr = this.f2615c;
            if (objArr[i3] == f2612e) {
                this.f2614b[i3] = i2;
                objArr[i3] = obj;
                return;
            }
        }
        if (this.f2613a && i4 >= this.f2614b.length) {
            c();
            i3 = ~AbstractC0137d.a(this.f2614b, this.f2616d, i2);
        }
        int i5 = this.f2616d;
        if (i5 >= this.f2614b.length) {
            int i6 = (i5 + 1) * 4;
            int i7 = 4;
            while (true) {
                if (i7 >= 32) {
                    break;
                }
                int i8 = (1 << i7) - 12;
                if (i6 <= i8) {
                    i6 = i8;
                    break;
                }
                i7++;
            }
            int i9 = i6 / 4;
            int[] iArr = new int[i9];
            Object[] objArr2 = new Object[i9];
            int[] iArr2 = this.f2614b;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.f2615c;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f2614b = iArr;
            this.f2615c = objArr2;
        }
        int i10 = this.f2616d - i3;
        if (i10 != 0) {
            int[] iArr3 = this.f2614b;
            int i11 = i3 + 1;
            System.arraycopy(iArr3, i3, iArr3, i11, i10);
            Object[] objArr4 = this.f2615c;
            System.arraycopy(objArr4, i3, objArr4, i11, this.f2616d - i3);
        }
        this.f2614b[i3] = i2;
        this.f2615c[i3] = obj;
        this.f2616d++;
    }

    public final int f() {
        if (this.f2613a) {
            c();
        }
        return this.f2616d;
    }

    public final Object g(int i2) {
        if (this.f2613a) {
            c();
        }
        return this.f2615c[i2];
    }

    public final String toString() {
        if (f() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f2616d * 28);
        sb.append('{');
        for (int i2 = 0; i2 < this.f2616d; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            if (this.f2613a) {
                c();
            }
            sb.append(this.f2614b[i2]);
            sb.append('=');
            Object objG = g(i2);
            if (objG != this) {
                sb.append(objG);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
