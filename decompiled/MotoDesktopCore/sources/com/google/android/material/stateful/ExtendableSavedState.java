package com.google.android.material.stateful;

import P.b;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.customview.view.AbsSavedState;
import h.AbstractC0137d;
import h.k;

/* JADX INFO: loaded from: classes.dex */
public class ExtendableSavedState extends AbsSavedState {
    public static final Parcelable.Creator<ExtendableSavedState> CREATOR = new b(1);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final k f2194c;

    public ExtendableSavedState(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        int i2 = parcel.readInt();
        String[] strArr = new String[i2];
        parcel.readStringArray(strArr);
        Bundle[] bundleArr = new Bundle[i2];
        parcel.readTypedArray(bundleArr, Bundle.CREATOR);
        k kVar = new k();
        if (i2 == 0) {
            kVar.f2609a = AbstractC0137d.f2581a;
            kVar.f2610b = AbstractC0137d.f2582b;
        } else {
            kVar.a(i2);
        }
        kVar.f2611c = 0;
        this.f2194c = kVar;
        for (int i3 = 0; i3 < i2; i3++) {
            this.f2194c.put(strArr[i3], bundleArr[i3]);
        }
    }

    public ExtendableSavedState(Parcelable parcelable) {
        super(parcelable);
        this.f2194c = new k();
    }

    public final String toString() {
        return "ExtendableSavedState{" + Integer.toHexString(System.identityHashCode(this)) + " states=" + this.f2194c + "}";
    }

    @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        k kVar = this.f2194c;
        int i3 = kVar.f2611c;
        parcel.writeInt(i3);
        String[] strArr = new String[i3];
        Bundle[] bundleArr = new Bundle[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            strArr[i4] = (String) kVar.h(i4);
            bundleArr[i4] = (Bundle) kVar.j(i4);
        }
        parcel.writeStringArray(strArr);
        parcel.writeTypedArray(bundleArr, 0);
    }
}
