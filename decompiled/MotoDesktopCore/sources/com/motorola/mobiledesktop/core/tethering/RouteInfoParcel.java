package com.motorola.mobiledesktop.core.tethering;

import E.a;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class RouteInfoParcel implements Parcelable {
    public static final Parcelable.Creator<RouteInfoParcel> CREATOR = new a(15);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IpPrefixParcel f2346a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public byte[] f2347b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public String f2348c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f2349d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f2350e;

    @Override // android.os.Parcelable
    public final int describeContents() {
        IpPrefixParcel ipPrefixParcel = this.f2346a;
        if (ipPrefixParcel == null) {
            return 0;
        }
        return ipPrefixParcel.describeContents();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        IpPrefixParcel ipPrefixParcel = this.f2346a;
        if (ipPrefixParcel != null) {
            parcel.writeInt(1);
            ipPrefixParcel.writeToParcel(parcel, i2);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeByteArray(this.f2347b);
        parcel.writeString(this.f2348c);
        parcel.writeInt(this.f2349d);
        parcel.writeInt(this.f2350e);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }
}
