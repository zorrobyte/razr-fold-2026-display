package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: X.g, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractBinderC0017g extends Binder implements InterfaceC0019h {
    public static InterfaceC0019h a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IActivityStateChangedCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof InterfaceC0019h)) {
            return (InterfaceC0019h) iInterfaceQueryLocalInterface;
        }
        C0015f c0015f = new C0015f();
        c0015f.f321a = iBinder;
        return c0015f;
    }
}
