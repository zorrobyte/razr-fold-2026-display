package com.motorola.plugin.sdk.channel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public class RemoteChannelRequestInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo.1
        @Override // android.os.Parcelable.Creator
        public RemoteChannelRequestInfo createFromParcel(Parcel parcel) {
            return new RemoteChannelRequestInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public RemoteChannelRequestInfo[] newArray(int i) {
            return new RemoteChannelRequestInfo[i];
        }
    };
    public final Bundle bundle;
    public final ClientId callingClientId;
    public final Intent origIntent;
    public final ClientId pluginId;

    public RemoteChannelRequestInfo(Intent intent, ClientId clientId, ClientId clientId2, Bundle bundle) {
        this.origIntent = intent;
        this.callingClientId = clientId;
        this.pluginId = clientId2;
        this.bundle = bundle;
    }

    private RemoteChannelRequestInfo(Parcel parcel) {
        this.origIntent = (Intent) parcel.readParcelable(Intent.class.getClassLoader());
        this.callingClientId = (ClientId) parcel.readParcelable(ClientId.class.getClassLoader());
        this.pluginId = (ClientId) parcel.readParcelable(ClientId.class.getClassLoader());
        this.bundle = parcel.readBundle(getClass().getClassLoader());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.bundle.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.origIntent, i);
        parcel.writeParcelable(this.callingClientId, i);
        parcel.writeParcelable(this.pluginId, i);
        parcel.writeBundle(this.bundle);
    }
}
