package e0;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class o implements WifiP2pManager.DeviceInfoListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ j0.g f2502a;

    public o(j0.g gVar) {
        this.f2502a = gVar;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.DeviceInfoListener
    public final void onDeviceInfoAvailable(WifiP2pDevice wifiP2pDevice) {
        try {
            ((j0.f) this.f2502a).a(wifiP2pDevice);
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
    }
}
