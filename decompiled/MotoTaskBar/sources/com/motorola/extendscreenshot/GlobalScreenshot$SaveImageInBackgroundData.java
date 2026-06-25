package com.motorola.extendscreenshot;

import android.graphics.Bitmap;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
class GlobalScreenshot$SaveImageInBackgroundData {
    public int errorMsgResId;
    public Consumer finisher;
    public Bitmap image;
    public GlobalScreenshot$ActionsReadyListener mActionsReadyListener;

    GlobalScreenshot$SaveImageInBackgroundData() {
    }

    void clearImage() {
        this.image = null;
    }
}
