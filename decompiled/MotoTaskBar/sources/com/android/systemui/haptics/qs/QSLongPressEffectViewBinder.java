package com.android.systemui.haptics.qs;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.haptics.qs.QSLongPressEffect;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* JADX INFO: compiled from: QSLongPressEffectViewBinder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class QSLongPressEffectViewBinder {
    public static final QSLongPressEffectViewBinder INSTANCE = new QSLongPressEffectViewBinder();

    /* JADX INFO: renamed from: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder$bind$1, reason: invalid class name */
    /* JADX INFO: compiled from: QSLongPressEffectViewBinder.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ QSLongPressEffect $qsLongPressEffect;
        final /* synthetic */ QSTileViewImpl $tile;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: QSLongPressEffectViewBinder.kt */
        final class C00031 extends SuspendLambda implements Function2 {
            final /* synthetic */ QSLongPressEffect $qsLongPressEffect;
            final /* synthetic */ QSTileViewImpl $tile;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX INFO: renamed from: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: QSLongPressEffectViewBinder.kt */
            final class C00041 extends SuspendLambda implements Function2 {
                final /* synthetic */ QSLongPressEffect $qsLongPressEffect;
                final /* synthetic */ QSTileViewImpl $tile;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C00041(QSLongPressEffect qSLongPressEffect, QSTileViewImpl qSTileViewImpl, Continuation continuation) {
                    super(2, continuation);
                    this.$qsLongPressEffect = qSLongPressEffect;
                    this.$tile = qSTileViewImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00041(this.$qsLongPressEffect, this.$tile, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((C00041) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                        Flow actionType = this.$qsLongPressEffect.getActionType();
                        final QSTileViewImpl qSTileViewImpl = this.$tile;
                        final QSLongPressEffect qSLongPressEffect = this.$qsLongPressEffect;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder.bind.1.1.1.1

                            /* JADX INFO: renamed from: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder$bind$1$1$1$1$WhenMappings */
                            /* JADX INFO: compiled from: QSLongPressEffectViewBinder.kt */
                            public abstract /* synthetic */ class WhenMappings {
                                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                                static {
                                    int[] iArr = new int[QSLongPressEffect.ActionType.values().length];
                                    try {
                                        iArr[QSLongPressEffect.ActionType.CLICK.ordinal()] = 1;
                                    } catch (NoSuchFieldError unused) {
                                    }
                                    try {
                                        iArr[QSLongPressEffect.ActionType.LONG_PRESS.ordinal()] = 2;
                                    } catch (NoSuchFieldError unused2) {
                                    }
                                    try {
                                        iArr[QSLongPressEffect.ActionType.RESET_AND_LONG_PRESS.ordinal()] = 3;
                                    } catch (NoSuchFieldError unused3) {
                                    }
                                    try {
                                        iArr[QSLongPressEffect.ActionType.START_ANIMATOR.ordinal()] = 4;
                                    } catch (NoSuchFieldError unused4) {
                                    }
                                    try {
                                        iArr[QSLongPressEffect.ActionType.REVERSE_ANIMATOR.ordinal()] = 5;
                                    } catch (NoSuchFieldError unused5) {
                                    }
                                    try {
                                        iArr[QSLongPressEffect.ActionType.CANCEL_ANIMATOR.ordinal()] = 6;
                                    } catch (NoSuchFieldError unused6) {
                                    }
                                    try {
                                        iArr[QSLongPressEffect.ActionType.INITIALIZE_ANIMATOR.ordinal()] = 7;
                                    } catch (NoSuchFieldError unused7) {
                                    }
                                    $EnumSwitchMapping$0 = iArr;
                                }
                            }

                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(QSLongPressEffect.ActionType actionType2, Continuation continuation) {
                                ValueAnimator valueAnimator;
                                if (actionType2 != null) {
                                    final QSTileViewImpl qSTileViewImpl2 = qSTileViewImpl;
                                    final QSLongPressEffect qSLongPressEffect2 = qSLongPressEffect;
                                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                                    switch (WhenMappings.$EnumSwitchMapping$0[actionType2.ordinal()]) {
                                        case 1:
                                            qSTileViewImpl2.performClick();
                                            qSLongPressEffect2.clearActionType();
                                            break;
                                        case 2:
                                            qSTileViewImpl2.performLongClick();
                                            qSLongPressEffect2.clearActionType();
                                            break;
                                        case 3:
                                            qSTileViewImpl2.resetLongPressEffectProperties();
                                            qSTileViewImpl2.performLongClick();
                                            qSLongPressEffect2.clearActionType();
                                            break;
                                        case 4:
                                            ValueAnimator valueAnimator2 = (ValueAnimator) ref$ObjectRef2.element;
                                            if (valueAnimator2 != null && !valueAnimator2.isRunning() && (valueAnimator = (ValueAnimator) ref$ObjectRef2.element) != null) {
                                                valueAnimator.start();
                                            }
                                            break;
                                        case 5:
                                            ValueAnimator valueAnimator3 = (ValueAnimator) ref$ObjectRef2.element;
                                            if (valueAnimator3 != null) {
                                                valueAnimator3.reverse();
                                            }
                                            break;
                                        case 6:
                                            qSTileViewImpl2.resetLongPressEffectProperties();
                                            ValueAnimator valueAnimator4 = (ValueAnimator) ref$ObjectRef2.element;
                                            if (valueAnimator4 != null) {
                                                valueAnimator4.cancel();
                                            }
                                            break;
                                        case 7:
                                            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                                            valueAnimatorOfFloat.setDuration(qSLongPressEffect2.getEffectDuration());
                                            valueAnimatorOfFloat.setInterpolator(new AccelerateDecelerateInterpolator());
                                            valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder$bind$1$1$1$1$emit$lambda$5$lambda$4$$inlined$doOnStart$1
                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationCancel(Animator animator) {
                                                }

                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationEnd(Animator animator) {
                                                }

                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationRepeat(Animator animator) {
                                                }

                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationStart(Animator animator) {
                                                    qSLongPressEffect2.handleAnimationStart();
                                                }
                                            });
                                            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder$bind$1$1$1$1$1$2$2
                                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                                public final void onAnimationUpdate(ValueAnimator valueAnimator5) {
                                                    valueAnimator5.getClass();
                                                    Object animatedValue = valueAnimatorOfFloat.getAnimatedValue();
                                                    animatedValue.getClass();
                                                    float fFloatValue = ((Float) animatedValue).floatValue();
                                                    if (fFloatValue == 0.0f) {
                                                        qSTileViewImpl2.bringToFront();
                                                    } else {
                                                        qSTileViewImpl2.updateLongPressEffectProperties(fFloatValue);
                                                    }
                                                }
                                            });
                                            valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder$bind$1$1$1$1$emit$lambda$5$lambda$4$$inlined$doOnEnd$1
                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationCancel(Animator animator) {
                                                }

                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationEnd(Animator animator) {
                                                    qSLongPressEffect2.handleAnimationComplete();
                                                }

                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationRepeat(Animator animator) {
                                                }

                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationStart(Animator animator) {
                                                }
                                            });
                                            valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder$bind$1$1$1$1$emit$lambda$5$lambda$4$$inlined$doOnCancel$1
                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationCancel(Animator animator) {
                                                    qSLongPressEffect2.handleAnimationCancel();
                                                }

                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationEnd(Animator animator) {
                                                }

                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationRepeat(Animator animator) {
                                                }

                                                @Override // android.animation.Animator.AnimatorListener
                                                public void onAnimationStart(Animator animator) {
                                                }
                                            });
                                            ref$ObjectRef2.element = valueAnimatorOfFloat;
                                            break;
                                        default:
                                            throw new NoWhenBranchMatchedException();
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (actionType.collect(flowCollector, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00031(QSLongPressEffect qSLongPressEffect, QSTileViewImpl qSTileViewImpl, Continuation continuation) {
                super(2, continuation);
                this.$qsLongPressEffect = qSLongPressEffect;
                this.$tile = qSTileViewImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00031 c00031 = new C00031(this.$qsLongPressEffect, this.$tile, continuation);
                c00031.L$0 = obj;
                return c00031;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00031) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                BuildersKt__Builders_commonKt.launch$default((CoroutineScope) this.L$0, null, null, new C00041(this.$qsLongPressEffect, this.$tile, null), 3, null);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(QSLongPressEffect qSLongPressEffect, QSTileViewImpl qSTileViewImpl, Continuation continuation) {
            super(3, continuation);
            this.$qsLongPressEffect = qSLongPressEffect;
            this.$tile = qSTileViewImpl;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$qsLongPressEffect, this.$tile, continuation);
            anonymousClass1.L$0 = lifecycleOwner;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C00031 c00031 = new C00031(this.$qsLongPressEffect, this.$tile, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c00031, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    private QSLongPressEffectViewBinder() {
    }

    private final void setTouchListener(final QSTileViewImpl qSTileViewImpl, final QSLongPressEffect qSLongPressEffect) {
        qSTileViewImpl.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder.setTouchListener.1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                QSLongPressEffect qSLongPressEffect2;
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    QSTileViewImpl qSTileViewImpl2 = qSTileViewImpl;
                    final QSLongPressEffect qSLongPressEffect3 = qSLongPressEffect;
                    qSTileViewImpl2.postDelayed(new Runnable() { // from class: com.android.systemui.haptics.qs.QSLongPressEffectViewBinder.setTouchListener.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            QSLongPressEffect qSLongPressEffect4 = qSLongPressEffect3;
                            if (qSLongPressEffect4 != null) {
                                qSLongPressEffect4.handleTimeoutComplete();
                            }
                        }
                    }, ViewConfiguration.getTapTimeout());
                    QSLongPressEffect qSLongPressEffect4 = qSLongPressEffect;
                    if (qSLongPressEffect4 != null) {
                        qSLongPressEffect4.handleActionDown();
                    }
                } else if (actionMasked == 1) {
                    QSLongPressEffect qSLongPressEffect5 = qSLongPressEffect;
                    if (qSLongPressEffect5 != null) {
                        qSLongPressEffect5.handleActionUp();
                    }
                } else if (actionMasked == 3 && (qSLongPressEffect2 = qSLongPressEffect) != null) {
                    qSLongPressEffect2.handleActionCancel();
                }
                return true;
            }
        });
    }

    public final DisposableHandle bind(QSTileViewImpl qSTileViewImpl, QSLongPressEffect qSLongPressEffect, String str) {
        qSTileViewImpl.getClass();
        if (qSLongPressEffect == null) {
            return null;
        }
        setTouchListener(qSTileViewImpl, qSLongPressEffect);
        return RepeatWhenAttachedKt.repeatWhenAttached$default(qSTileViewImpl, null, new AnonymousClass1(qSLongPressEffect, qSTileViewImpl, null), 1, null);
    }
}
