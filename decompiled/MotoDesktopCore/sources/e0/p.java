package e0;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class p implements WifiP2pManager.DiscoveryStateListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ j0.i f2503a;

    public p(j0.i iVar) {
        this.f2503a = iVar;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.DiscoveryStateListener
    public final void onDiscoveryStateAvailable(int i2) {
        try {
            ((j0.h) this.f2503a).a(i2);
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
    }
}
