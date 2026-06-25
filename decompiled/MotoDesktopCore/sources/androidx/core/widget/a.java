package androidx.core.widget;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f1435a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1436b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public float f1437c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public float f1438d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public long f1439e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public long f1440f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public long f1441g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public float f1442h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f1443i;

    public final float a(long j2) {
        if (j2 < this.f1439e) {
            return 0.0f;
        }
        long j3 = this.f1441g;
        if (j3 < 0 || j2 < j3) {
            return b.b((j2 - r0) / this.f1435a, 0.0f, 1.0f) * 0.5f;
        }
        float f2 = this.f1442h;
        return (b.b((j2 - j3) / this.f1443i, 0.0f, 1.0f) * f2) + (1.0f - f2);
    }
}
