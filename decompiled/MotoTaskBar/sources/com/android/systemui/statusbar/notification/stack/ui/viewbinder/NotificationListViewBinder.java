package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.Flags;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.common.ui.view.ViewExtKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder;
import com.android.systemui.util.kotlin.DisposableHandleExtKt;
import com.android.systemui.util.ui.AnimatedValue;
import com.motorola.taskbar.R$layout;
import java.util.Optional;
import javax.inject.Provider;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: NotificationListViewBinder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationListViewBinder {
    private final CoroutineDispatcher backgroundDispatcher;
    private final ConfigurationState configuration;
    private final HeadsUpNotificationViewBinder hunBinder;
    private final Optional loggerOptional;
    private final Provider notificationActivityStarter;
    private final SectionHeaderController silentHeaderController;
    private final NotificationListViewModel viewModel;

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindEmptyShade$4, reason: invalid class name */
    /* JADX INFO: compiled from: NotificationListViewBinder.kt */
    final /* synthetic */ class AnonymousClass4 extends AdaptedFunctionReference implements Function4 {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        AnonymousClass4() {
            super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function4
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            return invoke(((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue(), ((Boolean) obj3).booleanValue(), (Continuation) obj4);
        }

        public final Object invoke(boolean z, boolean z2, boolean z3, Continuation continuation) {
            return NotificationListViewBinder.bindEmptyShade$lambda$1(z, z2, z3, continuation);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2, reason: invalid class name */
    /* JADX INFO: compiled from: NotificationListViewBinder.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ FooterView $footerView;
        final /* synthetic */ FooterViewModel $footerViewModel;
        final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
        final /* synthetic */ NotificationStackScrollLayout $parentView;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ NotificationListViewBinder this$0;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: NotificationListViewBinder.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ FooterView $footerView;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(NotificationListViewBinder notificationListViewBinder, FooterView footerView, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$footerView = footerView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, this.$footerView, continuation);
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
                    Flow shouldIncludeFooterView = this.this$0.viewModel.getShouldIncludeFooterView();
                    final FooterView footerView = this.$footerView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.bindFooter.2.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(AnimatedValue animatedValue, Continuation continuation) {
                            Object value;
                            FooterView footerView2 = footerView;
                            boolean z = animatedValue instanceof AnimatedValue.Animating;
                            if (z) {
                                value = ((AnimatedValue.Animating) animatedValue).getValue();
                            } else {
                                if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                            }
                            footerView2.setVisible(((Boolean) value).booleanValue(), z);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (shouldIncludeFooterView.collect(flowCollector, this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$2, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NotificationListViewBinder.kt */
        final class C00212 extends SuspendLambda implements Function2 {
            final /* synthetic */ FooterView $footerView;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00212(NotificationListViewBinder notificationListViewBinder, FooterView footerView, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$footerView = footerView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00212(this.this$0, this.$footerView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00212) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow shouldHideFooterView = this.this$0.viewModel.getShouldHideFooterView();
                    final FooterView footerView = this.$footerView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.bindFooter.2.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation continuation) {
                            footerView.setShouldBeHidden(z);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (shouldHideFooterView.collect(flowCollector, this) == coroutine_suspended) {
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
        AnonymousClass2(FooterView footerView, FooterViewModel footerViewModel, NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
            super(2, continuation);
            this.$footerView = footerView;
            this.$footerViewModel = footerViewModel;
            this.this$0 = notificationListViewBinder;
            this.$parentView = notificationStackScrollLayout;
            this.$hasNonClearableSilentNotifications = stateFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$footerView, this.$footerViewModel, this.this$0, this.$parentView, this.$hasNonClearableSilentNotifications, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                FooterViewBinder footerViewBinder = FooterViewBinder.INSTANCE;
                FooterView footerView = this.$footerView;
                FooterViewModel footerViewModel = this.$footerViewModel;
                final NotificationListViewBinder notificationListViewBinder = this.this$0;
                final NotificationStackScrollLayout notificationStackScrollLayout = this.$parentView;
                final StateFlow stateFlow = this.$hasNonClearableSilentNotifications;
                DisposableHandle disposableHandleBindWhileAttached = footerViewBinder.bindWhileAttached(footerView, footerViewModel, new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$disposableHandle$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        notificationListViewBinder.clearAllNotifications(notificationStackScrollLayout, !((Boolean) stateFlow.getValue()).booleanValue());
                    }
                });
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, this.$footerView, null), 3, null);
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C00212(this.this$0, this.$footerView, null), 3, null);
                this.label = 1;
                if (DisposableHandleExtKt.awaitCancellationThenDispose(disposableHandleBindWhileAttached, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindLogger$1, reason: invalid class name */
    /* JADX INFO: compiled from: NotificationListViewBinder.kt */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return NotificationListViewBinder.this.bindLogger(null, this);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NotificationListViewBinder.kt */
    final class C01232 extends SuspendLambda implements Function2 {
        final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
        final /* synthetic */ NotificationStackScrollLayout $parentView;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01232(NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
            super(2, continuation);
            this.$parentView = notificationStackScrollLayout;
            this.$hasNonClearableSilentNotifications = stateFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C01232 c01232 = NotificationListViewBinder.this.new C01232(this.$parentView, this.$hasNonClearableSilentNotifications, continuation);
            c01232.L$0 = obj;
            return c01232;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01232) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0056, code lost:
        
            if (kotlinx.coroutines.DelayKt.awaitCancellation(r7) == r0) goto L19;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L20
                if (r1 == r3) goto L1c
                if (r1 == r2) goto L16
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r8)
                throw r7
            L16:
                kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L1a
                goto L59
            L1a:
                r8 = move-exception
                goto L5f
            L1c:
                kotlin.ResultKt.throwOnFailure(r8)
                goto L3a
            L20:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                kotlinx.coroutines.CoroutineScope r8 = (kotlinx.coroutines.CoroutineScope) r8
                com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder r1 = com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.this
                com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel r1 = com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.access$getViewModel$p(r1)
                kotlinx.coroutines.flow.Flow r1 = r1.getHasClearableAlertingNotifications()
                r7.label = r3
                java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.stateIn(r1, r8, r7)
                if (r8 != r0) goto L3a
                goto L58
            L3a:
                kotlinx.coroutines.flow.StateFlow r8 = (kotlinx.coroutines.flow.StateFlow) r8
                com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder r1 = com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.this
                com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r1 = com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.access$getSilentHeaderController$p(r1)
                com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$1 r3 = new com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$1
                com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder r4 = com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r7.$parentView
                kotlinx.coroutines.flow.StateFlow r6 = r7.$hasNonClearableSilentNotifications
                r3.<init>()
                r1.setOnClearSectionClickListener(r3)
                r7.label = r2     // Catch: java.lang.Throwable -> L1a
                java.lang.Object r8 = kotlinx.coroutines.DelayKt.awaitCancellation(r7)     // Catch: java.lang.Throwable -> L1a
                if (r8 != r0) goto L59
            L58:
                return r0
            L59:
                kotlin.KotlinNothingValueException r8 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L1a
                r8.<init>()     // Catch: java.lang.Throwable -> L1a
                throw r8     // Catch: java.lang.Throwable -> L1a
            L5f:
                com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder r7 = com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.this
                com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r7 = com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.access$getSilentHeaderController$p(r7)
                com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$2 r0 = new android.view.View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.bindSilentHeaderClickListener.2.2
                    static {
                        /*
                            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$2 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$2
                            r0.<init>()
                            
                            // error: 0x0005: SPUT 
  (r0 I:com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$2)
 com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.bindSilentHeaderClickListener.2.2.INSTANCE com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$2
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.C01232.ViewOnClickListenerC00222.<clinit>():void");
                    }

                    {
                        /*
                            r0 = this;
                            r0.<init>()
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.C01232.ViewOnClickListenerC00222.<init>():void");
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(android.view.View r1) {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.C01232.ViewOnClickListenerC00222.onClick(android.view.View):void");
                    }
                }
                r7.setOnClearSectionClickListener(r0)
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.C01232.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NotificationListViewBinder.kt */
    final class C01241 extends SuspendLambda implements Function3 {
        final /* synthetic */ NotificationStackScrollLayout $view;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NotificationListViewBinder.kt */
        final class C00231 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationStackScrollLayout $view;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ NotificationListViewBinder this$0;

            /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: NotificationListViewBinder.kt */
            final class C00241 extends SuspendLambda implements Function2 {
                final /* synthetic */ NotificationStackScrollLayout $view;
                int label;
                final /* synthetic */ NotificationListViewBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C00241(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = notificationListViewBinder;
                    this.$view = notificationStackScrollLayout;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00241(this.this$0, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((C00241) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        HeadsUpNotificationViewBinder headsUpNotificationViewBinder = this.this$0.hunBinder;
                        NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                        this.label = 1;
                        if (headsUpNotificationViewBinder.bindHeadsUpNotifications(notificationStackScrollLayout, this) == coroutine_suspended) {
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

            /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: NotificationListViewBinder.kt */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
                final /* synthetic */ NotificationStackScrollLayout $view;
                int label;
                final /* synthetic */ NotificationListViewBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass2(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = notificationListViewBinder;
                    this.$view = notificationStackScrollLayout;
                    this.$hasNonClearableSilentNotifications = stateFlow;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.this$0, this.$view, this.$hasNonClearableSilentNotifications, continuation);
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
                        NotificationListViewBinder notificationListViewBinder = this.this$0;
                        NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                        StateFlow stateFlow = this.$hasNonClearableSilentNotifications;
                        this.label = 1;
                        if (notificationListViewBinder.reinflateAndBindFooter(notificationStackScrollLayout, stateFlow, this) == coroutine_suspended) {
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

            /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$3, reason: invalid class name */
            /* JADX INFO: compiled from: NotificationListViewBinder.kt */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ NotificationStackScrollLayout $view;
                int label;
                final /* synthetic */ NotificationListViewBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass3(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = notificationListViewBinder;
                    this.$view = notificationStackScrollLayout;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.this$0, this.$view, continuation);
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
                        NotificationListViewBinder notificationListViewBinder = this.this$0;
                        NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                        this.label = 1;
                        if (notificationListViewBinder.bindEmptyShade(notificationStackScrollLayout, this) == coroutine_suspended) {
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

            /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$4, reason: invalid class name */
            /* JADX INFO: compiled from: NotificationListViewBinder.kt */
            final class AnonymousClass4 extends SuspendLambda implements Function2 {
                final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
                final /* synthetic */ NotificationStackScrollLayout $view;
                int label;
                final /* synthetic */ NotificationListViewBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass4(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = notificationListViewBinder;
                    this.$view = notificationStackScrollLayout;
                    this.$hasNonClearableSilentNotifications = stateFlow;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass4(this.this$0, this.$view, this.$hasNonClearableSilentNotifications, continuation);
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
                        NotificationListViewBinder notificationListViewBinder = this.this$0;
                        NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                        StateFlow stateFlow = this.$hasNonClearableSilentNotifications;
                        this.label = 1;
                        if (notificationListViewBinder.bindSilentHeaderClickListener(notificationStackScrollLayout, stateFlow, this) == coroutine_suspended) {
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

            /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$5, reason: invalid class name */
            /* JADX INFO: compiled from: NotificationListViewBinder.kt */
            final class AnonymousClass5 extends SuspendLambda implements Function2 {
                final /* synthetic */ NotificationStackScrollLayout $view;
                int label;
                final /* synthetic */ NotificationListViewBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass5(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = notificationListViewBinder;
                    this.$view = notificationStackScrollLayout;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass5(this.this$0, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((AnonymousClass5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow flowIsImportantForAccessibility = this.this$0.viewModel.isImportantForAccessibility();
                        final NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.bindWhileAttached.1.1.5.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                                return emit(((Boolean) obj2).booleanValue(), continuation);
                            }

                            public final Object emit(boolean z, Continuation continuation) {
                                ViewExtKt.setImportantForAccessibilityYesNo(notificationStackScrollLayout, z);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowIsImportantForAccessibility.collect(flowCollector, this) == coroutine_suspended) {
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

            /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindWhileAttached$1$1$6, reason: invalid class name */
            /* JADX INFO: compiled from: NotificationListViewBinder.kt */
            final class AnonymousClass6 extends SuspendLambda implements Function2 {
                final /* synthetic */ NotificationStackScrollLayout $view;
                int label;
                final /* synthetic */ NotificationListViewBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass6(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = notificationListViewBinder;
                    this.$view = notificationStackScrollLayout;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass6(this.this$0, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((AnonymousClass6) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        NotificationListViewBinder notificationListViewBinder = this.this$0;
                        NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                        this.label = 1;
                        if (notificationListViewBinder.bindLogger(notificationStackScrollLayout, this) == coroutine_suspended) {
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
            C00231(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationListViewBinder;
                this.$view = notificationStackScrollLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00231 c00231 = new C00231(this.this$0, this.$view, continuation);
                c00231.L$0 = obj;
                return c00231;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00231) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineScope coroutineScope;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    coroutineScope = (CoroutineScope) this.L$0;
                    if (Flags.notificationsHeadsUpRefactor()) {
                        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C00241(this.this$0, this.$view, null), 3, null);
                    }
                    if (Flags.notificationsFooterViewRefactor()) {
                        Flow hasNonClearableSilentNotifications = this.this$0.viewModel.getHasNonClearableSilentNotifications();
                        this.L$0 = coroutineScope;
                        this.label = 1;
                        obj = FlowKt.stateIn(hasNonClearableSilentNotifications, coroutineScope, this);
                        if (obj == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                    BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass6(this.this$0, this.$view, null), 3, null);
                    return Unit.INSTANCE;
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                CoroutineScope coroutineScope2 = (CoroutineScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                coroutineScope = coroutineScope2;
                StateFlow stateFlow = (StateFlow) obj;
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, this.$view, stateFlow, null), 3, null);
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, this.$view, null), 3, null);
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.this$0, this.$view, stateFlow, null), 3, null);
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.this$0, this.$view, null), 3, null);
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass6(this.this$0, this.$view, null), 3, null);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01241(NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
            super(3, continuation);
            this.$view = notificationStackScrollLayout;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation continuation) {
            C01241 c01241 = NotificationListViewBinder.this.new C01241(this.$view, continuation);
            c01241.L$0 = lifecycleOwner;
            return c01241.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new C00231(NotificationListViewBinder.this, this.$view, null), 3, null);
            return Unit.INSTANCE;
        }
    }

    public NotificationListViewBinder(CoroutineDispatcher coroutineDispatcher, ConfigurationState configurationState, HeadsUpNotificationViewBinder headsUpNotificationViewBinder, Optional optional, Provider provider, SectionHeaderController sectionHeaderController, NotificationListViewModel notificationListViewModel) {
        coroutineDispatcher.getClass();
        configurationState.getClass();
        headsUpNotificationViewBinder.getClass();
        optional.getClass();
        provider.getClass();
        sectionHeaderController.getClass();
        notificationListViewModel.getClass();
        this.backgroundDispatcher = coroutineDispatcher;
        this.configuration = configurationState;
        this.hunBinder = headsUpNotificationViewBinder;
        this.loggerOptional = optional;
        this.notificationActivityStarter = provider;
        this.silentHeaderController = sectionHeaderController;
        this.viewModel = notificationListViewModel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object bindEmptyShade(final NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
        Object objCollect = FlowKt.combine(this.viewModel.getShouldShowEmptyShadeView(), this.viewModel.getAreNotificationsHiddenInShade(), this.viewModel.getHasFilteredOutSeenNotifications(), AnonymousClass4.INSTANCE).collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.bindEmptyShade.5
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Triple triple, Continuation continuation2) {
                notificationStackScrollLayout.updateEmptyShadeView(((Boolean) triple.component1()).booleanValue(), ((Boolean) triple.component2()).booleanValue(), ((Boolean) triple.component3()).booleanValue());
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object bindEmptyShade$lambda$1(boolean z, boolean z2, boolean z3, Continuation continuation) {
        return new Triple(Boxing.boxBoolean(z), Boxing.boxBoolean(z2), Boxing.boxBoolean(z3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object bindFooter(FooterView footerView, FooterViewModel footerViewModel, NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(footerView, footerViewModel, this, notificationStackScrollLayout, stateFlow, null), continuation);
        return objCoroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCoroutineScope : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object bindLogger(com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindLogger$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindLogger$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindLogger$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5e
        L29:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L31:
            kotlin.ResultKt.throwOnFailure(r6)
            boolean r6 = com.android.systemui.Flags.notificationsLiveDataStoreRefactor()
            if (r6 == 0) goto L5e
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel r6 = r4.viewModel
            java.util.Optional r6 = r6.getLogger()
            r2 = 0
            java.lang.Object r6 = r6.orElse(r2)
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel r6 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel) r6
            if (r6 == 0) goto L5e
            java.util.Optional r4 = r4.loggerOptional
            java.lang.Object r4 = r4.orElse(r2)
            com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger r4 = (com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger) r4
            if (r4 == 0) goto L5e
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder r2 = com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder.INSTANCE
            r0.label = r3
            java.lang.Object r4 = r2.bindLogger(r5, r4, r6, r0)
            if (r4 != r1) goto L5e
            return r1
        L5e:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.bindLogger(com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object bindSilentHeaderClickListener(NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new C01232(notificationStackScrollLayout, stateFlow, null), continuation);
        return objCoroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCoroutineScope : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clearAllNotifications(NotificationStackScrollLayout notificationStackScrollLayout, boolean z) {
        notificationStackScrollLayout.clearAllNotifications(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clearSilentNotifications(NotificationStackScrollLayout notificationStackScrollLayout, boolean z, boolean z2) {
        notificationStackScrollLayout.clearSilentNotifications(z, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object reinflateAndBindFooter(NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
        Object objCollectLatest;
        FooterViewModel footerViewModel = (FooterViewModel) this.viewModel.getFooter().orElse(null);
        return (footerViewModel == null || (objCollectLatest = FlowKt.collectLatest(FlowKt.flowOn(this.configuration.inflateLayout(R$layout.status_bar_notification_footer, notificationStackScrollLayout, false), this.backgroundDispatcher), new NotificationListViewBinder$reinflateAndBindFooter$2$1(notificationStackScrollLayout, this, footerViewModel, stateFlow, null), continuation)) != IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? Unit.INSTANCE : objCollectLatest;
    }

    public final void bindWhileAttached(NotificationStackScrollLayout notificationStackScrollLayout, NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        notificationStackScrollLayout.getClass();
        notificationStackScrollLayoutController.getClass();
        RepeatWhenAttachedKt.repeatWhenAttached$default(notificationStackScrollLayout, null, new C01241(notificationStackScrollLayout, null), 1, null);
    }
}
