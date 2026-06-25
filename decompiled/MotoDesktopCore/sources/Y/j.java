package Y;

import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class j extends AudioManager.AudioPlaybackCallback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f413a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f414b;

    public /* synthetic */ j(int i2, Object obj) {
        this.f413a = i2;
        this.f414b = obj;
    }

    @Override // android.media.AudioManager.AudioPlaybackCallback
    public final void onPlaybackConfigChanged(List list) {
        int usage;
        switch (this.f413a) {
            case 0:
                ((e0.i) this.f414b).a(list, true);
                return;
            default:
                for (f fVar : ((q) this.f414b).f423c) {
                    i iVar = fVar.f360a;
                    boolean z2 = false;
                    if (list != null && list.size() != 0) {
                        synchronized (r.class) {
                            try {
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it.next();
                                    if (audioPlaybackConfiguration.isActive() && audioPlaybackConfiguration.getClientUid() != 1000 && ((usage = audioPlaybackConfiguration.getAudioAttributes().getUsage()) == 1 || usage == 14)) {
                                        z2 = true;
                                    }
                                }
                            } finally {
                            }
                            break;
                        }
                    }
                    iVar.w(z2);
                    fVar.f360a.e(list);
                }
                return;
        }
    }
}
