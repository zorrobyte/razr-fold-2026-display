package androidx.media3.session;

import android.os.Bundle;
import androidx.media3.common.util.Util;

/* JADX INFO: loaded from: classes.dex */
public final class MediaLibraryService$LibraryParams {
    public final Bundle extras;
    public final boolean isOffline;
    public final boolean isRecent;
    public final boolean isSuggested;
    private static final String FIELD_EXTRAS = Util.intToStringMaxRadix(0);
    private static final String FIELD_RECENT = Util.intToStringMaxRadix(1);
    private static final String FIELD_OFFLINE = Util.intToStringMaxRadix(2);
    private static final String FIELD_SUGGESTED = Util.intToStringMaxRadix(3);

    private MediaLibraryService$LibraryParams(Bundle bundle, boolean z, boolean z2, boolean z3) {
        this.extras = new Bundle(bundle);
        this.isRecent = z;
        this.isOffline = z2;
        this.isSuggested = z3;
    }

    public static MediaLibraryService$LibraryParams fromBundle(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(FIELD_EXTRAS);
        boolean z = bundle.getBoolean(FIELD_RECENT, false);
        boolean z2 = bundle.getBoolean(FIELD_OFFLINE, false);
        boolean z3 = bundle.getBoolean(FIELD_SUGGESTED, false);
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        return new MediaLibraryService$LibraryParams(bundle2, z, z2, z3);
    }
}
