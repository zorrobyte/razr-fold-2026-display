package com.motorola.taskbar;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopQSTileData implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.taskbar.DesktopQSTileData.1
        @Override // android.os.Parcelable.Creator
        public DesktopQSTileData createFromParcel(Parcel parcel) {
            return new DesktopQSTileData(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public DesktopQSTileData[] newArray(int i) {
            return new DesktopQSTileData[i];
        }
    };
    public boolean activityIn;
    public boolean activityOut;
    public int extra;
    public int state;
    public int type;
    public boolean value;
    public String label = "label";
    public String secondaryLabel = "secondaryLabel";
    public String contentDescription = "contentDescription";
    public String stateDescription = "";
    public boolean isTransient = false;
    public boolean isConnected = false;

    public enum QSTileType {
        WIFI,
        MOBILE_DATA,
        BLUETOOTH,
        DO_NOT_DISTURB,
        HOTSPOT,
        AIRPLANE_MODE,
        SCREENSHOT,
        SCREEN_RECORD
    }

    public DesktopQSTileData(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isOn() {
        return this.value;
    }

    public void readFromParcel(Parcel parcel) {
        this.type = parcel.readInt();
        this.value = parcel.readBoolean();
        this.state = parcel.readInt();
        this.label = parcel.readString();
        this.secondaryLabel = parcel.readString();
        this.contentDescription = parcel.readString();
        this.stateDescription = parcel.readString();
        this.isTransient = parcel.readBoolean();
        this.isConnected = parcel.readBoolean();
        this.activityIn = parcel.readBoolean();
        this.activityOut = parcel.readBoolean();
        this.extra = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeBoolean(this.value);
        parcel.writeInt(this.state);
        parcel.writeString(this.label);
        parcel.writeString(this.secondaryLabel);
        parcel.writeString(this.contentDescription);
        parcel.writeString(this.stateDescription);
        parcel.writeBoolean(this.isTransient);
        parcel.writeBoolean(this.isConnected);
        parcel.writeBoolean(this.activityIn);
        parcel.writeBoolean(this.activityOut);
        parcel.writeInt(this.extra);
    }
}
