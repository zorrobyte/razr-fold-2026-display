package e0;

import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class q implements WifiP2pManager.PeerListListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ j0.o f2504a;

    public q(j0.o oVar) {
        this.f2504a = oVar;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
    public final void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
        try {
            ((j0.n) this.f2504a).a(wifiP2pDeviceList);
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
    }
}
