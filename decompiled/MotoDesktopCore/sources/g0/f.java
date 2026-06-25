package g0;

import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int f2563a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final int f2564b;

    public f(int i2, int i3) {
        this.f2563a = i2;
        this.f2564b = i3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || f.class != obj.getClass()) {
            return false;
        }
        f fVar = (f) obj;
        return this.f2563a == fVar.f2563a && this.f2564b == fVar.f2564b;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.f2563a), Integer.valueOf(this.f2564b));
    }

    public final String toString() {
        return "{associationId=" + this.f2563a + ", deviceId=" + this.f2564b + '}';
    }
}
