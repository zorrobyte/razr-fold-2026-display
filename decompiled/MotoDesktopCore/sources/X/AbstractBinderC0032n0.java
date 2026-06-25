package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: X.n0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractBinderC0032n0 extends Binder implements InterfaceC0034o0 {
    public static InterfaceC0034o0 a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV3");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof InterfaceC0034o0)) {
            return (InterfaceC0034o0) iInterfaceQueryLocalInterface;
        }
        C0030m0 c0030m0 = new C0030m0();
        c0030m0.f327a = iBinder;
        return c0030m0;
    }
}
