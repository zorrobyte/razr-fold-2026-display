package com.motorola.taskbar.settings.wallpaper.asset.loader;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.motorola.taskbar.settings.wallpaper.asset.BuiltInWallpaperModel;

/* JADX INFO: compiled from: BuiltInWallpaperModelLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BuiltInWallpaperFetcher implements DataFetcher {
    private final int mHeight;
    private final BuiltInWallpaperModel mWallpaperModel;
    private final int mWidth;

    public BuiltInWallpaperFetcher(BuiltInWallpaperModel builtInWallpaperModel, int i, int i2) {
        builtInWallpaperModel.getClass();
        this.mWallpaperModel = builtInWallpaperModel;
        this.mWidth = i;
        this.mHeight = i2;
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
        dataCallback.onDataReady(this.mWallpaperModel.getDrawable(this.mWidth, this.mHeight));
    }
}
