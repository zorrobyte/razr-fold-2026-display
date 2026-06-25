package X;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcel;
import android.os.RemoteException;
import com.motorola.mobiledesktop.core.MotoDesktopService;

/* JADX INFO: loaded from: classes.dex */
public final class x0 extends ContentObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ InterfaceC0041u f340a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MotoDesktopService f341b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public x0(MotoDesktopService motoDesktopService, Handler handler, InterfaceC0041u interfaceC0041u) {
        super(handler);
        this.f341b = motoDesktopService;
        this.f340a = interfaceC0041u;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2, Uri uri, int i2) {
        InterfaceC0041u interfaceC0041u = this.f340a;
        try {
            C0040t c0040t = (C0040t) interfaceC0041u;
            c0040t.getClass();
            Parcel parcelObtain = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IContentObserver");
                parcelObtain.writeInt(z2 ? 1 : 0);
                if (uri != null) {
                    parcelObtain.writeInt(1);
                    uri.writeToParcel(parcelObtain, 0);
                } else {
                    parcelObtain.writeInt(0);
                }
                parcelObtain.writeInt(i2);
                c0040t.f332a.transact(1, parcelObtain, null, 1);
                parcelObtain.recycle();
            } catch (Throwable th) {
                parcelObtain.recycle();
                throw th;
            }
        } catch (RemoteException unused) {
            MotoDesktopService.c(this.f341b, interfaceC0041u);
        }
    }
}
