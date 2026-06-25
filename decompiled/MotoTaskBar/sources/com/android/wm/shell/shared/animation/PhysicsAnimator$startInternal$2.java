package com.android.wm.shell.shared.animation;

import androidx.dynamicanimation.animation.SpringAnimation;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: PhysicsAnimator.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class PhysicsAnimator$startInternal$2 extends FunctionReferenceImpl implements Function0 {
    PhysicsAnimator$startInternal$2(Object obj) {
        super(0, obj, SpringAnimation.class, "start", "start()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    /* JADX INFO: renamed from: invoke */
    public /* bridge */ /* synthetic */ Object mo2224invoke() {
        m2136invoke();
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
    public final void m2136invoke() {
        ((SpringAnimation) this.receiver).start();
    }
}
