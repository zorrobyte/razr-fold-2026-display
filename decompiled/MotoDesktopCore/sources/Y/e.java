package Y;

import X.w0;
import android.media.AudioSystem;

/* JADX INFO: loaded from: classes.dex */
public final class e implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        int forceUse = AudioSystem.getForceUse(1);
        w0.b(forceUse, "updateMediaRoute force = ", "i");
        if (forceUse == 0) {
            AudioSystem.setForceUse(1, 1);
        } else {
            AudioSystem.setForceUse(1, 0);
        }
        AudioSystem.setForceUse(1, forceUse);
    }
}
