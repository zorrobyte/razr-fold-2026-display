package com.motorola.taskbar.settings.wallpaper.model;

import android.content.Context;
import android.os.Parcelable;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: WallpaperInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class WallpaperInfo implements Parcelable {
    public static final Companion Companion = new Companion(null);
    private final int backupPermission = 1;

    /* JADX INFO: compiled from: WallpaperInfo.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public abstract IAsset getAsset(Context context);

    public int getBackupPermission() {
        return this.backupPermission;
    }

    public abstract IAsset getThumbAsset(Context context);
}
