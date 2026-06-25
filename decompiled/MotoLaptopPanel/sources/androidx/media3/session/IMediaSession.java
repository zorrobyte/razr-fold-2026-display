package androidx.media3.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Surface;

/* JADX INFO: loaded from: classes.dex */
public interface IMediaSession extends IInterface {

    public abstract class Stub extends Binder implements IMediaSession {

        class Proxy implements IMediaSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // androidx.media3.session.IMediaSession
            public void connect(IMediaController iMediaController, int i, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("androidx.media3.session.IMediaSession");
                    parcelObtain.writeStrongInterface(iMediaController);
                    parcelObtain.writeInt(i);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(3015, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.media3.session.IMediaSession
            public void flushCommandQueue(IMediaController iMediaController) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("androidx.media3.session.IMediaSession");
                    parcelObtain.writeStrongInterface(iMediaController);
                    this.mRemote.transact(3045, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.media3.session.IMediaSession
            public void onControllerResult(IMediaController iMediaController, int i, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("androidx.media3.session.IMediaSession");
                    parcelObtain.writeStrongInterface(iMediaController);
                    parcelObtain.writeInt(i);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(3014, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.media3.session.IMediaSession
            public void pause(IMediaController iMediaController, int i) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("androidx.media3.session.IMediaSession");
                    parcelObtain.writeStrongInterface(iMediaController);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3025, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.media3.session.IMediaSession
            public void play(IMediaController iMediaController, int i) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("androidx.media3.session.IMediaSession");
                    parcelObtain.writeStrongInterface(iMediaController);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3024, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.media3.session.IMediaSession
            public void release(IMediaController iMediaController, int i) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("androidx.media3.session.IMediaSession");
                    parcelObtain.writeStrongInterface(iMediaController);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3035, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.media3.session.IMediaSession
            public void seekTo(IMediaController iMediaController, int i, long j) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("androidx.media3.session.IMediaSession");
                    parcelObtain.writeStrongInterface(iMediaController);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(3038, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.media3.session.IMediaSession
            public void seekToNext(IMediaController iMediaController, int i) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("androidx.media3.session.IMediaSession");
                    parcelObtain.writeStrongInterface(iMediaController);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3047, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.media3.session.IMediaSession
            public void seekToPrevious(IMediaController iMediaController, int i) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("androidx.media3.session.IMediaSession");
                    parcelObtain.writeStrongInterface(iMediaController);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3046, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.media3.session.IMediaSession
            public void setVideoSurface(IMediaController iMediaController, int i, Surface surface) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("androidx.media3.session.IMediaSession");
                    parcelObtain.writeStrongInterface(iMediaController);
                    parcelObtain.writeInt(i);
                    _Parcel.writeTypedObject(parcelObtain, surface, 0);
                    this.mRemote.transact(3044, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public static IMediaSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("androidx.media3.session.IMediaSession");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMediaSession)) ? new Proxy(iBinder) : (IMediaSession) iInterfaceQueryLocalInterface;
        }
    }

    public abstract class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static void writeTypedObject(Parcel parcel, Parcelable parcelable, int i) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, i);
            }
        }
    }

    void connect(IMediaController iMediaController, int i, Bundle bundle);

    void flushCommandQueue(IMediaController iMediaController);

    void onControllerResult(IMediaController iMediaController, int i, Bundle bundle);

    void pause(IMediaController iMediaController, int i);

    void play(IMediaController iMediaController, int i);

    void release(IMediaController iMediaController, int i);

    void seekTo(IMediaController iMediaController, int i, long j);

    void seekToNext(IMediaController iMediaController, int i);

    void seekToPrevious(IMediaController iMediaController, int i);

    void setVideoSurface(IMediaController iMediaController, int i, Surface surface);
}
