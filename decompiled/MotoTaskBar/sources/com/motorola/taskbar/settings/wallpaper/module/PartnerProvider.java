package com.motorola.taskbar.settings.wallpaper.module;

import android.content.res.Resources;
import java.io.File;

/* JADX INFO: compiled from: PartnerProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PartnerProvider {
    File getLegacyWallpaperDirectory();

    String getPackageName();

    Resources getResources();

    boolean getShouldHideDefaultWallpaper();
}
