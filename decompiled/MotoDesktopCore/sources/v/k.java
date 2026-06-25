package v;

import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public final class k implements View.OnApplyWindowInsetsListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ j f2835a;

    public k(j jVar) {
        this.f2835a = jVar;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        o oVarG = this.f2835a.g(view, windowInsets == null ? null : new o(windowInsets));
        return (WindowInsets) (oVarG != null ? oVarG.f2838a : null);
    }
}
