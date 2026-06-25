package com.motorola.taskbar.settings.wallpaper.model;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: LiveWallpaperCategory.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LiveWallpaperCategory extends WallpaperCategory {
    private final List mExcludedPackages;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LiveWallpaperCategory(String str, String str2, int i, List list, List list2) {
        super(str, str2, i, list, 0, 16, null);
        str.getClass();
        str2.getClass();
        list.getClass();
        this.mExcludedPackages = list2;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.WallpaperCategory
    public List fetchWallpapers(Context context, boolean z) {
        ArrayList arrayList;
        context.getClass();
        if (!z) {
            return super.fetchWallpapers(context, false);
        }
        synchronized (this.mWallpapersLock) {
            List mutableWallpapers = getMutableWallpapers();
            List all = LiveWallpaperInfo.Companion.getAll(context, this.mExcludedPackages);
            mutableWallpapers.clear();
            mutableWallpapers.addAll(all);
            arrayList = new ArrayList(mutableWallpapers);
        }
        return arrayList;
    }
}
