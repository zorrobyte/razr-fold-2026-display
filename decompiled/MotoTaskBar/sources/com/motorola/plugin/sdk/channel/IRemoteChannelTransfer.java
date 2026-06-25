package com.motorola.plugin.sdk.channel;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.motorola.plugin.sdk.channel.IRemoteCallback;

/* JADX INFO: loaded from: classes2.dex */
public interface IRemoteChannelTransfer extends IInterface {

    public class Default implements IRemoteChannelTransfer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
        public void onConnect(ClientId clientId, IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
        public void onDisconnect(ClientId clientId) throws RemoteException {
        }

        @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
        public void transferRequest(RemoteChannelRequestInfo remoteChannelRequestInfo, int i, IRemoteCallback iRemoteCallback) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IRemoteChannelTransfer {
        private static final String DESCRIPTOR = "com.motorola.plugin.sdk.channel.IRemoteChannelTransfer";
        static final int TRANSACTION_onConnect = 1;
        static final int TRANSACTION_onDisconnect = 3;
        static final int TRANSACTION_transferRequest = 2;

        class Proxy implements IRemoteChannelTransfer {
            public static IRemoteChannelTransfer sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
            public void onConnect(ClientId clientId, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (clientId != null) {
                        parcelObtain.writeInt(1);
                        clientId.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(iRemoteCallback != null ? iRemoteCallback.asBinder() : null);
                    if (this.mRemote.transact(1, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        parcelObtain.recycle();
                    } else {
                        Stub.getDefaultImpl().onConnect(clientId, iRemoteCallback);
                        parcelObtain.recycle();
                    }
                } catch (Throwable th) {
                    parcelObtain.recycle();
                    throw th;
                }
            }

            @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
            public void onDisconnect(ClientId clientId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (clientId != null) {
                        parcelObtain.writeInt(1);
                        clientId.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (this.mRemote.transact(3, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        parcelObtain.recycle();
                    } else {
                        Stub.getDefaultImpl().onDisconnect(clientId);
                        parcelObtain.recycle();
                    }
                } catch (Throwable th) {
                    parcelObtain.recycle();
                    throw th;
                }
            }

            @Override // com.motorola.plugin.sdk.channel.IRemoteChannelTransfer
            public void transferRequest(RemoteChannelRequestInfo remoteChannelRequestInfo, int i, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteChannelRequestInfo != null) {
                        parcelObtain.writeInt(1);
                        remoteChannelRequestInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iRemoteCallback != null ? iRemoteCallback.asBinder() : null);
                    if (this.mRemote.transact(2, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        parcelObtain.recycle();
                    } else {
                        Stub.getDefaultImpl().transferRequest(remoteChannelRequestInfo, i, iRemoteCallback);
                        parcelObtain.recycle();
                    }
                } catch (Throwable th) {
                    parcelObtain.recycle();
                    throw th;
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteChannelTransfer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRemoteChannelTransfer)) ? new Proxy(iBinder) : (IRemoteChannelTransfer) iInterfaceQueryLocalInterface;
        }

        public static IRemoteChannelTransfer getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IRemoteChannelTransfer iRemoteChannelTransfer) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteChannelTransfer == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteChannelTransfer;
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onConnect(parcel.readInt() != 0 ? (ClientId) ClientId.CREATOR.createFromParcel(parcel) : null, IRemoteCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                transferRequest(parcel.readInt() != 0 ? (RemoteChannelRequestInfo) RemoteChannelRequestInfo.CREATOR.createFromParcel(parcel) : null, parcel.readInt(), IRemoteCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                onDisconnect(parcel.readInt() != 0 ? (ClientId) ClientId.CREATOR.createFromParcel(parcel) : null);
                return true;
            }
            if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }
    }

    void onConnect(ClientId clientId, IRemoteCallback iRemoteCallback) throws RemoteException;

    void onDisconnect(ClientId clientId) throws RemoteException;

    void transferRequest(RemoteChannelRequestInfo remoteChannelRequestInfo, int i, IRemoteCallback iRemoteCallback) throws RemoteException;
}
