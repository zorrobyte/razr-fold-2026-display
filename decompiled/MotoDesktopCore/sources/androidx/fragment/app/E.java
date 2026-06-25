package androidx.fragment.app;

import android.graphics.Rect;
import android.view.View;
import h.C0135b;

/* JADX INFO: loaded from: classes.dex */
public final class E implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AbstractComponentCallbacksC0098j f1507a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AbstractComponentCallbacksC0098j f1508b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ boolean f1509c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ View f1510d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ O f1511e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ Rect f1512f;

    public E(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2, boolean z2, C0135b c0135b, View view, O o2, Rect rect) {
        this.f1507a = abstractComponentCallbacksC0098j;
        this.f1508b = abstractComponentCallbacksC0098j2;
        this.f1509c = z2;
        this.f1510d = view;
        this.f1511e = o2;
        this.f1512f = rect;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.f1509c) {
            this.f1508b.getEnterTransitionCallback();
        } else {
            this.f1507a.getEnterTransitionCallback();
        }
        View view = this.f1510d;
        if (view != null) {
            this.f1511e.getClass();
            O.i(this.f1512f, view);
        }
    }
}
