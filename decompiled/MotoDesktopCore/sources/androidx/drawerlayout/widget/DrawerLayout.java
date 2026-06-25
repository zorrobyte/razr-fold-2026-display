package androidx.drawerlayout.widget;

import X.w0;
import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.customview.view.AbsSavedState;
import java.util.ArrayList;
import java.util.WeakHashMap;
import v.l;
import y.C0167b;
import y.InterfaceC0166a;

/* JADX INFO: loaded from: classes.dex */
public class DrawerLayout extends ViewGroup {

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public static final int[] f1466m = {R.attr.layout_gravity};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public float f1467a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public float f1468b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f1469c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f1470d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f1471e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1472f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f1473g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f1474h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public ArrayList f1475i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public Drawable f1476j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public Rect f1477k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public Matrix f1478l;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f1479c;

        /* JADX INFO: renamed from: d, reason: collision with root package name */
        public int f1480d;

        /* JADX INFO: renamed from: e, reason: collision with root package name */
        public int f1481e;

        /* JADX INFO: renamed from: f, reason: collision with root package name */
        public int f1482f;

        /* JADX INFO: renamed from: g, reason: collision with root package name */
        public int f1483g;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f1479c = 0;
            this.f1479c = parcel.readInt();
            this.f1480d = parcel.readInt();
            this.f1481e = parcel.readInt();
            this.f1482f = parcel.readInt();
            this.f1483g = parcel.readInt();
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f1479c);
            parcel.writeInt(this.f1480d);
            parcel.writeInt(this.f1481e);
            parcel.writeInt(this.f1482f);
            parcel.writeInt(this.f1483g);
        }
    }

    public static boolean e(View view) {
        return ((C0167b) view.getLayoutParams()).f2863a == 0;
    }

    public static boolean f(View view) {
        int i2 = ((C0167b) view.getLayoutParams()).f2863a;
        WeakHashMap weakHashMap = l.f2836a;
        int absoluteGravity = Gravity.getAbsoluteGravity(i2, view.getLayoutDirection());
        return ((absoluteGravity & 3) == 0 && (absoluteGravity & 5) == 0) ? false : true;
    }

    public final boolean a(View view) {
        int i2 = ((C0167b) view.getLayoutParams()).f2863a;
        WeakHashMap weakHashMap = l.f2836a;
        return (Gravity.getAbsoluteGravity(i2, getLayoutDirection()) & 3) == 3;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList arrayList, int i2, int i3) {
        if (getDescendantFocusability() == 393216) {
            return;
        }
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (!f(childAt)) {
                throw null;
            }
            if (!f(childAt)) {
                throw new IllegalArgumentException("View " + childAt + " is not a drawer");
            }
            if ((((C0167b) childAt.getLayoutParams()).f2865c & 1) == 1) {
                childAt.addFocusables(arrayList, i2, i3);
                z2 = true;
            }
        }
        if (!z2) {
            throw null;
        }
        throw null;
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        View childAt;
        super.addView(view, i2, layoutParams);
        int childCount = getChildCount();
        int i3 = 0;
        while (true) {
            if (i3 >= childCount) {
                childAt = null;
                break;
            }
            childAt = getChildAt(i3);
            if ((((C0167b) childAt.getLayoutParams()).f2865c & 1) == 1) {
                break;
            } else {
                i3++;
            }
        }
        if (childAt != null || f(view)) {
            WeakHashMap weakHashMap = l.f2836a;
            view.setImportantForAccessibility(4);
        } else {
            WeakHashMap weakHashMap2 = l.f2836a;
            view.setImportantForAccessibility(1);
        }
    }

    public final void b(boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            C0167b c0167b = (C0167b) childAt.getLayoutParams();
            if (f(childAt)) {
                if (!z2) {
                    childAt.getWidth();
                    if (a(childAt)) {
                        childAt.getTop();
                        throw null;
                    }
                    getWidth();
                    childAt.getTop();
                    throw null;
                }
                c0167b.getClass();
            }
        }
        throw null;
    }

    public final View c(int i2) {
        WeakHashMap weakHashMap = l.f2836a;
        int absoluteGravity = Gravity.getAbsoluteGravity(i2, getLayoutDirection()) & 7;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            int i4 = ((C0167b) childAt.getLayoutParams()).f2863a;
            WeakHashMap weakHashMap2 = l.f2836a;
            if ((Gravity.getAbsoluteGravity(i4, getLayoutDirection()) & 7) == absoluteGravity) {
                return childAt;
            }
        }
        return null;
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof C0167b) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public final void computeScroll() {
        int childCount = getChildCount();
        float fMax = 0.0f;
        for (int i2 = 0; i2 < childCount; i2++) {
            fMax = Math.max(fMax, ((C0167b) getChildAt(i2).getLayoutParams()).f2864b);
        }
        this.f1468b = fMax;
        throw null;
    }

    public final View d() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (f(childAt)) {
                if (!f(childAt)) {
                    throw new IllegalArgumentException("View " + childAt + " is not a drawer");
                }
                if (((C0167b) childAt.getLayoutParams()).f2864b > 0.0f) {
                    return childAt;
                }
            }
        }
        return null;
    }

    @Override // android.view.View
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        boolean zDispatchGenericMotionEvent;
        if ((motionEvent.getSource() & 2) == 0 || motionEvent.getAction() == 10 || this.f1468b <= 0.0f) {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
            View childAt = getChildAt(i2);
            if (this.f1477k == null) {
                this.f1477k = new Rect();
            }
            childAt.getHitRect(this.f1477k);
            if (this.f1477k.contains((int) x2, (int) y2) && !e(childAt)) {
                if (childAt.getMatrix().isIdentity()) {
                    float scrollX = getScrollX() - childAt.getLeft();
                    float scrollY = getScrollY() - childAt.getTop();
                    motionEvent.offsetLocation(scrollX, scrollY);
                    zDispatchGenericMotionEvent = childAt.dispatchGenericMotionEvent(motionEvent);
                    motionEvent.offsetLocation(-scrollX, -scrollY);
                } else {
                    float scrollX2 = getScrollX() - childAt.getLeft();
                    float scrollY2 = getScrollY() - childAt.getTop();
                    MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                    motionEventObtain.offsetLocation(scrollX2, scrollY2);
                    Matrix matrix = childAt.getMatrix();
                    if (!matrix.isIdentity()) {
                        if (this.f1478l == null) {
                            this.f1478l = new Matrix();
                        }
                        matrix.invert(this.f1478l);
                        motionEventObtain.transform(this.f1478l);
                    }
                    zDispatchGenericMotionEvent = childAt.dispatchGenericMotionEvent(motionEventObtain);
                    motionEventObtain.recycle();
                }
                if (zDispatchGenericMotionEvent) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j2) {
        Drawable background;
        int height = getHeight();
        boolean zE = e(view);
        int width = getWidth();
        int iSave = canvas.save();
        if (zE) {
            int childCount = getChildCount();
            int i2 = 0;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt != view && childAt.getVisibility() == 0 && (background = childAt.getBackground()) != null && background.getOpacity() == -1 && f(childAt) && childAt.getHeight() >= height) {
                    if (a(childAt)) {
                        int right = childAt.getRight();
                        if (right > i2) {
                            i2 = right;
                        }
                    } else {
                        int left = childAt.getLeft();
                        if (left < width) {
                            width = left;
                        }
                    }
                }
            }
            canvas.clipRect(i2, 0, width, getHeight());
        }
        boolean zDrawChild = super.drawChild(canvas, view, j2);
        canvas.restoreToCount(iSave);
        if (this.f1468b <= 0.0f || !zE) {
            return zDrawChild;
        }
        throw null;
    }

    public final void g(View view) {
        if (!f(view)) {
            throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
        }
        C0167b c0167b = (C0167b) view.getLayoutParams();
        if (!this.f1470d) {
            c0167b.f2865c |= 2;
            if (a(view)) {
                view.getTop();
                throw null;
            }
            getWidth();
            view.getWidth();
            view.getTop();
            throw null;
        }
        c0167b.f2864b = 1.0f;
        c0167b.f2865c = 1;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt == view) {
                WeakHashMap weakHashMap = l.f2836a;
                childAt.setImportantForAccessibility(1);
            } else {
                WeakHashMap weakHashMap2 = l.f2836a;
                childAt.setImportantForAccessibility(4);
            }
        }
        invalidate();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        C0167b c0167b = new C0167b(-1, -1);
        c0167b.f2863a = 0;
        return c0167b;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        C0167b c0167b = new C0167b(context, attributeSet);
        c0167b.f2863a = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f1466m);
        c0167b.f2863a = typedArrayObtainStyledAttributes.getInt(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return c0167b;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof C0167b) {
            C0167b c0167b = (C0167b) layoutParams;
            C0167b c0167b2 = new C0167b(c0167b);
            c0167b2.f2863a = 0;
            c0167b2.f2863a = c0167b.f2863a;
            return c0167b2;
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            C0167b c0167b3 = new C0167b((ViewGroup.MarginLayoutParams) layoutParams);
            c0167b3.f2863a = 0;
            return c0167b3;
        }
        C0167b c0167b4 = new C0167b(layoutParams);
        c0167b4.f2863a = 0;
        return c0167b4;
    }

    public float getDrawerElevation() {
        return this.f1467a;
    }

    public Drawable getStatusBarBackgroundDrawable() {
        return this.f1476j;
    }

    public final void h(int i2, int i3) {
        View viewC;
        WeakHashMap weakHashMap = l.f2836a;
        int absoluteGravity = Gravity.getAbsoluteGravity(i3, getLayoutDirection());
        if (i3 == 3) {
            this.f1471e = i2;
        } else if (i3 == 5) {
            this.f1472f = i2;
        } else if (i3 == 8388611) {
            this.f1473g = i2;
        } else if (i3 == 8388613) {
            this.f1474h = i2;
        }
        if (i2 != 0) {
            throw null;
        }
        if (i2 != 1) {
            if (i2 == 2 && (viewC = c(absoluteGravity)) != null) {
                g(viewC);
                return;
            }
            return;
        }
        View viewC2 = c(absoluteGravity);
        if (viewC2 != null) {
            if (!f(viewC2)) {
                throw new IllegalArgumentException("View " + viewC2 + " is not a sliding drawer");
            }
            C0167b c0167b = (C0167b) viewC2.getLayoutParams();
            if (this.f1470d) {
                c0167b.f2864b = 0.0f;
                c0167b.f2865c = 0;
                invalidate();
                return;
            }
            c0167b.f2865c |= 4;
            if (a(viewC2)) {
                viewC2.getWidth();
                viewC2.getTop();
                throw null;
            }
            getWidth();
            viewC2.getTop();
            throw null;
        }
    }

    public final void i(View view, float f2) {
        int size;
        C0167b c0167b = (C0167b) view.getLayoutParams();
        if (f2 == c0167b.f2864b) {
            return;
        }
        c0167b.f2864b = f2;
        if (this.f1475i == null || r2.size() - 1 < 0) {
            return;
        }
        w0.c(this.f1475i.get(size));
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.f1470d = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.f1470d = true;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        motionEvent.getActionMasked();
        throw null;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 != 4 || d() == null) {
            return super.onKeyDown(i2, keyEvent);
        }
        keyEvent.startTracking();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x006f  */
    @Override // android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onKeyUp(int r5, android.view.KeyEvent r6) {
        /*
            r4 = this;
            r0 = 4
            if (r5 != r0) goto L91
            android.view.View r5 = r4.d()
            r6 = 0
            if (r5 == 0) goto L8d
            boolean r0 = f(r5)
            if (r0 == 0) goto L74
            android.view.ViewGroup$LayoutParams r0 = r5.getLayoutParams()
            y.b r0 = (y.C0167b) r0
            int r0 = r0.f2863a
            java.util.WeakHashMap r1 = v.l.f2836a
            int r1 = r4.getLayoutDirection()
            r2 = 3
            if (r0 == r2) goto L5c
            r3 = 5
            if (r0 == r3) goto L4d
            r3 = 8388611(0x800003, float:1.1754948E-38)
            if (r0 == r3) goto L3e
            r3 = 8388613(0x800005, float:1.175495E-38)
            if (r0 == r3) goto L2f
            goto L6b
        L2f:
            int r0 = r4.f1474h
            if (r0 == r2) goto L34
            goto L6c
        L34:
            if (r1 != 0) goto L39
            int r0 = r4.f1472f
            goto L3b
        L39:
            int r0 = r4.f1471e
        L3b:
            if (r0 == r2) goto L6b
            goto L6c
        L3e:
            int r0 = r4.f1473g
            if (r0 == r2) goto L43
            goto L6c
        L43:
            if (r1 != 0) goto L48
            int r0 = r4.f1471e
            goto L4a
        L48:
            int r0 = r4.f1472f
        L4a:
            if (r0 == r2) goto L6b
            goto L6c
        L4d:
            int r0 = r4.f1472f
            if (r0 == r2) goto L52
            goto L6c
        L52:
            if (r1 != 0) goto L57
            int r0 = r4.f1474h
            goto L59
        L57:
            int r0 = r4.f1473g
        L59:
            if (r0 == r2) goto L6b
            goto L6c
        L5c:
            int r0 = r4.f1471e
            if (r0 == r2) goto L61
            goto L6c
        L61:
            if (r1 != 0) goto L66
            int r0 = r4.f1473g
            goto L68
        L66:
            int r0 = r4.f1474h
        L68:
            if (r0 == r2) goto L6b
            goto L6c
        L6b:
            r0 = r6
        L6c:
            if (r0 == 0) goto L6f
            goto L8d
        L6f:
            r4.b(r6)
            r4 = 0
            throw r4
        L74:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "View "
            r6.<init>(r0)
            r6.append(r5)
            java.lang.String r5 = " is not a drawer"
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            r4.<init>(r5)
            throw r4
        L8d:
            if (r5 == 0) goto L90
            r6 = 1
        L90:
            return r6
        L91:
            boolean r4 = super.onKeyUp(r5, r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.drawerlayout.widget.DrawerLayout.onKeyUp(int, android.view.KeyEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        float f2;
        int i6;
        boolean z3 = true;
        this.f1469c = true;
        int i7 = i4 - i2;
        int childCount = getChildCount();
        int i8 = 0;
        while (i8 < childCount) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                C0167b c0167b = (C0167b) childAt.getLayoutParams();
                if (e(childAt)) {
                    int i9 = ((ViewGroup.MarginLayoutParams) c0167b).leftMargin;
                    childAt.layout(i9, ((ViewGroup.MarginLayoutParams) c0167b).topMargin, childAt.getMeasuredWidth() + i9, childAt.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) c0167b).topMargin);
                } else {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (a(childAt)) {
                        float f3 = measuredWidth;
                        i6 = (-measuredWidth) + ((int) (c0167b.f2864b * f3));
                        f2 = (measuredWidth + i6) / f3;
                    } else {
                        float f4 = measuredWidth;
                        f2 = (i7 - r11) / f4;
                        i6 = i7 - ((int) (c0167b.f2864b * f4));
                    }
                    boolean z4 = f2 != c0167b.f2864b ? z3 : false;
                    int i10 = c0167b.f2863a & 112;
                    if (i10 == 16) {
                        int i11 = i5 - i3;
                        int i12 = (i11 - measuredHeight) / 2;
                        int i13 = ((ViewGroup.MarginLayoutParams) c0167b).topMargin;
                        if (i12 < i13) {
                            i12 = i13;
                        } else {
                            int i14 = i12 + measuredHeight;
                            int i15 = i11 - ((ViewGroup.MarginLayoutParams) c0167b).bottomMargin;
                            if (i14 > i15) {
                                i12 = i15 - measuredHeight;
                            }
                        }
                        childAt.layout(i6, i12, measuredWidth + i6, measuredHeight + i12);
                    } else if (i10 != 80) {
                        int i16 = ((ViewGroup.MarginLayoutParams) c0167b).topMargin;
                        childAt.layout(i6, i16, measuredWidth + i6, measuredHeight + i16);
                    } else {
                        int i17 = i5 - i3;
                        childAt.layout(i6, (i17 - ((ViewGroup.MarginLayoutParams) c0167b).bottomMargin) - childAt.getMeasuredHeight(), measuredWidth + i6, i17 - ((ViewGroup.MarginLayoutParams) c0167b).bottomMargin);
                    }
                    if (z4) {
                        i(childAt, f2);
                    }
                    int i18 = c0167b.f2864b > 0.0f ? 0 : 4;
                    if (childAt.getVisibility() != i18) {
                        childAt.setVisibility(i18);
                    }
                }
            }
            i8++;
            z3 = true;
        }
        this.f1469c = false;
        this.f1470d = false;
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        int i4 = 3;
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        if (mode != 1073741824 || mode2 != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
            if (mode != Integer.MIN_VALUE && mode == 0) {
                size = 300;
            }
            if (mode2 != Integer.MIN_VALUE && mode2 == 0) {
                size2 = 300;
            }
        }
        setMeasuredDimension(size, size2);
        WeakHashMap weakHashMap = l.f2836a;
        getLayoutDirection();
        int childCount = getChildCount();
        int i5 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i5 < childCount) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                C0167b c0167b = (C0167b) childAt.getLayoutParams();
                if (e(childAt)) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec((size - ((ViewGroup.MarginLayoutParams) c0167b).leftMargin) - ((ViewGroup.MarginLayoutParams) c0167b).rightMargin, 1073741824), View.MeasureSpec.makeMeasureSpec((size2 - ((ViewGroup.MarginLayoutParams) c0167b).topMargin) - ((ViewGroup.MarginLayoutParams) c0167b).bottomMargin, 1073741824));
                } else {
                    if (!f(childAt)) {
                        throw new IllegalStateException("Child " + childAt + " at index " + i5 + " does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY");
                    }
                    float elevation = childAt.getElevation();
                    float f2 = this.f1467a;
                    if (elevation != f2) {
                        childAt.setElevation(f2);
                    }
                    int i6 = ((C0167b) childAt.getLayoutParams()).f2863a;
                    WeakHashMap weakHashMap2 = l.f2836a;
                    int absoluteGravity = Gravity.getAbsoluteGravity(i6, getLayoutDirection());
                    int i7 = absoluteGravity & 7;
                    boolean z4 = i7 == i4;
                    if ((z4 && z2) || (!z4 && z3)) {
                        StringBuilder sb = new StringBuilder("Child drawer has absolute gravity ");
                        sb.append((absoluteGravity & 3) != i4 ? (absoluteGravity & 5) == 5 ? "RIGHT" : Integer.toHexString(i7) : "LEFT");
                        sb.append(" but this DrawerLayout already has a drawer view along that edge");
                        throw new IllegalStateException(sb.toString());
                    }
                    if (z4) {
                        z2 = true;
                    } else {
                        z3 = true;
                    }
                    childAt.measure(ViewGroup.getChildMeasureSpec(i2, ((ViewGroup.MarginLayoutParams) c0167b).leftMargin + ((ViewGroup.MarginLayoutParams) c0167b).rightMargin, ((ViewGroup.MarginLayoutParams) c0167b).width), ViewGroup.getChildMeasureSpec(i3, ((ViewGroup.MarginLayoutParams) c0167b).topMargin + ((ViewGroup.MarginLayoutParams) c0167b).bottomMargin, ((ViewGroup.MarginLayoutParams) c0167b).height));
                }
            }
            i5++;
            i4 = 3;
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        View viewC;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f1465a);
        int i2 = savedState.f1479c;
        if (i2 != 0 && (viewC = c(i2)) != null) {
            g(viewC);
        }
        int i3 = savedState.f1480d;
        if (i3 != 3) {
            h(i3, 3);
        }
        int i4 = savedState.f1481e;
        if (i4 != 3) {
            h(i4, 5);
        }
        int i5 = savedState.f1482f;
        if (i5 != 3) {
            h(i5, 8388611);
        }
        int i6 = savedState.f1483g;
        if (i6 != 3) {
            h(i6, 8388613);
        }
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i2) {
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f1479c = 0;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            C0167b c0167b = (C0167b) getChildAt(i2).getLayoutParams();
            int i3 = c0167b.f2865c;
            boolean z2 = i3 == 1;
            boolean z3 = i3 == 2;
            if (z2 || z3) {
                savedState.f1479c = c0167b.f2863a;
                break;
            }
        }
        savedState.f1480d = this.f1471e;
        savedState.f1481e = this.f1472f;
        savedState.f1482f = this.f1473g;
        savedState.f1483g = this.f1474h;
        return savedState;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z2) {
        super.requestDisallowInterceptTouchEvent(z2);
        if (z2) {
            b(true);
            throw null;
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        if (this.f1469c) {
            return;
        }
        super.requestLayout();
    }

    public void setDrawerElevation(float f2) {
        this.f1467a = f2;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (f(childAt)) {
                float f3 = this.f1467a;
                WeakHashMap weakHashMap = l.f2836a;
                childAt.setElevation(f3);
            }
        }
    }

    @Deprecated
    public void setDrawerListener(InterfaceC0166a interfaceC0166a) {
        if (interfaceC0166a != null) {
            if (this.f1475i == null) {
                this.f1475i = new ArrayList();
            }
            this.f1475i.add(interfaceC0166a);
        }
    }

    public void setDrawerLockMode(int i2) {
        h(i2, 3);
        h(i2, 5);
    }

    public void setScrimColor(int i2) {
        invalidate();
    }

    public void setStatusBarBackground(int i2) {
        this.f1476j = i2 != 0 ? getContext().getDrawable(i2) : null;
        invalidate();
    }

    public void setStatusBarBackground(Drawable drawable) {
        this.f1476j = drawable;
        invalidate();
    }

    public void setStatusBarBackgroundColor(int i2) {
        this.f1476j = new ColorDrawable(i2);
        invalidate();
    }
}
