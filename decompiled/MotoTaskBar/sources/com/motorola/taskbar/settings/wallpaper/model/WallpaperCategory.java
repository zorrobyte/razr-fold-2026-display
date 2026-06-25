package com.motorola.taskbar.settings.wallpaper.model;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: WallpaperCategory.kt */
/* JADX INFO: loaded from: classes2.dex */
public class WallpaperCategory extends Category {
    private final int featuredThumbnailIndex;
    private final boolean isEnumerable;
    protected final Object mWallpapersLock;
    private final List mutableWallpapers;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WallpaperCategory(String str, String str2, int i, List list, int i2) {
        super(str, str2, i);
        str.getClass();
        str2.getClass();
        list.getClass();
        this.mutableWallpapers = list;
        this.featuredThumbnailIndex = i2;
        this.mWallpapersLock = new Object();
        this.isEnumerable = true;
    }

    public /* synthetic */ WallpaperCategory(String str, String str2, int i, List list, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, i, list, (i3 & 16) != 0 ? 0 : i2);
    }

    public List fetchWallpapers(Context context, boolean z) {
        context.getClass();
        return new ArrayList(this.mutableWallpapers);
    }

    protected final List getMutableWallpapers() {
        return this.mutableWallpapers;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.model.Category
    public boolean isEnumerable() {
        return this.isEnumerable;
    }
}
