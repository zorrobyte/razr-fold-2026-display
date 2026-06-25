package android.support.v4.media.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import com.motorola.plugin.core.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IMediaControllerCallback extends IInterface {

    public abstract class Stub extends Binder implements IMediaControllerCallback {
        public Stub() {
            attachInterface(this, "android.support.v4.media.session.IMediaControllerCallback");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.support.v4.media.session.IMediaControllerCallback");
                return true;
            }
            switch (i) {
                case 1:
                    onEvent(parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 2:
                    onSessionDestroyed();
                    return true;
                case 3:
                    onPlaybackStateChanged((PlaybackStateCompat) _Parcel.readTypedObject(parcel, PlaybackStateCompat.CREATOR));
                    return true;
                case 4:
                    onMetadataChanged((MediaMetadataCompat) _Parcel.readTypedObject(parcel, MediaMetadataCompat.CREATOR));
                    return true;
                case 5:
                    onQueueChanged(parcel.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR));
                    return true;
                case 6:
                    onQueueTitleChanged((CharSequence) _Parcel.readTypedObject(parcel, TextUtils.CHAR_SEQUENCE_CREATOR));
                    return true;
                case 7:
                    onExtrasChanged((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 8:
                    onVolumeInfoChanged((ParcelableVolumeInfo) _Parcel.readTypedObject(parcel, ParcelableVolumeInfo.CREATOR));
                    return true;
                case 9:
                    onRepeatModeChanged(parcel.readInt());
                    return true;
                case R.styleable.GradientColor_android_endX /* 10 */:
                    onShuffleModeChangedRemoved(parcel.readInt() != 0);
                    return true;
                case R.styleable.GradientColor_android_endY /* 11 */:
                    onCaptioningEnabledChanged(parcel.readInt() != 0);
                    return true;
                case 12:
                    onShuffleModeChanged(parcel.readInt());
                    return true;
                case 13:
                    onSessionReady();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    public abstract class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static Object readTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }
    }

    void onCaptioningEnabledChanged(boolean z);

    void onEvent(String str, Bundle bundle);

    void onExtrasChanged(Bundle bundle);

    void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat);

    void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat);

    void onQueueChanged(List list);

    void onQueueTitleChanged(CharSequence charSequence);

    void onRepeatModeChanged(int i);

    void onSessionDestroyed();

    void onSessionReady();

    void onShuffleModeChanged(int i);

    void onShuffleModeChangedRemoved(boolean z);

    void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo);
}
