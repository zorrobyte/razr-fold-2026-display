package com.motorola.mobiledesktop.core.uinput;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class InputSetup implements Parcelable {
    public static final Parcelable.Creator<InputSetup> CREATOR = new Parcelable.Creator<InputSetup>() { // from class: com.motorola.mobiledesktop.core.uinput.InputSetup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputSetup createFromParcel(Parcel parcel) {
            InputSetup inputSetup = new InputSetup();
            inputSetup.readFromParcel(parcel);
            return inputSetup;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputSetup[] newArray(int i2) {
            return new InputSetup[i2];
        }
    };
    public InputId inputId;
    public byte[] name;

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.inputId);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i2 = parcel.readInt();
        try {
            if (i2 < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i2) {
                this.inputId = (InputId) parcel.readTypedObject(InputId.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i2) {
                    this.name = (byte[]) parcel.createFixedArray(byte[].class, 80);
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
        parcel.writeTypedObject(this.inputId, i2);
        parcel.writeFixedArray(this.name, i2, 80);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }
}
