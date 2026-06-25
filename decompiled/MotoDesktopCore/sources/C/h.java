package C;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import androidx.recyclerview.widget.C0112e;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class h extends AnimatorListenerAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f44a = 1;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f45b = false;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Object f46c;

    public h(O.d dVar) {
        this.f46c = dVar;
    }

    public h(View view) {
        this.f46c = view;
    }

    public h(C0112e c0112e) {
        this.f46c = c0112e;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        switch (this.f44a) {
            case 2:
                this.f45b = true;
                break;
            default:
                super.onAnimationCancel(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.f44a) {
            case 0:
                View view = (View) this.f46c;
                A.b(view, 1.0f);
                if (this.f45b) {
                    view.setLayerType(0, null);
                }
                break;
            case 1:
                O.d dVar = (O.d) this.f46c;
                dVar.f214a = 0;
                dVar.f215b = null;
                break;
            default:
                if (!this.f45b) {
                    C0112e c0112e = (C0112e) this.f46c;
                    if (((Float) c0112e.f1880u.getAnimatedValue()).floatValue() != 0.0f) {
                        c0112e.f1881v = 2;
                        c0112e.f1874n.invalidate();
                    } else {
                        c0112e.f1881v = 0;
                        c0112e.e(0);
                    }
                } else {
                    this.f45b = false;
                }
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        Object obj = this.f46c;
        switch (this.f44a) {
            case 0:
                WeakHashMap weakHashMap = v.l.f2836a;
                View view = (View) obj;
                if (view.hasOverlappingRendering() && view.getLayerType() == 0) {
                    this.f45b = true;
                    view.setLayerType(2, null);
                    break;
                }
                break;
            case 1:
                O.d dVar = (O.d) obj;
                dVar.f227n.a(0, this.f45b);
                dVar.f214a = 2;
                dVar.f215b = animator;
                break;
            default:
                super.onAnimationStart(animator);
                break;
        }
    }
}
