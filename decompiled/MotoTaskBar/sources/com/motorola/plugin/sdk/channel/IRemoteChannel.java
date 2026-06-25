package com.motorola.plugin.sdk.channel;

import android.os.Bundle;

/* JADX INFO: loaded from: classes2.dex */
public interface IRemoteChannel {

    public interface IOnRemoteChannelTransferCallback extends IOnRemoteResponse, IOnRemoteError {
    }

    @FunctionalInterface
    public interface IOnRemoteError {
        void onRemoteError(Throwable th);
    }

    @FunctionalInterface
    public interface IOnRemoteResponse {
        void onRemoteResponse(Bundle bundle);
    }

    IDataSetChangedRegistry getDataSetChangedRegistry();

    boolean isClosed();

    void requestClearPluginData(boolean z);

    void subscribeConnectStatus(IRemoteChannelConnectionStatusCallback iRemoteChannelConnectionStatusCallback);

    default void transfer(Bundle bundle, IOnRemoteChannelTransferCallback iOnRemoteChannelTransferCallback) {
        transfer(bundle, iOnRemoteChannelTransferCallback, iOnRemoteChannelTransferCallback);
    }

    void transfer(Bundle bundle, IOnRemoteError iOnRemoteError, IOnRemoteResponse iOnRemoteResponse);

    default void transfer(Bundle bundle, IOnRemoteResponse iOnRemoteResponse) {
        transfer(bundle, null, iOnRemoteResponse);
    }

    default void transfer(String str, IOnRemoteChannelTransferCallback iOnRemoteChannelTransferCallback) {
        Bundle bundle = new Bundle();
        bundle.putString(RemoteService.EXTRA_METHOD, str);
        transfer(bundle, iOnRemoteChannelTransferCallback);
    }

    default void transfer(String str, IOnRemoteResponse iOnRemoteResponse) {
        Bundle bundle = new Bundle();
        bundle.putString(RemoteService.EXTRA_METHOD, str);
        transfer(bundle, iOnRemoteResponse);
    }

    void unsubscribeConnectStatus(IRemoteChannelConnectionStatusCallback iRemoteChannelConnectionStatusCallback);
}
