package O;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.Property;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.VisibilityAwareImageButton;
import e0.k;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class e extends d {
    @Override // O.d
    public final float c() {
        return this.f227n.getElevation();
    }

    @Override // O.d
    public final void d(Rect rect) {
        FloatingActionButton floatingActionButton = (FloatingActionButton) this.f228o.f2495a;
        if (!floatingActionButton.f2158i) {
            rect.set(0, 0, 0, 0);
            return;
        }
        floatingActionButton.getSizeDimension();
        float elevation = this.f227n.getElevation() + this.f223j;
        int i2 = T.a.f239b;
        int iCeil = (int) Math.ceil(elevation);
        int iCeil2 = (int) Math.ceil(elevation * 1.5f);
        rect.set(iCeil, iCeil2, iCeil, iCeil2);
    }

    @Override // O.d
    public final void e() {
    }

    @Override // O.d
    public final void f() {
        j();
        throw null;
    }

    @Override // O.d
    public final void g(int[] iArr) {
    }

    @Override // O.d
    public final void h(float f2, float f3, float f4) {
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(d.f208t, k(f2, f4));
        stateListAnimator.addState(d.f209u, k(f2, f3));
        stateListAnimator.addState(d.f210v, k(f2, f3));
        stateListAnimator.addState(d.f211w, k(f2, f3));
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        float[] fArr = {f2};
        VisibilityAwareImageButton visibilityAwareImageButton = this.f227n;
        arrayList.add(ObjectAnimator.ofFloat(visibilityAwareImageButton, "elevation", fArr).setDuration(0L));
        arrayList.add(ObjectAnimator.ofFloat(visibilityAwareImageButton, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, 0.0f).setDuration(100L));
        animatorSet.playSequentially((Animator[]) arrayList.toArray(new Animator[0]));
        animatorSet.setInterpolator(d.f207s);
        stateListAnimator.addState(d.f212x, animatorSet);
        stateListAnimator.addState(d.f213y, k(0.0f, 0.0f));
        visibilityAwareImageButton.setStateListAnimator(stateListAnimator);
        if (((FloatingActionButton) this.f228o.f2495a).f2158i) {
            j();
            throw null;
        }
    }

    @Override // O.d
    public final void i(Rect rect) {
        k kVar = this.f228o;
        FloatingActionButton floatingActionButton = (FloatingActionButton) kVar.f2495a;
        if (!floatingActionButton.f2158i) {
            super/*android.view.View*/.setBackgroundDrawable(null);
        } else {
            super/*android.view.View*/.setBackgroundDrawable(new InsetDrawable((Drawable) null, rect.left, rect.top, rect.right, rect.bottom));
        }
    }

    public final AnimatorSet k(float f2, float f3) {
        AnimatorSet animatorSet = new AnimatorSet();
        VisibilityAwareImageButton visibilityAwareImageButton = this.f227n;
        animatorSet.play(ObjectAnimator.ofFloat(visibilityAwareImageButton, "elevation", f2).setDuration(0L)).with(ObjectAnimator.ofFloat(visibilityAwareImageButton, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, f3).setDuration(100L));
        animatorSet.setInterpolator(d.f207s);
        return animatorSet;
    }
}
