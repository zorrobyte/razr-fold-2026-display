package androidx.fragment.app;

import android.view.View;
import android.view.animation.Animation;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: androidx.fragment.app.s, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0106s extends AbstractAnimationAnimationListenerC0107t {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public View f1652b;

    @Override // androidx.fragment.app.AbstractAnimationAnimationListenerC0107t, android.view.animation.Animation.AnimationListener
    public final void onAnimationEnd(Animation animation) {
        WeakHashMap weakHashMap = v.l.f2836a;
        View view = this.f1652b;
        view.isAttachedToWindow();
        view.post(new P(4, this));
        super.onAnimationEnd(animation);
    }
}
