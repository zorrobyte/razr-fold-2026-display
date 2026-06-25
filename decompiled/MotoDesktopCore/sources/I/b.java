package I;

import android.animation.ValueAnimator;
import android.view.View;
import androidx.appcompat.app.C;
import androidx.recyclerview.widget.C0112e;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import e0.k;

/* JADX INFO: loaded from: classes.dex */
public final class b implements ValueAnimator.AnimatorUpdateListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f157a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f158b;

    public /* synthetic */ b(int i2, Object obj) {
        this.f157a = i2;
        this.f158b = obj;
    }

    public b(k kVar, View view) {
        this.f157a = 3;
        this.f158b = kVar;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.f157a) {
            case 0:
                ((CollapsingToolbarLayout) this.f158b).setScrimAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                break;
            case 1:
                ((TabLayout) this.f158b).scrollTo(((Integer) valueAnimator.getAnimatedValue()).intValue(), 0);
                break;
            case 2:
                int iFloatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
                C0112e c0112e = (C0112e) this.f158b;
                c0112e.f1862b.setAlpha(iFloatValue);
                c0112e.f1863c.setAlpha(iFloatValue);
                c0112e.f1874n.invalidate();
                break;
            default:
                ((View) ((C) ((k) this.f158b).f2495a).f495k.getParent()).invalidate();
                break;
        }
    }
}
