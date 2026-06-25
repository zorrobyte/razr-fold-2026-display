package e0;

import X.s0;
import X.v0;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.WifiDisplay;
import android.hardware.display.WifiDisplayStatus;
import android.media.MediaRouter;
import android.net.NetworkInfo;
import android.os.RemoteException;
import android.view.Display;
import android.widget.Toast;
import com.motorola.mobiledesktop.core.R;
import com.motorola.mobiledesktop.core.bean.MotoWifiDisplay;

/* JADX INFO: loaded from: classes.dex */
public final class w extends BroadcastReceiver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2525a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ A f2526b;

    public /* synthetic */ w(A a2, int i2) {
        this.f2525a = i2;
        this.f2526b = a2;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        A a2 = this.f2526b;
        switch (this.f2525a) {
            case 0:
                String action = intent.getAction();
                v0.a("A", "onReceive:" + action);
                if ("com.motorola.mobiledesktop.core.MIRACAST_QUALITY_POOR".equals(action)) {
                    Context context2 = a2.f2424c;
                    Display display = ((DisplayManager) context2.getSystemService("display")).getDisplay(a2.f2421D);
                    if (display != null) {
                        Toast.makeText(context2.createDisplayContext(display), R.string.wifi_quality_taskbar_content_poor_miracast, 0).show();
                    }
                    a2.f2438q = true;
                    Y.x xVar = a2.f2445y;
                    xVar.removeMessages(2);
                    xVar.removeMessages(1);
                    a2.b();
                }
                break;
            default:
                String action2 = intent.getAction();
                v0.a("A", "onReceive() action=" + action2);
                if (!"android.net.wifi.p2p.CONNECTION_STATE_CHANGE".equals(action2)) {
                    if ("android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED".equals(action2)) {
                        WifiDisplayStatus wifiDisplayStatus = a2.f2422a.getWifiDisplayStatus();
                        if (wifiDisplayStatus != null) {
                            v0.a("A", "onReceive() wifiDisplayStatus=" + wifiDisplayStatus);
                            v0.a("A", "onReceive() mSelectedWifiDisplay=" + a2.f2427f);
                            if (2 == wifiDisplayStatus.getActiveDisplayDeviceState()) {
                                F.f fVar = a2.f2433l;
                                fVar.getClass();
                                v0.a("DisconnectAlarmManager", "cancelDisconnectAlarm");
                                AlarmManager alarmManager = (AlarmManager) fVar.f124b;
                                Intent intent2 = new Intent("com.motorola.mobiledesktop.core.action_disconnect");
                                Context context3 = (Context) fVar.f123a;
                                alarmManager.cancel(PendingIntent.getBroadcast(context3, 0, intent2.setPackage(context3.getPackageName()), 201326592));
                            } else {
                                a2.f2433l.k();
                            }
                            MotoWifiDisplay motoWifiDisplay = a2.f2427f;
                            if (motoWifiDisplay != null) {
                                if (motoWifiDisplay.mIsChromecast) {
                                    MediaRouter.RouteInfo selectedRoute = a2.f2425d.getSelectedRoute();
                                    v0.a("A", "onReceive() selected route=" + selectedRoute);
                                    if (selectedRoute == null || selectedRoute.getSupportedTypes() != 7) {
                                        a2.f2427f = null;
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
                                } else {
                                    WifiDisplay activeDisplay = wifiDisplayStatus.getActiveDisplay();
                                    v0.a("A", "onReceive() activeDisplay=" + activeDisplay);
                                    if (a2.f2446z && activeDisplay == null) {
                                        a2.f2446z = false;
                                        String str = a2.f2427f.mDeviceAddress;
                                        a2.d();
                                        try {
                                            v.b(a2.f2424c).a(str, new y());
                                        } catch (Exception e3) {
                                            v0.c("A", "onReceive deletePersistentGroupByAddress() error", e3);
                                        }
                                        if (a2.f2426e != null) {
                                            try {
                                                v0.a("A", "onReceive() onConnectError()");
                                                ((s0) a2.f2426e).a();
                                            } catch (RemoteException e4) {
                                                e4.printStackTrace();
                                            }
                                        } else {
                                            v0.a("A", "onReceive() mWifiDisplayCallback is null ");
                                        }
                                    }
                                }
                            }
                        } else {
                            v0.a("A", "onReceive() wifiDisplayStatus is null");
                        }
                        A.a(a2, "CHANGE_WIFI_DISPLAY_STATUS");
                    }
                    break;
                } else {
                    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                    v0.a("A", "onReceive() WIFI_P2P_CONNECTION_CHANGED_ACTION networkInfo:" + networkInfo);
                    MotoWifiDisplay motoWifiDisplay2 = a2.f2427f;
                    if (motoWifiDisplay2 != null && !motoWifiDisplay2.mIsChromecast && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTING) {
                        a2.f2446z = true;
                        break;
                    }
                }
                break;
        }
    }
}
