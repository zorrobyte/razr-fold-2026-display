package androidx.viewpager.widget;

import F.c;
import F.d;
import F.e;
import F.f;
import F.g;
import F.h;
import F.i;
import F.j;
import F.k;
import F.l;
import X.w0;
import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import androidx.customview.view.AbsSavedState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class ViewPager extends ViewGroup {

    /* JADX INFO: renamed from: a0, reason: collision with root package name */
    public static final int[] f1957a0 = {R.attr.layout_gravity};

    /* JADX INFO: renamed from: b0, reason: collision with root package name */
    public static final c f1958b0 = new c(0);

    /* JADX INFO: renamed from: c0, reason: collision with root package name */
    public static final d f1959c0 = new d(0);

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public int f1960A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public final int f1961B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public float f1962C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public float f1963D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public float f1964E;

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public float f1965F;

    /* JADX INFO: renamed from: G, reason: collision with root package name */
    public int f1966G;

    /* JADX INFO: renamed from: H, reason: collision with root package name */
    public VelocityTracker f1967H;

    /* JADX INFO: renamed from: I, reason: collision with root package name */
    public final int f1968I;

    /* JADX INFO: renamed from: J, reason: collision with root package name */
    public final int f1969J;

    /* JADX INFO: renamed from: K, reason: collision with root package name */
    public final int f1970K;

    /* JADX INFO: renamed from: L, reason: collision with root package name */
    public final int f1971L;

    /* JADX INFO: renamed from: M, reason: collision with root package name */
    public final EdgeEffect f1972M;

    /* JADX INFO: renamed from: N, reason: collision with root package name */
    public final EdgeEffect f1973N;

    /* JADX INFO: renamed from: O, reason: collision with root package name */
    public boolean f1974O;

    /* JADX INFO: renamed from: P, reason: collision with root package name */
    public boolean f1975P;

    /* JADX INFO: renamed from: Q, reason: collision with root package name */
    public int f1976Q;

    /* JADX INFO: renamed from: R, reason: collision with root package name */
    public ArrayList f1977R;

    /* JADX INFO: renamed from: S, reason: collision with root package name */
    public k f1978S;

    /* JADX INFO: renamed from: T, reason: collision with root package name */
    public k f1979T;

    /* JADX INFO: renamed from: U, reason: collision with root package name */
    public ArrayList f1980U;
    public final e V;

    /* JADX INFO: renamed from: W, reason: collision with root package name */
    public int f1981W;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f1982a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final ArrayList f1983b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final h f1984c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final Rect f1985d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public F.a f1986e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1987f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f1988g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public Parcelable f1989h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public ClassLoader f1990i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final Scroller f1991j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f1992k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public l f1993l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f1994m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public Drawable f1995n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public int f1996o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public int f1997p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public float f1998q;
    public float r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public int f1999s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public boolean f2000t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public boolean f2001u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public boolean f2002v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public int f2003w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public boolean f2004x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public boolean f2005y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public final int f2006z;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f2007c;

        /* JADX INFO: renamed from: d, reason: collision with root package name */
        public Parcelable f2008d;

        /* JADX INFO: renamed from: e, reason: collision with root package name */
        public final ClassLoader f2009e;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
            this.f2007c = parcel.readInt();
            this.f2008d = parcel.readParcelable(classLoader);
            this.f2009e = classLoader;
        }

        public final String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.f2007c + "}";
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f2007c);
            parcel.writeParcelable(this.f2008d, i2);
        }
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f1983b = new ArrayList();
        this.f1984c = new h();
        this.f1985d = new Rect();
        this.f1988g = -1;
        this.f1989h = null;
        this.f1990i = null;
        this.f1998q = -3.4028235E38f;
        this.r = Float.MAX_VALUE;
        this.f2003w = 1;
        this.f1966G = -1;
        this.f1974O = true;
        this.V = new e(0, this);
        this.f1981W = 0;
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context2 = getContext();
        this.f1991j = new Scroller(context2, f1959c0);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context2);
        float f2 = context2.getResources().getDisplayMetrics().density;
        this.f1961B = viewConfiguration.getScaledPagingTouchSlop();
        this.f1968I = (int) (400.0f * f2);
        this.f1969J = viewConfiguration.getScaledMaximumFlingVelocity();
        this.f1972M = new EdgeEffect(context2);
        this.f1973N = new EdgeEffect(context2);
        this.f1970K = (int) (25.0f * f2);
        this.f1971L = (int) (2.0f * f2);
        this.f2006z = (int) (f2 * 16.0f);
        v.l.b(this, new j(this, 0));
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        v.l.c(this, new f(this));
    }

    public static boolean d(int i2, int i3, int i4, View view, boolean z2) {
        int i5;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i6 = i3 + scrollX;
                if (i6 >= childAt.getLeft() && i6 < childAt.getRight() && (i5 = i4 + scrollY) >= childAt.getTop() && i5 < childAt.getBottom() && d(i2, i6 - childAt.getLeft(), i5 - childAt.getTop(), childAt, true)) {
                    return true;
                }
            }
        }
        return z2 && view.canScrollHorizontally(-i2);
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private void setScrollingCacheEnabled(boolean z2) {
        if (this.f2001u != z2) {
            this.f2001u = z2;
        }
    }

    public final h a(int i2, int i3) {
        h hVar = new h();
        hVar.f126b = i2;
        hVar.f125a = this.f1986e.h(this, i2);
        hVar.f128d = this.f1986e.g(i2);
        ArrayList arrayList = this.f1983b;
        if (i3 < 0 || i3 >= arrayList.size()) {
            arrayList.add(hVar);
        } else {
            arrayList.add(i3, hVar);
        }
        return hVar;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList arrayList, int i2, int i3) {
        h hVarI;
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                View childAt = getChildAt(i4);
                if (childAt.getVisibility() == 0 && (hVarI = i(childAt)) != null && hVarI.f126b == this.f1987f) {
                    childAt.addFocusables(arrayList, i2, i3);
                }
            }
        }
        if ((descendantFocusability != 262144 || size == arrayList.size()) && isFocusable()) {
            if ((i3 & 1) == 1 && isInTouchMode() && !isFocusableInTouchMode()) {
                return;
            }
            arrayList.add(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addTouchables(ArrayList arrayList) {
        h hVarI;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (hVarI = i(childAt)) != null && hVarI.f126b == this.f1987f) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateDefaultLayoutParams();
        }
        i iVar = (i) layoutParams;
        boolean z2 = iVar.f130a | (view.getClass().getAnnotation(g.class) != null);
        iVar.f130a = z2;
        if (!this.f2000t) {
            super.addView(view, i2, layoutParams);
        } else {
            if (z2) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            iVar.f133d = true;
            addViewInLayout(view, i2, layoutParams);
        }
    }

    public void b(k kVar) {
        if (this.f1977R == null) {
            this.f1977R = new ArrayList();
        }
        this.f1977R.add(kVar);
    }

    public final boolean c(int i2) {
        boolean zRequestFocus;
        View viewFindFocus = findFocus();
        if (viewFindFocus == this) {
            viewFindFocus = null;
        } else if (viewFindFocus != null) {
            for (ViewParent parent = viewFindFocus.getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
                if (parent == this) {
                    break;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(viewFindFocus.getClass().getSimpleName());
            for (ViewParent parent2 = viewFindFocus.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                sb.append(" => ");
                sb.append(parent2.getClass().getSimpleName());
            }
            Log.e("ViewPager", "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
            viewFindFocus = null;
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i2);
        boolean zN = false;
        if (viewFindNextFocus != null && viewFindNextFocus != viewFindFocus) {
            Rect rect = this.f1985d;
            if (i2 == 17) {
                int i3 = h(rect, viewFindNextFocus).left;
                int i4 = h(rect, viewFindFocus).left;
                if (viewFindFocus == null || i3 < i4) {
                    zRequestFocus = viewFindNextFocus.requestFocus();
                    zN = zRequestFocus;
                } else {
                    int i5 = this.f1987f;
                    if (i5 > 0) {
                        w(i5 - 1);
                        zN = true;
                    }
                }
            } else if (i2 == 66) {
                zRequestFocus = (viewFindFocus == null || h(rect, viewFindNextFocus).left > h(rect, viewFindFocus).left) ? viewFindNextFocus.requestFocus() : n();
                zN = zRequestFocus;
            }
        } else if (i2 == 17 || i2 == 1) {
            int i6 = this.f1987f;
            if (i6 > 0) {
                w(i6 - 1);
                zN = true;
            }
        } else if (i2 == 66 || i2 == 2) {
            zN = n();
        }
        if (zN) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i2));
        }
        return zN;
    }

    @Override // android.view.View
    public final boolean canScrollHorizontally(int i2) {
        if (this.f1986e == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        return i2 < 0 ? scrollX > ((int) (((float) clientWidth) * this.f1998q)) : i2 > 0 && scrollX < ((int) (((float) clientWidth) * this.r));
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof i) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public final void computeScroll() {
        this.f1992k = true;
        Scroller scroller = this.f1991j;
        if (scroller.isFinished() || !scroller.computeScrollOffset()) {
            e(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = scroller.getCurrX();
        int currY = scroller.getCurrY();
        if (scrollX != currX || scrollY != currY) {
            scrollTo(currX, currY);
            if (!o(currX)) {
                scroller.abortAnimation();
                scrollTo(0, currY);
            }
        }
        WeakHashMap weakHashMap = v.l.f2836a;
        postInvalidateOnAnimation();
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x005f  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r6) {
        /*
            r5 = this;
            boolean r0 = super.dispatchKeyEvent(r6)
            r1 = 1
            if (r0 != 0) goto L64
            int r0 = r6.getAction()
            r2 = 0
            if (r0 != 0) goto L5f
            int r0 = r6.getKeyCode()
            r3 = 21
            r4 = 2
            if (r0 == r3) goto L48
            r3 = 22
            if (r0 == r3) goto L36
            r3 = 61
            if (r0 == r3) goto L20
            goto L5f
        L20:
            boolean r0 = r6.hasNoModifiers()
            if (r0 == 0) goto L2b
            boolean r5 = r5.c(r4)
            goto L60
        L2b:
            boolean r6 = r6.hasModifiers(r1)
            if (r6 == 0) goto L5f
            boolean r5 = r5.c(r1)
            goto L60
        L36:
            boolean r6 = r6.hasModifiers(r4)
            if (r6 == 0) goto L41
            boolean r5 = r5.n()
            goto L60
        L41:
            r6 = 66
            boolean r5 = r5.c(r6)
            goto L60
        L48:
            boolean r6 = r6.hasModifiers(r4)
            if (r6 == 0) goto L58
            int r6 = r5.f1987f
            if (r6 <= 0) goto L5f
            int r6 = r6 - r1
            r5.w(r6)
            r5 = r1
            goto L60
        L58:
            r6 = 17
            boolean r5 = r5.c(r6)
            goto L60
        L5f:
            r5 = r2
        L60:
            if (r5 == 0) goto L63
            goto L64
        L63:
            r1 = r2
        L64:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    @Override // android.view.View
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        h hVarI;
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (hVarI = i(childAt)) != null && hVarI.f126b == this.f1987f && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        F.a aVar;
        EdgeEffect edgeEffect = this.f1973N;
        EdgeEffect edgeEffect2 = this.f1972M;
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        boolean zDraw = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && (aVar = this.f1986e) != null && aVar.d() > 1)) {
            if (!edgeEffect2.isFinished()) {
                int iSave = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate(getPaddingTop() + (-height), this.f1998q * width);
                edgeEffect2.setSize(height, width);
                zDraw = edgeEffect2.draw(canvas);
                canvas.restoreToCount(iSave);
            }
            if (!edgeEffect.isFinished()) {
                int iSave2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate(-getPaddingTop(), (-(this.r + 1.0f)) * width2);
                edgeEffect.setSize(height2, width2);
                zDraw |= edgeEffect.draw(canvas);
                canvas.restoreToCount(iSave2);
            }
        } else {
            edgeEffect2.finish();
            edgeEffect.finish();
        }
        if (zDraw) {
            WeakHashMap weakHashMap = v.l.f2836a;
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.f1995n;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        drawable.setState(getDrawableState());
    }

    public final void e(boolean z2) {
        Scroller scroller = this.f1991j;
        boolean z3 = this.f1981W == 2;
        if (z3) {
            setScrollingCacheEnabled(false);
            if (!scroller.isFinished()) {
                scroller.abortAnimation();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = scroller.getCurrX();
                int currY = scroller.getCurrY();
                if (scrollX != currX || scrollY != currY) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        o(currX);
                    }
                }
            }
        }
        this.f2002v = false;
        int i2 = 0;
        while (true) {
            ArrayList arrayList = this.f1983b;
            if (i2 >= arrayList.size()) {
                break;
            }
            h hVar = (h) arrayList.get(i2);
            if (hVar.f127c) {
                hVar.f127c = false;
                z3 = true;
            }
            i2++;
        }
        if (z3) {
            e eVar = this.V;
            if (!z2) {
                eVar.run();
            } else {
                WeakHashMap weakHashMap = v.l.f2836a;
                postOnAnimation(eVar);
            }
        }
    }

    public final void f() {
        int iD = this.f1986e.d();
        this.f1982a = iD;
        ArrayList arrayList = this.f1983b;
        boolean z2 = arrayList.size() < (this.f2003w * 2) + 1 && arrayList.size() < iD;
        int iMax = this.f1987f;
        int i2 = 0;
        boolean z3 = false;
        while (i2 < arrayList.size()) {
            h hVar = (h) arrayList.get(i2);
            int iE = this.f1986e.e(hVar.f125a);
            if (iE != -1) {
                if (iE == -2) {
                    arrayList.remove(i2);
                    i2--;
                    if (!z3) {
                        this.f1986e.q(this);
                        z3 = true;
                    }
                    this.f1986e.a(this, hVar.f126b, hVar.f125a);
                    int i3 = this.f1987f;
                    if (i3 == hVar.f126b) {
                        iMax = Math.max(0, Math.min(i3, iD - 1));
                    }
                } else {
                    int i4 = hVar.f126b;
                    if (i4 != iE) {
                        if (i4 == this.f1987f) {
                            iMax = iE;
                        }
                        hVar.f126b = iE;
                    }
                }
                z2 = true;
            }
            i2++;
        }
        if (z3) {
            this.f1986e.c(this);
        }
        Collections.sort(arrayList, f1958b0);
        if (z2) {
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                i iVar = (i) getChildAt(i5).getLayoutParams();
                if (!iVar.f130a) {
                    iVar.f132c = 0.0f;
                }
            }
            x(iMax, 0, false, true);
            requestLayout();
        }
    }

    public final void g(int i2) {
        k kVar = this.f1978S;
        if (kVar != null) {
            kVar.c(i2);
        }
        ArrayList arrayList = this.f1977R;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                k kVar2 = (k) this.f1977R.get(i3);
                if (kVar2 != null) {
                    kVar2.c(i2);
                }
            }
        }
        k kVar3 = this.f1979T;
        if (kVar3 != null) {
            kVar3.c(i2);
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        i iVar = new i(-1, -1);
        iVar.f132c = 0.0f;
        return iVar;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        i iVar = new i(context, attributeSet);
        iVar.f132c = 0.0f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f1957a0);
        iVar.f131b = typedArrayObtainStyledAttributes.getInteger(0, 48);
        typedArrayObtainStyledAttributes.recycle();
        return iVar;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    public F.a getAdapter() {
        return this.f1986e;
    }

    @Override // android.view.ViewGroup
    public final int getChildDrawingOrder(int i2, int i3) {
        throw null;
    }

    public int getCurrentItem() {
        return this.f1987f;
    }

    public int getOffscreenPageLimit() {
        return this.f2003w;
    }

    public int getPageMargin() {
        return this.f1994m;
    }

    public final Rect h(Rect rect, View view) {
        if (rect == null) {
            rect = new Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        ViewParent parent = view.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = (ViewGroup) parent;
            rect.left = viewGroup.getLeft() + rect.left;
            rect.right = viewGroup.getRight() + rect.right;
            rect.top = viewGroup.getTop() + rect.top;
            rect.bottom = viewGroup.getBottom() + rect.bottom;
            parent = viewGroup.getParent();
        }
        return rect;
    }

    public final h i(View view) {
        int i2 = 0;
        while (true) {
            ArrayList arrayList = this.f1983b;
            if (i2 >= arrayList.size()) {
                return null;
            }
            h hVar = (h) arrayList.get(i2);
            if (this.f1986e.i(view, hVar.f125a)) {
                return hVar;
            }
            i2++;
        }
    }

    public final h j() {
        h hVar;
        int i2;
        int clientWidth = getClientWidth();
        float f2 = 0.0f;
        float scrollX = clientWidth > 0 ? getScrollX() / clientWidth : 0.0f;
        float f3 = clientWidth > 0 ? this.f1994m / clientWidth : 0.0f;
        int i3 = 0;
        boolean z2 = true;
        h hVar2 = null;
        int i4 = -1;
        float f4 = 0.0f;
        while (true) {
            ArrayList arrayList = this.f1983b;
            if (i3 >= arrayList.size()) {
                return hVar2;
            }
            h hVar3 = (h) arrayList.get(i3);
            if (z2 || hVar3.f126b == (i2 = i4 + 1)) {
                hVar = hVar3;
            } else {
                float f5 = f2 + f4 + f3;
                h hVar4 = this.f1984c;
                hVar4.f129e = f5;
                hVar4.f126b = i2;
                hVar4.f128d = this.f1986e.g(i2);
                i3--;
                hVar = hVar4;
            }
            f2 = hVar.f129e;
            float f6 = hVar.f128d + f2 + f3;
            if (!z2 && scrollX < f2) {
                return hVar2;
            }
            if (scrollX < f6 || i3 == arrayList.size() - 1) {
                break;
            }
            int i5 = hVar.f126b;
            float f7 = hVar.f128d;
            i3++;
            z2 = false;
            h hVar5 = hVar;
            i4 = i5;
            f4 = f7;
            hVar2 = hVar5;
        }
        return hVar;
    }

    public final h k(int i2) {
        int i3 = 0;
        while (true) {
            ArrayList arrayList = this.f1983b;
            if (i3 >= arrayList.size()) {
                return null;
            }
            h hVar = (h) arrayList.get(i3);
            if (hVar.f126b == i2) {
                return hVar;
            }
            i3++;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void l(int r13, int r14, float r15) {
        /*
            r12 = this;
            int r0 = r12.f1976Q
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto L6c
            int r0 = r12.getScrollX()
            int r3 = r12.getPaddingLeft()
            int r4 = r12.getPaddingRight()
            int r5 = r12.getWidth()
            int r6 = r12.getChildCount()
            r7 = r1
        L1b:
            if (r7 >= r6) goto L6c
            android.view.View r8 = r12.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            F.i r9 = (F.i) r9
            boolean r10 = r9.f130a
            if (r10 != 0) goto L2c
            goto L69
        L2c:
            int r9 = r9.f131b
            r9 = r9 & 7
            if (r9 == r2) goto L50
            r10 = 3
            if (r9 == r10) goto L4a
            r10 = 5
            if (r9 == r10) goto L3a
            r9 = r3
            goto L5d
        L3a:
            int r9 = r5 - r4
            int r10 = r8.getMeasuredWidth()
            int r9 = r9 - r10
            int r10 = r8.getMeasuredWidth()
            int r4 = r4 + r10
        L46:
            r11 = r9
            r9 = r3
            r3 = r11
            goto L5d
        L4a:
            int r9 = r8.getWidth()
            int r9 = r9 + r3
            goto L5d
        L50:
            int r9 = r8.getMeasuredWidth()
            int r9 = r5 - r9
            int r9 = r9 / 2
            int r9 = java.lang.Math.max(r9, r3)
            goto L46
        L5d:
            int r3 = r3 + r0
            int r10 = r8.getLeft()
            int r3 = r3 - r10
            if (r3 == 0) goto L68
            r8.offsetLeftAndRight(r3)
        L68:
            r3 = r9
        L69:
            int r7 = r7 + 1
            goto L1b
        L6c:
            F.k r0 = r12.f1978S
            if (r0 == 0) goto L73
            r0.a(r13, r14, r15)
        L73:
            java.util.ArrayList r0 = r12.f1977R
            if (r0 == 0) goto L8d
            int r0 = r0.size()
        L7b:
            if (r1 >= r0) goto L8d
            java.util.ArrayList r3 = r12.f1977R
            java.lang.Object r3 = r3.get(r1)
            F.k r3 = (F.k) r3
            if (r3 == 0) goto L8a
            r3.a(r13, r14, r15)
        L8a:
            int r1 = r1 + 1
            goto L7b
        L8d:
            F.k r0 = r12.f1979T
            if (r0 == 0) goto L94
            r0.a(r13, r14, r15)
        L94:
            r12.f1975P = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.l(int, int, float):void");
    }

    public final void m(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.f1966G) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.f1962C = motionEvent.getX(i2);
            this.f1966G = motionEvent.getPointerId(i2);
            VelocityTracker velocityTracker = this.f1967H;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    public final boolean n() {
        F.a aVar = this.f1986e;
        if (aVar == null || this.f1987f >= aVar.d() - 1) {
            return false;
        }
        w(this.f1987f + 1);
        return true;
    }

    public final boolean o(int i2) {
        if (this.f1983b.size() == 0) {
            if (this.f1974O) {
                return false;
            }
            this.f1975P = false;
            l(0, 0, 0.0f);
            if (this.f1975P) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        h hVarJ = j();
        int clientWidth = getClientWidth();
        int i3 = this.f1994m;
        int i4 = clientWidth + i3;
        float f2 = clientWidth;
        int i5 = hVarJ.f126b;
        float f3 = ((i2 / f2) - hVarJ.f129e) / (hVarJ.f128d + (i3 / f2));
        this.f1975P = false;
        l(i5, (int) (i4 * f3), f3);
        if (this.f1975P) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.f1974O = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        removeCallbacks(this.V);
        Scroller scroller = this.f1991j;
        if (scroller != null && !scroller.isFinished()) {
            this.f1991j.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i2;
        float f2;
        ArrayList arrayList;
        float f3;
        super.onDraw(canvas);
        if (this.f1994m <= 0 || this.f1995n == null) {
            return;
        }
        ArrayList arrayList2 = this.f1983b;
        if (arrayList2.size() <= 0 || this.f1986e == null) {
            return;
        }
        int scrollX = getScrollX();
        float width = getWidth();
        float f4 = this.f1994m / width;
        int i3 = 0;
        h hVar = (h) arrayList2.get(0);
        float f5 = hVar.f129e;
        int size = arrayList2.size();
        int i4 = hVar.f126b;
        int i5 = ((h) arrayList2.get(size - 1)).f126b;
        while (i4 < i5) {
            while (true) {
                i2 = hVar.f126b;
                if (i4 <= i2 || i3 >= size) {
                    break;
                }
                i3++;
                hVar = (h) arrayList2.get(i3);
            }
            if (i4 == i2) {
                float f6 = hVar.f129e;
                float f7 = hVar.f128d;
                f2 = (f6 + f7) * width;
                f5 = f6 + f7 + f4;
            } else {
                float fG = this.f1986e.g(i4);
                f2 = (f5 + fG) * width;
                f5 = fG + f4 + f5;
            }
            if (this.f1994m + f2 > scrollX) {
                arrayList = arrayList2;
                f3 = f4;
                this.f1995n.setBounds(Math.round(f2), this.f1996o, Math.round(this.f1994m + f2), this.f1997p);
                this.f1995n.draw(canvas);
            } else {
                arrayList = arrayList2;
                f3 = f4;
            }
            if (f2 > scrollX + r3) {
                return;
            }
            i4++;
            arrayList2 = arrayList;
            f4 = f3;
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i2 = this.f1961B;
        Scroller scroller = this.f1991j;
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            u();
            return false;
        }
        if (action != 0) {
            if (this.f2004x) {
                return true;
            }
            if (this.f2005y) {
                return false;
            }
        }
        if (action == 0) {
            float x2 = motionEvent.getX();
            this.f1964E = x2;
            this.f1962C = x2;
            float y2 = motionEvent.getY();
            this.f1965F = y2;
            this.f1963D = y2;
            this.f1966G = motionEvent.getPointerId(0);
            this.f2005y = false;
            this.f1992k = true;
            scroller.computeScrollOffset();
            if (this.f1981W != 2 || Math.abs(scroller.getFinalX() - scroller.getCurrX()) <= this.f1971L) {
                e(false);
                this.f2004x = false;
            } else {
                scroller.abortAnimation();
                this.f2002v = false;
                q();
                this.f2004x = true;
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                setScrollState(1);
            }
        } else if (action == 2) {
            int i3 = this.f1966G;
            if (i3 != -1) {
                int iFindPointerIndex = motionEvent.findPointerIndex(i3);
                float x3 = motionEvent.getX(iFindPointerIndex);
                float f2 = x3 - this.f1962C;
                float fAbs = Math.abs(f2);
                float y3 = motionEvent.getY(iFindPointerIndex);
                float fAbs2 = Math.abs(y3 - this.f1965F);
                if (f2 != 0.0f) {
                    float f3 = this.f1962C;
                    if ((f3 >= this.f1960A || f2 <= 0.0f) && ((f3 <= getWidth() - this.f1960A || f2 >= 0.0f) && d((int) f2, (int) x3, (int) y3, this, false))) {
                        this.f1962C = x3;
                        this.f1963D = y3;
                        this.f2005y = true;
                        return false;
                    }
                }
                float f4 = i2;
                if (fAbs > f4 && fAbs * 0.5f > fAbs2) {
                    this.f2004x = true;
                    ViewParent parent2 = getParent();
                    if (parent2 != null) {
                        parent2.requestDisallowInterceptTouchEvent(true);
                    }
                    setScrollState(1);
                    float f5 = this.f1964E;
                    float f6 = i2;
                    this.f1962C = f2 > 0.0f ? f5 + f6 : f5 - f6;
                    this.f1963D = y3;
                    setScrollingCacheEnabled(true);
                } else if (fAbs2 > f4) {
                    this.f2005y = true;
                }
                if (this.f2004x && p(x3)) {
                    WeakHashMap weakHashMap = v.l.f2836a;
                    postInvalidateOnAnimation();
                }
            }
        } else if (action == 6) {
            m(motionEvent);
        }
        if (this.f1967H == null) {
            this.f1967H = VelocityTracker.obtain();
        }
        this.f1967H.addMovement(motionEvent);
        return this.f2004x;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0094  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onLayout(boolean r19, int r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instruction units count: 286
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        i iVar;
        i iVar2;
        int i4;
        setMeasuredDimension(ViewGroup.getDefaultSize(0, i2), ViewGroup.getDefaultSize(0, i3));
        int measuredWidth = getMeasuredWidth();
        this.f1960A = Math.min(measuredWidth / 10, this.f2006z);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        int i5 = 0;
        while (true) {
            boolean z2 = true;
            int i6 = 1073741824;
            if (i5 >= childCount) {
                break;
            }
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8 && (iVar2 = (i) childAt.getLayoutParams()) != null && iVar2.f130a) {
                int i7 = iVar2.f131b;
                int i8 = i7 & 7;
                int i9 = i7 & 112;
                boolean z3 = i9 == 48 || i9 == 80;
                if (i8 != 3 && i8 != 5) {
                    z2 = false;
                }
                int i10 = Integer.MIN_VALUE;
                if (z3) {
                    i4 = Integer.MIN_VALUE;
                    i10 = 1073741824;
                } else {
                    i4 = z2 ? 1073741824 : Integer.MIN_VALUE;
                }
                int i11 = ((ViewGroup.LayoutParams) iVar2).width;
                if (i11 != -2) {
                    if (i11 == -1) {
                        i11 = paddingLeft;
                    }
                    i10 = 1073741824;
                } else {
                    i11 = paddingLeft;
                }
                int i12 = ((ViewGroup.LayoutParams) iVar2).height;
                if (i12 == -2) {
                    i12 = measuredHeight;
                    i6 = i4;
                } else if (i12 == -1) {
                    i12 = measuredHeight;
                }
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i11, i10), View.MeasureSpec.makeMeasureSpec(i12, i6));
                if (z3) {
                    measuredHeight -= childAt.getMeasuredHeight();
                } else if (z2) {
                    paddingLeft -= childAt.getMeasuredWidth();
                }
            }
            i5++;
        }
        View.MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
        this.f1999s = View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.f2000t = true;
        q();
        this.f2000t = false;
        int childCount2 = getChildCount();
        for (int i13 = 0; i13 < childCount2; i13++) {
            View childAt2 = getChildAt(i13);
            if (childAt2.getVisibility() != 8 && ((iVar = (i) childAt2.getLayoutParams()) == null || !iVar.f130a)) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (paddingLeft * iVar.f132c), 1073741824), this.f1999s);
            }
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i2, Rect rect) {
        int i3;
        int i4;
        int i5;
        h hVarI;
        int childCount = getChildCount();
        if ((i2 & 2) != 0) {
            i4 = childCount;
            i3 = 0;
            i5 = 1;
        } else {
            i3 = childCount - 1;
            i4 = -1;
            i5 = -1;
        }
        while (i3 != i4) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() == 0 && (hVarI = i(childAt)) != null && hVarI.f126b == this.f1987f && childAt.requestFocus(i2, rect)) {
                return true;
            }
            i3 += i5;
        }
        return false;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f1465a);
        F.a aVar = this.f1986e;
        ClassLoader classLoader = savedState.f2009e;
        if (aVar != null) {
            aVar.l(savedState.f2008d, classLoader);
            x(savedState.f2007c, 0, false, true);
        } else {
            this.f1988g = savedState.f2007c;
            this.f1989h = savedState.f2008d;
            this.f1990i = classLoader;
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f2007c = this.f1987f;
        F.a aVar = this.f1986e;
        if (aVar != null) {
            savedState.f2008d = aVar.m();
        }
        return savedState;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4) {
            int i6 = this.f1994m;
            s(i2, i4, i6, i6);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00d8  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            Method dump skipped, instruction units count: 418
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final boolean p(float f2) {
        boolean z2;
        boolean z3;
        float f3 = this.f1962C - f2;
        this.f1962C = f2;
        float scrollX = getScrollX() + f3;
        float clientWidth = getClientWidth();
        float f4 = this.f1998q * clientWidth;
        float f5 = this.r * clientWidth;
        ArrayList arrayList = this.f1983b;
        boolean z4 = false;
        h hVar = (h) arrayList.get(0);
        h hVar2 = (h) arrayList.get(arrayList.size() - 1);
        if (hVar.f126b != 0) {
            f4 = hVar.f129e * clientWidth;
            z2 = false;
        } else {
            z2 = true;
        }
        if (hVar2.f126b != this.f1986e.d() - 1) {
            f5 = hVar2.f129e * clientWidth;
            z3 = false;
        } else {
            z3 = true;
        }
        if (scrollX < f4) {
            if (z2) {
                this.f1972M.onPull(Math.abs(f4 - scrollX) / clientWidth);
                z4 = true;
            }
            scrollX = f4;
        } else if (scrollX > f5) {
            if (z3) {
                this.f1973N.onPull(Math.abs(scrollX - f5) / clientWidth);
                z4 = true;
            }
            scrollX = f5;
        }
        int i2 = (int) scrollX;
        this.f1962C = (scrollX - i2) + this.f1962C;
        scrollTo(i2, getScrollY());
        o(i2);
        return z4;
    }

    public final void q() {
        r(this.f1987f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x005e, code lost:
    
        r9 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00bc A[PHI: r7 r11 r15
      0x00bc: PHI (r7v13 int) = (r7v12 int), (r7v4 int), (r7v16 int) binds: [B:61:0x00de, B:58:0x00ca, B:50:0x00b3] A[DONT_GENERATE, DONT_INLINE]
      0x00bc: PHI (r11v26 int) = (r11v1 int), (r11v25 int), (r11v29 int) binds: [B:61:0x00de, B:58:0x00ca, B:50:0x00b3] A[DONT_GENERATE, DONT_INLINE]
      0x00bc: PHI (r15v6 float) = (r15v4 float), (r15v5 float), (r15v3 float) binds: [B:61:0x00de, B:58:0x00ca, B:50:0x00b3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x013b A[PHI: r3 r12
      0x013b: PHI (r3v42 float) = (r3v40 float), (r3v41 float), (r3v39 float) binds: [B:95:0x0162, B:92:0x014c, B:85:0x0132] A[DONT_GENERATE, DONT_INLINE]
      0x013b: PHI (r12v10 int) = (r12v8 int), (r12v9 int), (r12v7 int) binds: [B:95:0x0162, B:92:0x014c, B:85:0x0132] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void r(int r18) {
        /*
            Method dump skipped, instruction units count: 880
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.r(int):void");
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void removeView(View view) {
        if (this.f2000t) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public final void s(int i2, int i3, int i4, int i5) {
        if (i3 > 0 && !this.f1983b.isEmpty()) {
            if (!this.f1991j.isFinished()) {
                this.f1991j.setFinalX(getCurrentItem() * getClientWidth());
                return;
            } else {
                scrollTo((int) ((getScrollX() / (((i3 - getPaddingLeft()) - getPaddingRight()) + i5)) * (((i2 - getPaddingLeft()) - getPaddingRight()) + i4)), getScrollY());
                return;
            }
        }
        h hVarK = k(this.f1987f);
        int iMin = (int) ((hVarK != null ? Math.min(hVarK.f129e, this.r) : 0.0f) * ((i2 - getPaddingLeft()) - getPaddingRight()));
        if (iMin != getScrollX()) {
            e(false);
            scrollTo(iMin, getScrollY());
        }
    }

    public void setAdapter(F.a aVar) {
        ArrayList arrayList;
        F.a aVar2 = this.f1986e;
        if (aVar2 != null) {
            synchronized (aVar2) {
                aVar2.f116b = null;
            }
            this.f1986e.q(this);
            int i2 = 0;
            while (true) {
                arrayList = this.f1983b;
                if (i2 >= arrayList.size()) {
                    break;
                }
                h hVar = (h) arrayList.get(i2);
                this.f1986e.a(this, hVar.f126b, hVar.f125a);
                i2++;
            }
            this.f1986e.c(this);
            arrayList.clear();
            int i3 = 0;
            while (i3 < getChildCount()) {
                if (!((i) getChildAt(i3).getLayoutParams()).f130a) {
                    removeViewAt(i3);
                    i3--;
                }
                i3++;
            }
            this.f1987f = 0;
            scrollTo(0, 0);
        }
        this.f1986e = aVar;
        this.f1982a = 0;
        if (aVar != null) {
            if (this.f1993l == null) {
                this.f1993l = new l(0, this);
            }
            F.a aVar3 = this.f1986e;
            l lVar = this.f1993l;
            synchronized (aVar3) {
                aVar3.f116b = lVar;
            }
            this.f2002v = false;
            boolean z2 = this.f1974O;
            this.f1974O = true;
            this.f1982a = this.f1986e.d();
            if (this.f1988g >= 0) {
                this.f1986e.l(this.f1989h, this.f1990i);
                x(this.f1988g, 0, false, true);
                this.f1988g = -1;
                this.f1989h = null;
                this.f1990i = null;
            } else if (z2) {
                requestLayout();
            } else {
                q();
            }
        }
        ArrayList arrayList2 = this.f1980U;
        if (arrayList2 == null || arrayList2.isEmpty() || this.f1980U.size() <= 0) {
            return;
        }
        w0.c(this.f1980U.get(0));
        throw null;
    }

    public void setCurrentItem(int i2) {
        this.f2002v = false;
        x(i2, 0, !this.f1974O, false);
    }

    public void setOffscreenPageLimit(int i2) {
        if (i2 < 1) {
            Log.w("ViewPager", "Requested offscreen page limit " + i2 + " too small; defaulting to 1");
            i2 = 1;
        }
        if (i2 != this.f2003w) {
            this.f2003w = i2;
            q();
        }
    }

    @Deprecated
    public void setOnPageChangeListener(k kVar) {
        this.f1978S = kVar;
    }

    public void setPageMargin(int i2) {
        int i3 = this.f1994m;
        this.f1994m = i2;
        int width = getWidth();
        s(width, width, i2, i3);
        requestLayout();
    }

    public void setPageMarginDrawable(int i2) {
        setPageMarginDrawable(getContext().getDrawable(i2));
    }

    public void setPageMarginDrawable(Drawable drawable) {
        this.f1995n = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public void setScrollState(int i2) {
        if (this.f1981W == i2) {
            return;
        }
        this.f1981W = i2;
        k kVar = this.f1978S;
        if (kVar != null) {
            kVar.b(i2);
        }
        ArrayList arrayList = this.f1977R;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                k kVar2 = (k) this.f1977R.get(i3);
                if (kVar2 != null) {
                    kVar2.b(i2);
                }
            }
        }
        k kVar3 = this.f1979T;
        if (kVar3 != null) {
            kVar3.b(i2);
        }
    }

    public void t(k kVar) {
        ArrayList arrayList = this.f1977R;
        if (arrayList != null) {
            arrayList.remove(kVar);
        }
    }

    public final boolean u() {
        this.f1966G = -1;
        this.f2004x = false;
        this.f2005y = false;
        VelocityTracker velocityTracker = this.f1967H;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.f1967H = null;
        }
        this.f1972M.onRelease();
        this.f1973N.onRelease();
        return this.f1972M.isFinished() || this.f1973N.isFinished();
    }

    public final void v(int i2, int i3, boolean z2, boolean z3) {
        int scrollX;
        Scroller scroller = this.f1991j;
        h hVarK = k(i2);
        int iMax = hVarK != null ? (int) (Math.max(this.f1998q, Math.min(hVarK.f129e, this.r)) * getClientWidth()) : 0;
        if (!z2) {
            if (z3) {
                g(i2);
            }
            e(false);
            scrollTo(iMax, 0);
            o(iMax);
            return;
        }
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
        } else {
            if (scroller == null || scroller.isFinished()) {
                scrollX = getScrollX();
            } else {
                scrollX = this.f1992k ? scroller.getCurrX() : scroller.getStartX();
                scroller.abortAnimation();
                setScrollingCacheEnabled(false);
            }
            int i4 = scrollX;
            int scrollY = getScrollY();
            int i5 = iMax - i4;
            int i6 = 0 - scrollY;
            if (i5 == 0 && i6 == 0) {
                e(false);
                q();
                setScrollState(0);
            } else {
                setScrollingCacheEnabled(true);
                setScrollState(2);
                int clientWidth = getClientWidth();
                int i7 = clientWidth / 2;
                float f2 = clientWidth;
                float f3 = i7;
                float fSin = (((float) Math.sin((Math.min(1.0f, (Math.abs(i5) * 1.0f) / f2) - 0.5f) * 0.47123894f)) * f3) + f3;
                int iAbs = Math.abs(i3);
                int iMin = Math.min(iAbs > 0 ? Math.round(Math.abs(fSin / iAbs) * 1000.0f) * 4 : (int) (((Math.abs(i5) / ((this.f1986e.g(this.f1987f) * f2) + this.f1994m)) + 1.0f) * 100.0f), 600);
                this.f1992k = false;
                this.f1991j.startScroll(i4, scrollY, i5, i6, iMin);
                WeakHashMap weakHashMap = v.l.f2836a;
                postInvalidateOnAnimation();
            }
        }
        if (z3) {
            g(i2);
        }
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.f1995n;
    }

    public void w(int i2) {
        this.f2002v = false;
        x(i2, 0, true, false);
    }

    public final void x(int i2, int i3, boolean z2, boolean z3) {
        F.a aVar = this.f1986e;
        if (aVar == null || aVar.d() <= 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        ArrayList arrayList = this.f1983b;
        if (!z3 && this.f1987f == i2 && arrayList.size() != 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (i2 < 0) {
            i2 = 0;
        } else if (i2 >= this.f1986e.d()) {
            i2 = this.f1986e.d() - 1;
        }
        int i4 = this.f2003w;
        int i5 = this.f1987f;
        if (i2 > i5 + i4 || i2 < i5 - i4) {
            for (int i6 = 0; i6 < arrayList.size(); i6++) {
                ((h) arrayList.get(i6)).f127c = true;
            }
        }
        boolean z4 = this.f1987f != i2;
        if (!this.f1974O) {
            r(i2);
            v(i2, i3, z2, z4);
        } else {
            this.f1987f = i2;
            if (z4) {
                g(i2);
            }
            requestLayout();
        }
    }
}
