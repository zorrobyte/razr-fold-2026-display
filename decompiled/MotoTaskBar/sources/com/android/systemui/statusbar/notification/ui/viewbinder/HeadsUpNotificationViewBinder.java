package com.android.systemui.statusbar.notification.ui.viewbinder;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.HeadsUpRowKey;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.Iterator;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* JADX INFO: compiled from: HeadsUpNotificationViewBinder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpNotificationViewBinder {
    private final NotificationListViewModel viewModel;

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder$bindHeadsUpNotifications$2, reason: invalid class name */
    /* JADX INFO: compiled from: HeadsUpNotificationViewBinder.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ NotificationStackScrollLayout $parentView;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder$bindHeadsUpNotifications$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: HeadsUpNotificationViewBinder.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationStackScrollLayout $parentView;
            int label;
            final /* synthetic */ HeadsUpNotificationViewBinder this$0;

            /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder$bindHeadsUpNotifications$2$1$3, reason: invalid class name */
            /* JADX INFO: compiled from: HeadsUpNotificationViewBinder.kt */
            final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
                public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

                AnonymousClass3() {
                    super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                    return invoke((Set) obj, ((Boolean) obj2).booleanValue(), (Continuation) obj3);
                }

                public final Object invoke(Set set, boolean z, Continuation continuation) {
                    return AnonymousClass1.invokeSuspend$lambda$0(set, z, continuation);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(HeadsUpNotificationViewBinder headsUpNotificationViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                super(2, continuation);
                this.this$0 = headsUpNotificationViewBinder;
                this.$parentView = notificationStackScrollLayout;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static final /* synthetic */ Object invokeSuspend$lambda$0(Set set, boolean z, Continuation continuation) {
                return new Pair(set, Boxing.boxBoolean(z));
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, this.$parentView, continuation);
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
                    final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                    ref$ObjectRef.element = SetsKt.emptySet();
                    Flow flowSample = FlowKt.sample(this.this$0.viewModel.getPinnedHeadsUpRows(), this.this$0.viewModel.getHeadsUpAnimationsEnabled(), AnonymousClass3.INSTANCE);
                    final NotificationStackScrollLayout notificationStackScrollLayout = this.$parentView;
                    final HeadsUpNotificationViewBinder headsUpNotificationViewBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder.bindHeadsUpNotifications.2.1.4
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Pair pair, Continuation continuation) {
                            Set set = (Set) pair.component1();
                            boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
                            Set setMinus = SetsKt.minus(set, (Iterable) ref$ObjectRef.element);
                            Set setMinus2 = SetsKt.minus((Set) ref$ObjectRef.element, set);
                            ref$ObjectRef.element = set;
                            if (zBooleanValue) {
                                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayout;
                                HeadsUpNotificationViewBinder headsUpNotificationViewBinder2 = headsUpNotificationViewBinder;
                                Iterator it = setMinus.iterator();
                                while (it.hasNext()) {
                                    notificationStackScrollLayout2.generateHeadsUpAnimation(headsUpNotificationViewBinder2.obtainView((HeadsUpRowKey) it.next()), true);
                                }
                                HeadsUpNotificationViewBinder headsUpNotificationViewBinder3 = headsUpNotificationViewBinder;
                                NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayout;
                                Iterator it2 = setMinus2.iterator();
                                while (it2.hasNext()) {
                                    ExpandableNotificationRow expandableNotificationRowObtainView = headsUpNotificationViewBinder3.obtainView((HeadsUpRowKey) it2.next());
                                    notificationStackScrollLayout3.generateHeadsUpAnimation(expandableNotificationRowObtainView, false);
                                    expandableNotificationRowObtainView.setHeadsUpIsVisible();
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowSample.collect(flowCollector, this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder$bindHeadsUpNotifications$2$2, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: HeadsUpNotificationViewBinder.kt */
        final class C00262 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationStackScrollLayout $parentView;
            int label;
            final /* synthetic */ HeadsUpNotificationViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00262(HeadsUpNotificationViewBinder headsUpNotificationViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                super(2, continuation);
                this.this$0 = headsUpNotificationViewBinder;
                this.$parentView = notificationStackScrollLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00262(this.this$0, this.$parentView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00262) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow topHeadsUpRow = this.this$0.viewModel.getTopHeadsUpRow();
                    final NotificationStackScrollLayout notificationStackScrollLayout = this.$parentView;
                    final HeadsUpNotificationViewBinder headsUpNotificationViewBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder.bindHeadsUpNotifications.2.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(HeadsUpRowKey headsUpRowKey, Continuation continuation) {
                            notificationStackScrollLayout.setTopHeadsUpRow(headsUpRowKey != null ? headsUpNotificationViewBinder.obtainView(headsUpRowKey) : null);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (topHeadsUpRow.collect(flowCollector, this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder$bindHeadsUpNotifications$2$3, reason: invalid class name */
        /* JADX INFO: compiled from: HeadsUpNotificationViewBinder.kt */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationStackScrollLayout $parentView;
            int label;
            final /* synthetic */ HeadsUpNotificationViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(HeadsUpNotificationViewBinder headsUpNotificationViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                super(2, continuation);
                this.this$0 = headsUpNotificationViewBinder;
                this.$parentView = notificationStackScrollLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, this.$parentView, continuation);
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
                    Flow hasPinnedHeadsUpRow = this.this$0.viewModel.getHasPinnedHeadsUpRow();
                    final NotificationStackScrollLayout notificationStackScrollLayout = this.$parentView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder.bindHeadsUpNotifications.2.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation continuation) {
                            notificationStackScrollLayout.setInHeadsUpPinnedMode(z);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (hasPinnedHeadsUpRow.collect(flowCollector, this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder$bindHeadsUpNotifications$2$4, reason: invalid class name */
        /* JADX INFO: compiled from: HeadsUpNotificationViewBinder.kt */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationStackScrollLayout $parentView;
            int label;
            final /* synthetic */ HeadsUpNotificationViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass4(NotificationStackScrollLayout notificationStackScrollLayout, HeadsUpNotificationViewBinder headsUpNotificationViewBinder, Continuation continuation) {
                super(2, continuation);
                this.$parentView = notificationStackScrollLayout;
                this.this$0 = headsUpNotificationViewBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$parentView, this.this$0, continuation);
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
                    Flow flowIsHeadsUpAnimatingAway = HeadsUpNotificationViewBinderKt.isHeadsUpAnimatingAway(this.$parentView);
                    final HeadsUpNotificationViewBinder headsUpNotificationViewBinder = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder.bindHeadsUpNotifications.2.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation continuation) {
                            headsUpNotificationViewBinder.viewModel.setHeadsUpAnimatingAway(z);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowIsHeadsUpAnimatingAway.collect(flowCollector, this) == coroutine_suspended) {
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
        AnonymousClass2(NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
            super(2, continuation);
            this.$parentView = notificationStackScrollLayout;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = HeadsUpNotificationViewBinder.this.new AnonymousClass2(this.$parentView, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass1(HeadsUpNotificationViewBinder.this, this.$parentView, null), 3, null);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C00262(HeadsUpNotificationViewBinder.this, this.$parentView, null), 3, null);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass3(HeadsUpNotificationViewBinder.this, this.$parentView, null), 3, null);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$parentView, HeadsUpNotificationViewBinder.this, null), 3, null);
            return Unit.INSTANCE;
        }
    }

    public HeadsUpNotificationViewBinder(NotificationListViewModel notificationListViewModel) {
        notificationListViewModel.getClass();
        this.viewModel = notificationListViewModel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExpandableNotificationRow obtainView(HeadsUpRowKey headsUpRowKey) {
        Object objElementKeyFor = this.viewModel.elementKeyFor(headsUpRowKey);
        objElementKeyFor.getClass();
        return (ExpandableNotificationRow) objElementKeyFor;
    }

    public final Object bindHeadsUpNotifications(NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(notificationStackScrollLayout, null), continuation);
        return objCoroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCoroutineScope : Unit.INSTANCE;
    }
}
