package com.google.android.material.bottomsheet;

import K.b;
import K.e;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R$dimen;
import com.google.android.material.R$styleable;
import e0.k;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;
import k.AbstractC0143b;
import v.l;
import x.C0165b;

/* JADX INFO: loaded from: classes.dex */
public class BottomSheetBehavior<V extends View> extends AbstractC0143b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final boolean f2108a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final float f2109b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2110c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f2111d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f2112e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f2113f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f2114g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f2115h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f2116i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f2117j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final boolean f2118k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f2119l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public C0165b f2120m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public boolean f2121n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public int f2122o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public boolean f2123p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public int f2124q;
    public WeakReference r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public WeakReference f2125s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public k f2126t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public VelocityTracker f2127u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public int f2128v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public int f2129w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public boolean f2130x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public HashMap f2131y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public final b f2132z;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final int f2133c;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f2133c = parcel.readInt();
        }

        public SavedState(android.view.AbsSavedState absSavedState, int i2) {
            super(absSavedState);
            this.f2133c = i2;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f2133c);
        }
    }

    public BottomSheetBehavior() {
        this.f2108a = true;
        this.f2119l = 4;
        this.f2132z = new b(this);
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        int i2;
        this.f2108a = true;
        this.f2119l = 4;
        this.f2132z = new b(this);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BottomSheetBehavior_Layout);
        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(R$styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (typedValuePeekValue == null || (i2 = typedValuePeekValue.data) != -1) {
            u(typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        } else {
            u(i2);
        }
        this.f2117j = typedArrayObtainStyledAttributes.getBoolean(R$styleable.BottomSheetBehavior_Layout_behavior_hideable, false);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R$styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true);
        if (this.f2108a != z2) {
            this.f2108a = z2;
            if (this.r != null) {
                if (z2) {
                    this.f2116i = Math.max(this.f2124q - this.f2113f, this.f2114g);
                } else {
                    this.f2116i = this.f2124q - this.f2113f;
                }
            }
            v((this.f2108a && this.f2119l == 6) ? 3 : this.f2119l);
        }
        this.f2118k = typedArrayObtainStyledAttributes.getBoolean(R$styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false);
        typedArrayObtainStyledAttributes.recycle();
        this.f2109b = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    public static View s(View view) {
        WeakHashMap weakHashMap = l.f2836a;
        if (view.isNestedScrollingEnabled()) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View viewS = s(viewGroup.getChildAt(i2));
            if (viewS != null) {
                return viewS;
            }
        }
        return null;
    }

    @Override // k.AbstractC0143b
    public final boolean e(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        C0165b c0165b;
        if (!view.isShown()) {
            this.f2121n = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.f2128v = -1;
            VelocityTracker velocityTracker = this.f2127u;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.f2127u = null;
            }
        }
        if (this.f2127u == null) {
            this.f2127u = VelocityTracker.obtain();
        }
        this.f2127u.addMovement(motionEvent);
        if (actionMasked == 0) {
            int x2 = (int) motionEvent.getX();
            this.f2129w = (int) motionEvent.getY();
            WeakReference weakReference = this.f2125s;
            View view2 = weakReference != null ? (View) weakReference.get() : null;
            if (view2 != null && coordinatorLayout.n(view2, x2, this.f2129w)) {
                this.f2128v = motionEvent.getPointerId(motionEvent.getActionIndex());
                this.f2130x = true;
            }
            this.f2121n = this.f2128v == -1 && !coordinatorLayout.n(view, x2, this.f2129w);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.f2130x = false;
            this.f2128v = -1;
            if (this.f2121n) {
                this.f2121n = false;
                return false;
            }
        }
        if (!this.f2121n && (c0165b = this.f2120m) != null && c0165b.r(motionEvent)) {
            return true;
        }
        WeakReference weakReference2 = this.f2125s;
        View view3 = weakReference2 != null ? (View) weakReference2.get() : null;
        return (actionMasked != 2 || view3 == null || this.f2121n || this.f2119l == 1 || coordinatorLayout.n(view3, (int) motionEvent.getX(), (int) motionEvent.getY()) || this.f2120m == null || Math.abs(((float) this.f2129w) - motionEvent.getY()) <= ((float) this.f2120m.f2844b)) ? false : true;
    }

    @Override // k.AbstractC0143b
    public final boolean f(CoordinatorLayout coordinatorLayout, View view, int i2) {
        WeakHashMap weakHashMap = l.f2836a;
        if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
            view.setFitsSystemWindows(true);
        }
        int top = view.getTop();
        coordinatorLayout.p(view, i2);
        this.f2124q = coordinatorLayout.getHeight();
        if (this.f2111d) {
            if (this.f2112e == 0) {
                this.f2112e = coordinatorLayout.getResources().getDimensionPixelSize(R$dimen.design_bottom_sheet_peek_height_min);
            }
            this.f2113f = Math.max(this.f2112e, this.f2124q - ((coordinatorLayout.getWidth() * 9) / 16));
        } else {
            this.f2113f = this.f2110c;
        }
        int iMax = Math.max(0, this.f2124q - view.getHeight());
        this.f2114g = iMax;
        int i3 = this.f2124q;
        this.f2115h = i3 / 2;
        if (this.f2108a) {
            this.f2116i = Math.max(i3 - this.f2113f, iMax);
        } else {
            this.f2116i = i3 - this.f2113f;
        }
        int i4 = this.f2119l;
        if (i4 == 3) {
            view.offsetTopAndBottom(t());
        } else if (i4 == 6) {
            view.offsetTopAndBottom(this.f2115h);
        } else if (this.f2117j && i4 == 5) {
            view.offsetTopAndBottom(this.f2124q);
        } else if (i4 == 4) {
            view.offsetTopAndBottom(this.f2116i);
        } else if (i4 == 1 || i4 == 2) {
            view.offsetTopAndBottom(top - view.getTop());
        }
        if (this.f2120m == null) {
            this.f2120m = new C0165b(coordinatorLayout.getContext(), coordinatorLayout, this.f2132z);
        }
        this.r = new WeakReference(view);
        this.f2125s = new WeakReference(s(view));
        return true;
    }

    @Override // k.AbstractC0143b
    public final boolean h(View view) {
        return view == this.f2125s.get() && this.f2119l != 3;
    }

    @Override // k.AbstractC0143b
    public final void i(CoordinatorLayout coordinatorLayout, View view, View view2, int i2, int[] iArr, int i3) {
        if (i3 != 1 && view2 == ((View) this.f2125s.get())) {
            int top = view.getTop();
            int i4 = top - i2;
            if (i2 > 0) {
                if (i4 < t()) {
                    int iT = top - t();
                    iArr[1] = iT;
                    WeakHashMap weakHashMap = l.f2836a;
                    view.offsetTopAndBottom(-iT);
                    v(3);
                } else {
                    iArr[1] = i2;
                    WeakHashMap weakHashMap2 = l.f2836a;
                    view.offsetTopAndBottom(-i2);
                    v(1);
                }
            } else if (i2 < 0 && !view2.canScrollVertically(-1)) {
                int i5 = this.f2116i;
                if (i4 <= i5 || this.f2117j) {
                    iArr[1] = i2;
                    WeakHashMap weakHashMap3 = l.f2836a;
                    view.offsetTopAndBottom(-i2);
                    v(1);
                } else {
                    int i6 = top - i5;
                    iArr[1] = i6;
                    WeakHashMap weakHashMap4 = l.f2836a;
                    view.offsetTopAndBottom(-i6);
                    v(4);
                }
            }
            view.getTop();
            this.f2122o = i2;
            this.f2123p = true;
        }
    }

    @Override // k.AbstractC0143b
    public final void m(View view, Parcelable parcelable) {
        int i2 = ((SavedState) parcelable).f2133c;
        if (i2 == 1 || i2 == 2) {
            this.f2119l = 4;
        } else {
            this.f2119l = i2;
        }
    }

    @Override // k.AbstractC0143b
    public final Parcelable n(View view) {
        return new SavedState(View.BaseSavedState.EMPTY_STATE, this.f2119l);
    }

    @Override // k.AbstractC0143b
    public final boolean p(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i2, int i3) {
        this.f2122o = 0;
        this.f2123p = false;
        return (i2 & 2) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004b  */
    @Override // k.AbstractC0143b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void q(androidx.coordinatorlayout.widget.CoordinatorLayout r4, android.view.View r5, android.view.View r6, int r7) {
        /*
            Method dump skipped, instruction units count: 207
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.q(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.View, int):void");
    }

    @Override // k.AbstractC0143b
    public final boolean r(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (!view.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.f2119l == 1 && actionMasked == 0) {
            return true;
        }
        C0165b c0165b = this.f2120m;
        if (c0165b != null) {
            c0165b.k(motionEvent);
        }
        if (actionMasked == 0) {
            this.f2128v = -1;
            VelocityTracker velocityTracker = this.f2127u;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.f2127u = null;
            }
        }
        if (this.f2127u == null) {
            this.f2127u = VelocityTracker.obtain();
        }
        this.f2127u.addMovement(motionEvent);
        if (actionMasked == 2 && !this.f2121n) {
            float fAbs = Math.abs(this.f2129w - motionEvent.getY());
            C0165b c0165b2 = this.f2120m;
            if (fAbs > c0165b2.f2844b) {
                c0165b2.b(view, motionEvent.getPointerId(motionEvent.getActionIndex()));
            }
        }
        return !this.f2121n;
    }

    public final int t() {
        if (this.f2108a) {
            return this.f2114g;
        }
        return 0;
    }

    public final void u(int i2) {
        WeakReference weakReference;
        View view;
        if (i2 == -1) {
            if (this.f2111d) {
                return;
            } else {
                this.f2111d = true;
            }
        } else {
            if (!this.f2111d && this.f2110c == i2) {
                return;
            }
            this.f2111d = false;
            this.f2110c = Math.max(0, i2);
            this.f2116i = this.f2124q - i2;
        }
        if (this.f2119l != 4 || (weakReference = this.r) == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        view.requestLayout();
    }

    public final void v(int i2) {
        k kVar;
        if (this.f2119l == i2) {
            return;
        }
        this.f2119l = i2;
        if (i2 == 6 || i2 == 3) {
            y(true);
        } else if (i2 == 5 || i2 == 4) {
            y(false);
        }
        if (((View) this.r.get()) == null || (kVar = this.f2126t) == null) {
            return;
        }
        if (i2 == 5) {
            ((e) kVar.f2495a).cancel();
        } else {
            kVar.getClass();
        }
    }

    public final boolean w(View view, float f2) {
        if (this.f2118k) {
            return true;
        }
        if (view.getTop() < this.f2116i) {
            return false;
        }
        return Math.abs(((f2 * 0.1f) + ((float) view.getTop())) - ((float) this.f2116i)) / ((float) this.f2110c) > 0.5f;
    }

    public final void x(View view, int i2) {
        int iT;
        int i3;
        if (i2 == 4) {
            iT = this.f2116i;
        } else if (i2 == 6) {
            iT = this.f2115h;
            if (this.f2108a && iT <= (i3 = this.f2114g)) {
                i2 = 3;
                iT = i3;
            }
        } else if (i2 == 3) {
            iT = t();
        } else {
            if (!this.f2117j || i2 != 5) {
                throw new IllegalArgumentException("Illegal state argument: " + i2);
            }
            iT = this.f2124q;
        }
        C0165b c0165b = this.f2120m;
        int left = view.getLeft();
        c0165b.r = view;
        c0165b.f2845c = -1;
        boolean zI = c0165b.i(left, iT, 0, 0);
        if (!zI && c0165b.f2843a == 0 && c0165b.r != null) {
            c0165b.r = null;
        }
        if (!zI) {
            v(i2);
            return;
        }
        v(2);
        K.a aVar = new K.a(this, view, i2);
        WeakHashMap weakHashMap = l.f2836a;
        view.postOnAnimation(aVar);
    }

    public final void y(boolean z2) {
        WeakReference weakReference = this.r;
        if (weakReference == null) {
            return;
        }
        ViewParent parent = ((View) weakReference.get()).getParent();
        if (parent instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
            int childCount = coordinatorLayout.getChildCount();
            if (z2) {
                if (this.f2131y != null) {
                    return;
                } else {
                    this.f2131y = new HashMap(childCount);
                }
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = coordinatorLayout.getChildAt(i2);
                if (childAt != this.r.get()) {
                    if (z2) {
                        this.f2131y.put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                        WeakHashMap weakHashMap = l.f2836a;
                        childAt.setImportantForAccessibility(4);
                    } else {
                        HashMap map = this.f2131y;
                        if (map != null && map.containsKey(childAt)) {
                            int iIntValue = ((Integer) this.f2131y.get(childAt)).intValue();
                            WeakHashMap weakHashMap2 = l.f2836a;
                            childAt.setImportantForAccessibility(iIntValue);
                        }
                    }
                }
            }
            if (z2) {
                return;
            }
            this.f2131y = null;
        }
    }
}
