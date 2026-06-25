package com.google.android.material.appbar;

import I.c;
import I.d;
import I.f;
import I.g;
import I.i;
import android.R;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.app.AbstractC0054a;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R$attr;
import com.google.android.material.R$integer;
import com.google.android.material.R$styleable;
import h.k;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import k.AbstractC0143b;
import k.C0146e;
import k.InterfaceC0144c;
import v.e;
import v.l;

/* JADX INFO: loaded from: classes.dex */
@InterfaceC0144c(Behavior.class)
public class AppBarLayout extends LinearLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2024a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2025b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2026c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f2027d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f2028e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public ArrayList f2029f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f2030g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f2031h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f2032i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public int[] f2033j;

    public static class BaseBehavior<T extends AppBarLayout> extends f {

        /* JADX INFO: renamed from: j, reason: collision with root package name */
        public int f2034j;

        /* JADX INFO: renamed from: k, reason: collision with root package name */
        public int f2035k;

        /* JADX INFO: renamed from: l, reason: collision with root package name */
        public ValueAnimator f2036l;

        /* JADX INFO: renamed from: m, reason: collision with root package name */
        public int f2037m;

        /* JADX INFO: renamed from: n, reason: collision with root package name */
        public boolean f2038n;

        /* JADX INFO: renamed from: o, reason: collision with root package name */
        public float f2039o;

        /* JADX INFO: renamed from: p, reason: collision with root package name */
        public WeakReference f2040p;

        public static class SavedState extends AbsSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new b();

            /* JADX INFO: renamed from: c, reason: collision with root package name */
            public int f2041c;

            /* JADX INFO: renamed from: d, reason: collision with root package name */
            public float f2042d;

            /* JADX INFO: renamed from: e, reason: collision with root package name */
            public boolean f2043e;

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                this.f2041c = parcel.readInt();
                this.f2042d = parcel.readFloat();
                this.f2043e = parcel.readByte() != 0;
            }

            @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
            public final void writeToParcel(Parcel parcel, int i2) {
                super.writeToParcel(parcel, i2);
                parcel.writeInt(this.f2041c);
                parcel.writeFloat(this.f2042d);
                parcel.writeByte(this.f2043e ? (byte) 1 : (byte) 0);
            }
        }

        public BaseBehavior() {
            this.f169f = -1;
            this.f171h = -1;
            this.f2037m = -1;
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(0);
            this.f169f = -1;
            this.f171h = -1;
            this.f2037m = -1;
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x005d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public static void A(androidx.coordinatorlayout.widget.CoordinatorLayout r8, com.google.android.material.appbar.AppBarLayout r9, int r10, int r11, boolean r12) {
            /*
                Method dump skipped, instruction units count: 203
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.BaseBehavior.A(androidx.coordinatorlayout.widget.CoordinatorLayout, com.google.android.material.appbar.AppBarLayout, int, int, boolean):void");
        }

        @Override // I.h, k.AbstractC0143b
        public final boolean f(CoordinatorLayout coordinatorLayout, View view, int i2) {
            int iRound;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            super.f(coordinatorLayout, appBarLayout, i2);
            int pendingAction = appBarLayout.getPendingAction();
            int i3 = this.f2037m;
            if (i3 >= 0 && (pendingAction & 8) == 0) {
                View childAt = appBarLayout.getChildAt(i3);
                int i4 = -childAt.getBottom();
                if (this.f2038n) {
                    WeakHashMap weakHashMap = l.f2836a;
                    iRound = appBarLayout.getTopInset() + childAt.getMinimumHeight() + i4;
                } else {
                    iRound = Math.round(childAt.getHeight() * this.f2039o) + i4;
                }
                w(coordinatorLayout, appBarLayout, iRound, Integer.MIN_VALUE, Integer.MAX_VALUE);
            } else if (pendingAction != 0) {
                boolean z2 = (pendingAction & 4) != 0;
                if ((pendingAction & 2) != 0) {
                    int i5 = -appBarLayout.getUpNestedPreScrollRange();
                    if (z2) {
                        x(coordinatorLayout, appBarLayout, i5);
                    } else {
                        w(coordinatorLayout, appBarLayout, i5, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    }
                } else if ((pendingAction & 1) != 0) {
                    if (z2) {
                        x(coordinatorLayout, appBarLayout, 0);
                    } else {
                        w(coordinatorLayout, appBarLayout, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    }
                }
            }
            appBarLayout.f2028e = 0;
            this.f2037m = -1;
            int iG = AbstractC0054a.g(s(), -appBarLayout.getTotalScrollRange(), 0);
            i iVar = this.f177a;
            if (iVar != null) {
                iVar.a(iG);
            } else {
                this.f178b = iG;
            }
            A(coordinatorLayout, appBarLayout, s(), 0, true);
            appBarLayout.a(s());
            return true;
        }

        @Override // k.AbstractC0143b
        public final boolean g(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, int i4) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (((ViewGroup.MarginLayoutParams) ((C0146e) appBarLayout.getLayoutParams())).height != -2) {
                return false;
            }
            coordinatorLayout.q(appBarLayout, i2, i3, View.MeasureSpec.makeMeasureSpec(0, 0));
            return true;
        }

        @Override // k.AbstractC0143b
        public final void i(CoordinatorLayout coordinatorLayout, View view, View view2, int i2, int[] iArr, int i3) {
            int i4;
            int downNestedPreScrollRange;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (i2 != 0) {
                if (i2 < 0) {
                    i4 = -appBarLayout.getTotalScrollRange();
                    downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange() + i4;
                } else {
                    i4 = -appBarLayout.getUpNestedPreScrollRange();
                    downNestedPreScrollRange = 0;
                }
                int i5 = i4;
                int i6 = downNestedPreScrollRange;
                if (i5 != i6) {
                    iArr[1] = w(coordinatorLayout, appBarLayout, v() - i2, i5, i6);
                    z(i2, appBarLayout, view2, i3);
                }
            }
        }

        @Override // k.AbstractC0143b
        public final void k(CoordinatorLayout coordinatorLayout, View view, View view2, int i2, int i3, int i4, int i5, int i6) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (i5 < 0) {
                w(coordinatorLayout, appBarLayout, v() - i5, -appBarLayout.getDownNestedScrollRange(), 0);
                z(i5, appBarLayout, view2, i6);
            }
            if (appBarLayout.f2032i) {
                boolean z2 = view2.getScrollY() > 0;
                if (appBarLayout.f2031h != z2) {
                    appBarLayout.f2031h = z2;
                    appBarLayout.refreshDrawableState();
                }
            }
        }

        @Override // k.AbstractC0143b
        public final void m(View view, Parcelable parcelable) {
            if (!(parcelable instanceof SavedState)) {
                this.f2037m = -1;
                return;
            }
            SavedState savedState = (SavedState) parcelable;
            this.f2037m = savedState.f2041c;
            this.f2039o = savedState.f2042d;
            this.f2038n = savedState.f2043e;
        }

        @Override // k.AbstractC0143b
        public final Parcelable n(View view) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            android.view.AbsSavedState absSavedState = View.BaseSavedState.EMPTY_STATE;
            int iS = s();
            int childCount = appBarLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = appBarLayout.getChildAt(i2);
                int bottom = childAt.getBottom() + iS;
                if (childAt.getTop() + iS <= 0 && bottom >= 0) {
                    SavedState savedState = new SavedState(absSavedState);
                    savedState.f2041c = i2;
                    WeakHashMap weakHashMap = l.f2836a;
                    savedState.f2043e = bottom == appBarLayout.getTopInset() + childAt.getMinimumHeight();
                    savedState.f2042d = bottom / childAt.getHeight();
                    return savedState;
                }
            }
            return absSavedState;
        }

        @Override // k.AbstractC0143b
        public final boolean p(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i2, int i3) {
            ValueAnimator valueAnimator;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            boolean z2 = (i2 & 2) != 0 && (appBarLayout.f2032i || (appBarLayout.getTotalScrollRange() != 0 && coordinatorLayout.getHeight() - view2.getHeight() <= appBarLayout.getHeight()));
            if (z2 && (valueAnimator = this.f2036l) != null) {
                valueAnimator.cancel();
            }
            this.f2040p = null;
            this.f2035k = i3;
            return z2;
        }

        @Override // k.AbstractC0143b
        public final void q(CoordinatorLayout coordinatorLayout, View view, View view2, int i2) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (this.f2035k == 0 || i2 == 1) {
                y(coordinatorLayout, appBarLayout);
            }
            this.f2040p = new WeakReference(view2);
        }

        @Override // I.f
        public final boolean u(View view) {
            WeakReference weakReference = this.f2040p;
            if (weakReference == null) {
                return true;
            }
            View view2 = (View) weakReference.get();
            return (view2 == null || !view2.isShown() || view2.canScrollVertically(-1)) ? false : true;
        }

        @Override // I.f
        public final int v() {
            return s() + this.f2034j;
        }

        @Override // I.f
        public final int w(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, int i4) {
            int top;
            boolean zA;
            List list;
            int topInset;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            int iV = v();
            if (i3 == 0 || iV < i3 || iV > i4) {
                this.f2034j = 0;
                return 0;
            }
            int iG = AbstractC0054a.g(i2, i3, i4);
            if (iV == iG) {
                return 0;
            }
            if (appBarLayout.f2027d) {
                int iAbs = Math.abs(iG);
                int childCount = appBarLayout.getChildCount();
                int i5 = 0;
                while (true) {
                    if (i5 >= childCount) {
                        break;
                    }
                    View childAt = appBarLayout.getChildAt(i5);
                    I.a aVar = (I.a) childAt.getLayoutParams();
                    Interpolator interpolator = aVar.f156b;
                    if (iAbs < childAt.getTop() || iAbs > childAt.getBottom()) {
                        i5++;
                    } else if (interpolator != null) {
                        int i6 = aVar.f155a;
                        if ((i6 & 1) != 0) {
                            topInset = childAt.getHeight() + ((LinearLayout.LayoutParams) aVar).topMargin + ((LinearLayout.LayoutParams) aVar).bottomMargin;
                            if ((i6 & 2) != 0) {
                                WeakHashMap weakHashMap = l.f2836a;
                                topInset -= childAt.getMinimumHeight();
                            }
                        } else {
                            topInset = 0;
                        }
                        WeakHashMap weakHashMap2 = l.f2836a;
                        if (childAt.getFitsSystemWindows()) {
                            topInset -= appBarLayout.getTopInset();
                        }
                        if (topInset > 0) {
                            float f2 = topInset;
                            top = (childAt.getTop() + Math.round(interpolator.getInterpolation((iAbs - childAt.getTop()) / f2) * f2)) * Integer.signum(iG);
                        }
                    }
                }
                top = iG;
            } else {
                top = iG;
            }
            i iVar = this.f177a;
            if (iVar != null) {
                zA = iVar.a(top);
            } else {
                this.f178b = top;
                zA = false;
            }
            int i7 = iV - iG;
            this.f2034j = iG - top;
            if (!zA && appBarLayout.f2027d && (list = (List) ((k) coordinatorLayout.f1374b.f106b).get(appBarLayout)) != null && !list.isEmpty()) {
                for (int i8 = 0; i8 < list.size(); i8++) {
                    View view2 = (View) list.get(i8);
                    AbstractC0143b abstractC0143b = ((C0146e) view2.getLayoutParams()).f2746a;
                    if (abstractC0143b != null) {
                        abstractC0143b.d(coordinatorLayout, view2, appBarLayout);
                    }
                }
            }
            appBarLayout.a(s());
            A(coordinatorLayout, appBarLayout, iG, iG < iV ? -1 : 1, false);
            return i7;
        }

        public final void x(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i2) {
            int iAbs = Math.abs(v() - i2);
            float fAbs = Math.abs(0.0f);
            int iRound = fAbs > 0.0f ? Math.round((iAbs / fAbs) * 1000.0f) * 3 : (int) (((iAbs / appBarLayout.getHeight()) + 1.0f) * 150.0f);
            int iV = v();
            if (iV == i2) {
                ValueAnimator valueAnimator = this.f2036l;
                if (valueAnimator == null || !valueAnimator.isRunning()) {
                    return;
                }
                this.f2036l.cancel();
                return;
            }
            ValueAnimator valueAnimator2 = this.f2036l;
            if (valueAnimator2 == null) {
                ValueAnimator valueAnimator3 = new ValueAnimator();
                this.f2036l = valueAnimator3;
                valueAnimator3.setInterpolator(H.a.f145d);
                this.f2036l.addUpdateListener(new a(this, coordinatorLayout, appBarLayout));
            } else {
                valueAnimator2.cancel();
            }
            this.f2036l.setDuration(Math.min(iRound, 600));
            this.f2036l.setIntValues(iV, i2);
            this.f2036l.start();
        }

        public final void y(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            int iV = v();
            int childCount = appBarLayout.getChildCount();
            int i2 = 0;
            while (true) {
                if (i2 >= childCount) {
                    i2 = -1;
                    break;
                }
                View childAt = appBarLayout.getChildAt(i2);
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                I.a aVar = (I.a) childAt.getLayoutParams();
                if ((aVar.f155a & 32) == 32) {
                    top -= ((LinearLayout.LayoutParams) aVar).topMargin;
                    bottom += ((LinearLayout.LayoutParams) aVar).bottomMargin;
                }
                int i3 = -iV;
                if (top <= i3 && bottom >= i3) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 >= 0) {
                View childAt2 = appBarLayout.getChildAt(i2);
                I.a aVar2 = (I.a) childAt2.getLayoutParams();
                int i4 = aVar2.f155a;
                if ((i4 & 17) == 17) {
                    int i5 = -childAt2.getTop();
                    int minimumHeight = -childAt2.getBottom();
                    if (i2 == appBarLayout.getChildCount() - 1) {
                        minimumHeight += appBarLayout.getTopInset();
                    }
                    if ((i4 & 2) == 2) {
                        WeakHashMap weakHashMap = l.f2836a;
                        minimumHeight += childAt2.getMinimumHeight();
                    } else if ((i4 & 5) == 5) {
                        WeakHashMap weakHashMap2 = l.f2836a;
                        int minimumHeight2 = childAt2.getMinimumHeight() + minimumHeight;
                        if (iV < minimumHeight2) {
                            i5 = minimumHeight2;
                        } else {
                            minimumHeight = minimumHeight2;
                        }
                    }
                    if ((i4 & 32) == 32) {
                        i5 += ((LinearLayout.LayoutParams) aVar2).topMargin;
                        minimumHeight -= ((LinearLayout.LayoutParams) aVar2).bottomMargin;
                    }
                    if (iV < (minimumHeight + i5) / 2) {
                        i5 = minimumHeight;
                    }
                    x(coordinatorLayout, appBarLayout, AbstractC0054a.g(i5, -appBarLayout.getTotalScrollRange(), 0));
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void z(int i2, AppBarLayout appBarLayout, View view, int i3) {
            if (i3 == 1) {
                int iV = v();
                if ((i2 >= 0 || iV != 0) && (i2 <= 0 || iV != (-appBarLayout.getDownNestedScrollRange()))) {
                    return;
                }
                WeakHashMap weakHashMap = l.f2836a;
                if (view instanceof e) {
                    ((e) view).f(1);
                }
            }
        }
    }

    public static class Behavior extends BaseBehavior<AppBarLayout> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public static class ScrollingViewBehavior extends g {
        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(0);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ScrollingViewBehavior_Layout);
            this.f176f = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0);
            typedArrayObtainStyledAttributes.recycle();
        }

        public static AppBarLayout v(ArrayList arrayList) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = (View) arrayList.get(i2);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        @Override // k.AbstractC0143b
        public final boolean b(View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override // k.AbstractC0143b
        public final boolean d(CoordinatorLayout coordinatorLayout, View view, View view2) {
            AbstractC0143b abstractC0143b = ((C0146e) view2.getLayoutParams()).f2746a;
            if (abstractC0143b instanceof BaseBehavior) {
                int bottom = (((view2.getBottom() - view.getTop()) + ((BaseBehavior) abstractC0143b).f2034j) + this.f175e) - u(view2);
                WeakHashMap weakHashMap = l.f2836a;
                view.offsetTopAndBottom(bottom);
            }
            if (view2 instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view2;
                if (appBarLayout.f2032i) {
                    boolean z2 = view.getScrollY() > 0;
                    if (appBarLayout.f2031h != z2) {
                        appBarLayout.f2031h = z2;
                        appBarLayout.refreshDrawableState();
                    }
                }
            }
            return false;
        }

        @Override // k.AbstractC0143b
        public final boolean l(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z2) {
            AppBarLayout appBarLayoutV = v(coordinatorLayout.i(view));
            if (appBarLayoutV != null) {
                rect.offset(view.getLeft(), view.getTop());
                int width = coordinatorLayout.getWidth();
                int height = coordinatorLayout.getHeight();
                Rect rect2 = this.f173c;
                rect2.set(0, 0, width, height);
                if (!rect2.contains(rect)) {
                    appBarLayoutV.f2028e = (z2 ^ true ? 4 : 0) | 10;
                    appBarLayoutV.requestLayout();
                    return true;
                }
            }
            return false;
        }
    }

    public static I.a c(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            I.a aVar = new I.a((LinearLayout.LayoutParams) layoutParams);
            aVar.f155a = 1;
            return aVar;
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            I.a aVar2 = new I.a((ViewGroup.MarginLayoutParams) layoutParams);
            aVar2.f155a = 1;
            return aVar2;
        }
        I.a aVar3 = new I.a(layoutParams);
        aVar3.f155a = 1;
        return aVar3;
    }

    public final void a(int i2) {
        ArrayList arrayList = this.f2029f;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                d dVar = (d) this.f2029f.get(i3);
                if (dVar != null) {
                    CollapsingToolbarLayout collapsingToolbarLayout = dVar.f161a;
                    collapsingToolbarLayout.f2059p = i2;
                    int childCount = collapsingToolbarLayout.getChildCount();
                    for (int i4 = 0; i4 < childCount; i4++) {
                        View childAt = collapsingToolbarLayout.getChildAt(i4);
                        c cVar = (c) childAt.getLayoutParams();
                        i iVarB = CollapsingToolbarLayout.b(childAt);
                        int i5 = cVar.f159a;
                        if (i5 == 1) {
                            iVarB.a(AbstractC0054a.g(-i2, 0, ((collapsingToolbarLayout.getHeight() - CollapsingToolbarLayout.b(childAt).f180b) - childAt.getHeight()) - ((FrameLayout.LayoutParams) ((c) childAt.getLayoutParams())).bottomMargin));
                        } else if (i5 == 2) {
                            iVarB.a(Math.round((-i2) * cVar.f160b));
                        }
                    }
                    collapsingToolbarLayout.d();
                    collapsingToolbarLayout.getHeight();
                    WeakHashMap weakHashMap = l.f2836a;
                    collapsingToolbarLayout.getMinimumHeight();
                    Math.abs(i2);
                    throw null;
                }
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final I.a generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        I.a aVar = new I.a(context, attributeSet);
        aVar.f155a = 1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AppBarLayout_Layout);
        aVar.f155a = typedArrayObtainStyledAttributes.getInt(R$styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
        if (typedArrayObtainStyledAttributes.hasValue(R$styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
            aVar.f156b = AnimationUtils.loadInterpolator(context, typedArrayObtainStyledAttributes.getResourceId(R$styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
        }
        typedArrayObtainStyledAttributes.recycle();
        return aVar;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof I.a;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        I.a aVar = new I.a(-1, -2);
        aVar.f155a = 1;
        return aVar;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final LinearLayout.LayoutParams generateDefaultLayoutParams() {
        I.a aVar = new I.a(-1, -2);
        aVar.f155a = 1;
        return aVar;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return c(layoutParams);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return c(layoutParams);
    }

    public int getDownNestedPreScrollRange() {
        int topInset;
        int i2 = this.f2025b;
        if (i2 != -1) {
            return i2;
        }
        int minimumHeight = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            I.a aVar = (I.a) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i3 = aVar.f155a;
            if ((i3 & 5) != 5) {
                if (minimumHeight > 0) {
                    break;
                }
            } else {
                int i4 = ((LinearLayout.LayoutParams) aVar).topMargin + ((LinearLayout.LayoutParams) aVar).bottomMargin + minimumHeight;
                if ((i3 & 8) != 0) {
                    WeakHashMap weakHashMap = l.f2836a;
                    minimumHeight = childAt.getMinimumHeight() + i4;
                } else {
                    if ((i3 & 2) != 0) {
                        WeakHashMap weakHashMap2 = l.f2836a;
                        topInset = childAt.getMinimumHeight();
                    } else {
                        topInset = getTopInset();
                    }
                    minimumHeight = (measuredHeight - topInset) + i4;
                }
            }
        }
        int iMax = Math.max(0, minimumHeight);
        this.f2025b = iMax;
        return iMax;
    }

    public int getDownNestedScrollRange() {
        int i2 = this.f2026c;
        if (i2 != -1) {
            return i2;
        }
        int childCount = getChildCount();
        int i3 = 0;
        int topInset = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            }
            View childAt = getChildAt(i3);
            I.a aVar = (I.a) childAt.getLayoutParams();
            int measuredHeight = ((LinearLayout.LayoutParams) aVar).topMargin + ((LinearLayout.LayoutParams) aVar).bottomMargin + childAt.getMeasuredHeight();
            int i4 = aVar.f155a;
            if ((i4 & 1) == 0) {
                break;
            }
            topInset += measuredHeight;
            if ((i4 & 2) != 0) {
                WeakHashMap weakHashMap = l.f2836a;
                topInset -= getTopInset() + childAt.getMinimumHeight();
                break;
            }
            i3++;
        }
        int iMax = Math.max(0, topInset);
        this.f2026c = iMax;
        return iMax;
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        WeakHashMap weakHashMap = l.f2836a;
        int minimumHeight = getMinimumHeight();
        if (minimumHeight == 0) {
            int childCount = getChildCount();
            minimumHeight = childCount >= 1 ? getChildAt(childCount - 1).getMinimumHeight() : 0;
            if (minimumHeight == 0) {
                return getHeight() / 3;
            }
        }
        return (minimumHeight * 2) + topInset;
    }

    public int getPendingAction() {
        return this.f2028e;
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    public final int getTopInset() {
        return 0;
    }

    public final int getTotalScrollRange() {
        int i2 = this.f2024a;
        if (i2 != -1) {
            return i2;
        }
        int childCount = getChildCount();
        int i3 = 0;
        int minimumHeight = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            }
            View childAt = getChildAt(i3);
            I.a aVar = (I.a) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i4 = aVar.f155a;
            if ((i4 & 1) == 0) {
                break;
            }
            minimumHeight += measuredHeight + ((LinearLayout.LayoutParams) aVar).topMargin + ((LinearLayout.LayoutParams) aVar).bottomMargin;
            if ((i4 & 2) != 0) {
                WeakHashMap weakHashMap = l.f2836a;
                minimumHeight -= childAt.getMinimumHeight();
                break;
            }
            i3++;
        }
        int iMax = Math.max(0, minimumHeight - getTopInset());
        this.f2024a = iMax;
        return iMax;
    }

    public int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i2) {
        if (this.f2033j == null) {
            this.f2033j = new int[4];
        }
        int[] iArr = this.f2033j;
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i2 + iArr.length);
        boolean z2 = this.f2030g;
        int i3 = R$attr.state_liftable;
        if (!z2) {
            i3 = -i3;
        }
        iArr[0] = i3;
        iArr[1] = (z2 && this.f2031h) ? R$attr.state_lifted : -R$attr.state_lifted;
        int i4 = R$attr.state_collapsible;
        if (!z2) {
            i4 = -i4;
        }
        iArr[2] = i4;
        iArr[3] = (z2 && this.f2031h) ? R$attr.state_collapsed : -R$attr.state_collapsed;
        return LinearLayout.mergeDrawableStates(iArrOnCreateDrawableState, iArr);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        this.f2024a = -1;
        this.f2025b = -1;
        this.f2026c = -1;
        boolean z3 = false;
        this.f2027d = false;
        int childCount = getChildCount();
        int i6 = 0;
        while (true) {
            if (i6 >= childCount) {
                break;
            }
            if (((I.a) getChildAt(i6).getLayoutParams()).f156b != null) {
                this.f2027d = true;
                break;
            }
            i6++;
        }
        if (this.f2032i) {
            z3 = true;
            break;
        }
        int childCount2 = getChildCount();
        for (int i7 = 0; i7 < childCount2; i7++) {
            int i8 = ((I.a) getChildAt(i7).getLayoutParams()).f155a;
            if ((i8 & 1) == 1 && (i8 & 10) != 0) {
                z3 = true;
                break;
            }
        }
        if (this.f2030g != z3) {
            this.f2030g = z3;
            refreshDrawableState();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        this.f2024a = -1;
        this.f2025b = -1;
        this.f2026c = -1;
    }

    public void setExpanded(boolean z2) {
        WeakHashMap weakHashMap = l.f2836a;
        this.f2028e = (z2 ? 1 : 2) | (isLaidOut() ? 4 : 0) | 8;
        requestLayout();
    }

    public void setLiftOnScroll(boolean z2) {
        this.f2032i = z2;
    }

    @Override // android.widget.LinearLayout
    public void setOrientation(int i2) {
        if (i2 != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(i2);
    }

    @Deprecated
    public void setTargetElevation(float f2) {
        int integer = getResources().getInteger(R$integer.app_bar_elevation_anim_duration);
        StateListAnimator stateListAnimator = new StateListAnimator();
        long j2 = integer;
        stateListAnimator.addState(new int[]{R.attr.enabled, R$attr.state_liftable, -R$attr.state_lifted}, ObjectAnimator.ofFloat(this, "elevation", 0.0f).setDuration(j2));
        stateListAnimator.addState(new int[]{R.attr.enabled}, ObjectAnimator.ofFloat(this, "elevation", f2).setDuration(j2));
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this, "elevation", 0.0f).setDuration(0L));
        setStateListAnimator(stateListAnimator);
    }
}
