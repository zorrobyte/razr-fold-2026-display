package e0;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class r implements WifiP2pManager.ConnectionInfoListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ j0.e f2505a;

    public r(j0.e eVar) {
        this.f2505a = eVar;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener
    public final void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
        try {
            ((j0.d) this.f2505a).a(wifiP2pInfo);
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
    }
}
