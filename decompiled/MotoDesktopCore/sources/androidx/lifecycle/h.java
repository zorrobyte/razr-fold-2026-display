package androidx.lifecycle;

import android.os.Looper;
import e.C0125a;
import f.C0130c;
import f.C0131d;

/* JADX INFO: loaded from: classes.dex */
public class h extends g {
    public final void a(d dVar) {
        ((C0125a) C0125a.x().f2415d).getClass();
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Cannot invoke setValue on a background thread");
        }
        if (this.f1713e) {
            this.f1714f = true;
            return;
        }
        this.f1713e = true;
        do {
            this.f1714f = false;
            C0131d c0131d = this.f1710b;
            c0131d.getClass();
            C0130c c0130c = new C0130c(c0131d);
            c0131d.f2531a.put(c0130c, Boolean.FALSE);
            c0130c.hasNext();
        } while (this.f1714f);
        this.f1713e = false;
    }
}
