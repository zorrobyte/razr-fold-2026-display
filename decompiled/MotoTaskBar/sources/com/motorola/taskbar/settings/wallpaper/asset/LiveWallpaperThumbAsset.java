package com.motorola.taskbar.settings.wallpaper.asset;

import android.app.Activity;
import android.app.WallpaperInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LiveWallpaperThumbAsset.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LiveWallpaperThumbAsset extends AbstractAsset {
    private final WallpaperInfo info;
    private final Context mContext;

    /* JADX INFO: compiled from: LiveWallpaperThumbAsset.kt */
    final class LiveWallpaperThumbKey implements Key {
        private final WallpaperInfo mInfo;

        public LiveWallpaperThumbKey(WallpaperInfo wallpaperInfo) {
            wallpaperInfo.getClass();
            this.mInfo = wallpaperInfo;
        }

        private final String getCacheKey() {
            return "LiveWallpaperThumbKey{packageName=" + this.mInfo.getPackageName() + ",serviceName=" + this.mInfo.getServiceName() + "}";
        }

        @Override // com.bumptech.glide.load.Key
        public boolean equals(Object obj) {
            if (obj instanceof LiveWallpaperThumbKey) {
                return Intrinsics.areEqual(getCacheKey(), ((LiveWallpaperThumbKey) obj).getCacheKey());
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

    /* JADX INFO: compiled from: LiveWallpaperThumbAsset.kt */
    final class LoadThumbnailTask extends AsyncTask {
        private final WallpaperInfo mInfo;
        private final Function1 mReceiver;
        final /* synthetic */ LiveWallpaperThumbAsset this$0;

        public LoadThumbnailTask(LiveWallpaperThumbAsset liveWallpaperThumbAsset, WallpaperInfo wallpaperInfo, Function1 function1) {
            wallpaperInfo.getClass();
            function1.getClass();
            this.this$0 = liveWallpaperThumbAsset;
            this.mInfo = wallpaperInfo;
            this.mReceiver = function1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            voidArr.getClass();
            Drawable drawableLoadThumbnail = this.mInfo.loadThumbnail(this.this$0.mContext.getPackageManager());
            if (drawableLoadThumbnail == null || !(drawableLoadThumbnail instanceof BitmapDrawable)) {
                return null;
            }
            return ((BitmapDrawable) drawableLoadThumbnail).getBitmap();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            this.mReceiver.invoke(bitmap);
        }
    }

    public LiveWallpaperThumbAsset(Context context, WallpaperInfo wallpaperInfo) {
        context.getClass();
        wallpaperInfo.getClass();
        this.info = wallpaperInfo;
        Context applicationContext = context.getApplicationContext();
        applicationContext.getClass();
        this.mContext = applicationContext;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void decodeBitmap(int i, int i2, Function1 function1) {
        function1.getClass();
        new LoadThumbnailTask(this, this.info, function1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void decodeBitmapRegion(Rect rect, int i, int i2, Function1 function1) {
        rect.getClass();
        function1.getClass();
        function1.invoke(null);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void decodeRawDimensions(Activity activity, Function1 function1) {
        function1.getClass();
        function1.invoke(null);
    }

    public final Key getKey() {
        return new LiveWallpaperThumbKey(this.info);
    }

    public final Drawable getThumbnailDrawable() {
        Drawable drawableLoadThumbnail = this.info.loadThumbnail(this.mContext.getPackageManager());
        drawableLoadThumbnail.getClass();
        return drawableLoadThumbnail;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.AbstractAsset, com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void loadDrawable(Context context, ImageView imageView, int i) {
        context.getClass();
        imageView.getClass();
        Glide.with(context).asDrawable().load(this).apply(RequestOptions.centerCropTransform().placeholder(new ColorDrawable(i))).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
    }
}
