package C;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class l implements r {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Object f51a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ArrayList f52b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f53c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ ArrayList f54d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f55e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ ArrayList f56f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final /* synthetic */ m f57g;

    public l(m mVar, Object obj, ArrayList arrayList, Object obj2, ArrayList arrayList2, Object obj3, ArrayList arrayList3) {
        this.f57g = mVar;
        this.f51a = obj;
        this.f52b = arrayList;
        this.f53c = obj2;
        this.f54d = arrayList2;
        this.f55e = obj3;
        this.f56f = arrayList3;
    }

    @Override // C.r
    public final void a(s sVar) {
    }

    @Override // C.r
    public final void b() {
    }

    @Override // C.r
    public final void c() {
        m mVar = this.f57g;
        Object obj = this.f51a;
        if (obj != null) {
            mVar.n(obj, this.f52b, null);
        }
        Object obj2 = this.f53c;
        if (obj2 != null) {
            mVar.n(obj2, this.f54d, null);
        }
        Object obj3 = this.f55e;
        if (obj3 != null) {
            mVar.n(obj3, this.f56f, null);
        }
    }

    @Override // C.r
    public final void d() {
    }
}
