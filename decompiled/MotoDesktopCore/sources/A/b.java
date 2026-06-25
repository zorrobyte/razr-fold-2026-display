package A;

import X.w0;
import h.l;

/* JADX INFO: loaded from: classes.dex */
public final class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final l f0a = new l();

    public final void a() {
        l lVar = this.f0a;
        if (lVar.f() > 0) {
            w0.c(lVar.g(0));
            throw null;
        }
        int i2 = lVar.f2616d;
        Object[] objArr = lVar.f2615c;
        for (int i3 = 0; i3 < i2; i3++) {
            objArr[i3] = null;
        }
        lVar.f2616d = 0;
        lVar.f2613a = false;
    }
}
