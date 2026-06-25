package X;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: X.l, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractBinderC0027l extends Binder implements InterfaceC0029m {
    public static InterfaceC0029m a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.mobiledesktop.core.IAudioSettingChangeCallback");
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof InterfaceC0029m)) {
            return (InterfaceC0029m) iInterfaceQueryLocalInterface;
        }
        C0025k c0025k = new C0025k();
        c0025k.f326a = iBinder;
        return c0025k;
    }
}
