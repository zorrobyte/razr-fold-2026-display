package com.motorola.taskbar.settings.wallpaper.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import com.motorola.taskbar.settings.wallpaper.asset.LiveWallpaperThumbAsset;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: LiveWallpaperInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LiveWallpaperInfo extends WallpaperInfo {
    private android.app.WallpaperInfo mInfo;
    private LiveWallpaperThumbAsset mThumbAsset;
    private boolean mVisibleTitle;
    public static final Companion Companion = new Companion(null);
    private static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.taskbar.settings.wallpaper.model.LiveWallpaperInfo$Companion$CREATOR$1
        @Override // android.os.Parcelable.Creator
        public LiveWallpaperInfo createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new LiveWallpaperInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LiveWallpaperInfo[] newArray(int i) {
            return new LiveWallpaperInfo[i];
        }
    };

    /* JADX INFO: compiled from: LiveWallpaperInfo.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final List getAllOnDevice(Context context) {
            final PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(new Intent("android.service.wallpaper.WallpaperService"), 128);
            listQueryIntentServices.getClass();
            ArrayList arrayList = new ArrayList();
            Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
            while (it.hasNext()) {
                ResolveInfo next = it.next();
                if (Intrinsics.areEqual(packageName, next.serviceInfo.packageName)) {
                    it.remove();
                } else {
                    ApplicationInfo applicationInfo = next.serviceInfo.applicationInfo;
                    applicationInfo.getClass();
                    if (isSystemApp(applicationInfo)) {
                        arrayList.add(next);
                        it.remove();
                    }
                }
            }
            if (listQueryIntentServices.isEmpty()) {
                return arrayList;
            }
            Collections.sort(listQueryIntentServices, new Comparator() { // from class: com.motorola.taskbar.settings.wallpaper.model.LiveWallpaperInfo$Companion$getAllOnDevice$1
                private final Collator mCollator = Collator.getInstance();

                @Override // java.util.Comparator
                public int compare(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
                    resolveInfo.getClass();
                    resolveInfo2.getClass();
                    return this.mCollator.compare(resolveInfo.loadLabel(packageManager), resolveInfo2.loadLabel(packageManager));
                }
            });
            arrayList.addAll(listQueryIntentServices);
            return arrayList;
        }

        private final boolean isSystemApp(ApplicationInfo applicationInfo) {
            return (applicationInfo.flags & 129) != 0;
        }

        public final List getAll(Context context, List list) {
            context.getClass();
            List allOnDevice = getAllOnDevice(context);
            ArrayList arrayList = new ArrayList();
            int size = allOnDevice.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                ResolveInfo resolveInfo = (ResolveInfo) allOnDevice.get(i);
                try {
                    android.app.WallpaperInfo wallpaperInfo = new android.app.WallpaperInfo(context, resolveInfo);
                    if (list == null || !list.contains(wallpaperInfo.getPackageName())) {
                        arrayList.add(new LiveWallpaperInfo(wallpaperInfo, z, 2, null));
                    }
                } catch (IOException e) {
                    Log.w("LiveWallpaperInfo", "Skipping wallpaper " + resolveInfo.serviceInfo, e);
                } catch (XmlPullParserException e2) {
                    Log.w("LiveWallpaperInfo", "Skipping wallpaper " + resolveInfo.serviceInfo, e2);
                }
            }
            return arrayList;
        }
    }

    public LiveWallpaperInfo(android.app.WallpaperInfo wallpaperInfo, boolean z) {
        wallpaperInfo.getClass();
        this.mInfo = wallpaperInfo;
        this.mVisibleTitle = z;
    }

    public /* synthetic */ LiveWallpaperInfo(android.app.WallpaperInfo wallpaperInfo, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(wallpaperInfo, (i & 2) != 0 ? true : z);
    }

    public LiveWallpaperInfo(Parcel parcel) {
        parcel.getClass();
        Parcelable parcelable = parcel.readParcelable(android.app.WallpaperInfo.class.getClassLoader());
        parcelable.getClass();
        this.mInfo = (android.app.WallpaperInfo) parcelable;
        this.mVisibleTitle = parcel.readInt() == 1;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getAsset(Context context) {
        context.getClass();
        return null;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getThumbAsset(Context context) {
        context.getClass();
        if (this.mThumbAsset == null) {
            this.mThumbAsset = new LiveWallpaperThumbAsset(context, this.mInfo);
        }
        LiveWallpaperThumbAsset liveWallpaperThumbAsset = this.mThumbAsset;
        liveWallpaperThumbAsset.getClass();
        return liveWallpaperThumbAsset;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeParcelable(this.mInfo, 0);
        parcel.writeInt(this.mVisibleTitle ? 1 : 0);
    }
}
