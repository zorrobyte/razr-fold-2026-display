package U;

import C.z;
import X.w0;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
public final class c implements Handler.Callback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f240a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f241b;

    public /* synthetic */ c(int i2, Object obj) {
        this.f240a = i2;
        this.f241b = obj;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        switch (this.f240a) {
            case 0:
                if (message.what != 0) {
                    return false;
                }
                d dVar = (d) this.f241b;
                w0.c(message.obj);
                synchronized (dVar.f243a) {
                    throw null;
                }
            default:
                int i2 = message.what;
                if (i2 != 0) {
                    if (i2 == 1) {
                        z zVar = (z) this.f241b;
                        Runnable runnable = (Runnable) message.obj;
                        zVar.getClass();
                        runnable.run();
                        synchronized (zVar.f105a) {
                            ((Handler) zVar.f107c).removeMessages(0);
                            Handler handler = (Handler) zVar.f107c;
                            handler.sendMessageDelayed(handler.obtainMessage(0), 10000);
                        }
                    }
                    break;
                } else {
                    z zVar2 = (z) this.f241b;
                    synchronized (zVar2.f105a) {
                        try {
                            if (!((Handler) zVar2.f107c).hasMessages(1)) {
                                ((HandlerThread) zVar2.f106b).quit();
                                zVar2.f106b = null;
                                zVar2.f107c = null;
                            }
                        } finally {
                        }
                    }
                    break;
                }
                return true;
        }
    }
}
