package com.motorola.taskbar.settings.wallpaper.model;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.motorola.taskbar.settings.wallpaper.asset.ContentUriAsset;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ImageWallpaperInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ImageWallpaperInfo extends WallpaperInfo {
    private ContentUriAsset mAsset;
    private boolean mIsAssetUnCached;
    private Uri uri;
    public static final Companion Companion = new Companion(null);
    private static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.taskbar.settings.wallpaper.model.ImageWallpaperInfo$Companion$CREATOR$1
        @Override // android.os.Parcelable.Creator
        public ImageWallpaperInfo createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new ImageWallpaperInfo(parcel, null);
        }

        @Override // android.os.Parcelable.Creator
        public ImageWallpaperInfo[] newArray(int i) {
            return new ImageWallpaperInfo[i];
        }
    };

    /* JADX INFO: compiled from: ImageWallpaperInfo.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ImageWallpaperInfo(Uri uri) {
        uri.getClass();
        this.uri = uri;
    }

    private ImageWallpaperInfo(Parcel parcel) {
        this.uri = Uri.parse(parcel.readString());
    }

    public /* synthetic */ ImageWallpaperInfo(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getAsset(Context context) {
        context.getClass();
        if (this.mIsAssetUnCached) {
            this.mAsset = new ContentUriAsset(context, getUri(), null, true, 4, null);
        } else if (this.mAsset == null) {
            this.mAsset = new ContentUriAsset(context, getUri(), null, false, 12, null);
        }
        ContentUriAsset contentUriAsset = this.mAsset;
        contentUriAsset.getClass();
        return contentUriAsset;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getThumbAsset(Context context) {
        context.getClass();
        IAsset asset = getAsset(context);
        asset.getClass();
        return asset;
    }

    public Uri getUri() {
        return this.uri;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeString(getUri().toString());
    }
}
