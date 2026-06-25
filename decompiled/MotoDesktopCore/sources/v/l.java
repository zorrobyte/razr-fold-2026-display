package v;

import android.view.View;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public abstract class l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static WeakHashMap f2836a;

    static {
        new AtomicInteger(1);
        f2836a = null;
    }

    public static m a(View view) {
        if (f2836a == null) {
            f2836a = new WeakHashMap();
        }
        m mVar = (m) f2836a.get(view);
        if (mVar != null) {
            return mVar;
        }
        m mVar2 = new m();
        mVar2.f2837a = new WeakReference(view);
        f2836a.put(view, mVar2);
        return mVar2;
    }

    public static void b(View view, b bVar) {
        view.setAccessibilityDelegate(bVar == null ? null : bVar.f2828a);
    }

    public static void c(View view, j jVar) {
        if (jVar == null) {
            view.setOnApplyWindowInsetsListener(null);
        } else {
            view.setOnApplyWindowInsetsListener(new k(jVar));
        }
    }
}
