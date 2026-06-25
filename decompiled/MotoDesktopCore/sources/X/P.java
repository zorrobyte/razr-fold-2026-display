package X;

import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public final class P implements Q {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f285a;

    public final void a(int i2, int i3) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IPointerPositionChangedListener");
            parcelObtain.writeInt(i2);
            parcelObtain.writeInt(i3);
            this.f285a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f285a;
    }
}
