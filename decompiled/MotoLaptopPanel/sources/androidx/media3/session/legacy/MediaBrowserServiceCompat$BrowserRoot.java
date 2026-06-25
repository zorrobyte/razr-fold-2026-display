package androidx.media3.session.legacy;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public final class MediaBrowserServiceCompat$BrowserRoot {
    private final Bundle mExtras;
    private final String mRootId;

    public MediaBrowserServiceCompat$BrowserRoot(String str, Bundle bundle) {
        if (str == null) {
            throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead");
        }
        this.mRootId = str;
        this.mExtras = bundle;
    }
}
