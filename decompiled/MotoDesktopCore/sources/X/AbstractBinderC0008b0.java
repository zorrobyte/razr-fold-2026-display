package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: X.b0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractBinderC0008b0 extends Binder implements InterfaceC0010c0 {
    public static InterfaceC0010c0 a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.ISoftApCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof InterfaceC0010c0)) {
            return (InterfaceC0010c0) iInterfaceQueryLocalInterface;
        }
        C0006a0 c0006a0 = new C0006a0();
        c0006a0.f304a = iBinder;
        return c0006a0;
    }
}
