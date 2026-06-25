package com.motorola.mobiledesktop.core.bean;

import E.a;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class AudioSettingItemInfo implements Parcelable {
    public static final Parcelable.Creator<AudioSettingItemInfo> CREATOR = new a(3);
    private int checkBoxState;
    private String checkboxInfo;
    private int currentVolume;
    private String deviceSubInfo;
    private int iconId;
    private int index;
    private boolean isForceDevice;
    private int maxVolume;
    private String name;
    private String subTitle;
    private String title;
    private int titleIconId;

    public AudioSettingItemInfo() {
    }

    public AudioSettingItemInfo(Parcel parcel) {
        this.index = parcel.readInt();
        this.title = parcel.readString();
        this.subTitle = parcel.readString();
        this.titleIconId = parcel.readInt();
        this.iconId = parcel.readInt();
        this.name = parcel.readString();
        this.deviceSubInfo = parcel.readString();
        this.isForceDevice = parcel.readByte() != 0;
        this.maxVolume = parcel.readInt();
        this.currentVolume = parcel.readInt();
        this.checkboxInfo = parcel.readString();
        this.checkBoxState = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCheckBoxState() {
        return this.checkBoxState;
    }

    public String getCheckboxInfo() {
        return this.checkboxInfo;
    }

    public int getCurrentVolume() {
        return this.currentVolume;
    }

    public String getDeviceSubInfo() {
        return this.deviceSubInfo;
    }

    public int getIconId() {
        return this.iconId;
    }

    public int getIndex() {
        return this.index;
    }

    public int getMaxVolume() {
        return this.maxVolume;
    }

    public String getName() {
        return this.name;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public String getTitle() {
        return this.title;
    }

    public int getTitleIconId() {
        return this.titleIconId;
    }

    public boolean isForceDevice() {
        return this.isForceDevice;
    }

    public void setCheckBoxState(int i2) {
        this.checkBoxState = i2;
    }

    public void setCheckboxInfo(String str) {
        this.checkboxInfo = str;
    }

    public void setCurrentVolume(int i2) {
        this.currentVolume = i2;
    }

    public void setDeviceSubInfo(String str) {
        this.deviceSubInfo = str;
    }

    public void setForceDevice(boolean z2) {
        this.isForceDevice = z2;
    }

    public void setIconId(int i2) {
        this.iconId = i2;
    }

    public void setIndex(int i2) {
        this.index = i2;
    }

    public void setMaxVolume(int i2) {
        this.maxVolume = i2;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setSubTitle(String str) {
        this.subTitle = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setTitleIconId(int i2) {
        this.titleIconId = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.index);
        parcel.writeString(this.title);
        parcel.writeString(this.subTitle);
        parcel.writeInt(this.titleIconId);
        parcel.writeInt(this.iconId);
        parcel.writeString(this.name);
        parcel.writeString(this.deviceSubInfo);
        parcel.writeByte(this.isForceDevice ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.maxVolume);
        parcel.writeInt(this.currentVolume);
        parcel.writeString(this.checkboxInfo);
        parcel.writeInt(this.checkBoxState);
    }
}
