package androidx.appcompat.view.menu;

import android.widget.PopupWindow;

/* JADX INFO: loaded from: classes.dex */
public final class x implements PopupWindow.OnDismissListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ y f844a;

    public x(y yVar) {
        this.f844a = yVar;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        this.f844a.c();
    }
}
