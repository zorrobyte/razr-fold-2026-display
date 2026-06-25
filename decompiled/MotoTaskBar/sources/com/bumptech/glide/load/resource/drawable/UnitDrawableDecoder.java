package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;

/* JADX INFO: loaded from: classes.dex */
public class UnitDrawableDecoder implements ResourceDecoder {
    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource decode(Drawable drawable, int i, int i2, Options options) {
        return NonOwnedDrawableResource.newInstance(drawable);
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public boolean handles(Drawable drawable, Options options) {
        return true;
    }
}
