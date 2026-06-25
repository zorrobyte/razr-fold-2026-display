package androidx.appcompat.view.menu;

import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class u extends v.c implements ActionProvider.VisibilityListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ActionProvider f839a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ v f840b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public e0.k f841c;

    public u(v vVar, ActionProvider actionProvider) {
        this.f840b = vVar;
        this.f839a = actionProvider;
    }

    @Override // v.c
    public final boolean a() {
        return this.f839a.isVisible();
    }

    @Override // v.c
    public final View b(MenuItem menuItem) {
        return this.f839a.onCreateActionView(menuItem);
    }

    @Override // v.c
    public final boolean c() {
        return this.f839a.overridesItemVisibility();
    }

    @Override // v.c
    public final void d(e0.k kVar) {
        this.f841c = kVar;
        this.f839a.setVisibilityListener(this);
    }

    @Override // android.view.ActionProvider.VisibilityListener
    public final void onActionProviderVisibilityChanged(boolean z2) {
        e0.k kVar = this.f841c;
        if (kVar != null) {
            o oVar = ((q) kVar.f2495a).f824n;
            oVar.f788h = true;
            oVar.p(true);
        }
    }
}
