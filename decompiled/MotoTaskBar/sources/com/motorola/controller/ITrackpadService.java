package com.motorola.controller;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.motorola.taskbar.IReadyForTaskbarProxy;

/* JADX INFO: loaded from: classes.dex */
public interface ITrackpadService extends IInterface {

    public abstract class Stub extends Binder implements ITrackpadService {

        class Proxy implements ITrackpadService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.motorola.controller.ITrackpadService
            public boolean isExternalModeChooserShowing(int i) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.controller.ITrackpadService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.controller.ITrackpadService
            public boolean isSupportMllOnSc() {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.controller.ITrackpadService");
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.controller.ITrackpadService
            public void requestShowDesktopSplashScreen() {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.controller.ITrackpadService");
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.controller.ITrackpadService
            public void setReadyForTaskbarProxy(IReadyForTaskbarProxy iReadyForTaskbarProxy) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.controller.ITrackpadService");
                    parcelObtain.writeStrongInterface(iReadyForTaskbarProxy);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.controller.ITrackpadService
            public void setTrackpadProxy(ITrackpadProxy iTrackpadProxy) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.controller.ITrackpadService");
                    parcelObtain.writeStrongInterface(iTrackpadProxy);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.controller.ITrackpadService
            public void updateImeVisible(int i, boolean z) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.controller.ITrackpadService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static ITrackpadService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.controller.ITrackpadService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ITrackpadService)) ? new Proxy(iBinder) : (ITrackpadService) iInterfaceQueryLocalInterface;
        }
    }

    boolean isExternalModeChooserShowing(int i);

    boolean isSupportMllOnSc();

    void requestShowDesktopSplashScreen();

    void setReadyForTaskbarProxy(IReadyForTaskbarProxy iReadyForTaskbarProxy);

    void setTrackpadProxy(ITrackpadProxy iTrackpadProxy);

    void updateImeVisible(int i, boolean z);
}
