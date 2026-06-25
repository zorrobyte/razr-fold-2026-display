package com.motorola.mobiledesktop.core.uinput;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class AbsSetup implements Parcelable {
    public static final Parcelable.Creator<AbsSetup> CREATOR = new Parcelable.Creator<AbsSetup>() { // from class: com.motorola.mobiledesktop.core.uinput.AbsSetup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AbsSetup createFromParcel(Parcel parcel) {
            AbsSetup absSetup = new AbsSetup();
            absSetup.readFromParcel(parcel);
            return absSetup;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AbsSetup[] newArray(int i2) {
            return new AbsSetup[i2];
        }
    };
    public char code = 0;
    public int value = 0;
    public int minimum = 0;
    public int maximum = 0;
    public int fuzz = 0;
    public int flat = 0;
    public int resolution = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i2 = parcel.readInt();
        try {
            if (i2 < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i2) {
                this.code = (char) parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i2) {
                    this.value = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i2) {
                        this.minimum = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i2) {
                            this.maximum = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i2) {
                                this.fuzz = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i2) {
                                    this.flat = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i2) {
                                        this.resolution = parcel.readInt();
                                        if (iDataPosition > Integer.MAX_VALUE - i2) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                        parcel.setDataPosition(iDataPosition + i2);
                                        return;
                                    }
                                    if (iDataPosition > Integer.MAX_VALUE - i2) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i2);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i2) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i2);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.code);
        parcel.writeInt(this.value);
        parcel.writeInt(this.minimum);
        parcel.writeInt(this.maximum);
        parcel.writeInt(this.fuzz);
        parcel.writeInt(this.flat);
        parcel.writeInt(this.resolution);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }
}
