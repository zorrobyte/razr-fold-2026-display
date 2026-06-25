package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import android.view.View;
import android.widget.ViewFlipper;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
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

/* JADX INFO: compiled from: NotificationViewFlipperBinder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationViewFlipperBinder {
    public static final NotificationViewFlipperBinder INSTANCE = new NotificationViewFlipperBinder();

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder$bind$2, reason: invalid class name */
    /* JADX INFO: compiled from: NotificationViewFlipperBinder.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ViewFlipper $viewFlipper;
        final /* synthetic */ NotificationViewFlipperViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder$bind$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: NotificationViewFlipperBinder.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewFlipper $viewFlipper;
            final /* synthetic */ NotificationViewFlipperViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(NotificationViewFlipperViewModel notificationViewFlipperViewModel, ViewFlipper viewFlipper, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = notificationViewFlipperViewModel;
                this.$viewFlipper = viewFlipper;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$viewFlipper, continuation);
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
                    Flow flowIsPaused = this.$viewModel.isPaused();
                    final ViewFlipper viewFlipper = this.$viewFlipper;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder.bind.2.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation continuation) {
                            NotificationViewFlipperBinder.INSTANCE.setPaused(viewFlipper, z);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowIsPaused.collect(flowCollector, this) == coroutine_suspended) {
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
        AnonymousClass2(NotificationViewFlipperViewModel notificationViewFlipperViewModel, ViewFlipper viewFlipper, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = notificationViewFlipperViewModel;
            this.$viewFlipper = viewFlipper;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$viewFlipper, continuation);
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
            return BuildersKt__Builders_commonKt.launch$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.$viewModel, this.$viewFlipper, null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder$bindWhileAttached$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NotificationViewFlipperBinder.kt */
    final class C01222 extends SuspendLambda implements Function3 {
        final /* synthetic */ ViewFlipper $viewFlipper;
        final /* synthetic */ NotificationViewFlipperViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder$bindWhileAttached$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: NotificationViewFlipperBinder.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewFlipper $viewFlipper;
            final /* synthetic */ NotificationViewFlipperViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(ViewFlipper viewFlipper, NotificationViewFlipperViewModel notificationViewFlipperViewModel, Continuation continuation) {
                super(2, continuation);
                this.$viewFlipper = viewFlipper;
                this.$viewModel = notificationViewFlipperViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewFlipper, this.$viewModel, continuation);
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
                    NotificationViewFlipperBinder notificationViewFlipperBinder = NotificationViewFlipperBinder.INSTANCE;
                    ViewFlipper viewFlipper = this.$viewFlipper;
                    NotificationViewFlipperViewModel notificationViewFlipperViewModel = this.$viewModel;
                    this.label = 1;
                    if (notificationViewFlipperBinder.bind(viewFlipper, notificationViewFlipperViewModel, this) == coroutine_suspended) {
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
        C01222(ViewFlipper viewFlipper, NotificationViewFlipperViewModel notificationViewFlipperViewModel, Continuation continuation) {
            super(3, continuation);
            this.$viewFlipper = viewFlipper;
            this.$viewModel = notificationViewFlipperViewModel;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation continuation) {
            C01222 c01222 = new C01222(this.$viewFlipper, this.$viewModel, continuation);
            c01222.L$0 = lifecycleOwner;
            return c01222.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new AnonymousClass1(this.$viewFlipper, this.$viewModel, null), 3, null);
            return Unit.INSTANCE;
        }
    }

    private NotificationViewFlipperBinder() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setPaused(ViewFlipper viewFlipper, boolean z) {
        if (z) {
            viewFlipper.stopFlipping();
        } else if (viewFlipper.isAutoStart()) {
            viewFlipper.startFlipping();
        }
    }

    public final Object bind(ViewFlipper viewFlipper, NotificationViewFlipperViewModel notificationViewFlipperViewModel, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new AnonymousClass2(notificationViewFlipperViewModel, viewFlipper, null), continuation);
    }

    public final DisposableHandle bindWhileAttached(ViewFlipper viewFlipper, NotificationViewFlipperViewModel notificationViewFlipperViewModel) {
        viewFlipper.getClass();
        notificationViewFlipperViewModel.getClass();
        return viewFlipper.isAutoStart() ? new DisposableHandle() { // from class: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder.bindWhileAttached.1
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
            }
        } : RepeatWhenAttachedKt.repeatWhenAttached$default(viewFlipper, null, new C01222(viewFlipper, notificationViewFlipperViewModel, null), 1, null);
    }
}
