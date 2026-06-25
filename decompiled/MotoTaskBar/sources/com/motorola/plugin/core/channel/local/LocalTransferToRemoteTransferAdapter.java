package com.motorola.plugin.core.channel.local;

import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.motorola.plugin.core.channel.local.LocalTransferToRemoteTransferAdapter;
import com.motorola.plugin.sdk.channel.ClientId;
import com.motorola.plugin.sdk.channel.IRemoteCallback;
import com.motorola.plugin.sdk.channel.IRemoteChannelTransfer;
import com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo;
import com.motorola.plugin.sdk.channel.RemoteService;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LocalChannelTransferRegistry.kt */
/* JADX INFO: loaded from: classes2.dex */
final class LocalTransferToRemoteTransferAdapter extends IRemoteChannelTransfer.Default implements ILocalChannelTransfer, ILocalCallback {
    private final Map callbackMap;
    private Map mResponseToClientCallbackMap;
    private final ILocalChannelTransfer transfer;

    /* JADX INFO: compiled from: LocalChannelTransferRegistry.kt */
    final class LocalCallbackWithSequence implements ILocalCallback {
        private final ILocalCallback localCallback;
        private final int sequence;

        public LocalCallbackWithSequence(int i, ILocalCallback iLocalCallback) {
            iLocalCallback.getClass();
            this.sequence = i;
            this.localCallback = iLocalCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: sendResult$lambda-0, reason: not valid java name */
        public static final void m2209sendResult$lambda0(LocalCallbackWithSequence localCallbackWithSequence, Bundle bundle) {
            localCallbackWithSequence.getClass();
            bundle.getClass();
            localCallbackWithSequence.getLocalCallback().sendResult(bundle);
        }

        public final ILocalCallback getLocalCallback() {
            return this.localCallback;
        }

        public final int getSequence() {
            return this.sequence;
        }

        @Override // com.motorola.plugin.core.channel.local.ILocalCallback
        public void sendResult(final Bundle bundle) {
            bundle.getClass();
            bundle.putInt(RemoteService.EXTRA_SEQUENCE, this.sequence);
            CompletableFuture.runAsync(new Runnable() { // from class: com.motorola.plugin.core.channel.local.LocalTransferToRemoteTransferAdapter$LocalCallbackWithSequence$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LocalTransferToRemoteTransferAdapter.LocalCallbackWithSequence.m2209sendResult$lambda0(this.f$0, bundle);
                }
            });
        }
    }

    public LocalTransferToRemoteTransferAdapter(ILocalChannelTransfer iLocalChannelTransfer) {
        iLocalChannelTransfer.getClass();
        this.transfer = iLocalChannelTransfer;
        this.callbackMap = new LinkedHashMap();
        this.mResponseToClientCallbackMap = new LinkedHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: transferRequest$lambda-2, reason: not valid java name */
    public static final void m2208transferRequest$lambda2(int i, LocalTransferToRemoteTransferAdapter localTransferToRemoteTransferAdapter, RemoteChannelRequestInfo remoteChannelRequestInfo) {
        localTransferToRemoteTransferAdapter.getClass();
        remoteChannelRequestInfo.getClass();
        LocalCallbackWithSequence localCallbackWithSequence = new LocalCallbackWithSequence(i, localTransferToRemoteTransferAdapter);
        Object obj = remoteChannelRequestInfo.bundle.get(RemoteService.EXTRA_METHOD);
        if (!(Intrinsics.areEqual(obj, RemoteService.METHOD_CLEAR_CACHE) ? true : Intrinsics.areEqual(obj, RemoteService.METHOD_CLEAR_DATA))) {
            ILocalChannelTransfer iLocalChannelTransferAsLocalTransfer = localTransferToRemoteTransferAdapter.asLocalTransfer();
            ClientId clientId = remoteChannelRequestInfo.callingClientId;
            clientId.getClass();
            ClientId clientId2 = remoteChannelRequestInfo.pluginId;
            clientId2.getClass();
            Bundle bundle = remoteChannelRequestInfo.bundle;
            bundle.getClass();
            iLocalChannelTransferAsLocalTransfer.transfer(clientId, clientId2, bundle, localCallbackWithSequence);
            return;
        }
        ClientId clientId3 = remoteChannelRequestInfo.callingClientId;
        clientId3.getClass();
        ClientId clientId4 = remoteChannelRequestInfo.pluginId;
        clientId4.getClass();
        boolean zOnRequestClearPluginData = localTransferToRemoteTransferAdapter.onRequestClearPluginData(clientId3, clientId4, Intrinsics.areEqual(obj, RemoteService.METHOD_CLEAR_CACHE));
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean(RemoteService.EXTRA_RECEIPT, zOnRequestClearPluginData);
        Unit unit = Unit.INSTANCE;
        localCallbackWithSequence.sendResult(bundle2);
    }

    public final ILocalChannelTransfer asLocalTransfer() {
        return this.transfer;
    }

    public final IRemoteChannelTransfer asRemoteTransfer() {
        return this;
    }

    public final void notifyDataChanged(Uri[] uriArr, Bundle bundle) throws RemoteException {
        uriArr.getClass();
        Iterator it = this.mResponseToClientCallbackMap.entrySet().iterator();
        while (it.hasNext()) {
            IRemoteCallback iRemoteCallback = (IRemoteCallback) ((Map.Entry) it.next()).getValue();
            if (iRemoteCallback != null) {
                Bundle bundle2 = new Bundle();
                bundle2.putString(RemoteService.EXTRA_METHOD, RemoteService.METHOD_NOTIFY_DATASET_CHANGE);
                bundle2.putParcelableArrayList(RemoteService.EXTRA_NOTIFY_URIS, CollectionsKt.arrayListOf(Arrays.copyOf(uriArr, uriArr.length)));
                if (bundle != null) {
                    bundle2.putBundle(RemoteService.EXTRA_BUNDLE_DATA, bundle);
                }
                Unit unit = Unit.INSTANCE;
                iRemoteCallback.sendResult(bundle2);
            }
        }
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer.Default, com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
    public void onConnect(ClientId clientId, IRemoteCallback iRemoteCallback) {
        clientId.getClass();
        if (this.mResponseToClientCallbackMap.containsKey(clientId)) {
            return;
        }
        this.mResponseToClientCallbackMap.put(clientId, iRemoteCallback);
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer.Default, com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
    public void onDisconnect(ClientId clientId) {
        clientId.getClass();
        this.mResponseToClientCallbackMap.remove(clientId);
    }

    public final boolean onRequestClearPluginData(ClientId clientId, ClientId clientId2, boolean z) {
        clientId.getClass();
        clientId2.getClass();
        return false;
    }

    public final void sendExtraDataToClient(ClientId clientId, Bundle bundle) throws RemoteException {
        bundle.getClass();
        for (Map.Entry entry : this.mResponseToClientCallbackMap.entrySet()) {
            ClientId clientId2 = (ClientId) entry.getKey();
            IRemoteCallback iRemoteCallback = (IRemoteCallback) entry.getValue();
            if (clientId == null || Intrinsics.areEqual(clientId, clientId2)) {
                if (iRemoteCallback != null) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(RemoteService.EXTRA_METHOD, RemoteService.METHOD_SEND_EXTRA_DATA);
                    bundle2.putBundle(RemoteService.EXTRA_BUNDLE_DATA, bundle);
                    Unit unit = Unit.INSTANCE;
                    iRemoteCallback.sendResult(bundle2);
                }
            }
        }
    }

    @Override // com.motorola.plugin.core.channel.local.ILocalCallback
    public void sendResult(Bundle bundle) throws RemoteException {
        bundle.getClass();
        IRemoteCallback iRemoteCallback = (IRemoteCallback) this.callbackMap.remove(Integer.valueOf(bundle.getInt(RemoteService.EXTRA_SEQUENCE, -1)));
        if (iRemoteCallback == null) {
            return;
        }
        iRemoteCallback.sendResult(bundle);
    }

    @Override // com.motorola.plugin.core.channel.local.ILocalChannelTransfer
    public void transfer(ClientId clientId, ClientId clientId2, Bundle bundle, ILocalCallback iLocalCallback) {
        clientId.getClass();
        clientId2.getClass();
        bundle.getClass();
        iLocalCallback.getClass();
        this.transfer.transfer(clientId, clientId2, bundle, iLocalCallback);
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer.Default, com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
    public void transferRequest(final RemoteChannelRequestInfo remoteChannelRequestInfo, final int i, IRemoteCallback iRemoteCallback) {
        remoteChannelRequestInfo.getClass();
        iRemoteCallback.getClass();
        this.callbackMap.put(Integer.valueOf(i), iRemoteCallback);
        CompletableFuture.runAsync(new Runnable() { // from class: com.motorola.plugin.core.channel.local.LocalTransferToRemoteTransferAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LocalTransferToRemoteTransferAdapter.m2208transferRequest$lambda2(i, this, remoteChannelRequestInfo);
            }
        });
    }
}
