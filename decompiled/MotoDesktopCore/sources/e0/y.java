package e0;

import X.v0;
import X.w0;
import android.net.wifi.p2p.WifiP2pManager;

/* JADX INFO: loaded from: classes.dex */
public final class y implements WifiP2pManager.ActionListener {
    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
    public final void onFailure(int i2) {
        w0.b(i2, "onReceive deletePersistentGroupByAddress() onFailure() reason=", "A");
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
    public final void onSuccess() {
        v0.a("A", "onReceive deletePersistentGroupByAddress() onSuccess()");
    }
}
