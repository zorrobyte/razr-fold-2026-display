package androidx.appcompat.widget;

import android.view.View;

/* JADX INFO: renamed from: androidx.appcompat.widget.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0071h implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0069f f1210a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0074k f1211b;

    public RunnableC0071h(C0074k c0074k, C0069f c0069f) {
        this.f1211b = c0074k;
        this.f1210a = c0069f;
    }

    @Override // java.lang.Runnable
    public final void run() {
        androidx.appcompat.view.menu.m mVar;
        C0074k c0074k = this.f1211b;
        androidx.appcompat.view.menu.o oVar = c0074k.f1227c;
        if (oVar != null && (mVar = oVar.f785e) != null) {
            mVar.f(oVar);
        }
        View view = (View) c0074k.f1232h;
        if (view != null && view.getWindowToken() != null) {
            C0069f c0069f = this.f1210a;
            if (c0069f.b()) {
                c0074k.f1243t = c0069f;
            } else if (c0069f.f850f != null) {
                c0069f.d(0, 0, false, false);
                c0074k.f1243t = c0069f;
            }
        }
        c0074k.f1245v = null;
    }
}
