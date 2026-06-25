package X;

import android.content.ComponentName;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public final class M implements O {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f278a;

    public final void a(ComponentName componentName, IBinder iBinder) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IPlatformServiceConnection");
            Y.r.b(parcelObtain, componentName);
            parcelObtain.writeStrongBinder(iBinder);
            this.f278a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f278a;
    }
}
