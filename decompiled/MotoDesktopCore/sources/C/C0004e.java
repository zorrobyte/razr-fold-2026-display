package C;

import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: renamed from: C.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0004e extends t {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f29a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object f30b;

    public /* synthetic */ C0004e() {
        this.f29a = 3;
    }

    public /* synthetic */ C0004e(int i2, Object obj) {
        this.f29a = i2;
        this.f30b = obj;
    }

    @Override // C.r
    public final void a(s sVar) {
        switch (this.f29a) {
            case 0:
                Y.r.p0((ViewGroup) this.f30b, false);
                sVar.v(this);
                break;
            case 1:
                A.b((View) this.f30b, 1.0f);
                sVar.v(this);
                break;
            case 2:
                ((s) this.f30b).y();
                sVar.v(this);
                break;
            default:
                x xVar = (x) this.f30b;
                int i2 = xVar.f101z - 1;
                xVar.f101z = i2;
                if (i2 == 0) {
                    xVar.f97A = false;
                    xVar.m();
                }
                sVar.v(this);
                break;
        }
    }

    @Override // C.t, C.r
    public void b() {
        switch (this.f29a) {
            case 0:
                Y.r.p0((ViewGroup) this.f30b, false);
                break;
        }
    }

    @Override // C.t, C.r
    public void c() {
        switch (this.f29a) {
            case 3:
                x xVar = (x) this.f30b;
                if (!xVar.f97A) {
                    xVar.F();
                    xVar.f97A = true;
                }
                break;
        }
    }

    @Override // C.t, C.r
    public void d() {
        switch (this.f29a) {
            case 0:
                Y.r.p0((ViewGroup) this.f30b, true);
                break;
        }
    }
}
