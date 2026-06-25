package X;

import android.net.wifi.SoftApCapability;
import android.net.wifi.SoftApInfo;
import android.net.wifi.WifiClient;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.appcompat.app.AbstractC0054a;
import java.util.List;

/* JADX INFO: renamed from: X.a0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0006a0 implements InterfaceC0010c0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f304a;

    public final void a(WifiClient wifiClient, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ISoftApCallback");
            AbstractC0054a.w(parcelObtain, wifiClient);
            parcelObtain.writeInt(i2);
            this.f304a.transact(5, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f304a;
    }

    public final void b(SoftApCapability softApCapability) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ISoftApCallback");
            AbstractC0054a.w(parcelObtain, softApCapability);
            this.f304a.transact(4, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void c(List list) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ISoftApCallback");
            if (list == null) {
                parcelObtain.writeInt(-1);
            } else {
                int size = list.size();
                parcelObtain.writeInt(size);
                for (int i2 = 0; i2 < size; i2++) {
                    AbstractC0054a.w(parcelObtain, (Parcelable) list.get(i2));
                }
            }
            this.f304a.transact(2, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void d(SoftApInfo softApInfo) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ISoftApCallback");
            AbstractC0054a.w(parcelObtain, softApInfo);
            this.f304a.transact(3, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void e(int i2, int i3) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ISoftApCallback");
            parcelObtain.writeInt(i2);
            parcelObtain.writeInt(i3);
            this.f304a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
