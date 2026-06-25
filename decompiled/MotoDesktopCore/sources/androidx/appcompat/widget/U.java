package androidx.appcompat.widget;

/* JADX INFO: loaded from: classes.dex */
public final class U {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f1181a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1182b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1183c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f1184d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f1185e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1186f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f1187g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f1188h;

    public final void a(int i2, int i3) {
        this.f1183c = i2;
        this.f1184d = i3;
        this.f1188h = true;
        if (this.f1187g) {
            if (i3 != Integer.MIN_VALUE) {
                this.f1181a = i3;
            }
            if (i2 != Integer.MIN_VALUE) {
                this.f1182b = i2;
                return;
            }
            return;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.f1181a = i2;
        }
        if (i3 != Integer.MIN_VALUE) {
            this.f1182b = i3;
        }
    }
}
