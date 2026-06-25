package androidx.appcompat.view.menu;

import android.view.View;
import android.view.ViewTreeObserver;

/* JADX INFO: renamed from: androidx.appcompat.view.menu.g, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnAttachStateChangeListenerC0063g implements View.OnAttachStateChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f736a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ w f737b;

    public /* synthetic */ ViewOnAttachStateChangeListenerC0063g(w wVar, int i2) {
        this.f736a = i2;
        this.f737b = wVar;
    }

    private final void a(View view) {
    }

    private final void b(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        int i2 = this.f736a;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        switch (this.f736a) {
            case 0:
                j jVar = (j) this.f737b;
                ViewTreeObserver viewTreeObserver = jVar.f769y;
                if (viewTreeObserver != null) {
                    if (!viewTreeObserver.isAlive()) {
                        jVar.f769y = view.getViewTreeObserver();
                    }
                    jVar.f769y.removeGlobalOnLayoutListener(jVar.f755j);
                }
                view.removeOnAttachStateChangeListener(this);
                break;
            default:
                F f2 = (F) this.f737b;
                ViewTreeObserver viewTreeObserver2 = f2.f689p;
                if (viewTreeObserver2 != null) {
                    if (!viewTreeObserver2.isAlive()) {
                        f2.f689p = view.getViewTreeObserver();
                    }
                    f2.f689p.removeGlobalOnLayoutListener(f2.f683j);
                }
                view.removeOnAttachStateChangeListener(this);
                break;
        }
    }
}
