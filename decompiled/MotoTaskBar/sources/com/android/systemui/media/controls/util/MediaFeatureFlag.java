package com.android.systemui.media.controls.util;

import android.content.Context;
import com.android.systemui.util.Utils;

/* JADX INFO: compiled from: MediaFeatureFlag.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaFeatureFlag {
    private final Context context;

    public MediaFeatureFlag(Context context) {
        context.getClass();
        this.context = context;
    }

    public final boolean getEnabled() {
        return Utils.useQsMediaPlayer(this.context);
    }
}
