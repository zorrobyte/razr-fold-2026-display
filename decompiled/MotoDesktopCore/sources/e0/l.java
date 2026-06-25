package e0;

import X.v0;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pGroupList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class l implements WifiP2pManager.PersistentGroupInfoListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2496a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ String f2497b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ v f2498c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f2499d;

    public /* synthetic */ l(v vVar, String str, Object obj, int i2) {
        this.f2496a = i2;
        this.f2498c = vVar;
        this.f2497b = str;
        this.f2499d = obj;
    }

    public final void onPersistentGroupInfoAvailable(WifiP2pGroupList wifiP2pGroupList) {
        Object obj = this.f2499d;
        v vVar = this.f2498c;
        String str = this.f2497b;
        switch (this.f2496a) {
            case 0:
                v0.a("v", "WifiP2pGroupList:" + wifiP2pGroupList);
                String upperCase = str != null ? str.toUpperCase() : str;
                int networkId = -1;
                for (WifiP2pGroup wifiP2pGroup : wifiP2pGroupList.getGroupList()) {
                    if (wifiP2pGroup.getNetworkName().toUpperCase().contains(upperCase)) {
                        v0.a("v", "Device Name:" + str + " Found WifiP2pGroup:" + wifiP2pGroup);
                        networkId = wifiP2pGroup.getNetworkId();
                        vVar.f2514c.deletePersistentGroup(vVar.f2515d, networkId, null);
                    }
                }
                try {
                    j0.c cVar = (j0.c) obj;
                    if (cVar != null) {
                        if (networkId == -1) {
                            ((j0.a) cVar).a(1);
                        } else {
                            ((j0.a) cVar).b();
                        }
                    }
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                    return;
                }
                break;
            default:
                for (WifiP2pGroup wifiP2pGroup2 : wifiP2pGroupList.getGroupList()) {
                    if (wifiP2pGroup2.getOwner() != null && str.equalsIgnoreCase(wifiP2pGroup2.getOwner().deviceAddress)) {
                        vVar.f2514c.deletePersistentGroup(vVar.f2515d, wifiP2pGroup2.getNetworkId(), (WifiP2pManager.ActionListener) obj);
                    }
                }
                break;
        }
    }
}
