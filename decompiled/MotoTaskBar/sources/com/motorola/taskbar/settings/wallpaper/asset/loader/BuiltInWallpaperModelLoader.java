package com.motorola.taskbar.settings.wallpaper.asset.loader;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.motorola.taskbar.settings.wallpaper.asset.BuiltInWallpaperModel;

/* JADX INFO: compiled from: BuiltInWallpaperModelLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BuiltInWallpaperModelLoader implements ModelLoader {
    @Override // com.bumptech.glide.load.model.ModelLoader
    public ModelLoader.LoadData buildLoadData(BuiltInWallpaperModel builtInWallpaperModel, int i, int i2, Options options) {
        builtInWallpaperModel.getClass();
        options.getClass();
        return new ModelLoader.LoadData(builtInWallpaperModel.getKey(), new BuiltInWallpaperFetcher(builtInWallpaperModel, i, i2));
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public boolean handles(BuiltInWallpaperModel builtInWallpaperModel) {
        builtInWallpaperModel.getClass();
        return true;
    }
}
