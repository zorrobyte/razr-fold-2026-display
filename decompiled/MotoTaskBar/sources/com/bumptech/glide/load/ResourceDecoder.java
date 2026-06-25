package com.bumptech.glide.load;

import com.bumptech.glide.load.engine.Resource;

/* JADX INFO: loaded from: classes.dex */
public interface ResourceDecoder {
    Resource decode(Object obj, int i, int i2, Options options);

    boolean handles(Object obj, Options options);
}
