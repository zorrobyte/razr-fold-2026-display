package C;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import h.C0135b;

/* JADX INFO: loaded from: classes.dex */
public final class o extends AnimatorListenerAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f59a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f60b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f61c;

    public /* synthetic */ o(int i2, View view, Object obj) {
        this.f59a = i2;
        this.f60b = obj;
        this.f61c = view;
    }

    public o(s sVar, C0135b c0135b) {
        this.f59a = 0;
        this.f61c = sVar;
        this.f60b = c0135b;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        switch (this.f59a) {
            case 2:
                ((v.n) this.f60b).a((View) this.f61c);
                break;
            default:
                super.onAnimationCancel(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.f59a) {
            case 0:
                ((C0135b) this.f60b).remove(animator);
                ((s) this.f61c).f84m.remove(animator);
                break;
            case 1:
                ((e0.k) this.f60b).k((View) this.f61c);
                break;
            default:
                ((v.n) this.f60b).c();
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        switch (this.f59a) {
            case 0:
                ((s) this.f61c).f84m.add(animator);
                break;
            case 1:
            default:
                super.onAnimationStart(animator);
                break;
            case 2:
                ((v.n) this.f60b).b();
                break;
        }
    }
}
