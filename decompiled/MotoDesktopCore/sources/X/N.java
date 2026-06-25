package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public abstract class N extends Binder implements O {
    public static O a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IPlatformServiceConnection");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof O)) {
            return (O) iInterfaceQueryLocalInterface;
        }
        M m2 = new M();
        m2.f278a = iBinder;
        return m2;
    }
}
