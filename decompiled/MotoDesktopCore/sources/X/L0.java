package X;

import android.os.Handler;
import com.motorola.internal.inputmethod.IMotoInputMethodListener;
import com.motorola.mobiledesktop.core.MotoDesktopService;

/* JADX INFO: loaded from: classes.dex */
public final class L0 extends IMotoInputMethodListener.Stub {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public MotoDesktopService f277a;

    public final void onImeFocusStateChanged(int i2, boolean z2) {
        v0.a("MotoDesktopService", "onImeFocusStateChanged curTokendisplayId ： " + i2 + "; isShowRequested: " + z2);
        MotoDesktopService motoDesktopService = this.f277a;
        if (motoDesktopService != null) {
            if (motoDesktopService.f2258P == z2 && motoDesktopService.f2259Q == i2) {
                return;
            }
            motoDesktopService.f2259Q = i2;
            motoDesktopService.f2258P = z2;
            Handler handler = motoDesktopService.f2279g;
            F.e eVar = motoDesktopService.h0;
            handler.removeCallbacks(eVar);
            motoDesktopService.f2279g.post(eVar);
        }
    }
}
