package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import com.motorola.mobiledesktop.core.IRdpPointerStateChangedListener;

/* JADX INFO: loaded from: classes.dex */
public abstract class W extends Binder implements IRdpPointerStateChangedListener {
    public static IRdpPointerStateChangedListener a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRdpPointerStateChangedListener.DESCRIPTOR);
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRdpPointerStateChangedListener)) {
            return (IRdpPointerStateChangedListener) iInterfaceQueryLocalInterface;
        }
        V v2 = new V();
        v2.f300a = iBinder;
        return v2;
    }
}
