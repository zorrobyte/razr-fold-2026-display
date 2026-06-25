package e;

import androidx.appcompat.app.AbstractC0054a;
import java.util.concurrent.Executors;

/* JADX INFO: renamed from: e.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0125a extends AbstractC0054a {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static volatile C0125a f2414e;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public Object f2415d;

    public static C0125a x() {
        if (f2414e != null) {
            return f2414e;
        }
        synchronized (C0125a.class) {
            try {
                if (f2414e == null) {
                    C0125a c0125a = new C0125a();
                    C0125a c0125a2 = new C0125a();
                    c0125a2.f2415d = new Object();
                    Executors.newFixedThreadPool(2, new ThreadFactoryC0126b());
                    c0125a.f2415d = c0125a2;
                    f2414e = c0125a;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return f2414e;
    }
}
