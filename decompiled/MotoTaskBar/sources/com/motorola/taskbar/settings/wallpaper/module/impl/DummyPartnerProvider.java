package com.motorola.taskbar.settings.wallpaper.module.impl;

import android.content.res.Resources;
import com.motorola.taskbar.settings.wallpaper.module.PartnerProvider;
import java.io.File;

/* JADX INFO: compiled from: DummyPartnerProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DummyPartnerProvider implements PartnerProvider {
    private final File legacyWallpaperDirectory;
    private final String packageName;
    private final Resources resources;
    private final boolean shouldHideDefaultWallpaper;

    @Override // com.motorola.taskbar.settings.wallpaper.module.PartnerProvider
    public File getLegacyWallpaperDirectory() {
        return this.legacyWallpaperDirectory;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.PartnerProvider
    public String getPackageName() {
        return this.packageName;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.PartnerProvider
    public Resources getResources() {
        return this.resources;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.PartnerProvider
    public boolean getShouldHideDefaultWallpaper() {
        return this.shouldHideDefaultWallpaper;
    }
}
