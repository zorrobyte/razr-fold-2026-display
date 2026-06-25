package com.motorola.taskbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface IMainUserTaskBarProxy extends IInterface {

    public abstract class Stub extends Binder implements IMainUserTaskBarProxy {

        class Proxy implements IMainUserTaskBarProxy {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.motorola.taskbar.IMainUserTaskBarProxy
            public void requestShowDesktopSplashScreenFromSecondUser() {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.IMainUserTaskBarProxy");
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.IMainUserTaskBarProxy
            public void startTrackpadActivityFromSecondUser(int i, boolean z) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.IMainUserTaskBarProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.IMainUserTaskBarProxy
            public void updateDisplayChooserModeFromSecondUser(int i, String str) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.IMainUserTaskBarProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.IMainUserTaskBarProxy
            public void updateTrackpadStateChangedFromSecondUser(boolean z) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.IMainUserTaskBarProxy");
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.motorola.taskbar.IMainUserTaskBarProxy");
        }

        public static IMainUserTaskBarProxy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.taskbar.IMainUserTaskBarProxy");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMainUserTaskBarProxy)) ? new Proxy(iBinder) : (IMainUserTaskBarProxy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.motorola.taskbar.IMainUserTaskBarProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.motorola.taskbar.IMainUserTaskBarProxy");
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                updateDisplayChooserModeFromSecondUser(i3, string);
                parcel2.writeNoException();
            } else if (i == 2) {
                int i4 = parcel.readInt();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                handleThreeFingerGestureFromSecondUser(i4, z);
                parcel2.writeNoException();
            } else if (i == 3) {
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                updateTrackpadStateChangedFromSecondUser(z2);
                parcel2.writeNoException();
            } else if (i == 4) {
                int i5 = parcel.readInt();
                boolean z3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                startTrackpadActivityFromSecondUser(i5, z3);
                parcel2.writeNoException();
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                requestShowDesktopSplashScreenFromSecondUser();
                parcel2.writeNoException();
            }
            return true;
        }
    }

    void handleThreeFingerGestureFromSecondUser(int i, boolean z);

    void requestShowDesktopSplashScreenFromSecondUser();

    void startTrackpadActivityFromSecondUser(int i, boolean z);

    void updateDisplayChooserModeFromSecondUser(int i, String str);

    void updateTrackpadStateChangedFromSecondUser(boolean z);
}
