package com.motorola.mobiledesktop.core.bean;

import E.a;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class MotoActivityInfo implements Parcelable {
    public static final Parcelable.Creator<MotoActivityInfo> CREATOR = new a(4);
    private String activityInfoLable;
    private String name;
    private String packageName;

    public MotoActivityInfo() {
    }

    public MotoActivityInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.name = parcel.readString();
        this.activityInfoLable = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getActivityInfoLable() {
        return this.activityInfoLable;
    }

    public String getName() {
        return this.name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setActivityInfoLable(String str) {
        this.activityInfoLable = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public String toString() {
        return "MotoActivityInfo{packageName='" + this.packageName + "', name='" + this.name + "', activityInfoLable='" + this.activityInfoLable + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.name);
        parcel.writeString(this.activityInfoLable);
    }
}
