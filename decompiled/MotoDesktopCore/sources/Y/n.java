package Y;

import X.v0;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class n implements AudioManager.OnCommunicationDeviceChangedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ q f418a;

    public n(q qVar) {
        this.f418a = qVar;
    }

    @Override // android.media.AudioManager.OnCommunicationDeviceChangedListener
    public final void onCommunicationDeviceChanged(AudioDeviceInfo audioDeviceInfo) {
        int type;
        if (audioDeviceInfo != null) {
            v0.a("AudioReceiver", "onCommunicationDeviceChanged new type = " + audioDeviceInfo.getType());
            Iterator it = this.f418a.f423c.iterator();
            while (it.hasNext()) {
                i iVar = ((f) it.next()).f360a;
                if (iVar.f399l && iVar.f401n != (type = audioDeviceInfo.getType())) {
                    iVar.f401n = type;
                    g gVar = iVar.f388a;
                    int i2 = gVar.f367g;
                    if (gVar.b() && i2 != 5 && i2 != 4) {
                        if (type == 2) {
                            if (gVar.b()) {
                                gVar.f367g = 1;
                            }
                            gVar.c();
                        } else if (type == 1) {
                            if (gVar.b()) {
                                gVar.f367g = 0;
                            }
                            gVar.c();
                        } else if (r.S(type)) {
                            if (gVar.b()) {
                                gVar.f367g = 2;
                            }
                            gVar.c();
                        }
                    }
                    iVar.x();
                }
            }
        }
    }
}
