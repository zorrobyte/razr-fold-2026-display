package com.motorola.taskbar.settings.wallpaper.module.impl;

import com.motorola.taskbar.settings.wallpaper.model.CurrentWallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.model.LiveWallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.module.CurrentWallpaperInfoFactory;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat;
import java.util.ArrayList;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: DefaultCurrentWallpaperInfoFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DefaultCurrentWallpaperInfoFactory implements CurrentWallpaperInfoFactory {
    private WallpaperInfo mHomeWallpaper;
    private WallpaperInfo mLockWallpaper;
    private final WallpaperManagerCompat mWallpaperManagerCompat;

    public DefaultCurrentWallpaperInfoFactory(WallpaperManagerCompat wallpaperManagerCompat) {
        wallpaperManagerCompat.getClass();
        this.mWallpaperManagerCompat = wallpaperManagerCompat;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.CurrentWallpaperInfoFactory
    public void createCurrentWallpaperInfos(boolean z, Function2 function2) {
        WallpaperInfo liveWallpaperInfo;
        Object obj;
        function2.getClass();
        if (!z && (obj = this.mHomeWallpaper) != null) {
            obj.getClass();
            Object obj2 = this.mLockWallpaper;
            obj2.getClass();
            function2.invoke(obj, obj2);
            return;
        }
        if (z) {
            this.mHomeWallpaper = null;
            this.mLockWallpaper = null;
        }
        if (this.mWallpaperManagerCompat.getWallpaperInfo() == null) {
            liveWallpaperInfo = new CurrentWallpaperInfo(new ArrayList(), "home_wallpaper", 8);
        } else {
            android.app.WallpaperInfo wallpaperInfo = this.mWallpaperManagerCompat.getWallpaperInfo();
            wallpaperInfo.getClass();
            liveWallpaperInfo = new LiveWallpaperInfo(wallpaperInfo, false, 2, null);
        }
        this.mHomeWallpaper = liveWallpaperInfo;
        this.mLockWallpaper = liveWallpaperInfo;
        Object obj3 = this.mLockWallpaper;
        obj3.getClass();
        function2.invoke(liveWallpaperInfo, obj3);
    }
}
