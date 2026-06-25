package com.android.systemui.media.controls.ui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MetadataAnimationHandler.kt */
/* JADX INFO: loaded from: classes.dex */
public class MetadataAnimationHandler extends AnimatorListenerAdapter {
    private final Animator enterAnimator;
    private final Animator exitAnimator;
    private Function0 postEnterUpdate;
    private Function0 postExitUpdate;
    private Object targetData;

    public MetadataAnimationHandler(Animator animator, Animator animator2) {
        animator.getClass();
        animator2.getClass();
        this.exitAnimator = animator;
        this.enterAnimator = animator2;
        animator.addListener(this);
        animator2.addListener(this);
    }

    public final boolean isRunning() {
        return this.enterAnimator.isRunning() || this.exitAnimator.isRunning();
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        animator.getClass();
        if (animator == this.exitAnimator) {
            Function0 function0 = this.postExitUpdate;
            if (function0 != null) {
                function0.mo2224invoke();
            }
            this.postExitUpdate = null;
            this.enterAnimator.start();
        }
        if (animator == this.enterAnimator) {
            if (this.postExitUpdate != null) {
                this.exitAnimator.start();
                return;
            }
            Function0 function02 = this.postEnterUpdate;
            if (function02 != null) {
                function02.mo2224invoke();
            }
            this.postEnterUpdate = null;
        }
    }

    public final boolean setNext(Object obj, Function0 function0, Function0 function02) {
        obj.getClass();
        function0.getClass();
        function02.getClass();
        if (Intrinsics.areEqual(obj, this.targetData)) {
            return false;
        }
        this.targetData = obj;
        this.postExitUpdate = function0;
        this.postEnterUpdate = function02;
        if (isRunning()) {
            return true;
        }
        this.exitAnimator.start();
        return true;
    }
}
