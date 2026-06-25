package Y;

import X.w0;
import android.media.AudioManager;

/* JADX INFO: loaded from: classes.dex */
public final class m implements AudioManager.OnModeChangedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ q f417a;

    public m(q qVar) {
        this.f417a = qVar;
    }

    @Override // android.media.AudioManager.OnModeChangedListener
    public final void onModeChanged(int i2) {
        w0.b(i2, "onModeChanged new mode = ", "AudioReceiver");
        for (f fVar : this.f417a.f423c) {
            fVar.getClass();
            if (i2 != 2 && i2 != 5) {
                i iVar = fVar.f360a;
                if (i2 == 3 || i2 == 6) {
                    iVar.f400m = false;
                    iVar.k(true);
                } else {
                    iVar.f400m = false;
                    iVar.l(true);
                }
            }
        }
    }
}
