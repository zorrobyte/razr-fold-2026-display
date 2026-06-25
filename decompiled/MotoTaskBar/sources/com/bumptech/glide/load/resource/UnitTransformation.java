package com.bumptech.glide.load.resource;

import android.content.Context;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public final class UnitTransformation implements Transformation {
    private static final Transformation TRANSFORMATION = new UnitTransformation();

    private UnitTransformation() {
    }

    public static UnitTransformation get() {
        return (UnitTransformation) TRANSFORMATION;
    }

    @Override // com.bumptech.glide.load.Transformation
    public Resource transform(Context context, Resource resource, int i, int i2) {
        return resource;
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }
}
