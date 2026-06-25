package com.motorola.taskbar.settings.wallpaper.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.motorola.taskbar.settings.InjectorProvider;
import com.motorola.taskbar.settings.wallpaper.asset.FileAsset;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import com.motorola.taskbar.settings.wallpaper.module.PartnerProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: LegacyPartnerWallpaperInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LegacyPartnerWallpaperInfo extends WallpaperInfo {
    private FileAsset mAsset;
    private boolean mFetchedSystemLegacyDir;
    private String mFullName;
    private File mSystemLegacyDir;
    private FileAsset mThumbAsset;
    private String mThumbName;
    public static final Companion Companion = new Companion(null);
    private static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.taskbar.settings.wallpaper.model.LegacyPartnerWallpaperInfo$Companion$CREATOR$1
        @Override // android.os.Parcelable.Creator
        public LegacyPartnerWallpaperInfo createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new LegacyPartnerWallpaperInfo(parcel, (DefaultConstructorMarker) null);
        }

        @Override // android.os.Parcelable.Creator
        public LegacyPartnerWallpaperInfo[] newArray(int i) {
            return new LegacyPartnerWallpaperInfo[i];
        }
    };

    /* JADX INFO: compiled from: LegacyPartnerWallpaperInfo.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List getAll(Context context) {
            String strSubstring;
            context.getClass();
            PartnerProvider partnerProvider = InjectorProvider.Companion.getInjector().getPartnerProvider();
            ArrayList arrayList = new ArrayList();
            File legacyWallpaperDirectory = partnerProvider.getLegacyWallpaperDirectory();
            if (legacyWallpaperDirectory != null && legacyWallpaperDirectory.isDirectory()) {
                File[] fileArrListFiles = legacyWallpaperDirectory.listFiles();
                fileArrListFiles.getClass();
                for (File file : fileArrListFiles) {
                    if (file.isFile()) {
                        String name = file.getName();
                        String name2 = file.getName();
                        name2.getClass();
                        int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) name2, '.', 0, false, 6, (Object) null);
                        if (iLastIndexOf$default > -1) {
                            strSubstring = name2.substring(iLastIndexOf$default);
                            strSubstring.getClass();
                            name2 = name2.substring(0, iLastIndexOf$default);
                            name2.getClass();
                        } else {
                            strSubstring = "";
                        }
                        if (!StringsKt.endsWith$default(name2, "_small", false, 2, null)) {
                            name.getClass();
                            arrayList.add(new LegacyPartnerWallpaperInfo(name2 + "_small" + strSubstring, name));
                        }
                    }
                }
            }
            return arrayList;
        }
    }

    private LegacyPartnerWallpaperInfo(Parcel parcel) {
        String string = parcel.readString();
        string.getClass();
        this.mThumbName = string;
        String string2 = parcel.readString();
        string2.getClass();
        this.mFullName = string2;
    }

    public /* synthetic */ LegacyPartnerWallpaperInfo(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    public LegacyPartnerWallpaperInfo(String str, String str2) {
        str.getClass();
        str2.getClass();
        this.mThumbName = str;
        this.mFullName = str2;
    }

    private final File getSystemLegacyDir(Context context) {
        if (!this.mFetchedSystemLegacyDir) {
            this.mSystemLegacyDir = InjectorProvider.Companion.getInjector().getPartnerProvider().getLegacyWallpaperDirectory();
            this.mFetchedSystemLegacyDir = true;
        }
        return this.mSystemLegacyDir;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getAsset(Context context) {
        context.getClass();
        if (this.mAsset == null) {
            File systemLegacyDir = getSystemLegacyDir(context);
            this.mAsset = new FileAsset(systemLegacyDir != null ? new File(systemLegacyDir, this.mFullName) : null);
        }
        FileAsset fileAsset = this.mAsset;
        fileAsset.getClass();
        return fileAsset;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo
    public IAsset getThumbAsset(Context context) {
        context.getClass();
        if (this.mThumbAsset == null) {
            File systemLegacyDir = getSystemLegacyDir(context);
            this.mThumbAsset = new FileAsset(systemLegacyDir != null ? new File(systemLegacyDir, this.mThumbName) : null);
        }
        FileAsset fileAsset = this.mThumbAsset;
        fileAsset.getClass();
        return fileAsset;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeString(this.mThumbName);
        parcel.writeString(this.mFullName);
    }
}
