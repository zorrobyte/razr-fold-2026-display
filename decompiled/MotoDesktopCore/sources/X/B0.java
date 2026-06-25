package X;

import Y.RunnableC0049c;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.FragmentActivity;
import com.motorola.mobiledesktop.core.MotoDesktopService;
import com.motorola.mobiledesktop.core.desktop.DesktopFragment;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class B0 extends e0.c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f259a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f260b;

    public /* synthetic */ B0(int i2, Object obj) {
        this.f259a = i2;
        this.f260b = obj;
    }

    @Override // e0.c
    public void a(int i2, String str, String str2) {
        switch (this.f259a) {
            case 1:
                new Handler(Looper.getMainLooper()).post(new b0.a(this, i2, str, str2));
                break;
            case 2:
                FragmentActivity activity = ((DesktopFragment) this.f260b).getActivity();
                Objects.requireNonNull(activity);
                activity.runOnUiThread(new RunnableC0011d(this, i2, 1));
                break;
        }
    }

    @Override // e0.c
    public void b(int i2) {
        int i3 = 1;
        Object obj = this.f260b;
        switch (this.f259a) {
            case 0:
                w0.b(i2, "onMiracastAdded ", "MotoDesktopService");
                MotoDesktopService motoDesktopService = (MotoDesktopService) obj;
                if (motoDesktopService.f2270b0 == null) {
                    v0.a("MotoDesktopService", "registerMiracastReceiver");
                    motoDesktopService.f2270b0 = new K0(0, motoDesktopService);
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.motorola.mobiledesktop.core.action_disconnect");
                    intentFilter.addAction("android.intent.action.SCREEN_ON");
                    intentFilter.addAction("android.intent.action.SCREEN_OFF");
                    motoDesktopService.registerReceiver(motoDesktopService.f2270b0, intentFilter, "com.motorola.permission.ACCESS_DESKTOP", null, 4);
                }
                e0.A a2 = motoDesktopService.f2294t;
                if (a2.f2421D == -1) {
                    a2.f2430i.registerNetworkCallback(new NetworkRequest.Builder().addTransportType(1).build(), a2.f2419B);
                    a2.f2428g.requestGroupInfo(a2.f2429h, new e0.t(i3, a2));
                    a2.f2421D = i2;
                    a2.c();
                }
                break;
            case 3:
                v0.a("A", "onMiracastAdded()");
                e0.A a3 = (e0.A) obj;
                if (a3.f2442v == null) {
                    a3.f2442v = new K0(5, this);
                    IntentFilter intentFilter2 = new IntentFilter();
                    intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
                    intentFilter2.addDataScheme("package");
                    a3.f2424c.registerReceiverForAllUsers(a3.f2442v, intentFilter2, null, null);
                }
                break;
        }
    }

    @Override // e0.c
    public void c(int i2) {
        Object obj = this.f260b;
        switch (this.f259a) {
            case 0:
                String[] strArr = MotoDesktopService.w0;
                MotoDesktopService motoDesktopService = (MotoDesktopService) obj;
                motoDesktopService.q();
                e0.A a2 = motoDesktopService.f2294t;
                a2.getClass();
                v0.e("A", "clearConnectWifiDisplay");
                a2.f2427f = null;
                e0.A a3 = motoDesktopService.f2294t;
                if (a3.f2421D != -1) {
                    a3.f2430i.unregisterNetworkCallback(a3.f2419B);
                    a3.f2432k.removeDesktopIcon("wifi_quality", a3.f2421D);
                    a3.f2421D = -1;
                    return;
                }
                return;
            case 3:
                v0.a("A", "onMiracastRemoved()");
                e0.A a4 = (e0.A) obj;
                BroadcastReceiver broadcastReceiver = a4.f2442v;
                if (broadcastReceiver != null) {
                    try {
                        try {
                            a4.f2424c.unregisterReceiver(broadcastReceiver);
                        } catch (IllegalArgumentException e2) {
                            v0.h("A", "unregisterReceiver mPackageChanged error", e2);
                        }
                    } finally {
                        a4.f2442v = null;
                    }
                    break;
                }
                a4.f2443w.submit(new RunnableC0049c(1, this));
                return;
            default:
                return;
        }
    }
}
