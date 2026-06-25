package androidx.recyclerview.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.app.AbstractC0054a;

/* JADX INFO: loaded from: classes.dex */
public class LinearLayoutManager extends v {

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public C0120m f1746i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public o f1747j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final boolean f1748k;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f1745h = 1;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final boolean f1749l = false;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public boolean f1750m = false;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final boolean f1751n = true;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public SavedState f1752o = null;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final C0119l f1753p = new C0119l(0);

    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new C0121n();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f1754a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f1755b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public boolean f1756c;

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.f1754a);
            parcel.writeInt(this.f1755b);
            parcel.writeInt(this.f1756c ? 1 : 0);
        }
    }

    public LinearLayoutManager() {
        this.f1748k = false;
        R(1);
        a(null);
        if (this.f1748k) {
            this.f1748k = false;
            I();
        }
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        this.f1748k = false;
        C0119l c0119lX = v.x(context, attributeSet, i2, i3);
        R(c0119lX.f1900b);
        boolean z2 = c0119lX.f1902d;
        a(null);
        if (z2 != this.f1748k) {
            this.f1748k = z2;
            I();
        }
        S(c0119lX.f1903e);
    }

    @Override // androidx.recyclerview.widget.v
    public final void A(RecyclerView recyclerView) {
    }

    @Override // androidx.recyclerview.widget.v
    public final void B(AccessibilityEvent accessibilityEvent) {
        super.B(accessibilityEvent);
        if (p() > 0) {
            View viewQ = Q(0, false, p());
            if (viewQ != null) {
                ((w) viewQ.getLayoutParams()).getClass();
                throw null;
            }
            accessibilityEvent.setFromIndex(-1);
            View viewQ2 = Q(p() - 1, false, -1);
            if (viewQ2 == null) {
                accessibilityEvent.setToIndex(-1);
            } else {
                ((w) viewQ2.getLayoutParams()).getClass();
                throw null;
            }
        }
    }

    @Override // androidx.recyclerview.widget.v
    public final void C(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.f1752o = (SavedState) parcelable;
            I();
        }
    }

    @Override // androidx.recyclerview.widget.v
    public final Parcelable D() {
        SavedState savedState = this.f1752o;
        if (savedState != null) {
            SavedState savedState2 = new SavedState();
            savedState2.f1754a = savedState.f1754a;
            savedState2.f1755b = savedState.f1755b;
            savedState2.f1756c = savedState.f1756c;
            return savedState2;
        }
        SavedState savedState3 = new SavedState();
        if (p() <= 0) {
            savedState3.f1754a = -1;
            return savedState3;
        }
        N();
        boolean z2 = this.f1749l;
        savedState3.f1756c = z2;
        if (!z2) {
            v.w(o(z2 ? p() - 1 : 0));
            throw null;
        }
        View viewO = o(z2 ? 0 : p() - 1);
        savedState3.f1755b = this.f1747j.d() - this.f1747j.b(viewO);
        v.w(viewO);
        throw null;
    }

    public final int K(E e2) {
        if (p() == 0) {
            return 0;
        }
        N();
        o oVar = this.f1747j;
        boolean z2 = !this.f1751n;
        return AbstractC0054a.i(e2, oVar, P(z2), O(z2), this, this.f1751n);
    }

    public final void L(E e2) {
        if (p() == 0) {
            return;
        }
        N();
        boolean z2 = !this.f1751n;
        View viewP = P(z2);
        View viewO = O(z2);
        if (p() == 0 || e2.a() == 0 || viewP == null || viewO == null) {
            return;
        }
        ((w) viewP.getLayoutParams()).getClass();
        throw null;
    }

    public final int M(E e2) {
        if (p() == 0) {
            return 0;
        }
        N();
        o oVar = this.f1747j;
        boolean z2 = !this.f1751n;
        return AbstractC0054a.j(e2, oVar, P(z2), O(z2), this, this.f1751n);
    }

    public final void N() {
        if (this.f1746i == null) {
            this.f1746i = new C0120m();
        }
    }

    public final View O(boolean z2) {
        return this.f1749l ? Q(0, z2, p()) : Q(p() - 1, z2, -1);
    }

    public final View P(boolean z2) {
        return this.f1749l ? Q(p() - 1, z2, -1) : Q(0, z2, p());
    }

    public final View Q(int i2, boolean z2, int i3) {
        N();
        int i4 = z2 ? 24579 : 320;
        return this.f1745h == 0 ? this.f1914c.e(i2, i3, i4, 320) : this.f1915d.e(i2, i3, i4, 320);
    }

    public final void R(int i2) {
        if (i2 != 0 && i2 != 1) {
            throw new IllegalArgumentException("invalid orientation:" + i2);
        }
        a(null);
        if (i2 != this.f1745h || this.f1747j == null) {
            this.f1747j = o.a(this, i2);
            this.f1753p.getClass();
            this.f1745h = i2;
            I();
        }
    }

    public void S(boolean z2) {
        a(null);
        if (this.f1750m == z2) {
            return;
        }
        this.f1750m = z2;
        I();
    }

    @Override // androidx.recyclerview.widget.v
    public final void a(String str) {
        RecyclerView recyclerView;
        if (this.f1752o != null || (recyclerView = this.f1913b) == null) {
            return;
        }
        recyclerView.c(str);
    }

    @Override // androidx.recyclerview.widget.v
    public final boolean b() {
        return this.f1745h == 0;
    }

    @Override // androidx.recyclerview.widget.v
    public final boolean c() {
        return this.f1745h == 1;
    }

    @Override // androidx.recyclerview.widget.v
    public final int f(E e2) {
        return K(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final void g(E e2) {
        L(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final int h(E e2) {
        return M(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final int i(E e2) {
        return K(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final void j(E e2) {
        L(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public final int k(E e2) {
        return M(e2);
    }

    @Override // androidx.recyclerview.widget.v
    public w l() {
        return new w(-2, -2);
    }

    @Override // androidx.recyclerview.widget.v
    public final boolean z() {
        return true;
    }
}
