package androidx.appcompat.widget;

import android.view.View;

/* JADX INFO: renamed from: androidx.appcompat.widget.x, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0086x extends L {

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final /* synthetic */ A f1343j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final /* synthetic */ AppCompatSpinner f1344k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0086x(AppCompatSpinner appCompatSpinner, View view, A a2) {
        super(view);
        this.f1344k = appCompatSpinner;
        this.f1343j = a2;
    }

    @Override // androidx.appcompat.widget.L
    public final androidx.appcompat.view.menu.E b() {
        return this.f1343j;
    }

    @Override // androidx.appcompat.widget.L
    public final boolean c() {
        AppCompatSpinner appCompatSpinner = this.f1344k;
        if (appCompatSpinner.f956f.f1070y.isShowing()) {
            return true;
        }
        appCompatSpinner.f956f.show();
        return true;
    }
}
