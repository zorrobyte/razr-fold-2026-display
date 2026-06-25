package androidx.recyclerview.widget;

/* JADX INFO: renamed from: androidx.recyclerview.widget.l, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0119l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1899a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1900b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1901c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f1902d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1903e;

    public C0119l(int i2) {
        this.f1899a = i2;
        switch (i2) {
            case 1:
                break;
            default:
                a();
                break;
        }
    }

    public void a() {
        this.f1900b = -1;
        this.f1901c = Integer.MIN_VALUE;
        this.f1902d = false;
        this.f1903e = false;
    }

    public String toString() {
        switch (this.f1899a) {
            case 0:
                return "AnchorInfo{mPosition=" + this.f1900b + ", mCoordinate=" + this.f1901c + ", mLayoutFromEnd=" + this.f1902d + ", mValid=" + this.f1903e + '}';
            default:
                return super.toString();
        }
    }
}
