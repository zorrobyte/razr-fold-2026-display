package C;

import android.animation.ObjectAnimator;
import android.view.View;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class i extends s {

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public static final String[] f47y = {"android:visibility:visibility", "android:visibility:parent"};

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public final int f48x;

    public i(int i2) {
        this.f48x = i2;
    }

    public static void H(y yVar) {
        int visibility = yVar.f103b.getVisibility();
        HashMap map = yVar.f102a;
        map.put("android:visibility:visibility", Integer.valueOf(visibility));
        map.put("android:visibility:parent", yVar.f103b.getParent());
        int[] iArr = new int[2];
        yVar.f103b.getLocationOnScreen(iArr);
        map.put("android:visibility:screenLocation", iArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static C.D J(C.y r8, C.y r9) {
        /*
            C.D r0 = new C.D
            r0.<init>()
            r1 = 0
            r0.f19a = r1
            r0.f20b = r1
            r2 = 0
            r3 = -1
            java.lang.String r4 = "android:visibility:parent"
            java.lang.String r5 = "android:visibility:visibility"
            if (r8 == 0) goto L2f
            java.util.HashMap r6 = r8.f102a
            boolean r7 = r6.containsKey(r5)
            if (r7 == 0) goto L2f
            java.lang.Object r7 = r6.get(r5)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            r0.f21c = r7
            java.lang.Object r6 = r6.get(r4)
            android.view.ViewGroup r6 = (android.view.ViewGroup) r6
            r0.f23e = r6
            goto L33
        L2f:
            r0.f21c = r3
            r0.f23e = r2
        L33:
            if (r9 == 0) goto L52
            java.util.HashMap r6 = r9.f102a
            boolean r7 = r6.containsKey(r5)
            if (r7 == 0) goto L52
            java.lang.Object r2 = r6.get(r5)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            r0.f22d = r2
            java.lang.Object r2 = r6.get(r4)
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            r0.f24f = r2
            goto L56
        L52:
            r0.f22d = r3
            r0.f24f = r2
        L56:
            r2 = 1
            if (r8 == 0) goto L8a
            if (r9 == 0) goto L8a
            int r8 = r0.f21c
            int r9 = r0.f22d
            if (r8 != r9) goto L68
            android.view.ViewGroup r3 = r0.f23e
            android.view.ViewGroup r4 = r0.f24f
            if (r3 != r4) goto L68
            return r0
        L68:
            if (r8 == r9) goto L78
            if (r8 != 0) goto L71
            r0.f20b = r1
            r0.f19a = r2
            goto L9f
        L71:
            if (r9 != 0) goto L9f
            r0.f20b = r2
            r0.f19a = r2
            goto L9f
        L78:
            android.view.ViewGroup r8 = r0.f24f
            if (r8 != 0) goto L81
            r0.f20b = r1
            r0.f19a = r2
            goto L9f
        L81:
            android.view.ViewGroup r8 = r0.f23e
            if (r8 != 0) goto L9f
            r0.f20b = r2
            r0.f19a = r2
            goto L9f
        L8a:
            if (r8 != 0) goto L95
            int r8 = r0.f22d
            if (r8 != 0) goto L95
            r0.f20b = r2
            r0.f19a = r2
            goto L9f
        L95:
            if (r9 != 0) goto L9f
            int r8 = r0.f21c
            if (r8 != 0) goto L9f
            r0.f20b = r1
            r0.f19a = r2
        L9f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: C.i.J(C.y, C.y):C.D");
    }

    public final ObjectAnimator I(View view, float f2, float f3) {
        if (f2 == f3) {
            return null;
        }
        A.b(view, f2);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, A.f6d, f3);
        objectAnimatorOfFloat.addListener(new h(view));
        a(new C0004e(1, view));
        return objectAnimatorOfFloat;
    }

    @Override // C.s
    public final void d(y yVar) {
        H(yVar);
    }

    @Override // C.s
    public final void g(y yVar) {
        H(yVar);
        yVar.f102a.put("android:fade:transitionAlpha", Float.valueOf(A.f3a.G(yVar.f103b)));
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x00d6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0136  */
    @Override // C.s
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.animation.Animator k(android.view.ViewGroup r12, C.y r13, C.y r14) {
        /*
            Method dump skipped, instruction units count: 358
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: C.i.k(android.view.ViewGroup, C.y, C.y):android.animation.Animator");
    }

    @Override // C.s
    public final String[] p() {
        return f47y;
    }

    @Override // C.s
    public final boolean r(y yVar, y yVar2) {
        if (yVar == null && yVar2 == null) {
            return false;
        }
        if (yVar != null && yVar2 != null && yVar2.f102a.containsKey("android:visibility:visibility") != yVar.f102a.containsKey("android:visibility:visibility")) {
            return false;
        }
        D dJ = J(yVar, yVar2);
        if (dJ.f19a) {
            return dJ.f21c == 0 || dJ.f22d == 0;
        }
        return false;
    }
}
