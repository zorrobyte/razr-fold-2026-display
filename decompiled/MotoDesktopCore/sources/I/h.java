package I;

import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import k.AbstractC0143b;

/* JADX INFO: loaded from: classes.dex */
public abstract class h extends AbstractC0143b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public i f177a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f178b = 0;

    public h() {
    }

    public h(int i2) {
    }

    @Override // k.AbstractC0143b
    public boolean f(CoordinatorLayout coordinatorLayout, View view, int i2) {
        t(coordinatorLayout, view, i2);
        if (this.f177a == null) {
            this.f177a = new i(view);
        }
        i iVar = this.f177a;
        View view2 = iVar.f179a;
        iVar.f180b = view2.getTop();
        iVar.f181c = view2.getLeft();
        iVar.b();
        int i3 = this.f178b;
        if (i3 == 0) {
            return true;
        }
        this.f177a.a(i3);
        this.f178b = 0;
        return true;
    }

    public final int s() {
        i iVar = this.f177a;
        if (iVar != null) {
            return iVar.f182d;
        }
        return 0;
    }

    public void t(CoordinatorLayout coordinatorLayout, View view, int i2) {
        coordinatorLayout.p(view, i2);
    }
}
