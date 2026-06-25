package com.motorola.mobiledesktop.core.bean;

import E.a;
import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Display;
import android.view.DisplayAddress;

/* JADX INFO: loaded from: classes.dex */
public class MotoDisplay implements Parcelable {

    @NonNull
    public static final Parcelable.Creator<MotoDisplay> CREATOR = new a(5);
    public final String address;
    public final int displayId;
    public final int flags;
    public final String ownerPackageName;
    public final int ownerUid;
    public final int type;

    private MotoDisplay(Parcel parcel) {
        this.displayId = parcel.readInt();
        this.flags = parcel.readInt();
        this.type = parcel.readInt();
        this.address = parcel.readString();
        this.ownerUid = parcel.readInt();
        this.ownerPackageName = parcel.readString();
    }

    public /* synthetic */ MotoDisplay(Parcel parcel, int i2) {
        this(parcel);
    }

    public MotoDisplay(Display display) {
        this.displayId = display.getDisplayId();
        this.flags = display.getFlags();
        this.type = display.getType();
        DisplayAddress address = display.getAddress();
        this.address = address != null ? address.toString() : null;
        this.ownerUid = display.getOwnerUid();
        this.ownerPackageName = display.getOwnerPackageName();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.displayId);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.type);
        parcel.writeString(this.address);
        parcel.writeInt(this.ownerUid);
        parcel.writeString(this.ownerPackageName);
    }
}
