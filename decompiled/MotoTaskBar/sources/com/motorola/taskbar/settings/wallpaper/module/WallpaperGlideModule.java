package com.motorola.taskbar.settings.wallpaper.module;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.motorola.taskbar.settings.wallpaper.asset.BuiltInWallpaperModel;
import com.motorola.taskbar.settings.wallpaper.asset.CurrentWallpaperAsset;
import com.motorola.taskbar.settings.wallpaper.asset.LiveWallpaperThumbAsset;
import com.motorola.taskbar.settings.wallpaper.asset.ResourceAsset;
import com.motorola.taskbar.settings.wallpaper.asset.loader.BuiltInWallpaperModelLoaderFactory;
import com.motorola.taskbar.settings.wallpaper.asset.loader.CurrentWallpaperAssetLoaderFactory;
import com.motorola.taskbar.settings.wallpaper.asset.loader.DrawableResourceDecoder;
import com.motorola.taskbar.settings.wallpaper.asset.loader.LiveWallpaperThumbAssetLoaderFactory;
import com.motorola.taskbar.settings.wallpaper.asset.loader.ResourceAssetLoaderFactory;
import java.io.InputStream;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: WallpaperGlideModule.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class WallpaperGlideModule implements GlideModule {
    public static final Companion Companion = new Companion(null);
    private static final int WALLPAPER_DISK_CACHE_SIZE_BYTES = 104857600;

    /* JADX INFO: compiled from: WallpaperGlideModule.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.bumptech.glide.module.GlideModule
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        context.getClass();
        glideBuilder.getClass();
        glideBuilder.setDiskCache(new InternalCacheDiskCacheFactory(context, 104857600L));
        glideBuilder.setMemorySizeCalculator(new MemorySizeCalculator.Builder(context).setBitmapPoolScreens(2.0f).setMemoryCacheScreens(1.2f).build());
        glideBuilder.setDefaultRequestOptions((RequestOptions) new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888));
    }

    @Override // com.bumptech.glide.module.GlideModule
    public void registerComponents(Context context, Glide glide, Registry registry) {
        context.getClass();
        glide.getClass();
        registry.getClass();
        registry.append(BuiltInWallpaperModel.class, Drawable.class, new BuiltInWallpaperModelLoaderFactory());
        registry.append(ResourceAsset.class, InputStream.class, new ResourceAssetLoaderFactory());
        registry.append(LiveWallpaperThumbAsset.class, Drawable.class, new LiveWallpaperThumbAssetLoaderFactory());
        registry.append(CurrentWallpaperAsset.class, InputStream.class, new CurrentWallpaperAssetLoaderFactory());
        registry.append(Drawable.class, Drawable.class, new DrawableResourceDecoder());
    }
}
