package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: X.e0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractBinderC0014e0 extends Binder implements InterfaceC0016f0 {
    public static InterfaceC0016f0 a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IStartTetheringCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof InterfaceC0016f0)) {
            return (InterfaceC0016f0) iInterfaceQueryLocalInterface;
        }
        C0012d0 c0012d0 = new C0012d0();
        c0012d0.f313a = iBinder;
        return c0012d0;
    }
}
