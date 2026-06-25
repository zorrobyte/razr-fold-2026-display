package com.google.android.material.snackbar;

import C.n;
import U.d;
import android.view.MotionEvent;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.behavior.SwipeDismissBehavior;

/* JADX INFO: loaded from: classes.dex */
public class BaseTransientBottomBar$Behavior extends SwipeDismissBehavior<View> {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final n f2187h;

    public BaseTransientBottomBar$Behavior() {
        n nVar = new n(2);
        this.f2070e = Math.min(Math.max(0.0f, 0.1f), 1.0f);
        this.f2071f = Math.min(Math.max(0.0f, 0.6f), 1.0f);
        this.f2068c = 0;
        this.f2187h = nVar;
    }

    @Override // com.google.android.material.behavior.SwipeDismissBehavior, k.AbstractC0143b
    public final boolean e(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        this.f2187h.getClass();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1 || actionMasked == 3) {
                if (d.f242b == null) {
                    d.f242b = new d();
                }
                synchronized (d.f242b.f243a) {
                }
            }
        } else if (coordinatorLayout.n(view, (int) motionEvent.getX(), (int) motionEvent.getY())) {
            if (d.f242b == null) {
                d.f242b = new d();
            }
            synchronized (d.f242b.f243a) {
            }
        }
        return super.e(coordinatorLayout, view, motionEvent);
    }

    @Override // com.google.android.material.behavior.SwipeDismissBehavior
    public final boolean s(View view) {
        this.f2187h.getClass();
        return view instanceof BaseTransientBottomBar$SnackbarBaseLayout;
    }
}
