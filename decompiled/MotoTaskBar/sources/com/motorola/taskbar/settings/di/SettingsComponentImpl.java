package com.motorola.taskbar.settings.di;

import android.content.Context;
import com.motorola.taskbar.settings.InjectorProvider;
import com.motorola.taskbar.settings.SettingsActivity;
import com.motorola.taskbar.settings.WallpaperFragment;
import com.motorola.taskbar.settings.WallpaperViewModel;
import com.motorola.taskbar.settings.wallpaper.module.BitmapCropper;
import com.motorola.taskbar.settings.wallpaper.module.CategoryProvider;
import com.motorola.taskbar.settings.wallpaper.module.CurrentWallpaperInfoFactory;
import com.motorola.taskbar.settings.wallpaper.module.PartnerProvider;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperPersister;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperSupportChecker;
import com.motorola.taskbar.settings.wallpaper.module.impl.DefaultBitmapCropper;
import com.motorola.taskbar.settings.wallpaper.module.impl.DefaultCategoryProvider;
import com.motorola.taskbar.settings.wallpaper.module.impl.DefaultCurrentWallpaperInfoFactory;
import com.motorola.taskbar.settings.wallpaper.module.impl.DefaultWallpaperPersister;
import com.motorola.taskbar.settings.wallpaper.module.impl.DummyPartnerProvider;
import java.util.function.Supplier;

/* JADX INFO: compiled from: SettingsComponent.kt */
/* JADX INFO: loaded from: classes2.dex */
final class SettingsComponentImpl implements SettingsComponent {
    private Supplier bitmapCropperProvider;
    private Supplier categoryProviderProvider;
    private Supplier currentWallpaperInfoFactoryProvider;
    private Supplier partnerProviderProvider;
    private Supplier wallpaperManagerProvider;
    private Supplier wallpaperPersisterProvider;
    private Supplier wallpaperSupportCheckerProvider;

    public SettingsComponentImpl(final SettingsActivity settingsActivity) {
        settingsActivity.getClass();
        this.wallpaperSupportCheckerProvider = new Supplier() { // from class: com.motorola.taskbar.settings.di.SettingsComponentImpl$wallpaperSupportCheckerProvider$1
            @Override // java.util.function.Supplier
            public final WallpaperSupportChecker get() {
                return new WallpaperSupportChecker() { // from class: com.motorola.taskbar.settings.di.SettingsComponentImpl$wallpaperSupportCheckerProvider$1.1
                };
            }
        };
        this.wallpaperManagerProvider = new Supplier() { // from class: com.motorola.taskbar.settings.di.SettingsComponentImpl$wallpaperManagerProvider$1
            @Override // java.util.function.Supplier
            public final WallpaperManagerCompat get() {
                WallpaperManagerCompat.Companion companion = WallpaperManagerCompat.Companion;
                Context applicationContext = settingsActivity.getApplicationContext();
                applicationContext.getClass();
                return companion.newInstance(applicationContext);
            }
        };
        this.partnerProviderProvider = new Supplier() { // from class: com.motorola.taskbar.settings.di.SettingsComponentImpl$partnerProviderProvider$1
            @Override // java.util.function.Supplier
            public final PartnerProvider get() {
                return new DummyPartnerProvider();
            }
        };
        this.bitmapCropperProvider = new Supplier() { // from class: com.motorola.taskbar.settings.di.SettingsComponentImpl$bitmapCropperProvider$1
            @Override // java.util.function.Supplier
            public final BitmapCropper get() {
                return new DefaultBitmapCropper();
            }
        };
        this.wallpaperPersisterProvider = new Supplier() { // from class: com.motorola.taskbar.settings.di.SettingsComponentImpl$wallpaperPersisterProvider$1
            @Override // java.util.function.Supplier
            public final WallpaperPersister get() {
                Object obj = this.this$0.wallpaperManagerProvider.get();
                obj.getClass();
                return new DefaultWallpaperPersister((WallpaperManagerCompat) obj, this.this$0.bitmapCropperProvider);
            }
        };
        this.categoryProviderProvider = new Supplier() { // from class: com.motorola.taskbar.settings.di.SettingsComponentImpl$categoryProviderProvider$1
            @Override // java.util.function.Supplier
            public final CategoryProvider get() {
                SettingsActivity settingsActivity2 = settingsActivity;
                Object obj = this.partnerProviderProvider.get();
                obj.getClass();
                return new DefaultCategoryProvider(settingsActivity2, (PartnerProvider) obj);
            }
        };
        this.currentWallpaperInfoFactoryProvider = new Supplier() { // from class: com.motorola.taskbar.settings.di.SettingsComponentImpl$currentWallpaperInfoFactoryProvider$1
            @Override // java.util.function.Supplier
            public final CurrentWallpaperInfoFactory get() {
                Object obj = this.this$0.wallpaperManagerProvider.get();
                obj.getClass();
                return new DefaultCurrentWallpaperInfoFactory((WallpaperManagerCompat) obj);
            }
        };
    }

    @Override // com.motorola.taskbar.settings.Injector
    public PartnerProvider getPartnerProvider() {
        Object obj = this.partnerProviderProvider.get();
        obj.getClass();
        return (PartnerProvider) obj;
    }

    @Override // com.motorola.taskbar.settings.Injector
    public WallpaperManagerCompat getWallpaperManagerCompat() {
        Object obj = this.wallpaperManagerProvider.get();
        obj.getClass();
        return (WallpaperManagerCompat) obj;
    }

    @Override // com.motorola.taskbar.settings.di.SettingsComponent
    public void inject(SettingsActivity settingsActivity) {
        settingsActivity.getClass();
        InjectorProvider.Companion.installInjector(this);
    }

    @Override // com.motorola.taskbar.settings.di.SettingsComponent
    public void inject(WallpaperFragment wallpaperFragment) {
        wallpaperFragment.getClass();
    }

    @Override // com.motorola.taskbar.settings.di.SettingsComponent
    public void inject(WallpaperViewModel wallpaperViewModel) {
        wallpaperViewModel.getClass();
        wallpaperViewModel.setWallpaperPersister((WallpaperPersister) this.wallpaperPersisterProvider.get());
        wallpaperViewModel.setCategoryProvider((CategoryProvider) this.categoryProviderProvider.get());
        wallpaperViewModel.setCurrentWallpaperInfoFactory((CurrentWallpaperInfoFactory) this.currentWallpaperInfoFactoryProvider.get());
    }
}
