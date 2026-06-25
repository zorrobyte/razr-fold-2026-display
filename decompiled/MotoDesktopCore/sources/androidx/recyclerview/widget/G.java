package androidx.recyclerview.widget;

import android.view.animation.Interpolator;
import android.widget.OverScroller;
import com.google.android.material.internal.NavigationMenuView;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class G implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f1731a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1732b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public OverScroller f1733c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public Interpolator f1734d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1735e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f1736f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final /* synthetic */ RecyclerView f1737g;

    public G(NavigationMenuView navigationMenuView) {
        this.f1737g = navigationMenuView;
        p pVar = RecyclerView.l0;
        this.f1734d = pVar;
        this.f1735e = false;
        this.f1736f = false;
        this.f1733c = new OverScroller(navigationMenuView.getContext(), pVar);
    }

    public final void a() {
        if (this.f1735e) {
            this.f1736f = true;
            return;
        }
        RecyclerView recyclerView = this.f1737g;
        recyclerView.removeCallbacks(this);
        WeakHashMap weakHashMap = v.l.f2836a;
        recyclerView.postOnAnimation(this);
    }

    @Override // java.lang.Runnable
    public final void run() {
        RecyclerView recyclerView = this.f1737g;
        if (recyclerView.f1802i == null) {
            recyclerView.removeCallbacks(this);
            this.f1733c.abortAnimation();
            return;
        }
        this.f1736f = false;
        this.f1735e = true;
        recyclerView.e();
        OverScroller overScroller = this.f1733c;
        recyclerView.f1802i.getClass();
        if (overScroller.computeScrollOffset()) {
            int currX = overScroller.getCurrX();
            int currY = overScroller.getCurrY();
            int i2 = currX - this.f1731a;
            int i3 = currY - this.f1732b;
            this.f1731a = currX;
            this.f1732b = currY;
            RecyclerView recyclerView2 = this.f1737g;
            int[] iArr = recyclerView.f1796e0;
            if (recyclerView2.h(i2, i3, iArr, null, 1)) {
                i2 -= iArr[0];
                i3 -= iArr[1];
            }
            if (!recyclerView.f1803j.isEmpty()) {
                recyclerView.invalidate();
            }
            if (recyclerView.getOverScrollMode() != 2) {
                recyclerView.d(i2, i3);
            }
            recyclerView.i(0, 0, null, 1);
            if (!recyclerView.awakenScrollBars()) {
                recyclerView.invalidate();
            }
            boolean z2 = (i2 == 0 && i3 == 0) || (i2 != 0 && recyclerView.f1802i.b() && i2 == 0) || (i3 != 0 && recyclerView.f1802i.c() && i3 == 0);
            if (overScroller.isFinished() || !(z2 || recyclerView.m())) {
                recyclerView.setScrollState(0);
                C0114g c0114g = recyclerView.f1785U;
                c0114g.getClass();
                c0114g.f1885c = 0;
                recyclerView.f(1);
            } else {
                a();
                RunnableC0116i runnableC0116i = recyclerView.f1784T;
                if (runnableC0116i != null) {
                    runnableC0116i.a(recyclerView, i2, i3);
                }
            }
        }
        this.f1735e = false;
        if (this.f1736f) {
            a();
        }
    }
}
