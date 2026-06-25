package com.android.systemui.animation;

import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
/* JADX INFO: loaded from: classes.dex */
final class ActivityTransitionAnimator$Runner$initAndRun$1$success$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $controllerFactory;
    final /* synthetic */ Function1 $performAnimation;
    int label;
    final /* synthetic */ ActivityTransitionAnimator.Runner this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ActivityTransitionAnimator$Runner$initAndRun$1$success$1(ActivityTransitionAnimator.Runner runner, Function1 function1, Function1 function12, Continuation continuation) {
        super(2, continuation);
        this.this$0 = runner;
        this.$controllerFactory = function1;
        this.$performAnimation = function12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActivityTransitionAnimator$Runner$initAndRun$1$success$1(this.this$0, this.$controllerFactory, this.$performAnimation, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ActivityTransitionAnimator$Runner$initAndRun$1$success$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ActivityTransitionAnimator.Runner runner = this.this$0;
            Function1 function1 = this.$controllerFactory;
            this.label = 1;
            if (runner.setUp(function1, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Boxing.boxBoolean(this.this$0.startAnimation(this.$performAnimation));
    }
}
