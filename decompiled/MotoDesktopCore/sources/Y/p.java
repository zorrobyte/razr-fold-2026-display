package Y;

import X.v0;
import X.w0;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class p implements MediaRouter2Manager.Callback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ q f420a;

    public p(q qVar) {
        this.f420a = qVar;
    }

    public final void onPreferredFeaturesChanged(String str, List list) {
        v0.a("AudioReceiver", "MediaRouter2Manager.Callback onPreferredFeaturesChanged");
        Iterator it = this.f420a.f423c.iterator();
        while (it.hasNext()) {
            i iVar = ((f) it.next()).f360a;
            if (TextUtils.equals(iVar.f390c, str)) {
                iVar.x();
            }
        }
    }

    public final void onRequestFailed(int i2) {
        v0.a("AudioReceiver", "MediaRouter2Manager.Callback onRequestFailed");
        Iterator it = this.f420a.f423c.iterator();
        while (it.hasNext()) {
            ((f) it.next()).a();
        }
    }

    public final void onRoutesUpdated() {
        boolean z2;
        q qVar = this.f420a;
        List allRoutes = qVar.f422b.getAllRoutes();
        int size = allRoutes != null ? allRoutes.size() : -1;
        w0.b(size, "MediaRouter2Manager.Callback onRoutesUpdated routeSize = ", "AudioReceiver");
        int i2 = qVar.f425e;
        if (i2 == -1 || size == -1 || i2 == size) {
            Iterator it = qVar.f423c.iterator();
            while (it.hasNext()) {
                ((f) it.next()).a();
            }
        } else if (i2 < size) {
            for (f fVar : qVar.f423c) {
                fVar.getClass();
                Iterator it2 = allRoutes.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (r.P(((MediaRoute2Info) it2.next()).getType())) {
                            z2 = true;
                            break;
                        }
                    } else {
                        z2 = false;
                        break;
                    }
                }
                i iVar = fVar.f360a;
                if (z2) {
                    i.a(iVar);
                } else {
                    iVar.x();
                }
            }
        } else {
            Iterator it3 = qVar.f423c.iterator();
            while (it3.hasNext()) {
                i iVar2 = ((f) it3.next()).f360a;
                if (iVar2.f396i || iVar2.f409w != -1 || iVar2.f410x != -1) {
                    Iterator it4 = allRoutes.iterator();
                    while (true) {
                        if (it4.hasNext()) {
                            if (r.P(((MediaRoute2Info) it4.next()).getType())) {
                                break;
                            }
                        } else {
                            Iterator it5 = allRoutes.iterator();
                            while (true) {
                                if (it5.hasNext()) {
                                    if (r.S(((MediaRoute2Info) it5.next()).getType())) {
                                        break;
                                    }
                                } else {
                                    iVar2.f();
                                    break;
                                }
                            }
                        }
                    }
                }
                iVar2.x();
            }
        }
        r.f429c = false;
        qVar.f425e = size;
    }

    public final void onSessionUpdated(RoutingSessionInfo routingSessionInfo) {
        v0.a("AudioReceiver", "MediaRouter2Manager.Callback onSessionUpdated " + routingSessionInfo);
    }

    public final void onTransferFailed(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        v0.a("AudioReceiver", "MediaRouter2Manager.Callback onTransferFailed");
        Iterator it = this.f420a.f423c.iterator();
        while (it.hasNext()) {
            ((f) it.next()).a();
        }
    }

    public final void onTransferred(RoutingSessionInfo routingSessionInfo, RoutingSessionInfo routingSessionInfo2) {
        v0.a("AudioReceiver", "MediaRouter2Manager.Callback onTransferred");
        Iterator it = this.f420a.f423c.iterator();
        while (it.hasNext()) {
            ((f) it.next()).a();
        }
    }
}
