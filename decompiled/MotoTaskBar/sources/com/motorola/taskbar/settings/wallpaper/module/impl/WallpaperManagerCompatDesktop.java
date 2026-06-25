package com.motorola.taskbar.settings.wallpaper.module.impl;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.WindowManager;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat;
import java.io.InputStream;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: WallpaperManagerCompatDesktop.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class WallpaperManagerCompatDesktop extends WallpaperManagerCompat {
    public static final Companion Companion = new Companion(null);
    private final Context context;
    private final WallpaperManager mWallpaperManager;

    /* JADX INFO: compiled from: WallpaperManagerCompatDesktop.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public WallpaperManagerCompatDesktop(Context context) {
        context.getClass();
        this.context = context;
        Object systemService = context.getSystemService((Class<Object>) WallpaperManager.class);
        systemService.getClass();
        this.mWallpaperManager = (WallpaperManager) systemService;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat
    public void forgetLoadedWallpaper() {
        this.mWallpaperManager.forgetLoadedWallpaper();
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat
    public Drawable getBuiltInDrawable(int i) {
        return this.mWallpaperManager.getBuiltInDrawable(i);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat
    public Drawable getBuiltInDrawable(int i, int i2, boolean z, float f, float f2, int i3) {
        return this.mWallpaperManager.getBuiltInDrawable(i, i2, z, f, f2, i3);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat
    public Point getDisplaySize() {
        Point point = new Point();
        Object systemService = this.context.getSystemService((Class<Object>) WindowManager.class);
        systemService.getClass();
        ((WindowManager) systemService).getDefaultDisplay().getRealSize(point);
        return point;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat
    public ParcelFileDescriptor getWallpaperFile(int i) {
        if (this.context.checkSelfPermission("android.permission.READ_MEDIA_IMAGES") != 0) {
            return null;
        }
        try {
            return this.mWallpaperManager.getWallpaperFile(i);
        } catch (Exception e) {
            Log.e("WallpaperMgrCompatVN", "Exception on getWallpaperFile", e);
            return null;
        }
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat
    public int getWallpaperId(int i) {
        return this.mWallpaperManager.getWallpaperId(i);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat
    public WallpaperInfo getWallpaperInfo() {
        return null;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat
    public int setBitmap(Bitmap bitmap, Rect rect, boolean z, int i) {
        bitmap.getClass();
        return this.mWallpaperManager.setBitmap(bitmap, rect, z, i);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat
    public int setStream(InputStream inputStream, Rect rect, boolean z, int i) {
        inputStream.getClass();
        return this.mWallpaperManager.setStream(inputStream, rect, z, i);
    }
}
