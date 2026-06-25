package com.motorola.taskbar;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.internal.statusbar.StatusBarIcon;
import com.motorola.plugin.core.R;
import com.motorola.taskbar.IMainUserTaskBarProxy;
import com.motorola.taskbar.ITaskBarProxy;

/* JADX INFO: loaded from: classes2.dex */
public interface ITaskBarService extends IInterface {

    public abstract class Stub extends Binder implements ITaskBarService {

        class Proxy implements ITaskBarService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void onDisplayReady(int i) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void onDisplayRemoved(int i) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void onSystemUIReady() {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void onTaskbarWindowStateChanged(int i, int i2) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void requestDisplayChooserMode() {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void setMainUserTaskBarProxy(IMainUserTaskBarProxy iMainUserTaskBarProxy) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    parcelObtain.writeStrongInterface(iMainUserTaskBarProxy);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void setTaskBarImeSwitchButtonVisible(int i, boolean z) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void setTaskBarProxy(ITaskBarProxy iTaskBarProxy) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    parcelObtain.writeStrongInterface(iTaskBarProxy);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void setTaskBarTransitionMode(int i, int i2) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void setTaskBarViewVisibility(int i, int i2) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.motorola.taskbar.ITaskBarService
            public void updateImeVisible(int i, boolean z) {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.taskbar.ITaskBarService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.motorola.taskbar.ITaskBarService");
        }

        public static ITaskBarService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.motorola.taskbar.ITaskBarService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ITaskBarService)) ? new Proxy(iBinder) : (ITaskBarService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.motorola.taskbar.ITaskBarService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.motorola.taskbar.ITaskBarService");
                return true;
            }
            switch (i) {
                case 1:
                    ITaskBarProxy iTaskBarProxyAsInterface = ITaskBarProxy.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setTaskBarProxy(iTaskBarProxyAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    onSystemUIReady();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayReady(i3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayRemoved(i4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTaskBarTransitionMode(i5, i6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTaskBarViewVisibility(i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTaskBarImeSwitchButtonVisible(i9, z);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i10 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateImeVisible(i10, z2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string = parcel.readString();
                    int i11 = parcel.readInt();
                    StatusBarIcon statusBarIcon = (StatusBarIcon) parcel.readTypedObject(StatusBarIcon.CREATOR);
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    addDesktopIcon(string, i11, statusBarIcon, pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case R.styleable.GradientColor_android_endX /* 10 */:
                    String string2 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeDesktopIcon(string2, i12);
                    parcel2.writeNoException();
                    return true;
                case R.styleable.GradientColor_android_endY /* 11 */:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskbarWindowStateChanged(i13, i14);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IMainUserTaskBarProxy iMainUserTaskBarProxyAsInterface = IMainUserTaskBarProxy.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setMainUserTaskBarProxy(iMainUserTaskBarProxyAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    onNavIconClicked();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    onOverviewShown();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    requestDisplayChooserMode();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    DesktopQSTileData desktopQSTileData = (DesktopQSTileData) parcel.readTypedObject(DesktopQSTileData.CREATOR);
                    parcel.enforceNoDataAvail();
                    onQSTileDataUpdated(desktopQSTileData);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void addDesktopIcon(String str, int i, StatusBarIcon statusBarIcon, PendingIntent pendingIntent);

    void onDisplayReady(int i);

    void onDisplayRemoved(int i);

    void onNavIconClicked();

    void onOverviewShown();

    void onQSTileDataUpdated(DesktopQSTileData desktopQSTileData);

    void onSystemUIReady();

    void onTaskbarWindowStateChanged(int i, int i2);

    void removeDesktopIcon(String str, int i);

    void requestDisplayChooserMode();

    void setMainUserTaskBarProxy(IMainUserTaskBarProxy iMainUserTaskBarProxy);

    void setTaskBarImeSwitchButtonVisible(int i, boolean z);

    void setTaskBarProxy(ITaskBarProxy iTaskBarProxy);

    void setTaskBarTransitionMode(int i, int i2);

    void setTaskBarViewVisibility(int i, int i2);

    void updateImeVisible(int i, boolean z);
}
