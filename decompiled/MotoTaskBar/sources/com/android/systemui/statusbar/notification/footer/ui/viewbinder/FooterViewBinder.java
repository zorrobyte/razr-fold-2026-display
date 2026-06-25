package com.android.systemui.statusbar.notification.footer.ui.viewbinder;

import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import com.android.systemui.util.ui.AnimatedValue;
import java.util.function.Consumer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* JADX INFO: compiled from: FooterViewBinder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FooterViewBinder {
    public static final FooterViewBinder INSTANCE = new FooterViewBinder();

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bind$2, reason: invalid class name */
    /* JADX INFO: compiled from: FooterViewBinder.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ View.OnClickListener $clearAllNotifications;
        final /* synthetic */ FooterView $footer;
        final /* synthetic */ FooterViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bind$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: FooterViewBinder.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ View.OnClickListener $clearAllNotifications;
            final /* synthetic */ FooterView $footer;
            final /* synthetic */ FooterViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(FooterView footerView, FooterViewModel footerViewModel, View.OnClickListener onClickListener, Continuation continuation) {
                super(2, continuation);
                this.$footer = footerView;
                this.$viewModel = footerViewModel;
                this.$clearAllNotifications = onClickListener;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$footer, this.$viewModel, this.$clearAllNotifications, continuation);
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
                    FooterViewBinder footerViewBinder = FooterViewBinder.INSTANCE;
                    FooterView footerView = this.$footer;
                    FooterViewModel footerViewModel = this.$viewModel;
                    View.OnClickListener onClickListener = this.$clearAllNotifications;
                    this.label = 1;
                    if (footerViewBinder.bindClearAllButton(footerView, footerViewModel, onClickListener, this) == coroutine_suspended) {
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
        AnonymousClass2(FooterView footerView, FooterViewModel footerViewModel, View.OnClickListener onClickListener, Continuation continuation) {
            super(2, continuation);
            this.$footer = footerView;
            this.$viewModel = footerViewModel;
            this.$clearAllNotifications = onClickListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$footer, this.$viewModel, this.$clearAllNotifications, continuation);
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
            return BuildersKt__Builders_commonKt.launch$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.$footer, this.$viewModel, this.$clearAllNotifications, null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindClearAllButton$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FooterViewBinder.kt */
    final class C01182 extends SuspendLambda implements Function2 {
        final /* synthetic */ View.OnClickListener $clearAllNotifications;
        final /* synthetic */ FooterView $footer;
        final /* synthetic */ FooterViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindClearAllButton$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: FooterViewBinder.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ FooterView $footer;
            final /* synthetic */ FooterViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(FooterViewModel footerViewModel, FooterView footerView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = footerViewModel;
                this.$footer = footerView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$footer, continuation);
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
                    Flow labelId = this.$viewModel.getClearAllButton().getLabelId();
                    final FooterView footerView = this.$footer;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindClearAllButton.2.1.1
                        public final Object emit(int i2, Continuation continuation) {
                            footerView.setClearAllButtonText(i2);
                            return Unit.INSTANCE;
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).intValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (labelId.collect(flowCollector, this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindClearAllButton$2$2, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: FooterViewBinder.kt */
        final class C00142 extends SuspendLambda implements Function2 {
            final /* synthetic */ FooterView $footer;
            final /* synthetic */ FooterViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00142(FooterViewModel footerViewModel, FooterView footerView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = footerViewModel;
                this.$footer = footerView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00142(this.$viewModel, this.$footer, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00142) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow accessibilityDescriptionId = this.$viewModel.getClearAllButton().getAccessibilityDescriptionId();
                    final FooterView footerView = this.$footer;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindClearAllButton.2.2.1
                        public final Object emit(int i2, Continuation continuation) {
                            footerView.setClearAllButtonDescription(i2);
                            return Unit.INSTANCE;
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).intValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (accessibilityDescriptionId.collect(flowCollector, this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindClearAllButton$2$3, reason: invalid class name */
        /* JADX INFO: compiled from: FooterViewBinder.kt */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ FooterView $footer;
            final /* synthetic */ FooterViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(FooterViewModel footerViewModel, FooterView footerView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = footerViewModel;
                this.$footer = footerView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$footer, continuation);
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
                    Flow flowIsVisible = this.$viewModel.getClearAllButton().isVisible();
                    final FooterView footerView = this.$footer;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindClearAllButton.2.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(final AnimatedValue animatedValue, Continuation continuation) {
                            Object value;
                            Object value2;
                            boolean z = animatedValue instanceof AnimatedValue.Animating;
                            if (z) {
                                FooterView footerView2 = footerView;
                                if (animatedValue instanceof AnimatedValue.Animating) {
                                    value2 = ((AnimatedValue.Animating) animatedValue).getValue();
                                } else {
                                    if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    value2 = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                                }
                                footerView2.setClearAllButtonVisible(((Boolean) value2).booleanValue(), true, new Consumer() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindClearAllButton.2.3.1.1
                                    @Override // java.util.function.Consumer
                                    public final void accept(Boolean bool) {
                                        AnimatedValue animatedValue2 = animatedValue;
                                        if (animatedValue2 instanceof AnimatedValue.Animating) {
                                            ((AnimatedValue.Animating) animatedValue2).getOnStopAnimating().mo2224invoke();
                                        }
                                    }
                                });
                            } else {
                                FooterView footerView3 = footerView;
                                if (z) {
                                    value = ((AnimatedValue.Animating) animatedValue).getValue();
                                } else {
                                    if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                                }
                                footerView3.setClearAllButtonVisible(((Boolean) value).booleanValue(), false);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowIsVisible.collect(flowCollector, this) == coroutine_suspended) {
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
        C01182(FooterView footerView, View.OnClickListener onClickListener, FooterViewModel footerViewModel, Continuation continuation) {
            super(2, continuation);
            this.$footer = footerView;
            this.$clearAllNotifications = onClickListener;
            this.$viewModel = footerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C01182 c01182 = new C01182(this.$footer, this.$clearAllNotifications, this.$viewModel, continuation);
            c01182.L$0 = obj;
            return c01182;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01182) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            this.$footer.setClearAllButtonClickListener(this.$clearAllNotifications);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$footer, null), 3, null);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C00142(this.$viewModel, this.$footer, null), 3, null);
            return BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$footer, null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindWhileAttached$1, reason: invalid class name */
    /* JADX INFO: compiled from: FooterViewBinder.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ View.OnClickListener $clearAllNotifications;
        final /* synthetic */ FooterView $footer;
        final /* synthetic */ FooterViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindWhileAttached$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: FooterViewBinder.kt */
        final class C00161 extends SuspendLambda implements Function2 {
            final /* synthetic */ View.OnClickListener $clearAllNotifications;
            final /* synthetic */ FooterView $footer;
            final /* synthetic */ FooterViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00161(FooterView footerView, FooterViewModel footerViewModel, View.OnClickListener onClickListener, Continuation continuation) {
                super(2, continuation);
                this.$footer = footerView;
                this.$viewModel = footerViewModel;
                this.$clearAllNotifications = onClickListener;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00161(this.$footer, this.$viewModel, this.$clearAllNotifications, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00161) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.$footer.setManageOrHistoryButtonVisible(false);
                    FooterViewBinder footerViewBinder = FooterViewBinder.INSTANCE;
                    FooterView footerView = this.$footer;
                    FooterViewModel footerViewModel = this.$viewModel;
                    View.OnClickListener onClickListener = this.$clearAllNotifications;
                    this.label = 1;
                    if (footerViewBinder.bind(footerView, footerViewModel, onClickListener, this) == coroutine_suspended) {
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
        AnonymousClass1(FooterView footerView, FooterViewModel footerViewModel, View.OnClickListener onClickListener, Continuation continuation) {
            super(3, continuation);
            this.$footer = footerView;
            this.$viewModel = footerViewModel;
            this.$clearAllNotifications = onClickListener;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$footer, this.$viewModel, this.$clearAllNotifications, continuation);
            anonymousClass1.L$0 = lifecycleOwner;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new C00161(this.$footer, this.$viewModel, this.$clearAllNotifications, null), 3, null);
            return Unit.INSTANCE;
        }
    }

    private FooterViewBinder() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object bindClearAllButton(FooterView footerView, FooterViewModel footerViewModel, View.OnClickListener onClickListener, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new C01182(footerView, onClickListener, footerViewModel, null), continuation);
    }

    public final Object bind(FooterView footerView, FooterViewModel footerViewModel, View.OnClickListener onClickListener, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new AnonymousClass2(footerView, footerViewModel, onClickListener, null), continuation);
    }

    public final DisposableHandle bindWhileAttached(FooterView footerView, FooterViewModel footerViewModel, View.OnClickListener onClickListener) {
        footerView.getClass();
        footerViewModel.getClass();
        onClickListener.getClass();
        return RepeatWhenAttachedKt.repeatWhenAttached$default(footerView, null, new AnonymousClass1(footerView, footerViewModel, onClickListener, null), 1, null);
    }
}
