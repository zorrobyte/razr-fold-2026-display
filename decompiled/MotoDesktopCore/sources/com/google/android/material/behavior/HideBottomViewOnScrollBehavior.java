package com.google.android.material.behavior;

import C.p;
import H.a;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import k.AbstractC0143b;

/* JADX INFO: loaded from: classes.dex */
public class HideBottomViewOnScrollBehavior<V extends View> extends AbstractC0143b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2063a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2064b = 2;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public ViewPropertyAnimator f2065c;

    public HideBottomViewOnScrollBehavior() {
    }

    public HideBottomViewOnScrollBehavior(Context context, AttributeSet attributeSet) {
    }

    @Override // k.AbstractC0143b
    public boolean f(CoordinatorLayout coordinatorLayout, View view, int i2) {
        this.f2063a = view.getMeasuredHeight();
        return false;
    }

    @Override // k.AbstractC0143b
    public final void j(View view, int i2) {
        int i3 = this.f2064b;
        if (i3 != 1 && i2 > 0) {
            s(view);
        } else {
            if (i3 == 2 || i2 >= 0) {
                return;
            }
            t(view);
        }
    }

    @Override // k.AbstractC0143b
    public final boolean o(int i2) {
        return i2 == 2;
    }

    public void s(View view) {
        ViewPropertyAnimator viewPropertyAnimator = this.f2065c;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            view.clearAnimation();
        }
        this.f2064b = 1;
        this.f2065c = view.animate().translationY(this.f2063a).setInterpolator(a.f143b).setDuration(175L).setListener(new p(1, this));
    }

    public void t(View view) {
        ViewPropertyAnimator viewPropertyAnimator = this.f2065c;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            view.clearAnimation();
        }
        this.f2064b = 2;
        this.f2065c = view.animate().translationY(0).setInterpolator(a.f144c).setDuration(225L).setListener(new p(1, this));
    }
}
