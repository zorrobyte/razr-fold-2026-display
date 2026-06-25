package com.google.android.material.internal;

import P.c;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.google.android.material.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class ForegroundLinearLayout extends LinearLayoutCompat {

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public Drawable f2168p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final Rect f2169q;
    public final Rect r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public int f2170s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public final boolean f2171t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public boolean f2172u;

    public ForegroundLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.f2169q = new Rect();
        this.r = new Rect();
        this.f2170s = 119;
        this.f2171t = true;
        this.f2172u = false;
        int[] iArr = R$styleable.ForegroundLinearLayout;
        int[] iArr2 = c.f234a;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ThemeEnforcement, 0, 0);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R$styleable.ThemeEnforcement_enforceMaterialTheme, false);
        typedArrayObtainStyledAttributes.recycle();
        if (z2) {
            c.a(context, c.f235b, "Theme.MaterialComponents");
        }
        c.a(context, c.f234a, "Theme.AppCompat");
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.ThemeEnforcement, 0, 0);
        if (typedArrayObtainStyledAttributes2.getBoolean(R$styleable.ThemeEnforcement_enforceTextAppearance, false)) {
            boolean z3 = typedArrayObtainStyledAttributes2.getResourceId(R$styleable.ThemeEnforcement_android_textAppearance, -1) != -1;
            typedArrayObtainStyledAttributes2.recycle();
            if (!z3) {
                throw new IllegalArgumentException("This component requires that you specify a valid TextAppearance attribute. Update your app theme to inherit from Theme.MaterialComponents (or a descendant).");
            }
        } else {
            typedArrayObtainStyledAttributes2.recycle();
        }
        TypedArray typedArrayObtainStyledAttributes3 = context.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        this.f2170s = typedArrayObtainStyledAttributes3.getInt(R$styleable.ForegroundLinearLayout_android_foregroundGravity, this.f2170s);
        Drawable drawable = typedArrayObtainStyledAttributes3.getDrawable(R$styleable.ForegroundLinearLayout_android_foreground);
        if (drawable != null) {
            setForeground(drawable);
        }
        this.f2171t = typedArrayObtainStyledAttributes3.getBoolean(R$styleable.ForegroundLinearLayout_foregroundInsidePadding, true);
        typedArrayObtainStyledAttributes3.recycle();
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        Drawable drawable = this.f2168p;
        if (drawable != null) {
            if (this.f2172u) {
                this.f2172u = false;
                int right = getRight() - getLeft();
                int bottom = getBottom() - getTop();
                boolean z2 = this.f2171t;
                Rect rect = this.f2169q;
                if (z2) {
                    rect.set(0, 0, right, bottom);
                } else {
                    rect.set(getPaddingLeft(), getPaddingTop(), right - getPaddingRight(), bottom - getPaddingBottom());
                }
                int i2 = this.f2170s;
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                Rect rect2 = this.r;
                Gravity.apply(i2, intrinsicWidth, intrinsicHeight, rect, rect2);
                drawable.setBounds(rect2);
            }
            drawable.draw(canvas);
        }
    }

    @Override // android.view.View
    public final void drawableHotspotChanged(float f2, float f3) {
        super.drawableHotspotChanged(f2, f3);
        Drawable drawable = this.f2168p;
        if (drawable != null) {
            drawable.setHotspot(f2, f3);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.f2168p;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        this.f2168p.setState(getDrawableState());
    }

    @Override // android.view.View
    public Drawable getForeground() {
        return this.f2168p;
    }

    @Override // android.view.View
    public int getForegroundGravity() {
        return this.f2170s;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.f2168p;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        this.f2172u = z2 | this.f2172u;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.f2172u = true;
    }

    @Override // android.view.View
    public void setForeground(Drawable drawable) {
        Drawable drawable2 = this.f2168p;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
                unscheduleDrawable(this.f2168p);
            }
            this.f2168p = drawable;
            if (drawable != null) {
                setWillNotDraw(false);
                drawable.setCallback(this);
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                if (this.f2170s == 119) {
                    drawable.getPadding(new Rect());
                }
            } else {
                setWillNotDraw(true);
            }
            requestLayout();
            invalidate();
        }
    }

    @Override // android.view.View
    public void setForegroundGravity(int i2) {
        if (this.f2170s != i2) {
            if ((8388615 & i2) == 0) {
                i2 |= 8388611;
            }
            if ((i2 & 112) == 0) {
                i2 |= 48;
            }
            this.f2170s = i2;
            if (i2 == 119 && this.f2168p != null) {
                this.f2168p.getPadding(new Rect());
            }
            requestLayout();
        }
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.f2168p;
    }
}
