package androidx.appcompat.view.menu;

import android.view.MenuItem;

/* JADX INFO: loaded from: classes.dex */
public final class s extends AbstractC0061e implements MenuItem.OnActionExpandListener {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ v f837b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public s(v vVar, MenuItem.OnActionExpandListener onActionExpandListener) {
        super(onActionExpandListener);
        this.f837b = vVar;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return ((MenuItem.OnActionExpandListener) this.f733a).onMenuItemActionCollapse(this.f837b.c(menuItem));
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        return ((MenuItem.OnActionExpandListener) this.f733a).onMenuItemActionExpand(this.f837b.c(menuItem));
    }
}
