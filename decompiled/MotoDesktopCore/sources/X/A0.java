package X;

import android.net.wifi.SoftApCapability;
import android.net.wifi.SoftApInfo;
import android.net.wifi.WifiClient;
import android.net.wifi.WifiManager;
import android.os.RemoteException;
import com.motorola.mobiledesktop.core.MotoDesktopService;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class A0 implements WifiManager.SoftApCallback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MotoDesktopService f258a;

    public A0(MotoDesktopService motoDesktopService) {
        this.f258a = motoDesktopService;
    }

    public final void onBlockedClientConnecting(WifiClient wifiClient, int i2) {
        synchronized (this.f258a.f2291p) {
            try {
                InterfaceC0010c0 interfaceC0010c0 = this.f258a.u0;
                if (interfaceC0010c0 != null) {
                    try {
                        ((C0006a0) interfaceC0010c0).a(wifiClient, i2);
                    } catch (RemoteException unused) {
                        this.f258a.r();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onCapabilityChanged(SoftApCapability softApCapability) {
        synchronized (this.f258a.f2291p) {
            try {
                InterfaceC0010c0 interfaceC0010c0 = this.f258a.u0;
                if (interfaceC0010c0 != null) {
                    try {
                        ((C0006a0) interfaceC0010c0).b(softApCapability);
                    } catch (RemoteException unused) {
                        this.f258a.r();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onConnectedClientsChanged(List list) {
        synchronized (this.f258a.f2291p) {
            try {
                InterfaceC0010c0 interfaceC0010c0 = this.f258a.u0;
                if (interfaceC0010c0 != null) {
                    try {
                        ((C0006a0) interfaceC0010c0).c(list);
                    } catch (RemoteException unused) {
                        this.f258a.r();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onInfoChanged(SoftApInfo softApInfo) {
        synchronized (this.f258a.f2291p) {
            try {
                InterfaceC0010c0 interfaceC0010c0 = this.f258a.u0;
                if (interfaceC0010c0 != null) {
                    try {
                        ((C0006a0) interfaceC0010c0).d(softApInfo);
                    } catch (RemoteException unused) {
                        this.f258a.r();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onStateChanged(int i2, int i3) {
        synchronized (this.f258a.f2291p) {
            try {
                InterfaceC0010c0 interfaceC0010c0 = this.f258a.u0;
                if (interfaceC0010c0 != null) {
                    try {
                        ((C0006a0) interfaceC0010c0).e(i2, i3);
                    } catch (RemoteException unused) {
                        this.f258a.r();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
