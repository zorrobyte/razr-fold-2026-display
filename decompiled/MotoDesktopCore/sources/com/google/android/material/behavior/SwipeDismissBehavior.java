package com.google.android.material.behavior;

import J.a;
import android.view.MotionEvent;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import k.AbstractC0143b;
import x.C0165b;

/* JADX INFO: loaded from: classes.dex */
public class SwipeDismissBehavior<V extends View> extends AbstractC0143b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public C0165b f2066a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f2067b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2068c = 2;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final float f2069d = 0.5f;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public float f2070e = 0.0f;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public float f2071f = 0.5f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final a f2072g = new a(this);

    @Override // k.AbstractC0143b
    public boolean e(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        boolean zN = this.f2067b;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            zN = coordinatorLayout.n(view, (int) motionEvent.getX(), (int) motionEvent.getY());
            this.f2067b = zN;
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.f2067b = false;
        }
        if (!zN) {
            return false;
        }
        if (this.f2066a == null) {
            this.f2066a = new C0165b(coordinatorLayout.getContext(), coordinatorLayout, this.f2072g);
        }
        return this.f2066a.r(motionEvent);
    }

    @Override // k.AbstractC0143b
    public final boolean r(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        C0165b c0165b = this.f2066a;
        if (c0165b == null) {
            return false;
        }
        c0165b.k(motionEvent);
        return true;
    }

    public boolean s(View view) {
        return true;
    }
}
