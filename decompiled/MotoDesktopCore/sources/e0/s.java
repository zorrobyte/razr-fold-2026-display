package e0;

import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class s implements WifiP2pManager.NetworkInfoListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ j0.m f2506a;

    public s(j0.m mVar) {
        this.f2506a = mVar;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.NetworkInfoListener
    public final void onNetworkInfoAvailable(NetworkInfo networkInfo) {
        try {
            ((j0.l) this.f2506a).a(networkInfo);
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
    }
}
