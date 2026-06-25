package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public abstract class H extends Binder implements I {
    public static I a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.INearbyshareCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof I)) {
            return (I) iInterfaceQueryLocalInterface;
        }
        G g2 = new G();
        g2.f269a = iBinder;
        return g2;
    }
}
