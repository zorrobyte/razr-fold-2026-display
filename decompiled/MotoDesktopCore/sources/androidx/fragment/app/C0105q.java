package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: renamed from: androidx.fragment.app.q, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0105q extends AnimatorListenerAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ViewGroup f1645a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ View f1646b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AbstractComponentCallbacksC0098j f1647c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ z f1648d;

    public C0105q(z zVar, ViewGroup viewGroup, View view, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        this.f1648d = zVar;
        this.f1645a = viewGroup;
        this.f1646b = view;
        this.f1647c = abstractComponentCallbacksC0098j;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        ViewGroup viewGroup = this.f1645a;
        View view = this.f1646b;
        viewGroup.endViewTransition(view);
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = this.f1647c;
        Animator animator2 = abstractComponentCallbacksC0098j.getAnimator();
        abstractComponentCallbacksC0098j.setAnimator(null);
        if (animator2 == null || viewGroup.indexOfChild(view) >= 0) {
            return;
        }
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2 = this.f1647c;
        this.f1648d.S(abstractComponentCallbacksC0098j2, abstractComponentCallbacksC0098j2.getStateAfterAnimating(), 0, 0, false);
    }
}
