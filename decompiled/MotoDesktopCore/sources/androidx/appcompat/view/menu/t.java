package androidx.appcompat.view.menu;

import android.view.MenuItem;

/* JADX INFO: loaded from: classes.dex */
public final class t extends AbstractC0061e implements MenuItem.OnMenuItemClickListener {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ v f838b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public t(v vVar, MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        super(onMenuItemClickListener);
        this.f838b = vVar;
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final boolean onMenuItemClick(MenuItem menuItem) {
        return ((MenuItem.OnMenuItemClickListener) this.f733a).onMenuItemClick(this.f838b.c(menuItem));
    }
}
