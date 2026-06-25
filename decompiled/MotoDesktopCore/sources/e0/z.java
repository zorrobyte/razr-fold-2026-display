package e0;

import X.s0;
import X.v0;
import android.media.MediaRouter;
import android.os.RemoteException;
import com.motorola.mobiledesktop.core.bean.MotoWifiDisplay;

/* JADX INFO: loaded from: classes.dex */
public final class z extends MediaRouter.SimpleCallback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ A f2528a;

    public z(A a2) {
        this.f2528a = a2;
    }

    @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
    public final void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        v0.a("A", "onRouteAdded(): " + A.h(routeInfo));
    }

    @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
    public final void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        MotoWifiDisplay motoWifiDisplay;
        StringBuilder sb = new StringBuilder("onRouteChanged(): routeinfo=");
        sb.append(A.h(routeInfo));
        sb.append(", mSelectedWifiDisplay:");
        A a2 = this.f2528a;
        sb.append(a2.f2427f);
        v0.a("A", sb.toString());
        if (routeInfo != null && (motoWifiDisplay = a2.f2427f) != null && motoWifiDisplay.mIsChromecast && routeInfo.getSupportedTypes() == 7 && routeInfo.getName().equals(a2.f2427f.mDeviceName)) {
            if (routeInfo.getStatusCode() == 2 || routeInfo.getStatusCode() == 6) {
                if (routeInfo.getStatusCode() == 6) {
                    A.a(a2, "CHANGE_ROUTES");
                    return;
                }
                return;
            }
            a2.d();
            if (a2.f2426e != null) {
                try {
                    v0.a("A", "onReceive() onConnectError()");
                    ((s0) a2.f2426e).a();
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            } else {
                v0.a("A", "onReceive() mWifiDisplayCallback is null ");
            }
            A.a(a2, "CHANGE_ROUTES");
        }
    }

    @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
    public final void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
        v0.a("A", "onRouteRemoved(): " + A.h(routeInfo));
    }

    @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
    public final void onRouteSelected(MediaRouter mediaRouter, int i2, MediaRouter.RouteInfo routeInfo) {
        v0.a("A", "onRouteSelected(): " + A.h(routeInfo) + ", mWifiDisplayCallback=" + this.f2528a.f2426e);
    }

    @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
    public final void onRouteUnselected(MediaRouter mediaRouter, int i2, MediaRouter.RouteInfo routeInfo) {
        v0.a("A", "onRouteUnselected(): " + A.h(routeInfo));
    }
}
