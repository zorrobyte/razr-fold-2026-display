package com.motorola.taskbar.settings;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.motorola.taskbar.settings.wallpaper.model.Category;
import com.motorola.taskbar.settings.wallpaper.model.ImageWallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.model.WallpaperCategory;
import com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.module.CategoryProvider;
import com.motorola.taskbar.settings.wallpaper.module.CategoryReceiver;
import com.motorola.taskbar.settings.wallpaper.module.CurrentWallpaperInfoFactory;
import com.motorola.taskbar.settings.wallpaper.module.MyPhotosStarter;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperPersister;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WallpaperViewModel.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class WallpaperViewModel extends ViewModel {
    public static final Companion Companion = new Companion(null);
    private final MutableLiveData _allWallpaperCategory;
    private ArrayList _allWallpapers;
    private final MutableLiveData _currentWallpaper;
    private LiveData allWallpapers;
    private final Application application;
    public CategoryProvider categoryProvider;
    public CurrentWallpaperInfoFactory currentWallpaperInfoFactory;
    public WallpaperPersister wallpaperPersister;

    /* JADX INFO: compiled from: WallpaperViewModel.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public static boolean $r8$lambda$J6SpZrktii1G3yBReUjm1LaQAxA(Category category) {
        return category instanceof WallpaperCategory;
    }

    public static boolean $r8$lambda$hy_qlsD69wzezs0bJtNncEdsdfo(WallpaperViewModel wallpaperViewModel, WallpaperInfo wallpaperInfo) {
        Application application = wallpaperViewModel.application;
        application.getClass();
        return wallpaperInfo.getAsset(application) != null;
    }

    public static Stream $r8$lambda$ipPX9aleP9RXsccdxWiKj79mis4(WallpaperViewModel wallpaperViewModel, Category category) {
        category.getClass();
        Application application = wallpaperViewModel.application;
        application.getClass();
        return ((WallpaperCategory) category).fetchWallpapers(application, true).stream();
    }

    public WallpaperViewModel(WallpaperFragment wallpaperFragment) {
        wallpaperFragment.getClass();
        this.application = wallpaperFragment.requireActivity().getApplication();
        this._allWallpapers = new ArrayList();
        MutableLiveData mutableLiveData = new MutableLiveData();
        this._allWallpaperCategory = mutableLiveData;
        this._currentWallpaper = new MutableLiveData();
        FragmentActivity fragmentActivityRequireActivity = wallpaperFragment.requireActivity();
        fragmentActivityRequireActivity.getClass();
        ((SettingsActivity) fragmentActivityRequireActivity).mSettingsComponent.inject(this);
        loadAllCategory();
        this.allWallpapers = Transformations.map(mutableLiveData, new Function1() { // from class: com.motorola.taskbar.settings.WallpaperViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return WallpaperViewModel._init_$lambda$4(this.f$0, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List _init_$lambda$4(final WallpaperViewModel wallpaperViewModel, List list) {
        Stream stream = list.stream();
        final Function1 function1 = new Function1() { // from class: com.motorola.taskbar.settings.WallpaperViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((Category) obj).isEnumerable());
            }
        };
        Stream streamFilter = stream.filter(new Predicate(function1) { // from class: com.motorola.taskbar.settings.WallpaperViewModel$sam$java_util_function_Predicate$0
            private final /* synthetic */ Function1 function;

            {
                function1.getClass();
                this.function = function1;
            }

            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj) {
                return ((Boolean) this.function.invoke(obj)).booleanValue();
            }
        });
        final Function1 function12 = new Function1() { // from class: com.motorola.taskbar.settings.WallpaperViewModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(WallpaperViewModel.$r8$lambda$J6SpZrktii1G3yBReUjm1LaQAxA((Category) obj));
            }
        };
        Stream streamFilter2 = streamFilter.filter(new Predicate(function12) { // from class: com.motorola.taskbar.settings.WallpaperViewModel$sam$java_util_function_Predicate$0
            private final /* synthetic */ Function1 function;

            {
                function12.getClass();
                this.function = function12;
            }

            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj) {
                return ((Boolean) this.function.invoke(obj)).booleanValue();
            }
        });
        final Function1 function13 = new Function1() { // from class: com.motorola.taskbar.settings.WallpaperViewModel$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return WallpaperViewModel.$r8$lambda$ipPX9aleP9RXsccdxWiKj79mis4(this.f$0, (Category) obj);
            }
        };
        Stream streamFlatMap = streamFilter2.flatMap(new Function(function13) { // from class: com.motorola.taskbar.settings.WallpaperViewModel$sam$java_util_function_Function$0
            private final /* synthetic */ Function1 function;

            {
                function13.getClass();
                this.function = function13;
            }

            @Override // java.util.function.Function
            public final /* synthetic */ Object apply(Object obj) {
                return this.function.invoke(obj);
            }
        });
        final Function1 function14 = new Function1() { // from class: com.motorola.taskbar.settings.WallpaperViewModel$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(WallpaperViewModel.$r8$lambda$hy_qlsD69wzezs0bJtNncEdsdfo(this.f$0, (WallpaperInfo) obj));
            }
        };
        Object objCollect = streamFlatMap.filter(new Predicate(function14) { // from class: com.motorola.taskbar.settings.WallpaperViewModel$sam$java_util_function_Predicate$0
            private final /* synthetic */ Function1 function;

            {
                function14.getClass();
                this.function = function14;
            }

            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj) {
                return ((Boolean) this.function.invoke(obj)).booleanValue();
            }
        }).collect(Collectors.toList());
        objCollect.getClass();
        return (List) objCollect;
    }

    private final void loadAllCategory() {
        this._allWallpapers.clear();
        getCategoryProvider().fetchCategories(new CategoryReceiver() { // from class: com.motorola.taskbar.settings.WallpaperViewModel.loadAllCategory.1
            @Override // com.motorola.taskbar.settings.wallpaper.module.CategoryReceiver
            public void doneFetchingCategories() {
                WallpaperViewModel.this._allWallpaperCategory.setValue(WallpaperViewModel.this._allWallpapers);
            }

            @Override // com.motorola.taskbar.settings.wallpaper.module.CategoryReceiver
            public void onCategoryReceived(Category category) {
                category.getClass();
                WallpaperViewModel.this._allWallpapers.add(category);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit updateCurrentlySetWallpaper$lambda$5(WallpaperViewModel wallpaperViewModel, WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2) {
        wallpaperInfo.getClass();
        wallpaperInfo2.getClass();
        wallpaperViewModel._currentWallpaper.setValue(wallpaperInfo);
        return Unit.INSTANCE;
    }

    public final boolean canShowCurrentWallpaper(Context context) {
        context.getClass();
        return context.checkSelfPermission("android.permission.READ_WALLPAPER_INTERNAL") == 0 || isReadExternalStoragePermissionGranted(context);
    }

    public final LiveData getAllWallpapers() {
        return this.allWallpapers;
    }

    public final CategoryProvider getCategoryProvider() {
        CategoryProvider categoryProvider = this.categoryProvider;
        if (categoryProvider != null) {
            return categoryProvider;
        }
        Intrinsics.throwUninitializedPropertyAccessException("categoryProvider");
        return null;
    }

    public final LiveData getCurrentWallpaper() {
        return this._currentWallpaper;
    }

    public final CurrentWallpaperInfoFactory getCurrentWallpaperInfoFactory() {
        CurrentWallpaperInfoFactory currentWallpaperInfoFactory = this.currentWallpaperInfoFactory;
        if (currentWallpaperInfoFactory != null) {
            return currentWallpaperInfoFactory;
        }
        Intrinsics.throwUninitializedPropertyAccessException("currentWallpaperInfoFactory");
        return null;
    }

    public final WallpaperPersister getWallpaperPersister() {
        WallpaperPersister wallpaperPersister = this.wallpaperPersister;
        if (wallpaperPersister != null) {
            return wallpaperPersister;
        }
        Intrinsics.throwUninitializedPropertyAccessException("wallpaperPersister");
        return null;
    }

    public final boolean isReadExternalStoragePermissionGranted(Context context) {
        context.getClass();
        return context.checkSelfPermission("android.permission.READ_MEDIA_IMAGES") == 0;
    }

    public final void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        Uri data;
        activity.getClass();
        if (1001 == i) {
            if (-1 != i2) {
                if (Log.isLoggable("WallpaperViewModel", 5)) {
                    Log.w("WallpaperViewModel", "on pick wallpaper, target canceled with code " + i2);
                    return;
                }
                return;
            }
            if (Log.isLoggable("WallpaperViewModel", 4)) {
                Log.i("WallpaperViewModel", "on pick wallpaper, data " + (intent != null ? intent.getData() : null));
            }
            if (intent == null || (data = intent.getData()) == null) {
                return;
            }
            setCustomPhotoWallpaper(activity, new ImageWallpaperInfo(data));
        }
    }

    public final void onRequestPermissionsResult(Activity activity, int i, String[] strArr, int[] iArr) {
        activity.getClass();
        strArr.getClass();
        iArr.getClass();
        if (i == 1000) {
            if ((strArr.length == 0) || !Intrinsics.areEqual(strArr[0], "android.permission.READ_MEDIA_IMAGES")) {
                return;
            }
            if (iArr.length == 0) {
                return;
            }
            if (iArr[0] != 0) {
                activity.shouldShowRequestPermissionRationale("android.permission.READ_MEDIA_IMAGES");
                return;
            }
            Application application = this.application;
            application.getClass();
            updateCurrentlySetWallpaper(application, false);
        }
    }

    public final void requestCustomPhotoPicker(MyPhotosStarter myPhotosStarter) {
        myPhotosStarter.getClass();
        myPhotosStarter.requestCustomPhotoPicker();
    }

    public final void requestExternalStoragePermission(Fragment fragment) {
        fragment.getClass();
        Context contextRequireContext = fragment.requireContext();
        contextRequireContext.getClass();
        if (isReadExternalStoragePermissionGranted(contextRequireContext)) {
            return;
        }
        fragment.requestPermissions(new String[]{"android.permission.READ_MEDIA_IMAGES"}, 1000);
    }

    public final void setCategoryProvider(CategoryProvider categoryProvider) {
        categoryProvider.getClass();
        this.categoryProvider = categoryProvider;
    }

    public final void setCurrentWallpaperInfoFactory(CurrentWallpaperInfoFactory currentWallpaperInfoFactory) {
        currentWallpaperInfoFactory.getClass();
        this.currentWallpaperInfoFactory = currentWallpaperInfoFactory;
    }

    public final void setCustomPhotoWallpaper(Activity activity, WallpaperInfo wallpaperInfo) {
        activity.getClass();
        wallpaperInfo.getClass();
        WallpaperPersister.DefaultImpls.setIndividualWallpaperWithPosition$default(getWallpaperPersister(), activity, wallpaperInfo, 1, -1, null, 16, null);
    }

    public final void setWallpaper(Activity activity, View view, WallpaperInfo wallpaperInfo) {
        activity.getClass();
        wallpaperInfo.getClass();
        WallpaperPersister.DefaultImpls.setIndividualWallpaperWithPosition$default(getWallpaperPersister(), activity, wallpaperInfo, 1, -1, null, 16, null);
    }

    public final void setWallpaperPersister(WallpaperPersister wallpaperPersister) {
        wallpaperPersister.getClass();
        this.wallpaperPersister = wallpaperPersister;
    }

    public final void showCustomPhotoPicker(Fragment fragment) {
        fragment.getClass();
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        fragment.startActivityForResult(intent, 1001);
    }

    public final void updateCurrentlySetWallpaper(Context context, boolean z) {
        context.getClass();
        if (canShowCurrentWallpaper(context)) {
            getCurrentWallpaperInfoFactory().createCurrentWallpaperInfos(z, new Function2() { // from class: com.motorola.taskbar.settings.WallpaperViewModel$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return WallpaperViewModel.updateCurrentlySetWallpaper$lambda$5(this.f$0, (WallpaperInfo) obj, (WallpaperInfo) obj2);
                }
            });
        } else {
            Log.w("WallpaperViewModel", "no permission to show current set wallpaper");
        }
    }
}
