package com.android.systemui;

import android.os.Binder;
import android.os.Handler;
import android.view.Choreographer;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes.dex */
public abstract class DejankUtils {
    public static final boolean STRICT_MODE_ENABLED;
    private static final Runnable sAnimationCallbackRunnable;
    private static Stack sBlockingIpcs;
    private static final Choreographer sChoreographer;
    private static final Handler sHandler;
    private static boolean sImmediate;
    private static final Object sLock;
    private static final ArrayList sPendingRunnables;
    private static final Binder.ProxyTransactListener sProxy;
    private static final Random sRandom;
    private static boolean sTemporarilyIgnoreStrictMode;
    private static final HashSet sWhitelistedFrameworkClasses;

    /* JADX INFO: renamed from: $r8$lambda$86SPc93uCuT3gG-ukqaRH1LpGTY, reason: not valid java name */
    public static /* synthetic */ void m1157$r8$lambda$86SPc93uCuT3gGukqaRH1LpGTY() {
        int i = 0;
        while (true) {
            ArrayList arrayList = sPendingRunnables;
            if (i >= arrayList.size()) {
                arrayList.clear();
                return;
            } else {
                sHandler.post((Runnable) arrayList.get(i));
                i++;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x000d  */
    static {
        /*
            boolean r0 = android.os.Build.IS_ENG
            if (r0 != 0) goto Ld
            java.lang.String r0 = "persist.sysui.strictmode"
            r1 = 0
            boolean r0 = android.os.SystemProperties.getBoolean(r0, r1)
            if (r0 == 0) goto Le
        Ld:
            r1 = 1
        Le:
            com.android.systemui.DejankUtils.STRICT_MODE_ENABLED = r1
            android.view.Choreographer r0 = android.view.Choreographer.getInstance()
            com.android.systemui.DejankUtils.sChoreographer = r0
            android.os.Handler r0 = new android.os.Handler
            r0.<init>()
            com.android.systemui.DejankUtils.sHandler = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            com.android.systemui.DejankUtils.sPendingRunnables = r0
            java.util.Random r0 = new java.util.Random
            r0.<init>()
            com.android.systemui.DejankUtils.sRandom = r0
            java.util.Stack r0 = new java.util.Stack
            r0.<init>()
            com.android.systemui.DejankUtils.sBlockingIpcs = r0
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            com.android.systemui.DejankUtils.sWhitelistedFrameworkClasses = r0
            java.lang.Object r2 = new java.lang.Object
            r2.<init>()
            com.android.systemui.DejankUtils.sLock = r2
            com.android.systemui.DejankUtils$1 r2 = new com.android.systemui.DejankUtils$1
            r2.<init>()
            com.android.systemui.DejankUtils.sProxy = r2
            if (r1 == 0) goto L78
            java.lang.String r1 = "android.view.IWindowSession"
            r0.add(r1)
            java.lang.String r1 = "com.android.internal.policy.IKeyguardStateCallback"
            r0.add(r1)
            java.lang.String r1 = "android.os.IPowerManager"
            r0.add(r1)
            java.lang.String r1 = "com.android.internal.statusbar.IStatusBarService"
            r0.add(r1)
            android.os.Binder.setProxyTransactListener(r2)
            android.os.StrictMode$ThreadPolicy$Builder r0 = new android.os.StrictMode$ThreadPolicy$Builder
            r0.<init>()
            android.os.StrictMode$ThreadPolicy$Builder r0 = r0.detectCustomSlowCalls()
            android.os.StrictMode$ThreadPolicy$Builder r0 = r0.penaltyFlashScreen()
            android.os.StrictMode$ThreadPolicy$Builder r0 = r0.penaltyLog()
            android.os.StrictMode$ThreadPolicy r0 = r0.build()
            android.os.StrictMode.setThreadPolicy(r0)
        L78:
            com.android.systemui.DejankUtils$$ExternalSyntheticLambda0 r0 = new com.android.systemui.DejankUtils$$ExternalSyntheticLambda0
            r0.<init>()
            com.android.systemui.DejankUtils.sAnimationCallbackRunnable = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.DejankUtils.<clinit>():void");
    }

    public static void postAfterTraversal(Runnable runnable) {
        if (sImmediate) {
            runnable.run();
            return;
        }
        Assert.isMainThread();
        sPendingRunnables.add(runnable);
        postAnimationCallback();
    }

    private static void postAnimationCallback() {
        sChoreographer.postCallback(1, sAnimationCallbackRunnable, null);
    }

    public static void setImmediate(boolean z) {
        sImmediate = z;
    }

    public static Object whitelistIpcs(Supplier supplier) {
        if (!STRICT_MODE_ENABLED || sTemporarilyIgnoreStrictMode) {
            return supplier.get();
        }
        Object obj = sLock;
        synchronized (obj) {
            sTemporarilyIgnoreStrictMode = true;
        }
        try {
            Object obj2 = supplier.get();
            synchronized (obj) {
                sTemporarilyIgnoreStrictMode = false;
            }
            return obj2;
        } catch (Throwable th) {
            synchronized (sLock) {
                sTemporarilyIgnoreStrictMode = false;
                throw th;
            }
        }
    }
}
