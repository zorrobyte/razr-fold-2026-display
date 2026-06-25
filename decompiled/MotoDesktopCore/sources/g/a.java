package G;

import F.l;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import com.duolingo.open.rtlviewpager.RtlViewPager;

/* JADX INFO: loaded from: classes.dex */
public final class a extends F.a {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final F.a f138c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ RtlViewPager f139d;

    public a(RtlViewPager rtlViewPager, F.a aVar) {
        this.f139d = rtlViewPager;
        this.f138c = aVar;
        aVar.k(new l(this));
    }

    public static void s(a aVar) {
        super.j();
    }

    @Override // F.a
    public final void a(ViewGroup viewGroup, int i2, Object obj) {
        int i3 = RtlViewPager.f2019f0;
        boolean zA = this.f139d.A();
        F.a aVar = this.f138c;
        if (zA) {
            i2 = (aVar.d() - i2) - 1;
        }
        aVar.a(viewGroup, i2, obj);
    }

    @Override // F.a
    public final void b(View view) {
        this.f138c.b(view);
    }

    @Override // F.a
    public final void c(ViewGroup viewGroup) {
        this.f138c.c(viewGroup);
    }

    @Override // F.a
    public final int d() {
        return this.f138c.d();
    }

    @Override // F.a
    public final int e(Object obj) {
        int iE = this.f138c.e(obj);
        int i2 = RtlViewPager.f2019f0;
        if (!this.f139d.A()) {
            return iE;
        }
        if (iE == -1 || iE == -2) {
            return -2;
        }
        return (r0.d() - iE) - 1;
    }

    @Override // F.a
    public final CharSequence f(int i2) {
        int i3 = RtlViewPager.f2019f0;
        boolean zA = this.f139d.A();
        F.a aVar = this.f138c;
        if (zA) {
            i2 = (aVar.d() - i2) - 1;
        }
        return aVar.f(i2);
    }

    @Override // F.a
    public final float g(int i2) {
        int i3 = RtlViewPager.f2019f0;
        boolean zA = this.f139d.A();
        F.a aVar = this.f138c;
        if (zA) {
            i2 = (aVar.d() - i2) - 1;
        }
        return aVar.g(i2);
    }

    @Override // F.a
    public final Object h(ViewGroup viewGroup, int i2) {
        int i3 = RtlViewPager.f2019f0;
        boolean zA = this.f139d.A();
        F.a aVar = this.f138c;
        if (zA) {
            i2 = (aVar.d() - i2) - 1;
        }
        return aVar.h(viewGroup, i2);
    }

    @Override // F.a
    public final boolean i(View view, Object obj) {
        return this.f138c.i(view, obj);
    }

    @Override // F.a
    public final void j() {
        this.f138c.j();
    }

    @Override // F.a
    public final void k(DataSetObserver dataSetObserver) {
        this.f138c.k(dataSetObserver);
    }

    @Override // F.a
    public final void l(Parcelable parcelable, ClassLoader classLoader) {
        this.f138c.l(parcelable, classLoader);
    }

    @Override // F.a
    public final Parcelable m() {
        return this.f138c.m();
    }

    @Override // F.a
    public final void n(int i2, View view, Object obj) {
        int i3 = RtlViewPager.f2019f0;
        boolean zA = this.f139d.A();
        F.a aVar = this.f138c;
        if (zA) {
            i2 = (aVar.d() - i2) - 1;
        }
        aVar.n(i2, view, obj);
    }

    @Override // F.a
    public final void o(ViewGroup viewGroup, int i2, Object obj) {
        int i3 = RtlViewPager.f2019f0;
        boolean zA = this.f139d.A();
        F.a aVar = this.f138c;
        if (zA) {
            i2 = (aVar.d() - i2) - 1;
        }
        aVar.o(viewGroup, i2, obj);
    }

    @Override // F.a
    public final void p(View view) {
        this.f138c.p(view);
    }

    @Override // F.a
    public final void q(ViewGroup viewGroup) {
        this.f138c.q(viewGroup);
    }

    @Override // F.a
    public final void r(DataSetObserver dataSetObserver) {
        this.f138c.r(dataSetObserver);
    }
}
