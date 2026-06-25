package X;

import android.hardware.input.InputManager;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.motorola.mobiledesktop.core.MotoDesktopService;

/* JADX INFO: loaded from: classes.dex */
public final class N0 implements InputManager.OnPointerStateChangedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int f281a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MotoDesktopService f282b;

    public N0(MotoDesktopService motoDesktopService, int i2) {
        this.f282b = motoDesktopService;
        this.f281a = i2;
    }

    public final void onPointerDisplayChanged(int i2) {
        StringBuilder sb = new StringBuilder("onPointerDisplayChanged: ");
        int i3 = this.f281a;
        sb.append(i3);
        sb.append(" ");
        sb.append(i2);
        v0.a("MotoDesktopService", sb.toString());
        MotoDesktopService motoDesktopService = this.f282b;
        motoDesktopService.f2290o.put(i3, Integer.valueOf(i2));
        for (T t2 : motoDesktopService.h(i3)) {
            try {
                ((S) t2).a(i2);
            } catch (DeadObjectException unused) {
                motoDesktopService.g(t2);
            } catch (RemoteException unused2) {
            }
        }
    }

    public final void onPointerIconTypeChanged(int i2) {
        String[] strArr = MotoDesktopService.w0;
        int i3 = this.f281a;
        MotoDesktopService motoDesktopService = this.f282b;
        for (T t2 : motoDesktopService.h(i3)) {
            try {
                ((S) t2).b(i2);
            } catch (DeadObjectException unused) {
                motoDesktopService.g(t2);
            } catch (RemoteException unused2) {
            }
        }
    }

    public final void onPointerPositionReset(int i2, int i3) {
        String[] strArr = MotoDesktopService.w0;
        int i4 = this.f281a;
        MotoDesktopService motoDesktopService = this.f282b;
        for (T t2 : motoDesktopService.h(i4)) {
            try {
                ((S) t2).c(i2, i3);
            } catch (DeadObjectException unused) {
                motoDesktopService.g(t2);
            } catch (RemoteException unused2) {
            }
        }
    }
}
