package androidx.window.layout;

import android.content.Context;
import androidx.core.util.Consumer;
import androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0;
import androidx.window.WindowSdkExtensions;
import androidx.window.layout.adapter.WindowBackend;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: WindowInfoTrackerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class WindowInfoTrackerImpl implements WindowInfoTracker {
    private final WindowBackend windowBackend;
    private final WindowMetricsCalculator windowMetricsCalculator;
    private final WindowSdkExtensions windowSdkExtensions;

    /* JADX INFO: renamed from: androidx.window.layout.WindowInfoTrackerImpl$windowLayoutInfo$1, reason: invalid class name */
    /* JADX INFO: compiled from: WindowInfoTrackerImpl.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Context $context;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = WindowInfoTrackerImpl.this.new AnonymousClass1(this.$context, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final Consumer consumer = new Consumer() { // from class: androidx.window.layout.WindowInfoTrackerImpl$windowLayoutInfo$1$$ExternalSyntheticLambda0
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj2) {
                        producerScope.mo2736trySendJP2dKIU((WindowLayoutInfo) obj2);
                    }
                };
                WindowInfoTrackerImpl.this.windowBackend.registerLayoutChangeCallback(this.$context, new ProfileInstallReceiver$$ExternalSyntheticLambda0(), consumer);
                final WindowInfoTrackerImpl windowInfoTrackerImpl = WindowInfoTrackerImpl.this;
                Function0 function0 = new Function0() { // from class: androidx.window.layout.WindowInfoTrackerImpl.windowLayoutInfo.1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public /* bridge */ /* synthetic */ Object mo2224invoke() {
                        m1113invoke();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                    public final void m1113invoke() {
                        windowInfoTrackerImpl.windowBackend.unregisterLayoutChangeCallback(consumer);
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
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

    public WindowInfoTrackerImpl(WindowMetricsCalculator windowMetricsCalculator, WindowBackend windowBackend, WindowSdkExtensions windowSdkExtensions) {
        windowMetricsCalculator.getClass();
        windowBackend.getClass();
        windowSdkExtensions.getClass();
        this.windowMetricsCalculator = windowMetricsCalculator;
        this.windowBackend = windowBackend;
        this.windowSdkExtensions = windowSdkExtensions;
    }

    @Override // androidx.window.layout.WindowInfoTracker
    public Flow windowLayoutInfo(Context context) {
        context.getClass();
        return FlowKt.flowOn(FlowKt.callbackFlow(new AnonymousClass1(context, null)), Dispatchers.getMain());
    }
}
