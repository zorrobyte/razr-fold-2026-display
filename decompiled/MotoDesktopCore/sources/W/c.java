package W;

import F.k;
import com.google.android.material.tabs.TabLayout;
import java.lang.ref.WeakReference;
import u.C0161b;

/* JADX INFO: loaded from: classes.dex */
public final class c implements k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final WeakReference f248a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f249b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f250c;

    public c(TabLayout tabLayout) {
        this.f248a = new WeakReference(tabLayout);
    }

    @Override // F.k
    public final void a(int i2, int i3, float f2) {
        if (((TabLayout) this.f248a.get()) != null) {
            int i4 = this.f250c;
            if (i4 == 2) {
                int i5 = this.f249b;
            }
            if (i4 == 2) {
                int i6 = this.f249b;
            }
            C0161b c0161b = TabLayout.r;
            if (Math.round(i2 + f2) >= 0) {
                throw null;
            }
        }
    }

    @Override // F.k
    public final void b(int i2) {
        this.f249b = this.f250c;
        this.f250c = i2;
    }

    @Override // F.k
    public final void c(int i2) {
        TabLayout tabLayout = (TabLayout) this.f248a.get();
        if (tabLayout == null || tabLayout.getSelectedTabPosition() == i2 || i2 >= tabLayout.getTabCount()) {
            return;
        }
        int i3 = this.f250c;
        boolean z2 = i3 == 0 || (i3 == 2 && this.f249b == 0);
        if (i2 >= 0 && i2 < tabLayout.getTabCount()) {
            throw null;
        }
        tabLayout.b(null, z2);
    }
}
