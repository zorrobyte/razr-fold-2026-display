package Y;

import X.v0;
import android.media.AudioManager;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class l extends AudioManager.AudioRecordingCallback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ q f416a;

    public l(q qVar) {
        this.f416a = qVar;
    }

    @Override // android.media.AudioManager.AudioRecordingCallback
    public final void onRecordingConfigChanged(List list) {
        for (f fVar : this.f416a.f423c) {
            fVar.getClass();
            boolean zT = r.T(list);
            i iVar = fVar.f360a;
            if (iVar.f375F != zT) {
                iVar.f375F = zT;
                v0.a("i", "updateUsingMicState = " + iVar.f375F);
                if (!iVar.f399l && iVar.f396i) {
                    synchronized (iVar.f389b) {
                        try {
                            Iterator it = iVar.f395h.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                t tVar = (t) it.next();
                                if (tVar instanceof C) {
                                    tVar.f445f = zT;
                                    break;
                                }
                            }
                            Iterator it2 = iVar.f391d.iterator();
                            while (it2.hasNext()) {
                                ((h) it2.next()).a(iVar.f395h, iVar.f397j, iVar.f399l);
                            }
                        } finally {
                        }
                    }
                }
            }
        }
    }
}
