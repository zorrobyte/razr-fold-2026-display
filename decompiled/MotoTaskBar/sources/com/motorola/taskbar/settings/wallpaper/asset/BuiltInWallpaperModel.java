package com.motorola.taskbar.settings.wallpaper.asset;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.signature.ObjectKey;
import com.motorola.taskbar.settings.InjectorProvider;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BuiltInWallpaperModel.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BuiltInWallpaperModel {
    public static final Companion Companion = new Companion(null);
    private final WallpaperManagerCompat mWallpaperManager;
    private final int wallpaperManagerFlag;

    /* JADX INFO: compiled from: BuiltInWallpaperModel.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BuiltInWallpaperModel(Context context, int i) {
        context.getClass();
        this.wallpaperManagerFlag = i;
        this.mWallpaperManager = InjectorProvider.Companion.getInjector().getWallpaperManagerCompat();
    }

    public final Drawable getDrawable(int i, int i2) {
        return this.mWallpaperManager.getBuiltInDrawable(i, i2, true, 0.5f, 0.5f, this.wallpaperManagerFlag);
    }

    public final Key getKey() {
        return new ObjectKey(this);
    }
}
