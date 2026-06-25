package androidx.appcompat.app;

import X.w0;
import android.content.Context;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.C0074k;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.p0;
import androidx.appcompat.widget.v0;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class x extends Y.r {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final v0 f648h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f649i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final w f650j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f651k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public boolean f652l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final ArrayList f653m = new ArrayList();

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final F.e f654n = new F.e(4, this);

    public x(Toolbar toolbar, CharSequence charSequence, l lVar) {
        u uVar = new u(this);
        v0 v0Var = new v0(toolbar, false);
        this.f648h = v0Var;
        w wVar = new w(this, lVar);
        this.f650j = wVar;
        v0Var.f1331l = wVar;
        toolbar.setOnMenuItemClickListener(uVar);
        if (v0Var.f1327h) {
            return;
        }
        v0Var.f1328i = charSequence;
        if ((v0Var.f1321b & 8) != 0) {
            v0Var.f1320a.setTitle(charSequence);
        }
    }

    @Override // Y.r
    public final Context E() {
        return this.f648h.f1320a.getContext();
    }

    @Override // Y.r
    public final boolean M() {
        v0 v0Var = this.f648h;
        Toolbar toolbar = v0Var.f1320a;
        F.e eVar = this.f654n;
        toolbar.removeCallbacks(eVar);
        Toolbar toolbar2 = v0Var.f1320a;
        WeakHashMap weakHashMap = v.l.f2836a;
        toolbar2.postOnAnimation(eVar);
        return true;
    }

    @Override // Y.r
    public final void W() {
    }

    @Override // Y.r
    public final void X() {
        this.f648h.f1320a.removeCallbacks(this.f654n);
    }

    @Override // Y.r
    public final boolean Y(int i2, KeyEvent keyEvent) {
        Menu menuS0 = s0();
        if (menuS0 == null) {
            return false;
        }
        menuS0.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
        return menuS0.performShortcut(i2, keyEvent, 0);
    }

    @Override // Y.r
    public final boolean Z(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            e0();
        }
        return true;
    }

    @Override // Y.r
    public final boolean e0() {
        return this.f648h.f1320a.t();
    }

    @Override // Y.r
    public final void f0(boolean z2) {
    }

    @Override // Y.r
    public final boolean i() {
        C0074k c0074k;
        ActionMenuView actionMenuView = this.f648h.f1320a.f1154a;
        return (actionMenuView == null || (c0074k = actionMenuView.f921t) == null || !c0074k.j()) ? false : true;
    }

    @Override // Y.r
    public final boolean j() {
        androidx.appcompat.view.menu.q qVar;
        p0 p0Var = this.f648h.f1320a.f1149K;
        if (p0Var == null || (qVar = p0Var.f1281b) == null) {
            return false;
        }
        if (p0Var == null) {
            qVar = null;
        }
        if (qVar == null) {
            return true;
        }
        qVar.collapseActionView();
        return true;
    }

    @Override // Y.r
    public final void j0(boolean z2) {
    }

    @Override // Y.r
    public final void m0(CharSequence charSequence) {
        v0 v0Var = this.f648h;
        if (v0Var.f1327h) {
            return;
        }
        v0Var.f1328i = charSequence;
        if ((v0Var.f1321b & 8) != 0) {
            v0Var.f1320a.setTitle(charSequence);
        }
    }

    @Override // Y.r
    public final void q(boolean z2) {
        if (z2 == this.f652l) {
            return;
        }
        this.f652l = z2;
        ArrayList arrayList = this.f653m;
        if (arrayList.size() <= 0) {
            return;
        }
        w0.c(arrayList.get(0));
        throw null;
    }

    public final Menu s0() {
        boolean z2 = this.f651k;
        v0 v0Var = this.f648h;
        if (!z2) {
            v vVar = new v(this);
            u uVar = new u(this);
            Toolbar toolbar = v0Var.f1320a;
            toolbar.f1150L = vVar;
            toolbar.f1151M = uVar;
            ActionMenuView actionMenuView = toolbar.f1154a;
            if (actionMenuView != null) {
                actionMenuView.f922u = vVar;
                actionMenuView.f923v = uVar;
            }
            this.f651k = true;
        }
        return v0Var.f1320a.getMenu();
    }

    @Override // Y.r
    public final int t() {
        return this.f648h.f1321b;
    }
}
