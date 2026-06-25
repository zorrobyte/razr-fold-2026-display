package v;

import android.view.View;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public WeakReference f2837a;

    public final void a(float f2) {
        View view = (View) this.f2837a.get();
        if (view != null) {
            view.animate().alpha(f2);
        }
    }

    public final void b() {
        View view = (View) this.f2837a.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public final void c(long j2) {
        View view = (View) this.f2837a.get();
        if (view != null) {
            view.animate().setDuration(j2);
        }
    }

    public final void d(n nVar) {
        View view = (View) this.f2837a.get();
        if (view != null) {
            if (nVar != null) {
                view.animate().setListener(new C.o(2, view, nVar));
            } else {
                view.animate().setListener(null);
            }
        }
    }

    public final void e(float f2) {
        View view = (View) this.f2837a.get();
        if (view != null) {
            view.animate().translationY(f2);
        }
    }
}
