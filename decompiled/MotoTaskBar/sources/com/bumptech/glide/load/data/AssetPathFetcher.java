package com.bumptech.glide.load.data;

import android.content.res.AssetManager;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class AssetPathFetcher implements DataFetcher {
    private final AssetManager assetManager;
    private final String assetPath;
    private Object data;

    public AssetPathFetcher(AssetManager assetManager, String str) {
        this.assetManager = assetManager;
        this.assetPath = str;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
        Object obj = this.data;
        if (obj == null) {
            return;
        }
        try {
            close(obj);
        } catch (IOException unused) {
        }
    }

    protected abstract void close(Object obj);

    @Override // com.bumptech.glide.load.data.DataFetcher
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void loadData(Priority priority, DataFetcher.DataCallback dataCallback) {
        try {
            Object objLoadResource = loadResource(this.assetManager, this.assetPath);
            this.data = objLoadResource;
            dataCallback.onDataReady(objLoadResource);
        } catch (IOException e) {
            if (Log.isLoggable("AssetPathFetcher", 3)) {
                Log.d("AssetPathFetcher", "Failed to load data from asset manager", e);
            }
            dataCallback.onLoadFailed(e);
        }
    }

    protected abstract Object loadResource(AssetManager assetManager, String str);
}
