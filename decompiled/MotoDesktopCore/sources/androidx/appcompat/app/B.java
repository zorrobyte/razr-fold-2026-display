package androidx.appcompat.app;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.C0074k;
import androidx.appcompat.widget.v0;
import d.InterfaceC0124a;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class B extends d.b implements androidx.appcompat.view.menu.m {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Context f480c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final androidx.appcompat.view.menu.o f481d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public InterfaceC0124a f482e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public WeakReference f483f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final /* synthetic */ C f484g;

    public B(C c2, Context context, F.f fVar) {
        this.f484g = c2;
        this.f480c = context;
        this.f482e = fVar;
        androidx.appcompat.view.menu.o oVar = new androidx.appcompat.view.menu.o(context);
        oVar.f792l = 1;
        this.f481d = oVar;
        oVar.f785e = this;
    }

    @Override // d.b
    public final void a() {
        C c2 = this.f484g;
        if (c2.f500p != this) {
            return;
        }
        if (c2.f506w) {
            c2.f501q = this;
            c2.r = this.f482e;
        } else {
            this.f482e.a(this);
        }
        this.f482e = null;
        c2.s0(false);
        ActionBarContextView actionBarContextView = c2.f497m;
        if (actionBarContextView.f881k == null) {
            actionBarContextView.removeAllViews();
            actionBarContextView.f882l = null;
            actionBarContextView.f873c = null;
        }
        ((v0) c2.f496l).f1320a.sendAccessibilityEvent(32);
        c2.f494j.setHideOnContentScrollEnabled(c2.f488B);
        c2.f500p = null;
    }

    @Override // d.b
    public final View b() {
        WeakReference weakReference = this.f483f;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    @Override // androidx.appcompat.view.menu.m
    public final boolean c(androidx.appcompat.view.menu.o oVar, MenuItem menuItem) {
        InterfaceC0124a interfaceC0124a = this.f482e;
        if (interfaceC0124a != null) {
            return interfaceC0124a.b(this, menuItem);
        }
        return false;
    }

    @Override // d.b
    public final androidx.appcompat.view.menu.o d() {
        return this.f481d;
    }

    @Override // d.b
    public final MenuInflater e() {
        return new d.i(this.f480c);
    }

    @Override // androidx.appcompat.view.menu.m
    public final void f(androidx.appcompat.view.menu.o oVar) {
        if (this.f482e == null) {
            return;
        }
        i();
        C0074k c0074k = this.f484g.f497m.f874d;
        if (c0074k != null) {
            c0074k.l();
        }
    }

    @Override // d.b
    public final CharSequence g() {
        return this.f484g.f497m.getSubtitle();
    }

    @Override // d.b
    public final CharSequence h() {
        return this.f484g.f497m.getTitle();
    }

    @Override // d.b
    public final void i() {
        if (this.f484g.f500p != this) {
            return;
        }
        androidx.appcompat.view.menu.o oVar = this.f481d;
        oVar.w();
        try {
            this.f482e.c(this, oVar);
        } finally {
            oVar.v();
        }
    }

    @Override // d.b
    public final boolean j() {
        return this.f484g.f497m.r;
    }

    @Override // d.b
    public final void k(View view) {
        this.f484g.f497m.setCustomView(view);
        this.f483f = new WeakReference(view);
    }

    @Override // d.b
    public final void l(int i2) {
        m(this.f484g.f492h.getResources().getString(i2));
    }

    @Override // d.b
    public final void m(CharSequence charSequence) {
        this.f484g.f497m.setSubtitle(charSequence);
    }

    @Override // d.b
    public final void n(int i2) {
        o(this.f484g.f492h.getResources().getString(i2));
    }

    @Override // d.b
    public final void o(CharSequence charSequence) {
        this.f484g.f497m.setTitle(charSequence);
    }

    @Override // d.b
    public final void p(boolean z2) {
        this.f2352b = z2;
        this.f484g.f497m.setTitleOptional(z2);
    }
}
