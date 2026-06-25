package com.android.systemui.media.controls.domain.resume;

import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class MediaBrowserFactory {
    private final Context mContext;

    public MediaBrowserFactory(Context context) {
        this.mContext = context;
    }

    public MediaBrowser create(ComponentName componentName, MediaBrowser.ConnectionCallback connectionCallback, Bundle bundle) {
        return new MediaBrowser(this.mContext, componentName, connectionCallback, bundle);
    }
}
