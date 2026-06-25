package X;

import android.content.ClipboardManager;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.motorola.mobiledesktop.core.IRdpPimaryClipChangedListener;
import com.motorola.mobiledesktop.core.MotoDesktopService;

/* JADX INFO: loaded from: classes.dex */
public final class I0 implements ClipboardManager.OnPrimaryClipChangedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MotoDesktopService f272a;

    public I0(MotoDesktopService motoDesktopService) {
        this.f272a = motoDesktopService;
    }

    @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
    public final void onPrimaryClipChanged() {
        IRdpPimaryClipChangedListener iRdpPimaryClipChangedListener;
        v0.a("MotoDesktopService", "ClipboardManager onPrimaryClipChanged");
        synchronized (this.f272a.f2291p) {
            try {
                iRdpPimaryClipChangedListener = this.f272a.f2282i;
            } catch (DeadObjectException unused) {
                v0.a("MotoDesktopService", "IRdpPimaryClipChangedListener died");
                this.f272a.f2282i = null;
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            if (iRdpPimaryClipChangedListener != null) {
                iRdpPimaryClipChangedListener.onRdpPrimaryClipChanged();
            }
        }
    }
}
