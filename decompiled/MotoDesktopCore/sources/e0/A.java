package e0;

import X.B0;
import X.C0005a;
import X.Q0;
import X.s0;
import X.u0;
import X.v0;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.hardware.display.WifiDisplayStatus;
import android.media.MediaRouter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Looper;
import android.widget.Toast;
import com.motorola.mobiledesktop.core.R;
import com.motorola.mobiledesktop.core.bean.MotoWifiDisplay;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public final class A {

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public static final /* synthetic */ int f2417E = 0;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public WifiP2pGroup f2420C;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final DisplayManager f2422a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public WifiDisplayStatus f2423b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Context f2424c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final MediaRouter f2425d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public u0 f2426e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public MotoWifiDisplay f2427f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final WifiP2pManager f2428g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final WifiP2pManager.Channel f2429h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final ConnectivityManager f2430i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final WifiManager f2431j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final StatusBarManager f2432k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final F.f f2433l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public w f2434m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public C0005a f2435n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public z f2436o;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public final boolean f2440t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public d f2441u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public BroadcastReceiver f2442v;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public int f2437p = 0;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public boolean f2438q = false;
    public boolean r = false;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public String f2439s = null;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public final ExecutorService f2443w = Executors.newSingleThreadExecutor();

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public final B0 f2444x = new B0(3, this);

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public final Y.x f2445y = new Y.x(this, Looper.myLooper());

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public boolean f2446z = false;

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public final w f2418A = new w(this, 0);

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public final x f2419B = new x(this);

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public int f2421D = -1;

    public A(Context context) {
        this.f2424c = null;
        Context applicationContext = context.getApplicationContext();
        this.f2424c = applicationContext;
        this.f2422a = (DisplayManager) applicationContext.getSystemService("display");
        this.f2425d = (MediaRouter) applicationContext.getSystemService("media_router");
        applicationContext.getPackageManager();
        WifiP2pManager wifiP2pManager = (WifiP2pManager) applicationContext.getSystemService("wifip2p");
        this.f2428g = wifiP2pManager;
        this.f2429h = wifiP2pManager.initialize(applicationContext, Looper.getMainLooper(), null);
        this.f2427f = e();
        this.f2433l = new F.f(context.getApplicationContext(), 4);
        this.f2430i = (ConnectivityManager) context.getSystemService("connectivity");
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        this.f2431j = wifiManager;
        this.f2432k = (StatusBarManager) context.getSystemService("statusbar");
        this.f2440t = wifiManager.isDualBandSimultaneousSupported();
    }

    public static void a(A a2, String str) {
        a2.getClass();
        v0.a("A", "refreshDevices changeType=" + str + ", mWifiDisplayCallback=" + a2.f2426e);
        try {
            MotoWifiDisplay motoWifiDisplay = a2.f2427f;
            if (motoWifiDisplay != null && !motoWifiDisplay.mIsChromecast) {
                v0.a("A", "refreshDevices connecting status, don`t need to query the displays");
            } else if (a2.f2426e != null) {
                ((s0) a2.f2426e).b(a2.g());
            } else {
                v0.a("A", "refreshDevices mWifiDisplayCallback is null");
            }
        } catch (Exception e2) {
            v0.h("A", "refreshDevices error", e2);
        }
    }

    public static ArrayList f(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            MotoWifiDisplay motoWifiDisplay = (MotoWifiDisplay) it.next();
            if (motoWifiDisplay.mIsAvailable && motoWifiDisplay.mCanConnect) {
                arrayList2.add(motoWifiDisplay);
            } else {
                motoWifiDisplay.mIsAvailable = false;
                arrayList3.add(motoWifiDisplay);
            }
        }
        if (arrayList3.size() > 0) {
            arrayList2.addAll(arrayList3);
        }
        return arrayList2;
    }

    public static String h(MediaRouter.RouteInfo routeInfo) {
        if (routeInfo == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(routeInfo.getName());
        sb.append('/');
        sb.append(routeInfo.getDescription());
        sb.append('@');
        sb.append(routeInfo.getDeviceAddress());
        sb.append(",status=");
        sb.append(routeInfo.getStatus());
        sb.append(",getStatusCode=");
        sb.append(routeInfo.getStatusCode());
        sb.append(",supportedType=");
        sb.append(routeInfo.getSupportedTypes());
        if (routeInfo.isDefault()) {
            sb.append(",default");
        }
        if (routeInfo.isEnabled()) {
            sb.append(",enabled");
        }
        if (routeInfo.isConnecting()) {
            sb.append(",connecting");
        }
        if (routeInfo.isSelected()) {
            sb.append(",selected");
        }
        sb.append(",id=");
        sb.append(routeInfo.getTag());
        return sb.toString();
    }

    public final void b() {
        boolean z2 = this.r;
        Context context = this.f2424c;
        if (!z2) {
            this.r = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.motorola.mobiledesktop.core.MIRACAST_QUALITY_POOR");
            context.registerReceiver(this.f2418A, intentFilter, 4);
        }
        int i2 = this.f2421D;
        if (i2 == -1) {
            return;
        }
        int i3 = R.drawable.ic_wifi_quality_poor;
        String string = context.getString(R.string.wifi_quality_taskbar_content_poor_miracast);
        Intent intent = new Intent("com.motorola.mobiledesktop.core.MIRACAST_QUALITY_POOR");
        Context context2 = this.f2424c;
        intent.setPackage(context2.getPackageName());
        this.f2432k.addDesktopIcon("wifi_quality", i2, i3, 0, string, PendingIntent.getBroadcast(context2, 0, intent, 201326592));
    }

    public final void c() {
        WifiP2pGroup wifiP2pGroup;
        WifiP2pGroup wifiP2pGroup2;
        WifiInfo connectionInfo = this.f2431j.getConnectionInfo();
        if (this.f2420C == null || connectionInfo == null) {
            return;
        }
        StringBuilder sb = new StringBuilder("mDbsSupported:");
        boolean z2 = this.f2440t;
        sb.append(z2);
        sb.append(" mWifiP2pGroup:");
        sb.append(this.f2420C.getFrequency());
        sb.append(" wifiInfo:");
        sb.append(connectionInfo.getFrequency());
        v0.a("A", sb.toString());
        boolean zEquals = "<unknown ssid>".equals(connectionInfo.getSSID());
        Context context = this.f2424c;
        Y.x xVar = this.f2445y;
        if (zEquals || (((wifiP2pGroup = this.f2420C) != null && wifiP2pGroup.getFrequency() == connectionInfo.getFrequency()) || !(!z2 || (wifiP2pGroup2 = this.f2420C) == null || wifiP2pGroup2.getFrequency() / 1000 == connectionInfo.getFrequency() / 1000))) {
            xVar.removeMessages(2);
            xVar.removeMessages(1);
            this.f2432k.removeDesktopIcon("wifi_quality", this.f2421D);
            if (this.r) {
                this.r = false;
                context.unregisterReceiver(this.f2418A);
                return;
            }
            return;
        }
        if (d.d(context).f2468f) {
            String str = this.f2439s;
            if (str == null || !str.equals(connectionInfo.getSSID())) {
                this.f2439s = connectionInfo.getSSID();
                Toast.makeText(context, R.string.wifi_quality_taskbar_content_poor_miracast, 0).show();
                return;
            }
            return;
        }
        b();
        String str2 = this.f2439s;
        if (str2 == null || !str2.equals(connectionInfo.getSSID())) {
            this.f2438q = false;
            this.f2439s = connectionInfo.getSSID();
        }
        xVar.removeMessages(2);
        xVar.removeMessages(1);
        xVar.sendEmptyMessageDelayed(2, 500L);
    }

    public final void d() {
        v0.e("A", "disconnectWifiDisplay mSelectedWifiDisplay:" + this.f2427f);
        MotoWifiDisplay motoWifiDisplay = this.f2427f;
        if (motoWifiDisplay == null || !motoWifiDisplay.mIsChromecast) {
            this.f2427f = null;
            this.f2446z = false;
            this.f2422a.disconnectWifiDisplay();
            return;
        }
        this.f2427f = null;
        MediaRouter mediaRouter = this.f2425d;
        MediaRouter.RouteInfo selectedRoute = mediaRouter.getSelectedRoute();
        v0.a("A", "disconnectWifiDisplay getSelectedRoute:" + selectedRoute);
        v0.a("A", "getConnectedWifiDisplay getSelectedRoute getName:" + ((Object) selectedRoute.getName()) + " getStatus:" + ((Object) selectedRoute.getStatus()) + " getStatusCode:" + selectedRoute.getStatusCode() + " getSupportedTypes:" + selectedRoute.getSupportedTypes() + " getDescription:" + ((Object) selectedRoute.getDescription()) + " getDeviceType:" + selectedRoute.getDeviceType() + " getDeviceAddress:" + selectedRoute.getDeviceAddress() + " isSelected:" + selectedRoute.isSelected());
        mediaRouter.getFallbackRoute().select();
    }

    public final MotoWifiDisplay e() {
        this.f2423b = this.f2422a.getWifiDisplayStatus();
        v0.a("A", "getConnectedWifiDisplay mWifiDisplayStatus=" + this.f2423b);
        if (2 == this.f2423b.getActiveDisplayState()) {
            MotoWifiDisplay motoWifiDisplay = new MotoWifiDisplay();
            motoWifiDisplay.setWifidisplay(this.f2423b.getActiveDisplay());
            motoWifiDisplay.mActiveDisplayState = 2;
            v0.a("A", "getConnectedWifiDisplay MotoWifiDisplay=" + motoWifiDisplay);
            return motoWifiDisplay;
        }
        Context context = this.f2424c;
        boolean z2 = Q0.f288a;
        if (!context.getPackageManager().hasSystemFeature("com.motorola.readyforservice.qs")) {
            MediaRouter.RouteInfo selectedRoute = this.f2425d.getSelectedRoute();
            v0.a("A", "getConnectedWifiDisplay getSelectedRoute=" + selectedRoute);
            if (selectedRoute != null) {
                String strD = Y.r.D(selectedRoute, MediaRouter.RouteInfo.class.getName());
                v0.a("A", "getConnectedWifiDisplay MyChromecast getName:" + ((Object) selectedRoute.getName()) + " routeId:" + strD + " getStatus:" + ((Object) selectedRoute.getStatus()) + " getStatusCode:" + selectedRoute.getStatusCode() + " getSupportedTypes:" + selectedRoute.getSupportedTypes() + " getDescription:" + ((Object) selectedRoute.getDescription()) + " getDeviceType:" + selectedRoute.getDeviceType() + " getDeviceAddress:" + selectedRoute.getDeviceAddress() + " isSelected:" + selectedRoute.isSelected());
                if (selectedRoute.getSupportedTypes() == 7 && selectedRoute.getStatusCode() == 6) {
                    MotoWifiDisplay motoWifiDisplay2 = new MotoWifiDisplay();
                    motoWifiDisplay2.mListType = 1;
                    motoWifiDisplay2.mDeviceName = selectedRoute.getName().toString();
                    motoWifiDisplay2.mDeviceAddress = strD;
                    boolean z3 = selectedRoute.getStatusCode() != 4;
                    motoWifiDisplay2.mIsAvailable = z3;
                    motoWifiDisplay2.mCanConnect = z3;
                    motoWifiDisplay2.mIsChromecast = true;
                    motoWifiDisplay2.mActiveDisplayState = 0;
                    v0.a("A", "getConnectedWifiDisplay chromecast connected:" + motoWifiDisplay2);
                    return motoWifiDisplay2;
                }
            }
        }
        v0.g("A", "getConnectedWifiDisplay return null");
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.ArrayList g() {
        /*
            Method dump skipped, instruction units count: 675
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: e0.A.g():java.util.ArrayList");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void i() {
        z zVar;
        v0.e("A", "stopWifiDisplayScan");
        w wVar = this.f2434m;
        Context context = this.f2424c;
        if (wVar != null) {
            v0.a("A", "stopWifiDisplayScan unregisterReceiver:" + this.f2434m);
            try {
                try {
                    context.unregisterReceiver(this.f2434m);
                } catch (IllegalArgumentException e2) {
                    v0.h("A", "stopWifiDisplayScan unregisterReceiver error", e2);
                }
            } finally {
                this.f2434m = null;
            }
        }
        if (this.f2435n != null) {
            v0.a("A", "stopWifiDisplayScan unregisterContentObserver");
            try {
                try {
                    context.getContentResolver().unregisterContentObserver(this.f2435n);
                } catch (IllegalArgumentException e3) {
                    v0.h("A", "stopWifiDisplayScan unregisterContentObserver error", e3);
                }
            } finally {
                this.f2435n = null;
            }
        }
        MediaRouter mediaRouter = this.f2425d;
        if (mediaRouter == null || (zVar = this.f2436o) == null) {
            v0.a("A", "stopWifiDisplayScan mRouter is null or mRouterCallback is not null");
            return;
        }
        mediaRouter.removeCallback(zVar);
        this.f2436o = null;
        v0.a("A", "stopWifiDisplayScan MediaRouter removeCallback");
    }
}
