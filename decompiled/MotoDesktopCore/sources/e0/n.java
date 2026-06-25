package e0;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class n implements WifiP2pManager.P2pStateListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ j0.q f2501a;

    public n(j0.q qVar) {
        this.f2501a = qVar;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.P2pStateListener
    public final void onP2pStateAvailable(int i2) {
        try {
            ((j0.p) this.f2501a).a(i2);
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
    }
}
