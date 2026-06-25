package com.motorola.mobiledesktop.core.uinput;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class InputId implements Parcelable {
    public static final Parcelable.Creator<InputId> CREATOR = new Parcelable.Creator<InputId>() { // from class: com.motorola.mobiledesktop.core.uinput.InputId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputId createFromParcel(Parcel parcel) {
            InputId inputId = new InputId();
            inputId.readFromParcel(parcel);
            return inputId;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputId[] newArray(int i2) {
            return new InputId[i2];
        }
    };
    public char busType = 0;
    public char vendor = 0;
    public char product = 0;
    public char version = 0;

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
                this.busType = (char) parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i2) {
                    this.vendor = (char) parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i2) {
                        this.product = (char) parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i2) {
                            this.version = (char) parcel.readInt();
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
        parcel.writeInt(this.busType);
        parcel.writeInt(this.vendor);
        parcel.writeInt(this.product);
        parcel.writeInt(this.version);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }
}
