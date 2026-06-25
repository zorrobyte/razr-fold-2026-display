package androidx.recyclerview.widget;

import android.view.View;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class L {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ArrayList f1740a = new ArrayList();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1741b = Integer.MIN_VALUE;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1742c = Integer.MIN_VALUE;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final int f1743d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ StaggeredGridLayoutManager f1744e;

    public L(StaggeredGridLayoutManager staggeredGridLayoutManager, int i2) {
        this.f1744e = staggeredGridLayoutManager;
        this.f1743d = i2;
    }

    public final void a() {
        View view = (View) this.f1740a.get(r0.size() - 1);
        I i2 = (I) view.getLayoutParams();
        this.f1742c = this.f1744e.f1822j.b(view);
        i2.getClass();
    }

    public final void b() {
        this.f1740a.clear();
        this.f1741b = Integer.MIN_VALUE;
        this.f1742c = Integer.MIN_VALUE;
    }

    public final int c(int i2) {
        int i3 = this.f1742c;
        if (i3 != Integer.MIN_VALUE) {
            return i3;
        }
        if (this.f1740a.size() == 0) {
            return i2;
        }
        a();
        return this.f1742c;
    }

    public final int d(int i2) {
        int i3 = this.f1741b;
        if (i3 != Integer.MIN_VALUE) {
            return i3;
        }
        if (this.f1740a.size() == 0) {
            return i2;
        }
        View view = (View) this.f1740a.get(0);
        I i4 = (I) view.getLayoutParams();
        this.f1741b = this.f1744e.f1822j.c(view);
        i4.getClass();
        return this.f1741b;
    }
}
