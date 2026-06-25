package com.motorola.taskbar.settings.wallpaper.asset.loader;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.motorola.taskbar.settings.wallpaper.asset.ResourceAsset;
import java.io.InputStream;

/* JADX INFO: compiled from: ResourceAssetLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ResourceAssetFetcher implements DataFetcher {
    private final ResourceAsset mResourceAsset;

    public ResourceAssetFetcher(ResourceAsset resourceAsset) {
        resourceAsset.getClass();
        this.mResourceAsset = resourceAsset;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public Class getDataClass() {
        return InputStream.class;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void loadData(Priority priority, DataFetcher.DataCallback dataCallback) {
        priority.getClass();
        dataCallback.getClass();
        dataCallback.onDataReady(this.mResourceAsset.getResources().openRawResource(this.mResourceAsset.getResId()));
    }
}
