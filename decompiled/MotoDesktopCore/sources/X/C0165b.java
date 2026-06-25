package x;

import F.d;
import F.e;
import Y.r;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.OverScroller;
import java.util.Arrays;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: renamed from: x.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0165b {

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public static final d f2842v = new d(1);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2843a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final int f2844b;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public float[] f2846d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public float[] f2847e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public float[] f2848f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public float[] f2849g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int[] f2850h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int[] f2851i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public int[] f2852j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public int f2853k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public VelocityTracker f2854l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final float f2855m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final float f2856n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final int f2857o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final OverScroller f2858p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final r f2859q;
    public View r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public boolean f2860s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public final ViewGroup f2861t;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2845c = -1;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public final e f2862u = new e(13, this);

    public C0165b(Context context, ViewGroup viewGroup, r rVar) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        }
        if (rVar == null) {
            throw new IllegalArgumentException("Callback may not be null");
        }
        this.f2861t = viewGroup;
        this.f2859q = rVar;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.f2857o = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
        this.f2844b = viewConfiguration.getScaledTouchSlop();
        this.f2855m = viewConfiguration.getScaledMaximumFlingVelocity();
        this.f2856n = viewConfiguration.getScaledMinimumFlingVelocity();
        this.f2858p = new OverScroller(context, f2842v);
    }

    public final void a() {
        this.f2845c = -1;
        float[] fArr = this.f2846d;
        if (fArr != null) {
            Arrays.fill(fArr, 0.0f);
            Arrays.fill(this.f2847e, 0.0f);
            Arrays.fill(this.f2848f, 0.0f);
            Arrays.fill(this.f2849g, 0.0f);
            Arrays.fill(this.f2850h, 0);
            Arrays.fill(this.f2851i, 0);
            Arrays.fill(this.f2852j, 0);
            this.f2853k = 0;
        }
        VelocityTracker velocityTracker = this.f2854l;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.f2854l = null;
        }
    }

    public final void b(View view, int i2) {
        ViewParent parent = view.getParent();
        ViewGroup viewGroup = this.f2861t;
        if (parent != viewGroup) {
            throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + viewGroup + ")");
        }
        this.r = view;
        this.f2845c = i2;
        this.f2859q.a0(view, i2);
        p(1);
    }

    public final boolean c(float f2, float f3, int i2, int i3) {
        Math.abs(f2);
        Math.abs(f3);
        int i4 = this.f2850h[i2];
        return false;
    }

    public final boolean d(View view, float f2, float f3) {
        if (view == null) {
            return false;
        }
        r rVar = this.f2859q;
        boolean z2 = rVar.I(view) > 0;
        boolean z3 = rVar.J() > 0;
        if (!z2 || !z3) {
            return z2 ? Math.abs(f2) > ((float) this.f2844b) : z3 && Math.abs(f3) > ((float) this.f2844b);
        }
        float f4 = (f3 * f3) + (f2 * f2);
        int i2 = this.f2844b;
        return f4 > ((float) (i2 * i2));
    }

    public final void e(int i2) {
        float[] fArr = this.f2846d;
        if (fArr != null) {
            int i3 = this.f2853k;
            int i4 = 1 << i2;
            if ((i3 & i4) != 0) {
                fArr[i2] = 0.0f;
                this.f2847e[i2] = 0.0f;
                this.f2848f[i2] = 0.0f;
                this.f2849g[i2] = 0.0f;
                this.f2850h[i2] = 0;
                this.f2851i[i2] = 0;
                this.f2852j[i2] = 0;
                this.f2853k = (~i4) & i3;
            }
        }
    }

    public final int f(int i2, int i3, int i4) {
        if (i2 == 0) {
            return 0;
        }
        float width = this.f2861t.getWidth() / 2;
        float fSin = (((float) Math.sin((Math.min(1.0f, Math.abs(i2) / r3) - 0.5f) * 0.47123894f)) * width) + width;
        int iAbs = Math.abs(i3);
        return Math.min(iAbs > 0 ? Math.round(Math.abs(fSin / iAbs) * 1000.0f) * 4 : (int) (((Math.abs(i2) / i4) + 1.0f) * 256.0f), 600);
    }

    public final boolean g() {
        if (this.f2843a == 2) {
            OverScroller overScroller = this.f2858p;
            boolean zComputeScrollOffset = overScroller.computeScrollOffset();
            int currX = overScroller.getCurrX();
            int currY = overScroller.getCurrY();
            int left = currX - this.r.getLeft();
            int top = currY - this.r.getTop();
            if (left != 0) {
                View view = this.r;
                WeakHashMap weakHashMap = l.f2836a;
                view.offsetLeftAndRight(left);
            }
            if (top != 0) {
                View view2 = this.r;
                WeakHashMap weakHashMap2 = l.f2836a;
                view2.offsetTopAndBottom(top);
            }
            if (left != 0 || top != 0) {
                this.f2859q.c0(this.r, currX, currY);
            }
            if (zComputeScrollOffset && currX == overScroller.getFinalX() && currY == overScroller.getFinalY()) {
                overScroller.abortAnimation();
                zComputeScrollOffset = false;
            }
            if (!zComputeScrollOffset) {
                this.f2861t.post(this.f2862u);
            }
        }
        return this.f2843a == 2;
    }

    public final View h(int i2, int i3) {
        ViewGroup viewGroup = this.f2861t;
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            this.f2859q.getClass();
            View childAt = viewGroup.getChildAt(childCount);
            if (i2 >= childAt.getLeft() && i2 < childAt.getRight() && i3 >= childAt.getTop() && i3 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    public final boolean i(int i2, int i3, int i4, int i5) {
        float f2;
        float f3;
        float f4;
        float f5;
        int left = this.r.getLeft();
        int top = this.r.getTop();
        int i6 = i2 - left;
        int i7 = i3 - top;
        OverScroller overScroller = this.f2858p;
        int i8 = 0;
        if (i6 == 0 && i7 == 0) {
            overScroller.abortAnimation();
            p(0);
            return false;
        }
        View view = this.r;
        int i9 = (int) this.f2856n;
        int i10 = (int) this.f2855m;
        int iAbs = Math.abs(i4);
        if (iAbs < i9) {
            i4 = 0;
        } else if (iAbs > i10) {
            i4 = i4 > 0 ? i10 : -i10;
        }
        int iAbs2 = Math.abs(i5);
        if (iAbs2 < i9) {
            i5 = i8;
        } else if (iAbs2 > i10) {
            if (i5 > 0) {
                i5 = i10;
            } else {
                i8 = -i10;
                i5 = i8;
            }
        }
        int iAbs3 = Math.abs(i6);
        int iAbs4 = Math.abs(i7);
        int iAbs5 = Math.abs(i4);
        int iAbs6 = Math.abs(i5);
        int i11 = iAbs5 + iAbs6;
        int i12 = iAbs3 + iAbs4;
        if (i4 != 0) {
            f2 = iAbs5;
            f3 = i11;
        } else {
            f2 = iAbs3;
            f3 = i12;
        }
        float f6 = f2 / f3;
        if (i5 != 0) {
            f4 = iAbs6;
            f5 = i11;
        } else {
            f4 = iAbs4;
            f5 = i12;
        }
        float f7 = f4 / f5;
        r rVar = this.f2859q;
        overScroller.startScroll(left, top, i6, i7, (int) ((f(i7, i5, rVar.J()) * f7) + (f(i6, i4, rVar.I(view)) * f6)));
        p(2);
        return true;
    }

    public final boolean j(int i2) {
        if ((this.f2853k & (1 << i2)) != 0) {
            return true;
        }
        Log.e("ViewDragHelper", "Ignoring pointerId=" + i2 + " because ACTION_DOWN was not received for this pointer before ACTION_MOVE. It likely happened because  ViewDragHelper did not receive all the events in the event stream.");
        return false;
    }

    public final void k(MotionEvent motionEvent) {
        int i2;
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            a();
        }
        if (this.f2854l == null) {
            this.f2854l = VelocityTracker.obtain();
        }
        this.f2854l.addMovement(motionEvent);
        int i3 = 0;
        if (actionMasked == 0) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            int pointerId = motionEvent.getPointerId(0);
            View viewH = h((int) x2, (int) y2);
            n(x2, y2, pointerId);
            s(viewH, pointerId);
            int i4 = this.f2850h[pointerId];
            return;
        }
        if (actionMasked == 1) {
            if (this.f2843a == 1) {
                l();
            }
            a();
            return;
        }
        r rVar = this.f2859q;
        if (actionMasked == 2) {
            if (this.f2843a != 1) {
                int pointerCount = motionEvent.getPointerCount();
                while (i3 < pointerCount) {
                    int pointerId2 = motionEvent.getPointerId(i3);
                    if (j(pointerId2)) {
                        float x3 = motionEvent.getX(i3);
                        float y3 = motionEvent.getY(i3);
                        float f2 = x3 - this.f2846d[pointerId2];
                        float f3 = y3 - this.f2847e[pointerId2];
                        m(f2, f3, pointerId2);
                        if (this.f2843a != 1) {
                            View viewH2 = h((int) x3, (int) y3);
                            if (d(viewH2, f2, f3) && s(viewH2, pointerId2)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    i3++;
                }
                o(motionEvent);
                return;
            }
            if (j(this.f2845c)) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.f2845c);
                float x4 = motionEvent.getX(iFindPointerIndex);
                float y4 = motionEvent.getY(iFindPointerIndex);
                float[] fArr = this.f2848f;
                int i5 = this.f2845c;
                int i6 = (int) (x4 - fArr[i5]);
                int i7 = (int) (y4 - this.f2849g[i5]);
                int left = this.r.getLeft() + i6;
                int top = this.r.getTop() + i7;
                int left2 = this.r.getLeft();
                int top2 = this.r.getTop();
                if (i6 != 0) {
                    left = rVar.g(this.r, left);
                    WeakHashMap weakHashMap = l.f2836a;
                    this.r.offsetLeftAndRight(left - left2);
                }
                if (i7 != 0) {
                    top = rVar.h(this.r, top);
                    WeakHashMap weakHashMap2 = l.f2836a;
                    this.r.offsetTopAndBottom(top - top2);
                }
                if (i6 != 0 || i7 != 0) {
                    rVar.c0(this.r, left, top);
                }
                o(motionEvent);
                return;
            }
            return;
        }
        if (actionMasked == 3) {
            if (this.f2843a == 1) {
                this.f2860s = true;
                rVar.d0(this.r, 0.0f, 0.0f);
                this.f2860s = false;
                if (this.f2843a == 1) {
                    p(0);
                }
            }
            a();
            return;
        }
        if (actionMasked == 5) {
            int pointerId3 = motionEvent.getPointerId(actionIndex);
            float x5 = motionEvent.getX(actionIndex);
            float y5 = motionEvent.getY(actionIndex);
            n(x5, y5, pointerId3);
            if (this.f2843a == 0) {
                s(h((int) x5, (int) y5), pointerId3);
                int i8 = this.f2850h[pointerId3];
                return;
            }
            int i9 = (int) x5;
            int i10 = (int) y5;
            View view = this.r;
            if (view != null) {
                i3 = (i9 < view.getLeft() || i9 >= view.getRight() || i10 < view.getTop() || i10 >= view.getBottom()) ? 0 : 1;
            }
            if (i3 != 0) {
                s(this.r, pointerId3);
                return;
            }
            return;
        }
        if (actionMasked != 6) {
            return;
        }
        int pointerId4 = motionEvent.getPointerId(actionIndex);
        if (this.f2843a == 1 && pointerId4 == this.f2845c) {
            int pointerCount2 = motionEvent.getPointerCount();
            while (true) {
                if (i3 >= pointerCount2) {
                    i2 = -1;
                    break;
                }
                int pointerId5 = motionEvent.getPointerId(i3);
                if (pointerId5 != this.f2845c) {
                    View viewH3 = h((int) motionEvent.getX(i3), (int) motionEvent.getY(i3));
                    View view2 = this.r;
                    if (viewH3 == view2 && s(view2, pointerId5)) {
                        i2 = this.f2845c;
                        break;
                    }
                }
                i3++;
            }
            if (i2 == -1) {
                l();
            }
        }
        e(pointerId4);
    }

    public final void l() {
        VelocityTracker velocityTracker = this.f2854l;
        float f2 = this.f2855m;
        velocityTracker.computeCurrentVelocity(1000, f2);
        float xVelocity = this.f2854l.getXVelocity(this.f2845c);
        float f3 = this.f2856n;
        float fAbs = Math.abs(xVelocity);
        float f4 = 0.0f;
        if (fAbs < f3) {
            xVelocity = 0.0f;
        } else if (fAbs > f2) {
            xVelocity = xVelocity > 0.0f ? f2 : -f2;
        }
        float yVelocity = this.f2854l.getYVelocity(this.f2845c);
        float fAbs2 = Math.abs(yVelocity);
        if (fAbs2 >= f3) {
            if (fAbs2 > f2) {
                if (yVelocity <= 0.0f) {
                    f2 = -f2;
                }
                f4 = f2;
            } else {
                f4 = yVelocity;
            }
        }
        this.f2860s = true;
        this.f2859q.d0(this.r, xVelocity, f4);
        this.f2860s = false;
        if (this.f2843a == 1) {
            p(0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    public final void m(float f2, float f3, int i2) {
        boolean zC = c(f2, f3, i2, 1);
        ?? r0 = zC;
        if (c(f3, f2, i2, 4)) {
            r0 = (zC ? 1 : 0) | 4;
        }
        ?? r02 = r0;
        if (c(f2, f3, i2, 2)) {
            r02 = (r0 == true ? 1 : 0) | 2;
        }
        ?? r03 = r02;
        if (c(f3, f2, i2, 8)) {
            r03 = (r02 == true ? 1 : 0) | 8;
        }
        if (r03 != 0) {
            int[] iArr = this.f2851i;
            iArr[i2] = iArr[i2] | r03;
            this.f2859q.getClass();
        }
    }

    public final void n(float f2, float f3, int i2) {
        float[] fArr = this.f2846d;
        if (fArr == null || fArr.length <= i2) {
            int i3 = i2 + 1;
            float[] fArr2 = new float[i3];
            float[] fArr3 = new float[i3];
            float[] fArr4 = new float[i3];
            float[] fArr5 = new float[i3];
            int[] iArr = new int[i3];
            int[] iArr2 = new int[i3];
            int[] iArr3 = new int[i3];
            if (fArr != null) {
                System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                float[] fArr6 = this.f2847e;
                System.arraycopy(fArr6, 0, fArr3, 0, fArr6.length);
                float[] fArr7 = this.f2848f;
                System.arraycopy(fArr7, 0, fArr4, 0, fArr7.length);
                float[] fArr8 = this.f2849g;
                System.arraycopy(fArr8, 0, fArr5, 0, fArr8.length);
                int[] iArr4 = this.f2850h;
                System.arraycopy(iArr4, 0, iArr, 0, iArr4.length);
                int[] iArr5 = this.f2851i;
                System.arraycopy(iArr5, 0, iArr2, 0, iArr5.length);
                int[] iArr6 = this.f2852j;
                System.arraycopy(iArr6, 0, iArr3, 0, iArr6.length);
            }
            this.f2846d = fArr2;
            this.f2847e = fArr3;
            this.f2848f = fArr4;
            this.f2849g = fArr5;
            this.f2850h = iArr;
            this.f2851i = iArr2;
            this.f2852j = iArr3;
        }
        float[] fArr9 = this.f2846d;
        this.f2848f[i2] = f2;
        fArr9[i2] = f2;
        float[] fArr10 = this.f2847e;
        this.f2849g[i2] = f3;
        fArr10[i2] = f3;
        int[] iArr7 = this.f2850h;
        int i4 = (int) f2;
        int i5 = (int) f3;
        ViewGroup viewGroup = this.f2861t;
        int left = viewGroup.getLeft();
        int i6 = this.f2857o;
        int i7 = i4 < left + i6 ? 1 : 0;
        if (i5 < viewGroup.getTop() + i6) {
            i7 |= 4;
        }
        if (i4 > viewGroup.getRight() - i6) {
            i7 |= 2;
        }
        if (i5 > viewGroup.getBottom() - i6) {
            i7 |= 8;
        }
        iArr7[i2] = i7;
        this.f2853k |= 1 << i2;
    }

    public final void o(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        for (int i2 = 0; i2 < pointerCount; i2++) {
            int pointerId = motionEvent.getPointerId(i2);
            if (j(pointerId)) {
                float x2 = motionEvent.getX(i2);
                float y2 = motionEvent.getY(i2);
                this.f2848f[pointerId] = x2;
                this.f2849g[pointerId] = y2;
            }
        }
    }

    public final void p(int i2) {
        this.f2861t.removeCallbacks(this.f2862u);
        if (this.f2843a != i2) {
            this.f2843a = i2;
            this.f2859q.b0(i2);
            if (this.f2843a == 0) {
                this.r = null;
            }
        }
    }

    public final boolean q(int i2, int i3) {
        if (this.f2860s) {
            return i(i2, i3, (int) this.f2854l.getXVelocity(this.f2845c), (int) this.f2854l.getYVelocity(this.f2845c));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ed  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean r(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instruction units count: 284
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: x.C0165b.r(android.view.MotionEvent):boolean");
    }

    public final boolean s(View view, int i2) {
        if (view == this.r && this.f2845c == i2) {
            return true;
        }
        if (view == null || !this.f2859q.q0(view, i2)) {
            return false;
        }
        this.f2845c = i2;
        b(view, i2);
        return true;
    }
}
