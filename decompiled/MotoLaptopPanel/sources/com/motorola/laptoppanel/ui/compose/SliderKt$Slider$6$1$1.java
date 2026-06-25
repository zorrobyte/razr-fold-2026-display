package com.motorola.laptoppanel.ui.compose;

import androidx.compose.animation.core.Animatable;
import androidx.compose.runtime.MutableState;
import com.motorola.laptoppanel.ui.compose.SliderKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
final class SliderKt$Slider$6$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable $iconActiveAlphaAnimatable;
    final /* synthetic */ Animatable $iconInactiveAlphaAnimatable;
    final /* synthetic */ MutableState $showIconActive$delegate;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.compose.SliderKt$Slider$6$1$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: Slider.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Animatable $iconActiveAlphaAnimatable;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Animatable animatable, Continuation continuation) {
            super(2, continuation);
            this.$iconActiveAlphaAnimatable = animatable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$iconActiveAlphaAnimatable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable animatable = this.$iconActiveAlphaAnimatable;
                this.label = 1;
                if (SliderKt.appear(animatable, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.compose.SliderKt$Slider$6$1$1$2, reason: invalid class name */
    /* JADX INFO: compiled from: Slider.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Animatable $iconInactiveAlphaAnimatable;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Animatable animatable, Continuation continuation) {
            super(2, continuation);
            this.$iconInactiveAlphaAnimatable = animatable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$iconInactiveAlphaAnimatable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable animatable = this.$iconInactiveAlphaAnimatable;
                this.label = 1;
                if (SliderKt.disappear(animatable, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.compose.SliderKt$Slider$6$1$1$3, reason: invalid class name */
    /* JADX INFO: compiled from: Slider.kt */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Animatable $iconActiveAlphaAnimatable;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(Animatable animatable, Continuation continuation) {
            super(2, continuation);
            this.$iconActiveAlphaAnimatable = animatable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$iconActiveAlphaAnimatable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable animatable = this.$iconActiveAlphaAnimatable;
                this.label = 1;
                if (SliderKt.disappear(animatable, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.compose.SliderKt$Slider$6$1$1$4, reason: invalid class name */
    /* JADX INFO: compiled from: Slider.kt */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ Animatable $iconInactiveAlphaAnimatable;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(Animatable animatable, Continuation continuation) {
            super(2, continuation);
            this.$iconInactiveAlphaAnimatable = animatable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.$iconInactiveAlphaAnimatable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Animatable animatable = this.$iconInactiveAlphaAnimatable;
                this.label = 1;
                if (SliderKt.appear(animatable, this) == coroutine_suspended) {
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
    SliderKt$Slider$6$1$1(MutableState mutableState, Animatable animatable, Animatable animatable2, Continuation continuation) {
        super(2, continuation);
        this.$showIconActive$delegate = mutableState;
        this.$iconActiveAlphaAnimatable = animatable;
        this.$iconInactiveAlphaAnimatable = animatable2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SliderKt$Slider$6$1$1 sliderKt$Slider$6$1$1 = new SliderKt$Slider$6$1$1(this.$showIconActive$delegate, this.$iconActiveAlphaAnimatable, this.$iconInactiveAlphaAnimatable, continuation);
        sliderKt$Slider$6$1$1.L$0 = obj;
        return sliderKt$Slider$6$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SliderKt$Slider$6$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        if (SliderKt.AnonymousClass6.invoke$lambda$1(this.$showIconActive$delegate)) {
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$iconActiveAlphaAnimatable, null), 3, null);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$iconInactiveAlphaAnimatable, null), 3, null);
        } else {
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$iconActiveAlphaAnimatable, null), 3, null);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$iconInactiveAlphaAnimatable, null), 3, null);
        }
        return Unit.INSTANCE;
    }
}
