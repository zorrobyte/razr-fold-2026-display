package com.motorola.taskbar.settings.wallpaper.module;

import android.app.WallpaperInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.ParcelFileDescriptor;
import com.motorola.taskbar.settings.wallpaper.module.impl.WallpaperManagerCompatDesktop;
import java.io.InputStream;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: WallpaperManagerCompat.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class WallpaperManagerCompat {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: WallpaperManagerCompat.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final WallpaperManagerCompat newInstance(Context context) {
            context.getClass();
            return new WallpaperManagerCompatDesktop(context);
        }
    }

    public abstract void forgetLoadedWallpaper();

    public abstract Drawable getBuiltInDrawable(int i);

    public abstract Drawable getBuiltInDrawable(int i, int i2, boolean z, float f, float f2, int i3);

    public abstract Point getDisplaySize();

    public abstract ParcelFileDescriptor getWallpaperFile(int i);

    public abstract int getWallpaperId(int i);

    public abstract WallpaperInfo getWallpaperInfo();

    public abstract int setBitmap(Bitmap bitmap, Rect rect, boolean z, int i);

    public abstract int setStream(InputStream inputStream, Rect rect, boolean z, int i);
}
