package com.android.wm.shell.shared.animation;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: PhysicsAnimator.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class PhysicsAnimator$startAction$1 extends FunctionReferenceImpl implements Function0 {
    PhysicsAnimator$startAction$1(Object obj) {
        super(0, obj, PhysicsAnimator.class, "startInternal", "startInternal$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    /* JADX INFO: renamed from: invoke */
    public /* bridge */ /* synthetic */ Object mo2224invoke() {
        m2135invoke();
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
    public final void m2135invoke() {
        ((PhysicsAnimator) this.receiver).startInternal$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared();
    }
}
