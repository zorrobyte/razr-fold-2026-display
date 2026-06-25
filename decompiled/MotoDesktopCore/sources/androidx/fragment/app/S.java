package androidx.fragment.app;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
public final class S implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final View f1587a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ViewTreeObserver f1588b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Runnable f1589c;

    public S(ViewGroup viewGroup, Runnable runnable) {
        this.f1587a = viewGroup;
        this.f1588b = viewGroup.getViewTreeObserver();
        this.f1589c = runnable;
    }

    public static void a(ViewGroup viewGroup, Runnable runnable) {
        S s2 = new S(viewGroup, runnable);
        viewGroup.getViewTreeObserver().addOnPreDrawListener(s2);
        viewGroup.addOnAttachStateChangeListener(s2);
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        boolean zIsAlive = this.f1588b.isAlive();
        View view = this.f1587a;
        if (zIsAlive) {
            this.f1588b.removeOnPreDrawListener(this);
        } else {
            view.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        view.removeOnAttachStateChangeListener(this);
        this.f1589c.run();
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        this.f1588b = view.getViewTreeObserver();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        boolean zIsAlive = this.f1588b.isAlive();
        View view2 = this.f1587a;
        if (zIsAlive) {
            this.f1588b.removeOnPreDrawListener(this);
        } else {
            view2.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        view2.removeOnAttachStateChangeListener(this);
    }
}
