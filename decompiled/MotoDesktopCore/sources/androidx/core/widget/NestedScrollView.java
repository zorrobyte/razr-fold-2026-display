package androidx.core.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import java.util.WeakHashMap;
import v.g;
import v.h;
import v.i;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class NestedScrollView extends FrameLayout implements h, v.e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public long f1410a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Rect f1411b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final OverScroller f1412c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public EdgeEffect f1413d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public EdgeEffect f1414e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1415f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f1416g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f1417h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public View f1418i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f1419j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public VelocityTracker f1420k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public boolean f1421l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public boolean f1422m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final int f1423n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final int f1424o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final int f1425p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public int f1426q;
    public final int[] r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public final int[] f1427s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public int f1428t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public int f1429u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public SavedState f1430v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public final i f1431w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public final g f1432x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public float f1433y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public static final c f1409z = new c();

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public static final int[] f1408A = {R.attr.fillViewport};

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new e();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f1434a;

        public final String toString() {
            return "HorizontalScrollView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " scrollPosition=" + this.f1434a + "}";
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f1434a);
        }
    }

    public NestedScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.f1411b = new Rect();
        this.f1416g = true;
        this.f1417h = false;
        this.f1418i = null;
        this.f1419j = false;
        this.f1422m = true;
        this.f1426q = -1;
        this.r = new int[2];
        this.f1427s = new int[2];
        this.f1412c = new OverScroller(getContext());
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.f1423n = viewConfiguration.getScaledTouchSlop();
        this.f1424o = viewConfiguration.getScaledMinimumFlingVelocity();
        this.f1425p = viewConfiguration.getScaledMaximumFlingVelocity();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f1408A, 0, 0);
        setFillViewport(typedArrayObtainStyledAttributes.getBoolean(0, false));
        typedArrayObtainStyledAttributes.recycle();
        this.f1431w = new i();
        this.f1432x = new g(this);
        setNestedScrollingEnabled(true);
        l.b(this, f1409z);
    }

    private float getVerticalScrollFactorCompat() {
        if (this.f1433y == 0.0f) {
            TypedValue typedValue = new TypedValue();
            Context context = getContext();
            if (!context.getTheme().resolveAttribute(R.attr.listPreferredItemHeight, typedValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.f1433y = typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return this.f1433y;
    }

    public static boolean n(View view, View view2) {
        if (view == view2) {
            return true;
        }
        Object parent = view.getParent();
        return (parent instanceof ViewGroup) && n((View) parent, view2);
    }

    @Override // v.h
    public final void a(View view, int i2, int i3, int i4, int i5, int i6) {
        int scrollY = getScrollY();
        scrollBy(0, i5);
        int scrollY2 = getScrollY() - scrollY;
        this.f1432x.d(0, scrollY2, 0, i5 - scrollY2, null, i6);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i2) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i2);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i2, layoutParams);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    @Override // v.h
    public final boolean b(View view, View view2, int i2, int i3) {
        return (i2 & 2) != 0;
    }

    @Override // v.h
    public final void c(View view, int i2) {
        this.f1431w.f2834a = 0;
        f(i2);
    }

    @Override // android.view.View
    public final int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    @Override // android.view.View
    public final int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override // android.view.View
    public final int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    @Override // android.view.View
    public final void computeScroll() {
        int overScrollMode;
        OverScroller overScroller = this.f1412c;
        if (!overScroller.computeScrollOffset()) {
            if (this.f1432x.f(1)) {
                f(1);
            }
            this.f1429u = 0;
            return;
        }
        overScroller.getCurrX();
        int currY = overScroller.getCurrY();
        int i2 = currY - this.f1429u;
        if (this.f1432x.c(0, i2, this.f1427s, null, 1)) {
            i2 -= this.f1427s[1];
        }
        if (i2 != 0) {
            int scrollRange = getScrollRange();
            int scrollY = getScrollY();
            q(i2, getScrollX(), scrollY, scrollRange);
            int scrollY2 = getScrollY() - scrollY;
            if (!this.f1432x.d(0, scrollY2, 0, i2 - scrollY2, null, 1) && ((overScrollMode = getOverScrollMode()) == 0 || (overScrollMode == 1 && scrollRange > 0))) {
                j();
                if (currY <= 0 && scrollY > 0) {
                    this.f1413d.onAbsorb((int) overScroller.getCurrVelocity());
                } else if (currY >= scrollRange && scrollY < scrollRange) {
                    this.f1414e.onAbsorb((int) overScroller.getCurrVelocity());
                }
            }
        }
        this.f1429u = currY;
        WeakHashMap weakHashMap = l.f2836a;
        postInvalidateOnAnimation();
    }

    @Override // android.view.View
    public final int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override // android.view.View
    public final int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override // android.view.View
    public final int computeVerticalScrollRange() {
        int childCount = getChildCount();
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (childCount == 0) {
            return height;
        }
        View childAt = getChildAt(0);
        int bottom = childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
        int scrollY = getScrollY();
        int iMax = Math.max(0, bottom - height);
        return scrollY < 0 ? bottom - scrollY : scrollY > iMax ? bottom + (scrollY - iMax) : bottom;
    }

    @Override // v.h
    public final void d(View view, int i2, int i3, int[] iArr, int i4) {
        this.f1432x.c(i2, i3, iArr, null, i4);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || k(keyEvent);
    }

    @Override // android.view.View
    public final boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return this.f1432x.a(f2, f3, z2);
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreFling(float f2, float f3) {
        return this.f1432x.b(f2, f3);
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return this.f1432x.c(i2, i3, iArr, iArr2, 0);
    }

    @Override // android.view.View
    public final boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return this.f1432x.d(i2, i3, i4, i5, iArr, 0);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        int paddingLeft;
        super.draw(canvas);
        if (this.f1413d != null) {
            int scrollY = getScrollY();
            int paddingLeft2 = 0;
            if (!this.f1413d.isFinished()) {
                int iSave = canvas.save();
                int width = getWidth();
                int height = getHeight();
                int iMin = Math.min(0, scrollY);
                if (getClipToPadding()) {
                    width -= getPaddingRight() + getPaddingLeft();
                    paddingLeft = getPaddingLeft();
                } else {
                    paddingLeft = 0;
                }
                if (getClipToPadding()) {
                    height -= getPaddingBottom() + getPaddingTop();
                    iMin += getPaddingTop();
                }
                canvas.translate(paddingLeft, iMin);
                this.f1413d.setSize(width, height);
                if (this.f1413d.draw(canvas)) {
                    WeakHashMap weakHashMap = l.f2836a;
                    postInvalidateOnAnimation();
                }
                canvas.restoreToCount(iSave);
            }
            if (this.f1414e.isFinished()) {
                return;
            }
            int iSave2 = canvas.save();
            int width2 = getWidth();
            int height2 = getHeight();
            int iMax = Math.max(getScrollRange(), scrollY) + height2;
            if (getClipToPadding()) {
                width2 -= getPaddingRight() + getPaddingLeft();
                paddingLeft2 = getPaddingLeft();
            }
            if (getClipToPadding()) {
                height2 -= getPaddingBottom() + getPaddingTop();
                iMax -= getPaddingBottom();
            }
            canvas.translate(paddingLeft2 - width2, iMax);
            canvas.rotate(180.0f, width2, 0.0f);
            this.f1414e.setSize(width2, height2);
            if (this.f1414e.draw(canvas)) {
                WeakHashMap weakHashMap2 = l.f2836a;
                postInvalidateOnAnimation();
            }
            canvas.restoreToCount(iSave2);
        }
    }

    @Override // v.h
    public final void e(View view, int i2, int i3) {
        this.f1431w.f2834a = i2;
        t(2, i3);
    }

    @Override // v.e
    public final void f(int i2) {
        this.f1432x.h(i2);
    }

    public final boolean g(int i2) {
        View viewFindFocus = findFocus();
        if (viewFindFocus == this) {
            viewFindFocus = null;
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i2);
        int maxScrollAmount = getMaxScrollAmount();
        if (viewFindNextFocus == null || !o(viewFindNextFocus, maxScrollAmount, getHeight())) {
            if (i2 == 33 && getScrollY() < maxScrollAmount) {
                maxScrollAmount = getScrollY();
            } else if (i2 == 130 && getChildCount() > 0) {
                View childAt = getChildAt(0);
                maxScrollAmount = Math.min((childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin) - ((getHeight() + getScrollY()) - getPaddingBottom()), maxScrollAmount);
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i2 != 130) {
                maxScrollAmount = -maxScrollAmount;
            }
            i(maxScrollAmount);
        } else {
            Rect rect = this.f1411b;
            viewFindNextFocus.getDrawingRect(rect);
            offsetDescendantRectToMyCoords(viewFindNextFocus, rect);
            i(h(rect));
            viewFindNextFocus.requestFocus(i2);
        }
        if (viewFindFocus != null && viewFindFocus.isFocused() && (!o(viewFindFocus, 0, getHeight()))) {
            int descendantFocusability = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability(descendantFocusability);
        }
        return true;
    }

    @Override // android.view.View
    public float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = ((childAt.getBottom() + layoutParams.bottomMargin) - getScrollY()) - (getHeight() - getPaddingBottom());
        if (bottom < verticalFadingEdgeLength) {
            return bottom / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int) (getHeight() * 0.5f);
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.f1431w.f2834a;
    }

    public int getScrollRange() {
        if (getChildCount() <= 0) {
            return 0;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        return Math.max(0, ((childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin) - ((getHeight() - getPaddingTop()) - getPaddingBottom()));
    }

    @Override // android.view.View
    public float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int scrollY = getScrollY();
        if (scrollY < verticalFadingEdgeLength) {
            return scrollY / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    public final int h(Rect rect) {
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i2 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        int i3 = rect.bottom < (childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin ? i2 - verticalFadingEdgeLength : i2;
        int i4 = rect.bottom;
        if (i4 > i3 && rect.top > scrollY) {
            return Math.min(rect.height() > height ? rect.top - scrollY : rect.bottom - i3, (childAt.getBottom() + layoutParams.bottomMargin) - i2);
        }
        if (rect.top >= scrollY || i4 >= i3) {
            return 0;
        }
        return Math.max(rect.height() > height ? 0 - (i3 - rect.bottom) : 0 - (scrollY - rect.top), -getScrollY());
    }

    @Override // android.view.View
    public final boolean hasNestedScrollingParent() {
        return this.f1432x.f(0);
    }

    public final void i(int i2) {
        if (i2 != 0) {
            if (this.f1422m) {
                s(0, i2);
            } else {
                scrollBy(0, i2);
            }
        }
    }

    @Override // android.view.View
    public final boolean isNestedScrollingEnabled() {
        return this.f1432x.f2832d;
    }

    public final void j() {
        if (getOverScrollMode() == 2) {
            this.f1413d = null;
            this.f1414e = null;
        } else if (this.f1413d == null) {
            Context context = getContext();
            this.f1413d = new EdgeEffect(context);
            this.f1414e = new EdgeEffect(context);
        }
    }

    public final boolean k(KeyEvent keyEvent) {
        Rect rect = this.f1411b;
        rect.setEmpty();
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin > (getHeight() - getPaddingTop()) - getPaddingBottom()) {
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                int keyCode = keyEvent.getKeyCode();
                if (keyCode == 19) {
                    return !keyEvent.isAltPressed() ? g(33) : m(33);
                }
                if (keyCode == 20) {
                    return !keyEvent.isAltPressed() ? g(130) : m(130);
                }
                if (keyCode != 62) {
                    return false;
                }
                int i2 = keyEvent.isShiftPressed() ? 33 : 130;
                boolean z2 = i2 == 130;
                int height = getHeight();
                if (z2) {
                    rect.top = getScrollY() + height;
                    int childCount = getChildCount();
                    if (childCount > 0) {
                        View childAt2 = getChildAt(childCount - 1);
                        int paddingBottom = getPaddingBottom() + childAt2.getBottom() + ((FrameLayout.LayoutParams) childAt2.getLayoutParams()).bottomMargin;
                        if (rect.top + height > paddingBottom) {
                            rect.top = paddingBottom - height;
                        }
                    }
                } else {
                    int scrollY = getScrollY() - height;
                    rect.top = scrollY;
                    if (scrollY < 0) {
                        rect.top = 0;
                    }
                }
                int i3 = rect.top;
                int i4 = height + i3;
                rect.bottom = i4;
                r(i2, i3, i4);
                return false;
            }
        }
        if (!isFocused() || keyEvent.getKeyCode() == 4) {
            return false;
        }
        View viewFindFocus = findFocus();
        if (viewFindFocus == this) {
            viewFindFocus = null;
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, 130);
        return (viewFindNextFocus == null || viewFindNextFocus == this || !viewFindNextFocus.requestFocus(130)) ? false : true;
    }

    public final void l(int i2) {
        int scrollY = getScrollY();
        boolean z2 = (scrollY > 0 || i2 > 0) && (scrollY < getScrollRange() || i2 < 0);
        float f2 = i2;
        if (this.f1432x.b(0.0f, f2)) {
            return;
        }
        dispatchNestedFling(0.0f, f2, z2);
        if (getChildCount() > 0) {
            t(2, 1);
            this.f1412c.fling(getScrollX(), getScrollY(), 0, i2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            this.f1429u = getScrollY();
            WeakHashMap weakHashMap = l.f2836a;
            postInvalidateOnAnimation();
        }
    }

    public final boolean m(int i2) {
        int childCount;
        boolean z2 = i2 == 130;
        int height = getHeight();
        Rect rect = this.f1411b;
        rect.top = 0;
        rect.bottom = height;
        if (z2 && (childCount = getChildCount()) > 0) {
            View childAt = getChildAt(childCount - 1);
            int paddingBottom = getPaddingBottom() + childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
            rect.bottom = paddingBottom;
            rect.top = paddingBottom - height;
        }
        return r(i2, rect.top, rect.bottom);
    }

    @Override // android.view.ViewGroup
    public final void measureChild(View view, int i2, int i3) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(FrameLayout.getChildMeasureSpec(i2, getPaddingRight() + getPaddingLeft(), layoutParams.width), View.MeasureSpec.makeMeasureSpec(0, 0));
    }

    @Override // android.view.ViewGroup
    public final void measureChildWithMargins(View view, int i2, int i3, int i4, int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(FrameLayout.getChildMeasureSpec(i2, getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i3, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, 0));
    }

    public final boolean o(View view, int i2, int i3) {
        Rect rect = this.f1411b;
        view.getDrawingRect(rect);
        offsetDescendantRectToMyCoords(view, rect);
        return rect.bottom + i2 >= getScrollY() && rect.top - i2 <= getScrollY() + i3;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.f1417h = false;
    }

    @Override // android.view.View
    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8 && !this.f1419j) {
            float axisValue = motionEvent.getAxisValue(9);
            if (axisValue != 0.0f) {
                int verticalScrollFactorCompat = (int) (axisValue * getVerticalScrollFactorCompat());
                int scrollRange = getScrollRange();
                int scrollY = getScrollY();
                int i2 = scrollY - verticalScrollFactorCompat;
                if (i2 < 0) {
                    scrollRange = 0;
                } else if (i2 <= scrollRange) {
                    scrollRange = i2;
                }
                if (scrollRange != scrollY) {
                    super.scrollTo(getScrollX(), scrollRange);
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x010d  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r14) {
        /*
            Method dump skipped, instruction units count: 283
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.NestedScrollView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int measuredHeight;
        super.onLayout(z2, i2, i3, i4, i5);
        int i6 = 0;
        this.f1416g = false;
        View view = this.f1418i;
        if (view != null && n(view, this)) {
            View view2 = this.f1418i;
            Rect rect = this.f1411b;
            view2.getDrawingRect(rect);
            offsetDescendantRectToMyCoords(view2, rect);
            int iH = h(rect);
            if (iH != 0) {
                scrollBy(0, iH);
            }
        }
        this.f1418i = null;
        if (!this.f1417h) {
            if (this.f1430v != null) {
                scrollTo(getScrollX(), this.f1430v.f1434a);
                this.f1430v = null;
            }
            if (getChildCount() > 0) {
                View childAt = getChildAt(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                measuredHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            } else {
                measuredHeight = 0;
            }
            int paddingTop = ((i5 - i3) - getPaddingTop()) - getPaddingBottom();
            int scrollY = getScrollY();
            if (paddingTop < measuredHeight && scrollY >= 0) {
                i6 = paddingTop + scrollY > measuredHeight ? measuredHeight - paddingTop : scrollY;
            }
            if (i6 != scrollY) {
                scrollTo(getScrollX(), i6);
            }
        }
        scrollTo(getScrollX(), getScrollY());
        this.f1417h = true;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.f1421l && View.MeasureSpec.getMode(i3) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int measuredHeight2 = (((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - layoutParams.topMargin) - layoutParams.bottomMargin;
            if (measuredHeight < measuredHeight2) {
                childAt.measure(FrameLayout.getChildMeasureSpec(i2, getPaddingRight() + getPaddingLeft() + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width), View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824));
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        if (z2) {
            return false;
        }
        l((int) f3);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f2, float f3) {
        return dispatchNestedPreFling(f2, f3);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        this.f1432x.c(i2, i3, iArr, null, 0);
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
    public final void onOverScrolled(int i2, int i3, boolean z2, boolean z3) {
        super.scrollTo(i2, i3);
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i2, Rect rect) {
        if (i2 == 2) {
            i2 = 130;
        } else if (i2 == 1) {
            i2 = 33;
        }
        View viewFindNextFocus = rect == null ? FocusFinder.getInstance().findNextFocus(this, null, i2) : FocusFinder.getInstance().findNextFocusFromRect(this, rect, i2);
        if (viewFindNextFocus == null || (!o(viewFindNextFocus, 0, getHeight()))) {
            return false;
        }
        return viewFindNextFocus.requestFocus(i2, rect);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.f1430v = savedState;
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f1434a = getScrollY();
        return savedState;
    }

    @Override // android.view.View
    public final void onScrollChanged(int i2, int i3, int i4, int i5) {
        super.onScrollChanged(i2, i3, i4, i5);
    }

    @Override // android.view.View
    public final void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        View viewFindFocus = findFocus();
        if (viewFindFocus == null || this == viewFindFocus || !o(viewFindFocus, 0, i5)) {
            return;
        }
        Rect rect = this.f1411b;
        viewFindFocus.getDrawingRect(rect);
        offsetDescendantRectToMyCoords(viewFindFocus, rect);
        i(h(rect));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i2) {
        return b(view, view2, i2, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        c(view, 0);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        ViewParent parent;
        OverScroller overScroller = this.f1412c;
        int i2 = this.f1423n;
        if (this.f1420k == null) {
            this.f1420k = VelocityTracker.obtain();
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.f1428t = 0;
        }
        motionEventObtain.offsetLocation(0.0f, this.f1428t);
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                VelocityTracker velocityTracker = this.f1420k;
                velocityTracker.computeCurrentVelocity(1000, this.f1425p);
                int yVelocity = (int) velocityTracker.getYVelocity(this.f1426q);
                if (Math.abs(yVelocity) > this.f1424o) {
                    l(-yVelocity);
                } else if (this.f1412c.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    WeakHashMap weakHashMap = l.f2836a;
                    postInvalidateOnAnimation();
                }
                this.f1426q = -1;
                this.f1419j = false;
                VelocityTracker velocityTracker2 = this.f1420k;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                    this.f1420k = null;
                }
                f(0);
                EdgeEffect edgeEffect = this.f1413d;
                if (edgeEffect != null) {
                    edgeEffect.onRelease();
                    this.f1414e.onRelease();
                }
            } else if (actionMasked == 2) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.f1426q);
                if (iFindPointerIndex == -1) {
                    Log.e("NestedScrollView", "Invalid pointerId=" + this.f1426q + " in onTouchEvent");
                } else {
                    int y2 = (int) motionEvent.getY(iFindPointerIndex);
                    int i3 = this.f1415f - y2;
                    boolean zC = this.f1432x.c(0, i3, this.f1427s, this.r, 0);
                    int[] iArr = this.r;
                    if (zC) {
                        i3 -= this.f1427s[1];
                        motionEventObtain.offsetLocation(0.0f, iArr[1]);
                        this.f1428t += iArr[1];
                    }
                    if (!this.f1419j && Math.abs(i3) > i2) {
                        ViewParent parent2 = getParent();
                        if (parent2 != null) {
                            parent2.requestDisallowInterceptTouchEvent(true);
                        }
                        this.f1419j = true;
                        i3 = i3 > 0 ? i3 - i2 : i3 + i2;
                    }
                    if (this.f1419j) {
                        this.f1415f = y2 - iArr[1];
                        int scrollY = getScrollY();
                        int scrollRange = getScrollRange();
                        int overScrollMode = getOverScrollMode();
                        boolean z2 = overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0);
                        if (q(i3, 0, getScrollY(), scrollRange) && !this.f1432x.f(0)) {
                            this.f1420k.clear();
                        }
                        int scrollY2 = getScrollY() - scrollY;
                        if (this.f1432x.d(0, scrollY2, 0, i3 - scrollY2, this.r, 0)) {
                            int i4 = this.f1415f;
                            int i5 = iArr[1];
                            this.f1415f = i4 - i5;
                            motionEventObtain.offsetLocation(0.0f, i5);
                            this.f1428t += iArr[1];
                        } else if (z2) {
                            j();
                            int i6 = scrollY + i3;
                            if (i6 < 0) {
                                this.f1413d.onPull(i3 / getHeight(), motionEvent.getX(iFindPointerIndex) / getWidth());
                                if (!this.f1414e.isFinished()) {
                                    this.f1414e.onRelease();
                                }
                            } else if (i6 > scrollRange) {
                                this.f1414e.onPull(i3 / getHeight(), 1.0f - (motionEvent.getX(iFindPointerIndex) / getWidth()));
                                if (!this.f1413d.isFinished()) {
                                    this.f1413d.onRelease();
                                }
                            }
                            EdgeEffect edgeEffect2 = this.f1413d;
                            if (edgeEffect2 != null && (!edgeEffect2.isFinished() || !this.f1414e.isFinished())) {
                                WeakHashMap weakHashMap2 = l.f2836a;
                                postInvalidateOnAnimation();
                            }
                        }
                    }
                }
            } else if (actionMasked == 3) {
                if (this.f1419j && getChildCount() > 0 && this.f1412c.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    WeakHashMap weakHashMap3 = l.f2836a;
                    postInvalidateOnAnimation();
                }
                this.f1426q = -1;
                this.f1419j = false;
                VelocityTracker velocityTracker3 = this.f1420k;
                if (velocityTracker3 != null) {
                    velocityTracker3.recycle();
                    this.f1420k = null;
                }
                f(0);
                EdgeEffect edgeEffect3 = this.f1413d;
                if (edgeEffect3 != null) {
                    edgeEffect3.onRelease();
                    this.f1414e.onRelease();
                }
            } else if (actionMasked == 5) {
                int actionIndex = motionEvent.getActionIndex();
                this.f1415f = (int) motionEvent.getY(actionIndex);
                this.f1426q = motionEvent.getPointerId(actionIndex);
            } else if (actionMasked == 6) {
                p(motionEvent);
                this.f1415f = (int) motionEvent.getY(motionEvent.findPointerIndex(this.f1426q));
            }
        } else {
            if (getChildCount() == 0) {
                return false;
            }
            boolean z3 = !overScroller.isFinished();
            this.f1419j = z3;
            if (z3 && (parent = getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            if (!overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
            this.f1415f = (int) motionEvent.getY();
            this.f1426q = motionEvent.getPointerId(0);
            t(2, 0);
        }
        VelocityTracker velocityTracker4 = this.f1420k;
        if (velocityTracker4 != null) {
            velocityTracker4.addMovement(motionEventObtain);
        }
        motionEventObtain.recycle();
        return true;
    }

    public final void p(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.f1426q) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.f1415f = (int) motionEvent.getY(i2);
            this.f1426q = motionEvent.getPointerId(i2);
            VelocityTracker velocityTracker = this.f1420k;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    public final boolean q(int i2, int i3, int i4, int i5) {
        boolean z2;
        boolean z3;
        getOverScrollMode();
        super.computeHorizontalScrollRange();
        super.computeHorizontalScrollExtent();
        computeVerticalScrollRange();
        super.computeVerticalScrollExtent();
        int i6 = i4 + i2;
        if (i3 <= 0 && i3 >= 0) {
            z2 = false;
        } else {
            i3 = 0;
            z2 = true;
        }
        if (i6 > i5) {
            z3 = true;
        } else if (i6 < 0) {
            i5 = 0;
            z3 = true;
        } else {
            i5 = i6;
            z3 = false;
        }
        if (z3 && !this.f1432x.f(1)) {
            this.f1412c.springBack(i3, i5, 0, 0, 0, getScrollRange());
        }
        super.scrollTo(i3, i5);
        return z2 || z3;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean r(int r18, int r19, int r20) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            int r4 = r17.getHeight()
            int r5 = r17.getScrollY()
            int r4 = r4 + r5
            r6 = 33
            if (r1 != r6) goto L17
            r6 = 1
            goto L18
        L17:
            r6 = 0
        L18:
            r9 = 2
            java.util.ArrayList r9 = r0.getFocusables(r9)
            int r10 = r9.size()
            r11 = 0
            r12 = 0
            r13 = 0
        L24:
            if (r12 >= r10) goto L6c
            java.lang.Object r14 = r9.get(r12)
            android.view.View r14 = (android.view.View) r14
            int r15 = r14.getTop()
            int r7 = r14.getBottom()
            if (r2 >= r7) goto L69
            if (r15 >= r3) goto L69
            if (r2 >= r15) goto L3f
            if (r7 >= r3) goto L3f
            r16 = 1
            goto L41
        L3f:
            r16 = 0
        L41:
            if (r11 != 0) goto L47
            r11 = r14
            r13 = r16
            goto L69
        L47:
            if (r6 == 0) goto L4f
            int r8 = r11.getTop()
            if (r15 < r8) goto L57
        L4f:
            if (r6 != 0) goto L59
            int r8 = r11.getBottom()
            if (r7 <= r8) goto L59
        L57:
            r7 = 1
            goto L5a
        L59:
            r7 = 0
        L5a:
            if (r13 == 0) goto L61
            if (r16 == 0) goto L69
            if (r7 == 0) goto L69
            goto L68
        L61:
            if (r16 == 0) goto L66
            r11 = r14
            r13 = 1
            goto L69
        L66:
            if (r7 == 0) goto L69
        L68:
            r11 = r14
        L69:
            int r12 = r12 + 1
            goto L24
        L6c:
            if (r11 != 0) goto L6f
            r11 = r0
        L6f:
            if (r2 < r5) goto L75
            if (r3 > r4) goto L75
            r7 = 0
            goto L7f
        L75:
            if (r6 == 0) goto L79
            int r2 = r2 - r5
            goto L7b
        L79:
            int r2 = r3 - r4
        L7b:
            r0.i(r2)
            r7 = 1
        L7f:
            android.view.View r0 = r17.findFocus()
            if (r11 == r0) goto L88
            r11.requestFocus(r1)
        L88:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.NestedScrollView.r(int, int, int):boolean");
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        if (this.f1416g) {
            this.f1418i = view2;
        } else {
            Rect rect = this.f1411b;
            view2.getDrawingRect(rect);
            offsetDescendantRectToMyCoords(view2, rect);
            int iH = h(rect);
            if (iH != 0) {
                scrollBy(0, iH);
            }
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        int iH = h(rect);
        boolean z3 = iH != 0;
        if (z3) {
            if (z2) {
                scrollBy(0, iH);
            } else {
                s(0, iH);
            }
        }
        return z3;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z2) {
        VelocityTracker velocityTracker;
        if (z2 && (velocityTracker = this.f1420k) != null) {
            velocityTracker.recycle();
            this.f1420k = null;
        }
        super.requestDisallowInterceptTouchEvent(z2);
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        this.f1416g = true;
        super.requestLayout();
    }

    public final void s(int i2, int i3) {
        OverScroller overScroller = this.f1412c;
        if (getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.f1410a > 250) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int height = childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int scrollY = getScrollY();
            int iMax = Math.max(0, Math.min(i3 + scrollY, Math.max(0, height - height2))) - scrollY;
            this.f1429u = getScrollY();
            overScroller.startScroll(getScrollX(), scrollY, 0, iMax);
            WeakHashMap weakHashMap = l.f2836a;
            postInvalidateOnAnimation();
        } else {
            if (!overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
            scrollBy(i2, i3);
        }
        this.f1410a = AnimationUtils.currentAnimationTimeMillis();
    }

    @Override // android.view.View
    public final void scrollTo(int i2, int i3) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
            int width2 = childAt.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int height2 = childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (width >= width2 || i2 < 0) {
                i2 = 0;
            } else if (width + i2 > width2) {
                i2 = width2 - width;
            }
            if (height >= height2 || i3 < 0) {
                i3 = 0;
            } else if (height + i3 > height2) {
                i3 = height2 - height;
            }
            if (i2 == getScrollX() && i3 == getScrollY()) {
                return;
            }
            super.scrollTo(i2, i3);
        }
    }

    public void setFillViewport(boolean z2) {
        if (z2 != this.f1421l) {
            this.f1421l = z2;
            requestLayout();
        }
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z2) {
        g gVar = this.f1432x;
        if (gVar.f2832d) {
            WeakHashMap weakHashMap = l.f2836a;
            gVar.f2831c.stopNestedScroll();
        }
        gVar.f2832d = z2;
    }

    public void setOnScrollChangeListener(d dVar) {
    }

    public void setSmoothScrollingEnabled(boolean z2) {
        this.f1422m = z2;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    @Override // android.view.View
    public final boolean startNestedScroll(int i2) {
        return this.f1432x.g(i2, 0);
    }

    @Override // android.view.View
    public final void stopNestedScroll() {
        f(0);
    }

    public final void t(int i2, int i3) {
        this.f1432x.g(2, i3);
    }
}
