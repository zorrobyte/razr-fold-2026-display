package com.motorola.taskbar.settings.wallpaper.asset;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResourceAsset.kt */
/* JADX INFO: loaded from: classes2.dex */
public class ResourceAsset extends StreamingAsset {
    private Key mKey;
    private final RequestOptions mRequestOptions;
    private final int resId;
    private final Resources resources;

    /* JADX INFO: compiled from: ResourceAsset.kt */
    public class PackageResourceKey implements Key {
        private String mPackageName;
        private int resId;

        public PackageResourceKey(Resources resources, int i) {
            resources.getClass();
            this.resId = i;
            String resourcePackageName = resources.getResourcePackageName(i);
            resourcePackageName.getClass();
            this.mPackageName = resourcePackageName;
        }

        @Override // com.bumptech.glide.load.Key
        public boolean equals(Object obj) {
            if (obj instanceof PackageResourceKey) {
                return Intrinsics.areEqual(getCacheKey(), ((PackageResourceKey) obj).getCacheKey());
            }
            return false;
        }

        protected final String getCacheKey() {
            return "PackageResourceKey{packageName=" + this.mPackageName + ",resId=" + this.resId + "}";
        }

        @Override // com.bumptech.glide.load.Key
        public int hashCode() {
            return getCacheKey().hashCode();
        }

        public String toString() {
            return getCacheKey();
        }

        @Override // com.bumptech.glide.load.Key
        public void updateDiskCacheKey(MessageDigest messageDigest) {
            messageDigest.getClass();
            String cacheKey = getCacheKey();
            Charset charset = Key.CHARSET;
            charset.getClass();
            byte[] bytes = cacheKey.getBytes(charset);
            bytes.getClass();
            messageDigest.update(bytes);
        }
    }

    public ResourceAsset(Resources resources, int i, RequestOptions requestOptions) {
        resources.getClass();
        requestOptions.getClass();
        this.resources = resources;
        this.resId = i;
        this.mRequestOptions = requestOptions;
    }

    public /* synthetic */ ResourceAsset(Resources resources, int i, RequestOptions requestOptions, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(resources, i, (i2 & 4) != 0 ? RequestOptions.centerCropTransform() : requestOptions);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ResourceAsset) {
            return Intrinsics.areEqual(getKey(), ((ResourceAsset) obj).getKey());
        }
        return false;
    }

    public final Key getKey() {
        if (this.mKey == null) {
            this.mKey = new PackageResourceKey(this.resources, this.resId);
        }
        Key key = this.mKey;
        key.getClass();
        return key;
    }

    public final int getResId() {
        return this.resId;
    }

    public final Resources getResources() {
        return this.resources;
    }

    public int hashCode() {
        return getKey().hashCode();
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.AbstractAsset, com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void loadDrawable(Context context, ImageView imageView, int i) {
        context.getClass();
        imageView.getClass();
        Glide.with(context).asDrawable().load(this).apply(this.mRequestOptions.placeholder(new ColorDrawable(i))).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.StreamingAsset
    protected InputStream openInputStream() {
        return this.resources.openRawResource(this.resId);
    }
}
