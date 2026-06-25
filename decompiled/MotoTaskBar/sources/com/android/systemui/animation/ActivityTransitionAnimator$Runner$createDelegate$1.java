package com.android.systemui.animation;

import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class ActivityTransitionAnimator$Runner$createDelegate$1 extends FunctionReferenceImpl implements Function0 {
    ActivityTransitionAnimator$Runner$createDelegate$1(Object obj) {
        super(0, obj, ActivityTransitionAnimator.Runner.class, "dispose", "dispose()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    /* JADX INFO: renamed from: invoke */
    public /* bridge */ /* synthetic */ Object mo2224invoke() {
        m1180invoke();
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
    public final void m1180invoke() {
        ((ActivityTransitionAnimator.Runner) this.receiver).dispose();
    }
}
