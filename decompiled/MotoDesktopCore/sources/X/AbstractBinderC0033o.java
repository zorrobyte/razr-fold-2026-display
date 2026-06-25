package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: X.o, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractBinderC0033o extends Binder implements InterfaceC0035p {
    public static InterfaceC0035p a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.ICDMCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof InterfaceC0035p)) {
            return (InterfaceC0035p) iInterfaceQueryLocalInterface;
        }
        C0031n c0031n = new C0031n();
        c0031n.f328a = iBinder;
        return c0031n;
    }
}
