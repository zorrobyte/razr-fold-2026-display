package X;

/* JADX INFO: loaded from: classes.dex */
public final class O0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int f283a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Object f284b;

    public O0(int i2, Object obj) {
        this.f283a = i2;
        this.f284b = obj;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof O0)) {
            return false;
        }
        O0 o0 = (O0) obj;
        return this.f283a == o0.f283a && this.f284b == o0.f284b;
    }

    public final int hashCode() {
        return this.f284b.hashCode() + this.f283a;
    }
}
