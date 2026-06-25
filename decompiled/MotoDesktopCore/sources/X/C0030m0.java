package X;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: renamed from: X.m0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0030m0 implements InterfaceC0034o0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f327a;

    public final void a(ArrayList arrayList) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV3");
            int size = arrayList.size();
            parcelObtain.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                Parcelable parcelable = (Parcelable) arrayList.get(i2);
                if (parcelable != null) {
                    parcelObtain.writeInt(1);
                    parcelable.writeToParcel(parcelObtain, 0);
                } else {
                    parcelObtain.writeInt(0);
                }
            }
            this.f327a.transact(4, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f327a;
    }

    public final void b(int i2, int i3, String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV3");
            parcelObtain.writeInt(i2);
            parcelObtain.writeString(str);
            parcelObtain.writeInt(i3);
            this.f327a.transact(5, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void c(int i2, String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV3");
            parcelObtain.writeString(str);
            parcelObtain.writeInt(i2);
            this.f327a.transact(6, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void d(int i2, int i3, String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV3");
            parcelObtain.writeInt(i2);
            parcelObtain.writeString(str);
            parcelObtain.writeInt(i3);
            this.f327a.transact(7, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
