package com.motorola.plugin.sdk.channel;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public class ClientId implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.plugin.sdk.channel.ClientId.1
        @Override // android.os.Parcelable.Creator
        public ClientId createFromParcel(Parcel parcel) {
            return new ClientId(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ClientId[] newArray(int i) {
            return new ClientId[i];
        }
    };
    public final String packageName;
    public final String uniqueId;

    protected ClientId(Parcel parcel) {
        this.packageName = parcel.readString();
        this.uniqueId = parcel.readString();
    }

    public ClientId(String str, String str2) {
        this.packageName = str;
        this.uniqueId = str2;
    }

    public ClientId copy() {
        return new ClientId(this.packageName, this.uniqueId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "Identity:" + this.uniqueId + "|" + this.packageName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.uniqueId);
    }
}
