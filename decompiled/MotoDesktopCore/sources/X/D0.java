package X;

import android.net.TetheringManager;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class D0 implements TetheringManager.StartTetheringCallback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f261a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ IInterface f262b;

    public /* synthetic */ D0(IInterface iInterface, int i2) {
        this.f261a = i2;
        this.f262b = iInterface;
    }

    public final void onTetheringFailed(int i2) {
        switch (this.f261a) {
            case 0:
                w0.b(i2, "onTetheringFailed - error:", "MotoDesktopService");
                F f2 = (F) this.f262b;
                if (f2 != null) {
                    try {
                        ((E) f2).a(false);
                        return;
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                return;
            default:
                w0.b(i2, "onTetheringFailed - error:", "MotoDesktopService");
                InterfaceC0016f0 interfaceC0016f0 = (InterfaceC0016f0) this.f262b;
                if (interfaceC0016f0 != null) {
                    try {
                        C0012d0 c0012d0 = (C0012d0) interfaceC0016f0;
                        c0012d0.getClass();
                        Parcel parcelObtain = Parcel.obtain();
                        Parcel parcelObtain2 = Parcel.obtain();
                        try {
                            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IStartTetheringCallback");
                            parcelObtain.writeInt(i2);
                            c0012d0.f313a.transact(2, parcelObtain, parcelObtain2, 0);
                            parcelObtain2.readException();
                            parcelObtain2.recycle();
                            parcelObtain.recycle();
                            return;
                        } catch (Throwable th) {
                            parcelObtain2.recycle();
                            parcelObtain.recycle();
                            throw th;
                        }
                    } catch (Exception unused) {
                        return;
                    }
                }
                return;
        }
    }

    public final void onTetheringStarted() {
        switch (this.f261a) {
            case 0:
                v0.a("MotoDesktopService", "onTetheringStarted");
                F f2 = (F) this.f262b;
                if (f2 != null) {
                    try {
                        ((E) f2).a(true);
                        return;
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                return;
            default:
                v0.a("MotoDesktopService", "onTetheringStarted");
                InterfaceC0016f0 interfaceC0016f0 = (InterfaceC0016f0) this.f262b;
                if (interfaceC0016f0 != null) {
                    try {
                        C0012d0 c0012d0 = (C0012d0) interfaceC0016f0;
                        c0012d0.getClass();
                        Parcel parcelObtain = Parcel.obtain();
                        Parcel parcelObtain2 = Parcel.obtain();
                        try {
                            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IStartTetheringCallback");
                            c0012d0.f313a.transact(1, parcelObtain, parcelObtain2, 0);
                            parcelObtain2.readException();
                            parcelObtain2.recycle();
                            parcelObtain.recycle();
                            return;
                        } catch (Throwable th) {
                            parcelObtain2.recycle();
                            parcelObtain.recycle();
                            throw th;
                        }
                    } catch (Exception unused) {
                        return;
                    }
                }
                return;
        }
    }
}
