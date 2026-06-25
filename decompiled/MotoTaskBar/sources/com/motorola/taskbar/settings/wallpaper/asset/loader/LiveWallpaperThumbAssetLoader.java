package com.motorola.taskbar.settings.wallpaper.asset.loader;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.motorola.taskbar.settings.wallpaper.asset.LiveWallpaperThumbAsset;

/* JADX INFO: compiled from: LiveWallpaperThumbAssetLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LiveWallpaperThumbAssetLoader implements ModelLoader {
    @Override // com.bumptech.glide.load.model.ModelLoader
    public ModelLoader.LoadData buildLoadData(LiveWallpaperThumbAsset liveWallpaperThumbAsset, int i, int i2, Options options) {
        liveWallpaperThumbAsset.getClass();
        options.getClass();
        return new ModelLoader.LoadData(liveWallpaperThumbAsset.getKey(), new LiveWallpaperThumbFetcher(liveWallpaperThumbAsset));
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public boolean handles(LiveWallpaperThumbAsset liveWallpaperThumbAsset) {
        liveWallpaperThumbAsset.getClass();
        return true;
    }
}
