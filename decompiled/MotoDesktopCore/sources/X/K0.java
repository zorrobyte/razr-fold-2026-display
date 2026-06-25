package X;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.os.RemoteException;
import android.text.TextUtils;
import com.motorola.mobiledesktop.core.MotoDesktopService;
import com.motorola.mobiledesktop.core.uinput.EventType;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class K0 extends BroadcastReceiver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f275a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object f276b;

    public /* synthetic */ K0(int i2, Object obj) {
        this.f275a = i2;
        this.f276b = obj;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        byte b2 = -1;
        switch (this.f275a) {
            case 0:
                String action = intent.getAction();
                boolean zEquals = "android.intent.action.SCREEN_ON".equals(action);
                MotoDesktopService motoDesktopService = (MotoDesktopService) this.f276b;
                if (zEquals) {
                    v0.a("MotoDesktopService", "receive ACTION_SCREEN_ON");
                    F.f fVar = motoDesktopService.f2296v;
                    fVar.getClass();
                    v0.a("DisconnectAlarmManager", "cancelDisconnectAlarm");
                    AlarmManager alarmManager = (AlarmManager) fVar.f124b;
                    Intent intent2 = new Intent("com.motorola.mobiledesktop.core.action_disconnect");
                    Context context2 = (Context) fVar.f123a;
                    alarmManager.cancel(PendingIntent.getBroadcast(context2, 0, intent2.setPackage(context2.getPackageName()), 201326592));
                    return;
                }
                if ("android.intent.action.SCREEN_OFF".equals(action)) {
                    v0.a("MotoDesktopService", "receive ACTION_SCREEN_OFF");
                    motoDesktopService.f2296v.k();
                    return;
                } else {
                    if ("com.motorola.mobiledesktop.core.action_disconnect".equals(action)) {
                        v0.a("MotoDesktopService", "receive ACTION_DISCONNECT");
                        motoDesktopService.f2294t.d();
                        return;
                    }
                    return;
                }
            case 1:
                Z z2 = (Z) this.f276b;
                if (z2 != null) {
                    try {
                        ((X) z2).a(intent);
                        return;
                    } catch (RemoteException e2) {
                        throw new RuntimeException(e2);
                    }
                }
                return;
            case 2:
                if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction())) {
                    try {
                        int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                        for (Y.f fVar2 : ((Y.q) this.f276b).f423c) {
                            fVar2.getClass();
                            if (intExtra == 0 || intExtra == 6) {
                                fVar2.f360a.x();
                            }
                            break;
                        }
                        return;
                    } catch (Exception e3) {
                        v0.a("AudioReceiver", "receive VOLUME_CHANGED_ACTION error = " + e3);
                        return;
                    }
                }
                if ("android.intent.action.HEADSET_PLUG".equals(intent.getAction())) {
                    boolean z3 = intent.getIntExtra("state", 0) == 1;
                    v0.a("AudioReceiver", "onHeadsetPlugChange isPluggedIn = " + z3);
                    for (Y.f fVar3 : ((Y.q) this.f276b).f423c) {
                        if (z3) {
                            Y.i.a(fVar3.f360a);
                        } else {
                            Y.i iVar = fVar3.f360a;
                            if (iVar.f396i || iVar.f409w != -1 || iVar.f410x != -1) {
                                synchronized (iVar.f389b) {
                                    try {
                                        for (Y.t tVar : iVar.f395h) {
                                            if (tVar instanceof Y.s) {
                                                if (((Y.s) tVar).f436j == 3) {
                                                }
                                            } else if (Y.r.P(tVar.c())) {
                                            }
                                            break;
                                        }
                                        iVar.f();
                                    } finally {
                                    }
                                }
                            }
                        }
                    }
                    return;
                }
                if ("android.media.STREAM_MUTE_CHANGED_ACTION".equals(intent.getAction())) {
                    try {
                        int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                        v0.a("AudioReceiver", "onAudioMuteChange streamType = " + intExtra2);
                        Y.r.f429c = true;
                        for (Y.f fVar4 : ((Y.q) this.f276b).f423c) {
                            if (intExtra2 == 3) {
                                fVar4.f360a.x();
                            } else {
                                fVar4.getClass();
                            }
                            break;
                        }
                        return;
                    } catch (Exception e4) {
                        v0.a("AudioReceiver", "receive STREAM_MUTE_CHANGED_ACTION error = " + e4);
                        return;
                    }
                }
                if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(intent.getAction())) {
                    v0.a("AudioReceiver", "onDevicePolicyManagerStateChange");
                    Iterator it = ((Y.q) this.f276b).f423c.iterator();
                    while (it.hasNext()) {
                        ((Y.f) it.next()).f360a.m();
                    }
                    return;
                }
                if (!"com.motorola.server.telecom.action.CALL_AUDIO_ROUTE_CHANGE".equals(intent.getAction())) {
                    if ("com.motorola.mobiledesktop.action.call_audio_switch".equals(intent.getAction())) {
                        int intExtra3 = intent.getIntExtra("switch_to", 0);
                        w0.b(intExtra3, "onCallAudioRoutingChange routeTo = ", "AudioReceiver");
                        Iterator it2 = ((Y.q) this.f276b).f423c.iterator();
                        while (it2.hasNext()) {
                            Y.i iVar2 = ((Y.f) it2.next()).f360a;
                            if (iVar2.f399l) {
                                if (intExtra3 == 0) {
                                    iVar2.h();
                                    iVar2.x();
                                } else {
                                    iVar2.c(5);
                                }
                            }
                        }
                        return;
                    }
                    return;
                }
                int intExtra4 = intent.getIntExtra("com.motorola.server.telecom.extra.CALL_AUDIO_ROUTE_TYPE", 8);
                w0.b(intExtra4, "onDialCallAudioRouteChange routeType = ", "AudioReceiver");
                Iterator it3 = ((Y.q) this.f276b).f423c.iterator();
                while (it3.hasNext()) {
                    Y.i iVar3 = ((Y.f) it3.next()).f360a;
                    iVar3.i(false);
                    if (iVar3.f396i) {
                        v0.a("i", "onDialCallAudioRouteChange unMute");
                        AudioManager audioManager = iVar3.f398k;
                        audioManager.setStreamMute(0, true);
                        audioManager.setStreamMute(6, true);
                        audioManager.setStreamMute(0, false);
                        audioManager.setStreamMute(6, false);
                    }
                    if (iVar3.f388a.b()) {
                        if (intExtra4 == 8) {
                            Y.g gVar = iVar3.f388a;
                            Objects.requireNonNull(gVar);
                            if (gVar.b()) {
                                gVar.f367g = 1;
                            }
                            iVar3.f388a.c();
                        } else if (intExtra4 == 1) {
                            Y.g gVar2 = iVar3.f388a;
                            Objects.requireNonNull(gVar2);
                            if (gVar2.b()) {
                                gVar2.f367g = 0;
                            }
                            iVar3.f388a.c();
                        } else if (intExtra4 == 4) {
                            Y.g gVar3 = iVar3.f388a;
                            Objects.requireNonNull(gVar3);
                            if (gVar3.b()) {
                                gVar3.f367g = 2;
                            }
                            iVar3.f388a.c();
                        }
                    }
                    iVar3.x();
                }
                return;
            case 3:
                androidx.appcompat.app.m mVar = (androidx.appcompat.app.m) this.f276b;
                boolean zL = mVar.f573a.l();
                if (zL != mVar.f574b) {
                    mVar.f574b = zL;
                    mVar.f577e.a();
                    return;
                }
                return;
            case EventType.EVENT_MSC /* 4 */:
                if (intent != null) {
                    String action2 = intent.getAction();
                    v0.a("v", "onReceive:" + action2);
                    try {
                        switch (action2.hashCode()) {
                            case -1772632330:
                                if (action2.equals("android.net.wifi.p2p.CONNECTION_STATE_CHANGE")) {
                                    b2 = 4;
                                }
                                break;
                            case -1566767901:
                                if (action2.equals("android.net.wifi.p2p.THIS_DEVICE_CHANGED")) {
                                    b2 = 1;
                                }
                                break;
                            case -1394739139:
                                if (action2.equals("android.net.wifi.p2p.PEERS_CHANGED")) {
                                    b2 = 3;
                                }
                                break;
                            case -1331207498:
                                if (action2.equals("android.net.wifi.p2p.DISCOVERY_STATE_CHANGE")) {
                                    b2 = 2;
                                }
                                break;
                            case 1695662461:
                                if (action2.equals("android.net.wifi.p2p.STATE_CHANGED")) {
                                    b2 = 0;
                                }
                                break;
                        }
                        e0.v vVar = (e0.v) this.f276b;
                        if (b2 == 0) {
                            int intExtra5 = intent.getIntExtra("wifi_p2p_state", 1);
                            j0.q qVar = vVar.f2517f;
                            if (qVar != null) {
                                ((j0.p) qVar).a(intExtra5);
                                return;
                            }
                            return;
                        }
                        if (b2 == 1) {
                            WifiP2pDevice wifiP2pDevice = (WifiP2pDevice) intent.getParcelableExtra("wifiP2pDevice");
                            j0.g gVar4 = vVar.f2518g;
                            if (gVar4 != null) {
                                ((j0.f) gVar4).a(wifiP2pDevice);
                                return;
                            }
                            return;
                        }
                        if (b2 == 2) {
                            int intExtra6 = intent.getIntExtra("discoveryState", 1);
                            j0.i iVar4 = vVar.f2519h;
                            if (iVar4 != null) {
                                ((j0.h) iVar4).a(intExtra6);
                                return;
                            }
                            return;
                        }
                        if (b2 == 3) {
                            WifiP2pDeviceList wifiP2pDeviceList = (WifiP2pDeviceList) intent.getParcelableExtra("wifiP2pDeviceList");
                            j0.o oVar = vVar.f2520i;
                            if (oVar != null) {
                                ((j0.n) oVar).a(wifiP2pDeviceList);
                                return;
                            }
                            return;
                        }
                        if (b2 != 4) {
                            v0.g("v", "receive action invalid: ".concat(action2));
                            return;
                        }
                        if (vVar.f2521j != null) {
                            WifiP2pInfo wifiP2pInfo = (WifiP2pInfo) intent.getParcelableExtra("wifiP2pInfo");
                            v0.a("v", "WIFI_P2P_CONNECTION_CHANGED_ACTION connection:" + wifiP2pInfo);
                            ((j0.d) vVar.f2521j).a(wifiP2pInfo);
                        }
                        if (vVar.f2523l != null) {
                            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                            v0.a("v", "WIFI_P2P_CONNECTION_CHANGED_ACTION networkInfo:" + networkInfo);
                            ((j0.l) vVar.f2523l).a(networkInfo);
                        }
                        if (vVar.f2522k != null) {
                            WifiP2pGroup wifiP2pGroup = (WifiP2pGroup) intent.getParcelableExtra("p2pGroupInfo");
                            v0.a("v", "WIFI_P2P_CONNECTION_CHANGED_ACTION group:" + wifiP2pGroup);
                            if (wifiP2pGroup != null) {
                                if (wifiP2pGroup.isGroupOwner()) {
                                    Collection<WifiP2pDevice> clientList = wifiP2pGroup.getClientList();
                                    if (!clientList.isEmpty()) {
                                        String str = clientList.iterator().next().deviceAddress;
                                        v0.a("v", "WIFI_P2P_CONNECTION_CHANGED_ACTION cache GO, client deviceAddress:" + str + ", networkId:" + wifiP2pGroup.getNetworkId());
                                        e0.k kVar = vVar.f2513b;
                                        int networkId = wifiP2pGroup.getNetworkId();
                                        SharedPreferences.Editor editorEdit = ((SharedPreferences) kVar.f2495a).edit();
                                        editorEdit.putInt(str, networkId);
                                        editorEdit.commit();
                                    }
                                } else {
                                    WifiP2pDevice owner = wifiP2pGroup.getOwner();
                                    v0.a("v", "WIFI_P2P_CONNECTION_CHANGED_ACTION cache GC, Owner deviceAddress:" + owner.deviceAddress + ", networkId:" + wifiP2pGroup.getNetworkId());
                                    e0.k kVar2 = vVar.f2513b;
                                    String str2 = owner.deviceAddress;
                                    int networkId2 = wifiP2pGroup.getNetworkId();
                                    SharedPreferences.Editor editorEdit2 = ((SharedPreferences) kVar2.f2495a).edit();
                                    editorEdit2.putInt(str2, networkId2);
                                    editorEdit2.commit();
                                }
                            }
                            ((j0.j) vVar.f2522k).a(wifiP2pGroup);
                            return;
                        }
                        return;
                    } catch (RemoteException e5) {
                        v0.g("v", "RemoteException: " + e5.getMessage());
                        return;
                    }
                }
                return;
            default:
                v0.a("A", "mPackageChanged onReceive");
                if (intent != null && TextUtils.equals(intent.getAction(), "android.intent.action.PACKAGE_REMOVED") && "com.motorola.mobiledesktop".equals(intent.getData().getSchemeSpecificPart())) {
                    ((e0.A) ((B0) this.f276b).f260b).d();
                    return;
                }
                return;
        }
    }
}
