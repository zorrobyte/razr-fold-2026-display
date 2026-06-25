package Y;

import X.v0;
import android.hardware.display.DisplayManager;
import android.view.Display;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class k implements DisplayManager.DisplayListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ q f415a;

    public k(q qVar) {
        this.f415a = qVar;
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i2) {
        for (f fVar : this.f415a.f423c) {
            Display display = fVar.f360a.f408v.getDisplay(i2);
            if (display != null && display.getType() == 3) {
                v0.a("i", "wireless display add");
                fVar.f360a.f409w = display.getDisplayId();
                synchronized (fVar.f360a.f389b) {
                    try {
                        if (r.e(fVar.f360a.f395h)) {
                            r.i0("persist.desktop.allow_wireless_media", "false");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                i iVar = fVar.f360a;
                if (!iVar.f396i && iVar.f410x != -1) {
                    iVar.f378I = false;
                }
                iVar.x();
            } else if (display != null && display.getType() == 2) {
                v0.a("i", "hdmi display add");
                fVar.f360a.f410x = display.getDisplayId();
                i iVar2 = fVar.f360a;
                if (!iVar2.f396i && iVar2.f409w != -1) {
                    iVar2.f378I = false;
                }
            }
            i.b(fVar.f360a, 1000);
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(int i2) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i2) {
        Iterator it = this.f415a.f423c.iterator();
        while (it.hasNext()) {
            i iVar = ((f) it.next()).f360a;
            if (i2 == iVar.f409w) {
                v0.a("i", "wireless display remove");
                r.i0("persist.desktop.allow_wireless_media", "true");
                iVar.x();
                iVar.f409w = -1;
            } else if (i2 == iVar.f410x) {
                v0.a("i", "hdmi display remove");
                iVar.f410x = -1;
            }
            i.b(iVar, 500);
        }
    }
}
