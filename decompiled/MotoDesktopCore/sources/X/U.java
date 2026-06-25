package X;

import android.os.IBinder;
import android.os.Parcel;
import com.motorola.mobiledesktop.core.IRdpPimaryClipChangedListener;

/* JADX INFO: loaded from: classes.dex */
public final class U implements IRdpPimaryClipChangedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f299a;

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f299a;
    }

    @Override // com.motorola.mobiledesktop.core.IRdpPimaryClipChangedListener
    public final void onRdpPrimaryClipChanged() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IRdpPimaryClipChangedListener.DESCRIPTOR);
            this.f299a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
