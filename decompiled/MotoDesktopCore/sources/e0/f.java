package e0;

import X.v0;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import com.motorola.android.dragdrop.IMotoDragDropRf;

/* JADX INFO: loaded from: classes.dex */
public final class f implements ServiceConnection {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ g f2471a;

    public f(g gVar) {
        this.f2471a = gVar;
    }

    @Override // android.content.ServiceConnection
    public final void onBindingDied(ComponentName componentName) {
        v0.a("FwkDragDropManager", "onBindingDied");
        g gVar = this.f2471a;
        gVar.f2474b = null;
        gVar.f2476d = false;
        if (gVar.f2475c) {
            v0.a("FwkDragDropManager", "rebind");
            Handler handler = gVar.f2478f;
            F.e eVar = gVar.f2481i;
            handler.removeCallbacks(eVar);
            gVar.f2478f.postDelayed(eVar, gVar.f2477e == 0 ? 0L : 100L);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        v0.a("FwkDragDropManager", "onServiceConnected");
        IMotoDragDropRf iMotoDragDropRfAsInterface = IMotoDragDropRf.Stub.asInterface(iBinder);
        g gVar = this.f2471a;
        gVar.f2474b = iMotoDragDropRfAsInterface;
        gVar.f2476d = true;
        try {
            iMotoDragDropRfAsInterface.registerDragDropControllerRfCallback(gVar.f2479g);
        } catch (RemoteException e2) {
            v0.h("FwkDragDropManager", "registerDragDropControllerRfCallback", e2);
        }
        gVar.f2478f.removeCallbacks(gVar.f2481i);
        gVar.f2477e = 0;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        v0.a("FwkDragDropManager", "onServiceDisconnected");
        g gVar = this.f2471a;
        gVar.f2474b = null;
        gVar.f2476d = false;
        if (gVar.f2475c) {
            v0.a("FwkDragDropManager", "rebind");
            Handler handler = gVar.f2478f;
            F.e eVar = gVar.f2481i;
            handler.removeCallbacks(eVar);
            gVar.f2478f.postDelayed(eVar, gVar.f2477e == 0 ? 0L : 100L);
        }
    }
}
