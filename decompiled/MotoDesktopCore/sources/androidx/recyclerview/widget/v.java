package androidx.recyclerview.widget;

import X.w0;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.R$styleable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class v {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public f0.b f1912a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public RecyclerView f1913b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final F.f f1914c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final F.f f1915d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1916e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1917f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f1918g;

    public v() {
        u uVar = new u(this, 0);
        u uVar2 = new u(this, 1);
        this.f1914c = new F.f(uVar);
        this.f1915d = new F.f(uVar2);
        this.f1916e = false;
    }

    public static int e(int i2, int i3, int i4) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        return mode != Integer.MIN_VALUE ? mode != 1073741824 ? Math.max(i3, i4) : size : Math.min(size, Math.max(i3, i4));
    }

    public static void r(Rect rect, View view) {
        int[] iArr = RecyclerView.f1763i0;
        w wVar = (w) view.getLayoutParams();
        Rect rect2 = wVar.f1919a;
        rect.set((view.getLeft() - rect2.left) - ((ViewGroup.MarginLayoutParams) wVar).leftMargin, (view.getTop() - rect2.top) - ((ViewGroup.MarginLayoutParams) wVar).topMargin, view.getRight() + rect2.right + ((ViewGroup.MarginLayoutParams) wVar).rightMargin, view.getBottom() + rect2.bottom + ((ViewGroup.MarginLayoutParams) wVar).bottomMargin);
    }

    public static void w(View view) {
        ((w) view.getLayoutParams()).getClass();
        throw null;
    }

    public static C0119l x(Context context, AttributeSet attributeSet, int i2, int i3) {
        C0119l c0119l = new C0119l(1);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RecyclerView, i2, i3);
        c0119l.f1900b = typedArrayObtainStyledAttributes.getInt(R$styleable.RecyclerView_android_orientation, 1);
        c0119l.f1901c = typedArrayObtainStyledAttributes.getInt(R$styleable.RecyclerView_spanCount, 1);
        c0119l.f1902d = typedArrayObtainStyledAttributes.getBoolean(R$styleable.RecyclerView_reverseLayout, false);
        c0119l.f1903e = typedArrayObtainStyledAttributes.getBoolean(R$styleable.RecyclerView_stackFromEnd, false);
        typedArrayObtainStyledAttributes.recycle();
        return c0119l;
    }

    public abstract void A(RecyclerView recyclerView);

    public void B(AccessibilityEvent accessibilityEvent) {
        RecyclerView recyclerView = this.f1913b;
        B b2 = recyclerView.f1787a;
        E e2 = recyclerView.V;
        if (recyclerView == null || accessibilityEvent == null) {
            return;
        }
        boolean z2 = true;
        if (!recyclerView.canScrollVertically(1) && !this.f1913b.canScrollVertically(-1) && !this.f1913b.canScrollHorizontally(-1) && !this.f1913b.canScrollHorizontally(1)) {
            z2 = false;
        }
        accessibilityEvent.setScrollable(z2);
        this.f1913b.getClass();
    }

    public abstract void C(Parcelable parcelable);

    public abstract Parcelable D();

    public void E(int i2) {
    }

    public final void F() {
        int iP = p() - 1;
        if (iP < 0) {
            return;
        }
        RecyclerView.l(o(iP));
        throw null;
    }

    public final void G(B b2) {
        int size = b2.f1719a.size();
        int i2 = size - 1;
        ArrayList arrayList = b2.f1719a;
        if (i2 >= 0) {
            w0.c(arrayList.get(i2));
            throw null;
        }
        arrayList.clear();
        if (size > 0) {
            this.f1913b.invalidate();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean H(androidx.recyclerview.widget.RecyclerView r9, android.view.View r10, android.graphics.Rect r11, boolean r12, boolean r13) {
        /*
            r8 = this;
            int r0 = r8.t()
            int r1 = r8.v()
            int r2 = r8.f1917f
            int r3 = r8.u()
            int r2 = r2 - r3
            int r3 = r8.f1918g
            int r4 = r8.s()
            int r3 = r3 - r4
            int r4 = r10.getLeft()
            int r5 = r11.left
            int r4 = r4 + r5
            int r5 = r10.getScrollX()
            int r4 = r4 - r5
            int r5 = r10.getTop()
            int r6 = r11.top
            int r5 = r5 + r6
            int r10 = r10.getScrollY()
            int r5 = r5 - r10
            int r10 = r11.width()
            int r10 = r10 + r4
            int r11 = r11.height()
            int r11 = r11 + r5
            int r4 = r4 - r0
            r0 = 0
            int r6 = java.lang.Math.min(r0, r4)
            int r5 = r5 - r1
            int r1 = java.lang.Math.min(r0, r5)
            int r10 = r10 - r2
            int r2 = java.lang.Math.max(r0, r10)
            int r11 = r11 - r3
            int r11 = java.lang.Math.max(r0, r11)
            androidx.recyclerview.widget.RecyclerView r3 = r8.f1913b
            java.util.WeakHashMap r7 = v.l.f2836a
            int r3 = r3.getLayoutDirection()
            r7 = 1
            if (r3 != r7) goto L60
            if (r2 == 0) goto L5b
            goto L68
        L5b:
            int r2 = java.lang.Math.max(r6, r10)
            goto L68
        L60:
            if (r6 == 0) goto L63
            goto L67
        L63:
            int r6 = java.lang.Math.min(r4, r2)
        L67:
            r2 = r6
        L68:
            if (r1 == 0) goto L6b
            goto L6f
        L6b:
            int r1 = java.lang.Math.min(r5, r11)
        L6f:
            int[] r10 = new int[]{r2, r1}
            r11 = r10[r0]
            r10 = r10[r7]
            if (r13 == 0) goto Lb2
            android.view.View r13 = r9.getFocusedChild()
            if (r13 != 0) goto L80
            goto Lb7
        L80:
            int r1 = r8.t()
            int r2 = r8.v()
            int r3 = r8.f1917f
            int r4 = r8.u()
            int r3 = r3 - r4
            int r4 = r8.f1918g
            int r5 = r8.s()
            int r4 = r4 - r5
            androidx.recyclerview.widget.RecyclerView r8 = r8.f1913b
            android.graphics.Rect r8 = r8.f1799g
            r(r8, r13)
            int r13 = r8.left
            int r13 = r13 - r11
            if (r13 >= r3) goto Lb7
            int r13 = r8.right
            int r13 = r13 - r11
            if (r13 <= r1) goto Lb7
            int r13 = r8.top
            int r13 = r13 - r10
            if (r13 >= r4) goto Lb7
            int r8 = r8.bottom
            int r8 = r8 - r10
            if (r8 > r2) goto Lb2
            goto Lb7
        Lb2:
            if (r11 != 0) goto Lb8
            if (r10 == 0) goto Lb7
            goto Lb8
        Lb7:
            return r0
        Lb8:
            if (r12 == 0) goto Lbe
            r9.scrollBy(r11, r10)
            goto Lc1
        Lbe:
            r9.v(r11, r10)
        Lc1:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.v.H(androidx.recyclerview.widget.RecyclerView, android.view.View, android.graphics.Rect, boolean, boolean):boolean");
    }

    public final void I() {
        RecyclerView recyclerView = this.f1913b;
        if (recyclerView != null) {
            recyclerView.requestLayout();
        }
    }

    public final void J(RecyclerView recyclerView) {
        if (recyclerView == null) {
            this.f1913b = null;
            this.f1912a = null;
            this.f1917f = 0;
            this.f1918g = 0;
            return;
        }
        this.f1913b = recyclerView;
        this.f1912a = recyclerView.f1793d;
        this.f1917f = recyclerView.getWidth();
        this.f1918g = recyclerView.getHeight();
    }

    public abstract void a(String str);

    public abstract boolean b();

    public abstract boolean c();

    public boolean d(w wVar) {
        return wVar != null;
    }

    public abstract int f(E e2);

    public abstract void g(E e2);

    public abstract int h(E e2);

    public abstract int i(E e2);

    public abstract void j(E e2);

    public abstract int k(E e2);

    public abstract w l();

    public w m(Context context, AttributeSet attributeSet) {
        return new w(context, attributeSet);
    }

    public w n(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof w ? new w((w) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new w((ViewGroup.MarginLayoutParams) layoutParams) : new w(layoutParams);
    }

    public final View o(int i2) {
        f0.b bVar = this.f1912a;
        if (bVar != null) {
            return bVar.c(i2);
        }
        return null;
    }

    public final int p() {
        f0.b bVar = this.f1912a;
        if (bVar != null) {
            return bVar.d();
        }
        return 0;
    }

    public int q(B b2, E e2) {
        RecyclerView recyclerView = this.f1913b;
        if (recyclerView == null) {
            return 1;
        }
        recyclerView.getClass();
        return 1;
    }

    public final int s() {
        RecyclerView recyclerView = this.f1913b;
        if (recyclerView != null) {
            return recyclerView.getPaddingBottom();
        }
        return 0;
    }

    public final int t() {
        RecyclerView recyclerView = this.f1913b;
        if (recyclerView != null) {
            return recyclerView.getPaddingLeft();
        }
        return 0;
    }

    public final int u() {
        RecyclerView recyclerView = this.f1913b;
        if (recyclerView != null) {
            return recyclerView.getPaddingRight();
        }
        return 0;
    }

    public final int v() {
        RecyclerView recyclerView = this.f1913b;
        if (recyclerView != null) {
            return recyclerView.getPaddingTop();
        }
        return 0;
    }

    public int y(B b2, E e2) {
        RecyclerView recyclerView = this.f1913b;
        if (recyclerView == null) {
            return 1;
        }
        recyclerView.getClass();
        return 1;
    }

    public abstract boolean z();
}
