package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.app.AbstractC0054a;
import java.util.BitSet;
import java.util.List;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class StaggeredGridLayoutManager extends v {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final int f1820h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final L[] f1821i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final o f1822j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final o f1823k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final int f1824l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final boolean f1825m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final boolean f1826n = false;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final e0.k f1827o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final int f1828p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public SavedState f1829q;
    public final boolean r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public final F.e f1830s;

    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new K();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f1835a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f1836b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f1837c;

        /* JADX INFO: renamed from: d, reason: collision with root package name */
        public int[] f1838d;

        /* JADX INFO: renamed from: e, reason: collision with root package name */
        public int f1839e;

        /* JADX INFO: renamed from: f, reason: collision with root package name */
        public int[] f1840f;

        /* JADX INFO: renamed from: g, reason: collision with root package name */
        public List f1841g;

        /* JADX INFO: renamed from: h, reason: collision with root package name */
        public boolean f1842h;

        /* JADX INFO: renamed from: i, reason: collision with root package name */
        public boolean f1843i;

        /* JADX INFO: renamed from: j, reason: collision with root package name */
        public boolean f1844j;

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.f1835a);
            parcel.writeInt(this.f1836b);
            parcel.writeInt(this.f1837c);
            if (this.f1837c > 0) {
                parcel.writeIntArray(this.f1838d);
            }
            parcel.writeInt(this.f1839e);
            if (this.f1839e > 0) {
                parcel.writeIntArray(this.f1840f);
            }
            parcel.writeInt(this.f1842h ? 1 : 0);
            parcel.writeInt(this.f1843i ? 1 : 0);
            parcel.writeInt(this.f1844j ? 1 : 0);
            parcel.writeList(this.f1841g);
        }
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        this.f1820h = -1;
        this.f1825m = false;
        e0.k kVar = new e0.k();
        this.f1827o = kVar;
        this.f1828p = 2;
        new Rect();
        new C0120m().a();
        this.r = true;
        this.f1830s = new F.e(10, this);
        C0119l c0119lX = v.x(context, attributeSet, i2, i3);
        int i4 = c0119lX.f1900b;
        if (i4 != 0 && i4 != 1) {
            throw new IllegalArgumentException("invalid orientation.");
        }
        a(null);
        if (i4 != this.f1824l) {
            this.f1824l = i4;
            o oVar = this.f1822j;
            this.f1822j = this.f1823k;
            this.f1823k = oVar;
            I();
        }
        int i5 = c0119lX.f1901c;
        a(null);
        if (i5 != this.f1820h) {
            kVar.f2495a = null;
            I();
            this.f1820h = i5;
            new BitSet(this.f1820h);
            this.f1821i = new L[this.f1820h];
            for (int i6 = 0; i6 < this.f1820h; i6++) {
                this.f1821i[i6] = new L(this, i6);
            }
            I();
        }
        boolean z2 = c0119lX.f1902d;
        a(null);
        SavedState savedState = this.f1829q;
        if (savedState != null && savedState.f1842h != z2) {
            savedState.f1842h = z2;
        }
        this.f1825m = z2;
        I();
        C0118k c0118k = new C0118k();
        c0118k.f1897a = 0;
        c0118k.f1898b = 0;
        this.f1822j = o.a(this, this.f1824l);
        this.f1823k = o.a(this, 1 - this.f1824l);
    }

    @Override // androidx.recyclerview.widget.v
    public final void A(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.f1913b;
        if (recyclerView2 != null) {
            recyclerView2.removeCallbacks(this.f1830s);
        }
        for (int i2 = 0; i2 < this.f1820h; i2++) {
            this.f1821i[i2].b();
        }
        recyclerView.requestLayout();
    }

    @Override // androidx.recyclerview.widget.v
    public final void B(AccessibilityEvent accessibilityEvent) {
        super.B(accessibilityEvent);
        if (p() > 0) {
            View viewP = P(false);
            View viewO = O(false);
            if (viewP == null || viewO == null) {
                return;
            }
            ((w) viewP.getLayoutParams()).getClass();
            throw null;
        }
    }

    @Override // androidx.recyclerview.widget.v
    public final void C(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.f1829q = (SavedState) parcelable;
            I();
        }
    }

    @Override // androidx.recyclerview.widget.v
    public final Parcelable D() {
        SavedState savedState = this.f1829q;
        if (savedState != null) {
            SavedState savedState2 = new SavedState();
            savedState2.f1837c = savedState.f1837c;
            savedState2.f1835a = savedState.f1835a;
            savedState2.f1836b = savedState.f1836b;
            savedState2.f1838d = savedState.f1838d;
            savedState2.f1839e = savedState.f1839e;
            savedState2.f1840f = savedState.f1840f;
            savedState2.f1842h = savedState.f1842h;
            savedState2.f1843i = savedState.f1843i;
            savedState2.f1844j = savedState.f1844j;
            savedState2.f1841g = savedState.f1841g;
            return savedState2;
        }
        SavedState savedState3 = new SavedState();
        savedState3.f1842h = this.f1825m;
        savedState3.f1843i = false;
        savedState3.f1844j = false;
        e0.k kVar = this.f1827o;
        if (kVar != null) {
            kVar.getClass();
        }
        savedState3.f1839e = 0;
        if (p() > 0) {
            Q();
            savedState3.f1835a = 0;
            View viewO = this.f1826n ? O(true) : P(true);
            if (viewO != null) {
                ((w) viewO.getLayoutParams()).getClass();
                throw null;
            }
            savedState3.f1836b = -1;
            int i2 = this.f1820h;
            savedState3.f1837c = i2;
            savedState3.f1838d = new int[i2];
            for (int i3 = 0; i3 < this.f1820h; i3++) {
                int iD = this.f1821i[i3].d(Integer.MIN_VALUE);
                if (iD != Integer.MIN_VALUE) {
                    iD -= this.f1822j.e();
                }
                savedState3.f1838d[i3] = iD;
            }
        } else {
            savedState3.f1835a = -1;
            savedState3.f1836b = -1;
            savedState3.f1837c = 0;
        }
        return savedState3;
    }

    @Override // androidx.recyclerview.widget.v
    public final void E(int i2) {
        if (i2 == 0) {
            K();
        }
    }

    public final boolean K() {
        if (p() != 0 && this.f1828p != 0 && this.f1916e) {
            if (this.f1826n) {
                R();
                Q();
            } else {
                Q();
                R();
            }
            View viewS = S();
            e0.k kVar = this.f1827o;
            if (viewS != null) {
                kVar.getClass();
                kVar.f2495a = null;
                I();
                return true;
            }
        }
        return false;
    }

    public final int L(E e2) {
        if (p() == 0) {
            return 0;
        }
        o oVar = this.f1822j;
        boolean z2 = this.r;
        return AbstractC0054a.i(e2, oVar, P(!z2), O(!z2), this, this.r);
    }

    public final void M(E e2) {
        if (p() == 0) {
            return;
        }
        boolean z2 = !this.r;
        View viewP = P(z2);
        View viewO = O(z2);
        if (p() == 0 || e2.a() == 0 || viewP == null || viewO == null) {
            return;
        }
        ((w) viewP.getLayoutParams()).getClass();
        throw null;
    }

    public final int N(E e2) {
        if (p() == 0) {
            return 0;
        }
        o oVar = this.f1822j;
        boolean z2 = this.r;
        return AbstractC0054a.j(e2, oVar, P(!z2), O(!z2), this, this.r);
    }

    public final View O(boolean z2) {
        int iE = this.f1822j.e();
        int iD = this.f1822j.d();
        View view = null;
        for (int iP = p() - 1; iP >= 0; iP--) {
            View viewO = o(iP);
            int iC = this.f1822j.c(viewO);
            int iB = this.f1822j.b(viewO);
            if (iB > iE && iC < iD) {
                if (iB <= iD || !z2) {
                    return viewO;
                }
                if (view == null) {
                    view = viewO;
                }
            }
        }
        return view;
    }

    public final View P(boolean z2) {
        int iE = this.f1822j.e();
        int iD = this.f1822j.d();
        int iP = p();
        View view = null;
        for (int i2 = 0; i2 < iP; i2++) {
            View viewO = o(i2);
            int iC = this.f1822j.c(viewO);
            if (this.f1822j.b(viewO) > iE && iC < iD) {
                if (iC >= iE || !z2) {
                    return viewO;
                }
                if (view == null) {
                    view = viewO;
                }
            }
        }
        return view;
    }

    public final void Q() {
        if (p() == 0) {
            return;
        }
        v.w(o(0));
        throw null;
    }

    public final void R() {
        int iP = p();
        if (iP == 0) {
            return;
        }
        v.w(o(iP - 1));
        throw null;
    }

    public final View S() {
        int iP = p();
        int i2 = iP - 1;
        new BitSet(this.f1820h).set(0, this.f1820h, true);
        if (this.f1824l == 1) {
            T();
        }
        if (this.f1826n) {
            iP = -1;
        } else {
            i2 = 0;
        }
        if (i2 == iP) {
            return null;
        }
        ((I) o(i2).getLayoutParams()).getClass();
        throw null;
    }

    public final boolean T() {
        RecyclerView recyclerView = this.f1913b;
        WeakHashMap weakHashMap = v.l.f2836a;
        return recyclerView.getLayoutDirection() == 1;
    }

    @Override // androidx.recyclerview.widget.v
    public final void a(String str) {
        RecyclerView recyclerView;
        if (this.f1829q != null || (recyclerView = this.f1913b) == null) {
            return;
        }
        recyclerView.c(str);
    }

    @Override // androidx.recyclerview.widget.v
    public final boolean b() {
        return this.f1824l == 0;
    }

    @Override // androidx.recyclerview.widget.v
    public final boolean c() {
        return this.f1824l == 1;
    }

    @Override // androidx.recyclerview.widget.v
    public final boolean d(w wVar) {
        return wVar instanceof I;
    }

    @Override // androidx.recyclerview.widget.v
    public final int f(E e2) {
        return L(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final void g(E e2) {
        M(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final int h(E e2) {
        return N(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final int i(E e2) {
        return L(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final void j(E e2) {
        M(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final int k(E e2) {
        return N(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final w l() {
        return this.f1824l == 0 ? new I(-2, -1) : new I(-1, -2);
    }

    @Override // androidx.recyclerview.widget.v
    public final w m(Context context, AttributeSet attributeSet) {
        return new I(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.v
    public final w n(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new I((ViewGroup.MarginLayoutParams) layoutParams) : new I(layoutParams);
    }

    @Override // androidx.recyclerview.widget.v
    public final int q(B b2, E e2) {
        if (this.f1824l == 1) {
            return this.f1820h;
        }
        super.q(b2, e2);
        return 1;
    }

    @Override // androidx.recyclerview.widget.v
    public final int y(B b2, E e2) {
        if (this.f1824l == 0) {
            return this.f1820h;
        }
        super.y(b2, e2);
        return 1;
    }

    @Override // androidx.recyclerview.widget.v
    public final boolean z() {
        return this.f1828p != 0;
    }
}
