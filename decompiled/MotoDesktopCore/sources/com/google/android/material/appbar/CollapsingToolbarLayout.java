package com.google.android.material.appbar;

import I.c;
import I.d;
import I.i;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.google.android.material.R$id;
import com.google.android.material.R$styleable;
import java.util.ArrayList;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class CollapsingToolbarLayout extends FrameLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public View f2044a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2045b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2046c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f2047d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f2048e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f2049f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f2050g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public Drawable f2051h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public Drawable f2052i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public int f2053j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f2054k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public ValueAnimator f2055l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public long f2056m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public int f2057n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public d f2058o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public int f2059p;

    public static i b(View view) {
        i iVar = (i) view.getTag(R$id.view_offset_helper);
        if (iVar != null) {
            return iVar;
        }
        i iVar2 = new i(view);
        view.setTag(R$id.view_offset_helper, iVar2);
        return iVar2;
    }

    public final void a() {
    }

    public final void c() {
        View view;
        if (this.f2049f || (view = this.f2044a) == null) {
            return;
        }
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.f2044a);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof c;
    }

    public final void d() {
        if (this.f2051h == null && this.f2052i == null) {
            return;
        }
        setScrimsShown(getHeight() + this.f2059p < getScrimVisibleHeightTrigger());
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        Drawable drawable = this.f2051h;
        if (drawable != null && this.f2053j > 0) {
            drawable.mutate().setAlpha(this.f2053j);
            this.f2051h.draw(canvas);
        }
        if (this.f2049f && this.f2050g) {
            throw null;
        }
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j2) {
        boolean z2;
        Drawable drawable = this.f2051h;
        if (drawable == null || this.f2053j <= 0 || view != null) {
            z2 = false;
        } else {
            drawable.mutate().setAlpha(this.f2053j);
            this.f2051h.draw(canvas);
            z2 = true;
        }
        return super.drawChild(canvas, view, j2) || z2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.f2052i;
        boolean state = (drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState);
        Drawable drawable2 = this.f2051h;
        if (drawable2 != null && drawable2.isStateful()) {
            state |= drawable2.setState(drawableState);
        }
        if (state) {
            invalidate();
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        c cVar = new c(-1, -1);
        cVar.f159a = 0;
        cVar.f160b = 0.5f;
        return cVar;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateDefaultLayoutParams() {
        c cVar = new c(-1, -1);
        cVar.f159a = 0;
        cVar.f160b = 0.5f;
        return cVar;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        c cVar = new c(layoutParams);
        cVar.f159a = 0;
        cVar.f160b = 0.5f;
        return cVar;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        c cVar = new c(context, attributeSet);
        cVar.f159a = 0;
        cVar.f160b = 0.5f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CollapsingToolbarLayout_Layout);
        cVar.f159a = typedArrayObtainStyledAttributes.getInt(R$styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
        cVar.f160b = typedArrayObtainStyledAttributes.getFloat(R$styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5f);
        typedArrayObtainStyledAttributes.recycle();
        return cVar;
    }

    public int getCollapsedTitleGravity() {
        throw null;
    }

    public Typeface getCollapsedTitleTypeface() {
        throw null;
    }

    public Drawable getContentScrim() {
        return this.f2051h;
    }

    public int getExpandedTitleGravity() {
        throw null;
    }

    public int getExpandedTitleMarginBottom() {
        return this.f2048e;
    }

    public int getExpandedTitleMarginEnd() {
        return this.f2047d;
    }

    public int getExpandedTitleMarginStart() {
        return this.f2045b;
    }

    public int getExpandedTitleMarginTop() {
        return this.f2046c;
    }

    public Typeface getExpandedTitleTypeface() {
        throw null;
    }

    public int getScrimAlpha() {
        return this.f2053j;
    }

    public long getScrimAnimationDuration() {
        return this.f2056m;
    }

    public int getScrimVisibleHeightTrigger() {
        int i2 = this.f2057n;
        if (i2 >= 0) {
            return i2;
        }
        WeakHashMap weakHashMap = l.f2836a;
        int minimumHeight = getMinimumHeight();
        return minimumHeight > 0 ? Math.min(minimumHeight * 2, getHeight()) : getHeight() / 3;
    }

    public Drawable getStatusBarScrim() {
        return this.f2052i;
    }

    public CharSequence getTitle() {
        if (this.f2049f) {
            throw null;
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object parent = getParent();
        if (parent instanceof AppBarLayout) {
            WeakHashMap weakHashMap = l.f2836a;
            setFitsSystemWindows(((View) parent).getFitsSystemWindows());
            if (this.f2058o == null) {
                this.f2058o = new d(this);
            }
            AppBarLayout appBarLayout = (AppBarLayout) parent;
            d dVar = this.f2058o;
            if (appBarLayout.f2029f == null) {
                appBarLayout.f2029f = new ArrayList();
            }
            if (dVar != null && !appBarLayout.f2029f.contains(dVar)) {
                appBarLayout.f2029f.add(dVar);
            }
            requestApplyInsets();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        ArrayList arrayList;
        ViewParent parent = getParent();
        d dVar = this.f2058o;
        if (dVar != null && (parent instanceof AppBarLayout) && (arrayList = ((AppBarLayout) parent).f2029f) != null) {
            arrayList.remove(dVar);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        View view;
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.f2049f && (view = this.f2044a) != null) {
            WeakHashMap weakHashMap = l.f2836a;
            boolean z3 = view.isAttachedToWindow() && this.f2044a.getVisibility() == 0;
            this.f2050g = z3;
            if (z3) {
                getLayoutDirection();
                b(null);
                throw null;
            }
        }
        int childCount = getChildCount();
        for (int i6 = 0; i6 < childCount; i6++) {
            i iVarB = b(getChildAt(i6));
            View view2 = iVarB.f179a;
            iVarB.f180b = view2.getTop();
            iVarB.f181c = view2.getLeft();
            iVarB.b();
        }
        d();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        View.MeasureSpec.getMode(i3);
    }

    @Override // android.view.View
    public final void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        Drawable drawable = this.f2051h;
        if (drawable != null) {
            drawable.setBounds(0, 0, i2, i3);
        }
    }

    public void setCollapsedTitleGravity(int i2) {
        throw null;
    }

    public void setCollapsedTitleTextAppearance(int i2) {
        throw null;
    }

    public void setCollapsedTitleTextColor(int i2) {
        setCollapsedTitleTextColor(ColorStateList.valueOf(i2));
    }

    public void setCollapsedTitleTextColor(ColorStateList colorStateList) {
        throw null;
    }

    public void setCollapsedTitleTypeface(Typeface typeface) {
        throw null;
    }

    public void setContentScrim(Drawable drawable) {
        Drawable drawable2 = this.f2051h;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
            this.f2051h = drawableMutate;
            if (drawableMutate != null) {
                drawableMutate.setBounds(0, 0, getWidth(), getHeight());
                this.f2051h.setCallback(this);
                this.f2051h.setAlpha(this.f2053j);
            }
            WeakHashMap weakHashMap = l.f2836a;
            postInvalidateOnAnimation();
        }
    }

    public void setContentScrimColor(int i2) {
        setContentScrim(new ColorDrawable(i2));
    }

    public void setContentScrimResource(int i2) {
        setContentScrim(getContext().getDrawable(i2));
    }

    public void setExpandedTitleColor(int i2) {
        setExpandedTitleTextColor(ColorStateList.valueOf(i2));
    }

    public void setExpandedTitleGravity(int i2) {
        throw null;
    }

    public void setExpandedTitleMarginBottom(int i2) {
        this.f2048e = i2;
        requestLayout();
    }

    public void setExpandedTitleMarginEnd(int i2) {
        this.f2047d = i2;
        requestLayout();
    }

    public void setExpandedTitleMarginStart(int i2) {
        this.f2045b = i2;
        requestLayout();
    }

    public void setExpandedTitleMarginTop(int i2) {
        this.f2046c = i2;
        requestLayout();
    }

    public void setExpandedTitleTextAppearance(int i2) {
        throw null;
    }

    public void setExpandedTitleTextColor(ColorStateList colorStateList) {
        throw null;
    }

    public void setExpandedTitleTypeface(Typeface typeface) {
        throw null;
    }

    public void setScrimAlpha(int i2) {
        if (i2 != this.f2053j) {
            this.f2053j = i2;
            WeakHashMap weakHashMap = l.f2836a;
            postInvalidateOnAnimation();
        }
    }

    public void setScrimAnimationDuration(long j2) {
        this.f2056m = j2;
    }

    public void setScrimVisibleHeightTrigger(int i2) {
        if (this.f2057n != i2) {
            this.f2057n = i2;
            d();
        }
    }

    public void setScrimsShown(boolean z2) {
        WeakHashMap weakHashMap = l.f2836a;
        boolean z3 = isLaidOut() && !isInEditMode();
        if (this.f2054k != z2) {
            if (z3) {
                int i2 = z2 ? 255 : 0;
                ValueAnimator valueAnimator = this.f2055l;
                if (valueAnimator == null) {
                    ValueAnimator valueAnimator2 = new ValueAnimator();
                    this.f2055l = valueAnimator2;
                    valueAnimator2.setDuration(this.f2056m);
                    this.f2055l.setInterpolator(i2 > this.f2053j ? H.a.f143b : H.a.f144c);
                    this.f2055l.addUpdateListener(new I.b(i, this));
                } else if (valueAnimator.isRunning()) {
                    this.f2055l.cancel();
                }
                this.f2055l.setIntValues(this.f2053j, i2);
                this.f2055l.start();
            } else {
                setScrimAlpha(z2 ? 255 : 0);
            }
            this.f2054k = z2;
        }
    }

    public void setStatusBarScrim(Drawable drawable) {
        Drawable drawable2 = this.f2052i;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
            this.f2052i = drawableMutate;
            if (drawableMutate != null) {
                if (drawableMutate.isStateful()) {
                    this.f2052i.setState(getDrawableState());
                }
                Drawable drawable3 = this.f2052i;
                WeakHashMap weakHashMap = l.f2836a;
                drawable3.setLayoutDirection(getLayoutDirection());
                this.f2052i.setVisible(getVisibility() == 0, false);
                this.f2052i.setCallback(this);
                this.f2052i.setAlpha(this.f2053j);
            }
            WeakHashMap weakHashMap2 = l.f2836a;
            postInvalidateOnAnimation();
        }
    }

    public void setStatusBarScrimColor(int i2) {
        setStatusBarScrim(new ColorDrawable(i2));
    }

    public void setStatusBarScrimResource(int i2) {
        setStatusBarScrim(getContext().getDrawable(i2));
    }

    public void setTitle(CharSequence charSequence) {
        throw null;
    }

    public void setTitleEnabled(boolean z2) {
        if (z2 != this.f2049f) {
            this.f2049f = z2;
            setContentDescription(getTitle());
            c();
            requestLayout();
        }
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        boolean z2 = i2 == 0;
        Drawable drawable = this.f2052i;
        if (drawable != null && drawable.isVisible() != z2) {
            this.f2052i.setVisible(z2, false);
        }
        Drawable drawable2 = this.f2051h;
        if (drawable2 == null || drawable2.isVisible() == z2) {
            return;
        }
        this.f2051h.setVisible(z2, false);
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.f2051h || drawable == this.f2052i;
    }
}
