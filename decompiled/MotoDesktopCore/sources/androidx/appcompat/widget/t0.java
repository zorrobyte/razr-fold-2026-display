package androidx.appcompat.widget;

import android.view.View;
import android.view.Window;
import androidx.appcompat.view.menu.C0057a;

/* JADX INFO: loaded from: classes.dex */
public final class t0 implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0057a f1310a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ v0 f1311b;

    public t0(v0 v0Var) {
        this.f1311b = v0Var;
        this.f1310a = new C0057a(v0Var.f1320a.getContext(), v0Var.f1328i);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        v0 v0Var = this.f1311b;
        Window.Callback callback = v0Var.f1331l;
        if (callback == null || !v0Var.f1332m) {
            return;
        }
        callback.onMenuItemSelected(0, this.f1310a);
    }
}
