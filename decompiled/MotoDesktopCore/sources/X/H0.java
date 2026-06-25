package X;

import android.hardware.input.InputManager;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.motorola.mobiledesktop.core.IRdpPointerStateChangedListener;
import com.motorola.mobiledesktop.core.MotoDesktopService;

/* JADX INFO: loaded from: classes.dex */
public final class H0 implements InputManager.OnPointerStateChangedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MotoDesktopService f271a;

    public H0(MotoDesktopService motoDesktopService) {
        this.f271a = motoDesktopService;
    }

    public final void onPointerDisplayChanged(int i2) {
        IRdpPointerStateChangedListener iRdpPointerStateChangedListener;
        w0.b(i2, "Rdp onPointerDisplayChanged: ", "MotoDesktopService");
        synchronized (this.f271a.f2291p) {
            try {
                iRdpPointerStateChangedListener = this.f271a.f2284j;
            } catch (DeadObjectException unused) {
                v0.a("MotoDesktopService", "IRdpPointerStateChangedListener died");
                this.f271a.f2284j = null;
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            if (iRdpPointerStateChangedListener != null) {
                iRdpPointerStateChangedListener.onRdpPointerDisplayChanged(i2);
            }
        }
    }

    public final void onPointerIconTypeChanged(int i2) {
        IRdpPointerStateChangedListener iRdpPointerStateChangedListener;
        w0.b(i2, "Rdp onPointerIconTypeChanged: ", "MotoDesktopService");
        synchronized (this.f271a.f2291p) {
            try {
                iRdpPointerStateChangedListener = this.f271a.f2284j;
            } catch (DeadObjectException unused) {
                v0.a("MotoDesktopService", "IRdpPointerStateChangedListener died");
                this.f271a.f2284j = null;
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            if (iRdpPointerStateChangedListener != null) {
                iRdpPointerStateChangedListener.onRdpPointerIconTypeChanged(i2);
            }
        }
    }

    public final void onPointerPositionReset(int i2, int i3) {
        IRdpPointerStateChangedListener iRdpPointerStateChangedListener;
        v0.a("MotoDesktopService", "Rdp onPointerPositionReset: " + i2 + ", " + i3);
        synchronized (this.f271a.f2291p) {
            try {
                iRdpPointerStateChangedListener = this.f271a.f2284j;
            } catch (DeadObjectException unused) {
                v0.a("MotoDesktopService", "IRdpPointerStateChangedListener died");
                this.f271a.f2284j = null;
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            if (iRdpPointerStateChangedListener != null) {
                iRdpPointerStateChangedListener.onRdpPointerPositionReset(i2, i3);
            }
        }
    }
}
