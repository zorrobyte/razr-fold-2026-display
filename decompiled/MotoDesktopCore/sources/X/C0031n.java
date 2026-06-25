package X;

import android.os.IBinder;
import android.os.Parcel;
import android.text.TextUtils;

/* JADX INFO: renamed from: X.n, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0031n implements InterfaceC0035p {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f328a;

    public final void a(CharSequence charSequence) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ICDMCallback");
            if (charSequence != null) {
                parcelObtain.writeInt(1);
                TextUtils.writeToParcel(charSequence, parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            this.f328a.transact(3, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            parcelObtain2.recycle();
            parcelObtain.recycle();
        } catch (Throwable th) {
            parcelObtain2.recycle();
            parcelObtain.recycle();
            throw th;
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f328a;
    }
}
