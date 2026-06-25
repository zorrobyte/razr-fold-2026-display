package X;

import android.content.ClipData;
import android.os.IBinder;
import android.os.Parcel;
import com.motorola.mobiledesktop.core.bean.RfDragShadow;

/* JADX INFO: loaded from: classes.dex */
public final class A implements C {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f257a;

    public final void a(long j2, ClipData clipData, RfDragShadow rfDragShadow, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IDragDropCallback");
            parcelObtain.writeLong(j2);
            if (clipData != null) {
                parcelObtain.writeInt(1);
                clipData.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            parcelObtain.writeInt(1);
            rfDragShadow.writeToParcel(parcelObtain, 0);
            parcelObtain.writeInt(i2);
            this.f257a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f257a;
    }

    public final void onEventForRf(int i2, String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IDragDropCallback");
            parcelObtain.writeInt(i2);
            parcelObtain.writeString(str);
            this.f257a.transact(2, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
