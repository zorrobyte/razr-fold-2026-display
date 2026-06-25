package K;

import Y.r;
import android.view.View;
import androidx.appcompat.app.AbstractC0054a;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class b extends r {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final /* synthetic */ BottomSheetBehavior f193h;

    public b(BottomSheetBehavior bottomSheetBehavior) {
        this.f193h = bottomSheetBehavior;
    }

    @Override // Y.r
    public final int J() {
        BottomSheetBehavior bottomSheetBehavior = this.f193h;
        return bottomSheetBehavior.f2117j ? bottomSheetBehavior.f2124q : bottomSheetBehavior.f2116i;
    }

    @Override // Y.r
    public final void b0(int i2) {
        if (i2 == 1) {
            this.f193h.v(1);
        }
    }

    @Override // Y.r
    public final void c0(View view, int i2, int i3) {
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001c A[PHI: r2
      0x001c: PHI (r2v7 int) = (r2v0 int), (r2v6 int), (r2v0 int) binds: [B:37:0x0085, B:31:0x0072, B:8:0x0018] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // Y.r
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void d0(android.view.View r7, float r8, float r9) {
        /*
            r6 = this;
            r0 = 0
            int r1 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            r2 = 0
            r3 = 6
            r4 = 3
            com.google.android.material.bottomsheet.BottomSheetBehavior r6 = r6.f193h
            if (r1 >= 0) goto L21
            boolean r8 = r6.f2108a
            if (r8 == 0) goto L12
            int r8 = r6.f2114g
            goto La0
        L12:
            int r8 = r7.getTop()
            int r9 = r6.f2115h
            if (r8 <= r9) goto L1c
            r2 = r9
            goto L1d
        L1c:
            r3 = r4
        L1d:
            r8 = r2
            r4 = r3
            goto La0
        L21:
            boolean r1 = r6.f2117j
            if (r1 == 0) goto L43
            boolean r1 = r6.w(r7, r9)
            if (r1 == 0) goto L43
            int r1 = r7.getTop()
            int r5 = r6.f2116i
            if (r1 > r5) goto L3f
            float r1 = java.lang.Math.abs(r8)
            float r5 = java.lang.Math.abs(r9)
            int r1 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r1 >= 0) goto L43
        L3f:
            int r8 = r6.f2124q
            r4 = 5
            goto La0
        L43:
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            r1 = 4
            if (r0 == 0) goto L59
            float r8 = java.lang.Math.abs(r8)
            float r9 = java.lang.Math.abs(r9)
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 <= 0) goto L55
            goto L59
        L55:
            int r8 = r6.f2116i
            r4 = r1
            goto La0
        L59:
            int r8 = r7.getTop()
            boolean r9 = r6.f2108a
            if (r9 == 0) goto L79
            int r9 = r6.f2114g
            int r9 = r8 - r9
            int r9 = java.lang.Math.abs(r9)
            int r0 = r6.f2116i
            int r8 = r8 - r0
            int r8 = java.lang.Math.abs(r8)
            if (r9 >= r8) goto L75
            int r2 = r6.f2114g
            goto L1c
        L75:
            int r2 = r6.f2116i
        L77:
            r3 = r1
            goto L1d
        L79:
            int r9 = r6.f2115h
            if (r8 >= r9) goto L8b
            int r9 = r6.f2116i
            int r9 = r8 - r9
            int r9 = java.lang.Math.abs(r9)
            if (r8 >= r9) goto L88
            goto L1c
        L88:
            int r2 = r6.f2115h
            goto L1d
        L8b:
            int r9 = r8 - r9
            int r9 = java.lang.Math.abs(r9)
            int r0 = r6.f2116i
            int r8 = r8 - r0
            int r8 = java.lang.Math.abs(r8)
            if (r9 >= r8) goto L9d
            int r2 = r6.f2115h
            goto L1d
        L9d:
            int r2 = r6.f2116i
            goto L77
        La0:
            x.b r9 = r6.f2120m
            int r0 = r7.getLeft()
            boolean r8 = r9.q(r0, r8)
            if (r8 == 0) goto Lbb
            r8 = 2
            r6.v(r8)
            K.a r8 = new K.a
            r8.<init>(r6, r7, r4)
            java.util.WeakHashMap r6 = v.l.f2836a
            r7.postOnAnimation(r8)
            goto Lbe
        Lbb:
            r6.v(r4)
        Lbe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: K.b.d0(android.view.View, float, float):void");
    }

    @Override // Y.r
    public final int g(View view, int i2) {
        return view.getLeft();
    }

    @Override // Y.r
    public final int h(View view, int i2) {
        BottomSheetBehavior bottomSheetBehavior = this.f193h;
        return AbstractC0054a.g(i2, bottomSheetBehavior.t(), bottomSheetBehavior.f2117j ? bottomSheetBehavior.f2124q : bottomSheetBehavior.f2116i);
    }

    @Override // Y.r
    public final boolean q0(View view, int i2) {
        WeakReference weakReference;
        View view2;
        BottomSheetBehavior bottomSheetBehavior = this.f193h;
        int i3 = bottomSheetBehavior.f2119l;
        if (i3 == 1 || bottomSheetBehavior.f2130x) {
            return false;
        }
        return ((i3 == 3 && bottomSheetBehavior.f2128v == i2 && (view2 = (View) bottomSheetBehavior.f2125s.get()) != null && view2.canScrollVertically(-1)) || (weakReference = bottomSheetBehavior.r) == null || weakReference.get() != view) ? false : true;
    }
}
