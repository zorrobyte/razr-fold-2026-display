package androidx.fragment.app;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;

/* JADX INFO: loaded from: classes.dex */
public final class u extends AnimationSet implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ViewGroup f1654a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final View f1655b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f1656c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f1657d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1658e;

    public u(Animation animation, ViewGroup viewGroup, View view) {
        super(false);
        this.f1658e = true;
        this.f1654a = viewGroup;
        this.f1655b = view;
        addAnimation(animation);
        viewGroup.post(this);
    }

    @Override // android.view.animation.AnimationSet, android.view.animation.Animation
    public final boolean getTransformation(long j2, Transformation transformation) {
        this.f1658e = true;
        if (this.f1656c) {
            return !this.f1657d;
        }
        if (!super.getTransformation(j2, transformation)) {
            this.f1656c = true;
            S.a(this.f1654a, this);
        }
        return true;
    }

    @Override // android.view.animation.Animation
    public final boolean getTransformation(long j2, Transformation transformation, float f2) {
        this.f1658e = true;
        if (this.f1656c) {
            return !this.f1657d;
        }
        if (!super.getTransformation(j2, transformation, f2)) {
            this.f1656c = true;
            S.a(this.f1654a, this);
        }
        return true;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z2 = this.f1656c;
        ViewGroup viewGroup = this.f1654a;
        if (z2 || !this.f1658e) {
            viewGroup.endViewTransition(this.f1655b);
            this.f1657d = true;
        } else {
            this.f1658e = false;
            viewGroup.post(this);
        }
    }
}
