package androidx.coordinatorlayout.widget;

import C.z;
import F.c;
import O.b;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.coordinatorlayout.R$attr;
import androidx.coordinatorlayout.R$style;
import androidx.coordinatorlayout.R$styleable;
import androidx.customview.view.AbsSavedState;
import e0.k;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;
import k.AbstractC0143b;
import k.C0146e;
import k.InterfaceC0142a;
import k.InterfaceC0144c;
import k.ViewGroupOnHierarchyChangeListenerC0145d;
import k.f;
import u.C0161b;
import v.h;
import v.i;
import v.l;
import v.o;

/* JADX INFO: loaded from: classes.dex */
public class CoordinatorLayout extends ViewGroup implements h {

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public static final String f1368s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public static final Class[] f1369t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public static final ThreadLocal f1370u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public static final c f1371v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public static final C0161b f1372w;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ArrayList f1373a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final z f1374b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final ArrayList f1375c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final ArrayList f1376d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int[] f1377e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f1378f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f1379g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final int[] f1380h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public View f1381i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public View f1382j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public b f1383k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public boolean f1384l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public o f1385m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public boolean f1386n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public Drawable f1387o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public ViewGroup.OnHierarchyChangeListener f1388p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public k f1389q;
    public final i r;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public SparseArray f1390c;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            int i2 = parcel.readInt();
            int[] iArr = new int[i2];
            parcel.readIntArray(iArr);
            Parcelable[] parcelableArray = parcel.readParcelableArray(classLoader);
            this.f1390c = new SparseArray(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                this.f1390c.append(iArr[i3], parcelableArray[i3]);
            }
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            SparseArray sparseArray = this.f1390c;
            int size = sparseArray != null ? sparseArray.size() : 0;
            parcel.writeInt(size);
            int[] iArr = new int[size];
            Parcelable[] parcelableArr = new Parcelable[size];
            for (int i3 = 0; i3 < size; i3++) {
                iArr[i3] = this.f1390c.keyAt(i3);
                parcelableArr[i3] = (Parcelable) this.f1390c.valueAt(i3);
            }
            parcel.writeIntArray(iArr);
            parcel.writeParcelableArray(parcelableArr, i2);
        }
    }

    static {
        Package r0 = CoordinatorLayout.class.getPackage();
        f1368s = r0 != null ? r0.getName() : null;
        f1371v = new c(1);
        f1369t = new Class[]{Context.class, AttributeSet.class};
        f1370u = new ThreadLocal();
        f1372w = new C0161b(12);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        int i2 = R$attr.coordinatorLayoutStyle;
        super(context, attributeSet, i2);
        this.f1373a = new ArrayList();
        this.f1374b = new z(2);
        this.f1375c = new ArrayList();
        this.f1376d = new ArrayList();
        this.f1377e = new int[2];
        this.r = new i();
        TypedArray typedArrayObtainStyledAttributes = i2 == 0 ? context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout, 0, R$style.Widget_Support_CoordinatorLayout) : context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout, i2, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.CoordinatorLayout_keylines, 0);
        if (resourceId != 0) {
            Resources resources = context.getResources();
            int[] intArray = resources.getIntArray(resourceId);
            this.f1380h = intArray;
            float f2 = resources.getDisplayMetrics().density;
            int length = intArray.length;
            for (int i3 = 0; i3 < length; i3++) {
                this.f1380h[i3] = (int) (r2[i3] * f2);
            }
        }
        this.f1387o = typedArrayObtainStyledAttributes.getDrawable(R$styleable.CoordinatorLayout_statusBarBackground);
        typedArrayObtainStyledAttributes.recycle();
        w();
        super.setOnHierarchyChangeListener(new ViewGroupOnHierarchyChangeListenerC0145d(this));
    }

    public static Rect f() {
        Rect rect = (Rect) f1372w.a();
        return rect == null ? new Rect() : rect;
    }

    public static void k(int i2, Rect rect, Rect rect2, C0146e c0146e, int i3, int i4) {
        int i5 = c0146e.f2748c;
        if (i5 == 0) {
            i5 = 17;
        }
        int absoluteGravity = Gravity.getAbsoluteGravity(i5, i2);
        int i6 = c0146e.f2749d;
        if ((i6 & 7) == 0) {
            i6 |= 8388611;
        }
        if ((i6 & 112) == 0) {
            i6 |= 48;
        }
        int absoluteGravity2 = Gravity.getAbsoluteGravity(i6, i2);
        int i7 = absoluteGravity & 7;
        int i8 = absoluteGravity & 112;
        int i9 = absoluteGravity2 & 7;
        int i10 = absoluteGravity2 & 112;
        int iWidth = i9 != 1 ? i9 != 5 ? rect.left : rect.right : rect.left + (rect.width() / 2);
        int iHeight = i10 != 16 ? i10 != 80 ? rect.top : rect.bottom : rect.top + (rect.height() / 2);
        if (i7 == 1) {
            iWidth -= i3 / 2;
        } else if (i7 != 5) {
            iWidth -= i3;
        }
        if (i8 == 16) {
            iHeight -= i4 / 2;
        } else if (i8 != 80) {
            iHeight -= i4;
        }
        rect2.set(iWidth, iHeight, i3 + iWidth, i4 + iHeight);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static C0146e m(View view) {
        C0146e c0146e = (C0146e) view.getLayoutParams();
        if (!c0146e.f2747b) {
            if (view instanceof InterfaceC0142a) {
                AbstractC0143b behavior = ((InterfaceC0142a) view).getBehavior();
                if (behavior == null) {
                    Log.e("CoordinatorLayout", "Attached behavior class is null");
                }
                if (c0146e.f2746a != behavior) {
                    c0146e.f2746a = behavior;
                    c0146e.f2747b = true;
                    if (behavior != null) {
                        behavior.c(c0146e);
                    }
                }
                c0146e.f2747b = true;
            } else {
                InterfaceC0144c interfaceC0144c = null;
                for (Class<?> superclass = view.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                    interfaceC0144c = (InterfaceC0144c) superclass.getAnnotation(InterfaceC0144c.class);
                    if (interfaceC0144c != null) {
                        break;
                    }
                }
                if (interfaceC0144c != null) {
                    try {
                        AbstractC0143b abstractC0143b = (AbstractC0143b) interfaceC0144c.value().getDeclaredConstructor(null).newInstance(null);
                        if (c0146e.f2746a != abstractC0143b) {
                            c0146e.f2746a = abstractC0143b;
                            c0146e.f2747b = true;
                            if (abstractC0143b != null) {
                                abstractC0143b.c(c0146e);
                            }
                        }
                    } catch (Exception e2) {
                        Log.e("CoordinatorLayout", "Default behavior class " + interfaceC0144c.value().getName() + " could not be instantiated. Did you forget a default constructor?", e2);
                    }
                }
                c0146e.f2747b = true;
            }
        }
        return c0146e;
    }

    public static void u(View view, int i2) {
        C0146e c0146e = (C0146e) view.getLayoutParams();
        int i3 = c0146e.f2754i;
        if (i3 != i2) {
            WeakHashMap weakHashMap = l.f2836a;
            view.offsetLeftAndRight(i2 - i3);
            c0146e.f2754i = i2;
        }
    }

    public static void v(View view, int i2) {
        C0146e c0146e = (C0146e) view.getLayoutParams();
        int i3 = c0146e.f2755j;
        if (i3 != i2) {
            WeakHashMap weakHashMap = l.f2836a;
            view.offsetTopAndBottom(i2 - i3);
            c0146e.f2755j = i2;
        }
    }

    @Override // v.h
    public final void a(View view, int i2, int i3, int i4, int i5, int i6) {
        AbstractC0143b abstractC0143b;
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                C0146e c0146e = (C0146e) childAt.getLayoutParams();
                if (c0146e.a(i6) && (abstractC0143b = c0146e.f2746a) != null) {
                    abstractC0143b.k(this, childAt, view, i2, i3, i4, i5, i6);
                    z2 = true;
                }
            }
        }
        if (z2) {
            o(1);
        }
    }

    @Override // v.h
    public final boolean b(View view, View view2, int i2, int i3) {
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                C0146e c0146e = (C0146e) childAt.getLayoutParams();
                AbstractC0143b abstractC0143b = c0146e.f2746a;
                if (abstractC0143b != null) {
                    boolean zP = abstractC0143b.p(this, childAt, view, view2, i2, i3);
                    z2 |= zP;
                    if (i3 == 0) {
                        c0146e.f2759n = zP;
                    } else if (i3 == 1) {
                        c0146e.f2760o = zP;
                    }
                } else if (i3 == 0) {
                    c0146e.f2759n = false;
                } else if (i3 == 1) {
                    c0146e.f2760o = false;
                }
            }
        }
        return z2;
    }

    @Override // v.h
    public final void c(View view, int i2) {
        this.r.f2834a = 0;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            C0146e c0146e = (C0146e) childAt.getLayoutParams();
            if (c0146e.a(i2)) {
                AbstractC0143b abstractC0143b = c0146e.f2746a;
                if (abstractC0143b != null) {
                    abstractC0143b.q(this, childAt, view, i2);
                }
                if (i2 == 0) {
                    c0146e.f2759n = false;
                } else if (i2 == 1) {
                    c0146e.f2760o = false;
                }
                c0146e.f2761p = false;
            }
        }
        this.f1382j = null;
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof C0146e) && super.checkLayoutParams(layoutParams);
    }

    @Override // v.h
    public final void d(View view, int i2, int i3, int[] iArr, int i4) {
        AbstractC0143b abstractC0143b;
        int childCount = getChildCount();
        boolean z2 = false;
        int iMax = 0;
        int iMax2 = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                C0146e c0146e = (C0146e) childAt.getLayoutParams();
                if (c0146e.a(i4) && (abstractC0143b = c0146e.f2746a) != null) {
                    int[] iArr2 = this.f1377e;
                    iArr2[1] = 0;
                    iArr2[0] = 0;
                    abstractC0143b.i(this, childAt, view, i3, iArr2, i4);
                    iMax = i2 > 0 ? Math.max(iMax, iArr2[0]) : Math.min(iMax, iArr2[0]);
                    iMax2 = i3 > 0 ? Math.max(iMax2, iArr2[1]) : Math.min(iMax2, iArr2[1]);
                    z2 = true;
                }
            }
        }
        iArr[0] = iMax;
        iArr[1] = iMax2;
        if (z2) {
            o(1);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j2) {
        AbstractC0143b abstractC0143b = ((C0146e) view.getLayoutParams()).f2746a;
        if (abstractC0143b != null) {
            abstractC0143b.getClass();
        }
        return super.drawChild(canvas, view, j2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.f1387o;
        if ((drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState)) {
            invalidate();
        }
    }

    @Override // v.h
    public final void e(View view, int i2, int i3) {
        this.r.f2834a = i2;
        this.f1382j = view;
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            ((C0146e) getChildAt(i4).getLayoutParams()).getClass();
        }
    }

    public final void g(C0146e c0146e, Rect rect, int i2, int i3) {
        int width = getWidth();
        int height = getHeight();
        int iMax = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) c0146e).leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i2) - ((ViewGroup.MarginLayoutParams) c0146e).rightMargin));
        int iMax2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) c0146e).topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i3) - ((ViewGroup.MarginLayoutParams) c0146e).bottomMargin));
        rect.set(iMax, iMax2, i2 + iMax, i3 + iMax2);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new C0146e();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new C0146e(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof C0146e ? new C0146e((C0146e) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new C0146e((ViewGroup.MarginLayoutParams) layoutParams) : new C0146e(layoutParams);
    }

    public final List<View> getDependencySortedChildren() {
        s();
        return Collections.unmodifiableList(this.f1373a);
    }

    public final o getLastWindowInsets() {
        return this.f1385m;
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.r.f2834a;
    }

    public Drawable getStatusBarBackground() {
        return this.f1387o;
    }

    @Override // android.view.View
    public int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingBottom() + getPaddingTop());
    }

    @Override // android.view.View
    public int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingRight() + getPaddingLeft());
    }

    public final void h(View view, Rect rect, boolean z2) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
        } else if (z2) {
            j(rect, view);
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    public final ArrayList i(View view) {
        h.k kVar = (h.k) this.f1374b.f106b;
        int i2 = kVar.f2611c;
        ArrayList arrayList = null;
        for (int i3 = 0; i3 < i2; i3++) {
            ArrayList arrayList2 = (ArrayList) kVar.j(i3);
            if (arrayList2 != null && arrayList2.contains(view)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(kVar.h(i3));
            }
        }
        ArrayList arrayList3 = this.f1376d;
        arrayList3.clear();
        if (arrayList != null) {
            arrayList3.addAll(arrayList);
        }
        return arrayList3;
    }

    public final void j(Rect rect, View view) {
        ThreadLocal threadLocal = f.f2763a;
        rect.set(0, 0, view.getWidth(), view.getHeight());
        ThreadLocal threadLocal2 = f.f2763a;
        Matrix matrix = (Matrix) threadLocal2.get();
        if (matrix == null) {
            matrix = new Matrix();
            threadLocal2.set(matrix);
        } else {
            matrix.reset();
        }
        f.a(this, view, matrix);
        ThreadLocal threadLocal3 = f.f2764b;
        RectF rectF = (RectF) threadLocal3.get();
        if (rectF == null) {
            rectF = new RectF();
            threadLocal3.set(rectF);
        }
        rectF.set(rect);
        matrix.mapRect(rectF);
        rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f), (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
    }

    public final int l(int i2) {
        int[] iArr = this.f1380h;
        if (iArr == null) {
            Log.e("CoordinatorLayout", "No keylines defined for " + this + " - attempted index lookup " + i2);
            return 0;
        }
        if (i2 >= 0 && i2 < iArr.length) {
            return iArr[i2];
        }
        Log.e("CoordinatorLayout", "Keyline index " + i2 + " out of range for " + this);
        return 0;
    }

    public final boolean n(View view, int i2, int i3) {
        C0161b c0161b = f1372w;
        Rect rectF = f();
        j(rectF, view);
        try {
            return rectF.contains(i2, i3);
        } finally {
            rectF.setEmpty();
            c0161b.c(rectF);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0044 A[EDGE_INSN: B:146:0x0044->B:10:0x0044 BREAK  A[LOOP:2: B:122:0x02d4->B:139:0x030a], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void o(int r26) {
        /*
            Method dump skipped, instruction units count: 821
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.o(int):void");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        int i2 = 1;
        super.onAttachedToWindow();
        t(false);
        if (this.f1384l) {
            if (this.f1383k == null) {
                this.f1383k = new b(i2, this);
            }
            getViewTreeObserver().addOnPreDrawListener(this.f1383k);
        }
        if (this.f1385m == null) {
            WeakHashMap weakHashMap = l.f2836a;
            if (getFitsSystemWindows()) {
                requestApplyInsets();
            }
        }
        this.f1379g = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        t(false);
        if (this.f1384l && this.f1383k != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.f1383k);
        }
        View view = this.f1382j;
        if (view != null) {
            c(view, 0);
        }
        this.f1379g = false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.f1386n || this.f1387o == null) {
            return;
        }
        o oVar = this.f1385m;
        int iC = oVar != null ? oVar.c() : 0;
        if (iC > 0) {
            this.f1387o.setBounds(0, 0, getWidth(), iC);
            this.f1387o.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            t(true);
        }
        boolean zR = r(motionEvent, 0);
        if (actionMasked == 1 || actionMasked == 3) {
            t(true);
        }
        return zR;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        AbstractC0143b abstractC0143b;
        WeakHashMap weakHashMap = l.f2836a;
        int layoutDirection = getLayoutDirection();
        ArrayList arrayList = this.f1373a;
        int size = arrayList.size();
        for (int i6 = 0; i6 < size; i6++) {
            View view = (View) arrayList.get(i6);
            if (view.getVisibility() != 8 && ((abstractC0143b = ((C0146e) view.getLayoutParams()).f2746a) == null || !abstractC0143b.f(this, view, layoutDirection))) {
                p(view, layoutDirection);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x018d  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onMeasure(int r33, int r34) {
        /*
            Method dump skipped, instruction units count: 519
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.onMeasure(int, int):void");
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                C0146e c0146e = (C0146e) childAt.getLayoutParams();
                if (c0146e.a(0)) {
                    AbstractC0143b abstractC0143b = c0146e.f2746a;
                }
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f2, float f3) {
        AbstractC0143b abstractC0143b;
        int childCount = getChildCount();
        boolean zH = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                C0146e c0146e = (C0146e) childAt.getLayoutParams();
                if (c0146e.a(0) && (abstractC0143b = c0146e.f2746a) != null) {
                    zH |= abstractC0143b.h(view);
                }
            }
        }
        return zH;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        d(view, i2, i3, iArr, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        a(view, i2, i3, i4, i5, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScrollAccepted(View view, View view2, int i2) {
        e(view2, i2, 0);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f1465a);
        SparseArray sparseArray = savedState.f1390c;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int id = childAt.getId();
            AbstractC0143b abstractC0143b = m(childAt).f2746a;
            if (id != -1 && abstractC0143b != null && (parcelable2 = (Parcelable) sparseArray.get(id)) != null) {
                abstractC0143b.m(childAt, parcelable2);
            }
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Parcelable parcelableN;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int id = childAt.getId();
            AbstractC0143b abstractC0143b = ((C0146e) childAt.getLayoutParams()).f2746a;
            if (id != -1 && abstractC0143b != null && (parcelableN = abstractC0143b.n(childAt)) != null) {
                sparseArray.append(id, parcelableN);
            }
        }
        savedState.f1390c = sparseArray;
        return savedState;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i2) {
        return b(view, view2, i2, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        c(view, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015 A[PHI: r3
      0x0015: PHI (r3v4 boolean) = (r3v2 boolean), (r3v5 boolean) binds: [B:10:0x0022, B:5:0x0012] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onTouchEvent(android.view.MotionEvent r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            int r2 = r18.getActionMasked()
            android.view.View r3 = r0.f1381i
            r4 = 1
            r5 = 0
            if (r3 != 0) goto L17
            boolean r3 = r0.r(r1, r4)
            if (r3 == 0) goto L15
            goto L18
        L15:
            r6 = r5
            goto L2a
        L17:
            r3 = r5
        L18:
            android.view.View r6 = r0.f1381i
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            k.e r6 = (k.C0146e) r6
            k.b r6 = r6.f2746a
            if (r6 == 0) goto L15
            android.view.View r7 = r0.f1381i
            boolean r6 = r6.r(r0, r7, r1)
        L2a:
            android.view.View r7 = r0.f1381i
            r8 = 0
            if (r7 != 0) goto L35
            boolean r1 = super.onTouchEvent(r18)
            r6 = r6 | r1
            goto L48
        L35:
            if (r3 == 0) goto L48
            long r11 = android.os.SystemClock.uptimeMillis()
            r15 = 0
            r16 = 0
            r13 = 3
            r14 = 0
            r9 = r11
            android.view.MotionEvent r8 = android.view.MotionEvent.obtain(r9, r11, r13, r14, r15, r16)
            super.onTouchEvent(r8)
        L48:
            if (r8 == 0) goto L4d
            r8.recycle()
        L4d:
            if (r2 == r4) goto L52
            r1 = 3
            if (r2 != r1) goto L55
        L52:
            r0.t(r5)
        L55:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void p(View view, int i2) {
        Rect rectF;
        Rect rectF2;
        C0146e c0146e = (C0146e) view.getLayoutParams();
        View view2 = c0146e.f2756k;
        if (view2 == null && c0146e.f2751f != -1) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        }
        C0161b c0161b = f1372w;
        if (view2 != null) {
            rectF = f();
            rectF2 = f();
            try {
                j(rectF, view2);
                C0146e c0146e2 = (C0146e) view.getLayoutParams();
                int measuredWidth = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                k(i2, rectF, rectF2, c0146e2, measuredWidth, measuredHeight);
                g(c0146e2, rectF2, measuredWidth, measuredHeight);
                view.layout(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom);
                return;
            } finally {
                rectF.setEmpty();
                c0161b.c(rectF);
                rectF2.setEmpty();
                c0161b.c(rectF2);
            }
        }
        int i3 = c0146e.f2750e;
        if (i3 < 0) {
            C0146e c0146e3 = (C0146e) view.getLayoutParams();
            rectF = f();
            rectF.set(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) c0146e3).leftMargin, getPaddingTop() + ((ViewGroup.MarginLayoutParams) c0146e3).topMargin, (getWidth() - getPaddingRight()) - ((ViewGroup.MarginLayoutParams) c0146e3).rightMargin, (getHeight() - getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) c0146e3).bottomMargin);
            if (this.f1385m != null) {
                WeakHashMap weakHashMap = l.f2836a;
                if (getFitsSystemWindows() && !view.getFitsSystemWindows()) {
                    rectF.left = this.f1385m.a() + rectF.left;
                    rectF.top = this.f1385m.c() + rectF.top;
                    rectF.right -= this.f1385m.b();
                    rectF.bottom -= ((WindowInsets) this.f1385m.f2838a).getSystemWindowInsetBottom();
                }
            }
            rectF2 = f();
            int i4 = c0146e3.f2748c;
            if ((i4 & 7) == 0) {
                i4 |= 8388611;
            }
            if ((i4 & 112) == 0) {
                i4 |= 48;
            }
            Gravity.apply(i4, view.getMeasuredWidth(), view.getMeasuredHeight(), rectF, rectF2, i2);
            view.layout(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom);
            return;
        }
        C0146e c0146e4 = (C0146e) view.getLayoutParams();
        int i5 = c0146e4.f2748c;
        if (i5 == 0) {
            i5 = 8388661;
        }
        int absoluteGravity = Gravity.getAbsoluteGravity(i5, i2);
        int i6 = absoluteGravity & 7;
        int i7 = absoluteGravity & 112;
        int width = getWidth();
        int height = getHeight();
        int measuredWidth2 = view.getMeasuredWidth();
        int measuredHeight2 = view.getMeasuredHeight();
        if (i2 == 1) {
            i3 = width - i3;
        }
        int iL = l(i3) - measuredWidth2;
        if (i6 == 1) {
            iL += measuredWidth2 / 2;
        } else if (i6 == 5) {
            iL += measuredWidth2;
        }
        int i8 = i7 != 16 ? i7 != 80 ? 0 : measuredHeight2 : measuredHeight2 / 2;
        int iMax = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) c0146e4).leftMargin, Math.min(iL, ((width - getPaddingRight()) - measuredWidth2) - ((ViewGroup.MarginLayoutParams) c0146e4).rightMargin));
        int iMax2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) c0146e4).topMargin, Math.min(i8, ((height - getPaddingBottom()) - measuredHeight2) - ((ViewGroup.MarginLayoutParams) c0146e4).bottomMargin));
        view.layout(iMax, iMax2, measuredWidth2 + iMax, measuredHeight2 + iMax2);
    }

    public final void q(View view, int i2, int i3, int i4) {
        measureChildWithMargins(view, i2, i3, i4, 0);
    }

    public final boolean r(MotionEvent motionEvent, int i2) {
        boolean z2;
        int actionMasked = motionEvent.getActionMasked();
        ArrayList arrayList = this.f1375c;
        arrayList.clear();
        boolean zIsChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        int childCount = getChildCount();
        for (int i3 = childCount - 1; i3 >= 0; i3--) {
            arrayList.add(getChildAt(zIsChildrenDrawingOrderEnabled ? getChildDrawingOrder(childCount, i3) : i3));
        }
        c cVar = f1371v;
        if (cVar != null) {
            Collections.sort(arrayList, cVar);
        }
        int size = arrayList.size();
        MotionEvent motionEventObtain = null;
        boolean zE = false;
        boolean z3 = false;
        for (int i4 = 0; i4 < size; i4++) {
            View view = (View) arrayList.get(i4);
            C0146e c0146e = (C0146e) view.getLayoutParams();
            AbstractC0143b abstractC0143b = c0146e.f2746a;
            if (!(zE || z3) || actionMasked == 0) {
                if (!zE && abstractC0143b != null) {
                    if (i2 == 0) {
                        zE = abstractC0143b.e(this, view, motionEvent);
                    } else if (i2 == 1) {
                        zE = abstractC0143b.r(this, view, motionEvent);
                    }
                    if (zE) {
                        this.f1381i = view;
                    }
                }
                if (c0146e.f2746a == null) {
                    c0146e.f2758m = false;
                }
                boolean z4 = c0146e.f2758m;
                if (z4) {
                    z2 = true;
                } else {
                    c0146e.f2758m = z4;
                    z2 = z4;
                }
                z3 = z2 && !z4;
                if (z2 && !z3) {
                    break;
                }
            } else if (abstractC0143b != null) {
                if (motionEventObtain == null) {
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                }
                if (i2 == 0) {
                    abstractC0143b.e(this, view, motionEventObtain);
                } else if (i2 == 1) {
                    abstractC0143b.r(this, view, motionEventObtain);
                }
            }
        }
        arrayList.clear();
        return zE;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
        AbstractC0143b abstractC0143b = ((C0146e) view.getLayoutParams()).f2746a;
        if (abstractC0143b == null || !abstractC0143b.l(this, view, rect, z2)) {
            return super.requestChildRectangleOnScreen(view, rect, z2);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z2) {
        super.requestDisallowInterceptTouchEvent(z2);
        if (!z2 || this.f1378f) {
            return;
        }
        t(false);
        this.f1378f = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0106  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void s() {
        /*
            Method dump skipped, instruction units count: 402
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.s():void");
    }

    @Override // android.view.View
    public void setFitsSystemWindows(boolean z2) {
        super.setFitsSystemWindows(z2);
        w();
    }

    @Override // android.view.ViewGroup
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.f1388p = onHierarchyChangeListener;
    }

    public void setStatusBarBackground(Drawable drawable) {
        Drawable drawable2 = this.f1387o;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
            this.f1387o = drawableMutate;
            if (drawableMutate != null) {
                if (drawableMutate.isStateful()) {
                    this.f1387o.setState(getDrawableState());
                }
                Drawable drawable3 = this.f1387o;
                WeakHashMap weakHashMap = l.f2836a;
                drawable3.setLayoutDirection(getLayoutDirection());
                this.f1387o.setVisible(getVisibility() == 0, false);
                this.f1387o.setCallback(this);
            }
            WeakHashMap weakHashMap2 = l.f2836a;
            postInvalidateOnAnimation();
        }
    }

    public void setStatusBarBackgroundColor(int i2) {
        setStatusBarBackground(new ColorDrawable(i2));
    }

    public void setStatusBarBackgroundResource(int i2) {
        setStatusBarBackground(i2 != 0 ? getContext().getDrawable(i2) : null);
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        boolean z2 = i2 == 0;
        Drawable drawable = this.f1387o;
        if (drawable == null || drawable.isVisible() == z2) {
            return;
        }
        this.f1387o.setVisible(z2, false);
    }

    public final void t(boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            AbstractC0143b abstractC0143b = ((C0146e) childAt.getLayoutParams()).f2746a;
            if (abstractC0143b != null) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                if (z2) {
                    abstractC0143b.e(this, childAt, motionEventObtain);
                } else {
                    abstractC0143b.r(this, childAt, motionEventObtain);
                }
                motionEventObtain.recycle();
            }
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            ((C0146e) getChildAt(i3).getLayoutParams()).f2758m = false;
        }
        this.f1381i = null;
        this.f1378f = false;
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.f1387o;
    }

    public final void w() {
        WeakHashMap weakHashMap = l.f2836a;
        if (!getFitsSystemWindows()) {
            l.c(this, null);
            return;
        }
        if (this.f1389q == null) {
            this.f1389q = new k(this);
        }
        l.c(this, this.f1389q);
        setSystemUiVisibility(1280);
    }
}
