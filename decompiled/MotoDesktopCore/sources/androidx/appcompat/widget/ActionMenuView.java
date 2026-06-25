package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.view.menu.ActionMenuItemView;

/* JADX INFO: loaded from: classes.dex */
public class ActionMenuView extends LinearLayoutCompat implements androidx.appcompat.view.menu.n, androidx.appcompat.view.menu.C {

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public InterfaceC0078o f917A;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public androidx.appcompat.view.menu.o f918p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public Context f919q;
    public int r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public boolean f920s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public C0074k f921t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public androidx.appcompat.view.menu.z f922u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public androidx.appcompat.view.menu.m f923v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public boolean f924w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public int f925x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public final int f926y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public final int f927z;

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        setBaselineAligned(false);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.f926y = (int) (56.0f * f2);
        this.f927z = (int) (f2 * 4.0f);
        this.f919q = context;
        this.r = 0;
    }

    public static C0077n j() {
        C0077n c0077n = new C0077n(-2, -2);
        c0077n.f1270c = false;
        c0077n.f1035b = 16;
        return c0077n;
    }

    public static C0077n k(ViewGroup.LayoutParams layoutParams) {
        C0077n c0077n;
        if (layoutParams == null) {
            return j();
        }
        if (layoutParams instanceof C0077n) {
            C0077n c0077n2 = (C0077n) layoutParams;
            c0077n = new C0077n(c0077n2);
            c0077n.f1270c = c0077n2.f1270c;
        } else {
            c0077n = new C0077n(layoutParams);
        }
        if (c0077n.f1035b <= 0) {
            c0077n.f1035b = 16;
        }
        return c0077n;
    }

    @Override // androidx.appcompat.view.menu.n
    public final boolean a(androidx.appcompat.view.menu.q qVar) {
        return this.f918p.q(qVar, null, 0);
    }

    @Override // androidx.appcompat.view.menu.C
    public final void b(androidx.appcompat.view.menu.o oVar) {
        this.f918p = oVar;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && (layoutParams instanceof C0077n);
    }

    @Override // android.view.View
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat
    /* JADX INFO: renamed from: f */
    public final /* bridge */ /* synthetic */ M generateDefaultLayoutParams() {
        return j();
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat
    /* JADX INFO: renamed from: g */
    public final M generateLayoutParams(AttributeSet attributeSet) {
        return new C0077n(getContext(), attributeSet);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return j();
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new C0077n(getContext(), attributeSet);
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return k(layoutParams);
    }

    public Menu getMenu() {
        if (this.f918p == null) {
            Context context = getContext();
            androidx.appcompat.view.menu.o oVar = new androidx.appcompat.view.menu.o(context);
            this.f918p = oVar;
            oVar.f785e = new e0.k(this);
            C0074k c0074k = new C0074k(context);
            this.f921t = c0074k;
            c0074k.f1236l = true;
            c0074k.f1237m = true;
            androidx.appcompat.view.menu.z c0076m = this.f922u;
            if (c0076m == null) {
                c0076m = new C0076m();
            }
            c0074k.f1229e = c0076m;
            this.f918p.b(c0074k, this.f919q);
            C0074k c0074k2 = this.f921t;
            c0074k2.f1232h = this;
            this.f918p = c0074k2.f1227c;
        }
        return this.f918p;
    }

    public Drawable getOverflowIcon() {
        getMenu();
        C0074k c0074k = this.f921t;
        C0072i c0072i = c0074k.f1233i;
        if (c0072i != null) {
            return c0072i.getDrawable();
        }
        if (c0074k.f1235k) {
            return c0074k.f1234j;
        }
        return null;
    }

    public int getPopupTheme() {
        return this.r;
    }

    public int getWindowAnimations() {
        return 0;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat
    /* JADX INFO: renamed from: h */
    public final /* bridge */ /* synthetic */ M generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return k(layoutParams);
    }

    public final boolean l(int i2) {
        boolean zA = false;
        if (i2 == 0) {
            return false;
        }
        KeyEvent.Callback childAt = getChildAt(i2 - 1);
        KeyEvent.Callback childAt2 = getChildAt(i2);
        if (i2 < getChildCount() && (childAt instanceof InterfaceC0075l)) {
            zA = ((InterfaceC0075l) childAt).a();
        }
        return (i2 <= 0 || !(childAt2 instanceof InterfaceC0075l)) ? zA : zA | ((InterfaceC0075l) childAt2).b();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        C0074k c0074k = this.f921t;
        if (c0074k != null) {
            c0074k.f();
            if (this.f921t.k()) {
                this.f921t.j();
                this.f921t.l();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C0074k c0074k = this.f921t;
        if (c0074k != null) {
            c0074k.j();
            C0069f c0069f = c0074k.f1244u;
            if (c0069f == null || !c0069f.b()) {
                return;
            }
            c0069f.f854j.dismiss();
        }
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int width;
        int paddingLeft;
        if (!this.f924w) {
            super.onLayout(z2, i2, i3, i4, i5);
            return;
        }
        int childCount = getChildCount();
        int i6 = (i5 - i3) / 2;
        int dividerWidth = getDividerWidth();
        int i7 = i4 - i2;
        int paddingRight = (i7 - getPaddingRight()) - getPaddingLeft();
        boolean zA = y0.a(this);
        int i8 = 0;
        int i9 = 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                C0077n c0077n = (C0077n) childAt.getLayoutParams();
                if (c0077n.f1270c) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (l(i10)) {
                        measuredWidth += dividerWidth;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (zA) {
                        paddingLeft = getPaddingLeft() + ((ViewGroup.MarginLayoutParams) c0077n).leftMargin;
                        width = paddingLeft + measuredWidth;
                    } else {
                        width = (getWidth() - getPaddingRight()) - ((ViewGroup.MarginLayoutParams) c0077n).rightMargin;
                        paddingLeft = width - measuredWidth;
                    }
                    int i11 = i6 - (measuredHeight / 2);
                    childAt.layout(paddingLeft, i11, width, measuredHeight + i11);
                    paddingRight -= measuredWidth;
                    i8 = 1;
                } else {
                    paddingRight -= (childAt.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) c0077n).leftMargin) + ((ViewGroup.MarginLayoutParams) c0077n).rightMargin;
                    l(i10);
                    i9++;
                }
            }
        }
        if (childCount == 1 && i8 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth2 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i12 = (i7 / 2) - (measuredWidth2 / 2);
            int i13 = i6 - (measuredHeight2 / 2);
            childAt2.layout(i12, i13, measuredWidth2 + i12, measuredHeight2 + i13);
            return;
        }
        int i14 = i9 - (i8 ^ 1);
        int iMax = Math.max(0, i14 > 0 ? paddingRight / i14 : 0);
        if (zA) {
            int width2 = getWidth() - getPaddingRight();
            for (int i15 = 0; i15 < childCount; i15++) {
                View childAt3 = getChildAt(i15);
                C0077n c0077n2 = (C0077n) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !c0077n2.f1270c) {
                    int i16 = width2 - ((ViewGroup.MarginLayoutParams) c0077n2).rightMargin;
                    int measuredWidth3 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i17 = i6 - (measuredHeight3 / 2);
                    childAt3.layout(i16 - measuredWidth3, i17, i16, measuredHeight3 + i17);
                    width2 = i16 - ((measuredWidth3 + ((ViewGroup.MarginLayoutParams) c0077n2).leftMargin) + iMax);
                }
            }
            return;
        }
        int paddingLeft2 = getPaddingLeft();
        for (int i18 = 0; i18 < childCount; i18++) {
            View childAt4 = getChildAt(i18);
            C0077n c0077n3 = (C0077n) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !c0077n3.f1270c) {
                int i19 = paddingLeft2 + ((ViewGroup.MarginLayoutParams) c0077n3).leftMargin;
                int measuredWidth4 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i20 = i6 - (measuredHeight4 / 2);
                childAt4.layout(i19, i20, i19 + measuredWidth4, measuredHeight4 + i20);
                paddingLeft2 = measuredWidth4 + ((ViewGroup.MarginLayoutParams) c0077n3).rightMargin + iMax + i19;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v26 */
    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public final void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        boolean z2;
        int i7;
        int i8;
        int i9;
        int i10;
        ?? r1;
        int i11;
        int i12;
        int i13;
        androidx.appcompat.view.menu.o oVar;
        boolean z3 = this.f924w;
        boolean z4 = View.MeasureSpec.getMode(i2) == 1073741824;
        this.f924w = z4;
        if (z3 != z4) {
            this.f925x = 0;
        }
        int size = View.MeasureSpec.getSize(i2);
        if (this.f924w && (oVar = this.f918p) != null && size != this.f925x) {
            this.f925x = size;
            oVar.p(true);
        }
        int childCount = getChildCount();
        if (!this.f924w || childCount <= 0) {
            for (int i14 = 0; i14 < childCount; i14++) {
                C0077n c0077n = (C0077n) getChildAt(i14).getLayoutParams();
                ((ViewGroup.MarginLayoutParams) c0077n).rightMargin = 0;
                ((ViewGroup.MarginLayoutParams) c0077n).leftMargin = 0;
            }
            super.onMeasure(i2, i3);
            return;
        }
        int mode = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i2);
        int size3 = View.MeasureSpec.getSize(i3);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i3, paddingBottom, -2);
        int i15 = size2 - paddingRight;
        int i16 = this.f926y;
        int i17 = i15 / i16;
        int i18 = i15 % i16;
        if (i17 == 0) {
            setMeasuredDimension(i15, 0);
            return;
        }
        int i19 = (i18 / i17) + i16;
        int childCount2 = getChildCount();
        int i20 = 0;
        int iMax = 0;
        int i21 = 0;
        boolean z5 = false;
        int i22 = 0;
        int iMax2 = 0;
        long j2 = 0;
        while (true) {
            i4 = this.f927z;
            if (i21 >= childCount2) {
                break;
            }
            View childAt = getChildAt(i21);
            int i23 = size3;
            if (childAt.getVisibility() == 8) {
                i11 = i15;
                i12 = paddingBottom;
            } else {
                boolean z6 = childAt instanceof ActionMenuItemView;
                int i24 = i20 + 1;
                if (z6) {
                    childAt.setPadding(i4, 0, i4, 0);
                }
                C0077n c0077n2 = (C0077n) childAt.getLayoutParams();
                c0077n2.f1275h = false;
                c0077n2.f1272e = 0;
                c0077n2.f1271d = 0;
                c0077n2.f1273f = false;
                ((ViewGroup.MarginLayoutParams) c0077n2).leftMargin = 0;
                ((ViewGroup.MarginLayoutParams) c0077n2).rightMargin = 0;
                c0077n2.f1274g = z6 && (TextUtils.isEmpty(((ActionMenuItemView) childAt).getText()) ^ true);
                int i25 = c0077n2.f1270c ? 1 : i17;
                C0077n c0077n3 = (C0077n) childAt.getLayoutParams();
                i11 = i15;
                i12 = paddingBottom;
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(childMeasureSpec) - paddingBottom, View.MeasureSpec.getMode(childMeasureSpec));
                ActionMenuItemView actionMenuItemView = z6 ? (ActionMenuItemView) childAt : null;
                boolean z7 = actionMenuItemView != null && (TextUtils.isEmpty(actionMenuItemView.getText()) ^ true);
                if (i25 <= 0 || (z7 && i25 < 2)) {
                    i13 = 0;
                } else {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(i25 * i19, Integer.MIN_VALUE), iMakeMeasureSpec);
                    int measuredWidth = childAt.getMeasuredWidth();
                    i13 = measuredWidth / i19;
                    if (measuredWidth % i19 != 0) {
                        i13++;
                    }
                    if (z7 && i13 < 2) {
                        i13 = 2;
                    }
                }
                c0077n3.f1273f = !c0077n3.f1270c && z7;
                c0077n3.f1271d = i13;
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i13 * i19, 1073741824), iMakeMeasureSpec);
                iMax = Math.max(iMax, i13);
                if (c0077n2.f1273f) {
                    i22++;
                }
                if (c0077n2.f1270c) {
                    z5 = true;
                }
                i17 -= i13;
                iMax2 = Math.max(iMax2, childAt.getMeasuredHeight());
                if (i13 == 1) {
                    j2 |= (long) (1 << i21);
                }
                i20 = i24;
            }
            i21++;
            size3 = i23;
            paddingBottom = i12;
            i15 = i11;
        }
        int i26 = i15;
        int i27 = size3;
        int i28 = iMax2;
        boolean z8 = z5 && i20 == 2;
        boolean z9 = false;
        while (i22 > 0 && i17 > 0) {
            int i29 = Integer.MAX_VALUE;
            int i30 = 0;
            int i31 = 0;
            long j3 = 0;
            while (i31 < childCount2) {
                int i32 = i28;
                C0077n c0077n4 = (C0077n) getChildAt(i31).getLayoutParams();
                boolean z10 = z9;
                if (c0077n4.f1273f) {
                    int i33 = c0077n4.f1271d;
                    if (i33 < i29) {
                        j3 = 1 << i31;
                        i29 = i33;
                        i30 = 1;
                    } else if (i33 == i29) {
                        i30++;
                        j3 |= 1 << i31;
                    }
                }
                i31++;
                z9 = z10;
                i28 = i32;
            }
            i6 = i28;
            z2 = z9;
            j2 |= j3;
            if (i30 > i17) {
                i5 = mode;
                break;
            }
            int i34 = i29 + 1;
            int i35 = 0;
            while (i35 < childCount2) {
                View childAt2 = getChildAt(i35);
                C0077n c0077n5 = (C0077n) childAt2.getLayoutParams();
                int i36 = mode;
                int i37 = childMeasureSpec;
                int i38 = childCount2;
                long j4 = 1 << i35;
                if ((j3 & j4) != 0) {
                    if (z8 && c0077n5.f1274g) {
                        r1 = 1;
                        r1 = 1;
                        if (i17 == 1) {
                            childAt2.setPadding(i4 + i19, 0, i4, 0);
                        }
                    } else {
                        r1 = 1;
                    }
                    c0077n5.f1271d += r1;
                    c0077n5.f1275h = r1;
                    i17--;
                } else if (c0077n5.f1271d == i34) {
                    j2 |= j4;
                }
                i35++;
                childMeasureSpec = i37;
                mode = i36;
                childCount2 = i38;
            }
            i28 = i6;
            z9 = true;
        }
        i5 = mode;
        i6 = i28;
        z2 = z9;
        int i39 = childMeasureSpec;
        int i40 = childCount2;
        boolean z11 = !z5 && i20 == 1;
        if (i17 <= 0 || j2 == 0 || (i17 >= i20 - 1 && !z11 && iMax <= 1)) {
            i7 = i40;
        } else {
            float fBitCount = Long.bitCount(j2);
            if (!z11) {
                if ((j2 & 1) != 0 && !((C0077n) getChildAt(0).getLayoutParams()).f1274g) {
                    fBitCount -= 0.5f;
                }
                int i41 = i40 - 1;
                if ((j2 & ((long) (1 << i41))) != 0 && !((C0077n) getChildAt(i41).getLayoutParams()).f1274g) {
                    fBitCount -= 0.5f;
                }
            }
            int i42 = fBitCount > 0.0f ? (int) ((i17 * i19) / fBitCount) : 0;
            i7 = i40;
            for (int i43 = 0; i43 < i7; i43++) {
                if ((j2 & ((long) (1 << i43))) != 0) {
                    View childAt3 = getChildAt(i43);
                    C0077n c0077n6 = (C0077n) childAt3.getLayoutParams();
                    if (childAt3 instanceof ActionMenuItemView) {
                        c0077n6.f1272e = i42;
                        c0077n6.f1275h = true;
                        if (i43 == 0 && !c0077n6.f1274g) {
                            ((ViewGroup.MarginLayoutParams) c0077n6).leftMargin = (-i42) / 2;
                        }
                        z2 = true;
                    } else if (c0077n6.f1270c) {
                        c0077n6.f1272e = i42;
                        c0077n6.f1275h = true;
                        ((ViewGroup.MarginLayoutParams) c0077n6).rightMargin = (-i42) / 2;
                        z2 = true;
                    } else {
                        if (i43 != 0) {
                            ((ViewGroup.MarginLayoutParams) c0077n6).leftMargin = i42 / 2;
                        }
                        if (i43 != i7 - 1) {
                            ((ViewGroup.MarginLayoutParams) c0077n6).rightMargin = i42 / 2;
                        }
                    }
                }
            }
        }
        if (z2) {
            int i44 = 0;
            while (i44 < i7) {
                View childAt4 = getChildAt(i44);
                C0077n c0077n7 = (C0077n) childAt4.getLayoutParams();
                if (c0077n7.f1275h) {
                    i10 = i39;
                    childAt4.measure(View.MeasureSpec.makeMeasureSpec((c0077n7.f1271d * i19) + c0077n7.f1272e, 1073741824), i10);
                } else {
                    i10 = i39;
                }
                i44++;
                i39 = i10;
            }
        }
        if (i5 != 1073741824) {
            i9 = i26;
            i8 = i6;
        } else {
            i8 = i27;
            i9 = i26;
        }
        setMeasuredDimension(i9, i8);
    }

    public void setExpandedActionViewsExclusive(boolean z2) {
        this.f921t.f1241q = z2;
    }

    public void setOnMenuItemClickListener(InterfaceC0078o interfaceC0078o) {
        this.f917A = interfaceC0078o;
    }

    public void setOverflowIcon(Drawable drawable) {
        getMenu();
        C0074k c0074k = this.f921t;
        C0072i c0072i = c0074k.f1233i;
        if (c0072i != null) {
            c0072i.setImageDrawable(drawable);
        } else {
            c0074k.f1235k = true;
            c0074k.f1234j = drawable;
        }
    }

    public void setOverflowReserved(boolean z2) {
        this.f920s = z2;
    }

    public void setPopupTheme(int i2) {
        if (this.r != i2) {
            this.r = i2;
            if (i2 == 0) {
                this.f919q = getContext();
            } else {
                this.f919q = new ContextThemeWrapper(getContext(), i2);
            }
        }
    }

    public void setPresenter(C0074k c0074k) {
        this.f921t = c0074k;
        c0074k.f1232h = this;
        this.f918p = c0074k.f1227c;
    }
}
