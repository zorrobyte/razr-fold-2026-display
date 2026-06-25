package com.motorola.mobiledesktop.core.bean;

import E.a;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class AudioSettingInfo implements Parcelable {
    public static final Parcelable.Creator<AudioSettingInfo> CREATOR = new a(2);
    private AudioSettingItemInfo headItem;
    private ArrayList<AudioSettingItemInfo> itemInfoList;

    public AudioSettingInfo() {
    }

    public AudioSettingInfo(Parcel parcel) {
        this.headItem = (AudioSettingItemInfo) parcel.readParcelable(AudioSettingItemInfo.class.getClassLoader());
        this.itemInfoList = parcel.createTypedArrayList(AudioSettingItemInfo.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AudioSettingItemInfo getHeadItem() {
        return this.headItem;
    }

    public ArrayList<AudioSettingItemInfo> getItemInfoList() {
        return this.itemInfoList;
    }

    public void setHeadItem(AudioSettingItemInfo audioSettingItemInfo) {
        this.headItem = audioSettingItemInfo;
    }

    public void setItemInfoList(ArrayList<AudioSettingItemInfo> arrayList) {
        this.itemInfoList = arrayList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.headItem, i2);
        parcel.writeTypedList(this.itemInfoList);
    }
}
