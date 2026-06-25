package com.android.systemui.util.kotlin;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* JADX INFO: compiled from: Flow.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FlowKt {

    /* JADX INFO: renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1, reason: invalid class name */
    /* JADX INFO: compiled from: Flow.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Flow $this_pairwiseBy;
        final /* synthetic */ Function3 $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: Flow.kt */
        final class C00291 implements FlowCollector {
            final /* synthetic */ FlowCollector $$this$flow;
            final /* synthetic */ Object $noVal;
            final /* synthetic */ Ref$ObjectRef $previousValue;
            final /* synthetic */ Function3 $transform;

            C00291(Ref$ObjectRef ref$ObjectRef, Object obj, FlowCollector flowCollector, Function3 function3) {
                this.$previousValue = ref$ObjectRef;
                this.$noVal = obj;
                this.$$this$flow = flowCollector;
                this.$transform = function3;
            }

            /* JADX WARN: Removed duplicated region for block: B:25:0x0085  */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                /*
                    r7 = this;
                    boolean r0 = r9 instanceof com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1$emit$1
                    if (r0 == 0) goto L13
                    r0 = r9
                    com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1$emit$1 r0 = (com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L13
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L18
                L13:
                    com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1$emit$1 r0 = new com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1$emit$1
                    r0.<init>(r7, r9)
                L18:
                    java.lang.Object r9 = r0.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r0.label
                    r3 = 2
                    r4 = 1
                    if (r2 == 0) goto L4b
                    if (r2 == r4) goto L3d
                    if (r2 != r3) goto L35
                    java.lang.Object r7 = r0.L$1
                    java.lang.Object r8 = r0.L$0
                    com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1 r8 = (com.android.systemui.util.kotlin.FlowKt.AnonymousClass1.C00291) r8
                    kotlin.ResultKt.throwOnFailure(r9)
                    r6 = r8
                    r8 = r7
                    r7 = r6
                    goto L86
                L35:
                    java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                    java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                    r7.<init>(r8)
                    throw r7
                L3d:
                    java.lang.Object r7 = r0.L$2
                    kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                    java.lang.Object r8 = r0.L$1
                    java.lang.Object r2 = r0.L$0
                    com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1 r2 = (com.android.systemui.util.kotlin.FlowKt.AnonymousClass1.C00291) r2
                    kotlin.ResultKt.throwOnFailure(r9)
                    goto L75
                L4b:
                    kotlin.ResultKt.throwOnFailure(r9)
                    kotlin.jvm.internal.Ref$ObjectRef r9 = r7.$previousValue
                    java.lang.Object r9 = r9.element
                    java.lang.Object r2 = r7.$noVal
                    boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r2)
                    if (r9 != 0) goto L86
                    kotlinx.coroutines.flow.FlowCollector r9 = r7.$$this$flow
                    kotlin.jvm.functions.Function3 r2 = r7.$transform
                    kotlin.jvm.internal.Ref$ObjectRef r5 = r7.$previousValue
                    java.lang.Object r5 = r5.element
                    r0.L$0 = r7
                    r0.L$1 = r8
                    r0.L$2 = r9
                    r0.label = r4
                    java.lang.Object r2 = r2.invoke(r5, r8, r0)
                    if (r2 != r1) goto L71
                    goto L84
                L71:
                    r6 = r2
                    r2 = r7
                    r7 = r9
                    r9 = r6
                L75:
                    r0.L$0 = r2
                    r0.L$1 = r8
                    r4 = 0
                    r0.L$2 = r4
                    r0.label = r3
                    java.lang.Object r7 = r7.emit(r9, r0)
                    if (r7 != r1) goto L85
                L84:
                    return r1
                L85:
                    r7 = r2
                L86:
                    kotlin.jvm.internal.Ref$ObjectRef r7 = r7.$previousValue
                    r7.element = r8
                    kotlin.Unit r7 = kotlin.Unit.INSTANCE
                    return r7
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt.AnonymousClass1.C00291.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Flow flow, Function3 function3, Continuation continuation) {
            super(2, continuation);
            this.$this_pairwiseBy = flow;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_pairwiseBy, this.$transform, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Object obj2 = new Object();
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                ref$ObjectRef.element = obj2;
                Flow flow = this.$this_pairwiseBy;
                C00291 c00291 = new C00291(ref$ObjectRef, obj2, flowCollector, this.$transform);
                this.label = 1;
                if (flow.collect(c00291, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$2, reason: invalid class name */
    /* JADX INFO: compiled from: Flow.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function1 {
        final /* synthetic */ Object $initialValue;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Object obj, Continuation continuation) {
            super(1, continuation);
            this.$initialValue = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new AnonymousClass2(this.$initialValue, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation continuation) {
            return ((AnonymousClass2) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return this.$initialValue;
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3, reason: invalid class name */
    /* JADX INFO: compiled from: Flow.kt */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $getInitialValue;
        final /* synthetic */ Flow $this_pairwiseBy;
        final /* synthetic */ Function3 $transform;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1, reason: invalid class name */
        /* JADX INFO: compiled from: Flow.kt */
        final class AnonymousClass1 implements FlowCollector {
            final /* synthetic */ FlowCollector $$this$flow;
            final /* synthetic */ Ref$ObjectRef $previousValue;
            final /* synthetic */ Function3 $transform;

            AnonymousClass1(FlowCollector flowCollector, Function3 function3, Ref$ObjectRef ref$ObjectRef) {
                this.$$this$flow = flowCollector;
                this.$transform = function3;
                this.$previousValue = ref$ObjectRef;
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                /*
                    r7 = this;
                    boolean r0 = r9 instanceof com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1$emit$1
                    if (r0 == 0) goto L13
                    r0 = r9
                    com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1$emit$1 r0 = (com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L13
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L18
                L13:
                    com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1$emit$1 r0 = new com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1$emit$1
                    r0.<init>(r7, r9)
                L18:
                    java.lang.Object r9 = r0.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r0.label
                    r3 = 2
                    r4 = 1
                    if (r2 == 0) goto L48
                    if (r2 == r4) goto L3a
                    if (r2 != r3) goto L32
                    java.lang.Object r7 = r0.L$1
                    java.lang.Object r8 = r0.L$0
                    com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1 r8 = (com.android.systemui.util.kotlin.FlowKt.AnonymousClass3.AnonymousClass1) r8
                    kotlin.ResultKt.throwOnFailure(r9)
                    goto L78
                L32:
                    java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                    java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                    r7.<init>(r8)
                    throw r7
                L3a:
                    java.lang.Object r7 = r0.L$2
                    kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                    java.lang.Object r8 = r0.L$1
                    java.lang.Object r2 = r0.L$0
                    com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1 r2 = (com.android.systemui.util.kotlin.FlowKt.AnonymousClass3.AnonymousClass1) r2
                    kotlin.ResultKt.throwOnFailure(r9)
                    goto L66
                L48:
                    kotlin.ResultKt.throwOnFailure(r9)
                    kotlinx.coroutines.flow.FlowCollector r9 = r7.$$this$flow
                    kotlin.jvm.functions.Function3 r2 = r7.$transform
                    kotlin.jvm.internal.Ref$ObjectRef r5 = r7.$previousValue
                    java.lang.Object r5 = r5.element
                    r0.L$0 = r7
                    r0.L$1 = r8
                    r0.L$2 = r9
                    r0.label = r4
                    java.lang.Object r2 = r2.invoke(r5, r8, r0)
                    if (r2 != r1) goto L62
                    goto L75
                L62:
                    r6 = r2
                    r2 = r7
                    r7 = r9
                    r9 = r6
                L66:
                    r0.L$0 = r2
                    r0.L$1 = r8
                    r4 = 0
                    r0.L$2 = r4
                    r0.label = r3
                    java.lang.Object r7 = r7.emit(r9, r0)
                    if (r7 != r1) goto L76
                L75:
                    return r1
                L76:
                    r7 = r8
                    r8 = r2
                L78:
                    kotlin.jvm.internal.Ref$ObjectRef r8 = r8.$previousValue
                    r8.element = r7
                    kotlin.Unit r7 = kotlin.Unit.INSTANCE
                    return r7
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt.AnonymousClass3.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(Function1 function1, Flow flow, Function3 function3, Continuation continuation) {
            super(2, continuation);
            this.$getInitialValue = function1;
            this.$this_pairwiseBy = flow;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$getInitialValue, this.$this_pairwiseBy, this.$transform, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass3) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0061, code lost:
        
            if (r7.collect(r1, r6) == r0) goto L16;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L2a
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r7)
                goto L64
            L12:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L1a:
                java.lang.Object r1 = r6.L$2
                kotlin.jvm.internal.Ref$ObjectRef r1 = (kotlin.jvm.internal.Ref$ObjectRef) r1
                java.lang.Object r3 = r6.L$1
                kotlin.jvm.internal.Ref$ObjectRef r3 = (kotlin.jvm.internal.Ref$ObjectRef) r3
                java.lang.Object r4 = r6.L$0
                kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
                kotlin.ResultKt.throwOnFailure(r7)
                goto L49
            L2a:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                r4 = r7
                kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
                kotlin.jvm.internal.Ref$ObjectRef r1 = new kotlin.jvm.internal.Ref$ObjectRef
                r1.<init>()
                kotlin.jvm.functions.Function1 r7 = r6.$getInitialValue
                r6.L$0 = r4
                r6.L$1 = r1
                r6.L$2 = r1
                r6.label = r3
                java.lang.Object r7 = r7.invoke(r6)
                if (r7 != r0) goto L48
                goto L63
            L48:
                r3 = r1
            L49:
                r1.element = r7
                kotlinx.coroutines.flow.Flow r7 = r6.$this_pairwiseBy
                com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1 r1 = new com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3$1
                kotlin.jvm.functions.Function3 r5 = r6.$transform
                r1.<init>(r4, r5, r3)
                r3 = 0
                r6.L$0 = r3
                r6.L$1 = r3
                r6.L$2 = r3
                r6.label = r2
                java.lang.Object r6 = r7.collect(r1, r6)
                if (r6 != r0) goto L64
            L63:
                return r0
            L64:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.util.kotlin.FlowKt$sample$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Flow.kt */
    final class C01391 extends SuspendLambda implements Function2 {
        final /* synthetic */ Flow $other;
        final /* synthetic */ Flow $this_sample;
        final /* synthetic */ Function3 $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.util.kotlin.FlowKt$sample$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: Flow.kt */
        final class C00301 extends SuspendLambda implements Function2 {
            final /* synthetic */ FlowCollector $$this$flow;
            final /* synthetic */ Flow $other;
            final /* synthetic */ Flow $this_sample;
            final /* synthetic */ Function3 $transform;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX INFO: renamed from: com.android.systemui.util.kotlin.FlowKt$sample$1$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: Flow.kt */
            final class C00311 implements FlowCollector {
                final /* synthetic */ FlowCollector $$this$flow;
                final /* synthetic */ Object $noVal;
                final /* synthetic */ AtomicReference $sampledRef;
                final /* synthetic */ Function3 $transform;

                C00311(AtomicReference atomicReference, Object obj, FlowCollector flowCollector, Function3 function3) {
                    this.$sampledRef = atomicReference;
                    this.$noVal = obj;
                    this.$$this$flow = flowCollector;
                    this.$transform = function3;
                }

                /* JADX WARN: Code restructure failed: missing block: B:23:0x0066, code lost:
                
                    if (r5.emit(r7, r0) == r1) goto L24;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.util.kotlin.FlowKt$sample$1$1$1$emit$1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.util.kotlin.FlowKt$sample$1$1$1$emit$1 r0 = (com.android.systemui.util.kotlin.FlowKt$sample$1$1$1$emit$1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.util.kotlin.FlowKt$sample$1$1$1$emit$1 r0 = new com.android.systemui.util.kotlin.FlowKt$sample$1$1$1$emit$1
                        r0.<init>(r5, r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3c
                        if (r2 == r4) goto L34
                        if (r2 != r3) goto L2c
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L69
                    L2c:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L34:
                        java.lang.Object r5 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5d
                    L3c:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.concurrent.atomic.AtomicReference r7 = r5.$sampledRef
                        java.lang.Object r7 = r7.get()
                        java.lang.Object r2 = r5.$noVal
                        boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r2)
                        if (r2 != 0) goto L6c
                        kotlinx.coroutines.flow.FlowCollector r2 = r5.$$this$flow
                        kotlin.jvm.functions.Function3 r5 = r5.$transform
                        r0.L$0 = r2
                        r0.label = r4
                        java.lang.Object r7 = r5.invoke(r6, r7, r0)
                        if (r7 != r1) goto L5c
                        goto L68
                    L5c:
                        r5 = r2
                    L5d:
                        r6 = 0
                        r0.L$0 = r6
                        r0.label = r3
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L69
                    L68:
                        return r1
                    L69:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    L6c:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt.C01391.C00301.C00311.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00301(Flow flow, Flow flow2, FlowCollector flowCollector, Function3 function3, Continuation continuation) {
                super(2, continuation);
                this.$this_sample = flow;
                this.$other = flow2;
                this.$$this$flow = flowCollector;
                this.$transform = function3;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00301 c00301 = new C00301(this.$this_sample, this.$other, this.$$this$flow, this.$transform, continuation);
                c00301.L$0 = obj;
                return c00301;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00301) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Job job;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    Object obj2 = new Object();
                    AtomicReference atomicReference = new AtomicReference(obj2);
                    Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getUnconfined(), null, new FlowKt$sample$1$1$job$1(this.$other, atomicReference, null), 2, null);
                    Flow flow = this.$this_sample;
                    C00311 c00311 = new C00311(atomicReference, obj2, this.$$this$flow, this.$transform);
                    this.L$0 = jobLaunch$default;
                    this.label = 1;
                    if (flow.collect(c00311, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    job = jobLaunch$default;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    job = (Job) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                Job.DefaultImpls.cancel$default(job, null, 1, null);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01391(Flow flow, Flow flow2, Function3 function3, Continuation continuation) {
            super(2, continuation);
            this.$this_sample = flow;
            this.$other = flow2;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C01391 c01391 = new C01391(this.$this_sample, this.$other, this.$transform, continuation);
            c01391.L$0 = obj;
            return c01391;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((C01391) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                C00301 c00301 = new C00301(this.$this_sample, this.$other, (FlowCollector) this.L$0, this.$transform, null);
                this.label = 1;
                if (CoroutineScopeKt.coroutineScope(c00301, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.android.systemui.util.kotlin.FlowKt$sample$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Flow.kt */
    final class C01402 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        int label;

        C01402(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Continuation continuation) {
            C01402 c01402 = new C01402(continuation);
            c01402.L$0 = obj2;
            return c01402.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return this.L$0;
        }
    }

    public static final Flow pairwiseBy(Flow flow, Object obj, Function3 function3) {
        flow.getClass();
        function3.getClass();
        return pairwiseBy(flow, (Function1) new AnonymousClass2(obj, null), function3);
    }

    public static final Flow pairwiseBy(Flow flow, Function1 function1, Function3 function3) {
        flow.getClass();
        function1.getClass();
        function3.getClass();
        return kotlinx.coroutines.flow.FlowKt.flow(new AnonymousClass3(function1, flow, function3, null));
    }

    public static final Flow pairwiseBy(Flow flow, Function3 function3) {
        flow.getClass();
        function3.getClass();
        return kotlinx.coroutines.flow.FlowKt.flow(new AnonymousClass1(flow, function3, null));
    }

    public static final Flow sample(Flow flow, Flow flow2) {
        flow.getClass();
        flow2.getClass();
        return sample(flow, flow2, new C01402(null));
    }

    public static final Flow sample(Flow flow, Flow flow2, Function3 function3) {
        flow.getClass();
        flow2.getClass();
        function3.getClass();
        return kotlinx.coroutines.flow.FlowKt.flow(new C01391(flow, flow2, function3, null));
    }
}
