package androidx.fragment.app;

import android.view.ViewGroup;
import android.view.animation.Animation;

/* JADX INFO: renamed from: androidx.fragment.app.p, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0104p extends AbstractAnimationAnimationListenerC0107t {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ViewGroup f1642b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AbstractComponentCallbacksC0098j f1643c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ z f1644d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0104p(z zVar, Animation.AnimationListener animationListener, ViewGroup viewGroup, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        super(animationListener);
        this.f1644d = zVar;
        this.f1642b = viewGroup;
        this.f1643c = abstractComponentCallbacksC0098j;
    }

    @Override // androidx.fragment.app.AbstractAnimationAnimationListenerC0107t, android.view.animation.Animation.AnimationListener
    public final void onAnimationEnd(Animation animation) {
        super.onAnimationEnd(animation);
        this.f1642b.post(new P(3, this));
    }
}
