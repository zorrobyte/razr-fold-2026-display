package com.motorola.glass;

import android.view.Display;

/* JADX INFO: loaded from: classes.dex */
public interface IGlassManager {
    void onDeviceAttached(Object obj);

    void onDeviceDetached(Object obj);

    void onDisplayChanged(Display display);
}
