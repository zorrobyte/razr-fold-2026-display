package androidx.media3.session;

import androidx.media3.common.util.BitmapLoader;

/* JADX INFO: loaded from: classes.dex */
public final class CacheBitmapLoader implements BitmapLoader {
    private final BitmapLoader bitmapLoader;

    public CacheBitmapLoader(BitmapLoader bitmapLoader) {
        this.bitmapLoader = bitmapLoader;
    }
}
