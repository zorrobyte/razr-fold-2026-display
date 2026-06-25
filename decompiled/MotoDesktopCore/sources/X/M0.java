package X;

import android.hardware.input.InputManager;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.motorola.mobiledesktop.core.MotoDesktopService;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class M0 implements InputManager.OnPointerPositionChangedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int f279a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MotoDesktopService f280b;

    public M0(MotoDesktopService motoDesktopService, int i2) {
        this.f280b = motoDesktopService;
        this.f279a = i2;
    }

    public final void onPointerPositionChanged(int i2, int i3) {
        MotoDesktopService motoDesktopService = this.f280b;
        int i4 = this.f279a;
        String[] strArr = MotoDesktopService.w0;
        motoDesktopService.getClass();
        ArrayList<Q> arrayList = new ArrayList();
        synchronized (motoDesktopService.f2291p) {
            try {
                for (O0 o0 : motoDesktopService.f2287l) {
                    if (o0.f283a == i4) {
                        arrayList.add((Q) o0.f284b);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (Q q2 : arrayList) {
            try {
                ((P) q2).a(i2, i3);
            } catch (DeadObjectException unused) {
                motoDesktopService.f(q2);
            } catch (RemoteException unused2) {
            }
        }
    }
}
