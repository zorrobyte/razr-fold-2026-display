package X;

import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public final class E implements F {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f263a;

    public final void a(boolean z2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.INcmCallback");
            parcelObtain.writeInt(z2 ? 1 : 0);
            this.f263a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f263a;
    }
}
