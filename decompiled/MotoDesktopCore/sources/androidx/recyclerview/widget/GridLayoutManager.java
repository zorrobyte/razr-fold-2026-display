package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public class GridLayoutManager extends LinearLayoutManager {

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final int f1738q;
    public final e0.k r;

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.f1738q = -1;
        new SparseIntArray();
        new SparseIntArray();
        e0.k kVar = new e0.k();
        kVar.f2495a = new SparseIntArray();
        this.r = kVar;
        new Rect();
        int i4 = v.x(context, attributeSet, i2, i3).f1901c;
        if (i4 == this.f1738q) {
            return;
        }
        if (i4 < 1) {
            throw new IllegalArgumentException("Span count should be at least 1. Provided " + i4);
        }
        this.f1738q = i4;
        kVar.j();
        I();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void S(boolean z2) {
        if (z2) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.S(false);
    }

    public final int T(B b2, E e2, int i2) {
        boolean z2 = e2.f1728d;
        e0.k kVar = this.r;
        if (!z2) {
            int i3 = this.f1738q;
            kVar.getClass();
            return e0.k.i(i2, i3);
        }
        int iA = b2.a(i2);
        if (iA != -1) {
            int i4 = this.f1738q;
            kVar.getClass();
            return e0.k.i(iA, i4);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + i2);
        return 0;
    }

    @Override // androidx.recyclerview.widget.v
    public final boolean d(w wVar) {
        return wVar instanceof C0117j;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.v
    public final w l() {
        return this.f1745h == 0 ? new C0117j(-2, -1) : new C0117j(-1, -2);
    }

    @Override // androidx.recyclerview.widget.v
    public final w m(Context context, AttributeSet attributeSet) {
        return new C0117j(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.v
    public final w n(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new C0117j((ViewGroup.MarginLayoutParams) layoutParams) : new C0117j(layoutParams);
    }

    @Override // androidx.recyclerview.widget.v
    public final int q(B b2, E e2) {
        if (this.f1745h == 1) {
            return this.f1738q;
        }
        if (e2.a() < 1) {
            return 0;
        }
        return T(b2, e2, e2.a() - 1) + 1;
    }

    @Override // androidx.recyclerview.widget.v
    public final int y(B b2, E e2) {
        if (this.f1745h == 0) {
            return this.f1738q;
        }
        if (e2.a() < 1) {
            return 0;
        }
        return T(b2, e2, e2.a() - 1) + 1;
    }
}
