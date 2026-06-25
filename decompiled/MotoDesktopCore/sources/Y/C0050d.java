package Y;

import X.v0;
import java.util.Iterator;
import java.util.TimerTask;

/* JADX INFO: renamed from: Y.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0050d extends TimerTask {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f358a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ i f359b;

    public /* synthetic */ C0050d(i iVar, int i2) {
        this.f358a = i2;
        this.f359b = iVar;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final void run() {
        switch (this.f358a) {
            case 0:
                synchronized (this.f359b.f389b) {
                    try {
                        for (h hVar : this.f359b.f391d) {
                            i iVar = this.f359b;
                            hVar.a(iVar.f395h, iVar.f397j, iVar.f399l);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    break;
                }
                return;
            case 1:
                i iVar2 = this.f359b;
                if (r.r0(iVar2.f408v)) {
                    new Thread(new e()).start();
                }
                iVar2.x();
                return;
            default:
                i iVar3 = this.f359b;
                iVar3.f371B = false;
                v0.a("i", "sendShowAudioIcon change to " + iVar3.f370A);
                Iterator it = iVar3.f391d.iterator();
                while (it.hasNext()) {
                    ((h) it.next()).b();
                }
                return;
        }
    }
}
