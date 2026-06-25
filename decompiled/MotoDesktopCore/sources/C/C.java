package C;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class C extends AnimatorListenerAdapter implements r {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final View f13a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final int f14b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final ViewGroup f15c;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f17e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f18f = false;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final boolean f16d = true;

    public C(View view, int i2) {
        this.f13a = view;
        this.f14b = i2;
        this.f15c = (ViewGroup) view.getParent();
        e(true);
    }

    @Override // C.r
    public final void a(s sVar) {
        if (!this.f18f) {
            A.c(this.f13a, this.f14b);
            ViewGroup viewGroup = this.f15c;
            if (viewGroup != null) {
                viewGroup.invalidate();
            }
        }
        e(false);
        sVar.v(this);
    }

    @Override // C.r
    public final void b() {
        e(false);
    }

    @Override // C.r
    public final void c() {
    }

    @Override // C.r
    public final void d() {
        e(true);
    }

    public final void e(boolean z2) {
        ViewGroup viewGroup;
        if (!this.f16d || this.f17e == z2 || (viewGroup = this.f15c) == null) {
            return;
        }
        this.f17e = z2;
        Y.r.p0(viewGroup, z2);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        this.f18f = true;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        if (!this.f18f) {
            A.c(this.f13a, this.f14b);
            ViewGroup viewGroup = this.f15c;
            if (viewGroup != null) {
                viewGroup.invalidate();
            }
        }
        e(false);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public final void onAnimationPause(Animator animator) {
        if (this.f18f) {
            return;
        }
        A.c(this.f13a, this.f14b);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationRepeat(Animator animator) {
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
    public final void onAnimationResume(Animator animator) {
        if (this.f18f) {
            return;
        }
        A.c(this.f13a, 0);
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
    }
}
