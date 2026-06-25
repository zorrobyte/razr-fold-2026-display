package com.google.android.material.internal;

import android.view.View;
import android.view.ViewGroup;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class FlowLayout extends ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2165a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2166b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f2167c;

    public int getItemSpacing() {
        return this.f2166b;
    }

    public int getLineSpacing() {
        return this.f2165a;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int marginEnd;
        int marginStart;
        if (getChildCount() == 0) {
            return;
        }
        WeakHashMap weakHashMap = l.f2836a;
        boolean z3 = getLayoutDirection() == 1;
        int paddingRight = z3 ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = z3 ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int i6 = (i4 - i2) - paddingLeft;
        int measuredWidth = paddingRight;
        int i7 = paddingTop;
        for (int i8 = 0; i8 < getChildCount(); i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    marginStart = marginLayoutParams.getMarginStart();
                    marginEnd = marginLayoutParams.getMarginEnd();
                } else {
                    marginEnd = 0;
                    marginStart = 0;
                }
                int measuredWidth2 = childAt.getMeasuredWidth() + measuredWidth + marginStart;
                if (!this.f2167c && measuredWidth2 > i6) {
                    i7 = this.f2165a + paddingTop;
                    measuredWidth = paddingRight;
                }
                int i9 = measuredWidth + marginStart;
                int measuredWidth3 = childAt.getMeasuredWidth() + i9;
                int measuredHeight = childAt.getMeasuredHeight() + i7;
                if (z3) {
                    childAt.layout(i6 - measuredWidth3, i7, (i6 - measuredWidth) - marginStart, measuredHeight);
                } else {
                    childAt.layout(i9, i7, measuredWidth3, measuredHeight);
                }
                measuredWidth += childAt.getMeasuredWidth() + marginStart + marginEnd + this.f2166b;
                paddingTop = measuredHeight;
            }
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int paddingLeft;
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        int mode2 = View.MeasureSpec.getMode(i3);
        int i7 = Integer.MIN_VALUE;
        int i8 = (mode == Integer.MIN_VALUE || mode == 1073741824) ? size : Integer.MAX_VALUE;
        int paddingLeft2 = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = i8 - getPaddingRight();
        int i9 = paddingTop;
        int i10 = 0;
        int i11 = 0;
        while (i10 < getChildCount()) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i2, i3);
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    i6 = marginLayoutParams.leftMargin;
                    i5 = marginLayoutParams.rightMargin;
                } else {
                    i5 = 0;
                    i6 = 0;
                }
                int i12 = paddingLeft2;
                if (childAt.getMeasuredWidth() + paddingLeft2 + i6 <= paddingRight || this.f2167c) {
                    paddingLeft = i12;
                } else {
                    paddingLeft = getPaddingLeft();
                    i9 = this.f2165a + paddingTop;
                }
                int measuredWidth = childAt.getMeasuredWidth() + paddingLeft + i6;
                int measuredHeight = childAt.getMeasuredHeight() + i9;
                if (measuredWidth > i11) {
                    i11 = measuredWidth;
                }
                paddingLeft2 = childAt.getMeasuredWidth() + i6 + i5 + this.f2166b + paddingLeft;
                paddingTop = measuredHeight;
            }
            i10++;
            i7 = Integer.MIN_VALUE;
        }
        int i13 = i7;
        if (mode != i13) {
            i4 = 1073741824;
            if (mode != 1073741824) {
                size = i11;
            }
        } else {
            i4 = 1073741824;
            size = Math.min(i11, size);
        }
        if (mode2 == i13) {
            size2 = Math.min(paddingTop, size2);
        } else if (mode2 != i4) {
            size2 = paddingTop;
        }
        setMeasuredDimension(size, size2);
    }

    public void setItemSpacing(int i2) {
        this.f2166b = i2;
    }

    public void setLineSpacing(int i2) {
        this.f2165a = i2;
    }

    public void setSingleLine(boolean z2) {
        this.f2167c = z2;
    }
}
