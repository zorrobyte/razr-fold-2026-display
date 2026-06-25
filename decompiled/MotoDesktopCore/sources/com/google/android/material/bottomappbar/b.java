package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.q0;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public final class b extends AnimatorListenerAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f2084a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ActionMenuView f2085b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f2086c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ boolean f2087d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ BottomAppBar f2088e;

    public b(BottomAppBar bottomAppBar, ActionMenuView actionMenuView, int i2, boolean z2) {
        this.f2088e = bottomAppBar;
        this.f2085b = actionMenuView;
        this.f2086c = i2;
        this.f2087d = z2;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.f2084a = true;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        if (this.f2084a) {
            return;
        }
        int i2 = BottomAppBar.f2073U;
        BottomAppBar bottomAppBar = this.f2088e;
        bottomAppBar.getClass();
        WeakHashMap weakHashMap = l.f2836a;
        boolean z2 = bottomAppBar.getLayoutDirection() == 1;
        int iMax = 0;
        for (int i3 = 0; i3 < bottomAppBar.getChildCount(); i3++) {
            View childAt = bottomAppBar.getChildAt(i3);
            if ((childAt.getLayoutParams() instanceof q0) && (((q0) childAt.getLayoutParams()).f1289a & 8388615) == 8388611) {
                iMax = Math.max(iMax, z2 ? childAt.getLeft() : childAt.getRight());
            }
        }
        this.f2085b.setTranslationX((this.f2086c == 1 && this.f2087d) ? iMax - (z2 ? r0.getRight() : r0.getLeft()) : 0.0f);
    }
}
