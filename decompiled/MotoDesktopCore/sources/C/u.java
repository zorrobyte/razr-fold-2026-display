package C;

import h.C0135b;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class u extends t {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0135b f91a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ v f92b;

    public u(v vVar, C0135b c0135b) {
        this.f92b = vVar;
        this.f91a = c0135b;
    }

    @Override // C.r
    public final void a(s sVar) {
        ((ArrayList) this.f91a.get(this.f92b.f94b)).remove(sVar);
    }
}
