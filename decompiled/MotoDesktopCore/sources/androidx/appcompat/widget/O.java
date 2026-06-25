package androidx.appcompat.widget;

import android.os.Handler;
import android.widget.AbsListView;

/* JADX INFO: loaded from: classes.dex */
public final class O implements AbsListView.OnScrollListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Q f1042a;

    public O(Q q2) {
        this.f1042a = q2;
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public final void onScroll(AbsListView absListView, int i2, int i3, int i4) {
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public final void onScrollStateChanged(AbsListView absListView, int i2) {
        if (i2 == 1) {
            Q q2 = this.f1042a;
            if (q2.f1070y.getInputMethodMode() == 2 || q2.f1070y.getContentView() == null) {
                return;
            }
            Handler handler = q2.f1066u;
            N n2 = q2.f1063q;
            handler.removeCallbacks(n2);
            n2.run();
        }
    }
}
