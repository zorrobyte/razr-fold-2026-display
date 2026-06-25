package com.android.systemui.media.controls.ui.controller;

import android.content.Intent;
import android.util.Log;

/* JADX INFO: compiled from: MediaCarouselController.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MediaCarouselControllerKt {
    private static final boolean DEBUG;
    private static final Intent settingsIntent;

    static {
        Intent action = new Intent().setAction("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
        action.getClass();
        settingsIntent = action;
        DEBUG = Log.isLoggable("MediaCarouselController", 3);
    }
}
