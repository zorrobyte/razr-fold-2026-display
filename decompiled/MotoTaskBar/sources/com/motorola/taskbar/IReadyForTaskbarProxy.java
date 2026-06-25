package com.motorola.taskbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface IReadyForTaskbarProxy extends IInterface {

    public abstract class Stub extends Binder implements IReadyForTaskbarProxy {
        public Stub() {
            attachInterface(this, "com.motorola.taskbar.IReadyForTaskbarProxy");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.motorola.taskbar.IReadyForTaskbarProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.motorola.taskbar.IReadyForTaskbarProxy");
                return true;
            }
            if (i == 1) {
                int iAidlVersion = aidlVersion();
                parcel2.writeNoException();
                parcel2.writeInt(iAidlVersion);
            } else if (i == 2) {
                String string = parcel.readString();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDisplayModeSelected(string, i3);
                parcel2.writeNoException();
            } else if (i == 3) {
                String string2 = parcel.readString();
                int i4 = parcel.readInt();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onModeChooserAnimationStateChanged(string2, i4, z);
                parcel2.writeNoException();
            } else if (i == 4) {
                int i5 = parcel.readInt();
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onModeChooserShowingStateChanged(i5, z2);
                parcel2.writeNoException();
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int i6 = parcel.readInt();
                boolean z3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                requestQSNotificationPanel(i6, z3);
                parcel2.writeNoException();
            }
            return true;
        }
    }

    int aidlVersion();

    void onDisplayModeSelected(String str, int i);

    void onModeChooserAnimationStateChanged(String str, int i, boolean z);

    void onModeChooserShowingStateChanged(int i, boolean z);

    void requestQSNotificationPanel(int i, boolean z);
}
