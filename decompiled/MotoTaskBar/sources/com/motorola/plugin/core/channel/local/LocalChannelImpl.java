package com.motorola.plugin.core.channel.local;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.channel.AbstractCommonChannelImpl;
import com.motorola.plugin.core.channel.ChannelParams;
import com.motorola.plugin.core.channel.remote.RemoteChannelRequest;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.sdk.channel.ClientId;
import com.motorola.plugin.sdk.channel.IRemoteCallback;
import com.motorola.plugin.sdk.channel.IRemoteChannelTransfer;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: LocalChannelImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LocalChannelImpl extends AbstractCommonChannelImpl {
    private final LocalChannelImpl$myChannelResponseCallback$1 myChannelResponseCallback;
    private final Lazy myLocalChannelTransferRegistry$delegate;

    /* JADX INFO: compiled from: LocalChannelImpl.kt */
    final class LocalChannelSnapshot extends AbstractSnapshot {
        public ISnapshot myLocalChannelTransferRegistrySnapshot;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LocalChannelSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final ISnapshot getMyLocalChannelTransferRegistrySnapshot() {
            ISnapshot iSnapshot = this.myLocalChannelTransferRegistrySnapshot;
            if (iSnapshot != null) {
                return iSnapshot;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myLocalChannelTransferRegistrySnapshot");
            throw null;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printSingle(PluginConfigKt.TAG_LOCAL_CHANNEL).newLine();
            iPrinter.increaseIndent();
            getMyLocalChannelTransferRegistrySnapshot().onSnapshot(iPrinter);
            iPrinter.decreaseIndent();
        }

        public final void setMyLocalChannelTransferRegistrySnapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            this.myLocalChannelTransferRegistrySnapshot = iSnapshot;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.local.LocalChannelImpl$fetchTransferOrThrow$1, reason: invalid class name */
    /* JADX INFO: compiled from: LocalChannelImpl.kt */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return LocalChannelImpl.this.fetchTransferOrThrow(null, null, this);
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.local.LocalChannelImpl$fetchTransferOrThrow$3, reason: invalid class name */
    /* JADX INFO: compiled from: LocalChannelImpl.kt */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ IRemoteChannelTransfer $transfer;
        int label;
        final /* synthetic */ LocalChannelImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(IRemoteChannelTransfer iRemoteChannelTransfer, LocalChannelImpl localChannelImpl, Continuation continuation) {
            super(2, continuation);
            this.$transfer = iRemoteChannelTransfer;
            this.this$0 = localChannelImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$transfer, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws RemoteException {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$transfer.onConnect(this.this$0.getClientId(), this.this$0.myChannelResponseCallback);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.motorola.plugin.core.channel.local.LocalChannelImpl$myChannelResponseCallback$1] */
    public LocalChannelImpl(ChannelParams channelParams) {
        super(PluginConfigKt.TAG_LOCAL_CHANNEL, channelParams);
        channelParams.getClass();
        this.myLocalChannelTransferRegistry$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.channel.local.LocalChannelImpl$myLocalChannelTransferRegistry$2
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final ILocalChannelTransferRegistry mo2224invoke() {
                return LocalChannelTransferRegistryFactory.Factory.getOrCreate();
            }
        });
        this.myChannelResponseCallback = new IRemoteCallback.Default() { // from class: com.motorola.plugin.core.channel.local.LocalChannelImpl$myChannelResponseCallback$1
            @Override // com.motorola.plugin.sdk.channel.IRemoteCallback.Default, com.motorola.plugin.sdk.channel.IRemoteCallback
            public void sendResult(Bundle bundle) {
                bundle.getClass();
                this.this$0.getMyChannelResponseHelper().onChannelResponse(bundle);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object fetchTransferOrThrow(com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry r17, final java.lang.String r18, kotlin.coroutines.Continuation r19) throws com.motorola.plugin.sdk.channel.ConnectionException {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            r2 = r19
            boolean r3 = r2 instanceof com.motorola.plugin.core.channel.local.LocalChannelImpl.AnonymousClass1
            if (r3 == 0) goto L19
            r3 = r2
            com.motorola.plugin.core.channel.local.LocalChannelImpl$fetchTransferOrThrow$1 r3 = (com.motorola.plugin.core.channel.local.LocalChannelImpl.AnonymousClass1) r3
            int r4 = r3.label
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = r4 & r5
            if (r6 == 0) goto L19
            int r4 = r4 - r5
            r3.label = r4
            goto L1e
        L19:
            com.motorola.plugin.core.channel.local.LocalChannelImpl$fetchTransferOrThrow$1 r3 = new com.motorola.plugin.core.channel.local.LocalChannelImpl$fetchTransferOrThrow$1
            r3.<init>(r2)
        L1e:
            java.lang.Object r2 = r3.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r3.label
            r6 = 1
            if (r5 == 0) goto L3b
            if (r5 != r6) goto L33
            java.lang.Object r0 = r3.L$0
            com.motorola.plugin.sdk.channel.IRemoteChannelTransfer r0 = (com.motorola.plugin.sdk.channel.IRemoteChannelTransfer) r0
            kotlin.ResultKt.throwOnFailure(r2)
            return r0
        L33:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L3b:
            kotlin.ResultKt.throwOnFailure(r2)
            com.motorola.plugin.core.channel.local.ILocalChannelTransfer r2 = r17.getTransfer(r18)
            r5 = 0
            if (r2 != 0) goto L47
            r2 = r5
            goto L4d
        L47:
            com.motorola.plugin.core.channel.local.LocalChannelTransferRegistry r7 = com.motorola.plugin.core.channel.local.LocalChannelTransferRegistry.INSTANCE
            com.motorola.plugin.sdk.channel.IRemoteChannelTransfer r2 = r7.asRemoteTransfer$core_stubRelease(r2)
        L4d:
            if (r2 == 0) goto L64
            kotlinx.coroutines.MainCoroutineDispatcher r1 = kotlinx.coroutines.Dispatchers.getMain()
            com.motorola.plugin.core.channel.local.LocalChannelImpl$fetchTransferOrThrow$3 r7 = new com.motorola.plugin.core.channel.local.LocalChannelImpl$fetchTransferOrThrow$3
            r7.<init>(r2, r0, r5)
            r3.L$0 = r2
            r3.label = r6
            java.lang.Object r0 = kotlinx.coroutines.BuildersKt.withContext(r1, r7, r3)
            if (r0 != r4) goto L63
            return r4
        L63:
            return r2
        L64:
            com.motorola.plugin.core.Level r8 = com.motorola.plugin.core.Level.WARN
            com.motorola.plugin.core.channel.local.LocalChannelImpl$fetchTransferOrThrow$2 r13 = new com.motorola.plugin.core.channel.local.LocalChannelImpl$fetchTransferOrThrow$2
            r13.<init>()
            r14 = 61
            r15 = 0
            r7 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            com.motorola.plugin.core.PluginConfigKt.trace$default(r7, r8, r9, r10, r11, r12, r13, r14, r15)
            com.motorola.plugin.sdk.channel.ConnectionException r0 = new com.motorola.plugin.sdk.channel.ConnectionException
            java.lang.String r2 = "No transfer for plugin "
            java.lang.String r1 = kotlin.jvm.internal.Intrinsics.stringPlus(r2, r1)
            r0.<init>(r6, r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.channel.local.LocalChannelImpl.fetchTransferOrThrow(com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final ILocalChannelTransferRegistry getMyLocalChannelTransferRegistry() {
        return (ILocalChannelTransferRegistry) this.myLocalChannelTransferRegistry$delegate.getValue();
    }

    @Override // com.motorola.plugin.core.channel.AbstractCommonChannelImpl
    protected RemoteChannelRequest generateRemoteChannelRequest() {
        Intent intent = new Intent(getAction());
        ClientId clientId = getClientId();
        String str = getClientId().uniqueId;
        str.getClass();
        return new RemoteChannelRequest(intent, clientId, str);
    }

    @Override // com.motorola.plugin.core.channel.AbstractCommonChannelImpl
    protected Object getChannelTransferLocked(String str, Continuation continuation) {
        return fetchTransferOrThrow(getMyLocalChannelTransferRegistry(), getAction(), continuation);
    }

    @Override // com.motorola.plugin.core.channel.AbstractCommonChannelImpl, com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        LocalChannelSnapshot localChannelSnapshot = new LocalChannelSnapshot(super.snapshot(iSnapshot));
        localChannelSnapshot.setMyLocalChannelTransferRegistrySnapshot(ISnapshotAware.DefaultImpls.snapshot$default(getMyLocalChannelTransferRegistry(), null, 1, null));
        return localChannelSnapshot;
    }
}
