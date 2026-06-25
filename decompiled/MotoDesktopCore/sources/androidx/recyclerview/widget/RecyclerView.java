package androidx.recyclerview.widget;

import X.w0;
import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.customview.view.AbsSavedState;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerView extends ViewGroup implements v.e {

    /* JADX INFO: renamed from: i0, reason: collision with root package name */
    public static final int[] f1763i0 = {R.attr.nestedScrollingEnabled};

    /* JADX INFO: renamed from: j0, reason: collision with root package name */
    public static final int[] f1764j0 = {R.attr.clipToPadding};
    public static final Class[] k0;
    public static final p l0;

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public EdgeEffect f1765A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public EdgeEffect f1766B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public EdgeEffect f1767C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public EdgeEffect f1768D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public t f1769E;

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public int f1770F;

    /* JADX INFO: renamed from: G, reason: collision with root package name */
    public int f1771G;

    /* JADX INFO: renamed from: H, reason: collision with root package name */
    public VelocityTracker f1772H;

    /* JADX INFO: renamed from: I, reason: collision with root package name */
    public int f1773I;

    /* JADX INFO: renamed from: J, reason: collision with root package name */
    public int f1774J;

    /* JADX INFO: renamed from: K, reason: collision with root package name */
    public int f1775K;

    /* JADX INFO: renamed from: L, reason: collision with root package name */
    public int f1776L;

    /* JADX INFO: renamed from: M, reason: collision with root package name */
    public int f1777M;

    /* JADX INFO: renamed from: N, reason: collision with root package name */
    public final int f1778N;

    /* JADX INFO: renamed from: O, reason: collision with root package name */
    public final int f1779O;

    /* JADX INFO: renamed from: P, reason: collision with root package name */
    public final float f1780P;

    /* JADX INFO: renamed from: Q, reason: collision with root package name */
    public final float f1781Q;

    /* JADX INFO: renamed from: R, reason: collision with root package name */
    public boolean f1782R;

    /* JADX INFO: renamed from: S, reason: collision with root package name */
    public final G f1783S;

    /* JADX INFO: renamed from: T, reason: collision with root package name */
    public RunnableC0116i f1784T;

    /* JADX INFO: renamed from: U, reason: collision with root package name */
    public final C0114g f1785U;
    public final E V;

    /* JADX INFO: renamed from: W, reason: collision with root package name */
    public ArrayList f1786W;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final B f1787a;

    /* JADX INFO: renamed from: a0, reason: collision with root package name */
    public final C0120m f1788a0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public SavedState f1789b;

    /* JADX INFO: renamed from: b0, reason: collision with root package name */
    public H f1790b0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final f0.b f1791c;

    /* JADX INFO: renamed from: c0, reason: collision with root package name */
    public v.g f1792c0;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final f0.b f1793d;

    /* JADX INFO: renamed from: d0, reason: collision with root package name */
    public final int[] f1794d0;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final C0120m f1795e;

    /* JADX INFO: renamed from: e0, reason: collision with root package name */
    public final int[] f1796e0;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f1797f;

    /* JADX INFO: renamed from: f0, reason: collision with root package name */
    public final int[] f1798f0;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final Rect f1799g;

    /* JADX INFO: renamed from: g0, reason: collision with root package name */
    public final ArrayList f1800g0;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final Rect f1801h;
    public final F.e h0;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public v f1802i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final ArrayList f1803j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final ArrayList f1804k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public C0112e f1805l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public boolean f1806m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public boolean f1807n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public boolean f1808o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public int f1809p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public boolean f1810q;
    public boolean r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public boolean f1811s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public int f1812t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public final AccessibilityManager f1813u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public boolean f1814v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public boolean f1815w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public int f1816x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public final int f1817y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public s f1818z;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new D();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public Parcelable f1819c;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f1819c = parcel.readParcelable(classLoader == null ? v.class.getClassLoader() : classLoader);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeParcelable(this.f1819c, 0);
        }
    }

    static {
        Class cls = Integer.TYPE;
        k0 = new Class[]{Context.class, AttributeSet.class, cls, cls};
        l0 = new p();
    }

    /* JADX WARN: Removed duplicated region for block: B:82:0x038e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public RecyclerView(android.content.Context r19, android.util.AttributeSet r20) throws java.lang.NoSuchMethodException {
        /*
            Method dump skipped, instruction units count: 933
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    private v.g getScrollingChildHelper() {
        if (this.f1792c0 == null) {
            this.f1792c0 = new v.g(this);
        }
        return this.f1792c0;
    }

    public static void l(View view) {
        if (view == null) {
            return;
        }
        ((w) view.getLayoutParams()).getClass();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList arrayList, int i2, int i3) {
        v vVar = this.f1802i;
        if (vVar != null) {
            vVar.getClass();
        }
        super.addFocusables(arrayList, i2, i3);
    }

    public final void c(String str) {
        if (this.f1816x > 0) {
            if (str != null) {
                throw new IllegalStateException(str);
            }
            throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling" + j());
        }
        if (this.f1817y > 0) {
            Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException("" + j()));
        }
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof w) && this.f1802i.d((w) layoutParams);
    }

    @Override // android.view.View
    public final int computeHorizontalScrollExtent() {
        v vVar = this.f1802i;
        if (vVar != null && vVar.b()) {
            return this.f1802i.f(this.V);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeHorizontalScrollOffset() {
        v vVar = this.f1802i;
        if (vVar != null && vVar.b()) {
            this.f1802i.g(this.V);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeHorizontalScrollRange() {
        v vVar = this.f1802i;
        if (vVar != null && vVar.b()) {
            return this.f1802i.h(this.V);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeVerticalScrollExtent() {
        v vVar = this.f1802i;
        if (vVar != null && vVar.c()) {
            return this.f1802i.i(this.V);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeVerticalScrollOffset() {
        v vVar = this.f1802i;
        if (vVar != null && vVar.c()) {
            this.f1802i.j(this.V);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeVerticalScrollRange() {
        v vVar = this.f1802i;
        if (vVar != null && vVar.c()) {
            return this.f1802i.k(this.V);
        }
        return 0;
    }

    public final void d(int i2, int i3) {
        boolean zIsFinished;
        EdgeEffect edgeEffect = this.f1765A;
        if (edgeEffect == null || edgeEffect.isFinished() || i2 <= 0) {
            zIsFinished = false;
        } else {
            this.f1765A.onRelease();
            zIsFinished = this.f1765A.isFinished();
        }
        EdgeEffect edgeEffect2 = this.f1767C;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && i2 < 0) {
            this.f1767C.onRelease();
            zIsFinished |= this.f1767C.isFinished();
        }
        EdgeEffect edgeEffect3 = this.f1766B;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && i3 > 0) {
            this.f1766B.onRelease();
            zIsFinished |= this.f1766B.isFinished();
        }
        EdgeEffect edgeEffect4 = this.f1768D;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && i3 < 0) {
            this.f1768D.onRelease();
            zIsFinished |= this.f1768D.isFinished();
        }
        if (zIsFinished) {
            WeakHashMap weakHashMap = v.l.f2836a;
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.View
    public final boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return getScrollingChildHelper().a(f2, f3, z2);
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreFling(float f2, float f3) {
        return getScrollingChildHelper().b(f2, f3);
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().c(i2, i3, iArr, iArr2, 0);
    }

    @Override // android.view.View
    public final boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return getScrollingChildHelper().d(i2, i3, i4, i5, iArr, 0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchRestoreInstanceState(SparseArray sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchSaveInstanceState(SparseArray sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        boolean z2;
        super.draw(canvas);
        ArrayList arrayList = this.f1803j;
        int size = arrayList.size();
        boolean z3 = false;
        for (int i2 = 0; i2 < size; i2++) {
            C0112e c0112e = (C0112e) arrayList.get(i2);
            if (c0112e.f1872l != c0112e.f1874n.getWidth() || c0112e.f1873m != c0112e.f1874n.getHeight()) {
                c0112e.f1872l = c0112e.f1874n.getWidth();
                c0112e.f1873m = c0112e.f1874n.getHeight();
                c0112e.e(0);
            } else if (c0112e.f1881v != 0) {
                if (c0112e.f1875o) {
                    int i3 = c0112e.f1872l;
                    int i4 = c0112e.f1864d;
                    int i5 = i3 - i4;
                    c0112e.getClass();
                    c0112e.getClass();
                    int i6 = 0 - (0 / 2);
                    StateListDrawable stateListDrawable = c0112e.f1862b;
                    stateListDrawable.setBounds(0, 0, i4, 0);
                    int i7 = c0112e.f1873m;
                    Drawable drawable = c0112e.f1863c;
                    drawable.setBounds(0, 0, c0112e.f1865e, i7);
                    RecyclerView recyclerView = c0112e.f1874n;
                    WeakHashMap weakHashMap = v.l.f2836a;
                    if (recyclerView.getLayoutDirection() == 1) {
                        drawable.draw(canvas);
                        canvas.translate(i4, i6);
                        canvas.scale(-1.0f, 1.0f);
                        stateListDrawable.draw(canvas);
                        canvas.scale(1.0f, 1.0f);
                        canvas.translate(-i4, -i6);
                    } else {
                        canvas.translate(i5, 0.0f);
                        drawable.draw(canvas);
                        canvas.translate(0.0f, i6);
                        stateListDrawable.draw(canvas);
                        canvas.translate(-i5, -i6);
                    }
                }
                if (c0112e.f1876p) {
                    int i8 = c0112e.f1873m;
                    int i9 = c0112e.f1868h;
                    int i10 = i8 - i9;
                    c0112e.getClass();
                    c0112e.getClass();
                    StateListDrawable stateListDrawable2 = c0112e.f1866f;
                    stateListDrawable2.setBounds(0, 0, 0, i9);
                    int i11 = c0112e.f1872l;
                    Drawable drawable2 = c0112e.f1867g;
                    drawable2.setBounds(0, 0, i11, c0112e.f1869i);
                    canvas.translate(0.0f, i10);
                    drawable2.draw(canvas);
                    canvas.translate(0 - (0 / 2), 0.0f);
                    stateListDrawable2.draw(canvas);
                    canvas.translate(-r9, -i10);
                }
            }
        }
        EdgeEffect edgeEffect = this.f1765A;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z2 = false;
        } else {
            int iSave = canvas.save();
            int paddingBottom = this.f1797f ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((-getHeight()) + paddingBottom, 0.0f);
            EdgeEffect edgeEffect2 = this.f1765A;
            z2 = edgeEffect2 != null && edgeEffect2.draw(canvas);
            canvas.restoreToCount(iSave);
        }
        EdgeEffect edgeEffect3 = this.f1766B;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int iSave2 = canvas.save();
            if (this.f1797f) {
                canvas.translate(getPaddingLeft(), getPaddingTop());
            }
            EdgeEffect edgeEffect4 = this.f1766B;
            z2 |= edgeEffect4 != null && edgeEffect4.draw(canvas);
            canvas.restoreToCount(iSave2);
        }
        EdgeEffect edgeEffect5 = this.f1767C;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int iSave3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.f1797f ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate(-paddingTop, -width);
            EdgeEffect edgeEffect6 = this.f1767C;
            z2 |= edgeEffect6 != null && edgeEffect6.draw(canvas);
            canvas.restoreToCount(iSave3);
        }
        EdgeEffect edgeEffect7 = this.f1768D;
        if (edgeEffect7 != null && !edgeEffect7.isFinished()) {
            int iSave4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.f1797f) {
                canvas.translate(getPaddingRight() + (-getWidth()), getPaddingBottom() + (-getHeight()));
            } else {
                canvas.translate(-getWidth(), -getHeight());
            }
            EdgeEffect edgeEffect8 = this.f1768D;
            if (edgeEffect8 != null && edgeEffect8.draw(canvas)) {
                z3 = true;
            }
            z2 |= z3;
            canvas.restoreToCount(iSave4);
        }
        if ((z2 || this.f1769E == null || arrayList.size() <= 0 || !this.f1769E.c()) ? z2 : true) {
            WeakHashMap weakHashMap2 = v.l.f2836a;
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j2) {
        return super.drawChild(canvas, view, j2);
    }

    public final void e() {
        if (!this.f1808o || this.f1814v) {
            Trace.beginSection("RV FullInvalidate");
            Log.e("RecyclerView", "No adapter attached; skipping layout");
            Trace.endSection();
        } else if (((ArrayList) this.f1791c.f2538c).size() > 0) {
            this.f1791c.getClass();
            if (((ArrayList) this.f1791c.f2538c).size() > 0) {
                Trace.beginSection("RV FullInvalidate");
                Log.e("RecyclerView", "No adapter attached; skipping layout");
                Trace.endSection();
            }
        }
    }

    @Override // v.e
    public final void f(int i2) {
        getScrollingChildHelper().h(i2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final View focusSearch(View view, int i2) {
        int i3;
        this.f1802i.getClass();
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, view, i2);
        if (viewFindNextFocus != null && !viewFindNextFocus.hasFocusable()) {
            if (getFocusedChild() == null) {
                return super.focusSearch(view, i2);
            }
            s(viewFindNextFocus, null);
            return view;
        }
        if (viewFindNextFocus != null && viewFindNextFocus != this && k(viewFindNextFocus) != null) {
            if (view == null || k(view) == null) {
                return viewFindNextFocus;
            }
            int width = view.getWidth();
            int height = view.getHeight();
            Rect rect = this.f1799g;
            byte b2 = 0;
            rect.set(0, 0, width, height);
            int width2 = viewFindNextFocus.getWidth();
            int height2 = viewFindNextFocus.getHeight();
            Rect rect2 = this.f1801h;
            rect2.set(0, 0, width2, height2);
            offsetDescendantRectToMyCoords(view, rect);
            offsetDescendantRectToMyCoords(viewFindNextFocus, rect2);
            RecyclerView recyclerView = this.f1802i.f1913b;
            WeakHashMap weakHashMap = v.l.f2836a;
            int i4 = recyclerView.getLayoutDirection() == 1 ? -1 : 1;
            int i5 = rect.left;
            int i6 = rect2.left;
            if ((i5 < i6 || rect.right <= i6) && rect.right < rect2.right) {
                i3 = 1;
            } else {
                int i7 = rect.right;
                int i8 = rect2.right;
                i3 = ((i7 > i8 || i5 >= i8) && i5 > i6) ? -1 : 0;
            }
            int i9 = rect.top;
            int i10 = rect2.top;
            if ((i9 < i10 || rect.bottom <= i10) && rect.bottom < rect2.bottom) {
                b2 = 1;
            } else {
                int i11 = rect.bottom;
                int i12 = rect2.bottom;
                if ((i11 > i12 || i9 >= i12) && i9 > i10) {
                    b2 = -1;
                }
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 != 17) {
                        if (i2 != 33) {
                            if (i2 != 66) {
                                if (i2 != 130) {
                                    throw new IllegalArgumentException("Invalid direction: " + i2 + j());
                                }
                                if (b2 > 0) {
                                    return viewFindNextFocus;
                                }
                            } else if (i3 > 0) {
                                return viewFindNextFocus;
                            }
                        } else if (b2 < 0) {
                            return viewFindNextFocus;
                        }
                    } else if (i3 < 0) {
                        return viewFindNextFocus;
                    }
                } else {
                    if (b2 > 0) {
                        return viewFindNextFocus;
                    }
                    if (b2 == 0 && i3 * i4 >= 0) {
                        return viewFindNextFocus;
                    }
                }
            } else {
                if (b2 < 0) {
                    return viewFindNextFocus;
                }
                if (b2 == 0 && i3 * i4 <= 0) {
                    return viewFindNextFocus;
                }
            }
        }
        return super.focusSearch(view, i2);
    }

    public final void g(int i2, int i3) {
        int paddingRight = getPaddingRight() + getPaddingLeft();
        WeakHashMap weakHashMap = v.l.f2836a;
        setMeasuredDimension(v.e(i2, paddingRight, getMinimumWidth()), v.e(i3, getPaddingBottom() + getPaddingTop(), getMinimumHeight()));
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        v vVar = this.f1802i;
        if (vVar != null) {
            return vVar.l();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + j());
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        v vVar = this.f1802i;
        if (vVar != null) {
            return vVar.m(getContext(), attributeSet);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + j());
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        v vVar = this.f1802i;
        if (vVar != null) {
            return vVar.n(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + j());
    }

    public q getAdapter() {
        return null;
    }

    @Override // android.view.View
    public int getBaseline() {
        v vVar = this.f1802i;
        if (vVar == null) {
            return super.getBaseline();
        }
        vVar.getClass();
        return -1;
    }

    @Override // android.view.ViewGroup
    public final int getChildDrawingOrder(int i2, int i3) {
        return super.getChildDrawingOrder(i2, i3);
    }

    @Override // android.view.ViewGroup
    public boolean getClipToPadding() {
        return this.f1797f;
    }

    public H getCompatAccessibilityDelegate() {
        return this.f1790b0;
    }

    public s getEdgeEffectFactory() {
        return this.f1818z;
    }

    public t getItemAnimator() {
        return this.f1769E;
    }

    public int getItemDecorationCount() {
        return this.f1803j.size();
    }

    public v getLayoutManager() {
        return this.f1802i;
    }

    public int getMaxFlingVelocity() {
        return this.f1779O;
    }

    public int getMinFlingVelocity() {
        return this.f1778N;
    }

    public long getNanoTime() {
        return System.nanoTime();
    }

    public x getOnFlingListener() {
        return null;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.f1782R;
    }

    public A getRecycledViewPool() {
        B b2 = this.f1787a;
        if (b2.f1723e == null) {
            A a2 = new A();
            a2.f1717a = new SparseArray();
            a2.f1718b = 0;
            b2.f1723e = a2;
        }
        return b2.f1723e;
    }

    public int getScrollState() {
        return this.f1770F;
    }

    public final boolean h(int i2, int i3, int[] iArr, int[] iArr2, int i4) {
        return getScrollingChildHelper().c(i2, i3, iArr, iArr2, i4);
    }

    @Override // android.view.View
    public final boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().f(0);
    }

    public final boolean i(int i2, int i3, int[] iArr, int i4) {
        return getScrollingChildHelper().d(0, 0, 0, 0, iArr, i4);
    }

    @Override // android.view.View
    public final boolean isAttachedToWindow() {
        return this.f1806m;
    }

    @Override // android.view.View
    public final boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().f2832d;
    }

    public final String j() {
        return " " + super.toString() + ", adapter:null, layout:" + this.f1802i + ", context:" + getContext();
    }

    public final View k(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view = parent;
            parent = view.getParent();
        }
        if (parent == this) {
            return view;
        }
        return null;
    }

    public final boolean m() {
        return getScrollingChildHelper().f(1);
    }

    public final boolean n() {
        return !this.f1808o || this.f1814v || ((ArrayList) this.f1791c.f2538c).size() > 0;
    }

    public final void o() {
        int iK = this.f1793d.k();
        for (int i2 = 0; i2 < iK; i2++) {
            ((w) this.f1793d.j(i2).getLayoutParams()).f1920b = true;
        }
        ArrayList arrayList = this.f1787a.f1720b;
        if (arrayList.size() <= 0) {
            return;
        }
        w0.c(arrayList.get(0));
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0056  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onAttachedToWindow() {
        /*
            r5 = this;
            super.onAttachedToWindow()
            r0 = 0
            r5.f1816x = r0
            r1 = 1
            r5.f1806m = r1
            boolean r2 = r5.f1808o
            if (r2 == 0) goto L14
            boolean r2 = r5.isLayoutRequested()
            if (r2 != 0) goto L14
            r0 = r1
        L14:
            r5.f1808o = r0
            androidx.recyclerview.widget.v r0 = r5.f1802i
            if (r0 == 0) goto L1c
            r0.f1916e = r1
        L1c:
            java.lang.ThreadLocal r0 = androidx.recyclerview.widget.RunnableC0116i.f1891e
            java.lang.Object r1 = r0.get()
            androidx.recyclerview.widget.i r1 = (androidx.recyclerview.widget.RunnableC0116i) r1
            r5.f1784T = r1
            if (r1 != 0) goto L64
            androidx.recyclerview.widget.i r1 = new androidx.recyclerview.widget.i
            r1.<init>()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r1.f1893a = r2
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r1.f1896d = r2
            r5.f1784T = r1
            java.util.WeakHashMap r1 = v.l.f2836a
            android.view.Display r1 = r5.getDisplay()
            boolean r2 = r5.isInEditMode()
            if (r2 != 0) goto L56
            if (r1 == 0) goto L56
            float r1 = r1.getRefreshRate()
            r2 = 1106247680(0x41f00000, float:30.0)
            int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r2 < 0) goto L56
            goto L58
        L56:
            r1 = 1114636288(0x42700000, float:60.0)
        L58:
            androidx.recyclerview.widget.i r2 = r5.f1784T
            r3 = 1315859240(0x4e6e6b28, float:1.0E9)
            float r3 = r3 / r1
            long r3 = (long) r3
            r2.f1895c = r3
            r0.set(r2)
        L64:
            androidx.recyclerview.widget.i r0 = r5.f1784T
            java.util.ArrayList r0 = r0.f1893a
            r0.add(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onAttachedToWindow():void");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        t tVar = this.f1769E;
        if (tVar != null) {
            tVar.b();
        }
        setScrollState(0);
        G g2 = this.f1783S;
        g2.f1737g.removeCallbacks(g2);
        g2.f1733c.abortAnimation();
        this.f1806m = false;
        v vVar = this.f1802i;
        if (vVar != null) {
            vVar.f1916e = false;
            vVar.A(this);
        }
        this.f1800g0.clear();
        removeCallbacks(this.h0);
        this.f1795e.getClass();
        while (N.f1762a.a() != null) {
        }
        RunnableC0116i runnableC0116i = this.f1784T;
        if (runnableC0116i != null) {
            runnableC0116i.f1893a.remove(this);
            this.f1784T = null;
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ArrayList arrayList = this.f1803j;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((C0112e) arrayList.get(i2)).getClass();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x006c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onGenericMotionEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            androidx.recyclerview.widget.v r0 = r5.f1802i
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            boolean r0 = r5.r
            if (r0 == 0) goto Lb
            return r1
        Lb:
            int r0 = r6.getAction()
            r2 = 8
            if (r0 != r2) goto L77
            int r0 = r6.getSource()
            r0 = r0 & 2
            r2 = 0
            if (r0 == 0) goto L3e
            androidx.recyclerview.widget.v r0 = r5.f1802i
            boolean r0 = r0.c()
            if (r0 == 0) goto L2c
            r0 = 9
            float r0 = r6.getAxisValue(r0)
            float r0 = -r0
            goto L2d
        L2c:
            r0 = r2
        L2d:
            androidx.recyclerview.widget.v r3 = r5.f1802i
            boolean r3 = r3.b()
            if (r3 == 0) goto L3c
            r3 = 10
            float r3 = r6.getAxisValue(r3)
            goto L64
        L3c:
            r3 = r2
            goto L64
        L3e:
            int r0 = r6.getSource()
            r3 = 4194304(0x400000, float:5.877472E-39)
            r0 = r0 & r3
            if (r0 == 0) goto L62
            r0 = 26
            float r0 = r6.getAxisValue(r0)
            androidx.recyclerview.widget.v r3 = r5.f1802i
            boolean r3 = r3.c()
            if (r3 == 0) goto L57
            float r0 = -r0
            goto L3c
        L57:
            androidx.recyclerview.widget.v r3 = r5.f1802i
            boolean r3 = r3.b()
            if (r3 == 0) goto L62
            r3 = r0
            r0 = r2
            goto L64
        L62:
            r0 = r2
            r3 = r0
        L64:
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L6c
            int r2 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r2 == 0) goto L77
        L6c:
            float r2 = r5.f1780P
            float r3 = r3 * r2
            int r2 = (int) r3
            float r3 = r5.f1781Q
            float r0 = r0 * r3
            int r0 = (int) r0
            r5.u(r2, r0, r6)
        L77:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onGenericMotionEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        if (this.r) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 3 || action == 0) {
            this.f1805l = null;
        }
        ArrayList arrayList = this.f1804k;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            C0112e c0112e = (C0112e) arrayList.get(i2);
            if (c0112e.c(motionEvent) && action != 3) {
                this.f1805l = c0112e;
                t();
                setScrollState(0);
                return true;
            }
        }
        v vVar = this.f1802i;
        if (vVar == null) {
            return false;
        }
        boolean zB = vVar.b();
        boolean zC = this.f1802i.c();
        if (this.f1772H == null) {
            this.f1772H = VelocityTracker.obtain();
        }
        this.f1772H.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            if (this.f1811s) {
                this.f1811s = false;
            }
            this.f1771G = motionEvent.getPointerId(0);
            int x2 = (int) (motionEvent.getX() + 0.5f);
            this.f1775K = x2;
            this.f1773I = x2;
            int y2 = (int) (motionEvent.getY() + 0.5f);
            this.f1776L = y2;
            this.f1774J = y2;
            if (this.f1770F == 2) {
                getParent().requestDisallowInterceptTouchEvent(true);
                setScrollState(1);
            }
            int[] iArr = this.f1798f0;
            iArr[1] = 0;
            iArr[0] = 0;
            int i3 = zB;
            if (zC) {
                i3 = (zB ? 1 : 0) | 2;
            }
            getScrollingChildHelper().g(i3, 0);
        } else if (actionMasked == 1) {
            this.f1772H.clear();
            f(0);
        } else if (actionMasked == 2) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.f1771G);
            if (iFindPointerIndex < 0) {
                Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.f1771G + " not found. Did any MotionEvents get skipped?");
                return false;
            }
            int x3 = (int) (motionEvent.getX(iFindPointerIndex) + 0.5f);
            int y3 = (int) (motionEvent.getY(iFindPointerIndex) + 0.5f);
            if (this.f1770F != 1) {
                int i4 = x3 - this.f1773I;
                int i5 = y3 - this.f1774J;
                if (!zB || Math.abs(i4) <= this.f1777M) {
                    z2 = false;
                } else {
                    this.f1775K = x3;
                    z2 = true;
                }
                if (zC && Math.abs(i5) > this.f1777M) {
                    this.f1776L = y3;
                    z2 = true;
                }
                if (z2) {
                    setScrollState(1);
                }
            }
        } else if (actionMasked == 3) {
            t();
            setScrollState(0);
        } else if (actionMasked == 5) {
            this.f1771G = motionEvent.getPointerId(actionIndex);
            int x4 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.f1775K = x4;
            this.f1773I = x4;
            int y4 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.f1776L = y4;
            this.f1774J = y4;
        } else if (actionMasked == 6) {
            r(motionEvent);
        }
        return this.f1770F == 1;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        Trace.beginSection("RV OnLayout");
        Log.e("RecyclerView", "No adapter attached; skipping layout");
        Trace.endSection();
        this.f1808o = true;
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        v vVar = this.f1802i;
        if (vVar == null) {
            g(i2, i3);
            return;
        }
        if (vVar.z()) {
            View.MeasureSpec.getMode(i2);
            View.MeasureSpec.getMode(i3);
            this.f1802i.f1913b.g(i2, i3);
        } else {
            if (this.f1807n) {
                this.f1802i.f1913b.g(i2, i3);
                return;
            }
            E e2 = this.V;
            if (e2.f1730f) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                return;
            }
            e2.getClass();
            w();
            this.f1802i.f1913b.g(i2, i3);
            x(false);
            e2.f1728d = false;
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i2, Rect rect) {
        if (this.f1816x > 0) {
            return false;
        }
        return super.onRequestFocusInDescendants(i2, rect);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.f1789b = savedState;
        super.onRestoreInstanceState(savedState.f1465a);
        v vVar = this.f1802i;
        if (vVar == null || (parcelable2 = this.f1789b.f1819c) == null) {
            return;
        }
        vVar.C(parcelable2);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState savedState2 = this.f1789b;
        if (savedState2 != null) {
            savedState.f1819c = savedState2.f1819c;
        } else {
            v vVar = this.f1802i;
            if (vVar != null) {
                savedState.f1819c = vVar.D();
            } else {
                savedState.f1819c = null;
            }
        }
        return savedState;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 == i4 && i3 == i5) {
            return;
        }
        this.f1768D = null;
        this.f1766B = null;
        this.f1767C = null;
        this.f1765A = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0152 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0153  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onTouchEvent(android.view.MotionEvent r22) {
        /*
            Method dump skipped, instruction units count: 875
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void p() {
        this.f1816x++;
    }

    public final void q(boolean z2) {
        AccessibilityManager accessibilityManager;
        int i2 = this.f1816x - 1;
        this.f1816x = i2;
        if (i2 < 1) {
            this.f1816x = 0;
            if (z2) {
                int i3 = this.f1812t;
                this.f1812t = 0;
                if (i3 != 0 && (accessibilityManager = this.f1813u) != null && accessibilityManager.isEnabled()) {
                    AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
                    accessibilityEventObtain.setEventType(2048);
                    accessibilityEventObtain.setContentChangeTypes(i3);
                    sendAccessibilityEventUnchecked(accessibilityEventObtain);
                }
                ArrayList arrayList = this.f1800g0;
                int size = arrayList.size() - 1;
                if (size < 0) {
                    arrayList.clear();
                } else {
                    w0.c(arrayList.get(size));
                    throw null;
                }
            }
        }
    }

    public final void r(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.f1771G) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.f1771G = motionEvent.getPointerId(i2);
            int x2 = (int) (motionEvent.getX(i2) + 0.5f);
            this.f1775K = x2;
            this.f1773I = x2;
            int y2 = (int) (motionEvent.getY(i2) + 0.5f);
            this.f1776L = y2;
            this.f1774J = y2;
        }
    }

    @Override // android.view.ViewGroup
    public final void removeDetachedView(View view, boolean z2) {
        l(view);
        view.clearAnimation();
        l(view);
        super.removeDetachedView(view, z2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        this.f1802i.getClass();
        if (this.f1816x <= 0 && view2 != null) {
            s(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
        return this.f1802i.H(this, view, rect, z2, false);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z2) {
        ArrayList arrayList = this.f1804k;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((C0112e) arrayList.get(i2)).getClass();
        }
        super.requestDisallowInterceptTouchEvent(z2);
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        if (this.f1809p != 0 || this.r) {
            this.f1810q = true;
        } else {
            super.requestLayout();
        }
    }

    public final void s(View view, View view2) {
        View view3 = view2 != null ? view2 : view;
        int width = view3.getWidth();
        int height = view3.getHeight();
        Rect rect = this.f1799g;
        rect.set(0, 0, width, height);
        ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof w) {
            w wVar = (w) layoutParams;
            if (!wVar.f1920b) {
                int i2 = rect.left;
                Rect rect2 = wVar.f1919a;
                rect.left = i2 - rect2.left;
                rect.right += rect2.right;
                rect.top -= rect2.top;
                rect.bottom += rect2.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, rect);
            offsetRectIntoDescendantCoords(view, rect);
        }
        this.f1802i.H(this, view, this.f1799g, !this.f1808o, view2 == null);
    }

    @Override // android.view.View
    public final void scrollBy(int i2, int i3) {
        v vVar = this.f1802i;
        if (vVar == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.r) {
            return;
        }
        boolean zB = vVar.b();
        boolean zC = this.f1802i.c();
        if (zB || zC) {
            if (!zB) {
                i2 = 0;
            }
            if (!zC) {
                i3 = 0;
            }
            u(i2, i3, null);
        }
    }

    @Override // android.view.View
    public final void scrollTo(int i2, int i3) {
        Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public final void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (this.f1816x <= 0) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        } else {
            int contentChangeTypes = accessibilityEvent != null ? accessibilityEvent.getContentChangeTypes() : 0;
            this.f1812t |= contentChangeTypes != 0 ? contentChangeTypes : 0;
        }
    }

    public void setAccessibilityDelegateCompat(H h2) {
        this.f1790b0 = h2;
        v.l.b(this, h2);
    }

    public void setAdapter(q qVar) {
        setLayoutFrozen(false);
        t tVar = this.f1769E;
        if (tVar != null) {
            tVar.b();
        }
        v vVar = this.f1802i;
        B b2 = this.f1787a;
        if (vVar != null) {
            vVar.F();
            this.f1802i.G(b2);
        }
        b2.f1719a.clear();
        ArrayList arrayList = b2.f1720b;
        int size = arrayList.size() - 1;
        if (size >= 0) {
            w0.c(arrayList.get(size));
            throw null;
        }
        arrayList.clear();
        C0114g c0114g = b2.f1724f.f1785U;
        c0114g.getClass();
        c0114g.f1885c = 0;
        f0.b bVar = this.f1791c;
        bVar.o((ArrayList) bVar.f2538c);
        bVar.o((ArrayList) bVar.f2539d);
        b2.f1719a.clear();
        ArrayList arrayList2 = b2.f1720b;
        int size2 = arrayList2.size() - 1;
        if (size2 >= 0) {
            w0.c(arrayList2.get(size2));
            throw null;
        }
        arrayList2.clear();
        RecyclerView recyclerView = b2.f1724f;
        C0114g c0114g2 = recyclerView.f1785U;
        c0114g2.getClass();
        c0114g2.f1885c = 0;
        if (b2.f1723e == null) {
            A a2 = new A();
            a2.f1717a = new SparseArray();
            a2.f1718b = 0;
            b2.f1723e = a2;
        }
        A a3 = b2.f1723e;
        if (a3.f1718b == 0) {
            SparseArray sparseArray = a3.f1717a;
            if (sparseArray.size() > 0) {
                ((z) sparseArray.valueAt(0)).getClass();
                throw null;
            }
        }
        this.V.f1727c = true;
        this.f1815w = this.f1815w;
        this.f1814v = true;
        int iK = this.f1793d.k();
        for (int i2 = 0; i2 < iK; i2++) {
            l(this.f1793d.j(i2));
        }
        o();
        int size3 = arrayList2.size();
        for (int i3 = 0; i3 < size3; i3++) {
            w0.c(arrayList2.get(i3));
        }
        recyclerView.getClass();
        int size4 = arrayList2.size() - 1;
        if (size4 >= 0) {
            w0.c(arrayList2.get(size4));
            throw null;
        }
        arrayList2.clear();
        C0114g c0114g3 = recyclerView.f1785U;
        c0114g3.getClass();
        c0114g3.f1885c = 0;
        requestLayout();
    }

    public void setChildDrawingOrderCallback(r rVar) {
        if (rVar == null) {
            return;
        }
        setChildrenDrawingOrderEnabled(false);
    }

    @Override // android.view.ViewGroup
    public void setClipToPadding(boolean z2) {
        if (z2 != this.f1797f) {
            this.f1768D = null;
            this.f1766B = null;
            this.f1767C = null;
            this.f1765A = null;
        }
        this.f1797f = z2;
        super.setClipToPadding(z2);
        if (this.f1808o) {
            requestLayout();
        }
    }

    public void setEdgeEffectFactory(s sVar) {
        sVar.getClass();
        this.f1818z = sVar;
        this.f1768D = null;
        this.f1766B = null;
        this.f1767C = null;
        this.f1765A = null;
    }

    public void setHasFixedSize(boolean z2) {
        this.f1807n = z2;
    }

    public void setItemAnimator(t tVar) {
        t tVar2 = this.f1769E;
        if (tVar2 != null) {
            tVar2.b();
            this.f1769E.f1906a = null;
        }
        this.f1769E = tVar;
        if (tVar != null) {
            tVar.f1906a = this.f1788a0;
        }
    }

    public void setItemViewCacheSize(int i2) {
        B b2 = this.f1787a;
        b2.f1721c = i2;
        b2.c();
    }

    public void setLayoutFrozen(boolean z2) {
        if (z2 != this.r) {
            c("Do not setLayoutFrozen in layout or scroll");
            if (!z2) {
                this.r = false;
                this.f1810q = false;
                return;
            }
            long jUptimeMillis = SystemClock.uptimeMillis();
            onTouchEvent(MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0));
            this.r = true;
            this.f1811s = true;
            setScrollState(0);
            G g2 = this.f1783S;
            g2.f1737g.removeCallbacks(g2);
            g2.f1733c.abortAnimation();
        }
    }

    public void setLayoutManager(v vVar) {
        e0.k kVar;
        if (vVar == this.f1802i) {
            return;
        }
        setScrollState(0);
        G g2 = this.f1783S;
        g2.f1737g.removeCallbacks(g2);
        g2.f1733c.abortAnimation();
        v vVar2 = this.f1802i;
        B b2 = this.f1787a;
        if (vVar2 != null) {
            t tVar = this.f1769E;
            if (tVar != null) {
                tVar.b();
            }
            this.f1802i.F();
            this.f1802i.G(b2);
            b2.f1719a.clear();
            ArrayList arrayList = b2.f1720b;
            int size = arrayList.size() - 1;
            if (size >= 0) {
                w0.c(arrayList.get(size));
                throw null;
            }
            arrayList.clear();
            C0114g c0114g = b2.f1724f.f1785U;
            c0114g.getClass();
            c0114g.f1885c = 0;
            if (this.f1806m) {
                v vVar3 = this.f1802i;
                vVar3.f1916e = false;
                vVar3.A(this);
            }
            this.f1802i.J(null);
            this.f1802i = null;
        } else {
            b2.f1719a.clear();
            ArrayList arrayList2 = b2.f1720b;
            int size2 = arrayList2.size() - 1;
            if (size2 >= 0) {
                w0.c(arrayList2.get(size2));
                throw null;
            }
            arrayList2.clear();
            C0114g c0114g2 = b2.f1724f.f1785U;
            c0114g2.getClass();
            c0114g2.f1885c = 0;
        }
        f0.b bVar = this.f1793d;
        ((C0109b) bVar.f2538c).d();
        ArrayList arrayList3 = (ArrayList) bVar.f2539d;
        int size3 = arrayList3.size() - 1;
        while (true) {
            kVar = (e0.k) bVar.f2537b;
            if (size3 < 0) {
                break;
            }
            View view = (View) arrayList3.get(size3);
            kVar.getClass();
            l(view);
            arrayList3.remove(size3);
            size3--;
        }
        RecyclerView recyclerView = (RecyclerView) kVar.f2495a;
        int childCount = recyclerView.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            l(childAt);
            childAt.clearAnimation();
        }
        recyclerView.removeAllViews();
        this.f1802i = vVar;
        if (vVar != null) {
            if (vVar.f1913b != null) {
                throw new IllegalArgumentException("LayoutManager " + vVar + " is already attached to a RecyclerView:" + vVar.f1913b.j());
            }
            vVar.J(this);
            if (this.f1806m) {
                this.f1802i.f1916e = true;
            }
        }
        b2.c();
        requestLayout();
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z2) {
        v.g scrollingChildHelper = getScrollingChildHelper();
        if (scrollingChildHelper.f2832d) {
            WeakHashMap weakHashMap = v.l.f2836a;
            scrollingChildHelper.f2831c.stopNestedScroll();
        }
        scrollingChildHelper.f2832d = z2;
    }

    public void setOnFlingListener(x xVar) {
    }

    @Deprecated
    public void setOnScrollListener(y yVar) {
    }

    public void setPreserveFocusAfterLayout(boolean z2) {
        this.f1782R = z2;
    }

    public void setRecycledViewPool(A a2) {
        B b2 = this.f1787a;
        if (b2.f1723e != null) {
            r0.f1718b--;
        }
        b2.f1723e = a2;
        if (a2 != null) {
            b2.f1724f.getAdapter();
        }
    }

    public void setRecyclerListener(C c2) {
    }

    public void setScrollState(int i2) {
        if (i2 == this.f1770F) {
            return;
        }
        this.f1770F = i2;
        if (i2 != 2) {
            G g2 = this.f1783S;
            g2.f1737g.removeCallbacks(g2);
            g2.f1733c.abortAnimation();
        }
        v vVar = this.f1802i;
        if (vVar != null) {
            vVar.E(i2);
        }
        ArrayList arrayList = this.f1786W;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((y) this.f1786W.get(size)).getClass();
            }
        }
    }

    public void setScrollingTouchSlop(int i2) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        if (i2 != 0) {
            if (i2 == 1) {
                this.f1777M = viewConfiguration.getScaledPagingTouchSlop();
                return;
            }
            Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + i2 + "; using default value");
        }
        this.f1777M = viewConfiguration.getScaledTouchSlop();
    }

    public void setViewCacheExtension(F f2) {
        this.f1787a.getClass();
    }

    @Override // android.view.View
    public final boolean startNestedScroll(int i2) {
        return getScrollingChildHelper().g(i2, 0);
    }

    @Override // android.view.View
    public final void stopNestedScroll() {
        getScrollingChildHelper().h(0);
    }

    public final void t() {
        VelocityTracker velocityTracker = this.f1772H;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        boolean zIsFinished = false;
        f(0);
        EdgeEffect edgeEffect = this.f1765A;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            zIsFinished = this.f1765A.isFinished();
        }
        EdgeEffect edgeEffect2 = this.f1766B;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            zIsFinished |= this.f1766B.isFinished();
        }
        EdgeEffect edgeEffect3 = this.f1767C;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            zIsFinished |= this.f1767C.isFinished();
        }
        EdgeEffect edgeEffect4 = this.f1768D;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            zIsFinished |= this.f1768D.isFinished();
        }
        if (zIsFinished) {
            WeakHashMap weakHashMap = v.l.f2836a;
            postInvalidateOnAnimation();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void u(int r11, int r12, android.view.MotionEvent r13) {
        /*
            Method dump skipped, instruction units count: 488
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.u(int, int, android.view.MotionEvent):void");
    }

    public final void v(int i2, int i3) {
        int iRound;
        v vVar = this.f1802i;
        if (vVar == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.r) {
            return;
        }
        int i4 = !vVar.b() ? 0 : i2;
        int i5 = !this.f1802i.c() ? 0 : i3;
        if (i4 == 0 && i5 == 0) {
            return;
        }
        G g2 = this.f1783S;
        g2.getClass();
        int iAbs = Math.abs(i4);
        int iAbs2 = Math.abs(i5);
        boolean z2 = iAbs > iAbs2;
        int iSqrt = (int) Math.sqrt(0);
        int iSqrt2 = (int) Math.sqrt((i5 * i5) + (i4 * i4));
        RecyclerView recyclerView = g2.f1737g;
        int width = z2 ? recyclerView.getWidth() : recyclerView.getHeight();
        int i6 = width / 2;
        float f2 = width;
        float f3 = i6;
        float fSin = (((float) Math.sin((Math.min(1.0f, (iSqrt2 * 1.0f) / f2) - 0.5f) * 0.47123894f)) * f3) + f3;
        if (iSqrt > 0) {
            iRound = Math.round(Math.abs(fSin / iSqrt) * 1000.0f) * 4;
        } else {
            if (!z2) {
                iAbs = iAbs2;
            }
            iRound = (int) (((iAbs / f2) + 1.0f) * 300.0f);
        }
        int iMin = Math.min(iRound, 2000);
        p pVar = l0;
        if (g2.f1734d != pVar) {
            g2.f1734d = pVar;
            g2.f1733c = new OverScroller(recyclerView.getContext(), pVar);
        }
        recyclerView.setScrollState(2);
        g2.f1732b = 0;
        g2.f1731a = 0;
        g2.f1733c.startScroll(0, 0, i4, i5, iMin);
        g2.a();
    }

    public final void w() {
        int i2 = this.f1809p + 1;
        this.f1809p = i2;
        if (i2 != 1 || this.r) {
            return;
        }
        this.f1810q = false;
    }

    public final void x(boolean z2) {
        if (this.f1809p < 1) {
            this.f1809p = 1;
        }
        if (!z2 && !this.r) {
            this.f1810q = false;
        }
        int i2 = this.f1809p;
        if (i2 == 1) {
            if (z2) {
                boolean z3 = this.f1810q;
            }
            if (!this.r) {
                this.f1810q = false;
            }
        }
        this.f1809p = i2 - 1;
    }
}
