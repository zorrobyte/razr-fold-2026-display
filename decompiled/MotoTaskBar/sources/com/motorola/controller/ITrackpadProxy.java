package com.motorola.controller;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface ITrackpadProxy extends IInterface {

    public abstract class Stub extends Binder implements ITrackpadProxy {
        public Stub() {
            attachInterface(this, "com.motorola.controller.ITrackpadProxy");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.motorola.controller.ITrackpadProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.motorola.controller.ITrackpadProxy");
                return true;
            }
            if (i == 1) {
                requestBackToHome();
                parcel2.writeNoException();
            } else if (i == 2) {
                requestShowDesktopSplashScreen();
                parcel2.writeNoException();
            } else if (i == 3) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                handleGesture(i3);
                parcel2.writeNoException();
            } else if (i == 4) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onTrackpadStateChanged(z);
                parcel2.writeNoException();
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                requestNavTrackpadGuide(z2);
                parcel2.writeNoException();
            }
            return true;
        }
    }

    void handleGesture(int i);

    void onTrackpadStateChanged(boolean z);

    void requestBackToHome();

    void requestNavTrackpadGuide(boolean z);

    void requestShowDesktopSplashScreen();
}
