package androidx.appcompat.widget;

import android.view.View;
import android.view.ViewConfiguration;

/* JADX INFO: loaded from: classes.dex */
public abstract class L implements View.OnTouchListener, View.OnAttachStateChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final float f1010a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final int f1011b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f1012c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final View f1013d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public K f1014e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public K f1015f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f1016g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f1017h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final int[] f1018i = new int[2];

    public L(View view) {
        this.f1013d = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener(this);
        this.f1010a = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        int tapTimeout = ViewConfiguration.getTapTimeout();
        this.f1011b = tapTimeout;
        this.f1012c = (ViewConfiguration.getLongPressTimeout() + tapTimeout) / 2;
    }

    public final void a() {
        K k2 = this.f1015f;
        View view = this.f1013d;
        if (k2 != null) {
            view.removeCallbacks(k2);
        }
        K k3 = this.f1014e;
        if (k3 != null) {
            view.removeCallbacks(k3);
        }
    }

    public abstract androidx.appcompat.view.menu.E b();

    public abstract boolean c();

    public boolean d() {
        androidx.appcompat.view.menu.E eB = b();
        if (eB == null || !eB.isShowing()) {
            return true;
        }
        eB.dismiss();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0100  */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onTouch(android.view.View r13, android.view.MotionEvent r14) {
        /*
            Method dump skipped, instruction units count: 284
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.L.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.f1016g = false;
        this.f1017h = -1;
        K k2 = this.f1014e;
        if (k2 != null) {
            this.f1013d.removeCallbacks(k2);
        }
    }
}
