package j0;

import android.net.wifi.p2p.WifiP2pDevice;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public final class f implements g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f2739a;

    public final void a(WifiP2pDevice wifiP2pDevice) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.wifidirect.IDeviceInfoListener");
            if (wifiP2pDevice != null) {
                parcelObtain.writeInt(1);
                wifiP2pDevice.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            this.f2739a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f2739a;
    }
}
