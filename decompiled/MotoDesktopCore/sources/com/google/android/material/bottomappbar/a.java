package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* JADX INFO: loaded from: classes.dex */
public final class a extends AnimatorListenerAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2082a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BottomAppBar f2083b;

    public /* synthetic */ a(BottomAppBar bottomAppBar, int i2) {
        this.f2082a = i2;
        this.f2083b = bottomAppBar;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.f2082a) {
            case 0:
                this.f2083b.f2074P = null;
                break;
            default:
                this.f2083b.f2075Q = null;
                break;
        }
    }
}
