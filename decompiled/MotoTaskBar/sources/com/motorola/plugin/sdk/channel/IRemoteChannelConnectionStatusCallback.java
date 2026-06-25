package com.motorola.plugin.sdk.channel;

/* JADX INFO: loaded from: classes2.dex */
public interface IRemoteChannelConnectionStatusCallback {
    default void onRemoteChannelDisconnected() {
    }

    void onRemoteChannelReconnected();
}
