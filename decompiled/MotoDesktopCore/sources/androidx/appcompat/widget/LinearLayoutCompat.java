package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class LinearLayoutCompat extends ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f1019a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1020b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1021c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f1022d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f1023e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1024f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public float f1025g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f1026h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int[] f1027i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public int[] f1028j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public Drawable f1029k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f1030l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f1031m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public int f1032n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public int f1033o;

    public LinearLayoutCompat(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f1019a = true;
        this.f1020b = -1;
        this.f1021c = 0;
        this.f1023e = 8388659;
        f0.b bVarM = f0.b.m(context, attributeSet, R$styleable.LinearLayoutCompat, i2, 0);
        int i3 = R$styleable.LinearLayoutCompat_android_orientation;
        TypedArray typedArray = (TypedArray) bVarM.f2538c;
        int i4 = typedArray.getInt(i3, -1);
        if (i4 >= 0) {
            setOrientation(i4);
        }
        int i5 = typedArray.getInt(R$styleable.LinearLayoutCompat_android_gravity, -1);
        if (i5 >= 0) {
            setGravity(i5);
        }
        boolean z2 = typedArray.getBoolean(R$styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!z2) {
            setBaselineAligned(z2);
        }
        this.f1025g = typedArray.getFloat(R$styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.f1020b = typedArray.getInt(R$styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.f1026h = typedArray.getBoolean(R$styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(bVarM.f(R$styleable.LinearLayoutCompat_divider));
        this.f1032n = typedArray.getInt(R$styleable.LinearLayoutCompat_showDividers, 0);
        this.f1033o = typedArray.getDimensionPixelSize(R$styleable.LinearLayoutCompat_dividerPadding, 0);
        bVarM.n();
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof M;
    }

    public final void d(Canvas canvas, int i2) {
        this.f1029k.setBounds(getPaddingLeft() + this.f1033o, i2, (getWidth() - getPaddingRight()) - this.f1033o, this.f1031m + i2);
        this.f1029k.draw(canvas);
    }

    public final void e(Canvas canvas, int i2) {
        this.f1029k.setBounds(i2, getPaddingTop() + this.f1033o, this.f1030l + i2, (getHeight() - getPaddingBottom()) - this.f1033o);
        this.f1029k.draw(canvas);
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
    public M generateDefaultLayoutParams() {
        int i2 = this.f1022d;
        if (i2 == 0) {
            return new M(-2, -2);
        }
        if (i2 == 1) {
            return new M(-1, -2);
        }
        return null;
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public M generateLayoutParams(AttributeSet attributeSet) {
        return new M(getContext(), attributeSet);
    }

    @Override // android.view.View
    public int getBaseline() {
        int i2;
        if (this.f1020b < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i3 = this.f1020b;
        if (childCount <= i3) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(i3);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.f1020b == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int bottom = this.f1021c;
        if (this.f1022d == 1 && (i2 = this.f1023e & 112) != 48) {
            if (i2 == 16) {
                bottom += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.f1024f) / 2;
            } else if (i2 == 80) {
                bottom = ((getBottom() - getTop()) - getPaddingBottom()) - this.f1024f;
            }
        }
        return bottom + ((ViewGroup.MarginLayoutParams) ((M) childAt.getLayoutParams())).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.f1020b;
    }

    public Drawable getDividerDrawable() {
        return this.f1029k;
    }

    public int getDividerPadding() {
        return this.f1033o;
    }

    public int getDividerWidth() {
        return this.f1030l;
    }

    public int getGravity() {
        return this.f1023e;
    }

    public int getOrientation() {
        return this.f1022d;
    }

    public int getShowDividers() {
        return this.f1032n;
    }

    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.f1025g;
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
    public M generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new M(layoutParams);
    }

    public final boolean i(int i2) {
        if (i2 == 0) {
            return (this.f1032n & 1) != 0;
        }
        if (i2 == getChildCount()) {
            return (this.f1032n & 4) != 0;
        }
        if ((this.f1032n & 2) == 0) {
            return false;
        }
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            if (getChildAt(i3).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int right;
        int left;
        int i2;
        if (this.f1029k == null) {
            return;
        }
        int i3 = 0;
        if (this.f1022d == 1) {
            int virtualChildCount = getVirtualChildCount();
            while (i3 < virtualChildCount) {
                View childAt = getChildAt(i3);
                if (childAt != null && childAt.getVisibility() != 8 && i(i3)) {
                    d(canvas, (childAt.getTop() - ((ViewGroup.MarginLayoutParams) ((M) childAt.getLayoutParams())).topMargin) - this.f1031m);
                }
                i3++;
            }
            if (i(virtualChildCount)) {
                View childAt2 = getChildAt(virtualChildCount - 1);
                d(canvas, childAt2 == null ? (getHeight() - getPaddingBottom()) - this.f1031m : childAt2.getBottom() + ((ViewGroup.MarginLayoutParams) ((M) childAt2.getLayoutParams())).bottomMargin);
                return;
            }
            return;
        }
        int virtualChildCount2 = getVirtualChildCount();
        boolean zA = y0.a(this);
        while (i3 < virtualChildCount2) {
            View childAt3 = getChildAt(i3);
            if (childAt3 != null && childAt3.getVisibility() != 8 && i(i3)) {
                M m2 = (M) childAt3.getLayoutParams();
                e(canvas, zA ? childAt3.getRight() + ((ViewGroup.MarginLayoutParams) m2).rightMargin : (childAt3.getLeft() - ((ViewGroup.MarginLayoutParams) m2).leftMargin) - this.f1030l);
            }
            i3++;
        }
        if (i(virtualChildCount2)) {
            View childAt4 = getChildAt(virtualChildCount2 - 1);
            if (childAt4 != null) {
                M m3 = (M) childAt4.getLayoutParams();
                if (zA) {
                    left = childAt4.getLeft() - ((ViewGroup.MarginLayoutParams) m3).leftMargin;
                    i2 = this.f1030l;
                    right = left - i2;
                } else {
                    right = childAt4.getRight() + ((ViewGroup.MarginLayoutParams) m3).rightMargin;
                }
            } else if (zA) {
                right = getPaddingLeft();
            } else {
                left = getWidth() - getPaddingRight();
                i2 = this.f1030l;
                right = left - i2;
            }
            e(canvas, right);
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(LinearLayoutCompat.class.getName());
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(LinearLayoutCompat.class.getName());
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x019e  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onLayout(boolean r24, int r25, int r26, int r27, int r28) {
        /*
            Method dump skipped, instruction units count: 458
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.onLayout(boolean, int, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:153:0x02f4  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0486  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x048b  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x04b3  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x04c0  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x04cc  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x04de  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x04f2  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0537  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0548  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0550  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x055b  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x05e4  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x0691  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0698  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x06b4  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x07cb  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0806  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x083d  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x0860  */
    /* JADX WARN: Removed duplicated region for block: B:442:? A[RETURN, SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onMeasure(int r38, int r39) {
        /*
            Method dump skipped, instruction units count: 2208
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.onMeasure(int, int):void");
    }

    public void setBaselineAligned(boolean z2) {
        this.f1019a = z2;
    }

    public void setBaselineAlignedChildIndex(int i2) {
        if (i2 >= 0 && i2 < getChildCount()) {
            this.f1020b = i2;
            return;
        }
        throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.f1029k) {
            return;
        }
        this.f1029k = drawable;
        if (drawable != null) {
            this.f1030l = drawable.getIntrinsicWidth();
            this.f1031m = drawable.getIntrinsicHeight();
        } else {
            this.f1030l = 0;
            this.f1031m = 0;
        }
        setWillNotDraw(drawable == null);
        requestLayout();
    }

    public void setDividerPadding(int i2) {
        this.f1033o = i2;
    }

    public void setGravity(int i2) {
        if (this.f1023e != i2) {
            if ((8388615 & i2) == 0) {
                i2 |= 8388611;
            }
            if ((i2 & 112) == 0) {
                i2 |= 48;
            }
            this.f1023e = i2;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i2) {
        int i3 = i2 & 8388615;
        int i4 = this.f1023e;
        if ((8388615 & i4) != i3) {
            this.f1023e = i3 | ((-8388616) & i4);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z2) {
        this.f1026h = z2;
    }

    public void setOrientation(int i2) {
        if (this.f1022d != i2) {
            this.f1022d = i2;
            requestLayout();
        }
    }

    public void setShowDividers(int i2) {
        if (i2 != this.f1032n) {
            requestLayout();
        }
        this.f1032n = i2;
    }

    public void setVerticalGravity(int i2) {
        int i3 = i2 & 112;
        int i4 = this.f1023e;
        if ((i4 & 112) != i3) {
            this.f1023e = i3 | (i4 & (-113));
            requestLayout();
        }
    }

    public void setWeightSum(float f2) {
        this.f1025g = Math.max(0.0f, f2);
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }
}
