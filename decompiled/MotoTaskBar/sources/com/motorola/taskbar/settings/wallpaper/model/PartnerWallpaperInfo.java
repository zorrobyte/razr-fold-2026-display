package com.motorola.taskbar.settings.wallpaper.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.motorola.taskbar.settings.Injector;
import com.motorola.taskbar.settings.InjectorProvider;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import com.motorola.taskbar.settings.wallpaper.asset.ResourceAsset;
import com.motorola.taskbar.settings.wallpaper.module.PartnerProvider;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PartnerWallpaperInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PartnerWallpaperInfo extends WallpaperInfo {
    private ResourceAsset mAsset;
    private boolean mFetchedPartnerResources;
    private int mFullRes;
    private Resources mPartnerResources;
    private ResourceAsset mThumbAsset;
    private int mThumbRes;
    public static final Companion Companion = new Companion(null);
    private static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.taskbar.settings.wallpaper.model.PartnerWallpaperInfo$Companion$CREATOR$1
        @Override // android.os.Parcelable.Creator
        public PartnerWallpaperInfo createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new PartnerWallpaperInfo(parcel, (DefaultConstructorMarker) null);
        }

        @Override // android.os.Parcelable.Creator
        public PartnerWallpaperInfo[] newArray(int i) {
            return new PartnerWallpaperInfo[i];
        }
    };

    /* JADX INFO: compiled from: PartnerWallpaperInfo.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List getAll(Context context) {
            int identifier;
            context.getClass();
            Injector injector = InjectorProvider.Companion.getInjector();
            injector.getWallpaperManagerCompat();
            PartnerProvider partnerProvider = injector.getPartnerProvider();
            ArrayList arrayList = new ArrayList();
            Resources resources = partnerProvider.getResources();
            String packageName = partnerProvider.getPackageName();
            if (resources != null && (identifier = resources.getIdentifier("partner_desktop_display_wallpapers", "array", packageName)) != 0) {
                String[] stringArray = resources.getStringArray(identifier);
                stringArray.getClass();
                for (String str : stringArray) {
                    if (resources.getIdentifier(str, "drawable", packageName) != 0) {
                        int identifier2 = resources.getIdentifier(str + "_small", "drawable", packageName);
                        if (identifier2 != 0) {
                            arrayList.add(new PartnerWallpaperInfo(identifier2, resources.getIdentifier(str, "drawable", packageName)));
                        }
                    } else {
                        Log.e("PartnerWallpaperInfo", "Couldn't find wallpaper " + str);
                    }
                }
            }
            return arrayList;
        }
    }

    public PartnerWallpaperInfo(int i, int i2) {
        this.mThumbRes = i;
        this.mFullRes = i2;
    }

    private PartnerWallpaperInfo(Parcel parcel) {
        this.mThumbRes = parcel.readInt();
        this.mFullRes = parcel.readInt();
    }

    public /* synthetic */ PartnerWallpaperInfo(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    private final Resources getPartnerResources(Context context) {
        if (!this.mFetchedPartnerResources) {
            this.mPartnerResources = InjectorProvider.Companion.getInjector().getPartnerProvider().getResources();
            this.mFetchedPartnerResources = true;
        }
        Resources resources = this.mPartnerResources;
        resources.getClass();
        return resources;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getAsset(Context context) {
        context.getClass();
        if (this.mAsset == null) {
            this.mAsset = new ResourceAsset(getPartnerResources(context), this.mFullRes, null, 4, null);
        }
        ResourceAsset resourceAsset = this.mAsset;
        resourceAsset.getClass();
        return resourceAsset;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getThumbAsset(Context context) {
        context.getClass();
        if (this.mThumbAsset == null) {
            this.mThumbAsset = new ResourceAsset(getPartnerResources(context), this.mThumbRes, null, 4, null);
        }
        ResourceAsset resourceAsset = this.mThumbAsset;
        resourceAsset.getClass();
        return resourceAsset;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeInt(this.mThumbRes);
        parcel.writeInt(this.mFullRes);
    }
}
