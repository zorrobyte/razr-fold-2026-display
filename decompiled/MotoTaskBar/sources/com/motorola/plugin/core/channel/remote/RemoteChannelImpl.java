package com.motorola.plugin.core.channel.remote;

import android.content.Intent;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.channel.AbstractCommonChannelImpl;
import com.motorola.plugin.core.channel.ChannelParams;
import com.motorola.plugin.sdk.channel.ClientId;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: RemoteChannelImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RemoteChannelImpl extends AbstractCommonChannelImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemoteChannelImpl(ChannelParams channelParams) {
        super(PluginConfigKt.TAG_DL_CHANNEL, channelParams);
        channelParams.getClass();
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
        PluginConfigKt.trace$default(PluginConfigKt.TAG_DL_CHANNEL, Level.ERROR, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.remote.RemoteChannelImpl.getChannelTransferLocked.2
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + RemoteChannelImpl.this.getToken() + "] remote plugin using channel was deprecated, please fetch the data in your plugin instance directly";
            }
        }, 60, null);
        throw new UnsupportedOperationException("remote plugin using channel was deprecated, please fetch the data in your plugin instance directly");
    }
}
