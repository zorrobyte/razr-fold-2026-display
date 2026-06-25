package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class r extends AnimatorListenerAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ViewGroup f1649a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ View f1650b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AbstractComponentCallbacksC0098j f1651c;

    public r(ViewGroup viewGroup, View view, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        this.f1649a = viewGroup;
        this.f1650b = view;
        this.f1651c = abstractComponentCallbacksC0098j;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        this.f1649a.endViewTransition(this.f1650b);
        animator.removeListener(this);
        View view = this.f1651c.mView;
        if (view != null) {
            view.setVisibility(8);
        }
    }
}
