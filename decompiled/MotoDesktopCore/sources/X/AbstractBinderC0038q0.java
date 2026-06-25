package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: X.q0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractBinderC0038q0 extends Binder implements r0 {
    public static r0 a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IVirtualDisplayCallbackV4");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof r0)) {
            return (r0) iInterfaceQueryLocalInterface;
        }
        C0036p0 c0036p0 = new C0036p0();
        c0036p0.f329a = iBinder;
        return c0036p0;
    }
}
