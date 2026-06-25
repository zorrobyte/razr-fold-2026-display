package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public abstract class t0 extends Binder implements u0 {
    public static u0 a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IWifiDisplayCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof u0)) {
            return (u0) iInterfaceQueryLocalInterface;
        }
        s0 s0Var = new s0();
        s0Var.f331a = iBinder;
        return s0Var;
    }
}
