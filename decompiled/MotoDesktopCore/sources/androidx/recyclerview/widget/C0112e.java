package androidx.recyclerview.widget;

import android.R;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.MotionEvent;
import com.google.android.material.internal.NavigationMenuView;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: androidx.recyclerview.widget.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0112e {

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public static final int[] f1859x = {R.attr.state_pressed};

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public static final int[] f1860y = new int[0];

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int f1861a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final StateListDrawable f1862b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Drawable f1863c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final int f1864d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f1865e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final StateListDrawable f1866f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final Drawable f1867g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final int f1868h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final int f1869i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public float f1870j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public float f1871k;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final RecyclerView f1874n;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public final ValueAnimator f1880u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public int f1881v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public final F.e f1882w;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f1872l = 0;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f1873m = 0;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final boolean f1875o = false;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final boolean f1876p = false;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public int f1877q = 0;
    public int r = 0;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public final int[] f1878s = new int[2];

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public final int[] f1879t = new int[2];

    public C0112e(NavigationMenuView navigationMenuView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i2, int i3, int i4) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.f1880u = valueAnimatorOfFloat;
        this.f1881v = 0;
        F.e eVar = new F.e(8, this);
        this.f1882w = eVar;
        C0111d c0111d = new C0111d();
        this.f1862b = stateListDrawable;
        this.f1863c = drawable;
        this.f1866f = stateListDrawable2;
        this.f1867g = drawable2;
        this.f1864d = Math.max(i2, stateListDrawable.getIntrinsicWidth());
        this.f1865e = Math.max(i2, drawable.getIntrinsicWidth());
        this.f1868h = Math.max(i2, stateListDrawable2.getIntrinsicWidth());
        this.f1869i = Math.max(i2, drawable2.getIntrinsicWidth());
        this.f1861a = i4;
        stateListDrawable.setAlpha(255);
        drawable.setAlpha(255);
        valueAnimatorOfFloat.addListener(new C.h(this));
        valueAnimatorOfFloat.addUpdateListener(new I.b(2, this));
        RecyclerView recyclerView = this.f1874n;
        if (recyclerView == navigationMenuView) {
            return;
        }
        if (recyclerView != null) {
            v vVar = recyclerView.f1802i;
            if (vVar != null) {
                vVar.a("Cannot remove item decoration during a scroll  or layout");
            }
            ArrayList arrayList = recyclerView.f1803j;
            arrayList.remove(this);
            if (arrayList.isEmpty()) {
                recyclerView.setWillNotDraw(recyclerView.getOverScrollMode() == 2);
            }
            recyclerView.o();
            recyclerView.requestLayout();
            RecyclerView recyclerView2 = this.f1874n;
            recyclerView2.f1804k.remove(this);
            if (recyclerView2.f1805l == this) {
                recyclerView2.f1805l = null;
            }
            ArrayList arrayList2 = this.f1874n.f1786W;
            if (arrayList2 != null) {
                arrayList2.remove(c0111d);
            }
            this.f1874n.removeCallbacks(eVar);
        }
        this.f1874n = navigationMenuView;
        v vVar2 = navigationMenuView.f1802i;
        if (vVar2 != null) {
            vVar2.a("Cannot add item decoration during a scroll  or layout");
        }
        ArrayList arrayList3 = navigationMenuView.f1803j;
        if (arrayList3.isEmpty()) {
            navigationMenuView.setWillNotDraw(false);
        }
        arrayList3.add(this);
        navigationMenuView.o();
        navigationMenuView.requestLayout();
        this.f1874n.f1804k.add(this);
        RecyclerView recyclerView3 = this.f1874n;
        if (recyclerView3.f1786W == null) {
            recyclerView3.f1786W = new ArrayList();
        }
        recyclerView3.f1786W.add(c0111d);
    }

    public static int d(float f2, float f3, int[] iArr, int i2, int i3, int i4) {
        int i5 = iArr[1] - iArr[0];
        if (i5 == 0) {
            return 0;
        }
        int i6 = i2 - i4;
        int i7 = (int) (((f3 - f2) / i5) * i6);
        int i8 = i3 + i7;
        if (i8 >= i6 || i8 < 0) {
            return 0;
        }
        return i7;
    }

    public final boolean a(float f2, float f3) {
        return f3 >= ((float) (this.f1873m - this.f1868h)) && f2 >= ((float) (0 - (0 / 2))) && f2 <= ((float) ((0 / 2) + 0));
    }

    public final boolean b(float f2, float f3) {
        RecyclerView recyclerView = this.f1874n;
        WeakHashMap weakHashMap = v.l.f2836a;
        boolean z2 = recyclerView.getLayoutDirection() == 1;
        int i2 = this.f1864d;
        if (z2) {
            if (f2 > i2 / 2) {
                return false;
            }
        } else if (f2 < this.f1872l - i2) {
            return false;
        }
        int i3 = 0 / 2;
        return f3 >= ((float) (0 - i3)) && f3 <= ((float) (i3 + 0));
    }

    public final boolean c(MotionEvent motionEvent) {
        int i2 = this.f1877q;
        if (i2 == 1) {
            boolean zB = b(motionEvent.getX(), motionEvent.getY());
            boolean zA = a(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (!zB && !zA) {
                return false;
            }
            if (zA) {
                this.r = 1;
                this.f1871k = (int) motionEvent.getX();
            } else if (zB) {
                this.r = 2;
                this.f1870j = (int) motionEvent.getY();
            }
            e(2);
        } else if (i2 != 2) {
            return false;
        }
        return true;
    }

    public final void e(int i2) {
        F.e eVar = this.f1882w;
        StateListDrawable stateListDrawable = this.f1862b;
        if (i2 == 2 && this.f1877q != 2) {
            stateListDrawable.setState(f1859x);
            this.f1874n.removeCallbacks(eVar);
        }
        if (i2 == 0) {
            this.f1874n.invalidate();
        } else {
            f();
        }
        if (this.f1877q == 2 && i2 != 2) {
            stateListDrawable.setState(f1860y);
            this.f1874n.removeCallbacks(eVar);
            this.f1874n.postDelayed(eVar, 1200);
        } else if (i2 == 1) {
            this.f1874n.removeCallbacks(eVar);
            this.f1874n.postDelayed(eVar, 1500);
        }
        this.f1877q = i2;
    }

    public final void f() {
        int i2 = this.f1881v;
        ValueAnimator valueAnimator = this.f1880u;
        if (i2 != 0) {
            if (i2 != 3) {
                return;
            } else {
                valueAnimator.cancel();
            }
        }
        this.f1881v = 1;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f);
        valueAnimator.setDuration(500L);
        valueAnimator.setStartDelay(0L);
        valueAnimator.start();
    }
}
