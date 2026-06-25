package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class G extends o implements SubMenu {

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public final o f694A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public final q f695B;

    public G(Context context, o oVar, q qVar) {
        super(context);
        this.f694A = oVar;
        this.f695B = qVar;
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean d(q qVar) {
        return this.f694A.d(qVar);
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean e(o oVar, MenuItem menuItem) {
        return super.e(oVar, menuItem) || this.f694A.e(oVar, menuItem);
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean f(q qVar) {
        return this.f694A.f(qVar);
    }

    @Override // android.view.SubMenu
    public final MenuItem getItem() {
        return this.f695B;
    }

    @Override // androidx.appcompat.view.menu.o
    public final String j() {
        q qVar = this.f695B;
        int i2 = qVar != null ? qVar.f811a : 0;
        if (i2 == 0) {
            return null;
        }
        return "android:menu:actionviewstates:" + i2;
    }

    @Override // androidx.appcompat.view.menu.o
    public final o k() {
        return this.f694A.k();
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean m() {
        return this.f694A.m();
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean n() {
        return this.f694A.n();
    }

    @Override // androidx.appcompat.view.menu.o
    public final boolean o() {
        return this.f694A.o();
    }

    @Override // androidx.appcompat.view.menu.o, android.view.Menu
    public final void setGroupDividerEnabled(boolean z2) {
        this.f694A.setGroupDividerEnabled(z2);
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderIcon(int i2) {
        u(0, null, i2, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderIcon(Drawable drawable) {
        u(0, null, 0, drawable, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderTitle(int i2) {
        u(i2, null, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderTitle(CharSequence charSequence) {
        u(0, charSequence, 0, null, null);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setHeaderView(View view) {
        u(0, null, 0, null, view);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setIcon(int i2) {
        this.f695B.setIcon(i2);
        return this;
    }

    @Override // android.view.SubMenu
    public final SubMenu setIcon(Drawable drawable) {
        this.f695B.setIcon(drawable);
        return this;
    }

    @Override // androidx.appcompat.view.menu.o, android.view.Menu
    public final void setQwertyMode(boolean z2) {
        this.f694A.setQwertyMode(z2);
    }
}
