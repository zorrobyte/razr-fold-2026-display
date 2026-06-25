package com.motorola.mobiledesktop.core.bean;

import E.a;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class RfDragShadow implements Parcelable {
    public static final Parcelable.Creator<RfDragShadow> CREATOR = new a(11);
    private Bitmap mBitmap;
    private int mHeight;
    private int mShadowTouchX;
    private int mShadowTouchY;
    private int mWidth;

    public RfDragShadow(int i2, int i3, int i4, int i5, Bitmap bitmap) {
        this.mWidth = i2;
        this.mHeight = i3;
        this.mShadowTouchX = i4;
        this.mShadowTouchY = i5;
        this.mBitmap = bitmap;
    }

    private RfDragShadow(Parcel parcel) {
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mShadowTouchX = parcel.readInt();
        this.mShadowTouchY = parcel.readInt();
        this.mBitmap = (Bitmap) parcel.readParcelable(Bitmap.class.getClassLoader());
    }

    public /* synthetic */ RfDragShadow(Parcel parcel, int i2) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Bitmap getmBitmap() {
        return this.mBitmap;
    }

    public int getmHeight() {
        return this.mHeight;
    }

    public int getmShadowTouchX() {
        return this.mShadowTouchX;
    }

    public int getmShadowTouchY() {
        return this.mShadowTouchY;
    }

    public int getmWidth() {
        return this.mWidth;
    }

    public void setmBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public void setmHeight(int i2) {
        this.mHeight = i2;
    }

    public void setmShadowTouchX(int i2) {
        this.mShadowTouchX = i2;
    }

    public void setmShadowTouchY(int i2) {
        this.mShadowTouchY = i2;
    }

    public void setmWidth(int i2) {
        this.mWidth = i2;
    }

    public String toString() {
        return "DragShadow{mWidth=" + this.mWidth + ", mHeight=" + this.mHeight + ", mShadowTouchX=" + this.mShadowTouchX + ", mShadowTouchY=" + this.mShadowTouchY + ", mBitmap=" + this.mBitmap + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mShadowTouchX);
        parcel.writeInt(this.mShadowTouchY);
        parcel.writeParcelable(this.mBitmap, i2);
    }
}
