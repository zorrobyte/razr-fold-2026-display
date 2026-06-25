package com.motorola.mobiledesktop.core.tethering;

import E.a;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class InterfaceConfigurationParcel implements Parcelable {
    public static final Parcelable.Creator<InterfaceConfigurationParcel> CREATOR = new a(13);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public String f2339a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public String f2340b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public String f2341c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f2342d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public String[] f2343e;

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.f2339a);
        parcel.writeString(this.f2340b);
        parcel.writeString(this.f2341c);
        parcel.writeInt(this.f2342d);
        parcel.writeStringArray(this.f2343e);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }
}
