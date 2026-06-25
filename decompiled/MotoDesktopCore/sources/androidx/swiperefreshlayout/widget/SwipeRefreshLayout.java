package androidx.swiperefreshlayout.widget;

import B.a;
import B.b;
import B.c;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.WeakHashMap;
import v.f;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class SwipeRefreshLayout extends ViewGroup implements f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public View f1936a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f1937b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public float f1938c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public float f1939d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public float f1940e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f1941f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f1942g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f1943h;

    private void setColorViewAlpha(int i2) {
        throw null;
    }

    public final void a() {
        if (this.f1936a == null) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                if (!childAt.equals(null)) {
                    this.f1936a = childAt;
                    return;
                }
            }
        }
    }

    @Override // android.view.View
    public final boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        throw null;
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreFling(float f2, float f3) {
        throw null;
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        throw null;
    }

    @Override // android.view.View
    public final boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        throw null;
    }

    @Override // android.view.ViewGroup
    public final int getChildDrawingOrder(int i2, int i3) {
        if (i3 == i2 - 1) {
            return 0;
        }
        return i3 >= 0 ? i3 + 1 : i3;
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        throw null;
    }

    public int getProgressCircleDiameter() {
        return this.f1943h;
    }

    public int getProgressViewEndOffset() {
        return 0;
    }

    public int getProgressViewStartOffset() {
        return 0;
    }

    @Override // android.view.View
    public final boolean hasNestedScrollingParent() {
        throw null;
    }

    @Override // android.view.View
    public final boolean isNestedScrollingEnabled() {
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0075  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            r6.a()
            int r0 = r7.getActionMasked()
            boolean r1 = r6.isEnabled()
            r2 = 0
            if (r1 == 0) goto L7d
            android.view.View r1 = r6.f1936a
            boolean r3 = r1 instanceof android.widget.ListView
            r4 = -1
            if (r3 == 0) goto L1c
            android.widget.ListView r1 = (android.widget.ListView) r1
            boolean r1 = r1.canScrollList(r4)
            goto L20
        L1c:
            boolean r1 = r1.canScrollVertically(r4)
        L20:
            if (r1 != 0) goto L7d
            boolean r1 = r6.f1937b
            if (r1 != 0) goto L7d
            r1 = 0
            if (r0 == 0) goto L7c
            r3 = 1
            if (r0 == r3) goto L75
            r5 = 2
            if (r0 == r5) goto L4c
            r1 = 3
            if (r0 == r1) goto L75
            r1 = 6
            if (r0 == r1) goto L36
            goto L79
        L36:
            int r0 = r7.getActionIndex()
            int r1 = r7.getPointerId(r0)
            int r4 = r6.f1942g
            if (r1 != r4) goto L79
            if (r0 != 0) goto L45
            r2 = r3
        L45:
            int r7 = r7.getPointerId(r2)
            r6.f1942g = r7
            goto L79
        L4c:
            int r0 = r6.f1942g
            if (r0 != r4) goto L58
            java.lang.String r6 = "SwipeRefreshLayout"
            java.lang.String r7 = "Got ACTION_MOVE event but don't have an active pointer id."
            android.util.Log.e(r6, r7)
            return r2
        L58:
            int r0 = r7.findPointerIndex(r0)
            if (r0 >= 0) goto L5f
            return r2
        L5f:
            float r7 = r7.getY(r0)
            r0 = 0
            float r7 = r7 - r0
            float r2 = (float) r2
            int r7 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r7 <= 0) goto L79
            boolean r7 = r6.f1941f
            if (r7 == 0) goto L6f
            goto L79
        L6f:
            float r0 = r0 + r2
            r6.f1940e = r0
            r6.f1941f = r3
            throw r1
        L75:
            r6.f1941f = r2
            r6.f1942g = r4
        L79:
            boolean r6 = r6.f1941f
            return r6
        L7c:
            throw r1
        L7d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.swiperefreshlayout.widget.SwipeRefreshLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (this.f1936a == null) {
            a();
        }
        View view = this.f1936a;
        if (view == null) {
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        view.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop);
        throw null;
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.f1936a == null) {
            a();
        }
        View view = this.f1936a;
        if (view == null) {
            return;
        }
        view.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
        View.MeasureSpec.makeMeasureSpec(this.f1943h, 1073741824);
        View.MeasureSpec.makeMeasureSpec(this.f1943h, 1073741824);
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f2, float f3) {
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        if (i3 > 0) {
            float f2 = this.f1939d;
            if (f2 > 0.0f) {
                float f3 = i3;
                if (f3 > f2) {
                    iArr[1] = i3 - ((int) f2);
                    this.f1939d = 0.0f;
                    throw null;
                }
                this.f1939d = f2 - f3;
                iArr[1] = i3;
                throw null;
            }
        }
        int i4 = iArr[0];
        int i5 = iArr[1];
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScrollAccepted(View view, View view2, int i2) {
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i2) {
        return (!isEnabled() || this.f1937b || (i2 & 2) == 0) ? false : true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        throw null;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (isEnabled()) {
            View view = this.f1936a;
            if (!(view instanceof ListView ? ((ListView) view).canScrollList(-1) : view.canScrollVertically(-1)) && !this.f1937b) {
                if (actionMasked == 0) {
                    this.f1942g = motionEvent.getPointerId(0);
                    this.f1941f = false;
                } else {
                    if (actionMasked == 1) {
                        int iFindPointerIndex = motionEvent.findPointerIndex(this.f1942g);
                        if (iFindPointerIndex < 0) {
                            Log.e("SwipeRefreshLayout", "Got ACTION_UP event but don't have an active pointer id.");
                            return false;
                        }
                        if (this.f1941f) {
                            float y2 = (motionEvent.getY(iFindPointerIndex) - this.f1940e) * 0.5f;
                            this.f1941f = false;
                            if (y2 <= this.f1938c) {
                                this.f1937b = false;
                                throw null;
                            }
                            if (!this.f1937b) {
                                a();
                                this.f1937b = true;
                                throw null;
                            }
                        }
                        this.f1942g = -1;
                        return false;
                    }
                    if (actionMasked == 2) {
                        int iFindPointerIndex2 = motionEvent.findPointerIndex(this.f1942g);
                        if (iFindPointerIndex2 < 0) {
                            Log.e("SwipeRefreshLayout", "Got ACTION_MOVE event but have an invalid active pointer id.");
                            return false;
                        }
                        float y3 = motionEvent.getY(iFindPointerIndex2);
                        float f2 = 0;
                        if (y3 - 0.0f > f2 && !this.f1941f) {
                            this.f1940e = 0.0f + f2;
                            this.f1941f = true;
                            throw null;
                        }
                        if (this.f1941f) {
                            if ((y3 - this.f1940e) * 0.5f <= 0.0f) {
                                return false;
                            }
                            throw null;
                        }
                    } else {
                        if (actionMasked == 3) {
                            return false;
                        }
                        if (actionMasked == 5) {
                            int actionIndex = motionEvent.getActionIndex();
                            if (actionIndex < 0) {
                                Log.e("SwipeRefreshLayout", "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                                return false;
                            }
                            this.f1942g = motionEvent.getPointerId(actionIndex);
                        } else if (actionMasked == 6) {
                            int actionIndex2 = motionEvent.getActionIndex();
                            if (motionEvent.getPointerId(actionIndex2) == this.f1942g) {
                                this.f1942g = motionEvent.getPointerId(actionIndex2 == 0 ? 1 : 0);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z2) {
        View view = this.f1936a;
        if (view != null) {
            WeakHashMap weakHashMap = l.f2836a;
            if (!view.isNestedScrollingEnabled()) {
                return;
            }
        }
        super.requestDisallowInterceptTouchEvent(z2);
    }

    public void setAnimationProgress(float f2) {
        throw null;
    }

    @Deprecated
    public void setColorScheme(int... iArr) {
        setColorSchemeResources(iArr);
    }

    public void setColorSchemeColors(int... iArr) {
        a();
        throw null;
    }

    public void setColorSchemeResources(int... iArr) {
        Context context = getContext();
        int[] iArr2 = new int[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr2[i2] = context.getColor(iArr[i2]);
        }
        setColorSchemeColors(iArr2);
    }

    public void setDistanceToTriggerSync(int i2) {
        this.f1938c = i2;
    }

    @Override // android.view.View
    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        if (!z2) {
            throw null;
        }
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z2) {
        throw null;
    }

    public void setOnChildScrollUpCallback(b bVar) {
    }

    public void setOnRefreshListener(c cVar) {
    }

    @Deprecated
    public void setProgressBackgroundColor(int i2) {
        setProgressBackgroundColorSchemeResource(i2);
    }

    public void setProgressBackgroundColorSchemeColor(int i2) {
        throw null;
    }

    public void setProgressBackgroundColorSchemeResource(int i2) {
        setProgressBackgroundColorSchemeColor(getContext().getColor(i2));
    }

    public void setRefreshing(boolean z2) {
        if (z2 && this.f1937b != z2) {
            this.f1937b = z2;
            setTargetOffsetTopAndBottom(0);
            throw null;
        }
        if (this.f1937b != z2) {
            a();
            this.f1937b = z2;
            if (z2) {
                throw null;
            }
            new a(this).setDuration(150L);
            throw null;
        }
    }

    public void setSize(int i2) {
        if (i2 == 0 || i2 == 1) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (i2 == 0) {
                this.f1943h = (int) (displayMetrics.density * 56.0f);
            } else {
                this.f1943h = (int) (displayMetrics.density * 40.0f);
            }
            throw null;
        }
    }

    public void setSlingshotDistance(int i2) {
    }

    public void setTargetOffsetTopAndBottom(int i2) {
        throw null;
    }

    @Override // android.view.View
    public final boolean startNestedScroll(int i2) {
        throw null;
    }

    @Override // android.view.View
    public final void stopNestedScroll() {
        throw null;
    }
}
