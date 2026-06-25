package X;

import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: renamed from: X.y, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0045y implements InterfaceC0046z {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f342a;

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f342a;
    }

    public final void onImeFocusStateChanged(int i2, boolean z2) {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IDesktopInputMethodListener");
            parcelObtain.writeInt(i2);
            parcelObtain.writeInt(z2 ? 1 : 0);
            this.f342a.transact(1, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }
}
