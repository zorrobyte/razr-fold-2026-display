package androidx.appcompat.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

/* JADX INFO: loaded from: classes.dex */
public final class K implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1008a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ L f1009b;

    public /* synthetic */ K(L l2, int i2) {
        this.f1008a = i2;
        this.f1009b = l2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f1008a) {
            case 0:
                ViewParent parent = this.f1009b.f1013d.getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                break;
            default:
                L l2 = this.f1009b;
                l2.a();
                View view = l2.f1013d;
                if (view.isEnabled() && !view.isLongClickable() && l2.c()) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                    view.onTouchEvent(motionEventObtain);
                    motionEventObtain.recycle();
                    l2.f1016g = true;
                    break;
                }
                break;
        }
    }
}
