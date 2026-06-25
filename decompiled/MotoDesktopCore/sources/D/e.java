package d;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.view.menu.o;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.C0074k;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class e extends b implements m {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Context f2357c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public ActionBarContextView f2358d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public InterfaceC0124a f2359e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public WeakReference f2360f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f2361g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public o f2362h;

    @Override // d.b
    public final void a() {
        if (this.f2361g) {
            return;
        }
        this.f2361g = true;
        this.f2358d.sendAccessibilityEvent(32);
        this.f2359e.a(this);
    }

    @Override // d.b
    public final View b() {
        WeakReference weakReference = this.f2360f;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    @Override // androidx.appcompat.view.menu.m
    public final boolean c(o oVar, MenuItem menuItem) {
        return this.f2359e.b(this, menuItem);
    }

    @Override // d.b
    public final o d() {
        return this.f2362h;
    }

    @Override // d.b
    public final MenuInflater e() {
        return new i(this.f2358d.getContext());
    }

    @Override // androidx.appcompat.view.menu.m
    public final void f(o oVar) {
        i();
        C0074k c0074k = this.f2358d.f874d;
        if (c0074k != null) {
            c0074k.l();
        }
    }

    @Override // d.b
    public final CharSequence g() {
        return this.f2358d.getSubtitle();
    }

    @Override // d.b
    public final CharSequence h() {
        return this.f2358d.getTitle();
    }

    @Override // d.b
    public final void i() {
        this.f2359e.c(this, this.f2362h);
    }

    @Override // d.b
    public final boolean j() {
        return this.f2358d.r;
    }

    @Override // d.b
    public final void k(View view) {
        this.f2358d.setCustomView(view);
        this.f2360f = view != null ? new WeakReference(view) : null;
    }

    @Override // d.b
    public final void l(int i2) {
        m(this.f2357c.getString(i2));
    }

    @Override // d.b
    public final void m(CharSequence charSequence) {
        this.f2358d.setSubtitle(charSequence);
    }

    @Override // d.b
    public final void n(int i2) {
        o(this.f2357c.getString(i2));
    }

    @Override // d.b
    public final void o(CharSequence charSequence) {
        this.f2358d.setTitle(charSequence);
    }

    @Override // d.b
    public final void p(boolean z2) {
        this.f2352b = z2;
        this.f2358d.setTitleOptional(z2);
    }
}
