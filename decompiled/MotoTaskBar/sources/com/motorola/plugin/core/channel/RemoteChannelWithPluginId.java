package com.motorola.plugin.core.channel;

import android.os.Bundle;
import com.motorola.plugin.sdk.channel.ClientId;
import com.motorola.plugin.sdk.channel.IDataSetChangedRegistry;
import com.motorola.plugin.sdk.channel.IRemoteChannel;
import com.motorola.plugin.sdk.channel.IRemoteChannelConnectionStatusCallback;

/* JADX INFO: compiled from: RemoteChannelWithPluginId.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RemoteChannelWithPluginId implements IRemoteChannel {
    private final ClientId pluginId;
    private final IRemoteChannelExtension remoteChannel;

    public RemoteChannelWithPluginId(ClientId clientId, IRemoteChannelExtension iRemoteChannelExtension) {
        clientId.getClass();
        iRemoteChannelExtension.getClass();
        this.pluginId = clientId;
        this.remoteChannel = iRemoteChannelExtension;
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public IDataSetChangedRegistry getDataSetChangedRegistry() {
        return this.remoteChannel.getDataSetChangedRegistry();
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public boolean isClosed() {
        return this.remoteChannel.isClosed();
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public void requestClearPluginData(boolean z) {
        this.remoteChannel.requestClearPluginData(z);
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public void subscribeConnectStatus(IRemoteChannelConnectionStatusCallback iRemoteChannelConnectionStatusCallback) {
        this.remoteChannel.subscribeConnectStatus(iRemoteChannelConnectionStatusCallback);
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public void transfer(Bundle bundle, IRemoteChannel.IOnRemoteError iOnRemoteError, IRemoteChannel.IOnRemoteResponse iOnRemoteResponse) {
        bundle.getClass();
        iOnRemoteResponse.getClass();
        bundle.putParcelable(this.remoteChannel.getToken(), this.pluginId);
        this.remoteChannel.transfer(bundle, iOnRemoteError, iOnRemoteResponse);
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public void unsubscribeConnectStatus(IRemoteChannelConnectionStatusCallback iRemoteChannelConnectionStatusCallback) {
        this.remoteChannel.unsubscribeConnectStatus(iRemoteChannelConnectionStatusCallback);
    }
}
