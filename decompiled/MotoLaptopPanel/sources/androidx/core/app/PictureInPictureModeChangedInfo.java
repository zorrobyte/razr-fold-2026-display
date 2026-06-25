package androidx.core.app;

import android.content.res.Configuration;

/* JADX INFO: compiled from: PictureInPictureModeChangedInfo.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PictureInPictureModeChangedInfo {
    private final boolean isInPictureInPictureMode;
    private Configuration newConfiguration;

    public PictureInPictureModeChangedInfo(boolean z) {
        this.isInPictureInPictureMode = z;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PictureInPictureModeChangedInfo(boolean z, Configuration configuration) {
        this(z);
        configuration.getClass();
        this.newConfiguration = configuration;
    }

    public final boolean isInPictureInPictureMode() {
        return this.isInPictureInPictureMode;
    }
}
