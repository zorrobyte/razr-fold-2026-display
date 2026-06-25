package com.motorola.taskbar.settings.wallpaper.asset.loader;

import android.os.ParcelFileDescriptor;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.motorola.taskbar.settings.wallpaper.asset.CurrentWallpaperAsset;
import java.io.InputStream;

/* JADX INFO: compiled from: CurrentWallpaperAssetLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
final class CurrentWallpaperAssetDataFetcher implements DataFetcher {
    private final CurrentWallpaperAsset mAsset;

    public CurrentWallpaperAssetDataFetcher(CurrentWallpaperAsset currentWallpaperAsset) {
        currentWallpaperAsset.getClass();
        this.mAsset = currentWallpaperAsset;
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
        ParcelFileDescriptor wallpaperPfd = this.mAsset.getWallpaperPfd();
        if (wallpaperPfd == null) {
            dataCallback.onLoadFailed(new Exception("ParcelFileDescriptor for wallpaper is null, unable to open InputStream."));
        } else {
            dataCallback.onDataReady(new ParcelFileDescriptor.AutoCloseInputStream(wallpaperPfd));
        }
    }
}
