package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import b.AbstractC0122a;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class ActionBarOverlayLayout extends ViewGroup implements E {

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public static final int[] f889B = {R$attr.actionBarSize, R.attr.windowContentOverlay};

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public final v.i f890A;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f891a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f892b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public ContentFrameLayout f893c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public ActionBarContainer f894d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public F f895e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public Drawable f896f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f897g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f898h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f899i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f900j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f901k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f902l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f903m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final Rect f904n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final Rect f905o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final Rect f906p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final Rect f907q;
    public final Rect r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public final Rect f908s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public final Rect f909t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public InterfaceC0067d f910u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public OverScroller f911v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public ViewPropertyAnimator f912w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public final C.p f913x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public final RunnableC0066c f914y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public final RunnableC0066c f915z;

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f892b = 0;
        this.f904n = new Rect();
        this.f905o = new Rect();
        this.f906p = new Rect();
        this.f907q = new Rect();
        this.r = new Rect();
        this.f908s = new Rect();
        this.f909t = new Rect();
        this.f913x = new C.p(3, this);
        this.f914y = new RunnableC0066c(this, 0);
        this.f915z = new RunnableC0066c(this, 1);
        c(context);
        this.f890A = new v.i();
    }

    public static boolean a(FrameLayout frameLayout, Rect rect, boolean z2) {
        boolean z3;
        C0068e c0068e = (C0068e) frameLayout.getLayoutParams();
        int i2 = ((ViewGroup.MarginLayoutParams) c0068e).leftMargin;
        int i3 = rect.left;
        if (i2 != i3) {
            ((ViewGroup.MarginLayoutParams) c0068e).leftMargin = i3;
            z3 = true;
        } else {
            z3 = false;
        }
        int i4 = ((ViewGroup.MarginLayoutParams) c0068e).topMargin;
        int i5 = rect.top;
        if (i4 != i5) {
            ((ViewGroup.MarginLayoutParams) c0068e).topMargin = i5;
            z3 = true;
        }
        int i6 = ((ViewGroup.MarginLayoutParams) c0068e).rightMargin;
        int i7 = rect.right;
        if (i6 != i7) {
            ((ViewGroup.MarginLayoutParams) c0068e).rightMargin = i7;
            z3 = true;
        }
        if (z2) {
            int i8 = ((ViewGroup.MarginLayoutParams) c0068e).bottomMargin;
            int i9 = rect.bottom;
            if (i8 != i9) {
                ((ViewGroup.MarginLayoutParams) c0068e).bottomMargin = i9;
                return true;
            }
        }
        return z3;
    }

    public final void b() {
        removeCallbacks(this.f914y);
        removeCallbacks(this.f915z);
        ViewPropertyAnimator viewPropertyAnimator = this.f912w;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    public final void c(Context context) {
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(f889B);
        this.f891a = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(1);
        this.f896f = drawable;
        setWillNotDraw(drawable == null);
        typedArrayObtainStyledAttributes.recycle();
        this.f897g = context.getApplicationInfo().targetSdkVersion < 19;
        this.f911v = new OverScroller(context);
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof C0068e;
    }

    public final void d(int i2) {
        e();
        if (i2 == 2) {
            ((v0) this.f895e).getClass();
            Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
        } else if (i2 == 5) {
            ((v0) this.f895e).getClass();
            Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
        } else {
            if (i2 != 109) {
                return;
            }
            setOverlayMode(true);
        }
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        int translationY;
        super.draw(canvas);
        if (this.f896f == null || this.f897g) {
            return;
        }
        if (this.f894d.getVisibility() == 0) {
            translationY = (int) (this.f894d.getTranslationY() + this.f894d.getBottom() + 0.5f);
        } else {
            translationY = 0;
        }
        this.f896f.setBounds(0, translationY, getWidth(), this.f896f.getIntrinsicHeight() + translationY);
        this.f896f.draw(canvas);
    }

    public final void e() {
        F wrapper;
        if (this.f893c == null) {
            this.f893c = (ContentFrameLayout) findViewById(R$id.action_bar_activity_content);
            this.f894d = (ActionBarContainer) findViewById(R$id.action_bar_container);
            KeyEvent.Callback callbackFindViewById = findViewById(R$id.action_bar);
            if (callbackFindViewById instanceof F) {
                wrapper = (F) callbackFindViewById;
            } else {
                if (!(callbackFindViewById instanceof Toolbar)) {
                    throw new IllegalStateException("Can't make a decor toolbar out of ".concat(callbackFindViewById.getClass().getSimpleName()));
                }
                wrapper = ((Toolbar) callbackFindViewById).getWrapper();
            }
            this.f895e = wrapper;
        }
    }

    public final void f(androidx.appcompat.view.menu.o oVar, androidx.appcompat.app.j jVar) {
        e();
        v0 v0Var = (v0) this.f895e;
        C0074k c0074k = v0Var.f1333n;
        Toolbar toolbar = v0Var.f1320a;
        if (c0074k == null) {
            v0Var.f1333n = new C0074k(toolbar.getContext());
        }
        C0074k c0074k2 = v0Var.f1333n;
        c0074k2.f1229e = jVar;
        if (oVar == null && toolbar.f1154a == null) {
            return;
        }
        toolbar.e();
        androidx.appcompat.view.menu.o oVar2 = toolbar.f1154a.f918p;
        if (oVar2 == oVar) {
            return;
        }
        if (oVar2 != null) {
            oVar2.r(toolbar.f1148J);
            oVar2.r(toolbar.f1149K);
        }
        if (toolbar.f1149K == null) {
            toolbar.f1149K = new p0(toolbar);
        }
        c0074k2.f1241q = true;
        if (oVar != null) {
            oVar.b(c0074k2, toolbar.f1163j);
            oVar.b(toolbar.f1149K, toolbar.f1163j);
        } else {
            c0074k2.e(toolbar.f1163j, null);
            toolbar.f1149K.e(toolbar.f1163j, null);
            c0074k2.f();
            toolbar.f1149K.f();
        }
        toolbar.f1154a.setPopupTheme(toolbar.f1164k);
        toolbar.f1154a.setPresenter(c0074k2);
        toolbar.f1148J = c0074k2;
    }

    @Override // android.view.View
    public final boolean fitSystemWindows(Rect rect) {
        e();
        WeakHashMap weakHashMap = v.l.f2836a;
        getWindowSystemUiVisibility();
        boolean zA = a(this.f894d, rect, false);
        Rect rect2 = this.f907q;
        rect2.set(rect);
        Method method = y0.f1347a;
        Rect rect3 = this.f904n;
        if (method != null) {
            try {
                method.invoke(this, rect2, rect3);
            } catch (Exception e2) {
                Log.d("ViewUtils", "Could not invoke computeFitSystemWindows", e2);
            }
        }
        Rect rect4 = this.r;
        if (!rect4.equals(rect2)) {
            rect4.set(rect2);
            zA = true;
        }
        Rect rect5 = this.f905o;
        if (!rect5.equals(rect3)) {
            rect5.set(rect3);
            zA = true;
        }
        if (zA) {
            requestLayout();
        }
        return true;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new C0068e(-1, -1);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new C0068e(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new C0068e(layoutParams);
    }

    public int getActionBarHideOffset() {
        ActionBarContainer actionBarContainer = this.f894d;
        if (actionBarContainer != null) {
            return -((int) actionBarContainer.getTranslationY());
        }
        return 0;
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.f890A.f2834a;
    }

    public CharSequence getTitle() {
        e();
        return ((v0) this.f895e).f1320a.getTitle();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        c(getContext());
        WeakHashMap weakHashMap = v.l.f2836a;
        requestApplyInsets();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        getPaddingRight();
        int paddingTop = getPaddingTop();
        getPaddingBottom();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                C0068e c0068e = (C0068e) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i7 = ((ViewGroup.MarginLayoutParams) c0068e).leftMargin + paddingLeft;
                int i8 = ((ViewGroup.MarginLayoutParams) c0068e).topMargin + paddingTop;
                childAt.layout(i7, i8, measuredWidth + i7, measuredHeight + i8);
            }
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        e();
        measureChildWithMargins(this.f894d, i2, 0, i3, 0);
        C0068e c0068e = (C0068e) this.f894d.getLayoutParams();
        int measuredHeight = 0;
        int iMax = Math.max(0, this.f894d.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) c0068e).leftMargin + ((ViewGroup.MarginLayoutParams) c0068e).rightMargin);
        int iMax2 = Math.max(0, this.f894d.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) c0068e).topMargin + ((ViewGroup.MarginLayoutParams) c0068e).bottomMargin);
        int iCombineMeasuredStates = View.combineMeasuredStates(0, this.f894d.getMeasuredState());
        WeakHashMap weakHashMap = v.l.f2836a;
        boolean z2 = (getWindowSystemUiVisibility() & 256) != 0;
        if (z2) {
            measuredHeight = this.f891a;
            if (this.f899i && this.f894d.getTabContainer() != null) {
                measuredHeight += this.f891a;
            }
        } else if (this.f894d.getVisibility() != 8) {
            measuredHeight = this.f894d.getMeasuredHeight();
        }
        Rect rect = this.f904n;
        Rect rect2 = this.f906p;
        rect2.set(rect);
        Rect rect3 = this.f908s;
        rect3.set(this.f907q);
        if (this.f898h || z2) {
            rect3.top += measuredHeight;
            rect3.bottom = rect3.bottom;
        } else {
            rect2.top += measuredHeight;
            rect2.bottom = rect2.bottom;
        }
        a(this.f893c, rect2, true);
        Rect rect4 = this.f909t;
        if (!rect4.equals(rect3)) {
            rect4.set(rect3);
            this.f893c.a(rect3);
        }
        measureChildWithMargins(this.f893c, i2, 0, i3, 0);
        C0068e c0068e2 = (C0068e) this.f893c.getLayoutParams();
        int iMax3 = Math.max(iMax, this.f893c.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) c0068e2).leftMargin + ((ViewGroup.MarginLayoutParams) c0068e2).rightMargin);
        int iMax4 = Math.max(iMax2, this.f893c.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) c0068e2).topMargin + ((ViewGroup.MarginLayoutParams) c0068e2).bottomMargin);
        int iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.f893c.getMeasuredState());
        setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + iMax3, getSuggestedMinimumWidth()), i2, iCombineMeasuredStates2), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + iMax4, getSuggestedMinimumHeight()), i3, iCombineMeasuredStates2 << 16));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        if (!this.f900j || !z2) {
            return false;
        }
        this.f911v.fling(0, 0, 0, (int) f3, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (this.f911v.getFinalY() > this.f894d.getHeight()) {
            b();
            this.f915z.run();
        } else {
            b();
            this.f914y.run();
        }
        this.f901k = true;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f2, float f3) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        int i6 = this.f902l + i3;
        this.f902l = i6;
        setActionBarHideOffset(i6);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScrollAccepted(View view, View view2, int i2) {
        androidx.appcompat.app.C c2;
        d.j jVar;
        this.f890A.f2834a = i2;
        this.f902l = getActionBarHideOffset();
        b();
        InterfaceC0067d interfaceC0067d = this.f910u;
        if (interfaceC0067d == null || (jVar = (c2 = (androidx.appcompat.app.C) interfaceC0067d).f509z) == null) {
            return;
        }
        jVar.a();
        c2.f509z = null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i2) {
        if ((i2 & 2) == 0 || this.f894d.getVisibility() != 0) {
            return false;
        }
        return this.f900j;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        if (!this.f900j || this.f901k) {
            return;
        }
        if (this.f902l <= this.f894d.getHeight()) {
            b();
            postDelayed(this.f914y, 600L);
        } else {
            b();
            postDelayed(this.f915z, 600L);
        }
    }

    @Override // android.view.View
    public final void onWindowSystemUiVisibilityChanged(int i2) {
        super.onWindowSystemUiVisibilityChanged(i2);
        e();
        int i3 = this.f903m ^ i2;
        this.f903m = i2;
        boolean z2 = (i2 & 4) == 0;
        boolean z3 = (i2 & 256) != 0;
        InterfaceC0067d interfaceC0067d = this.f910u;
        if (interfaceC0067d != null) {
            ((androidx.appcompat.app.C) interfaceC0067d).f505v = !z3;
            if (z2 || !z3) {
                androidx.appcompat.app.C c2 = (androidx.appcompat.app.C) interfaceC0067d;
                if (c2.f506w) {
                    c2.f506w = false;
                    c2.v0(true);
                }
            } else {
                androidx.appcompat.app.C c3 = (androidx.appcompat.app.C) interfaceC0067d;
                if (!c3.f506w) {
                    c3.f506w = true;
                    c3.v0(true);
                }
            }
        }
        if ((i3 & 256) == 0 || this.f910u == null) {
            return;
        }
        WeakHashMap weakHashMap = v.l.f2836a;
        requestApplyInsets();
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i2) {
        super.onWindowVisibilityChanged(i2);
        this.f892b = i2;
        InterfaceC0067d interfaceC0067d = this.f910u;
        if (interfaceC0067d != null) {
            ((androidx.appcompat.app.C) interfaceC0067d).f504u = i2;
        }
    }

    public void setActionBarHideOffset(int i2) {
        b();
        this.f894d.setTranslationY(-Math.max(0, Math.min(i2, this.f894d.getHeight())));
    }

    public void setActionBarVisibilityCallback(InterfaceC0067d interfaceC0067d) {
        this.f910u = interfaceC0067d;
        if (getWindowToken() != null) {
            ((androidx.appcompat.app.C) this.f910u).f504u = this.f892b;
            int i2 = this.f903m;
            if (i2 != 0) {
                onWindowSystemUiVisibilityChanged(i2);
                WeakHashMap weakHashMap = v.l.f2836a;
                requestApplyInsets();
            }
        }
    }

    public void setHasNonEmbeddedTabs(boolean z2) {
        this.f899i = z2;
    }

    public void setHideOnContentScrollEnabled(boolean z2) {
        if (z2 != this.f900j) {
            this.f900j = z2;
            if (z2) {
                return;
            }
            b();
            setActionBarHideOffset(0);
        }
    }

    public void setIcon(int i2) {
        e();
        v0 v0Var = (v0) this.f895e;
        v0Var.f1324e = i2 != 0 ? AbstractC0122a.a(v0Var.f1320a.getContext(), i2) : null;
        v0Var.c();
    }

    public void setIcon(Drawable drawable) {
        e();
        v0 v0Var = (v0) this.f895e;
        v0Var.f1324e = drawable;
        v0Var.c();
    }

    public void setLogo(int i2) {
        e();
        v0 v0Var = (v0) this.f895e;
        v0Var.f1325f = i2 != 0 ? AbstractC0122a.a(v0Var.f1320a.getContext(), i2) : null;
        v0Var.c();
    }

    public void setOverlayMode(boolean z2) {
        this.f898h = z2;
        this.f897g = z2 && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public void setShowingForActionMode(boolean z2) {
    }

    public void setUiOptions(int i2) {
    }

    @Override // androidx.appcompat.widget.E
    public void setWindowCallback(Window.Callback callback) {
        e();
        ((v0) this.f895e).f1331l = callback;
    }

    @Override // androidx.appcompat.widget.E
    public void setWindowTitle(CharSequence charSequence) {
        e();
        v0 v0Var = (v0) this.f895e;
        if (v0Var.f1327h) {
            return;
        }
        v0Var.f1328i = charSequence;
        if ((v0Var.f1321b & 8) != 0) {
            v0Var.f1320a.setTitle(charSequence);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }
}
