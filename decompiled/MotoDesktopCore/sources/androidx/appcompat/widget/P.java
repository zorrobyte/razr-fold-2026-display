package androidx.appcompat.widget;

import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class P implements View.OnTouchListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Q f1043a;

    public P(Q q2) {
        this.f1043a = q2;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        C0083u c0083u;
        int action = motionEvent.getAction();
        int x2 = (int) motionEvent.getX();
        int y2 = (int) motionEvent.getY();
        Q q2 = this.f1043a;
        if (action == 0 && (c0083u = q2.f1070y) != null && c0083u.isShowing() && x2 >= 0 && x2 < q2.f1070y.getWidth() && y2 >= 0 && y2 < q2.f1070y.getHeight()) {
            q2.f1066u.postDelayed(q2.f1063q, 250L);
            return false;
        }
        if (action != 1) {
            return false;
        }
        q2.f1066u.removeCallbacks(q2.f1063q);
        return false;
    }
}
