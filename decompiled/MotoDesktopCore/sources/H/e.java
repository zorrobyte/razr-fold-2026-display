package h;

/* JADX INFO: loaded from: classes.dex */
public final class e implements Cloneable {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final Object f2583e = new Object();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f2584a = false;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public long[] f2585b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object[] f2586c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f2587d;

    public e() {
        int i2;
        int i3 = 4;
        while (true) {
            i2 = 80;
            if (i3 >= 32) {
                break;
            }
            int i4 = (1 << i3) - 12;
            if (80 <= i4) {
                i2 = i4;
                break;
            }
            i3++;
        }
        int i5 = i2 / 8;
        this.f2585b = new long[i5];
        this.f2586c = new Object[i5];
        this.f2587d = 0;
    }

    public final void a() {
        int i2 = this.f2587d;
        Object[] objArr = this.f2586c;
        for (int i3 = 0; i3 < i2; i3++) {
            objArr[i3] = null;
        }
        this.f2587d = 0;
        this.f2584a = false;
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final e clone() {
        try {
            e eVar = (e) super.clone();
            eVar.f2585b = (long[]) this.f2585b.clone();
            eVar.f2586c = (Object[]) this.f2586c.clone();
            return eVar;
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    public final void c() {
        int i2 = this.f2587d;
        long[] jArr = this.f2585b;
        Object[] objArr = this.f2586c;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            Object obj = objArr[i4];
            if (obj != f2583e) {
                if (i4 != i3) {
                    jArr[i3] = jArr[i4];
                    objArr[i3] = obj;
                    objArr[i4] = null;
                }
                i3++;
            }
        }
        this.f2584a = false;
        this.f2587d = i3;
    }

    public final Object d(long j2, Long l2) {
        Object obj;
        int iB = AbstractC0137d.b(this.f2585b, this.f2587d, j2);
        return (iB < 0 || (obj = this.f2586c[iB]) == f2583e) ? l2 : obj;
    }

    public final void e(long j2, Object obj) {
        int iB = AbstractC0137d.b(this.f2585b, this.f2587d, j2);
        if (iB >= 0) {
            this.f2586c[iB] = obj;
            return;
        }
        int i2 = ~iB;
        int i3 = this.f2587d;
        if (i2 < i3) {
            Object[] objArr = this.f2586c;
            if (objArr[i2] == f2583e) {
                this.f2585b[i2] = j2;
                objArr[i2] = obj;
                return;
            }
        }
        if (this.f2584a && i3 >= this.f2585b.length) {
            c();
            i2 = ~AbstractC0137d.b(this.f2585b, this.f2587d, j2);
        }
        int i4 = this.f2587d;
        if (i4 >= this.f2585b.length) {
            int i5 = (i4 + 1) * 8;
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
            int i8 = i5 / 8;
            long[] jArr = new long[i8];
            Object[] objArr2 = new Object[i8];
            long[] jArr2 = this.f2585b;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr3 = this.f2586c;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f2585b = jArr;
            this.f2586c = objArr2;
        }
        int i9 = this.f2587d - i2;
        if (i9 != 0) {
            long[] jArr3 = this.f2585b;
            int i10 = i2 + 1;
            System.arraycopy(jArr3, i2, jArr3, i10, i9);
            Object[] objArr4 = this.f2586c;
            System.arraycopy(objArr4, i2, objArr4, i10, this.f2587d - i2);
        }
        this.f2585b[i2] = j2;
        this.f2586c[i2] = obj;
        this.f2587d++;
    }

    public final int f() {
        if (this.f2584a) {
            c();
        }
        return this.f2587d;
    }

    public final Object g(int i2) {
        if (this.f2584a) {
            c();
        }
        return this.f2586c[i2];
    }

    public final String toString() {
        if (f() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f2587d * 28);
        sb.append('{');
        for (int i2 = 0; i2 < this.f2587d; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            if (this.f2584a) {
                c();
            }
            sb.append(this.f2585b[i2]);
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
