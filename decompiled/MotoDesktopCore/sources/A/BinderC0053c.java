package a;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.support.v4.os.ResultReceiver;

/* JADX INFO: renamed from: a.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class BinderC0053c extends Binder implements InterfaceC0052b {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final /* synthetic */ int f462b = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ResultReceiver f463a;

    public BinderC0053c(ResultReceiver resultReceiver) {
        this.f463a = resultReceiver;
        attachInterface(this, "android.support.v4.os.IResultReceiver");
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) {
        if (i2 != 1) {
            if (i2 != 1598968902) {
                return super.onTransact(i2, parcel, parcel2, i3);
            }
            parcel2.writeString("android.support.v4.os.IResultReceiver");
            return true;
        }
        parcel.enforceInterface("android.support.v4.os.IResultReceiver");
        parcel.readInt();
        if (parcel.readInt() != 0) {
        }
        this.f463a.getClass();
        return true;
    }
}
