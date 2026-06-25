package d;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import androidx.appcompat.view.menu.D;

/* JADX INFO: loaded from: classes.dex */
public final class f extends ActionMode {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f2363a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final b f2364b;

    public f(Context context, b bVar) {
        this.f2363a = context;
        this.f2364b = bVar;
    }

    @Override // android.view.ActionMode
    public final void finish() {
        this.f2364b.a();
    }

    @Override // android.view.ActionMode
    public final View getCustomView() {
        return this.f2364b.b();
    }

    @Override // android.view.ActionMode
    public final Menu getMenu() {
        return new D(this.f2363a, this.f2364b.d());
    }

    @Override // android.view.ActionMode
    public final MenuInflater getMenuInflater() {
        return this.f2364b.e();
    }

    @Override // android.view.ActionMode
    public final CharSequence getSubtitle() {
        return this.f2364b.g();
    }

    @Override // android.view.ActionMode
    public final Object getTag() {
        return this.f2364b.f2351a;
    }

    @Override // android.view.ActionMode
    public final CharSequence getTitle() {
        return this.f2364b.h();
    }

    @Override // android.view.ActionMode
    public final boolean getTitleOptionalHint() {
        return this.f2364b.f2352b;
    }

    @Override // android.view.ActionMode
    public final void invalidate() {
        this.f2364b.i();
    }

    @Override // android.view.ActionMode
    public final boolean isTitleOptional() {
        return this.f2364b.j();
    }

    @Override // android.view.ActionMode
    public final void setCustomView(View view) {
        this.f2364b.k(view);
    }

    @Override // android.view.ActionMode
    public final void setSubtitle(int i2) {
        this.f2364b.l(i2);
    }

    @Override // android.view.ActionMode
    public final void setSubtitle(CharSequence charSequence) {
        this.f2364b.m(charSequence);
    }

    @Override // android.view.ActionMode
    public final void setTag(Object obj) {
        this.f2364b.f2351a = obj;
    }

    @Override // android.view.ActionMode
    public final void setTitle(int i2) {
        this.f2364b.n(i2);
    }

    @Override // android.view.ActionMode
    public final void setTitle(CharSequence charSequence) {
        this.f2364b.o(charSequence);
    }

    @Override // android.view.ActionMode
    public final void setTitleOptionalHint(boolean z2) {
        this.f2364b.p(z2);
    }
}
