package com.bumptech.glide.load.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import java.io.FileNotFoundException;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class LocalUriFetcher implements DataFetcher {
    private final ContentResolver contentResolver;
    private Object data;
    private final Uri uri;

    public LocalUriFetcher(ContentResolver contentResolver, Uri uri) {
        this.contentResolver = contentResolver;
        this.uri = uri;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
        Object obj = this.data;
        if (obj != null) {
            try {
                close(obj);
            } catch (IOException unused) {
            }
        }
    }

    protected abstract void close(Object obj);

    @Override // com.bumptech.glide.load.data.DataFetcher
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public final void loadData(Priority priority, DataFetcher.DataCallback dataCallback) {
        try {
            Object objLoadResource = loadResource(this.uri, this.contentResolver);
            this.data = objLoadResource;
            dataCallback.onDataReady(objLoadResource);
        } catch (FileNotFoundException e) {
            if (Log.isLoggable("LocalUriFetcher", 3)) {
                Log.d("LocalUriFetcher", "Failed to open Uri", e);
            }
            dataCallback.onLoadFailed(e);
        }
    }

    protected abstract Object loadResource(Uri uri, ContentResolver contentResolver);
}
