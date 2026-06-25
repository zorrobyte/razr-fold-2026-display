package androidx.mediarouter.media;

import android.os.Messenger;

/* JADX INFO: loaded from: classes.dex */
abstract class MediaRouteProviderProtocol {
    public static boolean isValidRemoteMessenger(Messenger messenger) {
        if (messenger != null) {
            try {
                if (messenger.getBinder() != null) {
                    return true;
                }
            } catch (NullPointerException unused) {
            }
        }
        return false;
    }
}
