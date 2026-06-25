package com.motorola.taskbar.settings.wallpaper.model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import com.motorola.taskbar.settings.wallpaper.asset.ResourceAsset;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppResourceWallpaperInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class AppResourceWallpaperInfo extends WallpaperInfo {
    private ResourceAsset mAsset;
    private int mFullRes;
    private String mPackageName;
    private Resources mResources;
    private ResourceAsset mThumbAsset;
    private int mThumbRes;
    public static final Companion Companion = new Companion(null);
    private static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.taskbar.settings.wallpaper.model.AppResourceWallpaperInfo$Companion$CREATOR$1
        @Override // android.os.Parcelable.Creator
        public AppResourceWallpaperInfo createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new AppResourceWallpaperInfo(parcel, null);
        }

        @Override // android.os.Parcelable.Creator
        public AppResourceWallpaperInfo[] newArray(int i) {
            return new AppResourceWallpaperInfo[i];
        }
    };

    /* JADX INFO: compiled from: AppResourceWallpaperInfo.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List getAll(Context context, ApplicationInfo applicationInfo, int i) {
            context.getClass();
            applicationInfo.getClass();
            ArrayList arrayList = new ArrayList();
            try {
                Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(applicationInfo);
                resourcesForApplication.getClass();
                String[] stringArray = resourcesForApplication.getStringArray(i);
                stringArray.getClass();
                for (String str : stringArray) {
                    int identifier = resourcesForApplication.getIdentifier(str, "drawable", applicationInfo.packageName);
                    int identifier2 = resourcesForApplication.getIdentifier(str + "_small", "drawable", applicationInfo.packageName);
                    if (identifier != 0 && identifier2 != 0) {
                        String str2 = applicationInfo.packageName;
                        str2.getClass();
                        arrayList.add(new AppResourceWallpaperInfo(str2, identifier2, identifier));
                    }
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("AppResource", "Hosting app package not found");
            }
            return arrayList;
        }
    }

    private AppResourceWallpaperInfo(Parcel parcel) {
        String string = parcel.readString();
        string.getClass();
        this.mPackageName = string;
        this.mThumbRes = parcel.readInt();
        this.mFullRes = parcel.readInt();
    }

    public /* synthetic */ AppResourceWallpaperInfo(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    public AppResourceWallpaperInfo(String str, int i, int i2) {
        str.getClass();
        this.mPackageName = str;
        this.mThumbRes = i;
        this.mFullRes = i2;
    }

    private final Resources getPackageResources(Context context) {
        Resources resources = this.mResources;
        if (resources != null) {
            resources.getClass();
            return resources;
        }
        try {
            this.mResources = context.getPackageManager().getResourcesForApplication(this.mPackageName);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("AppResource", "Could not get app resources");
        }
        Resources resources2 = this.mResources;
        resources2.getClass();
        return resources2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(AppResourceWallpaperInfo.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        obj.getClass();
        AppResourceWallpaperInfo appResourceWallpaperInfo = (AppResourceWallpaperInfo) obj;
        return Intrinsics.areEqual(this.mPackageName, appResourceWallpaperInfo.mPackageName) && this.mThumbRes == appResourceWallpaperInfo.mThumbRes && this.mFullRes == appResourceWallpaperInfo.mFullRes;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getAsset(Context context) {
        context.getClass();
        if (this.mAsset == null) {
            this.mAsset = new ResourceAsset(getPackageResources(context), this.mFullRes, null, 4, null);
        }
        return this.mAsset;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public int getBackupPermission() {
        return 0;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getThumbAsset(Context context) {
        context.getClass();
        if (this.mThumbAsset == null) {
            this.mThumbAsset = new ResourceAsset(getPackageResources(context), this.mThumbRes, null, 4, null);
        }
        ResourceAsset resourceAsset = this.mThumbAsset;
        resourceAsset.getClass();
        return resourceAsset;
    }

    public int hashCode() {
        return ((((527 + this.mPackageName.hashCode()) * 31) + this.mThumbRes) * 31) + this.mFullRes;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mThumbRes);
        parcel.writeInt(this.mFullRes);
    }
}
