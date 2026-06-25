package com.motorola.plugin.core.channel.remote;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.channel.AbstractCommonChannelImpl;
import com.motorola.plugin.core.channel.ChannelParams;
import com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.DisposableKt;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.sdk.channel.ClientId;
import com.motorola.plugin.sdk.channel.IRemoteCallback;
import com.motorola.plugin.sdk.channel.IRemoteChannel;
import com.motorola.plugin.sdk.channel.IRemoteChannelTransfer;
import java.lang.ref.WeakReference;
import kotlin.Lazy;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;

/* JADX INFO: compiled from: SecurityRemoteChannelImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SecurityRemoteChannelImpl extends AbstractCommonChannelImpl {
    public static final Companion Companion = new Companion(null);
    private static final int SERVICE_BIND_FLAGS = 1;
    private final Intent intent;
    private final Channel mRemoteChannelTransferChannel;
    private final RemoteChannelServiceConnection mServiceConnection;

    /* JADX INFO: compiled from: SecurityRemoteChannelImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: SecurityRemoteChannelImpl.kt */
    final class RemoteChannelServiceConnection implements ServiceConnection, Disposable {
        private final WeakReference channelRef;
        private final Lazy mChannelResponseCallback$delegate;

        public RemoteChannelServiceConnection(SecurityRemoteChannelImpl securityRemoteChannelImpl) {
            securityRemoteChannelImpl.getClass();
            this.channelRef = new WeakReference(securityRemoteChannelImpl);
            this.mChannelResponseCallback$delegate = ExtensionsKt.unsafeLazy(new Function0() { // from class: com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl$RemoteChannelServiceConnection$mChannelResponseCallback$2
                {
                    super(0);
                }

                /* JADX WARN: Type inference failed for: r0v0, types: [com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl$RemoteChannelServiceConnection$mChannelResponseCallback$2$1] */
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final AnonymousClass1 mo2224invoke() {
                    final SecurityRemoteChannelImpl.RemoteChannelServiceConnection remoteChannelServiceConnection = this.this$0;
                    return new IRemoteCallback.Stub() { // from class: com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl$RemoteChannelServiceConnection$mChannelResponseCallback$2.1
                        @Override // com.motorola.plugin.sdk.channel.IRemoteCallback
                        public void sendResult(Bundle bundle) {
                            bundle.getClass();
                            SecurityRemoteChannelImpl securityRemoteChannelImpl2 = (SecurityRemoteChannelImpl) remoteChannelServiceConnection.channelRef.get();
                            if (securityRemoteChannelImpl2 == null) {
                                return;
                            }
                            securityRemoteChannelImpl2.getMyChannelResponseHelper().onChannelResponse(bundle);
                        }
                    };
                }
            });
        }

        private final IRemoteCallback getMChannelResponseCallback() {
            return (IRemoteCallback) this.mChannelResponseCallback$delegate.getValue();
        }

        @Override // com.motorola.plugin.core.misc.Disposable
        public void dispose() {
            this.channelRef.clear();
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            componentName.getClass();
            SecurityRemoteChannelImpl securityRemoteChannelImpl = (SecurityRemoteChannelImpl) this.channelRef.get();
            if (securityRemoteChannelImpl == null) {
                return;
            }
            AbstractCommonChannelImpl.reportDisconnection$default(securityRemoteChannelImpl, "binding died", false, 2, null);
            securityRemoteChannelImpl.optimisticBind();
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(final ComponentName componentName) {
            componentName.getClass();
            final SecurityRemoteChannelImpl securityRemoteChannelImpl = (SecurityRemoteChannelImpl) this.channelRef.get();
            if (securityRemoteChannelImpl == null) {
                return;
            }
            PluginConfigKt.trace$default(PluginConfigKt.TAG_REMOTE_CHANNEL, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onNullBinding$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + securityRemoteChannelImpl.getToken() + "] Null binding from remote channel service [" + componentName.flattenToShortString() + ']';
                }
            }, 60, null);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(final ComponentName componentName, IBinder iBinder) {
            componentName.getClass();
            iBinder.getClass();
            final SecurityRemoteChannelImpl securityRemoteChannelImpl = (SecurityRemoteChannelImpl) this.channelRef.get();
            if (securityRemoteChannelImpl == null) {
                return;
            }
            PluginConfigKt.trace$default(PluginConfigKt.TAG_REMOTE_CHANNEL, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onServiceConnected$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + securityRemoteChannelImpl.getToken() + "] Remote channel service connected";
                }
            }, 60, null);
            IRemoteChannelTransfer iRemoteChannelTransferAsInterface = IRemoteChannelTransfer.Stub.asInterface(iBinder);
            if (iRemoteChannelTransferAsInterface == null) {
                return;
            }
            try {
                iRemoteChannelTransferAsInterface.onConnect(securityRemoteChannelImpl.getClientId(), getMChannelResponseCallback());
                PluginConfigKt.trace$default(PluginConfigKt.TAG_REMOTE_CHANNEL, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onServiceConnected$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return '[' + securityRemoteChannelImpl.getToken() + "] Publishing remote channel service [" + componentName.flattenToShortString() + ']';
                    }
                }, 60, null);
                BuildersKt__Builders_commonKt.launch$default(securityRemoteChannelImpl.getChannelScope(), Dispatchers.getDefault(), null, new SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onServiceConnected$2$2(securityRemoteChannelImpl, iRemoteChannelTransferAsInterface, null), 2, null);
            } catch (RemoteException e) {
                PluginConfigKt.trace$default(PluginConfigKt.TAG_REMOTE_CHANNEL, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onServiceConnected$2$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return '[' + securityRemoteChannelImpl.getToken() + "] Remote channel service connected, but it died soon cause " + ((Object) e.getMessage());
                    }
                }, 60, null);
                BuildersKt__Builders_commonKt.launch$default(securityRemoteChannelImpl.getChannelScope(), Dispatchers.getDefault(), null, new SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onServiceConnected$2$4(securityRemoteChannelImpl, null), 2, null);
                securityRemoteChannelImpl.reportDisconnection("remote object died soon", false);
                Unit unit = Unit.INSTANCE;
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(final ComponentName componentName) {
            componentName.getClass();
            final SecurityRemoteChannelImpl securityRemoteChannelImpl = (SecurityRemoteChannelImpl) this.channelRef.get();
            if (securityRemoteChannelImpl == null) {
                return;
            }
            PluginConfigKt.trace$default(PluginConfigKt.TAG_REMOTE_CHANNEL, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onServiceDisconnected$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + securityRemoteChannelImpl.getToken() + "] Remote channel service disconnected [" + componentName.flattenToShortString() + ']';
                }
            }, 60, null);
        }
    }

    /* JADX INFO: compiled from: SecurityRemoteChannelImpl.kt */
    final class RemoteChannelSnapshot extends AbstractSnapshot {
        private boolean myChannelIsClosedForReceive;
        private boolean myChannelIsClosedForSend;
        private boolean myChannelIsEmpty;
        public Intent myIntent;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RemoteChannelSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final boolean getMyChannelIsClosedForReceive() {
            return this.myChannelIsClosedForReceive;
        }

        public final boolean getMyChannelIsClosedForSend() {
            return this.myChannelIsClosedForSend;
        }

        public final boolean getMyChannelIsEmpty() {
            return this.myChannelIsEmpty;
        }

        public final Intent getMyIntent() {
            Intent intent = this.myIntent;
            if (intent != null) {
                return intent;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myIntent");
            throw null;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printSingle(PluginConfigKt.TAG_REMOTE_CHANNEL).newLine();
            iPrinter.increaseIndent();
            iPrinter.printPair("intent", getMyIntent());
            iPrinter.printPair("closedForReceive", Boolean.valueOf(getMyChannelIsClosedForReceive()));
            iPrinter.printPair("closedForSend", Boolean.valueOf(getMyChannelIsClosedForSend()));
            iPrinter.printPair("isEmpty", Boolean.valueOf(getMyChannelIsEmpty()));
            iPrinter.decreaseIndent();
            iPrinter.newLine();
        }

        public final void setMyChannelIsClosedForReceive(boolean z) {
            this.myChannelIsClosedForReceive = z;
        }

        public final void setMyChannelIsClosedForSend(boolean z) {
            this.myChannelIsClosedForSend = z;
        }

        public final void setMyChannelIsEmpty(boolean z) {
            this.myChannelIsEmpty = z;
        }

        public final void setMyIntent(Intent intent) {
            intent.getClass();
            this.myIntent = intent;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl$getChannelTransferLocked$1, reason: invalid class name */
    /* JADX INFO: compiled from: SecurityRemoteChannelImpl.kt */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SecurityRemoteChannelImpl.this.getChannelTransferLocked(null, this);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecurityRemoteChannelImpl(ChannelParams channelParams) {
        super(PluginConfigKt.TAG_REMOTE_CHANNEL, channelParams);
        channelParams.getClass();
        Intent component = new Intent("com.motorola.plugin.action.PLUGIN_DISCOVERY").setComponent(channelParams.getServiceComponent());
        component.getClass();
        this.intent = component;
        RemoteChannelServiceConnection remoteChannelServiceConnection = new RemoteChannelServiceConnection(this);
        this.mServiceConnection = remoteChannelServiceConnection;
        Channel channelChannel$default = ChannelKt.Channel$default(0, null, null, 6, null);
        this.mRemoteChannelTransferChannel = channelChannel$default;
        DisposableKt.add(getMDisposable(), channelChannel$default);
        getMDisposable().add(remoteChannelServiceConnection);
    }

    @Override // com.motorola.plugin.core.channel.AbstractCommonChannelImpl
    protected RemoteChannelRequest generateRemoteChannelRequest() {
        Intent intent = this.intent;
        ClientId clientId = getClientId();
        String str = getClientId().uniqueId;
        str.getClass();
        return new RemoteChannelRequest(intent, clientId, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.motorola.plugin.core.channel.AbstractCommonChannelImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected java.lang.Object getChannelTransferLocked(final java.lang.String r14, kotlin.coroutines.Continuation r15) throws com.motorola.plugin.sdk.channel.ConnectionException {
        /*
            Method dump skipped, instruction units count: 227
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl.getChannelTransferLocked(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.motorola.plugin.core.channel.AbstractCommonChannelImpl
    protected void onDisconnected(String str) {
        str.getClass();
        ExtensionsKt.unbindServiceSafely(this.mServiceConnection, getContext());
    }

    @Override // com.motorola.plugin.core.channel.AbstractCommonChannelImpl
    protected boolean onInterceptTransfer(Job job, Bundle bundle, IRemoteChannel.IOnRemoteError iOnRemoteError, IRemoteChannel.IOnRemoteResponse iOnRemoteResponse) {
        bundle.getClass();
        iOnRemoteError.getClass();
        iOnRemoteResponse.getClass();
        return false;
    }

    @Override // com.motorola.plugin.core.channel.AbstractCommonChannelImpl, com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        RemoteChannelSnapshot remoteChannelSnapshot = new RemoteChannelSnapshot(super.snapshot(iSnapshot));
        remoteChannelSnapshot.setMyIntent(this.intent);
        remoteChannelSnapshot.setMyChannelIsClosedForReceive(this.mRemoteChannelTransferChannel.isClosedForReceive());
        remoteChannelSnapshot.setMyChannelIsClosedForSend(this.mRemoteChannelTransferChannel.isClosedForSend());
        remoteChannelSnapshot.setMyChannelIsEmpty(this.mRemoteChannelTransferChannel.isEmpty());
        return remoteChannelSnapshot;
    }
}
