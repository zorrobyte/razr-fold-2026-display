package com.motorola.taskbar.settings.wallpaper.asset.loader;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableResource;

/* JADX INFO: compiled from: DrawableResourceDecoder.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DrawableResourceDecoder implements ResourceDecoder {
    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource decode(Drawable drawable, int i, int i2, Options options) {
        drawable.getClass();
        options.getClass();
        return new DrawableResource(drawable) { // from class: com.motorola.taskbar.settings.wallpaper.asset.loader.DrawableResourceDecoder.decode.1
            @Override // com.bumptech.glide.load.engine.Resource
            public Class getResourceClass() {
                return Drawable.class;
            }

            @Override // com.bumptech.glide.load.engine.Resource
            public int getSize() {
                Drawable drawable2 = get();
                return drawable2.getIntrinsicWidth() * drawable2.getIntrinsicHeight() * 4;
            }

            @Override // com.bumptech.glide.load.engine.Resource
            public void recycle() {
            }
        };
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public boolean handles(Drawable drawable, Options options) {
        drawable.getClass();
        options.getClass();
        return true;
    }
}
