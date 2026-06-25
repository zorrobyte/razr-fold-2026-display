package d0;

import X.C0024j0;
import X.C0030m0;
import X.C0036p0;
import X.InterfaceC0028l0;
import X.InterfaceC0034o0;
import X.r0;
import android.hardware.display.VirtualDisplay;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class a extends VirtualDisplay.Callback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2411a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2412b = -1;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public IInterface f2413c;

    public /* synthetic */ a(IInterface iInterface, int i2) {
        this.f2411a = i2;
        this.f2413c = iInterface;
    }

    @Override // android.hardware.display.VirtualDisplay.Callback
    public final void onPaused() {
        int i2;
        Parcel parcelObtain;
        Parcel parcelObtain2;
        int i3;
        int i4;
        switch (this.f2411a) {
            case 0:
                InterfaceC0034o0 interfaceC0034o0 = (InterfaceC0034o0) this.f2413c;
                if (interfaceC0034o0 == null || (i2 = this.f2412b) == -1) {
                    return;
                }
                try {
                    C0030m0 c0030m0 = (C0030m0) interfaceC0034o0;
                    c0030m0.getClass();
                    parcelObtain = Parcel.obtain();
                    parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV3");
                        parcelObtain.writeInt(i2);
                        c0030m0.f327a.transact(1, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        return;
                    } finally {
                    }
                } catch (RemoteException unused) {
                    this.f2413c = null;
                    return;
                }
            case 1:
                r0 r0Var = (r0) this.f2413c;
                if (r0Var == null || (i3 = this.f2412b) == -1) {
                    return;
                }
                try {
                    C0036p0 c0036p0 = (C0036p0) r0Var;
                    c0036p0.getClass();
                    parcelObtain = Parcel.obtain();
                    parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV4");
                        parcelObtain.writeInt(i3);
                        c0036p0.f329a.transact(1, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        return;
                    } finally {
                    }
                } catch (RemoteException unused2) {
                    this.f2413c = null;
                    return;
                }
            default:
                InterfaceC0028l0 interfaceC0028l0 = (InterfaceC0028l0) this.f2413c;
                if (interfaceC0028l0 == null || (i4 = this.f2412b) == -1) {
                    return;
                }
                try {
                    C0024j0 c0024j0 = (C0024j0) interfaceC0028l0;
                    c0024j0.getClass();
                    parcelObtain = Parcel.obtain();
                    parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV2");
                        parcelObtain.writeInt(i4);
                        c0024j0.f325a.transact(1, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        return;
                    } finally {
                    }
                } catch (RemoteException unused3) {
                    this.f2413c = null;
                    return;
                }
        }
    }

    @Override // android.hardware.display.VirtualDisplay.Callback
    public final void onResumed() {
        int i2;
        Parcel parcelObtain;
        Parcel parcelObtain2;
        int i3;
        int i4;
        switch (this.f2411a) {
            case 0:
                InterfaceC0034o0 interfaceC0034o0 = (InterfaceC0034o0) this.f2413c;
                if (interfaceC0034o0 == null || (i2 = this.f2412b) == -1) {
                    return;
                }
                try {
                    C0030m0 c0030m0 = (C0030m0) interfaceC0034o0;
                    c0030m0.getClass();
                    parcelObtain = Parcel.obtain();
                    parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV3");
                        parcelObtain.writeInt(i2);
                        c0030m0.f327a.transact(2, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        return;
                    } finally {
                    }
                } catch (RemoteException unused) {
                    this.f2413c = null;
                    return;
                }
            case 1:
                r0 r0Var = (r0) this.f2413c;
                if (r0Var == null || (i3 = this.f2412b) == -1) {
                    return;
                }
                try {
                    C0036p0 c0036p0 = (C0036p0) r0Var;
                    c0036p0.getClass();
                    parcelObtain = Parcel.obtain();
                    parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV4");
                        parcelObtain.writeInt(i3);
                        c0036p0.f329a.transact(2, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        return;
                    } finally {
                    }
                } catch (RemoteException unused2) {
                    this.f2413c = null;
                    return;
                }
            default:
                InterfaceC0028l0 interfaceC0028l0 = (InterfaceC0028l0) this.f2413c;
                if (interfaceC0028l0 == null || (i4 = this.f2412b) == -1) {
                    return;
                }
                try {
                    C0024j0 c0024j0 = (C0024j0) interfaceC0028l0;
                    c0024j0.getClass();
                    parcelObtain = Parcel.obtain();
                    parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV2");
                        parcelObtain.writeInt(i4);
                        c0024j0.f325a.transact(2, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        return;
                    } finally {
                    }
                } catch (RemoteException unused3) {
                    this.f2413c = null;
                    return;
                }
        }
    }

    @Override // android.hardware.display.VirtualDisplay.Callback
    public final void onStopped() {
        int i2;
        Parcel parcelObtain;
        Parcel parcelObtain2;
        int i3;
        int i4;
        switch (this.f2411a) {
            case 0:
                InterfaceC0034o0 interfaceC0034o0 = (InterfaceC0034o0) this.f2413c;
                if (interfaceC0034o0 == null || (i2 = this.f2412b) == -1) {
                    return;
                }
                try {
                    C0030m0 c0030m0 = (C0030m0) interfaceC0034o0;
                    c0030m0.getClass();
                    parcelObtain = Parcel.obtain();
                    parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV3");
                        parcelObtain.writeInt(i2);
                        c0030m0.f327a.transact(3, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        return;
                    } finally {
                    }
                } catch (RemoteException unused) {
                    this.f2413c = null;
                    return;
                }
            case 1:
                r0 r0Var = (r0) this.f2413c;
                if (r0Var == null || (i3 = this.f2412b) == -1) {
                    return;
                }
                try {
                    C0036p0 c0036p0 = (C0036p0) r0Var;
                    c0036p0.getClass();
                    parcelObtain = Parcel.obtain();
                    parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV4");
                        parcelObtain.writeInt(i3);
                        c0036p0.f329a.transact(3, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        return;
                    } finally {
                    }
                } catch (RemoteException unused2) {
                    this.f2413c = null;
                    return;
                }
            default:
                InterfaceC0028l0 interfaceC0028l0 = (InterfaceC0028l0) this.f2413c;
                if (interfaceC0028l0 == null || (i4 = this.f2412b) == -1) {
                    return;
                }
                try {
                    C0024j0 c0024j0 = (C0024j0) interfaceC0028l0;
                    c0024j0.getClass();
                    parcelObtain = Parcel.obtain();
                    parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV2");
                        parcelObtain.writeInt(i4);
                        c0024j0.f325a.transact(3, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        return;
                    } finally {
                    }
                } catch (RemoteException unused3) {
                    this.f2413c = null;
                    return;
                }
        }
    }
}
