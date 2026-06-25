package androidx.appcompat.app;

/* JADX INFO: loaded from: classes.dex */
public final class i implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f566a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ q f567b;

    public /* synthetic */ i(q qVar, int i2) {
        this.f566a = i2;
        this.f567b = qVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002a  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            r6 = this;
            r0 = 1
            androidx.appcompat.app.q r1 = r6.f567b
            r2 = 0
            int r3 = r6.f566a
            switch(r3) {
                case 0: goto L54;
                default: goto L9;
            }
        L9:
            android.widget.PopupWindow r3 = r1.f625n
            androidx.appcompat.widget.ActionBarContextView r4 = r1.f624m
            r5 = 55
            r3.showAtLocation(r4, r5, r2, r2)
            v.m r3 = r1.f627p
            if (r3 == 0) goto L19
            r3.b()
        L19:
            boolean r3 = r1.f628q
            if (r3 == 0) goto L2a
            android.view.ViewGroup r3 = r1.r
            if (r3 == 0) goto L2a
            java.util.WeakHashMap r4 = v.l.f2836a
            boolean r3 = r3.isLaidOut()
            if (r3 == 0) goto L2a
            goto L2b
        L2a:
            r0 = r2
        L2b:
            r3 = 1065353216(0x3f800000, float:1.0)
            if (r0 == 0) goto L49
            androidx.appcompat.widget.ActionBarContextView r0 = r1.f624m
            r4 = 0
            r0.setAlpha(r4)
            androidx.appcompat.widget.ActionBarContextView r0 = r1.f624m
            v.m r0 = v.l.a(r0)
            r0.a(r3)
            r1.f627p = r0
            androidx.appcompat.app.k r1 = new androidx.appcompat.app.k
            r1.<init>(r2, r6)
            r0.d(r1)
            goto L53
        L49:
            androidx.appcompat.widget.ActionBarContextView r6 = r1.f624m
            r6.setAlpha(r3)
            androidx.appcompat.widget.ActionBarContextView r6 = r1.f624m
            r6.setVisibility(r2)
        L53:
            return
        L54:
            int r6 = r1.f606K
            r6 = r6 & r0
            if (r6 == 0) goto L5c
            r1.h(r2)
        L5c:
            int r6 = r1.f606K
            r6 = r6 & 4096(0x1000, float:5.74E-42)
            if (r6 == 0) goto L67
            r6 = 108(0x6c, float:1.51E-43)
            r1.h(r6)
        L67:
            r1.f605J = r2
            r1.f606K = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.i.run():void");
    }
}
