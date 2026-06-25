package k;

import android.graphics.Rect;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* JADX INFO: renamed from: k.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0143b {
    public boolean a(View view) {
        return false;
    }

    public boolean b(View view, View view2) {
        return false;
    }

    public void c(C0146e c0146e) {
    }

    public boolean d(CoordinatorLayout coordinatorLayout, View view, View view2) {
        return false;
    }

    public boolean e(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        return false;
    }

    public boolean f(CoordinatorLayout coordinatorLayout, View view, int i2) {
        return false;
    }

    public boolean g(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, int i4) {
        return false;
    }

    public boolean h(View view) {
        return false;
    }

    public void i(CoordinatorLayout coordinatorLayout, View view, View view2, int i2, int[] iArr, int i3) {
    }

    public void j(View view, int i2) {
    }

    public void k(CoordinatorLayout coordinatorLayout, View view, View view2, int i2, int i3, int i4, int i5, int i6) {
        if (i6 == 0) {
            j(view, i3);
        }
    }

    public boolean l(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z2) {
        return false;
    }

    public void m(View view, Parcelable parcelable) {
    }

    public Parcelable n(View view) {
        return View.BaseSavedState.EMPTY_STATE;
    }

    public boolean o(int i2) {
        return false;
    }

    public boolean p(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i2, int i3) {
        if (i3 == 0) {
            return o(i2);
        }
        return false;
    }

    public void q(CoordinatorLayout coordinatorLayout, View view, View view2, int i2) {
    }

    public boolean r(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        return false;
    }
}
