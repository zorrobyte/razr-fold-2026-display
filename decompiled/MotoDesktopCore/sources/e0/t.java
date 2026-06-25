package e0;

import X.v0;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class t implements WifiP2pManager.GroupInfoListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2507a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f2508b;

    public /* synthetic */ t(int i2, Object obj) {
        this.f2507a = i2;
        this.f2508b = obj;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.GroupInfoListener
    public final void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
        Object obj = this.f2508b;
        switch (this.f2507a) {
            case 0:
                try {
                    ((j0.j) ((j0.k) obj)).a(wifiP2pGroup);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                    return;
                }
                break;
            default:
                v0.a("A", "onGroupInfoAvailable:" + wifiP2pGroup);
                ((A) obj).f2420C = wifiP2pGroup;
                break;
        }
    }
}
