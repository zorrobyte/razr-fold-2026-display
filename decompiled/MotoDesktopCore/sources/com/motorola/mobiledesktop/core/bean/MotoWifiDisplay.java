package com.motorola.mobiledesktop.core.bean;

import E.a;
import android.hardware.display.WifiDisplay;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class MotoWifiDisplay implements Parcelable {
    public static final Parcelable.Creator<MotoWifiDisplay> CREATOR = new a(9);
    public static final int LIST_TYPE_FOUND_DEVICES = 2;
    public static final int LIST_TYPE_NOT_TITLE = 0;
    public static final int LIST_TYPE_SAVED_DEVICES = 1;
    public static final int NEARBY_DISPLAY_TYPE_CHROMECAST = 2;
    public static final int NEARBY_DISPLAY_TYPE_DONGLE = 1;
    public static final int NEARBY_DISPLAY_TYPE_WIFI_DISPLAY = 0;
    public int mActiveDisplayState;
    public boolean mCanConnect;
    public String mDeviceAddress;
    public String mDeviceAlias;
    public String mDeviceName;
    public boolean mIsAvailable;
    public boolean mIsChromecast;
    public boolean mIsMotoDongle;
    public boolean mIsRemembered;
    public boolean mIsTitle;
    public int mListType;

    public MotoWifiDisplay() {
        this.mIsTitle = false;
        this.mListType = 0;
    }

    public MotoWifiDisplay(Parcel parcel) {
        this.mIsTitle = false;
        this.mListType = 0;
        this.mIsTitle = parcel.readByte() != 0;
        this.mListType = parcel.readInt();
        this.mDeviceAddress = parcel.readString();
        this.mDeviceName = parcel.readString();
        this.mDeviceAlias = parcel.readString();
        this.mIsAvailable = parcel.readByte() != 0;
        this.mCanConnect = parcel.readByte() != 0;
        this.mIsRemembered = parcel.readByte() != 0;
        this.mActiveDisplayState = parcel.readInt();
        byte b2 = parcel.readByte();
        if (b2 == 1) {
            this.mIsMotoDongle = true;
        } else if (b2 == 2) {
            this.mIsChromecast = true;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setWifidisplay(WifiDisplay wifiDisplay) {
        if (wifiDisplay == null) {
            return;
        }
        this.mDeviceAddress = wifiDisplay.getDeviceAddress();
        this.mDeviceName = wifiDisplay.getDeviceName();
        this.mDeviceAlias = wifiDisplay.getDeviceAlias();
        this.mIsAvailable = wifiDisplay.isAvailable();
        this.mCanConnect = wifiDisplay.canConnect();
        this.mIsRemembered = wifiDisplay.isRemembered();
    }

    public String toString() {
        return "MotoWifiDisplay{mIsTitle='" + this.mIsTitle + "'mListType='" + this.mListType + "'mDeviceAddress='" + this.mDeviceAddress + "', mDeviceName='" + this.mDeviceName + "', mDeviceAlias='" + this.mDeviceAlias + "', mIsAvailable=" + this.mIsAvailable + ", mCanConnect=" + this.mCanConnect + ", mIsRemembered=" + this.mIsRemembered + ", mActiveDisplayState=" + this.mActiveDisplayState + ", mIsMotoDongle=" + this.mIsMotoDongle + ", mIsChromecast=" + this.mIsChromecast + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeByte(this.mIsTitle ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mListType);
        parcel.writeString(this.mDeviceAddress);
        parcel.writeString(this.mDeviceName);
        parcel.writeString(this.mDeviceAlias);
        parcel.writeByte(this.mIsAvailable ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mCanConnect ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mIsRemembered ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mActiveDisplayState);
        parcel.writeByte(this.mIsMotoDongle ? (byte) 1 : this.mIsChromecast ? (byte) 2 : (byte) 0);
    }
}
