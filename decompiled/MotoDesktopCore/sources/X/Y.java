package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public abstract class Y extends Binder implements Z {
    public static Z a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IReceiverCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof Z)) {
            return (Z) iInterfaceQueryLocalInterface;
        }
        X x2 = new X();
        x2.f301a = iBinder;
        return x2;
    }
}
