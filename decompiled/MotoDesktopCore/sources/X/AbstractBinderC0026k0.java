package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: X.k0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractBinderC0026k0 extends Binder implements InterfaceC0028l0 {
    public static InterfaceC0028l0 a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV2");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof InterfaceC0028l0)) {
            return (InterfaceC0028l0) iInterfaceQueryLocalInterface;
        }
        C0024j0 c0024j0 = new C0024j0();
        c0024j0.f325a = iBinder;
        return c0024j0;
    }
}
