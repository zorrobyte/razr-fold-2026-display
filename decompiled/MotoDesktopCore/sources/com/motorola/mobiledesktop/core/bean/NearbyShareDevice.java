package com.motorola.mobiledesktop.core.bean;

import E.a;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class NearbyShareDevice implements Parcelable {
    public static final Parcelable.Creator<NearbyShareDevice> CREATOR = new a(10);
    private String displayName;
    private String id;
    private String kind;
    private int status;

    public NearbyShareDevice() {
    }

    public NearbyShareDevice(Parcel parcel) {
        this.displayName = parcel.readString();
        this.id = parcel.readString();
        this.kind = parcel.readString();
        this.status = parcel.readInt();
    }

    public NearbyShareDevice(String str, String str2, String str3, int i2) {
        this.id = str;
        this.displayName = str2;
        this.kind = str3;
        this.status = i2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getId() {
        return this.id;
    }

    public String getKind() {
        return this.kind;
    }

    public int getStatus() {
        return this.status;
    }

    public void readFromParcel(Parcel parcel) {
        this.displayName = parcel.readString();
        this.id = parcel.readString();
        this.kind = parcel.readString();
        this.status = parcel.readInt();
    }

    public void setDisplayName(String str) {
        this.displayName = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setKind(String str) {
        this.kind = str;
    }

    public void setStatus(int i2) {
        this.status = i2;
    }

    public String toString() {
        return "NearbyShareDevice{displayName='" + this.displayName + "', id='" + this.id + "', kind='" + this.kind + "', status=" + this.status + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.displayName);
        parcel.writeString(this.id);
        parcel.writeString(this.kind);
        parcel.writeInt(this.status);
    }
}
