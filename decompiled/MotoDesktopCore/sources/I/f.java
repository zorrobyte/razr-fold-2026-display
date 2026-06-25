package I;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public abstract class f extends h {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public e f166c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public OverScroller f167d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f168e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f169f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f170g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f171h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public VelocityTracker f172i;

    /* JADX WARN: Removed duplicated region for block: B:27:0x0051  */
    @Override // k.AbstractC0143b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean e(androidx.coordinatorlayout.widget.CoordinatorLayout r5, android.view.View r6, android.view.MotionEvent r7) {
        /*
            r4 = this;
            int r0 = r4.f171h
            if (r0 >= 0) goto L12
            android.content.Context r0 = r5.getContext()
            android.view.ViewConfiguration r0 = android.view.ViewConfiguration.get(r0)
            int r0 = r0.getScaledTouchSlop()
            r4.f171h = r0
        L12:
            int r0 = r7.getAction()
            r1 = 2
            r2 = 1
            if (r0 != r1) goto L1f
            boolean r0 = r4.f168e
            if (r0 == 0) goto L1f
            return r2
        L1f:
            int r0 = r7.getActionMasked()
            r3 = 0
            if (r0 == 0) goto L60
            r5 = -1
            if (r0 == r2) goto L51
            if (r0 == r1) goto L2f
            r6 = 3
            if (r0 == r6) goto L51
            goto L8a
        L2f:
            int r6 = r4.f169f
            if (r6 != r5) goto L34
            goto L8a
        L34:
            int r6 = r7.findPointerIndex(r6)
            if (r6 != r5) goto L3b
            goto L8a
        L3b:
            float r5 = r7.getY(r6)
            int r5 = (int) r5
            int r6 = r4.f170g
            int r6 = r5 - r6
            int r6 = java.lang.Math.abs(r6)
            int r0 = r4.f171h
            if (r6 <= r0) goto L8a
            r4.f168e = r2
            r4.f170g = r5
            goto L8a
        L51:
            r4.f168e = r3
            r4.f169f = r5
            android.view.VelocityTracker r5 = r4.f172i
            if (r5 == 0) goto L8a
            r5.recycle()
            r5 = 0
            r4.f172i = r5
            goto L8a
        L60:
            r4.f168e = r3
            float r0 = r7.getX()
            int r0 = (int) r0
            float r1 = r7.getY()
            int r1 = (int) r1
            boolean r2 = r4.u(r6)
            if (r2 == 0) goto L8a
            boolean r5 = r5.n(r6, r0, r1)
            if (r5 == 0) goto L8a
            r4.f170g = r1
            int r5 = r7.getPointerId(r3)
            r4.f169f = r5
            android.view.VelocityTracker r5 = r4.f172i
            if (r5 != 0) goto L8a
            android.view.VelocityTracker r5 = android.view.VelocityTracker.obtain()
            r4.f172i = r5
        L8a:
            android.view.VelocityTracker r5 = r4.f172i
            if (r5 == 0) goto L91
            r5.addMovement(r7)
        L91:
            boolean r4 = r4.f168e
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: I.f.e(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    @Override // k.AbstractC0143b
    public final boolean r(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (this.f171h < 0) {
            this.f171h = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                VelocityTracker velocityTracker = this.f172i;
                if (velocityTracker != null) {
                    velocityTracker.addMovement(motionEvent);
                    this.f172i.computeCurrentVelocity(1000);
                    float yVelocity = this.f172i.getYVelocity(this.f169f);
                    AppBarLayout appBarLayout = (AppBarLayout) view;
                    int i2 = -appBarLayout.getTotalScrollRange();
                    Runnable runnable = this.f166c;
                    if (runnable != null) {
                        view.removeCallbacks(runnable);
                        this.f166c = null;
                    }
                    if (this.f167d == null) {
                        this.f167d = new OverScroller(view.getContext());
                    }
                    this.f167d.fling(0, s(), 0, Math.round(yVelocity), 0, 0, i2, 0);
                    if (this.f167d.computeScrollOffset()) {
                        e eVar = new e(this, coordinatorLayout, view);
                        this.f166c = eVar;
                        WeakHashMap weakHashMap = l.f2836a;
                        view.postOnAnimation(eVar);
                    } else {
                        ((AppBarLayout.BaseBehavior) this).y(coordinatorLayout, appBarLayout);
                    }
                }
            } else if (actionMasked == 2) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.f169f);
                if (iFindPointerIndex == -1) {
                    return false;
                }
                int y2 = (int) motionEvent.getY(iFindPointerIndex);
                int i3 = this.f170g - y2;
                if (!this.f168e) {
                    int iAbs = Math.abs(i3);
                    int i4 = this.f171h;
                    if (iAbs > i4) {
                        this.f168e = true;
                        i3 = i3 > 0 ? i3 - i4 : i3 + i4;
                    }
                }
                if (this.f168e) {
                    this.f170g = y2;
                    w(coordinatorLayout, view, v() - i3, -((AppBarLayout) view).getDownNestedScrollRange(), 0);
                }
            } else if (actionMasked == 3) {
            }
            this.f168e = false;
            this.f169f = -1;
            VelocityTracker velocityTracker2 = this.f172i;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
                this.f172i = null;
            }
        } else {
            int x2 = (int) motionEvent.getX();
            int y3 = (int) motionEvent.getY();
            if (!coordinatorLayout.n(view, x2, y3) || !u(view)) {
                return false;
            }
            this.f170g = y3;
            this.f169f = motionEvent.getPointerId(0);
            if (this.f172i == null) {
                this.f172i = VelocityTracker.obtain();
            }
        }
        VelocityTracker velocityTracker3 = this.f172i;
        if (velocityTracker3 != null) {
            velocityTracker3.addMovement(motionEvent);
        }
        return true;
    }

    public abstract boolean u(View view);

    public abstract int v();

    public abstract int w(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, int i4);
}
