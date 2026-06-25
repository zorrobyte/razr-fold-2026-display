package androidx.media3.session;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IMediaController extends IInterface {

    public abstract class Stub extends Binder implements IMediaController {
        public Stub() {
            attachInterface(this, "androidx.media3.session.IMediaController");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("androidx.media3.session.IMediaController");
            }
            if (i == 1598968902) {
                parcel2.writeString("androidx.media3.session.IMediaController");
                return true;
            }
            if (i == 4001) {
                onChildrenChanged(parcel.readInt(), parcel.readString(), parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
            } else if (i != 4002) {
                switch (i) {
                    case 3001:
                        onConnected(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                        break;
                    case 3002:
                        onSessionResult(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                        break;
                    case 3003:
                        onLibraryResult(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                        break;
                    case 3004:
                        onSetCustomLayout(parcel.readInt(), parcel.createTypedArrayList(Bundle.CREATOR));
                        break;
                    case 3005:
                        int i3 = parcel.readInt();
                        Parcelable.Creator creator = Bundle.CREATOR;
                        onCustomCommand(i3, (Bundle) _Parcel.readTypedObject(parcel, creator), (Bundle) _Parcel.readTypedObject(parcel, creator));
                        break;
                    case 3006:
                        onDisconnected(parcel.readInt());
                        break;
                    case 3007:
                        onPlayerInfoChanged(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), parcel.readInt() != 0);
                        break;
                    case 3008:
                        onPeriodicSessionPositionInfoChanged(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                        break;
                    case 3009:
                        onAvailableCommandsChangedFromPlayer(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                        break;
                    case 3010:
                        int i4 = parcel.readInt();
                        Parcelable.Creator creator2 = Bundle.CREATOR;
                        onAvailableCommandsChangedFromSession(i4, (Bundle) _Parcel.readTypedObject(parcel, creator2), (Bundle) _Parcel.readTypedObject(parcel, creator2));
                        break;
                    case 3011:
                        onRenderedFirstFrame(parcel.readInt());
                        break;
                    case 3012:
                        onExtrasChanged(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                        break;
                    case 3013:
                        int i5 = parcel.readInt();
                        Parcelable.Creator creator3 = Bundle.CREATOR;
                        onPlayerInfoChangedWithExclusions(i5, (Bundle) _Parcel.readTypedObject(parcel, creator3), (Bundle) _Parcel.readTypedObject(parcel, creator3));
                        break;
                    case 3014:
                        onSessionActivityChanged(parcel.readInt(), (PendingIntent) _Parcel.readTypedObject(parcel, PendingIntent.CREATOR));
                        break;
                    case 3015:
                        onError(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                        break;
                    case 3016:
                        onSetMediaButtonPreferences(parcel.readInt(), parcel.createTypedArrayList(Bundle.CREATOR));
                        break;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                onSearchResultChanged(parcel.readInt(), parcel.readString(), parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
            }
            return true;
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

    void onAvailableCommandsChangedFromPlayer(int i, Bundle bundle);

    void onAvailableCommandsChangedFromSession(int i, Bundle bundle, Bundle bundle2);

    void onChildrenChanged(int i, String str, int i2, Bundle bundle);

    void onConnected(int i, Bundle bundle);

    void onCustomCommand(int i, Bundle bundle, Bundle bundle2);

    void onDisconnected(int i);

    void onError(int i, Bundle bundle);

    void onExtrasChanged(int i, Bundle bundle);

    void onLibraryResult(int i, Bundle bundle);

    void onPeriodicSessionPositionInfoChanged(int i, Bundle bundle);

    void onPlayerInfoChanged(int i, Bundle bundle, boolean z);

    void onPlayerInfoChangedWithExclusions(int i, Bundle bundle, Bundle bundle2);

    void onRenderedFirstFrame(int i);

    void onSearchResultChanged(int i, String str, int i2, Bundle bundle);

    void onSessionActivityChanged(int i, PendingIntent pendingIntent);

    void onSessionResult(int i, Bundle bundle);

    void onSetCustomLayout(int i, List list);

    void onSetMediaButtonPreferences(int i, List list);
}
