package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public abstract class B extends Binder implements C {
    public static C a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IDragDropCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof C)) {
            return (C) iInterfaceQueryLocalInterface;
        }
        A a2 = new A();
        a2.f257a = iBinder;
        return a2;
    }
}
