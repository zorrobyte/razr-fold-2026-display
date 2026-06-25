package j0;

import android.net.wifi.p2p.WifiP2pGroup;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public final class j implements k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f2741a;

    public final void a(WifiP2pGroup wifiP2pGroup) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.wifidirect.IGroupInfoListener");
            if (wifiP2pGroup != null) {
                parcelObtain.writeInt(1);
                wifiP2pGroup.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            this.f2741a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f2741a;
    }
}
