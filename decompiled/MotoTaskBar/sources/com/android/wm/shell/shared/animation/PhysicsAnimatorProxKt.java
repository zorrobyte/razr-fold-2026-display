package com.android.wm.shell.shared.animation;

import android.view.View;

/* JADX INFO: compiled from: PhysicsAnimatorProx.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PhysicsAnimatorProxKt {
    public static final PhysicsAnimator getPhysicsAnimator(View view) {
        view.getClass();
        return PhysicsAnimator.Companion.getInstance(view);
    }
}
