package androidx.appcompat.view.menu;

import android.view.MenuItem;

/* JADX INFO: loaded from: classes.dex */
public final class h implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ i f738a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MenuItem f739b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ o f740c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ e0.k f741d;

    public h(e0.k kVar, i iVar, q qVar, o oVar) {
        this.f741d = kVar;
        this.f738a = iVar;
        this.f739b = qVar;
        this.f740c = oVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        i iVar = this.f738a;
        if (iVar != null) {
            e0.k kVar = this.f741d;
            ((j) kVar.f2495a).f746A = true;
            iVar.f743b.c(false);
            ((j) kVar.f2495a).f746A = false;
        }
        MenuItem menuItem = this.f739b;
        if (menuItem.isEnabled() && menuItem.hasSubMenu()) {
            this.f740c.q(menuItem, null, 4);
        }
    }
}
