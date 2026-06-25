package X;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothCsipSetCoordinator;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import com.motorola.mobiledesktop.core.MotoDesktopService;

/* JADX INFO: loaded from: classes.dex */
public final class P0 implements BluetoothProfile.ServiceListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f286a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f287b;

    public /* synthetic */ P0(int i2, Object obj) {
        this.f286a = i2;
        this.f287b = obj;
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public final void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) {
        switch (this.f286a) {
            case 0:
                synchronized (((MotoDesktopService) this.f287b).f2268a0) {
                    try {
                        if (i2 == 1) {
                            ((MotoDesktopService) this.f287b).f2265X = (BluetoothHeadset) bluetoothProfile;
                            v0.b("MotoDesktopService", "get HeadsetProfile proxy");
                        } else if (i2 == 2) {
                            ((MotoDesktopService) this.f287b).f2264W = (BluetoothA2dp) bluetoothProfile;
                            v0.b("MotoDesktopService", "get A2dpProfile proxy");
                        } else if (i2 == 22) {
                            ((MotoDesktopService) this.f287b).f2266Y = (BluetoothLeAudio) bluetoothProfile;
                            v0.b("MotoDesktopService", "get LeaProfile proxy");
                        } else {
                            if (i2 != 25) {
                                return;
                            }
                            ((MotoDesktopService) this.f287b).Z = (BluetoothCsipSetCoordinator) bluetoothProfile;
                            v0.b("MotoDesktopService", "get CsipSetCoordinatorProfile proxy");
                        }
                        ((MotoDesktopService) this.f287b).f2268a0.notify();
                        return;
                    } finally {
                    }
                }
            default:
                Y.i iVar = (Y.i) this.f287b;
                iVar.f402o = (BluetoothHeadset) bluetoothProfile;
                v0.a("i", "getBluetoothHeadset " + iVar.f402o);
                return;
        }
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public final void onServiceDisconnected(int i2) {
        switch (this.f286a) {
            case 0:
                synchronized (((MotoDesktopService) this.f287b)) {
                    try {
                        if (i2 == 1) {
                            ((MotoDesktopService) this.f287b).f2265X = null;
                            v0.b("MotoDesktopService", "destruct HeadsetProfile proxy");
                        } else if (i2 == 2) {
                            ((MotoDesktopService) this.f287b).f2264W = null;
                            v0.b("MotoDesktopService", "destruct A2dpProfile proxy");
                        } else if (i2 == 22) {
                            ((MotoDesktopService) this.f287b).f2266Y = null;
                            v0.b("MotoDesktopService", "destruct LeaProfile proxy");
                        } else {
                            if (i2 != 25) {
                                return;
                            }
                            ((MotoDesktopService) this.f287b).Z = null;
                            v0.b("MotoDesktopService", "destruct CsipSetCoordinatorProfile proxy");
                        }
                        return;
                    } finally {
                    }
                }
            default:
                ((Y.i) this.f287b).f402o = null;
                v0.a("i", "getBluetoothHeadset null");
                return;
        }
    }
}
