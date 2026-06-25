package com.android.systemui.flags;

import android.os.Parcelable;

/* JADX INFO: compiled from: Flag.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ParcelableFlag extends Parcelable {
    @Override // android.os.Parcelable
    default int describeContents() {
        return 0;
    }
}
