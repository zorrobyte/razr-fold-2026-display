package H;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import h.k;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final k f149a = new k();

    public static c a(Context context, int i2) {
        try {
            Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(context, i2);
            if (animatorLoadAnimator instanceof AnimatorSet) {
                return b(((AnimatorSet) animatorLoadAnimator).getChildAnimations());
            }
            if (animatorLoadAnimator == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(animatorLoadAnimator);
            return b(arrayList);
        } catch (Exception e2) {
            Log.w("MotionSpec", "Can't load animation resource ID #0x" + Integer.toHexString(i2), e2);
            return null;
        }
    }

    public static c b(ArrayList arrayList) {
        c cVar = new c();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Animator animator = (Animator) arrayList.get(i2);
            if (!(animator instanceof ObjectAnimator)) {
                throw new IllegalArgumentException("Animator must be an ObjectAnimator: " + animator);
            }
            ObjectAnimator objectAnimator = (ObjectAnimator) animator;
            String propertyName = objectAnimator.getPropertyName();
            long startDelay = objectAnimator.getStartDelay();
            long duration = objectAnimator.getDuration();
            TimeInterpolator interpolator = objectAnimator.getInterpolator();
            if ((interpolator instanceof AccelerateDecelerateInterpolator) || interpolator == null) {
                interpolator = a.f142a;
            } else if (interpolator instanceof AccelerateInterpolator) {
                interpolator = a.f143b;
            } else if (interpolator instanceof DecelerateInterpolator) {
                interpolator = a.f144c;
            }
            d dVar = new d();
            dVar.f153d = 0;
            dVar.f154e = 1;
            dVar.f150a = startDelay;
            dVar.f151b = duration;
            dVar.f152c = interpolator;
            dVar.f153d = objectAnimator.getRepeatCount();
            dVar.f154e = objectAnimator.getRepeatMode();
            cVar.f149a.put(propertyName, dVar);
        }
        return cVar;
    }

    public final d c(String str) {
        k kVar = this.f149a;
        if (kVar.get(str) != null) {
            return (d) kVar.get(str);
        }
        throw new IllegalArgumentException();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || c.class != obj.getClass()) {
            return false;
        }
        return this.f149a.equals(((c) obj).f149a);
    }

    public final int hashCode() {
        return this.f149a.hashCode();
    }

    public final String toString() {
        return "\n" + c.class.getName() + '{' + Integer.toHexString(System.identityHashCode(this)) + " timings: " + this.f149a + "}\n";
    }
}
