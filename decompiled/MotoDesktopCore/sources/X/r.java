package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public abstract class r extends Binder implements InterfaceC0039s {
    public static InterfaceC0039s a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.ICallStateListener");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof InterfaceC0039s)) {
            return (InterfaceC0039s) iInterfaceQueryLocalInterface;
        }
        C0037q c0037q = new C0037q();
        c0037q.f330a = iBinder;
        return c0037q;
    }
}
