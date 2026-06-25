package androidx.core.widget;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public final class b implements View.OnTouchListener {
    public static final int r = ViewConfiguration.getTapTimeout();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final a f1444a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final AccelerateInterpolator f1445b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final View f1446c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public F.e f1447d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final float[] f1448e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final float[] f1449f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final int f1450g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final int f1451h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final float[] f1452i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final float[] f1453j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final float[] f1454k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public boolean f1455l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public boolean f1456m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public boolean f1457n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public boolean f1458o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public boolean f1459p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final ListView f1460q;

    public b(ListView listView) {
        a aVar = new a();
        aVar.f1439e = Long.MIN_VALUE;
        aVar.f1441g = -1L;
        aVar.f1440f = 0L;
        this.f1444a = aVar;
        this.f1445b = new AccelerateInterpolator();
        float[] fArr = {0.0f, 0.0f};
        this.f1448e = fArr;
        float[] fArr2 = {Float.MAX_VALUE, Float.MAX_VALUE};
        this.f1449f = fArr2;
        float[] fArr3 = {0.0f, 0.0f};
        this.f1452i = fArr3;
        float[] fArr4 = {0.0f, 0.0f};
        this.f1453j = fArr4;
        float[] fArr5 = {Float.MAX_VALUE, Float.MAX_VALUE};
        this.f1454k = fArr5;
        this.f1446c = listView;
        float f2 = Resources.getSystem().getDisplayMetrics().density;
        float f3 = ((int) ((1575.0f * f2) + 0.5f)) / 1000.0f;
        fArr5[0] = f3;
        fArr5[1] = f3;
        float f4 = ((int) ((f2 * 315.0f) + 0.5f)) / 1000.0f;
        fArr4[0] = f4;
        fArr4[1] = f4;
        this.f1450g = 1;
        fArr2[0] = Float.MAX_VALUE;
        fArr2[1] = Float.MAX_VALUE;
        fArr[0] = 0.2f;
        fArr[1] = 0.2f;
        fArr3[0] = 0.001f;
        fArr3[1] = 0.001f;
        this.f1451h = r;
        aVar.f1435a = 500;
        aVar.f1436b = 500;
        this.f1460q = listView;
    }

    public static float b(float f2, float f3, float f4) {
        return f2 > f4 ? f4 : f2 < f3 ? f3 : f2;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final float a(int r4, float r5, float r6, float r7) {
        /*
            r3 = this;
            float[] r0 = r3.f1448e
            r0 = r0[r4]
            float[] r1 = r3.f1449f
            r1 = r1[r4]
            float r0 = r0 * r6
            r2 = 0
            float r0 = b(r0, r2, r1)
            float r1 = r3.c(r5, r0)
            float r6 = r6 - r5
            float r5 = r3.c(r6, r0)
            float r5 = r5 - r1
            int r6 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            android.view.animation.AccelerateInterpolator r0 = r3.f1445b
            if (r6 >= 0) goto L25
            float r5 = -r5
            float r5 = r0.getInterpolation(r5)
            float r5 = -r5
            goto L2d
        L25:
            int r6 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r6 <= 0) goto L36
            float r5 = r0.getInterpolation(r5)
        L2d:
            r6 = -1082130432(0xffffffffbf800000, float:-1.0)
            r0 = 1065353216(0x3f800000, float:1.0)
            float r5 = b(r5, r6, r0)
            goto L37
        L36:
            r5 = r2
        L37:
            int r6 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r6 != 0) goto L3c
            return r2
        L3c:
            float[] r0 = r3.f1452i
            r0 = r0[r4]
            float[] r1 = r3.f1453j
            r1 = r1[r4]
            float[] r3 = r3.f1454k
            r3 = r3[r4]
            float r0 = r0 * r7
            if (r6 <= 0) goto L51
            float r5 = r5 * r0
            float r3 = b(r5, r1, r3)
            return r3
        L51:
            float r4 = -r5
            float r4 = r4 * r0
            float r3 = b(r4, r1, r3)
            float r3 = -r3
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.b.a(int, float, float, float):float");
    }

    public final float c(float f2, float f3) {
        if (f3 == 0.0f) {
            return 0.0f;
        }
        int i2 = this.f1450g;
        if (i2 == 0 || i2 == 1) {
            if (f2 < f3) {
                if (f2 >= 0.0f) {
                    return 1.0f - (f2 / f3);
                }
                if (this.f1458o && i2 == 1) {
                    return 1.0f;
                }
            }
        } else if (i2 == 2 && f2 < 0.0f) {
            return f2 / (-f3);
        }
        return 0.0f;
    }

    public final void d() {
        int i2 = 0;
        if (this.f1456m) {
            this.f1458o = false;
            return;
        }
        a aVar = this.f1444a;
        aVar.getClass();
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        int i3 = (int) (jCurrentAnimationTimeMillis - aVar.f1439e);
        int i4 = aVar.f1436b;
        if (i3 > i4) {
            i2 = i4;
        } else if (i3 >= 0) {
            i2 = i3;
        }
        aVar.f1443i = i2;
        aVar.f1442h = aVar.a(jCurrentAnimationTimeMillis);
        aVar.f1441g = jCurrentAnimationTimeMillis;
    }

    public final boolean e() {
        ListView listView;
        int count;
        a aVar = this.f1444a;
        float f2 = aVar.f1438d;
        int iAbs = (int) (f2 / Math.abs(f2));
        Math.abs(aVar.f1437c);
        if (iAbs == 0 || (count = (listView = this.f1460q).getCount()) == 0) {
            return false;
        }
        int childCount = listView.getChildCount();
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int i2 = firstVisiblePosition + childCount;
        if (iAbs > 0) {
            if (i2 >= count && listView.getChildAt(childCount - 1).getBottom() <= listView.getHeight()) {
                return false;
            }
        } else {
            if (iAbs >= 0) {
                return false;
            }
            if (firstVisiblePosition <= 0 && listView.getChildAt(0).getTop() >= 0) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0016  */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onTouch(android.view.View r8, android.view.MotionEvent r9) {
        /*
            r7 = this;
            boolean r0 = r7.f1459p
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            int r0 = r9.getActionMasked()
            r2 = 1
            if (r0 == 0) goto L1a
            if (r0 == r2) goto L16
            r3 = 2
            if (r0 == r3) goto L1e
            r8 = 3
            if (r0 == r8) goto L16
            goto L7c
        L16:
            r7.d()
            goto L7c
        L1a:
            r7.f1457n = r2
            r7.f1455l = r1
        L1e:
            float r0 = r9.getX()
            int r3 = r8.getWidth()
            float r3 = (float) r3
            android.view.View r4 = r7.f1446c
            int r5 = r4.getWidth()
            float r5 = (float) r5
            float r0 = r7.a(r1, r0, r3, r5)
            float r9 = r9.getY()
            int r8 = r8.getHeight()
            float r8 = (float) r8
            int r3 = r4.getHeight()
            float r3 = (float) r3
            float r8 = r7.a(r2, r9, r8, r3)
            androidx.core.widget.a r9 = r7.f1444a
            r9.f1437c = r0
            r9.f1438d = r8
            boolean r8 = r7.f1458o
            if (r8 != 0) goto L7c
            boolean r8 = r7.e()
            if (r8 == 0) goto L7c
            F.e r8 = r7.f1447d
            if (r8 != 0) goto L60
            F.e r8 = new F.e
            r9 = 7
            r8.<init>(r9, r7)
            r7.f1447d = r8
        L60:
            r7.f1458o = r2
            r7.f1456m = r2
            boolean r8 = r7.f1455l
            if (r8 != 0) goto L75
            int r8 = r7.f1451h
            if (r8 <= 0) goto L75
            F.e r9 = r7.f1447d
            long r5 = (long) r8
            java.util.WeakHashMap r8 = v.l.f2836a
            r4.postOnAnimationDelayed(r9, r5)
            goto L7a
        L75:
            F.e r8 = r7.f1447d
            r8.run()
        L7a:
            r7.f1455l = r2
        L7c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.b.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }
}
