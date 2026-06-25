package com.motorola.taskbar.settings.wallpaper.module.impl;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import com.motorola.taskbar.guide.R$array;
import com.motorola.taskbar.guide.R$drawable;
import com.motorola.taskbar.settings.wallpaper.model.AppResourceWallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.model.Category;
import com.motorola.taskbar.settings.wallpaper.model.DefaultWallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.model.ImageCategory;
import com.motorola.taskbar.settings.wallpaper.model.LegacyPartnerWallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.model.LiveWallpaperCategory;
import com.motorola.taskbar.settings.wallpaper.model.LiveWallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.model.PartnerWallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.model.ThirdPartyAppCategory;
import com.motorola.taskbar.settings.wallpaper.model.WallpaperCategory;
import com.motorola.taskbar.settings.wallpaper.module.CategoryProvider;
import com.motorola.taskbar.settings.wallpaper.module.CategoryReceiver;
import com.motorola.taskbar.settings.wallpaper.module.Feature;
import com.motorola.taskbar.settings.wallpaper.module.PartnerProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: DefaultCategoryProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
public class DefaultCategoryProvider implements CategoryProvider {
    public static final Companion Companion = new Companion(null);
    private final Context mAppContext;
    private ArrayList mCategories;
    private boolean mFetchedCategories;
    private final PartnerProvider partnerProvider;

    /* JADX INFO: compiled from: DefaultCategoryProvider.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: DefaultCategoryProvider.kt */
    public class FetchCategoriesTask extends AsyncTask {
        private final Context mAppContext;
        private final CategoryReceiver mReceiver;
        private final PartnerProvider partnerProvider;

        public FetchCategoriesTask(CategoryReceiver categoryReceiver, Context context, PartnerProvider partnerProvider) {
            categoryReceiver.getClass();
            context.getClass();
            partnerProvider.getClass();
            this.mReceiver = categoryReceiver;
            this.partnerProvider = partnerProvider;
            Context applicationContext = context.getApplicationContext();
            applicationContext.getClass();
            this.mAppContext = applicationContext;
        }

        private final List getExcludedThirdPartyPackageNames() {
            return CollectionsKt.listOf((Object[]) new String[]{"com.android.launcher", "com.android.wallpaper.livepicker"});
        }

        private final Category getMyPhotosCategory() {
            return new ImageCategory("My photo wallpaper", "my_photo_wallpaper", 100, R$drawable.ic_wallpaper_uncheck);
        }

        private final Category getOnDeviceCategory() {
            ArrayList arrayList = new ArrayList();
            if (Feature.INSTANCE.getForceLoadDefaultWallpaper() || !this.partnerProvider.getShouldHideDefaultWallpaper()) {
                arrayList.add(new DefaultWallpaperInfo(8));
            }
            arrayList.addAll(PartnerWallpaperInfo.Companion.getAll(this.mAppContext));
            arrayList.addAll(LegacyPartnerWallpaperInfo.Companion.getAll(this.mAppContext));
            List listPrivateDeviceWallpapers = privateDeviceWallpapers();
            if (listPrivateDeviceWallpapers != null) {
                arrayList.addAll(listPrivateDeviceWallpapers);
            }
            return new WallpaperCategory("On Device wallpaper", "on_device_wallpaper", 200, arrayList, 0, 16, null);
        }

        private final List privateDeviceWallpapers() {
            AppResourceWallpaperInfo.Companion companion = AppResourceWallpaperInfo.Companion;
            Context context = this.mAppContext;
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            applicationInfo.getClass();
            return companion.getAll(context, applicationInfo, R$array.preload_wallpapers_array);
        }

        private final void publishDeviceCategories() {
            if (Feature.INSTANCE.getSupportOnDeviceWallpaper()) {
                publishProgress(getOnDeviceCategory());
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            voidArr.getClass();
            publishProgress(getMyPhotosCategory());
            publishDeviceCategories();
            Feature feature = Feature.INSTANCE;
            if (feature.getSupportLiveWallpaper() && this.mAppContext.getPackageManager().hasSystemFeature("android.software.live_wallpaper")) {
                List all = LiveWallpaperInfo.Companion.getAll(this.mAppContext, getExcludedLiveWallpaperPackageNames());
                if (!all.isEmpty()) {
                    publishProgress(new LiveWallpaperCategory("Live wallpapers", "live_wallpaper", 300, all, getExcludedLiveWallpaperPackageNames()));
                }
            }
            if (!feature.getSupport3rdWallpaper()) {
                return null;
            }
            Iterator it = ThirdPartyAppCategory.Companion.getAll(this.mAppContext, 400, getExcludedThirdPartyPackageNames()).iterator();
            while (it.hasNext()) {
                publishProgress((ThirdPartyAppCategory) it.next());
            }
            return null;
        }

        public final List getExcludedLiveWallpaperPackageNames() {
            return new ArrayList();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r1) {
            this.mReceiver.doneFetchingCategories();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(Category... categoryArr) {
            categoryArr.getClass();
            super.onProgressUpdate(Arrays.copyOf(categoryArr, categoryArr.length));
            for (Category category : categoryArr) {
                this.mReceiver.onCategoryReceived(category);
            }
        }
    }

    public DefaultCategoryProvider(Context context, PartnerProvider partnerProvider) {
        context.getClass();
        partnerProvider.getClass();
        this.partnerProvider = partnerProvider;
        Context applicationContext = context.getApplicationContext();
        applicationContext.getClass();
        this.mAppContext = applicationContext;
        this.mCategories = new ArrayList();
    }

    private final void doFetch(final CategoryReceiver categoryReceiver, boolean z) {
        new FetchCategoriesTask(new CategoryReceiver() { // from class: com.motorola.taskbar.settings.wallpaper.module.impl.DefaultCategoryProvider$doFetch$delegatingReceiver$1
            @Override // com.motorola.taskbar.settings.wallpaper.module.CategoryReceiver
            public void doneFetchingCategories() {
                categoryReceiver.doneFetchingCategories();
                this.mFetchedCategories = true;
            }

            @Override // com.motorola.taskbar.settings.wallpaper.module.CategoryReceiver
            public void onCategoryReceived(Category category) {
                category.getClass();
                categoryReceiver.onCategoryReceived(category);
                this.mCategories.add(category);
            }
        }, this.mAppContext, this.partnerProvider).execute(new Void[0]);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.CategoryProvider
    public void fetchCategories(CategoryReceiver categoryReceiver, boolean z) {
        categoryReceiver.getClass();
        if (z || !this.mFetchedCategories) {
            if (z) {
                this.mCategories.clear();
                this.mFetchedCategories = false;
            }
            doFetch(categoryReceiver, z);
            return;
        }
        Iterator it = this.mCategories.iterator();
        it.getClass();
        while (it.hasNext()) {
            Object next = it.next();
            next.getClass();
            categoryReceiver.onCategoryReceived((Category) next);
        }
        categoryReceiver.doneFetchingCategories();
    }
}
