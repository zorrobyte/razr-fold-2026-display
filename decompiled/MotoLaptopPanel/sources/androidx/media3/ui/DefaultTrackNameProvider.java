package androidx.media3.ui;

import android.content.res.Resources;
import androidx.media3.common.util.Assertions;

/* JADX INFO: loaded from: classes.dex */
public class DefaultTrackNameProvider implements TrackNameProvider {
    private final Resources resources;

    public DefaultTrackNameProvider(Resources resources) {
        this.resources = (Resources) Assertions.checkNotNull(resources);
    }
}
