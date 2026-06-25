package com.motorola.taskbar.settings.wallpaper.asset.loader;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.motorola.taskbar.settings.wallpaper.asset.CurrentWallpaperAsset;

/* JADX INFO: compiled from: CurrentWallpaperAssetLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CurrentWallpaperAssetLoader implements ModelLoader {
    @Override // com.bumptech.glide.load.model.ModelLoader
    public ModelLoader.LoadData buildLoadData(CurrentWallpaperAsset currentWallpaperAsset, int i, int i2, Options options) {
        currentWallpaperAsset.getClass();
        options.getClass();
        return new ModelLoader.LoadData(currentWallpaperAsset.getKey(), new CurrentWallpaperAssetDataFetcher(currentWallpaperAsset));
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public boolean handles(CurrentWallpaperAsset currentWallpaperAsset) {
        currentWallpaperAsset.getClass();
        return true;
    }
}
