package com.google.android.material.bottomnavigation;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.C;
import androidx.appcompat.view.menu.o;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class BottomNavigationMenuView extends ViewGroup implements C {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f2095a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2096b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public ColorStateList f2097c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f2098d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public ColorStateList f2099e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f2100f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f2101g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public Drawable f2102h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f2103i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public o f2104j;

    private BottomNavigationItemView getNewItem() {
        throw null;
    }

    @Override // androidx.appcompat.view.menu.C
    public final void b(o oVar) {
        this.f2104j = oVar;
    }

    public ColorStateList getIconTintList() {
        return this.f2097c;
    }

    public Drawable getItemBackground() {
        return this.f2102h;
    }

    @Deprecated
    public int getItemBackgroundRes() {
        return this.f2103i;
    }

    public int getItemIconSize() {
        return this.f2098d;
    }

    public int getItemTextAppearanceActive() {
        return this.f2101g;
    }

    public int getItemTextAppearanceInactive() {
        return this.f2100f;
    }

    public ColorStateList getItemTextColor() {
        return this.f2099e;
    }

    public int getLabelVisibilityMode() {
        return this.f2096b;
    }

    public int getSelectedItemId() {
        return 0;
    }

    public int getWindowAnimations() {
        return 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        int i6 = i4 - i2;
        int i7 = i5 - i3;
        int measuredWidth = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                WeakHashMap weakHashMap = l.f2836a;
                if (getLayoutDirection() == 1) {
                    int i9 = i6 - measuredWidth;
                    childAt.layout(i9 - childAt.getMeasuredWidth(), 0, i9, i7);
                } else {
                    childAt.layout(measuredWidth, 0, childAt.getMeasuredWidth() + measuredWidth, i7);
                }
                measuredWidth += childAt.getMeasuredWidth();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0072  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onMeasure(int r10, int r11) {
        /*
            r9 = this;
            int r10 = android.view.View.MeasureSpec.getSize(r10)
            androidx.appcompat.view.menu.o r11 = r9.f2104j
            java.util.ArrayList r11 = r11.l()
            int r11 = r11.size()
            int r0 = r9.getChildCount()
            r1 = 0
            r2 = 1073741824(0x40000000, float:2.0)
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r2)
            int r4 = r9.f2096b
            r5 = -1
            r6 = 1
            r7 = 0
            r8 = 8
            if (r4 != r5) goto L26
            r4 = 3
            if (r11 <= r4) goto L72
            goto L28
        L26:
            if (r4 != 0) goto L72
        L28:
            boolean r4 = r9.f2095a
            if (r4 == 0) goto L72
            android.view.View r4 = r9.getChildAt(r1)
            int r5 = r4.getVisibility()
            if (r5 == r8) goto L48
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r5)
            r4.measure(r5, r3)
            int r5 = r4.getMeasuredWidth()
            int r5 = java.lang.Math.max(r1, r5)
            goto L49
        L48:
            r5 = r1
        L49:
            int r4 = r4.getVisibility()
            if (r4 == r8) goto L51
            r4 = r6
            goto L52
        L51:
            r4 = r1
        L52:
            int r11 = r11 - r4
            int r4 = java.lang.Math.min(r5, r1)
            int r4 = java.lang.Math.min(r10, r4)
            int r10 = r10 - r4
            if (r11 != 0) goto L5f
            goto L60
        L5f:
            r6 = r11
        L60:
            int r10 = r10 / r6
            java.lang.Math.min(r10, r1)
            if (r0 <= 0) goto L87
            android.view.View r9 = r9.getChildAt(r1)
            int r9 = r9.getVisibility()
            if (r9 == r8) goto L71
            throw r7
        L71:
            throw r7
        L72:
            if (r11 != 0) goto L75
            r11 = r6
        L75:
            int r10 = r10 / r11
            java.lang.Math.min(r10, r1)
            if (r0 <= 0) goto L87
            android.view.View r9 = r9.getChildAt(r1)
            int r9 = r9.getVisibility()
            if (r9 == r8) goto L86
            throw r7
        L86:
            throw r7
        L87:
            r10 = r1
        L88:
            if (r10 >= r0) goto L98
            android.view.View r11 = r9.getChildAt(r10)
            int r11 = r11.getVisibility()
            if (r11 != r8) goto L97
            int r10 = r10 + 1
            goto L88
        L97:
            throw r7
        L98:
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r2)
            int r10 = android.view.View.resolveSizeAndState(r1, r10, r1)
            int r11 = android.view.View.resolveSizeAndState(r1, r3, r1)
            r9.setMeasuredDimension(r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomnavigation.BottomNavigationMenuView.onMeasure(int, int):void");
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.f2097c = colorStateList;
    }

    public void setItemBackground(Drawable drawable) {
        this.f2102h = drawable;
    }

    public void setItemBackgroundRes(int i2) {
        this.f2103i = i2;
    }

    public void setItemHorizontalTranslationEnabled(boolean z2) {
        this.f2095a = z2;
    }

    public void setItemIconSize(int i2) {
        this.f2098d = i2;
    }

    public void setItemTextAppearanceActive(int i2) {
        this.f2101g = i2;
    }

    public void setItemTextAppearanceInactive(int i2) {
        this.f2100f = i2;
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.f2099e = colorStateList;
    }

    public void setLabelVisibilityMode(int i2) {
        this.f2096b = i2;
    }

    public void setPresenter(b bVar) {
    }
}
