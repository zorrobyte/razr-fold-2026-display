package com.motorola.mobiledesktop.core.uinput;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class InputEvent implements Parcelable {
    public static final Parcelable.Creator<InputEvent> CREATOR = new Parcelable.Creator<InputEvent>() { // from class: com.motorola.mobiledesktop.core.uinput.InputEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputEvent createFromParcel(Parcel parcel) {
            InputEvent inputEvent = new InputEvent();
            inputEvent.readFromParcel(parcel);
            return inputEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputEvent[] newArray(int i2) {
            return new InputEvent[i2];
        }
    };
    public int type;
    public char code = 0;
    public int value = 0;

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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i2) {
                    this.code = (char) parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i2) {
                        this.value = parcel.readInt();
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
        parcel.writeInt(this.type);
        parcel.writeInt(this.code);
        parcel.writeInt(this.value);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }
}
