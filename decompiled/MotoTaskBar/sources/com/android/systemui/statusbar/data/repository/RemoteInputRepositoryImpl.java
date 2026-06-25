package com.android.systemui.statusbar.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.data.repository.RemoteInputRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: RemoteInputRepository.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RemoteInputRepositoryImpl implements RemoteInputRepository {
    private final Flow isRemoteInputActive;
    private final NotificationRemoteInputManager notificationRemoteInputManager;

    /* JADX INFO: renamed from: com.android.systemui.statusbar.data.repository.RemoteInputRepositoryImpl$isRemoteInputActive$1, reason: invalid class name */
    /* JADX INFO: compiled from: RemoteInputRepository.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(RemoteInputRepositoryImpl remoteInputRepositoryImpl, RemoteInputRepositoryImpl$isRemoteInputActive$1$callback$1 remoteInputRepositoryImpl$isRemoteInputActive$1$callback$1) {
            remoteInputRepositoryImpl.notificationRemoteInputManager.removeControllerCallback(remoteInputRepositoryImpl$isRemoteInputActive$1$callback$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = RemoteInputRepositoryImpl.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.RemoteInputController$Callback, com.android.systemui.statusbar.data.repository.RemoteInputRepositoryImpl$isRemoteInputActive$1$callback$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                producerScope.mo2736trySendJP2dKIU(Boxing.boxBoolean(false));
                final ?? r1 = new RemoteInputController.Callback() { // from class: com.android.systemui.statusbar.data.repository.RemoteInputRepositoryImpl$isRemoteInputActive$1$callback$1
                    @Override // com.android.systemui.statusbar.RemoteInputController.Callback
                    public void onRemoteInputActive(boolean z) {
                        producerScope.mo2736trySendJP2dKIU(Boolean.valueOf(z));
                    }
                };
                RemoteInputRepositoryImpl.this.notificationRemoteInputManager.addControllerCallback(r1);
                final RemoteInputRepositoryImpl remoteInputRepositoryImpl = RemoteInputRepositoryImpl.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.data.repository.RemoteInputRepositoryImpl$isRemoteInputActive$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return RemoteInputRepositoryImpl.AnonymousClass1.invokeSuspend$lambda$0(remoteInputRepositoryImpl, r1);
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

    public RemoteInputRepositoryImpl(NotificationRemoteInputManager notificationRemoteInputManager) {
        notificationRemoteInputManager.getClass();
        this.notificationRemoteInputManager = notificationRemoteInputManager;
        this.isRemoteInputActive = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new AnonymousClass1(null));
    }

    @Override // com.android.systemui.statusbar.data.repository.RemoteInputRepository
    public Flow isRemoteInputActive() {
        return this.isRemoteInputActive;
    }
}
