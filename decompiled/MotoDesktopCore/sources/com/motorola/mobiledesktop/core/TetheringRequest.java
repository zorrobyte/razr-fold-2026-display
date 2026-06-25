package com.motorola.mobiledesktop.core;

import E.a;
import android.net.LinkAddress;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class TetheringRequest implements Parcelable {
    public static final Parcelable.Creator<TetheringRequest> CREATOR = new a(1);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2301a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public LinkAddress f2302b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public LinkAddress f2303c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f2304d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f2305e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f2306f;

    @Override // android.os.Parcelable
    public final int describeContents() {
        LinkAddress linkAddress = this.f2302b;
        int iDescribeContents = linkAddress == null ? 0 : linkAddress.describeContents();
        LinkAddress linkAddress2 = this.f2303c;
        return iDescribeContents | (linkAddress2 != null ? linkAddress2.describeContents() : 0);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.f2301a);
        LinkAddress linkAddress = this.f2302b;
        if (linkAddress != null) {
            parcel.writeInt(1);
            linkAddress.writeToParcel(parcel, i2);
        } else {
            parcel.writeInt(0);
        }
        LinkAddress linkAddress2 = this.f2303c;
        if (linkAddress2 != null) {
            parcel.writeInt(1);
            linkAddress2.writeToParcel(parcel, i2);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.f2304d ? 1 : 0);
        parcel.writeInt(this.f2305e ? 1 : 0);
        parcel.writeInt(this.f2306f);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }
}
