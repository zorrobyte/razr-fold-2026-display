package O;

import C.C0001b;
import android.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Property;
import android.view.View;
import androidx.appcompat.app.AbstractC0054a;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.VisibilityAwareImageButton;
import e0.k;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class d {

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public static final z.a f207s = H.a.f143b;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public static final int[] f208t = {R.attr.state_pressed, R.attr.state_enabled};

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public static final int[] f209u = {R.attr.state_hovered, R.attr.state_focused, R.attr.state_enabled};

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public static final int[] f210v = {R.attr.state_focused, R.attr.state_enabled};

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public static final int[] f211w = {R.attr.state_hovered, R.attr.state_enabled};

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public static final int[] f212x = {R.attr.state_enabled};

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public static final int[] f213y = new int[0];

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Animator f215b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public H.c f216c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public H.c f217d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public H.c f218e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public H.c f219f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public float f220g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public float f221h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public float f222i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public float f223j;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public ArrayList f225l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public ArrayList f226m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final VisibilityAwareImageButton f227n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final k f228o;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final Matrix f230q;
    public b r;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f214a = 0;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public float f224k = 1.0f;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final Rect f229p = new Rect();

    public d(VisibilityAwareImageButton visibilityAwareImageButton, k kVar) {
        new RectF();
        new RectF();
        this.f230q = new Matrix();
        this.f227n = visibilityAwareImageButton;
        this.f228o = kVar;
        f0.b bVar = new f0.b(1);
        e eVar = (e) this;
        bVar.a(f208t, b(new c(eVar)));
        bVar.a(f209u, b(new c(eVar)));
        bVar.a(f210v, b(new c(eVar)));
        bVar.a(f211w, b(new c(eVar)));
        bVar.a(f212x, b(new c(eVar)));
        bVar.a(f213y, b(new c(eVar)));
        this.f220g = visibilityAwareImageButton.getRotation();
    }

    public static ValueAnimator b(c cVar) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(f207s);
        valueAnimator.setDuration(100L);
        valueAnimator.addListener(cVar);
        valueAnimator.addUpdateListener(cVar);
        valueAnimator.setFloatValues(0.0f, 1.0f);
        return valueAnimator;
    }

    public final AnimatorSet a(H.c cVar, float f2, float f3, float f4) {
        ArrayList arrayList = new ArrayList();
        Property property = View.ALPHA;
        float[] fArr = {f2};
        VisibilityAwareImageButton visibilityAwareImageButton = this.f227n;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(visibilityAwareImageButton, (Property<VisibilityAwareImageButton, Float>) property, fArr);
        cVar.c("opacity").a(objectAnimatorOfFloat);
        arrayList.add(objectAnimatorOfFloat);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(visibilityAwareImageButton, (Property<VisibilityAwareImageButton, Float>) View.SCALE_X, f3);
        cVar.c("scale").a(objectAnimatorOfFloat2);
        arrayList.add(objectAnimatorOfFloat2);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(visibilityAwareImageButton, (Property<VisibilityAwareImageButton, Float>) View.SCALE_Y, f3);
        cVar.c("scale").a(objectAnimatorOfFloat3);
        arrayList.add(objectAnimatorOfFloat3);
        Matrix matrix = this.f230q;
        matrix.reset();
        visibilityAwareImageButton.getDrawable();
        ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(visibilityAwareImageButton, new C0001b(), new H.b(), new Matrix(matrix));
        cVar.c("iconScale").a(objectAnimatorOfObject);
        arrayList.add(objectAnimatorOfObject);
        AnimatorSet animatorSet = new AnimatorSet();
        AbstractC0054a.t(animatorSet, arrayList);
        return animatorSet;
    }

    public abstract float c();

    public abstract void d(Rect rect);

    public abstract void e();

    public abstract void f();

    public abstract void g(int[] iArr);

    public abstract void h(float f2, float f3, float f4);

    public abstract void i(Rect rect);

    public final void j() {
        Rect rect = this.f229p;
        d(rect);
        i(rect);
        int i2 = rect.left;
        ((FloatingActionButton) this.f228o.f2495a).getClass();
        throw null;
    }
}
