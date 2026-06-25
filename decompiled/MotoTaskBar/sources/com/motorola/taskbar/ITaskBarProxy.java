package com.motorola.taskbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface ITaskBarProxy extends IInterface {

    public abstract class Stub extends Binder implements ITaskBarProxy {

        class Proxy implements ITaskBarProxy {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.motorola.taskbar.ITaskBarProxy
            public void forceShowKeyguardPresentation() {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarProxy");
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarProxy
            public DesktopQSTileData getQSTileDataData(int i) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarProxy");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DesktopQSTileData) parcelObtain2.readTypedObject(DesktopQSTileData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarProxy
            public void onHardwareDisplayStatusChanged(boolean z, boolean z2) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarProxy");
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarProxy
            public void onQsTileClick(int i, int i2) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarProxy");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarProxy
            public void requestNavIcon(boolean z, int i) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarProxy");
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarProxy
            public void requestNavTrackpadGuide(boolean z) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarProxy");
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarProxy
            public void touchAutoHide(int i) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarProxy");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.motorola.taskbar.ITaskBarProxy");
        }

        public static ITaskBarProxy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.taskbar.ITaskBarProxy");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ITaskBarProxy)) ? new Proxy(iBinder) : (ITaskBarProxy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.motorola.taskbar.ITaskBarProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.motorola.taskbar.ITaskBarProxy");
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    touchAutoHide(i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean z = parcel.readBoolean();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestNavIcon(z, i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestNavTrackpadGuide(z2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTrackpadStateChanged(z3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onHardwareDisplayStatusChanged(z4, z5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    DesktopQSTileData qSTileDataData = getQSTileDataData(i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(qSTileDataData, 1);
                    return true;
                case 7:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onQsTileClick(i6, i7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    forceShowKeyguardPresentation();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void forceShowKeyguardPresentation();

    DesktopQSTileData getQSTileDataData(int i);

    void onHardwareDisplayStatusChanged(boolean z, boolean z2);

    void onQsTileClick(int i, int i2);

    void onTrackpadStateChanged(boolean z);

    void requestNavIcon(boolean z, int i);

    void requestNavTrackpadGuide(boolean z);

    void touchAutoHide(int i);
}
