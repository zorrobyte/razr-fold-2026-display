package com.motorola.mobiledesktop.core.tethering;

import E.a;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class IpPrefixParcel implements Parcelable {
    public static final Parcelable.Creator<IpPrefixParcel> CREATOR = new a(14);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public byte[] f2344a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2345b;

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.f2344a);
        parcel.writeInt(this.f2345b);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }
}
