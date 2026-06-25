package com.motorola.taskbar.settings.wallpaper.asset;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.motorola.taskbar.settings.InjectorProvider;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat;
import com.motorola.taskbar.settings.wallpaper.utils.BitmapUtils;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BuiltInWallpaperAsset.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BuiltInWallpaperAsset extends AbstractAsset {
    public static final Companion Companion = new Companion(null);
    private BuiltInWallpaperModel mBuiltInBuiltInWallpaperModel;
    private final Context mContext;
    private Point mDimensions;
    private final int wallpaperManagerFlag;

    /* JADX INFO: compiled from: BuiltInWallpaperAsset.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: BuiltInWallpaperAsset.kt */
    final class DecodeBitmapAsyncTask extends AsyncTask {
        private final int mHeight;
        private final Function1 mReceiver;
        private final int mWidth;
        final /* synthetic */ BuiltInWallpaperAsset this$0;

        public DecodeBitmapAsyncTask(BuiltInWallpaperAsset builtInWallpaperAsset, int i, int i2, Function1 function1) {
            function1.getClass();
            this.this$0 = builtInWallpaperAsset;
            this.mWidth = i;
            this.mHeight = i2;
            this.mReceiver = function1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            voidArr.getClass();
            WallpaperManagerCompat wallpaperManagerCompat = InjectorProvider.Companion.getInjector().getWallpaperManagerCompat();
            Drawable builtInDrawable = wallpaperManagerCompat.getBuiltInDrawable(this.mWidth, this.mHeight, true, 0.5f, 0.5f, this.this$0.wallpaperManagerFlag);
            wallpaperManagerCompat.forgetLoadedWallpaper();
            builtInDrawable.getClass();
            Bitmap bitmap = ((BitmapDrawable) builtInDrawable).getBitmap();
            bitmap.getClass();
            return bitmap;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            bitmap.getClass();
            this.mReceiver.invoke(bitmap);
        }
    }

    /* JADX INFO: compiled from: BuiltInWallpaperAsset.kt */
    final class DecodeBitmapRegionAsyncTask extends AsyncTask {
        private final Function1 mReceiver;
        private final Rect mRect;
        final /* synthetic */ BuiltInWallpaperAsset this$0;

        public DecodeBitmapRegionAsyncTask(BuiltInWallpaperAsset builtInWallpaperAsset, Rect rect, Function1 function1) {
            rect.getClass();
            function1.getClass();
            this.this$0 = builtInWallpaperAsset;
            this.mRect = rect;
            this.mReceiver = function1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            voidArr.getClass();
            Point pointCalculateRawDimensions = this.this$0.calculateRawDimensions();
            BitmapUtils.Companion companion = BitmapUtils.Companion;
            BitmapDrawable bitmapDrawable = (BitmapDrawable) InjectorProvider.Companion.getInjector().getWallpaperManagerCompat().getBuiltInDrawable(this.mRect.width(), this.mRect.height(), false, companion.calculateHorizontalAlignment(pointCalculateRawDimensions, this.mRect), companion.calculateVerticalAlignment(pointCalculateRawDimensions, this.mRect), this.this$0.wallpaperManagerFlag);
            bitmapDrawable.getClass();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            bitmap.getClass();
            return bitmap;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            bitmap.getClass();
            this.mReceiver.invoke(bitmap);
        }
    }

    /* JADX INFO: compiled from: BuiltInWallpaperAsset.kt */
    final class DecodeDimensionsAsyncTask extends AsyncTask {
        private final Function1 mReceiver;
        final /* synthetic */ BuiltInWallpaperAsset this$0;

        public DecodeDimensionsAsyncTask(BuiltInWallpaperAsset builtInWallpaperAsset, Function1 function1) {
            function1.getClass();
            this.this$0 = builtInWallpaperAsset;
            this.mReceiver = function1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Point doInBackground(Void... voidArr) {
            voidArr.getClass();
            return this.this$0.calculateRawDimensions();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Point point) {
            point.getClass();
            this.mReceiver.invoke(point);
        }
    }

    public BuiltInWallpaperAsset(Context context, int i) {
        context.getClass();
        this.wallpaperManagerFlag = i;
        Context applicationContext = context.getApplicationContext();
        applicationContext.getClass();
        this.mContext = applicationContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Point calculateRawDimensions() {
        Point point = this.mDimensions;
        if (point != null) {
            point.getClass();
            return point;
        }
        Drawable builtInDrawable = InjectorProvider.Companion.getInjector().getWallpaperManagerCompat().getBuiltInDrawable(this.wallpaperManagerFlag);
        builtInDrawable.getClass();
        Bitmap bitmap = ((BitmapDrawable) builtInDrawable).getBitmap();
        Point point2 = new Point(bitmap.getWidth(), bitmap.getHeight());
        this.mDimensions = point2;
        return point2;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void decodeBitmap(int i, int i2, Function1 function1) {
        function1.getClass();
        new DecodeBitmapAsyncTask(this, i, i2, function1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void decodeBitmapRegion(Rect rect, int i, int i2, Function1 function1) {
        rect.getClass();
        function1.getClass();
        new DecodeBitmapRegionAsyncTask(this, rect, function1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void decodeRawDimensions(Activity activity, Function1 function1) {
        function1.getClass();
        new DecodeDimensionsAsyncTask(this, function1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.AbstractAsset, com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void loadDrawable(Context context, ImageView imageView, int i) {
        context.getClass();
        imageView.getClass();
        if (this.mBuiltInBuiltInWallpaperModel == null) {
            Context applicationContext = context.getApplicationContext();
            applicationContext.getClass();
            this.mBuiltInBuiltInWallpaperModel = new BuiltInWallpaperModel(applicationContext, this.wallpaperManagerFlag);
        }
        Glide.with(context).asDrawable().load(this.mBuiltInBuiltInWallpaperModel).apply(RequestOptions.centerCropTransform().placeholder(new ColorDrawable(i))).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
    }
}
