package com.bumptech.glide.load;

import android.content.Context;
import com.bumptech.glide.load.engine.Resource;

/* JADX INFO: loaded from: classes.dex */
public interface Transformation extends Key {
    Resource transform(Context context, Resource resource, int i, int i2);
}
