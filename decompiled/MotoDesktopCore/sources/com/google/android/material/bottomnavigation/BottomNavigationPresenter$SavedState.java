package com.google.android.material.bottomnavigation;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
class BottomNavigationPresenter$SavedState implements Parcelable {
    public static final Parcelable.Creator<BottomNavigationPresenter$SavedState> CREATOR = new a();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2105a;

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f2105a);
    }
}
