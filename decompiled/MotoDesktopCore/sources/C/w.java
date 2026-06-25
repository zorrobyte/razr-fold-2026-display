package C;

import h.C0135b;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class w {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final ThreadLocal f95a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final ArrayList f96b;

    static {
        C0000a c0000a = new C0000a();
        c0000a.f100y = false;
        c0000a.H(new i(2));
        c0000a.H(new g());
        c0000a.H(new i(1));
        f95a = new ThreadLocal();
        f96b = new ArrayList();
    }

    public static C0135b a() {
        C0135b c0135b;
        ThreadLocal threadLocal = f95a;
        WeakReference weakReference = (WeakReference) threadLocal.get();
        if (weakReference != null && (c0135b = (C0135b) weakReference.get()) != null) {
            return c0135b;
        }
        C0135b c0135b2 = new C0135b();
        threadLocal.set(new WeakReference(c0135b2));
        return c0135b2;
    }
}
