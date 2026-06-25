package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatButton;
import b.AbstractC0122a;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class MaterialButton extends AppCompatButton {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2134c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public PorterDuff.Mode f2135d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public ColorStateList f2136e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public Drawable f2137f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f2138g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f2139h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f2140i;

    public final void a() {
        Drawable drawable = this.f2137f;
        if (drawable != null) {
            Drawable drawableMutate = drawable.mutate();
            this.f2137f = drawableMutate;
            drawableMutate.setTintList(this.f2136e);
            PorterDuff.Mode mode = this.f2135d;
            if (mode != null) {
                this.f2137f.setTintMode(mode);
            }
            int intrinsicWidth = this.f2138g;
            if (intrinsicWidth == 0) {
                intrinsicWidth = this.f2137f.getIntrinsicWidth();
            }
            int intrinsicHeight = this.f2138g;
            if (intrinsicHeight == 0) {
                intrinsicHeight = this.f2137f.getIntrinsicHeight();
            }
            Drawable drawable2 = this.f2137f;
            int i2 = this.f2139h;
            drawable2.setBounds(i2, 0, intrinsicWidth + i2, intrinsicHeight);
        }
        setCompoundDrawablesRelative(this.f2137f, null, null, null);
    }

    @Override // android.view.View
    public ColorStateList getBackgroundTintList() {
        return getSupportBackgroundTintList();
    }

    @Override // android.view.View
    public PorterDuff.Mode getBackgroundTintMode() {
        return getSupportBackgroundTintMode();
    }

    public int getCornerRadius() {
        return 0;
    }

    public Drawable getIcon() {
        return this.f2137f;
    }

    public int getIconGravity() {
        return this.f2140i;
    }

    public int getIconPadding() {
        return this.f2134c;
    }

    public int getIconSize() {
        return this.f2138g;
    }

    public ColorStateList getIconTint() {
        return this.f2136e;
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.f2135d;
    }

    public ColorStateList getRippleColor() {
        return null;
    }

    public ColorStateList getStrokeColor() {
        return null;
    }

    public int getStrokeWidth() {
        return 0;
    }

    @Override // androidx.appcompat.widget.AppCompatButton
    public ColorStateList getSupportBackgroundTintList() {
        return super.getSupportBackgroundTintList();
    }

    @Override // androidx.appcompat.widget.AppCompatButton
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return super.getSupportBackgroundTintMode();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.widget.TextView, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.f2137f == null || this.f2140i != 2) {
            return;
        }
        int iMeasureText = (int) getPaint().measureText(getText().toString());
        int intrinsicWidth = this.f2138g;
        if (intrinsicWidth == 0) {
            intrinsicWidth = this.f2137f.getIntrinsicWidth();
        }
        int measuredWidth = getMeasuredWidth() - iMeasureText;
        WeakHashMap weakHashMap = l.f2836a;
        int paddingEnd = ((((measuredWidth - getPaddingEnd()) - intrinsicWidth) - this.f2134c) - getPaddingStart()) / 2;
        if (getLayoutDirection() == 1) {
            paddingEnd = -paddingEnd;
        }
        if (this.f2139h != paddingEnd) {
            this.f2139h = paddingEnd;
            a();
        }
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
        super.setBackgroundColor(i2);
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.view.View
    public void setBackgroundResource(int i2) {
        setBackgroundDrawable(i2 != 0 ? AbstractC0122a.a(getContext(), i2) : null);
    }

    @Override // android.view.View
    public void setBackgroundTintList(ColorStateList colorStateList) {
        setSupportBackgroundTintList(colorStateList);
    }

    @Override // android.view.View
    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        setSupportBackgroundTintMode(mode);
    }

    public void setCornerRadius(int i2) {
    }

    public void setCornerRadiusResource(int i2) {
    }

    public void setIcon(Drawable drawable) {
        if (this.f2137f != drawable) {
            this.f2137f = drawable;
            a();
        }
    }

    public void setIconGravity(int i2) {
        this.f2140i = i2;
    }

    public void setIconPadding(int i2) {
        if (this.f2134c != i2) {
            this.f2134c = i2;
            setCompoundDrawablePadding(i2);
        }
    }

    public void setIconResource(int i2) {
        setIcon(i2 != 0 ? AbstractC0122a.a(getContext(), i2) : null);
    }

    public void setIconSize(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("iconSize cannot be less than 0");
        }
        if (this.f2138g != i2) {
            this.f2138g = i2;
            a();
        }
    }

    public void setIconTint(ColorStateList colorStateList) {
        if (this.f2136e != colorStateList) {
            this.f2136e = colorStateList;
            a();
        }
    }

    public void setIconTintMode(PorterDuff.Mode mode) {
        if (this.f2135d != mode) {
            this.f2135d = mode;
            a();
        }
    }

    public void setIconTintResource(int i2) {
        Context context = getContext();
        Object obj = AbstractC0122a.f2010a;
        setIconTint(context.getColorStateList(i2));
    }

    public void setInternalBackground(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    public void setRippleColor(ColorStateList colorStateList) {
    }

    public void setRippleColorResource(int i2) {
    }

    public void setStrokeColor(ColorStateList colorStateList) {
    }

    public void setStrokeColorResource(int i2) {
    }

    public void setStrokeWidth(int i2) {
    }

    public void setStrokeWidthResource(int i2) {
    }

    @Override // androidx.appcompat.widget.AppCompatButton
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
    }

    @Override // androidx.appcompat.widget.AppCompatButton
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
    }
}
