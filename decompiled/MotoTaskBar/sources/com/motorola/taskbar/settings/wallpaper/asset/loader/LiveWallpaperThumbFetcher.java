package com.motorola.taskbar.settings.wallpaper.asset.loader;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.motorola.taskbar.settings.wallpaper.asset.LiveWallpaperThumbAsset;

/* JADX INFO: compiled from: LiveWallpaperThumbAssetLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
final class LiveWallpaperThumbFetcher implements DataFetcher {
    private final LiveWallpaperThumbAsset mLiveWallpaperThumbAsset;

    public LiveWallpaperThumbFetcher(LiveWallpaperThumbAsset liveWallpaperThumbAsset) {
        liveWallpaperThumbAsset.getClass();
        this.mLiveWallpaperThumbAsset = liveWallpaperThumbAsset;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public Class getDataClass() {
        return Drawable.class;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void loadData(Priority priority, DataFetcher.DataCallback dataCallback) {
        priority.getClass();
        dataCallback.getClass();
        dataCallback.onDataReady(this.mLiveWallpaperThumbAsset.getThumbnailDrawable());
    }
}
