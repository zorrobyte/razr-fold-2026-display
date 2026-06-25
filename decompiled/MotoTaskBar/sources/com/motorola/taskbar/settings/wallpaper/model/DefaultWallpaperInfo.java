package com.motorola.taskbar.settings.wallpaper.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.motorola.taskbar.settings.wallpaper.asset.BuiltInWallpaperAsset;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: DefaultWallpaperInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DefaultWallpaperInfo extends WallpaperInfo {
    private IAsset mAsset;
    private int mWallpaperManagerFlag;
    public static final Companion Companion = new Companion(null);
    private static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.taskbar.settings.wallpaper.model.DefaultWallpaperInfo$Companion$CREATOR$1
        @Override // android.os.Parcelable.Creator
        public DefaultWallpaperInfo createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new DefaultWallpaperInfo(parcel, null);
        }

        @Override // android.os.Parcelable.Creator
        public DefaultWallpaperInfo[] newArray(int i) {
            return new DefaultWallpaperInfo[i];
        }
    };

    /* JADX INFO: compiled from: DefaultWallpaperInfo.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DefaultWallpaperInfo(int i) {
        this.mWallpaperManagerFlag = i;
    }

    private DefaultWallpaperInfo(Parcel parcel) {
        this.mWallpaperManagerFlag = parcel.readInt();
    }

    public /* synthetic */ DefaultWallpaperInfo(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getAsset(Context context) {
        context.getClass();
        if (this.mAsset == null) {
            this.mAsset = new BuiltInWallpaperAsset(context, this.mWallpaperManagerFlag);
        }
        IAsset iAsset = this.mAsset;
        iAsset.getClass();
        return iAsset;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getThumbAsset(Context context) {
        context.getClass();
        return getAsset(context);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeInt(this.mWallpaperManagerFlag);
    }
}
