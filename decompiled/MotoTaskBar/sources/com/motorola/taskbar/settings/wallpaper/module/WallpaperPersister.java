package com.motorola.taskbar.settings.wallpaper.module;

import android.app.Activity;
import com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo;

/* JADX INFO: compiled from: WallpaperPersister.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface WallpaperPersister {

    /* JADX INFO: compiled from: WallpaperPersister.kt */
    public abstract class DefaultImpls {
        public static /* synthetic */ void setIndividualWallpaperWithPosition$default(WallpaperPersister wallpaperPersister, Activity activity, WallpaperInfo wallpaperInfo, int i, int i2, SetWallpaperCallback setWallpaperCallback, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setIndividualWallpaperWithPosition");
            }
            if ((i3 & 16) != 0) {
                setWallpaperCallback = null;
            }
            wallpaperPersister.setIndividualWallpaperWithPosition(activity, wallpaperInfo, i, i2, setWallpaperCallback);
        }
    }

    /* JADX INFO: compiled from: WallpaperPersister.kt */
    public interface SetWallpaperCallback {
        void onError(Throwable th);
    }

    void setIndividualWallpaperWithPosition(Activity activity, WallpaperInfo wallpaperInfo, int i, int i2, SetWallpaperCallback setWallpaperCallback);
}
