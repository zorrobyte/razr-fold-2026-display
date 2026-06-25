package com.motorola.taskbar.settings.wallpaper.model;

import android.content.Context;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import com.motorola.taskbar.settings.InjectorProvider;
import com.motorola.taskbar.settings.wallpaper.asset.BuiltInWallpaperAsset;
import com.motorola.taskbar.settings.wallpaper.asset.CurrentWallpaperAsset;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: CurrentWallpaperInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CurrentWallpaperInfo extends WallpaperInfo {
    private IAsset mAsset;
    private List mAttributions;
    private String mCollectionId;
    private int wallpaperManagerFlag;
    public static final Companion Companion = new Companion(null);
    private static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.taskbar.settings.wallpaper.model.CurrentWallpaperInfo$Companion$CREATOR$1
        @Override // android.os.Parcelable.Creator
        public CurrentWallpaperInfo createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new CurrentWallpaperInfo(parcel, null);
        }

        @Override // android.os.Parcelable.Creator
        public CurrentWallpaperInfo[] newArray(int i) {
            return new CurrentWallpaperInfo[i];
        }
    };

    /* JADX INFO: compiled from: CurrentWallpaperInfo.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private CurrentWallpaperInfo(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mAttributions = arrayList;
        parcel.readStringList(arrayList);
        this.wallpaperManagerFlag = parcel.readInt();
        String string = parcel.readString();
        string.getClass();
        this.mCollectionId = string;
    }

    public /* synthetic */ CurrentWallpaperInfo(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    public CurrentWallpaperInfo(List list, String str, int i) {
        list.getClass();
        str.getClass();
        this.mAttributions = list;
        this.wallpaperManagerFlag = i;
        this.mCollectionId = str;
    }

    private final IAsset createCurrentWallpaperAsset(Context context) {
        ParcelFileDescriptor wallpaperFile = InjectorProvider.Companion.getInjector().getWallpaperManagerCompat().getWallpaperFile(this.wallpaperManagerFlag);
        int i = this.wallpaperManagerFlag;
        boolean z = (i == 1 || i == 8) && wallpaperFile == null;
        Log.d("CurrentWallpaperInfoVN", "createCurrentWallpaperAsset: " + z + ", " + wallpaperFile);
        if (wallpaperFile != null) {
            try {
                wallpaperFile.close();
            } catch (IOException e) {
                Log.e("CurrentWallpaperInfoVN", "Unable to close system wallpaper ParcelFileDescriptor", e);
            }
        }
        return z ? new BuiltInWallpaperAsset(context, this.wallpaperManagerFlag) : new CurrentWallpaperAsset(context, this.wallpaperManagerFlag);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getAsset(Context context) {
        context.getClass();
        if (this.mAsset == null) {
            this.mAsset = createCurrentWallpaperAsset(context);
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
        parcel.writeStringList(this.mAttributions);
        parcel.writeInt(this.wallpaperManagerFlag);
        parcel.writeString(this.mCollectionId);
    }
}
