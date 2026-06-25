package com.motorola.plugin.core.channel.local;

import android.os.Bundle;
import com.motorola.plugin.sdk.channel.ClientId;

/* JADX INFO: compiled from: ILocalChannelTransfer.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ILocalChannelTransfer {
    void transfer(ClientId clientId, ClientId clientId2, Bundle bundle, ILocalCallback iLocalCallback);
}
