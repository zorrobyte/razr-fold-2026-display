package e0;

import X.C0018g0;
import X.C0024j0;
import X.C0030m0;
import X.C0036p0;
import X.InterfaceC0020h0;
import X.InterfaceC0028l0;
import X.InterfaceC0034o0;
import X.J0;
import X.r0;
import X.v0;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.hardware.display.VirtualDisplay;
import android.os.RemoteException;
import com.motorola.mobiledesktop.core.bean.AppInfo;
import java.util.HashMap;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class b {

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public static b f2449m;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f2450a;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final ActivityTaskManager f2456g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public InterfaceC0028l0 f2457h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public InterfaceC0034o0 f2458i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public r0 f2459j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public InterfaceC0020h0 f2460k;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final HashMap f2451b = new HashMap();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final HashSet f2452c = new HashSet();

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final HashSet f2453d = new HashSet();

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final HashSet f2454e = new HashSet();

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final Object f2455f = new Object();

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final TaskStackListenerC0127a f2461l = new TaskStackListenerC0127a(0, this);

    public b(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f2450a = applicationContext;
        this.f2456g = (ActivityTaskManager) applicationContext.getSystemService("activity_task");
    }

    public final void d(r0 r0Var, J0 j02) {
        synchronized (this.f2455f) {
            if (this.f2459j == null) {
                this.f2459j = r0Var;
                try {
                    ((C0036p0) r0Var).f329a.linkToDeath(j02, 0);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
                this.f2456g.registerTaskStackListener(this.f2461l);
            }
        }
    }

    public final void e(InterfaceC0034o0 interfaceC0034o0, J0 j02, int i2, VirtualDisplay virtualDisplay) {
        synchronized (this.f2455f) {
            if (this.f2458i == null) {
                this.f2458i = interfaceC0034o0;
                try {
                    ((C0030m0) interfaceC0034o0).f327a.linkToDeath(j02, 0);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
                this.f2456g.registerTaskStackListener(this.f2461l);
                this.f2451b.put(Integer.valueOf(i2), virtualDisplay);
            } else {
                this.f2451b.put(Integer.valueOf(i2), virtualDisplay);
            }
        }
    }

    public final void f(J0 j02, int i2) {
        synchronized (this.f2455f) {
            try {
                VirtualDisplay virtualDisplay = (VirtualDisplay) this.f2451b.get(Integer.valueOf(i2));
                if (virtualDisplay != null) {
                    v0.a("AppStreamManager_Core", "releaseVirtualDisplayInternal, displayId = " + i2);
                    virtualDisplay.release();
                } else {
                    v0.g("AppStreamManager_Core", "releaseVirtualDisplayInternal, virtualDisplay is null for displayId: " + i2);
                }
                this.f2451b.remove(Integer.valueOf(i2));
                HashSet<AppInfo> hashSet = new HashSet();
                hashSet.addAll(this.f2454e);
                for (AppInfo appInfo : hashSet) {
                    if (appInfo.displayId == i2) {
                        this.f2452c.remove(appInfo.pkg);
                        this.f2453d.remove(appInfo.pkgAndUserId);
                        this.f2454e.remove(appInfo);
                    }
                }
                v0.a("AppStreamManager_Core", "releaseVirtualDisplayInternal, remain mAppStreamPkgs = " + this.f2452c);
                v0.a("AppStreamManager_Core", "releaseVirtualDisplayInternal, remain mAppStreamPkgAndUserIds = " + this.f2453d);
                v0.a("AppStreamManager_Core", "releaseVirtualDisplayInternal, remain mAppStreamApps = " + this.f2454e);
                if (this.f2451b.size() == 0) {
                    this.f2456g.unregisterTaskStackListener(this.f2461l);
                    InterfaceC0028l0 interfaceC0028l0 = this.f2457h;
                    if (interfaceC0028l0 != null) {
                        ((C0024j0) interfaceC0028l0).f325a.unlinkToDeath(j02, 0);
                    }
                    this.f2457h = null;
                    InterfaceC0034o0 interfaceC0034o0 = this.f2458i;
                    if (interfaceC0034o0 != null) {
                        ((C0030m0) interfaceC0034o0).f327a.unlinkToDeath(j02, 0);
                    }
                    this.f2458i = null;
                    r0 r0Var = this.f2459j;
                    if (r0Var != null) {
                        ((C0036p0) r0Var).f329a.unlinkToDeath(j02, 0);
                    }
                    this.f2459j = null;
                    InterfaceC0020h0 interfaceC0020h0 = this.f2460k;
                    if (interfaceC0020h0 != null) {
                        ((C0018g0) interfaceC0020h0).f322a.unlinkToDeath(j02, 0);
                    }
                    this.f2460k = null;
                    this.f2452c.clear();
                    this.f2453d.clear();
                    this.f2454e.clear();
                    v0.a("AppStreamManager_Core", "releaseVirtualDisplayInternal, set mIVirtualDisplayCallback to null");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
