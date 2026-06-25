package C;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.motorola.mobiledesktop.core.uinput.EventType;

/* JADX INFO: loaded from: classes.dex */
public final class p extends AnimatorListenerAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f62a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object f63b;

    public /* synthetic */ p(int i2, Object obj) {
        this.f62a = i2;
        this.f63b = obj;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        switch (this.f62a) {
            case 3:
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.f63b;
                actionBarOverlayLayout.f912w = null;
                actionBarOverlayLayout.f901k = false;
                break;
            default:
                super.onAnimationCancel(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.f62a) {
            case 0:
                ((s) this.f63b).m();
                animator.removeListener(this);
                break;
            case 1:
                ((HideBottomViewOnScrollBehavior) this.f63b).f2065c = null;
                break;
            case 2:
                f0.b bVar = (f0.b) this.f63b;
                if (((ValueAnimator) bVar.f2538c) == animator) {
                    bVar.f2538c = null;
                }
                break;
            case 3:
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.f63b;
                actionBarOverlayLayout.f912w = null;
                actionBarOverlayLayout.f901k = false;
                break;
            default:
                ((View) this.f63b).setLayerType(0, null);
                animator.removeListener(this);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        switch (this.f62a) {
            case EventType.EVENT_MSC /* 4 */:
                ((View) this.f63b).setLayerType(2, null);
                break;
            default:
                super.onAnimationStart(animator);
                break;
        }
    }
}
