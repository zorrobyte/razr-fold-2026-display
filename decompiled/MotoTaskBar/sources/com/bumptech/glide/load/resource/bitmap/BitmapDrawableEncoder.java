package com.bumptech.glide.load.resource.bitmap;

import android.graphics.drawable.BitmapDrawable;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class BitmapDrawableEncoder implements ResourceEncoder {
    private final BitmapPool bitmapPool;
    private final ResourceEncoder encoder;

    public BitmapDrawableEncoder(BitmapPool bitmapPool, ResourceEncoder resourceEncoder) {
        this.bitmapPool = bitmapPool;
        this.encoder = resourceEncoder;
    }

    @Override // com.bumptech.glide.load.Encoder
    public boolean encode(Resource resource, File file, Options options) {
        return this.encoder.encode(new BitmapResource(((BitmapDrawable) resource.get()).getBitmap(), this.bitmapPool), file, options);
    }

    @Override // com.bumptech.glide.load.ResourceEncoder
    public EncodeStrategy getEncodeStrategy(Options options) {
        return this.encoder.getEncodeStrategy(options);
    }
}
