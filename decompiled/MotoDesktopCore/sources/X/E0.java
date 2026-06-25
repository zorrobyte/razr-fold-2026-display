package X;

import android.os.RemoteException;
import android.util.Log;
import android.view.WindowManagerGlobal;

/* JADX INFO: loaded from: classes.dex */
public final class E0 implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f264a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f265b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f266c;

    public E0(int i2, int i3, int i4) {
        this.f264a = i2;
        this.f265b = i3;
        this.f266c = i4;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            WindowManagerGlobal.getWindowManagerService().setForcedDisplayDensityForUser(this.f264a, this.f265b, this.f266c);
        } catch (RemoteException unused) {
            Log.w("MotoDesktopService", "Unable to save forced display density setting");
        }
    }
}
