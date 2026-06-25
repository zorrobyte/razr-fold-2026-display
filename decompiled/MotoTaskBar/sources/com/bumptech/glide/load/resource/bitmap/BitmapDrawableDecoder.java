package com.bumptech.glide.load.resource.bitmap;

import android.content.res.Resources;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class BitmapDrawableDecoder implements ResourceDecoder {
    private final ResourceDecoder decoder;
    private final Resources resources;

    public BitmapDrawableDecoder(Resources resources, ResourceDecoder resourceDecoder) {
        this.resources = (Resources) Preconditions.checkNotNull(resources);
        this.decoder = (ResourceDecoder) Preconditions.checkNotNull(resourceDecoder);
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource decode(Object obj, int i, int i2, Options options) {
        return LazyBitmapDrawableResource.obtain(this.resources, this.decoder.decode(obj, i, i2, options));
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public boolean handles(Object obj, Options options) {
        return this.decoder.handles(obj, options);
    }
}
