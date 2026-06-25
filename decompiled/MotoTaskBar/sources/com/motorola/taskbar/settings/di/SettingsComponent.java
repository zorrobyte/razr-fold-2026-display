package com.motorola.taskbar.settings.di;

import com.motorola.taskbar.settings.Injector;
import com.motorola.taskbar.settings.SettingsActivity;
import com.motorola.taskbar.settings.WallpaperFragment;
import com.motorola.taskbar.settings.WallpaperViewModel;

/* JADX INFO: compiled from: SettingsComponent.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface SettingsComponent extends Injector {

    /* JADX INFO: compiled from: SettingsComponent.kt */
    public interface Factory {
        SettingsComponent create(SettingsActivity settingsActivity);
    }

    void inject(SettingsActivity settingsActivity);

    void inject(WallpaperFragment wallpaperFragment);

    void inject(WallpaperViewModel wallpaperViewModel);
}
