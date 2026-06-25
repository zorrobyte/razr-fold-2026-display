package android.support.v4.os;

import E.a;
import a.BinderC0053c;
import a.InterfaceC0052b;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new a(12);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public InterfaceC0052b f465a;

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        synchronized (this) {
            try {
                if (this.f465a == null) {
                    this.f465a = new BinderC0053c(this);
                }
                parcel.writeStrongBinder(this.f465a.asBinder());
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
