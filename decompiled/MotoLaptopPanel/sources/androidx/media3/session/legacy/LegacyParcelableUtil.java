package androidx.media3.session.legacy;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaDescriptionCompat;
import androidx.media3.common.util.Util;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class LegacyParcelableUtil {
    public static Parcelable convert(Parcelable parcelable, Parcelable.Creator creator) {
        if (parcelable == null) {
            return null;
        }
        Parcelable parcelable2 = (Parcelable) maybeApplyMediaDescriptionParcelableBugWorkaround(parcelable);
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelable2.writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            return (Parcelable) maybeApplyMediaDescriptionParcelableBugWorkaround((Parcelable) creator.createFromParcel(parcelObtain));
        } finally {
            parcelObtain.recycle();
        }
    }

    public static ArrayList convertList(List list, Parcelable.Creator creator) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(convert((Parcelable) list.get(i), creator));
        }
        return arrayList;
    }

    private static Object maybeApplyMediaDescriptionParcelableBugWorkaround(Object obj) {
        int i = Util.SDK_INT;
        if (i < 21 || i >= 23) {
            return obj;
        }
        if (!(obj instanceof android.support.v4.media.MediaBrowserCompat$MediaItem)) {
            return obj instanceof android.support.v4.media.MediaDescriptionCompat ? rebuildMediaDescriptionCompat((android.support.v4.media.MediaDescriptionCompat) obj) : obj;
        }
        android.support.v4.media.MediaBrowserCompat$MediaItem mediaBrowserCompat$MediaItem = (android.support.v4.media.MediaBrowserCompat$MediaItem) obj;
        return new android.support.v4.media.MediaBrowserCompat$MediaItem(rebuildMediaDescriptionCompat(mediaBrowserCompat$MediaItem.getDescription()), mediaBrowserCompat$MediaItem.getFlags());
    }

    private static android.support.v4.media.MediaDescriptionCompat rebuildMediaDescriptionCompat(android.support.v4.media.MediaDescriptionCompat mediaDescriptionCompat) {
        return new MediaDescriptionCompat.Builder().setMediaId(mediaDescriptionCompat.getMediaId()).setTitle(mediaDescriptionCompat.getTitle()).setSubtitle(mediaDescriptionCompat.getSubtitle()).setDescription(mediaDescriptionCompat.getDescription()).setIconBitmap(mediaDescriptionCompat.getIconBitmap()).setIconUri(mediaDescriptionCompat.getIconUri()).setExtras(mediaDescriptionCompat.getExtras()).setMediaUri(mediaDescriptionCompat.getMediaUri()).build();
    }
}
