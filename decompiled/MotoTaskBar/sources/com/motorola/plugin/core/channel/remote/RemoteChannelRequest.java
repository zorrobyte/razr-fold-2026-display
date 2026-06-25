package com.motorola.plugin.core.channel.remote;

import android.content.Intent;
import com.motorola.plugin.sdk.channel.ClientId;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RemoteChannelRequest.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RemoteChannelRequest {
    private final ClientId callingClientId;
    private final Intent origIntent;
    private final String token;

    public RemoteChannelRequest(Intent intent, ClientId clientId, String str) {
        intent.getClass();
        clientId.getClass();
        str.getClass();
        this.origIntent = intent;
        this.callingClientId = clientId;
        this.token = str;
    }

    public static /* synthetic */ RemoteChannelRequest copy$default(RemoteChannelRequest remoteChannelRequest, Intent intent, ClientId clientId, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            intent = remoteChannelRequest.origIntent;
        }
        if ((i & 2) != 0) {
            clientId = remoteChannelRequest.callingClientId;
        }
        if ((i & 4) != 0) {
            str = remoteChannelRequest.token;
        }
        return remoteChannelRequest.copy(intent, clientId, str);
    }

    public final Intent component1() {
        return this.origIntent;
    }

    public final ClientId component2() {
        return this.callingClientId;
    }

    public final String component3() {
        return this.token;
    }

    public final RemoteChannelRequest copy(Intent intent, ClientId clientId, String str) {
        intent.getClass();
        clientId.getClass();
        str.getClass();
        return new RemoteChannelRequest(intent, clientId, str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RemoteChannelRequest)) {
            return false;
        }
        RemoteChannelRequest remoteChannelRequest = (RemoteChannelRequest) obj;
        return Intrinsics.areEqual(this.origIntent, remoteChannelRequest.origIntent) && Intrinsics.areEqual(this.callingClientId, remoteChannelRequest.callingClientId) && Intrinsics.areEqual(this.token, remoteChannelRequest.token);
    }

    public final ClientId getCallingClientId() {
        return this.callingClientId;
    }

    public final Intent getOrigIntent() {
        return this.origIntent;
    }

    public final String getToken() {
        return this.token;
    }

    public int hashCode() {
        return (((this.origIntent.hashCode() * 31) + this.callingClientId.hashCode()) * 31) + this.token.hashCode();
    }

    public String toString() {
        return "RemoteChannelRequest(origIntent=" + this.origIntent + ", callingClientId=" + this.callingClientId + ", token=" + this.token + ')';
    }
}
