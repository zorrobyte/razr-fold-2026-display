package androidx.appcompat.app;

import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.C0069f;
import androidx.appcompat.widget.C0074k;

/* JADX INFO: loaded from: classes.dex */
public final class v implements androidx.appcompat.view.menu.z {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f645a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ x f646b;

    public v(x xVar) {
        this.f646b = xVar;
    }

    @Override // androidx.appcompat.view.menu.z
    public final void a(androidx.appcompat.view.menu.o oVar, boolean z2) {
        C0074k c0074k;
        if (this.f645a) {
            return;
        }
        this.f645a = true;
        x xVar = this.f646b;
        ActionMenuView actionMenuView = xVar.f648h.f1320a.f1154a;
        if (actionMenuView != null && (c0074k = actionMenuView.f921t) != null) {
            c0074k.j();
            C0069f c0069f = c0074k.f1244u;
            if (c0069f != null && c0069f.b()) {
                c0069f.f854j.dismiss();
            }
        }
        w wVar = xVar.f650j;
        if (wVar != null) {
            wVar.onPanelClosed(108, oVar);
        }
        this.f645a = false;
    }

    @Override // androidx.appcompat.view.menu.z
    public final boolean e(androidx.appcompat.view.menu.o oVar) {
        w wVar = this.f646b.f650j;
        if (wVar == null) {
            return false;
        }
        wVar.onMenuOpened(108, oVar);
        return true;
    }
}
