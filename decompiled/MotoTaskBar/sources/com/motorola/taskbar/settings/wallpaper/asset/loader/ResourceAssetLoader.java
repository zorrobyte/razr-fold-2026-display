package com.motorola.taskbar.settings.wallpaper.asset.loader;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.motorola.taskbar.settings.wallpaper.asset.ResourceAsset;

/* JADX INFO: compiled from: ResourceAssetLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ResourceAssetLoader implements ModelLoader {
    @Override // com.bumptech.glide.load.model.ModelLoader
    public ModelLoader.LoadData buildLoadData(ResourceAsset resourceAsset, int i, int i2, Options options) {
        resourceAsset.getClass();
        options.getClass();
        return new ModelLoader.LoadData(resourceAsset.getKey(), new ResourceAssetFetcher(resourceAsset));
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public boolean handles(ResourceAsset resourceAsset) {
        resourceAsset.getClass();
        return true;
    }
}
