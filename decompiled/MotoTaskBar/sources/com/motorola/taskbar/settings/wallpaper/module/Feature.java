package com.motorola.taskbar.settings.wallpaper.module;

/* JADX INFO: compiled from: Feature.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class Feature {
    private static boolean forceLoadDefaultWallpaper;
    private static final boolean support3rdWallpaper = false;
    private static final boolean supportLiveWallpaper = false;
    public static final Feature INSTANCE = new Feature();
    private static boolean supportOnDeviceWallpaper = true;

    private Feature() {
    }

    public final boolean getForceLoadDefaultWallpaper() {
        return forceLoadDefaultWallpaper;
    }

    public final boolean getSupport3rdWallpaper() {
        return support3rdWallpaper;
    }

    public final boolean getSupportLiveWallpaper() {
        return supportLiveWallpaper;
    }

    public final boolean getSupportOnDeviceWallpaper() {
        return supportOnDeviceWallpaper;
    }
}
