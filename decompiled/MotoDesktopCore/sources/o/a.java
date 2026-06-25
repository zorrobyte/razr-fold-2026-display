package O;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.google.android.material.internal.VisibilityAwareImageButton;

/* JADX INFO: loaded from: classes.dex */
public final class a extends AnimatorListenerAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f201a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ boolean f202b = false;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ d f203c;

    public a(d dVar) {
        this.f203c = dVar;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.f201a = true;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        d dVar = this.f203c;
        dVar.f214a = 0;
        dVar.f215b = null;
        if (this.f201a) {
            return;
        }
        VisibilityAwareImageButton visibilityAwareImageButton = dVar.f227n;
        boolean z2 = this.f202b;
        visibilityAwareImageButton.a(z2 ? 8 : 4, z2);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        d dVar = this.f203c;
        dVar.f227n.a(0, this.f202b);
        dVar.f214a = 1;
        dVar.f215b = animator;
        this.f201a = false;
    }
}
