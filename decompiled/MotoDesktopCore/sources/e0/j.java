package e0;

import X.J;
import X.L;
import X.Q0;
import X.v0;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.text.TextUtils;
import com.motorola.mobiledesktop.core.MotoDesktopService;

/* JADX INFO: loaded from: classes.dex */
public final class j extends BroadcastReceiver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2492a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ i f2493b;

    public /* synthetic */ j(i iVar, int i2) {
        this.f2492a = i2;
        this.f2493b = iVar;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        switch (this.f2492a) {
            case 0:
                v0.a("PackageChangeManager", "mBroadcastReceiver onReceive");
                try {
                    L l2 = (L) this.f2493b.f2486a;
                    if (l2 != null) {
                        ((J) l2).a(intent);
                    }
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                    return;
                }
                break;
            case 1:
                v0.a("PackageChangeManager", "mBroadcastReceiver2 onReceive");
                try {
                    L l3 = (L) this.f2493b.f2486a;
                    if (l3 != null) {
                        ((J) l3).a(intent);
                    }
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                    return;
                }
                break;
            default:
                v0.a("PackageChangeManager", "mBroadcastReceiverPackageChanged onReceive");
                k kVar = (k) this.f2493b.f2487b;
                if (kVar != null) {
                    kVar.getClass();
                    if (intent != null) {
                        try {
                            String action = intent.getAction();
                            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                            boolean zEquals = TextUtils.equals(action, "android.intent.action.PACKAGE_ADDED");
                            int i2 = 0;
                            MotoDesktopService motoDesktopService = (MotoDesktopService) kVar.f2495a;
                            if (zEquals) {
                                String[] strArr = MotoDesktopService.w0;
                                while (i2 < 2) {
                                    if (schemeSpecificPart.equals(strArr[i2])) {
                                        motoDesktopService.s();
                                    }
                                    i2++;
                                }
                                if ("com.motorola.mobiledesktop".equals(schemeSpecificPart)) {
                                    Q0.l(motoDesktopService, Q0.f(motoDesktopService));
                                }
                            } else if (TextUtils.equals(action, "android.intent.action.PACKAGE_REMOVED")) {
                                String[] strArr2 = MotoDesktopService.w0;
                                while (i2 < 2) {
                                    String str = strArr2[i2];
                                    if (schemeSpecificPart.equals(str)) {
                                        motoDesktopService.s0.remove(str);
                                        v0.e("MotoDesktopService", "exist client uid: " + motoDesktopService.s0.toString());
                                    }
                                    i2++;
                                }
                                if ("com.motorola.mobiledesktop".equals(schemeSpecificPart)) {
                                    Q0.l(motoDesktopService, Q0.f(motoDesktopService));
                                }
                            }
                        } catch (Exception e4) {
                            v0.a("MotoDesktopService", "onPackageChanged ,e:" + e4);
                        }
                        break;
                    }
                }
                break;
        }
    }
}
