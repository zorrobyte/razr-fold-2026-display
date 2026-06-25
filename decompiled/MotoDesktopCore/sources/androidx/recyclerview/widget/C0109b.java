package androidx.recyclerview.widget;

/* JADX INFO: renamed from: androidx.recyclerview.widget.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0109b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public long f1846a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public C0109b f1847b;

    public final int a(int i2) {
        C0109b c0109b = this.f1847b;
        if (c0109b == null) {
            if (i2 >= 64) {
                return Long.bitCount(this.f1846a);
            }
            return Long.bitCount(((1 << i2) - 1) & this.f1846a);
        }
        if (i2 < 64) {
            return Long.bitCount(((1 << i2) - 1) & this.f1846a);
        }
        return Long.bitCount(this.f1846a) + c0109b.a(i2 - 64);
    }

    public final void b() {
        if (this.f1847b == null) {
            this.f1847b = new C0109b();
        }
    }

    public final boolean c(int i2) {
        if (i2 < 64) {
            return ((1 << i2) & this.f1846a) != 0;
        }
        b();
        return this.f1847b.c(i2 - 64);
    }

    public final void d() {
        this.f1846a = 0L;
        C0109b c0109b = this.f1847b;
        if (c0109b != null) {
            c0109b.d();
        }
    }

    public final String toString() {
        if (this.f1847b == null) {
            return Long.toBinaryString(this.f1846a);
        }
        return this.f1847b.toString() + "xx" + Long.toBinaryString(this.f1846a);
    }
}
