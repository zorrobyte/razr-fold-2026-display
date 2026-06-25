package androidx.appcompat.widget;

import android.view.View;
import androidx.appcompat.app.AbstractC0054a;

/* JADX INFO: loaded from: classes.dex */
public final class u0 extends AbstractC0054a {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ int f1312d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1313e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1314f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final /* synthetic */ Object f1315g;

    public u0(v0 v0Var, int i2) {
        this.f1312d = 0;
        this.f1315g = v0Var;
        this.f1314f = i2;
        this.f1313e = false;
    }

    public u0(d.j jVar) {
        this.f1312d = 1;
        this.f1315g = jVar;
        this.f1313e = false;
        this.f1314f = 0;
    }

    @Override // androidx.appcompat.app.AbstractC0054a, v.n
    public void a(View view) {
        switch (this.f1312d) {
            case 0:
                this.f1313e = true;
                break;
        }
    }

    @Override // androidx.appcompat.app.AbstractC0054a, v.n
    public final void b() {
        switch (this.f1312d) {
            case 0:
                ((v0) this.f1315g).f1320a.setVisibility(0);
                break;
            default:
                if (!this.f1313e) {
                    this.f1313e = true;
                    v.n nVar = ((d.j) this.f1315g).f2407d;
                    if (nVar != null) {
                        nVar.b();
                    }
                    break;
                }
                break;
        }
    }

    @Override // v.n
    public final void c() {
        switch (this.f1312d) {
            case 0:
                if (!this.f1313e) {
                    ((v0) this.f1315g).f1320a.setVisibility(this.f1314f);
                }
                break;
            default:
                int i2 = this.f1314f + 1;
                this.f1314f = i2;
                d.j jVar = (d.j) this.f1315g;
                if (i2 == jVar.f2404a.size()) {
                    v.n nVar = jVar.f2407d;
                    if (nVar != null) {
                        nVar.c();
                    }
                    this.f1314f = 0;
                    this.f1313e = false;
                    jVar.f2408e = false;
                }
                break;
        }
    }
}
