package com.motorola.taskbar.settings.wallpaper.asset;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.motorola.taskbar.settings.InjectorProvider;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CurrentWallpaperAsset.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CurrentWallpaperAsset extends StreamingAsset {
    public static final Companion Companion = new Companion(null);
    private int mWallpaperId;
    private final WallpaperManagerCompat mWallpaperManagerCompat;
    private final int wallpaperManagerFlag;

    /* JADX INFO: compiled from: CurrentWallpaperAsset.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: CurrentWallpaperAsset.kt */
    final class CurrentWallpaperKey implements Key {
        private final int mWallpaperFlag;
        private final WallpaperManagerCompat mWallpaperManagerCompat;

        public CurrentWallpaperKey(WallpaperManagerCompat wallpaperManagerCompat, int i) {
            wallpaperManagerCompat.getClass();
            this.mWallpaperManagerCompat = wallpaperManagerCompat;
            this.mWallpaperFlag = i;
        }

        private final String getCacheKey() {
            int i = this.mWallpaperFlag;
            return "CurrentWallpaperKey{flag=" + i + ",id=" + this.mWallpaperManagerCompat.getWallpaperId(i) + "}";
        }

        @Override // com.bumptech.glide.load.Key
        public boolean equals(Object obj) {
            if (obj instanceof CurrentWallpaperKey) {
                return Intrinsics.areEqual(getCacheKey(), ((CurrentWallpaperKey) obj).getCacheKey());
            }
            return false;
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

    public CurrentWallpaperAsset(Context context, int i) {
        context.getClass();
        this.wallpaperManagerFlag = i;
        WallpaperManagerCompat wallpaperManagerCompat = InjectorProvider.Companion.getInjector().getWallpaperManagerCompat();
        this.mWallpaperManagerCompat = wallpaperManagerCompat;
        this.mWallpaperId = wallpaperManagerCompat.getWallpaperId(i);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CurrentWallpaperAsset) {
            CurrentWallpaperAsset currentWallpaperAsset = (CurrentWallpaperAsset) obj;
            if (currentWallpaperAsset.wallpaperManagerFlag == this.wallpaperManagerFlag && currentWallpaperAsset.mWallpaperId == this.mWallpaperId) {
                return true;
            }
        }
        return false;
    }

    public final Key getKey() {
        return new CurrentWallpaperKey(this.mWallpaperManagerCompat, this.wallpaperManagerFlag);
    }

    public final ParcelFileDescriptor getWallpaperPfd() {
        return this.mWallpaperManagerCompat.getWallpaperFile(this.wallpaperManagerFlag);
    }

    public int hashCode() {
        return ((527 + this.wallpaperManagerFlag) * 31) + this.mWallpaperId;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.AbstractAsset, com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void loadDrawable(Context context, ImageView imageView, int i) {
        context.getClass();
        imageView.getClass();
        Glide.with(context).asDrawable().load(this).apply((BaseRequestOptions) RequestOptions.centerCropTransform()).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.StreamingAsset
    protected InputStream openInputStream() {
        ParcelFileDescriptor wallpaperFile = this.mWallpaperManagerCompat.getWallpaperFile(this.wallpaperManagerFlag);
        if (wallpaperFile != null) {
            return new ParcelFileDescriptor.AutoCloseInputStream(wallpaperFile);
        }
        Log.e("CurrentWallpaperAsset", "ParcelFileDescriptor for wallpaper " + this.wallpaperManagerFlag + " is null, unable to open InputStream.");
        return null;
    }
}
