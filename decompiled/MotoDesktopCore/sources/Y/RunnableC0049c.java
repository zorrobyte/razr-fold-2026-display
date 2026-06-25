package Y;

/* JADX INFO: renamed from: Y.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class RunnableC0049c implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f356a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f357b;

    public /* synthetic */ RunnableC0049c(int i2, Object obj) {
        this.f356a = i2;
        this.f357b = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x004f A[Catch: all -> 0x003f, DONT_GENERATE, TRY_LEAVE, TryCatch #0 {all -> 0x003f, blocks: (B:18:0x002d, B:20:0x0033, B:22:0x003d, B:30:0x004f, B:26:0x0041, B:28:0x004d), top: B:37:0x002d }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            r4 = this;
            int r0 = r4.f356a
            switch(r0) {
                case 0: goto L15;
                default: goto L5;
            }
        L5:
            java.lang.Object r4 = r4.f357b
            X.B0 r4 = (X.B0) r4
            java.lang.Object r4 = r4.f260b
            e0.A r4 = (e0.A) r4
            e0.d r0 = r4.f2441u
            X.B0 r4 = r4.f2444x
            r0.g(r4)
            return
        L15:
            java.lang.Object r4 = r4.f357b
            Y.i r4 = (Y.i) r4
            boolean r0 = r4.f399l
            if (r0 == 0) goto L57
            if (r0 != 0) goto L20
            goto L60
        L20:
            boolean r0 = r4.f396i
            if (r0 != 0) goto L25
            goto L60
        L25:
            boolean r0 = r4.f379J
            if (r0 == 0) goto L2a
            goto L60
        L2a:
            java.lang.Object r0 = r4.f389b
            monitor-enter(r0)
            boolean r1 = Y.r.L()     // Catch: java.lang.Throwable -> L3f
            if (r1 != 0) goto L41
            java.util.ArrayList r1 = r4.f395h     // Catch: java.lang.Throwable -> L3f
            int r2 = r4.f397j     // Catch: java.lang.Throwable -> L3f
            boolean r1 = Y.r.n(r1, r2)     // Catch: java.lang.Throwable -> L3f
            if (r1 == 0) goto L4f
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3f
            goto L60
        L3f:
            r4 = move-exception
            goto L55
        L41:
            java.util.ArrayList r1 = r4.f395h     // Catch: java.lang.Throwable -> L3f
            int r2 = r4.f397j     // Catch: java.lang.Throwable -> L3f
            java.lang.String r3 = X.Q0.f295h     // Catch: java.lang.Throwable -> L3f
            boolean r1 = Y.r.o(r1, r2, r3)     // Catch: java.lang.Throwable -> L3f
            if (r1 == 0) goto L4f
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3f
            goto L60
        L4f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3f
            r0 = 0
            r4.d(r0)
            goto L60
        L55:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3f
            throw r4
        L57:
            android.media.AudioManager r0 = r4.f398k
            java.util.List r0 = r0.getActivePlaybackConfigurations()
            r4.e(r0)
        L60:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: Y.RunnableC0049c.run():void");
    }
}
