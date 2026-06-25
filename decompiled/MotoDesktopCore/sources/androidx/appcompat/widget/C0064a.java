package androidx.appcompat.widget;

import android.view.View;

/* JADX INFO: renamed from: androidx.appcompat.widget.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0064a implements v.n {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f1198a = false;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1199b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ ActionBarContextView f1200c;

    public C0064a(ActionBarContextView actionBarContextView) {
        this.f1200c = actionBarContextView;
    }

    @Override // v.n
    public final void a(View view) {
        this.f1198a = true;
    }

    @Override // v.n
    public final void b() {
        super/*android.view.View*/.setVisibility(0);
        this.f1198a = false;
    }

    @Override // v.n
    public final void c() {
        if (this.f1198a) {
            return;
        }
        ActionBarContextView actionBarContextView = this.f1200c;
        actionBarContextView.f876f = null;
        super/*android.view.View*/.setVisibility(this.f1199b);
    }
}
