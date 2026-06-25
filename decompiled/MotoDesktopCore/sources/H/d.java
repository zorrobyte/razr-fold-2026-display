package H;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;

/* JADX INFO: loaded from: classes.dex */
public final class d {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public long f150a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public long f151b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public TimeInterpolator f152c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f153d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f154e;

    public final void a(Animator animator) {
        animator.setStartDelay(this.f150a);
        animator.setDuration(this.f151b);
        animator.setInterpolator(b());
        if (animator instanceof ValueAnimator) {
            ValueAnimator valueAnimator = (ValueAnimator) animator;
            valueAnimator.setRepeatCount(this.f153d);
            valueAnimator.setRepeatMode(this.f154e);
        }
    }

    public final TimeInterpolator b() {
        TimeInterpolator timeInterpolator = this.f152c;
        return timeInterpolator != null ? timeInterpolator : a.f142a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || d.class != obj.getClass()) {
            return false;
        }
        d dVar = (d) obj;
        if (this.f150a == dVar.f150a && this.f151b == dVar.f151b && this.f153d == dVar.f153d && this.f154e == dVar.f154e) {
            return b().getClass().equals(dVar.b().getClass());
        }
        return false;
    }

    public final int hashCode() {
        long j2 = this.f150a;
        long j3 = this.f151b;
        return ((((b().getClass().hashCode() + (((((int) (j2 ^ (j2 >>> 32))) * 31) + ((int) ((j3 >>> 32) ^ j3))) * 31)) * 31) + this.f153d) * 31) + this.f154e;
    }

    public final String toString() {
        return "\n" + d.class.getName() + '{' + Integer.toHexString(System.identityHashCode(this)) + " delay: " + this.f150a + " duration: " + this.f151b + " interpolator: " + b().getClass() + " repeatCount: " + this.f153d + " repeatMode: " + this.f154e + "}\n";
    }
}
