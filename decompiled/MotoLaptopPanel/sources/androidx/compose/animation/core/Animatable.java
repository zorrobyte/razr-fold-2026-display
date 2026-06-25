package androidx.compose.animation.core;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Animatable.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Animatable {
    public static final int $stable = 8;
    private final SpringSpec defaultSpringSpec;
    private final AnimationState internalState;
    private final MutableState isRunning$delegate;
    private final String label;
    private AnimationVector lowerBoundVector;
    private final MutatorMutex mutatorMutex;
    private final AnimationVector negativeInfinityBounds;
    private final AnimationVector positiveInfinityBounds;
    private final MutableState targetValue$delegate;
    private final TwoWayConverter typeConverter;
    private AnimationVector upperBoundVector;
    private final Object visibilityThreshold;

    /* JADX INFO: renamed from: androidx.compose.animation.core.Animatable$runAnimation$2, reason: invalid class name */
    /* JADX INFO: compiled from: Animatable.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function1 {
        final /* synthetic */ Animation $animation;
        final /* synthetic */ Function1 $block;
        final /* synthetic */ Object $initialVelocity;
        final /* synthetic */ long $startTime;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Object obj, Animation animation, long j, Function1 function1, Continuation continuation) {
            super(1, continuation);
            this.$initialVelocity = obj;
            this.$animation = animation;
            this.$startTime = j;
            this.$block = function1;
        }

        public final Continuation create(Continuation continuation) {
            return Animatable.this.new AnonymousClass2(this.$initialVelocity, this.$animation, this.$startTime, this.$block, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation continuation) {
            return ((AnonymousClass2) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            AnimationState animationState;
            Ref$BooleanRef ref$BooleanRef;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Animatable.this.getInternalState$animation_core_release().setVelocityVector$animation_core_release((AnimationVector) Animatable.this.getTypeConverter().getConvertToVector().invoke(this.$initialVelocity));
                    Animatable.this.setTargetValue(this.$animation.getTargetValue());
                    Animatable.this.setRunning(true);
                    final AnimationState animationStateCopy$default = AnimationStateKt.copy$default(Animatable.this.getInternalState$animation_core_release(), null, null, 0L, Long.MIN_VALUE, false, 23, null);
                    final Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
                    Animation animation = this.$animation;
                    long j = this.$startTime;
                    final Animatable animatable = Animatable.this;
                    final Function1 function1 = this.$block;
                    Function1 function12 = new Function1() { // from class: androidx.compose.animation.core.Animatable.runAnimation.2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                            invoke((AnimationScope) obj2);
                            return Unit.INSTANCE;
                        }

                        public final void invoke(AnimationScope animationScope) {
                            SuspendAnimationKt.updateState(animationScope, animatable.getInternalState$animation_core_release());
                            Object objClampToBounds = animatable.clampToBounds(animationScope.getValue());
                            if (Intrinsics.areEqual(objClampToBounds, animationScope.getValue())) {
                                Function1 function13 = function1;
                                if (function13 != null) {
                                    function13.invoke(animatable);
                                    return;
                                }
                                return;
                            }
                            animatable.getInternalState$animation_core_release().setValue$animation_core_release(objClampToBounds);
                            animationStateCopy$default.setValue$animation_core_release(objClampToBounds);
                            Function1 function14 = function1;
                            if (function14 != null) {
                                function14.invoke(animatable);
                            }
                            animationScope.cancelAnimation();
                            ref$BooleanRef2.element = true;
                        }
                    };
                    this.L$0 = animationStateCopy$default;
                    this.L$1 = ref$BooleanRef2;
                    this.label = 1;
                    if (SuspendAnimationKt.animate(animationStateCopy$default, animation, j, function12, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    animationState = animationStateCopy$default;
                    ref$BooleanRef = ref$BooleanRef2;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ref$BooleanRef = (Ref$BooleanRef) this.L$1;
                    animationState = (AnimationState) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                AnimationEndReason animationEndReason = ref$BooleanRef.element ? AnimationEndReason.BoundReached : AnimationEndReason.Finished;
                Animatable.this.endAnimation();
                return new AnimationResult(animationState, animationEndReason);
            } catch (CancellationException e) {
                Animatable.this.endAnimation();
                throw e;
            }
        }
    }

    /* JADX INFO: renamed from: androidx.compose.animation.core.Animatable$snapTo$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Animatable.kt */
    final class C00292 extends SuspendLambda implements Function1 {
        final /* synthetic */ Object $targetValue;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00292(Object obj, Continuation continuation) {
            super(1, continuation);
            this.$targetValue = obj;
        }

        public final Continuation create(Continuation continuation) {
            return Animatable.this.new C00292(this.$targetValue, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation continuation) {
            return ((C00292) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Animatable.this.endAnimation();
            Object objClampToBounds = Animatable.this.clampToBounds(this.$targetValue);
            Animatable.this.getInternalState$animation_core_release().setValue$animation_core_release(objClampToBounds);
            Animatable.this.setTargetValue(objClampToBounds);
            return Unit.INSTANCE;
        }
    }

    public Animatable(Object obj, TwoWayConverter twoWayConverter, Object obj2, String str) {
        this.typeConverter = twoWayConverter;
        this.visibilityThreshold = obj2;
        this.label = str;
        this.internalState = new AnimationState(twoWayConverter, obj, null, 0L, 0L, false, 60, null);
        this.isRunning$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, null, 2, null);
        this.targetValue$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(obj, null, 2, null);
        this.mutatorMutex = new MutatorMutex();
        this.defaultSpringSpec = new SpringSpec(0.0f, 0.0f, obj2, 3, null);
        AnimationVector velocityVector = getVelocityVector();
        AnimationVector animationVector = velocityVector instanceof AnimationVector1D ? AnimatableKt.negativeInfinityBounds1D : velocityVector instanceof AnimationVector2D ? AnimatableKt.negativeInfinityBounds2D : velocityVector instanceof AnimationVector3D ? AnimatableKt.negativeInfinityBounds3D : AnimatableKt.negativeInfinityBounds4D;
        animationVector.getClass();
        this.negativeInfinityBounds = animationVector;
        AnimationVector velocityVector2 = getVelocityVector();
        AnimationVector animationVector2 = velocityVector2 instanceof AnimationVector1D ? AnimatableKt.positiveInfinityBounds1D : velocityVector2 instanceof AnimationVector2D ? AnimatableKt.positiveInfinityBounds2D : velocityVector2 instanceof AnimationVector3D ? AnimatableKt.positiveInfinityBounds3D : AnimatableKt.positiveInfinityBounds4D;
        animationVector2.getClass();
        this.positiveInfinityBounds = animationVector2;
        this.lowerBoundVector = animationVector;
        this.upperBoundVector = animationVector2;
    }

    public /* synthetic */ Animatable(Object obj, TwoWayConverter twoWayConverter, Object obj2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, twoWayConverter, (i & 4) != 0 ? null : obj2, (i & 8) != 0 ? "Animatable" : str);
    }

    public static /* synthetic */ Object animateTo$default(Animatable animatable, Object obj, AnimationSpec animationSpec, Object obj2, Function1 function1, Continuation continuation, int i, Object obj3) {
        if ((i & 2) != 0) {
            animationSpec = animatable.defaultSpringSpec;
        }
        AnimationSpec animationSpec2 = animationSpec;
        if ((i & 4) != 0) {
            obj2 = animatable.getVelocity();
        }
        Object obj4 = obj2;
        if ((i & 8) != 0) {
            function1 = null;
        }
        return animatable.animateTo(obj, animationSpec2, obj4, function1, continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object clampToBounds(Object obj) {
        if (!Intrinsics.areEqual(this.lowerBoundVector, this.negativeInfinityBounds) || !Intrinsics.areEqual(this.upperBoundVector, this.positiveInfinityBounds)) {
            AnimationVector animationVector = (AnimationVector) this.typeConverter.getConvertToVector().invoke(obj);
            int size$animation_core_release = animationVector.getSize$animation_core_release();
            boolean z = false;
            for (int i = 0; i < size$animation_core_release; i++) {
                if (animationVector.get$animation_core_release(i) < this.lowerBoundVector.get$animation_core_release(i) || animationVector.get$animation_core_release(i) > this.upperBoundVector.get$animation_core_release(i)) {
                    animationVector.set$animation_core_release(i, RangesKt.coerceIn(animationVector.get$animation_core_release(i), this.lowerBoundVector.get$animation_core_release(i), this.upperBoundVector.get$animation_core_release(i)));
                    z = true;
                }
            }
            if (z) {
                return this.typeConverter.getConvertFromVector().invoke(animationVector);
            }
        }
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void endAnimation() {
        AnimationState animationState = this.internalState;
        animationState.getVelocityVector().reset$animation_core_release();
        animationState.setLastFrameTimeNanos$animation_core_release(Long.MIN_VALUE);
        setRunning(false);
    }

    private final Object runAnimation(Animation animation, Object obj, Function1 function1, Continuation continuation) {
        return MutatorMutex.mutate$default(this.mutatorMutex, null, new AnonymousClass2(obj, animation, this.internalState.getLastFrameTimeNanos(), function1, null), continuation, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setRunning(boolean z) {
        this.isRunning$delegate.setValue(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setTargetValue(Object obj) {
        this.targetValue$delegate.setValue(obj);
    }

    public final Object animateTo(Object obj, AnimationSpec animationSpec, Object obj2, Function1 function1, Continuation continuation) {
        return runAnimation(AnimationKt.TargetBasedAnimation(animationSpec, this.typeConverter, getValue(), obj, obj2), obj2, function1, continuation);
    }

    public final State asState() {
        return this.internalState;
    }

    public final AnimationState getInternalState$animation_core_release() {
        return this.internalState;
    }

    public final Object getTargetValue() {
        return this.targetValue$delegate.getValue();
    }

    public final TwoWayConverter getTypeConverter() {
        return this.typeConverter;
    }

    public final Object getValue() {
        return this.internalState.getValue();
    }

    public final Object getVelocity() {
        return this.typeConverter.getConvertFromVector().invoke(getVelocityVector());
    }

    public final AnimationVector getVelocityVector() {
        return this.internalState.getVelocityVector();
    }

    public final Object snapTo(Object obj, Continuation continuation) {
        Object objMutate$default = MutatorMutex.mutate$default(this.mutatorMutex, null, new C00292(obj, null), continuation, 1, null);
        return objMutate$default == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objMutate$default : Unit.INSTANCE;
    }
}
