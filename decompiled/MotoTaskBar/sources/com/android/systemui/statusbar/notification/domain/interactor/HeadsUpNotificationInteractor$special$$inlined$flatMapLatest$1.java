package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository;
import java.util.ArrayList;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* JADX INFO: compiled from: Merge.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(FlowCollector flowCollector, Object obj, Continuation continuation) {
        HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$1 headsUpNotificationInteractor$special$$inlined$flatMapLatest$1 = new HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$1(continuation);
        headsUpNotificationInteractor$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        headsUpNotificationInteractor$special$$inlined$flatMapLatest$1.L$1 = obj;
        return headsUpNotificationInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowFlowOf;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Set set = (Set) this.L$1;
            if (set.isEmpty()) {
                flowFlowOf = FlowKt.flowOf(SetsKt.emptySet());
            } else {
                Set<HeadsUpRowRepository> set2 = set;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
                for (final HeadsUpRowRepository headsUpRowRepository : set2) {
                    final StateFlow stateFlowIsPinned = headsUpRowRepository.isPinned();
                    arrayList.add(new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$lambda$1$$inlined$map$1

                        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$lambda$1$$inlined$map$1$2, reason: invalid class name */
                        /* JADX INFO: compiled from: Emitters.kt */
                        public final class AnonymousClass2 implements FlowCollector {
                            final /* synthetic */ HeadsUpRowRepository $repo$inlined;
                            final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
                            public final class AnonymousClass1 extends ContinuationImpl {
                                Object L$0;
                                int label;
                                /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector, HeadsUpRowRepository headsUpRowRepository) {
                                this.$this_unsafeFlow = flowCollector;
                                this.$repo$inlined = headsUpRowRepository;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct add '--show-bad-code' argument
                            */
                            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                /*
                                    r4 = this;
                                    boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$lambda$1$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$lambda$1$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$lambda$1$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L31
                                    if (r2 != r3) goto L29
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L4f
                                L29:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L31:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow
                                    java.lang.Boolean r5 = (java.lang.Boolean) r5
                                    boolean r5 = r5.booleanValue()
                                    com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository r4 = r4.$repo$inlined
                                    java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r5)
                                    kotlin.Pair r4 = kotlin.TuplesKt.to(r4, r5)
                                    r0.label = r3
                                    java.lang.Object r4 = r6.emit(r4, r0)
                                    if (r4 != r1) goto L4f
                                    return r1
                                L4f:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$lambda$1$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public Object collect(FlowCollector flowCollector2, Continuation continuation) {
                            Object objCollect = stateFlowIsPinned.collect(new AnonymousClass2(flowCollector2, headsUpRowRepository), continuation);
                            return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                        }
                    });
                }
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    FlowKt.checkCombinedFlow((Flow) obj2);
                }
                final Flow[] flowArr = (Flow[]) CollectionsKt.toList(arrayList).toArray(new Flow[0]);
                flowFlowOf = new Flow() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$$inlined$combine$1

                    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$$inlined$combine$1$3, reason: invalid class name */
                    /* JADX INFO: compiled from: Zip.kt */
                    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                        private /* synthetic */ Object L$0;
                        /* synthetic */ Object L$1;
                        int label;

                        public AnonymousClass3(Continuation continuation) {
                            super(3, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(FlowCollector flowCollector, Object[] objArr, Continuation continuation) {
                            AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
                            anonymousClass3.L$0 = flowCollector;
                            anonymousClass3.L$1 = objArr;
                            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                FlowCollector flowCollector = (FlowCollector) this.L$0;
                                Pair[] pairArr = (Pair[]) ((Object[]) this.L$1);
                                ArrayList arrayList = new ArrayList();
                                int i2 = 0;
                                for (Pair pair : pairArr) {
                                    if (((Boolean) pair.component2()).booleanValue()) {
                                        arrayList.add(pair);
                                    }
                                }
                                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
                                int size = arrayList.size();
                                while (i2 < size) {
                                    Object obj2 = arrayList.get(i2);
                                    i2++;
                                    arrayList2.add((HeadsUpRowRepository) ((Pair) obj2).component1());
                                }
                                Set set = CollectionsKt.toSet(arrayList2);
                                this.label = 1;
                                if (flowCollector.emit(set, this) == coroutine_suspended) {
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

                    @Override // kotlinx.coroutines.flow.Flow
                    public Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        final Flow[] flowArr2 = flowArr;
                        Object objCombineInternal = CombineKt.combineInternal(flowCollector2, flowArr2, new Function0() { // from class: com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$pinnedHeadsUpRows$lambda$5$$inlined$combine$1.2
                            @Override // kotlin.jvm.functions.Function0
                            /* JADX INFO: renamed from: invoke */
                            public final Object[] mo2224invoke() {
                                return new Pair[flowArr2.length];
                            }
                        }, new AnonymousClass3(null), continuation);
                        return objCombineInternal == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCombineInternal : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowFlowOf, this) == coroutine_suspended) {
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
