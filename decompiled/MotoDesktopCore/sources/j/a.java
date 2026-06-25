package J;

import Y.r;
import android.view.View;
import android.view.ViewParent;
import com.google.android.material.behavior.SwipeDismissBehavior;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public final class a extends r {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f183h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f184i = -1;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final /* synthetic */ SwipeDismissBehavior f185j;

    public a(SwipeDismissBehavior swipeDismissBehavior) {
        this.f185j = swipeDismissBehavior;
    }

    @Override // Y.r
    public final int I(View view) {
        return view.getWidth();
    }

    @Override // Y.r
    public final void a0(View view, int i2) {
        this.f184i = i2;
        this.f183h = view.getLeft();
        ViewParent parent = view.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override // Y.r
    public final void b0(int i2) {
        this.f185j.getClass();
    }

    @Override // Y.r
    public final void c0(View view, int i2, int i3) {
        float f2 = this.f183h;
        float width = view.getWidth();
        SwipeDismissBehavior swipeDismissBehavior = this.f185j;
        float f3 = (width * swipeDismissBehavior.f2070e) + f2;
        float width2 = (view.getWidth() * swipeDismissBehavior.f2071f) + this.f183h;
        float f4 = i2;
        if (f4 <= f3) {
            view.setAlpha(1.0f);
        } else if (f4 >= width2) {
            view.setAlpha(0.0f);
        } else {
            view.setAlpha(Math.min(Math.max(0.0f, 1.0f - ((f4 - f3) / (width2 - f3))), 1.0f));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005e  */
    @Override // Y.r
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void d0(android.view.View r9, float r10, float r11) {
        /*
            r8 = this;
            r11 = -1
            r8.f184i = r11
            int r11 = r9.getWidth()
            r0 = 0
            int r1 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            r2 = 1
            com.google.android.material.behavior.SwipeDismissBehavior r3 = r8.f185j
            r4 = 0
            if (r1 == 0) goto L39
            java.util.WeakHashMap r5 = v.l.f2836a
            int r5 = r9.getLayoutDirection()
            if (r5 != r2) goto L1a
            r5 = r2
            goto L1b
        L1a:
            r5 = r4
        L1b:
            int r6 = r3.f2068c
            r7 = 2
            if (r6 != r7) goto L21
            goto L52
        L21:
            if (r6 != 0) goto L2d
            if (r5 == 0) goto L2a
            int r10 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r10 >= 0) goto L5e
            goto L52
        L2a:
            if (r1 <= 0) goto L5e
            goto L52
        L2d:
            if (r6 != r2) goto L5e
            if (r5 == 0) goto L34
            if (r1 <= 0) goto L5e
            goto L52
        L34:
            int r10 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r10 >= 0) goto L5e
            goto L52
        L39:
            int r10 = r9.getLeft()
            int r0 = r8.f183h
            int r10 = r10 - r0
            int r0 = r9.getWidth()
            float r0 = (float) r0
            float r1 = r3.f2069d
            float r0 = r0 * r1
            int r0 = java.lang.Math.round(r0)
            int r10 = java.lang.Math.abs(r10)
            if (r10 < r0) goto L5e
        L52:
            int r10 = r9.getLeft()
            int r8 = r8.f183h
            if (r10 >= r8) goto L5c
            int r8 = r8 - r11
            goto L61
        L5c:
            int r8 = r8 + r11
            goto L61
        L5e:
            int r8 = r8.f183h
            r2 = r4
        L61:
            x.b r10 = r3.f2066a
            int r11 = r9.getTop()
            boolean r8 = r10.q(r8, r11)
            if (r8 == 0) goto L77
            J.b r8 = new J.b
            r8.<init>(r3, r9, r2)
            java.util.WeakHashMap r10 = v.l.f2836a
            r9.postOnAnimation(r8)
        L77:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: J.a.d0(android.view.View, float, float):void");
    }

    @Override // Y.r
    public final int g(View view, int i2) {
        int width;
        int width2;
        int width3;
        WeakHashMap weakHashMap = l.f2836a;
        boolean z2 = view.getLayoutDirection() == 1;
        int i3 = this.f185j.f2068c;
        if (i3 == 0) {
            if (z2) {
                width = this.f183h - view.getWidth();
                width2 = this.f183h;
            } else {
                width = this.f183h;
                width3 = view.getWidth();
                width2 = width3 + width;
            }
        } else if (i3 != 1) {
            width = this.f183h - view.getWidth();
            width2 = this.f183h + view.getWidth();
        } else if (z2) {
            width = this.f183h;
            width3 = view.getWidth();
            width2 = width3 + width;
        } else {
            width = this.f183h - view.getWidth();
            width2 = this.f183h;
        }
        return Math.min(Math.max(width, i2), width2);
    }

    @Override // Y.r
    public final int h(View view, int i2) {
        return view.getTop();
    }

    @Override // Y.r
    public final boolean q0(View view, int i2) {
        return this.f184i == -1 && this.f185j.s(view);
    }
}
