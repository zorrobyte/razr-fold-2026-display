package androidx.appcompat.app;

import android.view.MenuItem;
import androidx.appcompat.widget.r0;

/* JADX INFO: loaded from: classes.dex */
public final class u implements r0, androidx.appcompat.view.menu.m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ x f644a;

    public /* synthetic */ u(x xVar) {
        this.f644a = xVar;
    }

    @Override // androidx.appcompat.view.menu.m
    public boolean c(androidx.appcompat.view.menu.o oVar, MenuItem menuItem) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.m
    public void f(androidx.appcompat.view.menu.o oVar) {
        x xVar = this.f644a;
        if (xVar.f650j != null) {
            if (xVar.f648h.f1320a.n()) {
                xVar.f650j.onPanelClosed(108, oVar);
            } else if (xVar.f650j.onPreparePanel(0, null, oVar)) {
                xVar.f650j.onMenuOpened(108, oVar);
            }
        }
    }
}
