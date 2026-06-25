package com.google.android.material.bottomnavigation;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class a implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        BottomNavigationPresenter$SavedState bottomNavigationPresenter$SavedState = new BottomNavigationPresenter$SavedState();
        bottomNavigationPresenter$SavedState.f2105a = parcel.readInt();
        return bottomNavigationPresenter$SavedState;
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i2) {
        return new BottomNavigationPresenter$SavedState[i2];
    }
}
