package X;

import android.os.IBinder;
import android.os.Parcel;
import com.motorola.mobiledesktop.core.IRdpPointerStateChangedListener;

/* JADX INFO: loaded from: classes.dex */
public final class V implements IRdpPointerStateChangedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f300a;

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f300a;
    }

    @Override // com.motorola.mobiledesktop.core.IRdpPointerStateChangedListener
    public final void onRdpPointerDisplayChanged(int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IRdpPointerStateChangedListener.DESCRIPTOR);
            parcelObtain.writeInt(i2);
            this.f300a.transact(2, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.motorola.mobiledesktop.core.IRdpPointerStateChangedListener
    public final void onRdpPointerIconTypeChanged(int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IRdpPointerStateChangedListener.DESCRIPTOR);
            parcelObtain.writeInt(i2);
            this.f300a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.motorola.mobiledesktop.core.IRdpPointerStateChangedListener
    public final void onRdpPointerPositionReset(int i2, int i3) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IRdpPointerStateChangedListener.DESCRIPTOR);
            parcelObtain.writeInt(i2);
            parcelObtain.writeInt(i3);
            this.f300a.transact(3, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
