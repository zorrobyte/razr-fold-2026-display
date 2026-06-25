package e0;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;

/* JADX INFO: loaded from: classes.dex */
public final class m implements WifiP2pManager.ChannelListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ v f2500a;

    public m(v vVar) {
        this.f2500a = vVar;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ChannelListener
    public final void onChannelDisconnected() {
        Context context;
        v vVar = this.f2500a;
        WifiP2pManager wifiP2pManager = vVar.f2514c;
        if (wifiP2pManager == null || (context = vVar.f2512a) == null) {
            return;
        }
        vVar.f2515d = wifiP2pManager.initialize(context, context.getMainLooper(), vVar.f2516e);
    }
}
