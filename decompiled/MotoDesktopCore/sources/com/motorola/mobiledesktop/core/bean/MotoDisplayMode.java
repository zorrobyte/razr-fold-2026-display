package com.motorola.mobiledesktop.core.bean;

import E.a;
import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class MotoDisplayMode implements Parcelable {

    @NonNull
    public static final Parcelable.Creator<MotoDisplayMode> CREATOR = new a(6);
    public final float fps;
    public final int height;
    public final int width;

    public MotoDisplayMode(int i2, int i3, float f2) {
        this.width = i2;
        this.height = i3;
        this.fps = f2;
    }

    private MotoDisplayMode(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readFloat());
    }

    public /* synthetic */ MotoDisplayMode(Parcel parcel, int i2) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MotoDisplayMode)) {
            return false;
        }
        MotoDisplayMode motoDisplayMode = (MotoDisplayMode) obj;
        return matches(motoDisplayMode.width, motoDisplayMode.height, motoDisplayMode.fps);
    }

    public int hashCode() {
        return Float.floatToIntBits(this.fps) + ((((this.width + 17) * 17) + this.height) * 17);
    }

    public boolean matches(int i2, int i3, float f2) {
        return this.width == i2 && this.height == i3 && Float.floatToIntBits(this.fps) == Float.floatToIntBits(f2);
    }

    public String toString() {
        return "{, width=" + this.width + ", height=" + this.height + ", fps=" + this.fps + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeFloat(this.fps);
    }
}
