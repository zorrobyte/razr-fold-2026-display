package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: X.w, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractBinderC0043w extends Binder implements InterfaceC0044x {
    public static InterfaceC0044x a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.ICorePermissionDialogCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof InterfaceC0044x)) {
            return (InterfaceC0044x) iInterfaceQueryLocalInterface;
        }
        C0042v c0042v = new C0042v();
        c0042v.f333a = iBinder;
        return c0042v;
    }
}
