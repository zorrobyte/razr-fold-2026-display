package X;

import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: renamed from: X.q, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0037q implements InterfaceC0039s {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f330a;

    public final void a(int i2, String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ICallStateListener");
            parcelObtain.writeInt(i2);
            parcelObtain.writeString(str);
            this.f330a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f330a;
    }
}
