package com.google.android.material.tabs;

import C.n;
import F.g;
import W.a;
import W.b;
import W.c;
import W.d;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import androidx.viewpager.widget.ViewPager;
import b.AbstractC0122a;
import java.util.WeakHashMap;
import u.C0161b;
import v.l;

/* JADX INFO: loaded from: classes.dex */
@g
public class TabLayout extends HorizontalScrollView {
    public static final C0161b r = new C0161b(16);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public b f2195a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ColorStateList f2196b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public ColorStateList f2197c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public ColorStateList f2198d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public Drawable f2199e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f2200f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f2201g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f2202h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f2203i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f2204j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f2205k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public a f2206l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public n f2207m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public ValueAnimator f2208n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public ViewPager f2209o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public F.a f2210p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public c f2211q;

    private int getDefaultHeight() {
        throw null;
    }

    private int getTabMinWidth() {
        return 0;
    }

    private int getTabScrollRange() {
        throw null;
    }

    private void setSelectedTabView(int i2) {
        throw null;
    }

    public final void a(View view) {
        if (!(view instanceof TabItem)) {
            throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
        }
        TabItem tabItem = (TabItem) view;
        b bVar = (b) r.a();
        if (bVar == null) {
            bVar = new b();
            bVar.f245b = -1;
        }
        bVar.f246c = this;
        d dVar = new d(this, getContext());
        if (bVar != dVar.f251a) {
            dVar.f251a = bVar;
            dVar.a();
        }
        dVar.setFocusable(true);
        dVar.setMinimumWidth(getTabMinWidth());
        if (TextUtils.isEmpty(bVar.f244a)) {
            dVar.setContentDescription(null);
        } else {
            dVar.setContentDescription(bVar.f244a);
        }
        bVar.f247d = dVar;
        tabItem.getClass();
        if (TextUtils.isEmpty(tabItem.getContentDescription())) {
            throw null;
        }
        bVar.f244a = tabItem.getContentDescription();
        d dVar2 = bVar.f247d;
        if (dVar2 == null) {
            throw null;
        }
        dVar2.a();
        throw null;
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final void addView(View view) {
        a(view);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final void addView(View view, int i2) {
        a(view);
        throw null;
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        a(view);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        a(view);
    }

    public final void b(b bVar, boolean z2) {
        b bVar2 = this.f2195a;
        if (bVar2 == bVar) {
            if (bVar2 != null) {
                throw null;
            }
            return;
        }
        int i2 = bVar != null ? bVar.f245b : -1;
        if (z2) {
            if ((bVar2 == null || bVar2.f245b == -1) && i2 != -1) {
                if (Math.round(i2 + 0.0f) >= 0) {
                    throw null;
                }
            } else if (i2 != -1) {
                if (getWindowToken() != null) {
                    WeakHashMap weakHashMap = l.f2836a;
                    if (isLaidOut()) {
                        throw null;
                    }
                }
                if (Math.round(i2 + 0.0f) >= 0) {
                    throw null;
                }
            }
            if (i2 != -1) {
                setSelectedTabView(i2);
            }
        }
        this.f2195a = bVar;
        if (bVar2 != null || bVar != null) {
            throw null;
        }
    }

    public final void c(F.a aVar) {
        this.f2210p = aVar;
        throw null;
    }

    public final void d(ViewPager viewPager) {
        c cVar;
        ViewPager viewPager2 = this.f2209o;
        if (viewPager2 != null && (cVar = this.f2211q) != null) {
            viewPager2.t(cVar);
        }
        if (this.f2207m != null) {
            throw null;
        }
        if (viewPager == null) {
            this.f2209o = null;
            c(null);
            throw null;
        }
        this.f2209o = viewPager;
        if (this.f2211q == null) {
            this.f2211q = new c(this);
        }
        c cVar2 = this.f2211q;
        cVar2.f250c = 0;
        cVar2.f249b = 0;
        viewPager.b(cVar2);
        this.f2207m = new n(3);
        throw null;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    public int getSelectedTabPosition() {
        b bVar = this.f2195a;
        if (bVar != null) {
            return bVar.f245b;
        }
        return -1;
    }

    public int getTabCount() {
        throw null;
    }

    public int getTabGravity() {
        return this.f2201g;
    }

    public ColorStateList getTabIconTint() {
        return this.f2197c;
    }

    public int getTabIndicatorGravity() {
        return this.f2202h;
    }

    public int getTabMaxWidth() {
        return this.f2200f;
    }

    public int getTabMode() {
        return this.f2203i;
    }

    public ColorStateList getTabRippleColor() {
        return this.f2198d;
    }

    public Drawable getTabSelectedIndicator() {
        return this.f2199e;
    }

    public ColorStateList getTabTextColors() {
        return this.f2196b;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f2209o == null) {
            ViewParent parent = getParent();
            if (parent instanceof ViewPager) {
                d((ViewPager) parent);
                throw null;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        throw null;
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i2, int i3) {
        int paddingBottom = getPaddingBottom() + getPaddingTop() + Math.round(getResources().getDisplayMetrics().density * getDefaultHeight());
        int mode = View.MeasureSpec.getMode(i3);
        if (mode == Integer.MIN_VALUE) {
            i3 = View.MeasureSpec.makeMeasureSpec(Math.min(paddingBottom, View.MeasureSpec.getSize(i3)), 1073741824);
        } else if (mode == 0) {
            i3 = View.MeasureSpec.makeMeasureSpec(paddingBottom, 1073741824);
        }
        int size = View.MeasureSpec.getSize(i2);
        if (View.MeasureSpec.getMode(i2) != 0) {
            this.f2200f = size - Math.round(getResources().getDisplayMetrics().density * 56);
        }
        super.onMeasure(i2, i3);
        if (getChildCount() == 1) {
            View childAt = getChildAt(0);
            int i4 = this.f2203i;
            if (i4 != 0) {
                if (i4 != 1 || childAt.getMeasuredWidth() == getMeasuredWidth()) {
                    return;
                }
            } else if (childAt.getMeasuredWidth() >= getMeasuredWidth()) {
                return;
            }
            childAt.measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), HorizontalScrollView.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop(), childAt.getLayoutParams().height));
        }
    }

    public void setInlineLabel(boolean z2) {
        if (this.f2204j == z2) {
            return;
        }
        this.f2204j = z2;
        throw null;
    }

    public void setInlineLabelResource(int i2) {
        setInlineLabel(getResources().getBoolean(i2));
    }

    @Deprecated
    public void setOnTabSelectedListener(a aVar) {
        if (this.f2206l != null) {
            throw null;
        }
        this.f2206l = aVar;
        if (aVar != null) {
            throw null;
        }
    }

    public void setScrollAnimatorListener(Animator.AnimatorListener animatorListener) {
        if (this.f2208n == null) {
            ValueAnimator valueAnimator = new ValueAnimator();
            this.f2208n = valueAnimator;
            valueAnimator.setInterpolator(H.a.f142a);
            this.f2208n.setDuration(0);
            this.f2208n.addUpdateListener(new I.b(1, this));
        }
        this.f2208n.addListener(animatorListener);
    }

    public void setSelectedTabIndicator(int i2) {
        if (i2 != 0) {
            setSelectedTabIndicator(AbstractC0122a.a(getContext(), i2));
        } else {
            setSelectedTabIndicator((Drawable) null);
        }
    }

    public void setSelectedTabIndicator(Drawable drawable) {
        if (this.f2199e == drawable) {
            return;
        }
        this.f2199e = drawable;
        WeakHashMap weakHashMap = l.f2836a;
        throw null;
    }

    public void setSelectedTabIndicatorColor(int i2) {
        throw null;
    }

    public void setSelectedTabIndicatorGravity(int i2) {
        if (this.f2202h == i2) {
            return;
        }
        this.f2202h = i2;
        WeakHashMap weakHashMap = l.f2836a;
        throw null;
    }

    @Deprecated
    public void setSelectedTabIndicatorHeight(int i2) {
        throw null;
    }

    public void setTabGravity(int i2) {
        if (this.f2201g == i2) {
            return;
        }
        this.f2201g = i2;
        if (this.f2203i == 0) {
            Math.max(0, 0);
        }
        WeakHashMap weakHashMap = l.f2836a;
        throw null;
    }

    public void setTabIconTint(ColorStateList colorStateList) {
        if (this.f2197c == colorStateList) {
            return;
        }
        this.f2197c = colorStateList;
        throw null;
    }

    public void setTabIconTintResource(int i2) {
        Context context = getContext();
        Object obj = AbstractC0122a.f2010a;
        setTabIconTint(context.getColorStateList(i2));
    }

    public void setTabIndicatorFullWidth(boolean z2) {
        WeakHashMap weakHashMap = l.f2836a;
        throw null;
    }

    public void setTabMode(int i2) {
        if (i2 == this.f2203i) {
            return;
        }
        this.f2203i = i2;
        if (i2 == 0) {
            Math.max(0, 0);
        }
        WeakHashMap weakHashMap = l.f2836a;
        throw null;
    }

    public void setTabRippleColor(ColorStateList colorStateList) {
        if (this.f2198d == colorStateList) {
            return;
        }
        this.f2198d = colorStateList;
        throw null;
    }

    public void setTabRippleColorResource(int i2) {
        Context context = getContext();
        Object obj = AbstractC0122a.f2010a;
        setTabRippleColor(context.getColorStateList(i2));
    }

    public void setTabTextColors(ColorStateList colorStateList) {
        if (this.f2196b == colorStateList) {
            return;
        }
        this.f2196b = colorStateList;
        throw null;
    }

    @Deprecated
    public void setTabsFromPagerAdapter(F.a aVar) {
        c(aVar);
        throw null;
    }

    public void setUnboundedRipple(boolean z2) {
        if (this.f2205k == z2) {
            return;
        }
        this.f2205k = z2;
        throw null;
    }

    public void setUnboundedRippleResource(int i2) {
        setUnboundedRipple(getResources().getBoolean(i2));
    }

    public void setupWithViewPager(ViewPager viewPager) {
        d(viewPager);
        throw null;
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }
}
