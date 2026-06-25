package androidx.fragment.app;

import android.view.animation.Animation;

/* JADX INFO: renamed from: androidx.fragment.app.t, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractAnimationAnimationListenerC0107t implements Animation.AnimationListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Animation.AnimationListener f1653a;

    public AbstractAnimationAnimationListenerC0107t(Animation.AnimationListener animationListener) {
        this.f1653a = animationListener;
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        Animation.AnimationListener animationListener = this.f1653a;
        if (animationListener != null) {
            animationListener.onAnimationEnd(animation);
        }
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationRepeat(Animation animation) {
        Animation.AnimationListener animationListener = this.f1653a;
        if (animationListener != null) {
            animationListener.onAnimationRepeat(animation);
        }
    }

    @Override // android.view.animation.Animation.AnimationListener
    public final void onAnimationStart(Animation animation) {
        Animation.AnimationListener animationListener = this.f1653a;
        if (animationListener != null) {
            animationListener.onAnimationStart(animation);
        }
    }
}
