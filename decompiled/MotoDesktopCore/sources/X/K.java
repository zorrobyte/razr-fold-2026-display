package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public abstract class K extends Binder implements L {
    public static L a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IPackageChangeCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof L)) {
            return (L) iInterfaceQueryLocalInterface;
        }
        J j2 = new J();
        j2.f273a = iBinder;
        return j2;
    }
}
