package androidx.compose.animation.core;

import androidx.compose.animation.core.SeekableTransitionState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.DisposableEffectScope;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableLongState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotLongStateKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;

/* JADX INFO: compiled from: Transition.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Transition {
    private final SnapshotStateList _animations;
    private final MutableLongState _playTimeNanos$delegate;
    private final SnapshotStateList _transitions;
    private final MutableState isSeeking$delegate;
    private final String label;
    private long lastSeekedTimeNanos;
    private final Transition parentTransition;
    private final MutableState segment$delegate;
    private final MutableLongState startTimeNanos$delegate;
    private final MutableState targetState$delegate;
    private final State totalDurationNanos$delegate;
    private final TransitionState transitionState;
    private final MutableState updateChildrenNeeded$delegate;

    /* JADX INFO: compiled from: Transition.kt */
    public final class DeferredAnimation {
        private final MutableState data$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(null, null, 2, null);
        private final String label;
        private final TwoWayConverter typeConverter;

        /* JADX INFO: compiled from: Transition.kt */
        public final class DeferredAnimationData implements State {
            private final TransitionAnimationState animation;
            private Function1 targetValueByState;
            private Function1 transitionSpec;

            public DeferredAnimationData(TransitionAnimationState transitionAnimationState, Function1 function1, Function1 function12) {
                this.animation = transitionAnimationState;
                this.transitionSpec = function1;
                this.targetValueByState = function12;
            }

            public final TransitionAnimationState getAnimation() {
                return this.animation;
            }

            public final Function1 getTargetValueByState() {
                return this.targetValueByState;
            }

            public final Function1 getTransitionSpec() {
                return this.transitionSpec;
            }

            @Override // androidx.compose.runtime.State
            public Object getValue() {
                updateAnimationStates(Transition.this.getSegment());
                return this.animation.getValue();
            }

            public final void setTargetValueByState(Function1 function1) {
                this.targetValueByState = function1;
            }

            public final void setTransitionSpec(Function1 function1) {
                this.transitionSpec = function1;
            }

            public final void updateAnimationStates(Segment segment) {
                Object objInvoke = this.targetValueByState.invoke(segment.getTargetState());
                if (!Transition.this.isSeeking()) {
                    this.animation.updateTargetValue$animation_core_release(objInvoke, (FiniteAnimationSpec) this.transitionSpec.invoke(segment));
                } else {
                    this.animation.updateInitialAndTargetValue$animation_core_release(this.targetValueByState.invoke(segment.getInitialState()), objInvoke, (FiniteAnimationSpec) this.transitionSpec.invoke(segment));
                }
            }
        }

        public DeferredAnimation(TwoWayConverter twoWayConverter, String str) {
            this.typeConverter = twoWayConverter;
            this.label = str;
        }

        public final State animate(Function1 function1, Function1 function12) {
            DeferredAnimationData data$animation_core_release = getData$animation_core_release();
            if (data$animation_core_release == null) {
                Transition transition = Transition.this;
                data$animation_core_release = new DeferredAnimationData(transition.new TransitionAnimationState(function12.invoke(transition.getCurrentState()), AnimationStateKt.createZeroVectorFrom(this.typeConverter, function12.invoke(Transition.this.getCurrentState())), this.typeConverter, this.label), function1, function12);
                Transition transition2 = Transition.this;
                setData$animation_core_release(data$animation_core_release);
                transition2.addAnimation$animation_core_release(data$animation_core_release.getAnimation());
            }
            Transition transition3 = Transition.this;
            data$animation_core_release.setTargetValueByState(function12);
            data$animation_core_release.setTransitionSpec(function1);
            data$animation_core_release.updateAnimationStates(transition3.getSegment());
            return data$animation_core_release;
        }

        public final DeferredAnimationData getData$animation_core_release() {
            return (DeferredAnimationData) this.data$delegate.getValue();
        }

        public final void setData$animation_core_release(DeferredAnimationData deferredAnimationData) {
            this.data$delegate.setValue(deferredAnimationData);
        }

        public final void setupSeeking$animation_core_release() {
            DeferredAnimationData data$animation_core_release = getData$animation_core_release();
            if (data$animation_core_release != null) {
                Transition transition = Transition.this;
                data$animation_core_release.getAnimation().updateInitialAndTargetValue$animation_core_release(data$animation_core_release.getTargetValueByState().invoke(transition.getSegment().getInitialState()), data$animation_core_release.getTargetValueByState().invoke(transition.getSegment().getTargetState()), (FiniteAnimationSpec) data$animation_core_release.getTransitionSpec().invoke(transition.getSegment()));
            }
        }
    }

    /* JADX INFO: compiled from: Transition.kt */
    public interface Segment {
        Object getInitialState();

        Object getTargetState();

        default boolean isTransitioningTo(Object obj, Object obj2) {
            return Intrinsics.areEqual(obj, getInitialState()) && Intrinsics.areEqual(obj2, getTargetState());
        }
    }

    /* JADX INFO: compiled from: Transition.kt */
    final class SegmentImpl implements Segment {
        private final Object initialState;
        private final Object targetState;

        public SegmentImpl(Object obj, Object obj2) {
            this.initialState = obj;
            this.targetState = obj2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Segment)) {
                return false;
            }
            Segment segment = (Segment) obj;
            return Intrinsics.areEqual(getInitialState(), segment.getInitialState()) && Intrinsics.areEqual(getTargetState(), segment.getTargetState());
        }

        @Override // androidx.compose.animation.core.Transition.Segment
        public Object getInitialState() {
            return this.initialState;
        }

        @Override // androidx.compose.animation.core.Transition.Segment
        public Object getTargetState() {
            return this.targetState;
        }

        public int hashCode() {
            Object initialState = getInitialState();
            int iHashCode = (initialState != null ? initialState.hashCode() : 0) * 31;
            Object targetState = getTargetState();
            return iHashCode + (targetState != null ? targetState.hashCode() : 0);
        }
    }

    /* JADX INFO: compiled from: Transition.kt */
    public final class TransitionAnimationState implements State {
        private final MutableState animation$delegate;
        private final MutableState animationSpec$delegate;
        private final SpringSpec defaultSpring;
        private final MutableLongState durationNanos$delegate;
        private TargetBasedAnimation initialValueAnimation;
        private final FiniteAnimationSpec interruptionSpec;
        private final MutableState isFinished$delegate;
        private boolean isSeeking;
        private final String label;
        private final MutableFloatState resetSnapValue$delegate;
        private final MutableState targetValue$delegate;
        private final TwoWayConverter typeConverter;
        private boolean useOnlyInitialValue;
        private final MutableState value$delegate;
        private AnimationVector velocityVector;

        public TransitionAnimationState(Object obj, AnimationVector animationVector, TwoWayConverter twoWayConverter, String str) {
            Object objInvoke;
            this.typeConverter = twoWayConverter;
            this.label = str;
            this.targetValue$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(obj, null, 2, null);
            SpringSpec springSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7, null);
            this.defaultSpring = springSpecSpring$default;
            this.animationSpec$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(springSpecSpring$default, null, 2, null);
            this.animation$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(new TargetBasedAnimation(getAnimationSpec(), twoWayConverter, obj, getTargetValue(), animationVector), null, 2, null);
            this.isFinished$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(Boolean.TRUE, null, 2, null);
            this.resetSnapValue$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(-1.0f);
            this.value$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(obj, null, 2, null);
            this.velocityVector = animationVector;
            this.durationNanos$delegate = SnapshotLongStateKt.mutableLongStateOf(getAnimation().getDurationNanos());
            Float f = (Float) VisibilityThresholdsKt.getVisibilityThresholdMap().get(twoWayConverter);
            if (f != null) {
                float fFloatValue = f.floatValue();
                AnimationVector animationVector2 = (AnimationVector) twoWayConverter.getConvertToVector().invoke(obj);
                int size$animation_core_release = animationVector2.getSize$animation_core_release();
                for (int i = 0; i < size$animation_core_release; i++) {
                    animationVector2.set$animation_core_release(i, fFloatValue);
                }
                objInvoke = this.typeConverter.getConvertFromVector().invoke(animationVector2);
            } else {
                objInvoke = null;
            }
            this.interruptionSpec = AnimationSpecKt.spring$default(0.0f, 0.0f, objInvoke, 3, null);
        }

        private final Object getTargetValue() {
            return this.targetValue$delegate.getValue();
        }

        private final void setAnimation(TargetBasedAnimation targetBasedAnimation) {
            this.animation$delegate.setValue(targetBasedAnimation);
        }

        private final void setAnimationSpec(FiniteAnimationSpec finiteAnimationSpec) {
            this.animationSpec$delegate.setValue(finiteAnimationSpec);
        }

        private final void setTargetValue(Object obj) {
            this.targetValue$delegate.setValue(obj);
        }

        private final void updateAnimation(Object obj, boolean z) {
            TargetBasedAnimation targetBasedAnimation = this.initialValueAnimation;
            if (Intrinsics.areEqual(targetBasedAnimation != null ? targetBasedAnimation.getTargetValue() : null, getTargetValue())) {
                setAnimation(new TargetBasedAnimation(this.interruptionSpec, this.typeConverter, obj, obj, AnimationVectorsKt.newInstance(this.velocityVector)));
                this.useOnlyInitialValue = true;
                setDurationNanos$animation_core_release(getAnimation().getDurationNanos());
                return;
            }
            AnimationSpec animationSpec = (!z || this.isSeeking || (getAnimationSpec() instanceof SpringSpec)) ? getAnimationSpec() : this.interruptionSpec;
            if (Transition.this.getPlayTimeNanos() > 0) {
                animationSpec = AnimationSpecKt.delayed(animationSpec, Transition.this.getPlayTimeNanos());
            }
            setAnimation(new TargetBasedAnimation(animationSpec, this.typeConverter, obj, getTargetValue(), this.velocityVector));
            setDurationNanos$animation_core_release(getAnimation().getDurationNanos());
            this.useOnlyInitialValue = false;
            Transition.this.onChildAnimationUpdated();
        }

        static /* synthetic */ void updateAnimation$default(TransitionAnimationState transitionAnimationState, Object obj, boolean z, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = transitionAnimationState.getValue();
            }
            if ((i & 2) != 0) {
                z = false;
            }
            transitionAnimationState.updateAnimation(obj, z);
        }

        public final TargetBasedAnimation getAnimation() {
            return (TargetBasedAnimation) this.animation$delegate.getValue();
        }

        public final FiniteAnimationSpec getAnimationSpec() {
            return (FiniteAnimationSpec) this.animationSpec$delegate.getValue();
        }

        public final long getDurationNanos$animation_core_release() {
            return this.durationNanos$delegate.getLongValue();
        }

        public final SeekableTransitionState.SeekingAnimationState getInitialValueState$animation_core_release() {
            return null;
        }

        public final float getResetSnapValue$animation_core_release() {
            return this.resetSnapValue$delegate.getFloatValue();
        }

        @Override // androidx.compose.runtime.State
        public Object getValue() {
            return this.value$delegate.getValue();
        }

        public final boolean isFinished$animation_core_release() {
            return ((Boolean) this.isFinished$delegate.getValue()).booleanValue();
        }

        public final void onPlayTimeChanged$animation_core_release(long j, boolean z) {
            if (z) {
                j = getAnimation().getDurationNanos();
            }
            setValue$animation_core_release(getAnimation().getValueFromNanos(j));
            this.velocityVector = getAnimation().getVelocityVectorFromNanos(j);
            if (getAnimation().isFinishedFromNanos(j)) {
                setFinished$animation_core_release(true);
            }
        }

        public final void resetAnimation$animation_core_release() {
            setResetSnapValue$animation_core_release(-2.0f);
        }

        public final void seekTo$animation_core_release(long j) {
            if (getResetSnapValue$animation_core_release() == -1.0f) {
                this.isSeeking = true;
                if (Intrinsics.areEqual(getAnimation().getTargetValue(), getAnimation().getInitialValue())) {
                    setValue$animation_core_release(getAnimation().getTargetValue());
                } else {
                    setValue$animation_core_release(getAnimation().getValueFromNanos(j));
                    this.velocityVector = getAnimation().getVelocityVectorFromNanos(j);
                }
            }
        }

        public final void setDurationNanos$animation_core_release(long j) {
            this.durationNanos$delegate.setLongValue(j);
        }

        public final void setFinished$animation_core_release(boolean z) {
            this.isFinished$delegate.setValue(Boolean.valueOf(z));
        }

        public final void setResetSnapValue$animation_core_release(float f) {
            this.resetSnapValue$delegate.setFloatValue(f);
        }

        public void setValue$animation_core_release(Object obj) {
            this.value$delegate.setValue(obj);
        }

        public String toString() {
            return "current value: " + getValue() + ", target: " + getTargetValue() + ", spec: " + getAnimationSpec();
        }

        public final void updateInitialAndTargetValue$animation_core_release(Object obj, Object obj2, FiniteAnimationSpec finiteAnimationSpec) {
            setTargetValue(obj2);
            setAnimationSpec(finiteAnimationSpec);
            if (Intrinsics.areEqual(getAnimation().getInitialValue(), obj) && Intrinsics.areEqual(getAnimation().getTargetValue(), obj2)) {
                return;
            }
            updateAnimation$default(this, obj, false, 2, null);
        }

        public final void updateTargetValue$animation_core_release(Object obj, FiniteAnimationSpec finiteAnimationSpec) {
            if (this.useOnlyInitialValue) {
                TargetBasedAnimation targetBasedAnimation = this.initialValueAnimation;
                if (Intrinsics.areEqual(obj, targetBasedAnimation != null ? targetBasedAnimation.getTargetValue() : null)) {
                    return;
                }
            }
            if (Intrinsics.areEqual(getTargetValue(), obj) && getResetSnapValue$animation_core_release() == -1.0f) {
                return;
            }
            setTargetValue(obj);
            setAnimationSpec(finiteAnimationSpec);
            updateAnimation(getResetSnapValue$animation_core_release() == -3.0f ? obj : getValue(), !isFinished$animation_core_release());
            setFinished$animation_core_release(getResetSnapValue$animation_core_release() == -3.0f);
            if (getResetSnapValue$animation_core_release() >= 0.0f) {
                setValue$animation_core_release(getAnimation().getValueFromNanos((long) (getAnimation().getDurationNanos() * getResetSnapValue$animation_core_release())));
            } else if (getResetSnapValue$animation_core_release() == -3.0f) {
                setValue$animation_core_release(obj);
            }
            this.useOnlyInitialValue = false;
            setResetSnapValue$animation_core_release(-1.0f);
        }
    }

    public Transition(TransitionState transitionState, Transition transition, String str) {
        this.transitionState = transitionState;
        this.parentTransition = transition;
        this.label = str;
        this.targetState$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(getCurrentState(), null, 2, null);
        this.segment$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(new SegmentImpl(getCurrentState(), getCurrentState()), null, 2, null);
        this._playTimeNanos$delegate = SnapshotLongStateKt.mutableLongStateOf(0L);
        this.startTimeNanos$delegate = SnapshotLongStateKt.mutableLongStateOf(Long.MIN_VALUE);
        Boolean bool = Boolean.FALSE;
        this.updateChildrenNeeded$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(bool, null, 2, null);
        this._animations = SnapshotStateKt.mutableStateListOf();
        this._transitions = SnapshotStateKt.mutableStateListOf();
        this.isSeeking$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(bool, null, 2, null);
        this.totalDurationNanos$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.animation.core.Transition$totalDurationNanos$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Long invoke() {
                return Long.valueOf(this.this$0.calculateTotalDurationNanos());
            }
        });
        transitionState.transitionConfigured$animation_core_release(this);
    }

    public Transition(Object obj, String str) {
        this(new MutableTransitionState(obj), null, str);
    }

    private static final boolean animateTo$lambda$13(State state) {
        return ((Boolean) state.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long calculateTotalDurationNanos() {
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        long jMax = 0;
        for (int i = 0; i < size; i++) {
            jMax = Math.max(jMax, ((TransitionAnimationState) snapshotStateList.get(i)).getDurationNanos$animation_core_release());
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            jMax = Math.max(jMax, ((Transition) snapshotStateList2.get(i2)).calculateTotalDurationNanos());
        }
        return jMax;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getUpdateChildrenNeeded() {
        return ((Boolean) this.updateChildrenNeeded$delegate.getValue()).booleanValue();
    }

    private final long get_playTimeNanos() {
        return this._playTimeNanos$delegate.getLongValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onChildAnimationUpdated() {
        setUpdateChildrenNeeded(true);
        if (isSeeking()) {
            SnapshotStateList snapshotStateList = this._animations;
            int size = snapshotStateList.size();
            long jMax = 0;
            for (int i = 0; i < size; i++) {
                TransitionAnimationState transitionAnimationState = (TransitionAnimationState) snapshotStateList.get(i);
                jMax = Math.max(jMax, transitionAnimationState.getDurationNanos$animation_core_release());
                transitionAnimationState.seekTo$animation_core_release(this.lastSeekedTimeNanos);
            }
            setUpdateChildrenNeeded(false);
        }
    }

    private final void resetAnimations() {
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            ((TransitionAnimationState) snapshotStateList.get(i)).resetAnimation$animation_core_release();
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((Transition) snapshotStateList2.get(i2)).resetAnimations();
        }
    }

    private final void setSegment(Segment segment) {
        this.segment$delegate.setValue(segment);
    }

    private final void setUpdateChildrenNeeded(boolean z) {
        this.updateChildrenNeeded$delegate.setValue(Boolean.valueOf(z));
    }

    private final void set_playTimeNanos(long j) {
        this._playTimeNanos$delegate.setLongValue(j);
    }

    public final boolean addAnimation$animation_core_release(TransitionAnimationState transitionAnimationState) {
        return this._animations.add(transitionAnimationState);
    }

    public final boolean addTransition$animation_core_release(Transition transition) {
        return this._transitions.add(transition);
    }

    public final void animateTo$animation_core_release(final Object obj, Composer composer, final int i) {
        int i2;
        Composer composerStartRestartGroup = composer.startRestartGroup(-1493585151);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerStartRestartGroup.changed(obj) : composerStartRestartGroup.changedInstance(obj) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerStartRestartGroup.changed(this) ? 32 : 16;
        }
        if (composerStartRestartGroup.shouldExecute((i2 & 19) != 18, i2 & 1)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1493585151, i2, -1, "androidx.compose.animation.core.Transition.animateTo (Transition.kt:1183)");
            }
            if (isSeeking()) {
                composerStartRestartGroup.startReplaceGroup(1824822651);
                composerStartRestartGroup.endReplaceGroup();
            } else {
                composerStartRestartGroup.startReplaceGroup(1823162043);
                updateTarget$animation_core_release(obj);
                int i3 = i2 & 112;
                boolean z = i3 == 32;
                Object objRememberedValue = composerStartRestartGroup.rememberedValue();
                if (z || objRememberedValue == Composer.Companion.getEmpty()) {
                    objRememberedValue = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.animation.core.Transition$animateTo$runFrameLoop$2$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Boolean invoke() {
                            return Boolean.valueOf(!Intrinsics.areEqual(this.this$0.getTargetState(), this.this$0.getCurrentState()) || this.this$0.isRunning() || this.this$0.getUpdateChildrenNeeded());
                        }
                    });
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue);
                }
                if (animateTo$lambda$13((State) objRememberedValue)) {
                    composerStartRestartGroup.startReplaceGroup(1823570158);
                    Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
                    Composer.Companion companion = Composer.Companion;
                    if (objRememberedValue2 == companion.getEmpty()) {
                        objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerStartRestartGroup);
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
                    }
                    final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue2;
                    boolean zChangedInstance = composerStartRestartGroup.changedInstance(coroutineScope) | (i3 == 32);
                    Object objRememberedValue3 = composerStartRestartGroup.rememberedValue();
                    if (zChangedInstance || objRememberedValue3 == companion.getEmpty()) {
                        objRememberedValue3 = new Function1() { // from class: androidx.compose.animation.core.Transition$animateTo$1$1

                            /* JADX INFO: renamed from: androidx.compose.animation.core.Transition$animateTo$1$1$1, reason: invalid class name */
                            /* JADX INFO: compiled from: Transition.kt */
                            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                float F$0;
                                private /* synthetic */ Object L$0;
                                int label;
                                final /* synthetic */ Transition this$0;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                AnonymousClass1(Transition transition, Continuation continuation) {
                                    super(2, continuation);
                                    this.this$0 = transition;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation create(Object obj, Continuation continuation) {
                                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                                    anonymousClass1.L$0 = obj;
                                    return anonymousClass1;
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                                    return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    final float durationScale;
                                    CoroutineScope coroutineScope;
                                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                    int i = this.label;
                                    if (i == 0) {
                                        ResultKt.throwOnFailure(obj);
                                        CoroutineScope coroutineScope2 = (CoroutineScope) this.L$0;
                                        durationScale = SuspendAnimationKt.getDurationScale(coroutineScope2.getCoroutineContext());
                                        coroutineScope = coroutineScope2;
                                    } else {
                                        if (i != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        durationScale = this.F$0;
                                        coroutineScope = (CoroutineScope) this.L$0;
                                        ResultKt.throwOnFailure(obj);
                                    }
                                    while (CoroutineScopeKt.isActive(coroutineScope)) {
                                        final Transition transition = this.this$0;
                                        Function1 function1 = new Function1() { // from class: androidx.compose.animation.core.Transition.animateTo.1.1.1.1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                                                invoke(((Number) obj2).longValue());
                                                return Unit.INSTANCE;
                                            }

                                            public final void invoke(long j) {
                                                if (transition.isSeeking()) {
                                                    return;
                                                }
                                                transition.onFrame$animation_core_release(j, durationScale);
                                            }
                                        };
                                        this.L$0 = coroutineScope;
                                        this.F$0 = durationScale;
                                        this.label = 1;
                                        if (MonotonicFrameClockKt.withFrameNanos(function1, this) == coroutine_suspended) {
                                            return coroutine_suspended;
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final DisposableEffectResult invoke(DisposableEffectScope disposableEffectScope) {
                                BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new AnonymousClass1(this, null), 1, null);
                                return new DisposableEffectResult() { // from class: androidx.compose.animation.core.Transition$animateTo$1$1$invoke$$inlined$onDispose$1
                                    @Override // androidx.compose.runtime.DisposableEffectResult
                                    public void dispose() {
                                    }
                                };
                            }
                        };
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
                    }
                    EffectsKt.DisposableEffect(coroutineScope, this, (Function1) objRememberedValue3, composerStartRestartGroup, i3);
                    composerStartRestartGroup.endReplaceGroup();
                } else {
                    composerStartRestartGroup.startReplaceGroup(1824812731);
                    composerStartRestartGroup.endReplaceGroup();
                }
                composerStartRestartGroup.endReplaceGroup();
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerStartRestartGroup.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composerStartRestartGroup.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: androidx.compose.animation.core.Transition$animateTo$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Object invoke(Object obj2, Object obj3) {
                    invoke((Composer) obj2, ((Number) obj3).intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer2, int i4) {
                    this.$tmp2_rcvr.animateTo$animation_core_release(obj, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                }
            });
        }
    }

    public final List getAnimations() {
        return this._animations;
    }

    public final Object getCurrentState() {
        return this.transitionState.getCurrentState();
    }

    public final boolean getHasInitialValueAnimations() {
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            ((TransitionAnimationState) snapshotStateList.get(i)).getInitialValueState$animation_core_release();
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (((Transition) snapshotStateList2.get(i2)).getHasInitialValueAnimations()) {
                return true;
            }
        }
        return false;
    }

    public final String getLabel() {
        return this.label;
    }

    public final long getLastSeekedTimeNanos$animation_core_release() {
        return this.lastSeekedTimeNanos;
    }

    public final long getPlayTimeNanos() {
        Transition transition = this.parentTransition;
        return transition != null ? transition.getPlayTimeNanos() : get_playTimeNanos();
    }

    public final Segment getSegment() {
        return (Segment) this.segment$delegate.getValue();
    }

    public final long getStartTimeNanos$animation_core_release() {
        return this.startTimeNanos$delegate.getLongValue();
    }

    public final Object getTargetState() {
        return this.targetState$delegate.getValue();
    }

    public final boolean isRunning() {
        return getStartTimeNanos$animation_core_release() != Long.MIN_VALUE;
    }

    public final boolean isSeeking() {
        return ((Boolean) this.isSeeking$delegate.getValue()).booleanValue();
    }

    public final void onDisposed$animation_core_release() {
        onTransitionEnd$animation_core_release();
        this.transitionState.transitionRemoved$animation_core_release();
    }

    public final void onFrame$animation_core_release(long j, float f) {
        if (getStartTimeNanos$animation_core_release() == Long.MIN_VALUE) {
            onTransitionStart$animation_core_release(j);
        }
        long startTimeNanos$animation_core_release = j - getStartTimeNanos$animation_core_release();
        if (f != 0.0f) {
            startTimeNanos$animation_core_release = MathKt.roundToLong(startTimeNanos$animation_core_release / ((double) f));
        }
        setPlayTimeNanos(startTimeNanos$animation_core_release);
        onFrame$animation_core_release(startTimeNanos$animation_core_release, f == 0.0f);
    }

    public final void onFrame$animation_core_release(long j, boolean z) {
        boolean z2 = true;
        if (getStartTimeNanos$animation_core_release() == Long.MIN_VALUE) {
            onTransitionStart$animation_core_release(j);
        } else if (!this.transitionState.isRunning$animation_core_release()) {
            this.transitionState.setRunning$animation_core_release(true);
        }
        setUpdateChildrenNeeded(false);
        SnapshotStateList snapshotStateList = this._animations;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            TransitionAnimationState transitionAnimationState = (TransitionAnimationState) snapshotStateList.get(i);
            if (!transitionAnimationState.isFinished$animation_core_release()) {
                transitionAnimationState.onPlayTimeChanged$animation_core_release(j, z);
            }
            if (!transitionAnimationState.isFinished$animation_core_release()) {
                z2 = false;
            }
        }
        SnapshotStateList snapshotStateList2 = this._transitions;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            Transition transition = (Transition) snapshotStateList2.get(i2);
            if (!Intrinsics.areEqual(transition.getTargetState(), transition.getCurrentState())) {
                transition.onFrame$animation_core_release(j, z);
            }
            if (!Intrinsics.areEqual(transition.getTargetState(), transition.getCurrentState())) {
                z2 = false;
            }
        }
        if (z2) {
            onTransitionEnd$animation_core_release();
        }
    }

    public final void onTransitionEnd$animation_core_release() {
        setStartTimeNanos$animation_core_release(Long.MIN_VALUE);
        TransitionState transitionState = this.transitionState;
        if (transitionState instanceof MutableTransitionState) {
            transitionState.setCurrentState$animation_core_release(getTargetState());
        }
        setPlayTimeNanos(0L);
        this.transitionState.setRunning$animation_core_release(false);
        SnapshotStateList snapshotStateList = this._transitions;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            ((Transition) snapshotStateList.get(i)).onTransitionEnd$animation_core_release();
        }
    }

    public final void onTransitionStart$animation_core_release(long j) {
        setStartTimeNanos$animation_core_release(j);
        this.transitionState.setRunning$animation_core_release(true);
    }

    public final void removeAnimation$animation_core_release(DeferredAnimation deferredAnimation) {
        TransitionAnimationState animation;
        DeferredAnimation.DeferredAnimationData data$animation_core_release = deferredAnimation.getData$animation_core_release();
        if (data$animation_core_release == null || (animation = data$animation_core_release.getAnimation()) == null) {
            return;
        }
        removeAnimation$animation_core_release(animation);
    }

    public final void removeAnimation$animation_core_release(TransitionAnimationState transitionAnimationState) {
        this._animations.remove(transitionAnimationState);
    }

    public final boolean removeTransition$animation_core_release(Transition transition) {
        return this._transitions.remove(transition);
    }

    public final void seek(Object obj, Object obj2, long j) {
        setStartTimeNanos$animation_core_release(Long.MIN_VALUE);
        this.transitionState.setRunning$animation_core_release(false);
        if (!isSeeking() || !Intrinsics.areEqual(getCurrentState(), obj) || !Intrinsics.areEqual(getTargetState(), obj2)) {
            if (!Intrinsics.areEqual(getCurrentState(), obj)) {
                TransitionState transitionState = this.transitionState;
                if (transitionState instanceof MutableTransitionState) {
                    transitionState.setCurrentState$animation_core_release(obj);
                }
            }
            setTargetState$animation_core_release(obj2);
            setSeeking$animation_core_release(true);
            setSegment(new SegmentImpl(obj, obj2));
        }
        SnapshotStateList snapshotStateList = this._transitions;
        int size = snapshotStateList.size();
        for (int i = 0; i < size; i++) {
            Transition transition = (Transition) snapshotStateList.get(i);
            transition.getClass();
            if (transition.isSeeking()) {
                transition.seek(transition.getCurrentState(), transition.getTargetState(), j);
            }
        }
        SnapshotStateList snapshotStateList2 = this._animations;
        int size2 = snapshotStateList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((TransitionAnimationState) snapshotStateList2.get(i2)).seekTo$animation_core_release(j);
        }
        this.lastSeekedTimeNanos = j;
    }

    public final void setPlayTimeNanos(long j) {
        if (this.parentTransition == null) {
            set_playTimeNanos(j);
        }
    }

    public final void setSeeking$animation_core_release(boolean z) {
        this.isSeeking$delegate.setValue(Boolean.valueOf(z));
    }

    public final void setStartTimeNanos$animation_core_release(long j) {
        this.startTimeNanos$delegate.setLongValue(j);
    }

    public final void setTargetState$animation_core_release(Object obj) {
        this.targetState$delegate.setValue(obj);
    }

    public String toString() {
        List animations = getAnimations();
        int size = animations.size();
        String str = "Transition animation values: ";
        for (int i = 0; i < size; i++) {
            str = str + ((TransitionAnimationState) animations.get(i)) + ", ";
        }
        return str;
    }

    public final void updateTarget$animation_core_release(Object obj) {
        if (Intrinsics.areEqual(getTargetState(), obj)) {
            return;
        }
        setSegment(new SegmentImpl(getTargetState(), obj));
        if (!Intrinsics.areEqual(getCurrentState(), getTargetState())) {
            this.transitionState.setCurrentState$animation_core_release(getTargetState());
        }
        setTargetState$animation_core_release(obj);
        if (!isRunning()) {
            setUpdateChildrenNeeded(true);
        }
        resetAnimations();
    }
}
