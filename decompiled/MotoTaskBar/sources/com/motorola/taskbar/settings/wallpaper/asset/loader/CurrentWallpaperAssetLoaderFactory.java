package com.motorola.taskbar.settings.wallpaper.asset.loader;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

/* JADX INFO: compiled from: CurrentWallpaperAssetLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CurrentWallpaperAssetLoaderFactory implements ModelLoaderFactory {
    @Override // com.bumptech.glide.load.model.ModelLoaderFactory
    public ModelLoader build(MultiModelLoaderFactory multiModelLoaderFactory) {
        multiModelLoaderFactory.getClass();
        return new CurrentWallpaperAssetLoader();
    }
}
