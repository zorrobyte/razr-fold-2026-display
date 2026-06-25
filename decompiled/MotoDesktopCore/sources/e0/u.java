package e0;

import X.v0;
import X.w0;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class u implements WifiP2pManager.ActionListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2509a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object f2510b;

    public /* synthetic */ u() {
    }

    public u(String str) {
        this.f2510b = str;
    }

    public static u a(j0.c cVar) {
        if (cVar == null) {
            return null;
        }
        u uVar = new u();
        uVar.f2510b = cVar;
        return uVar;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
    public final void onFailure(int i2) {
        switch (this.f2509a) {
            case 0:
                try {
                    ((j0.a) ((j0.c) this.f2510b)).a(i2);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                    return;
                }
                break;
            default:
                w0.b(i2, "forgetWifiDisplay deletePersistentGroupByAddress() onFailure() reason=", "A");
                break;
        }
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
    public final void onSuccess() {
        switch (this.f2509a) {
            case 0:
                try {
                    ((j0.a) ((j0.c) this.f2510b)).b();
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                    return;
                }
                break;
            default:
                v0.a("A", "forgetWifiDisplay deletePersistentGroupByAddress() onSuccess()" + ((String) this.f2510b));
                break;
        }
    }
}
