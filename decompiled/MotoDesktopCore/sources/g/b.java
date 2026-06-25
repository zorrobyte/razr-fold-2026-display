package G;

import F.k;
import com.duolingo.open.rtlviewpager.RtlViewPager;

/* JADX INFO: loaded from: classes.dex */
public final class b implements k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final k f140a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ RtlViewPager f141b;

    public b(RtlViewPager rtlViewPager, k kVar) {
        this.f141b = rtlViewPager;
        this.f140a = kVar;
    }

    @Override // F.k
    public final void a(int i2, int i3, float f2) {
        RtlViewPager rtlViewPager = this.f141b;
        int width = rtlViewPager.getWidth();
        F.a adapter = super/*androidx.viewpager.widget.ViewPager*/.getAdapter();
        if (rtlViewPager.A() && adapter != null) {
            int iD = adapter.d();
            float f3 = width;
            int iG = ((int) ((1.0f - adapter.g(i2)) * f3)) + i3;
            while (i2 < iD && iG > 0) {
                i2++;
                iG -= (int) (adapter.g(i2) * f3);
            }
            i2 = (iD - i2) - 1;
            i3 = -iG;
            f2 = i3 / (adapter.g(i2) * f3);
        }
        this.f140a.a(i2, i3, f2);
    }

    @Override // F.k
    public final void b(int i2) {
        this.f140a.b(i2);
    }

    @Override // F.k
    public final void c(int i2) {
        RtlViewPager rtlViewPager = this.f141b;
        F.a adapter = super/*androidx.viewpager.widget.ViewPager*/.getAdapter();
        if (rtlViewPager.A() && adapter != null) {
            i2 = (adapter.d() - i2) - 1;
        }
        this.f140a.c(i2);
    }
}
