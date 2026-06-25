package com.motorola.laptoppanel;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface ILaptopPanelController extends IInterface {

    public abstract class Stub extends Binder implements ILaptopPanelController {

        class Proxy implements ILaptopPanelController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.motorola.laptoppanel.ILaptopPanelController
            public void setLaptopPostureEnabled(boolean z) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.laptoppanel.ILaptopPanelController");
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.motorola.laptoppanel.ILaptopPanelController");
        }

        public static ILaptopPanelController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.laptoppanel.ILaptopPanelController");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ILaptopPanelController)) ? new Proxy(iBinder) : (ILaptopPanelController) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.motorola.laptoppanel.ILaptopPanelController");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.motorola.laptoppanel.ILaptopPanelController");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setLaptopPostureEnabled(z);
            parcel2.writeNoException();
            return true;
        }
    }

    void setLaptopPostureEnabled(boolean z);
}
